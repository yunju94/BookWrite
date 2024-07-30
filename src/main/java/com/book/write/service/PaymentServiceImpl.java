package com.book.write.service;

import com.book.write.dto.PaymentCallbackRequest;
import com.book.write.dto.RequestPayDto;

import com.book.write.entity.Point;
import com.book.write.repository.PaymentRepository;
import com.book.write.repository.PointRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;

import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

import static com.book.write.constant.PaymentStatus.CANCEL;
import static com.book.write.constant.PaymentStatus.OK;


@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PointRepository pointRepository;
    private final PaymentRepository paymentRepository;
    private final IamportClient iamportClient;

    @Override
    public RequestPayDto findRequestDto(Long id) {

        Point order = pointRepository.findOrderAndPaymentAndMember(id);

        return RequestPayDto.builder()
                .buyerName(order.getMember().getName())
                .buyerEmail(order.getMember().getEmail())
                .buyerTel(order.getMember().getTel())
                .paymentPrice(order.getPayment().getPrice())
                .orderUid(order.getPayment().getPaymentUid())
                .build();
    }


    @Override
    public IamportResponse<Payment> paymentByCallback(PaymentCallbackRequest request) {

        try {

            // 결제 단건 조회(아임포트)
            IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(request.getPaymentUid());

            // 주문내역 조회
            Point order = pointRepository.findOrderAndPayment(request.getOrderUid());


            // 결제 완료가 아니면
            if(!iamportResponse.getResponse().getStatus().equals("paid")) {
                // 주문, 결제 삭제
                pointRepository.delete(order);
                paymentRepository.delete(order.getPayment());

                throw new RuntimeException("결제 미완료");
            }

            // DB에 저장된 결제 금액
            int price = order.getPayment().getPrice();
            // 실 결제 금액
            int iamportPrice = iamportResponse.getResponse().getAmount().intValue();

            // 결제 금액 검증
            if(iamportPrice != price) {
                // 주문, 결제 삭제
                pointRepository.delete(order);
                paymentRepository.delete(order.getPayment());

                // 결제금액 위변조로 의심되는 결제금액을 취소(아임포트)
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportPrice)));

                throw new RuntimeException("결제금액 위변조 의심");
            }

            // 결제 상태 변경
            order.getPayment().changePaymentBySuccess(OK, iamportResponse.getResponse().getImpUid(), order);

            return iamportResponse;

        } catch (IamportResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void  updatePayment(String orderUid){
        com.book.write.entity.Payment payment =paymentRepository.findByOrderUid(orderUid);
        payment.setStatus(OK);

    }

    public void  canclePayment(String orderUid){
        com.book.write.entity.Payment payment =paymentRepository.findByOrderUid(orderUid);
        payment.setStatus(CANCEL);

    }

}

package com.book.write.service;

import com.book.write.dto.PaymentCallbackRequest;
import com.book.write.dto.RequestPayDto;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;


public interface PaymentService {
    // 결제 요청 데이터 조회
    RequestPayDto findRequestDto(Long id);
    // 결제(콜백)
    IamportResponse<Payment> paymentByCallback(PaymentCallbackRequest request);

    void updatePayment(String orderUid);
    void canclePayment(String orderUid);


}

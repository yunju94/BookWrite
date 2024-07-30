package com.book.write.service;

import com.book.write.constant.PaymentStatus;
import com.book.write.entity.Member;
import com.book.write.entity.Payment;
import com.book.write.entity.Point;
import com.book.write.repository.PaymentRepository;
import com.book.write.repository.PointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.book.write.entity.Point.createOrder;

@Service
@Transactional
@AllArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final PaymentRepository paymentRepository;

    public Point  paymentPoint(Member member, int price, int point){
        Payment payment = new Payment(price, PaymentStatus.READY);
        Point pointOrder=createOrder(member, payment, price, point);
        payment.setPaymentUid(pointOrder.getOrderUid());
        pointRepository.save(pointOrder);
        paymentRepository.save(payment);
        return pointOrder;
    }

    public  List<Point> SearchIdtopoint(Long memberId){
       List<Point> pointList = pointRepository.findByMemberId(memberId);
       return pointList;
    }


}

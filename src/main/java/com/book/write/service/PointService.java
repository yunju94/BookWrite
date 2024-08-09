package com.book.write.service;

import com.book.write.constant.Order;
import com.book.write.constant.PaymentStatus;
import com.book.write.entity.Coin;
import com.book.write.entity.Member;
import com.book.write.entity.Payment;
import com.book.write.entity.Point;
import com.book.write.repository.CoinRepository;
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
    private  final CoinRepository coinRepository;

    public Point  paymentPoint(Member member, int price, int point){

        Payment payment = new Payment(price, PaymentStatus.READY);

        Point pointOrder= createOrder(member, payment, price);

        payment.setPaymentUid(pointOrder.getOrderUid());
        pointRepository.save(pointOrder);
        paymentRepository.save(payment);


        return pointOrder;
    }

    public  List<Point> SearchIdtopoint(Long memberId){
        return pointRepository.findByMemberId(memberId);
    }

    public void  cancelOrder(String OrderUid){
       Point point= pointRepository.findOrderAndPayment(OrderUid);
       point.setOrderstatus(Order.CANCEL);
    }


    public  void  coinChanger(Member member,double KDR_coin, double YES_coin, int coinMoney){
        coinRepository.save(Coin.PointcreateCoin(member, KDR_coin, YES_coin));
        pointRepository.save(Point.coinChange(member, coinMoney));
    }

    public Point saveCoin(Member member){
        Point point= Point.saveCoin(member);
        pointRepository.save(point);
        return point;
    }



}

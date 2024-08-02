package com.book.write.entity;

import com.book.write.constant.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "Point")
@Getter
@Setter
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "point_id")
    private Long id;

    private String orderUid; // 주문 번호

    private int money;//결제 금액



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private Order orderstatus;

    public  static Point createOrder(Member member, Payment payment, int price){
        Point order = new Point();
        order.setOrderUid(UUID.randomUUID().toString());
        order.setMember(member);
        order.setPayment(payment);

        order.setMoney(price);
        order.setOrderstatus(Order.OK);

        return  order;
        //주문서 생성
        //현재 로그인된 멤버 주문서에 추가
        //주문 아이템 리스트를 반복문을 통해서 주문서에 추가
        //상태는 주문으로 세팅
        //주문 시간은 현재시간으로 세팅
        //주문서 리턴

    }


}

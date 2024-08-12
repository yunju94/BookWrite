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

    private  int point;//포인트

    private  String content; //내용


    @ManyToOne(fetch = FetchType.LAZY, cascade =CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade =CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private Order orderstatus;

    public  static Point createOrder(Member member, Payment payment, int price, int point){
        Point order = new Point();
        order.setOrderUid(UUID.randomUUID().toString());
        order.setContent("포인트 구매");
        order.setPoint(point);
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

    public  static  Point saveCoin(Member member){
        Point order = new Point();
        order.setOrderUid(UUID.randomUUID().toString());
        order.setContent("코인 사용");
        order.setMember(member);
        order.setMoney(0);
        order.setOrderstatus(Order.OK);

        return  order;
    }

    public  static Point coinChange(Member member, int coinMoney){
        Point order = new Point();
        order.setOrderUid(UUID.randomUUID().toString());
        order.setContent("코인 환전");
        order.setMember(member);
        order.setMoney(0);
        order.setPoint(-coinMoney);
        order.setOrderstatus(Order.OK);

        return order;
    }

    public  static  Point saveCoinAuthor(Member Author){
        Point order = new Point();
        order.setOrderUid(UUID.randomUUID().toString());
        order.setContent("코인 적립");
        order.setMember(Author);
        order.setMoney(0);
        order.setOrderstatus(Order.OK);

        return  order;
    }


}

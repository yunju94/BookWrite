package com.book.write.entity;


import com.book.write.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "payment")
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int price;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private String paymentUid; // 결제 고유 번호

    @Column(name = "order_uid")
    private  String OrderUid;


    @Builder
    public Payment(int price, PaymentStatus status) {
        this.price = price;
        this.status = status;
    }

    public void changePaymentBySuccess(PaymentStatus status, String paymentUid,  Point point) {
        this.status = status;
        this.paymentUid = paymentUid;
        this.OrderUid = point.getOrderUid();
    }

}
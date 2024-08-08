package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "coin")
@Getter
@Setter
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coin_id")
    private Long id;

    private double KDR_coin;//변경 코인
    private double YES_coin;//변경 코인

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "write_id")
    private WriteDetail writeDetail;




    public  static  Coin PointcreateCoin(Member member, double KDR_coin, double YES_coin){
        Coin coins = new Coin();
        coins.setKDR_coin(KDR_coin);
        coins.setYES_coin(YES_coin);
        coins.setMember(member);
        return coins;

    }

    public  static  Coin createCoin(Member member, double KDR_coin, double YES_coin , Point point){
        Coin coins = new Coin();
        coins.setYES_coin(-YES_coin);
        coins.setKDR_coin(-KDR_coin);
        coins.setPoint(point);
        coins.setMember(member);
        return coins;
    }

}

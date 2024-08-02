package com.book.write.entity;

import com.book.write.constant.Order;
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

    private int point;//변경 포인트

    private int KDR_coin;//변경 코인
    private int YES_coin;//변경 코인

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public  static Coin pointOrder(Member member, int point){
        Coin coin = new Coin();
        coin.setPoint(point);
        coin.setMember(member);
        return coin;
    }
    public  static  Coin KDR_createCoin(Member member, int coin, int coinMoney){
        Coin coins = new Coin();
        coins.setMember(member);
        coins.setKDR_coin(coin);
        coins.setPoint(-coinMoney);
        return coins;
    }

    public  static  Coin YES_createCoin(Member member, int coin, int coinMoney){
        Coin coins = new Coin();
        coins.setMember(member);
        coins.setYES_coin(coin);
        coins.setPoint(-coinMoney);
        return coins;
    }

}

package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@Entity
@Table(name = "Purchanse")
@Getter
@Setter
public class Purchanse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Purchanse_id")
    private  Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "write_id")
    private Write write;//본 회차

    private double KDR_coin;//키다리 스튜디오 코인

    private double YES_coin; //예스 24 코인


    public static Purchanse createSave(Write write, double KDR_coin, double YES_coin){
        Purchanse purchanse = new Purchanse();
        purchanse.setWrite(write);
        purchanse.setKDR_coin(KDR_coin);
        purchanse.setYES_coin(YES_coin);
        return  purchanse;
    }


}

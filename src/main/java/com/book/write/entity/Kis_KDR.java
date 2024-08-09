package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "kis_KDR")
@Getter
@Setter
public class Kis_KDR {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "kis_KDR_id")
    private  Long id;

    private Integer  won;//금액

    private LocalDate upDate;//올라온 날짜.

    public  static  Kis_KDR createKDR(Integer  won, LocalDate upDate){
        Kis_KDR kdr = new Kis_KDR();
        kdr.setWon(won);
        kdr.setUpDate(upDate);
        return kdr;
    }

}

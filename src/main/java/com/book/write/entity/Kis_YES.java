package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Kis_YES")
@Getter
@Setter
public class Kis_YES {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Kis_YES_id")
    private  Long id;

    private Integer  won;//금액

    private LocalDate upDate;//올라온 날짜.

    public  static  Kis_YES createYES(Integer  won, LocalDate upDate){
        Kis_YES yes = new Kis_YES();
        yes.setWon(won);
        yes.setUpDate(upDate);
        return yes;
    }


}

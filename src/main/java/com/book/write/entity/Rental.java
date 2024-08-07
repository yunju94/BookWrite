package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "rental")
@Getter
@Setter
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "point_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "write_id")
    private Write write;

    private LocalDateTime endTime;//마감시간

    public  static  Rental  create(Write write){
        Rental rental = new Rental();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = now.plusDays(3);
        rental.setWrite(write);
        rental.setEndTime(endTime);

        return  rental;
    }
}

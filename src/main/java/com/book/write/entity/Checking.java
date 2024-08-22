package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "checking")//출석체크
@Setter
@Getter
public class Checking extends BaseDateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "checking_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;


    public  static Checking saveChecking(Member member, Point point){
        Checking checking = new Checking();
        checking.setPoint(point);
        checking.setMember(member);
        checking.setRegDate(LocalDate.now());
        return  checking;
    }



}

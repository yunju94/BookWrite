package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "write")
@Getter
@Setter
public class Write {
    @Id
    @Column(name = "write_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String miniTitle;//회차 제목

    @Column(columnDefinition = "TEXT")
    private String miniWrite;//회차 내용

    private int count;//조회수

    private int heart;//별점



}

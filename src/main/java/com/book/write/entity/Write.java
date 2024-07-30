package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "write")
@Getter
@Setter
public class Write {
    @Id
    @Column(name = "write_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writeInfo_id")
    private WriteInfo writeInfo;//작성단위


    private String miniTitle;//회차 제목

    @Column(columnDefinition = "TEXT")
    private String miniWrite;//회차 내용

    private int count;//조회수

    private int heart;//별점



}

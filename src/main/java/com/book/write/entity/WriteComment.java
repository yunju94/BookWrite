package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "WriteComment")
@Getter
@Setter
public class WriteComment {
    @Id
    @Column(name = "WriteComment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;


    @Column(columnDefinition = "TEXT")
    private String content;//댓글

}

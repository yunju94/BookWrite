package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Fantasy")
@Getter
@Setter
public class Fantasy {
    @Id
    @Column(name = "Fantasy_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private List<WriteInfo> writeInfo;


}

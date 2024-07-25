package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private WriteInfo writeInfo;


}

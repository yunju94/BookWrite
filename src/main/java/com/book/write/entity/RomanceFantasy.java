package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "RomanceFantasy")
@Getter
@Setter
public class RomanceFantasy {
    @Id
    @Column(name = "RomanceFantasy_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private WriteInfo writeInfo;


}

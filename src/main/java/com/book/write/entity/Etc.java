package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Etc")
@Getter
@Setter
public class Etc {
    @Id
    @Column(name = "Etc_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private WriteInfo writeInfo;


}

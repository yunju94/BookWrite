package com.book.write.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "member")
@AllArgsConstructor
@Getter
@Setter
public class Member {
    @Id
    @Column(name = "member_id")
    private int id;

    private String name;
    private String nickname;
    private String tel;
    private String loginId;
    private String loginPassword;

}

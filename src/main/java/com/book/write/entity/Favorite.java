package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "favorite")
@Getter
@Setter
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "write_info_id")
    private WriteInfo writeInfo;



    public static Favorite createFav(Member member, WriteInfo writeInfo){
        Favorite favorite = new Favorite();
        favorite.setMember(member);
        favorite.setWriteInfo(writeInfo);

        return favorite;
    }
}

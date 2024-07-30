package com.book.write.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "writeImg")
@Getter
@Setter
public class WriteImg {
    @Id
    @Column(name = "writeImg_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    private String imgName;
    private String oriImgName;
    private String imgUrl;

    public  void  uploadImg(String oriImgName, String imgName, String imgUrl){
        this.imgName = imgName;
        this.imgUrl = imgUrl;
        this.oriImgName = oriImgName;
    }

}

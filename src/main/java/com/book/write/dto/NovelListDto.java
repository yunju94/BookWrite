package com.book.write.dto;

import com.book.write.constant.Category;
import com.book.write.entity.Member;
import com.book.write.entity.WriteImg;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NovelListDto {
    private  Long id;

    private String title;//제목
    private Member member;//글쓴이

    private Category category;//종류

    private  int totalHeart;
    private  int totalView;


    private WriteImg writeImg;
    @QueryProjection
    public NovelListDto(Member member,Long id, String title, Category category,int totalHeart, int totalView, WriteImg imgUrl) {
        this.id = id;
        this.member = member;
        this.title = title;

        this.category = category;
        this.totalHeart = totalHeart;
        this.totalView = totalView;

        this.writeImg= imgUrl;


    }
}

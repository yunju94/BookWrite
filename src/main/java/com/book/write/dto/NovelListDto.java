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

    private String title;//제목
    private Member member;//글쓴이

    private Category category;//종류


    private Long Heart;//관심수

    private Long view;//조회수

    private WriteImg writeImg;
    @QueryProjection
    public NovelListDto(Member member, String title, Long heart, Long view, Category category, WriteImg imgUrl) {
        this.member = member;
        this.title = title;
        this.Heart = heart;
        this.view = view;
        this.category = category;
        this.writeImg= imgUrl;


    }
}

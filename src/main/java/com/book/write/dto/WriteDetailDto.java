package com.book.write.dto;

import com.book.write.entity.WriteInfo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
public class WriteDetailDto {
    private Long id;

    private WriteInfo writeInfo;//작성단위


    private String miniTitle;//회차 제목

    @Length(min = 1, max = 10000, message = "10,000자 이하로 입력해주세요.")
    private String miniWrite;//회차 내용

    private int viewcount;//조회수

    private int commentCount;//댓글수


    private int heart;//추천


}

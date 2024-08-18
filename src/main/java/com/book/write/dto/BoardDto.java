package com.book.write.dto;

import com.book.write.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
    private  Long id;
    private  String content;
    private Member member;



}

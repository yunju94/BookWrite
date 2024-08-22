package com.book.write.entity;

import com.book.write.dto.BoardDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "board")//문의
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private  Long id;

    @Column(columnDefinition = "TEXT")
    private String content;//문의글

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private  Member member;

    public  static  Board  createDto(BoardDto boardDto){
        Board board = new Board();
        board.setContent(boardDto.getContent());
        board.setMember(boardDto.getMember());

        return  board;
    }

}

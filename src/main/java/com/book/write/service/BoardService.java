package com.book.write.service;

import com.book.write.dto.BoardDto;
import com.book.write.entity.Board;
import com.book.write.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<Board> findByAll(){
        return  boardRepository.findAllByOrderByIdDesc();
    }

    public  void  saveBoardDto(BoardDto boardDto){
       Board board =  Board.createDto(boardDto);
       boardRepository.save(board);

    }
}

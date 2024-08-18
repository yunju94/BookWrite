package com.book.write.repository;

import com.book.write.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board>  findAllByOrderByIdDesc();

}

package com.book.write.service;

import com.book.write.dto.CommentDto;
import com.book.write.entity.Comment;
import com.book.write.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    public  void  saveCreate(CommentDto commentDto){
        Comment comment = Comment.createDto(commentDto);
        commentRepository.save(comment);
    }

    public List<Comment> searchCommentList(Long detailId){
        return  commentRepository.findByWriteDetailId(detailId);
    }

    public  Comment searchComment(Long id){
        return  commentRepository.findById(id).orElseThrow();
    }

    public  void  deleteCommnet(Comment comment){
        commentRepository.delete(comment);
    }

}

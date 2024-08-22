package com.book.write.controller;

import com.book.write.dto.CommentDto;
import com.book.write.entity.Comment;
import com.book.write.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

import static com.book.write.entity.QComment.comment;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private  final CommentService commentService;
    @PostMapping(value = "/comment/write")
    public String commentadd(@Valid CommentDto commentDto){
        commentService.saveCreate(commentDto);
        return "redirect:/detail/novel/" + commentDto.getWriteDetail().getId();
    }

    @DeleteMapping(value = "/comment/delete/{commentId}")
    public @ResponseBody ResponseEntity commentDelete(@PathVariable Long commentId, Model model){

        Comment comment = commentService.searchComment(commentId);

        try {


           commentService.deleteCommnet(comment);

       }catch (Exception e){

           model.addAttribute("errorMessage", "오류!!!!!");
       }


       return  new ResponseEntity(comment.getWriteDetail().getId(), HttpStatus.OK);
    }


}

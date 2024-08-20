package com.book.write.controller;

import com.book.write.dto.CommentDto;
import com.book.write.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private  final CommentService commentService;
    @PostMapping(value = "/comment/write")
    public String commentadd(@Valid CommentDto commentDto){
        commentService.saveCreate(commentDto);
        return "redirect:/detail/novel/" + commentDto.getWriteDetail().getId();
    }

}

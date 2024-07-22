package com.book.write.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MemberController {

    @GetMapping(value = "/member/login")
    public String login(){
        return "member/login";
    }
    @GetMapping(value = "/member/new")
    public String memberNew(){
        return "member/new";
    }

}

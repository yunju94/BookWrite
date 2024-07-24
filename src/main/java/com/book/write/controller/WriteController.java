package com.book.write.controller;

import com.book.write.dto.WriteInfoDto;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class WriteController {

    @GetMapping(value = "/write")
    public String writePage(){
        return "write/list";
    }

    @GetMapping(value = "/write/InfoForm")
    public String writeInfoForm(Model model){
        model.addAttribute("writeInfoDto", new WriteInfoDto());
        return "write/InfoForm";
    }

    @PostMapping(value = "/write/InfoForm")
    public String writeInfoFormPost(@Valid WriteInfoDto writeInfoDto){


        return "redirect:/";
    }




}

package com.book.write.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class WriteDetailController {

    @GetMapping(value = "/detail/new/{id}")
    public String WriteDetailNew(@PathVariable Long id){
        return "writeDetail/new";
    }
}

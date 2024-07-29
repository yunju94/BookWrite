package com.book.write.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PointController {


    @GetMapping(value = "/charge")
    public String pointMainCharge(){
        return  "point/Charge";
    }

}

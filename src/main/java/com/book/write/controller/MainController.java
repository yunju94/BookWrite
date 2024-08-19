package com.book.write.controller;

import com.book.write.entity.WriteInfo;
import com.book.write.service.WriteInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {
    private  final WriteInfoService writeInfoService;
    @GetMapping(value = "/")
    public String mainPage(Model model){

        List<WriteInfo> writeInfoList = writeInfoService.findAll();
        model.addAttribute("writeInfoList", writeInfoList);
        return "main";
    }


}

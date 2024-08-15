package com.book.write.controller;

import com.book.write.entity.Member;
import com.book.write.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Transactional
@RequiredArgsConstructor
public class AdminController {
    private  final MemberService memberService;

    @GetMapping(value = "/admin/menu")
    public String adminMenu(Model model){
        System.out.println("111111111222222222333333333");
        List<Member> memberList = memberService.AllMember();
        model.addAttribute("member", memberList);
        return "admin/Menu";
    }
}

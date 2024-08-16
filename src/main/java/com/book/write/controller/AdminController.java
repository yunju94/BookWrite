package com.book.write.controller;

import com.book.write.constant.Role;
import com.book.write.entity.Member;
import com.book.write.repository.MemberRepository;
import com.book.write.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Transactional
@RequiredArgsConstructor
public class AdminController {
    private  final MemberService memberService;
    private  final MemberRepository memberRepository;

    @GetMapping(value = "/admin/menu")
    public String adminMenu(Model model){
        System.out.println("111111111222222222333333333");
        List<Member> memberList = memberService.AllMember();
        model.addAttribute("member", memberList);
        return "admin/Menu";
    }

    @PostMapping(value = "/admin/change/{selectedRole}/{selectId}")
    public @ResponseBody ResponseEntity changeRole(@PathVariable Role selectedRole,
                                                   @PathVariable Long selectId){
        Member member = memberService.searchMemberId(selectId);
        member.setRole(selectedRole);
        memberRepository.save(member);
        return new ResponseEntity(member.getId(), HttpStatus.OK);
    }
}

package com.book.write.controller;

import com.book.write.dto.WriteInfoDto;
import com.book.write.entity.Member;
import com.book.write.entity.WriteInfo;
import com.book.write.service.MemberService;
import com.book.write.service.WriteInfoService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class WriteController {

    private final MemberService memberService;
    private  final WriteInfoService writeInfoService;

    @GetMapping(value = "/write")
    public String writePage(Model model, Principal principal){
        if (principal == null){
            return "member/login";
        }
        String Id = principal.getName();
        Member member = memberService.SearchIdtoName(Id);
        WriteInfo writeInfo = writeInfoService.SearchMemberId(member.getId());


        model.addAttribute("writeInfo", writeInfo);


        return "write/list";
    }

    @GetMapping(value = "/write/InfoForm")
    public String writeInfoForm(Model model, Principal principal){

        Member member = memberService.SearchIdtoName(principal.getName());
        WriteInfoDto writeInfoDto = new WriteInfoDto();
        writeInfoDto.setMember(member);

        model.addAttribute("writeInfoDto", writeInfoDto);
        return "write/InfoForm";
    }

    @PostMapping(value = "/write/InfoForm")
    public String writeInfoFormPost(@Valid WriteInfoDto writeInfoDto){

        writeInfoService.save(writeInfoDto);
        return "redirect:/";
    }




}

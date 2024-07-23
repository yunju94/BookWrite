package com.book.write.controller;

import com.book.write.dto.MemberFormDto;
import com.book.write.entity.Member;
import com.book.write.service.MemberService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @GetMapping(value = "/member/login")
    public String login(){
        return "member/login";
    }

    @GetMapping(value = "/member/login/error")
    public String loginError(Model model){
        model.addAttribute("errorMessage", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/login";
    }

    @GetMapping(value = "/member/agreement")
    public  String agreement(){

        return "member/Agreement";
    }

    @GetMapping(value = "/member/{Id}")
    public  @ResponseBody  ResponseEntity  searchName(@PathVariable String Id){
        Member member =memberService.SearchIdtoName(Id);

        return  new ResponseEntity<Member>(member, HttpStatus.OK);
    }

    @GetMapping(value = "/member/new")
    public String memberNew(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto() );;
        return "member/new";
    }
    @PostMapping(value = "/member/new")
    public String membersave(@Valid MemberFormDto memberFormDto){

        memberService.saveMemberForm(memberFormDto);
        return "redirect:/";


    }

    @PostMapping(value = "/member/Idch/{IdCheck}")
    public @ResponseBody ResponseEntity IdCheck(@PathVariable String IdCheck){
        Member member = memberService.SearchIdtoName(IdCheck);

        if (member == null) {

            return new ResponseEntity<>("사용 가능한 아이디입니다.", HttpStatus.OK);
        }

        return new ResponseEntity<>("중복된 아이디입니다.", HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value = "/member/{NickName}")
    public @ResponseBody ResponseEntity NickNameCheck(@PathVariable String NickName){
        Member member = memberService.SearchNickName(NickName) ;
        if (member == null) {
            return new ResponseEntity<>("사용 가능한 아이디입니다.", HttpStatus.OK);
        }

        return new ResponseEntity<>("중복된 아이디입니다.", HttpStatus.BAD_REQUEST);

    }



}

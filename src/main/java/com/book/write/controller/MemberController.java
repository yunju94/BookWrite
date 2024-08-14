package com.book.write.controller;


import com.book.write.dto.MemberFormDto;
import com.book.write.dto.SessionUser;
import com.book.write.entity.Member;
import com.book.write.service.MailService;
import com.book.write.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    private final MailService mailService;

    private final HttpSession httpSession;
    String confirm = "";
    boolean confirmCheck = false;
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
    public @ResponseBody ResponseEntity IdCheckPage(@PathVariable("IdCheck") String CheckId){
        Member member = memberService.SearchIdtoName(CheckId);

        if (member == null) {

            String jsonResponse = "{ \"message\": \"사용 가능한 아이디입니다.\" }";
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
        String jsonResponse = "{ \"message\": \"중복된 아이디입니다.\" }";
        return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value = "/member/{NickName}")
    public @ResponseBody ResponseEntity NickNameCheck(@PathVariable("NickName") String NickName){
        Member member = memberService.SearchNickName(NickName) ;
        if (member == null) {
            String jsonResponse = "{ \"message\": \"사용 가능한 별명입니다.\" }";
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }

        String jsonResponse = "{ \"message\": \"중복된 별명입니다.\" }";
        return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value = "/members/{email}/emailConfirm")
    public @ResponseBody ResponseEntity emailConfrim(@PathVariable("email") String email)
            throws Exception{

        System.out.println("email"+ email);

        confirm = mailService.sendSimpleMessage(email);
        System.out.println("인증코드 : "+ confirm);
        return new ResponseEntity<String>("인증 메일을 보냈습니다.", HttpStatus.OK);
    }


    @PostMapping(value = "/members/{code}/codeCheck")
    public @ResponseBody ResponseEntity codeConfirm(@PathVariable("code")String code)
            throws Exception {
        if (confirm.equals(code)) {
            confirmCheck = true;
            return new ResponseEntity<String>("인증 성공하였습니다.", HttpStatus.OK);
        }
        return new ResponseEntity<String>("인증 코드를 올바르게 입력해주세요.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/member/oauth/new")
    public String oauthMemberForm (Model model, Principal principal, OAuth2AuthenticationToken authenticationToken){

        String email=getEmailFromPrincipalOrSession(principal);
        Member member = memberService.searchEmail(email);
        if (member.getNickname().isEmpty()||member.getNickname().equals(null) ){
            MemberFormDto memberFormDto = new MemberFormDto();
            model.addAttribute("memberFormDto", memberFormDto);
            return "member/oauthForm";
        }
        return "redirect:/";
    }

    @PostMapping(value = "/member/oauth")
    public  String oauthMemberFormSend(@Valid MemberFormDto memberFormDto, Principal principal){

        String email=getEmailFromPrincipalOrSession(principal);
        Member member = memberService.searchEmail(email);
        System.out.println("member"+member);
        memberService.updateMemberForm(memberFormDto, member);
        return "redirect:/";
    }

    private String getEmailFromPrincipalOrSession(Principal principal) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            return user.getEmail();
        }
        return principal.getName();
    }



}




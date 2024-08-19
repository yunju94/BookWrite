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
import org.springframework.validation.BindingResult;
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
    boolean IdCheck = false;
    boolean nickCheck = false;
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
    public String membersave(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "member/new";//다시 회원가입으로 돌려보닙니다.
        }

        if (!IdCheck){
            model.addAttribute("errorMessage", "아이디 인증하세요");
            return "member/new";
        }

        if (!nickCheck){
            model.addAttribute("errorMessage", "별명 인증하세요");
            return "member/new";
        }
        if (!confirmCheck){
            model.addAttribute("errorMessage", "이메일 인증하세요");
            return "member/new";
        }
        try {
            //Member 객체 생성
            memberService.saveMemberForm(memberFormDto);


        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/new";
        }

        return "redirect:/";
    }

    @PostMapping(value = "/member/Idch/{IdCheck}")
    public @ResponseBody ResponseEntity IdCheckPage(@PathVariable("IdCheck") String CheckId){
        Member member = memberService.SearchIdtoName(CheckId);

        if (member == null) {

            String jsonResponse = "{ \"message\": \"사용 가능한 아이디입니다.\" }";
            IdCheck = true;
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
        String jsonResponse = "{ \"message\": \"중복된 아이디입니다.\" }";
        IdCheck = false;
        return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value = "/member/{NickName}")
    public @ResponseBody ResponseEntity NickNameCheck(@PathVariable("NickName") String NickName){
        Member member = memberService.SearchNickName(NickName) ;
        if (member == null) {
            String jsonResponse = "{ \"message\": \"사용 가능한 별명입니다.\" }";
            nickCheck = true;
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }

        String jsonResponse = "{ \"message\": \"중복된 별명입니다.\" }";
        nickCheck = false;
        return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);

    }

    @PostMapping(value = "/member/{email}/emailConfirm")
    public @ResponseBody ResponseEntity emailConfrim(@PathVariable String email)
            throws Exception{

        System.out.println("email"+ email);

        confirm = mailService.sendSimpleMessage(email);
        System.out.println("인증코드 : "+ confirm);
        return new ResponseEntity<String>("인증 메일을 보냈습니다.", HttpStatus.OK);
    }


    @PostMapping(value = "/member/{code}/codeCheck")
    public @ResponseBody ResponseEntity codeConfirm(@PathVariable("code") String code)
            throws Exception {
        if (confirm.equals(code)) {
            confirmCheck = true;
            return new ResponseEntity<String>("인증 성공하였습니다.", HttpStatus.OK);
        }
        return new ResponseEntity<String>("인증 코드를 올바르게 입력해주세요.", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/member/oauth/new")
    public String oauthMemberForm (Model model, Principal principal){

        String email=getEmailFromPrincipalOrSession(principal);
        Member member = memberService.searchEmail(email);
        if (member.getNickname() == null || member.getNickname().isEmpty()){
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


    @PostMapping(value =  "/member/mypage")
    public  String updateMemberInfo(@ModelAttribute  MemberFormDto memberFormDto
                                , BindingResult bindingResult
                                ,Model model, Principal principal){
        if (bindingResult.hasErrors()) {
            System.out.println("bindingResult"+bindingResult);
            System.out.println(bindingResult.hasErrors());
            model.addAttribute("errorMessage", "오류가 발생했습니다.");
        }
        String email=getEmailFromPrincipalOrSession(principal);
        Member member = memberService.searchEmail(email);
       
        memberService.myPageUpdate(memberFormDto, member);
        return "redirect:/";
    }


}




package com.book.write.controller;

import com.book.write.constant.Order;
import com.book.write.dto.PaymentCallbackRequest;
import com.book.write.dto.RequestPayDto;
import com.book.write.dto.SessionUser;
import com.book.write.entity.Checking;
import com.book.write.entity.Member;
import com.book.write.entity.Point;
import com.book.write.service.*;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PointController {
    private final MemberService memberService;
    private  final PointService pointService;
    private  final PaymentService paymentService;
    private  final CheckingService checkingService;


    private final HttpSession httpSession;

    private String getEmailFromPrincipalOrSession(Principal principal) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            return user.getEmail();
        }
        return principal.getName();
    }


    @GetMapping(value = "/charge")
    public String pointMainCharge(Principal principal, Model model){
        String getName=getEmailFromPrincipalOrSession(principal);
        if (getName == null){
            return "member/login";
        }

        return  "point/Charge";
    }


    @PostMapping(value = "/point/payment/{point}/{price}")
    public @ResponseBody ResponseEntity pointPayment(@PathVariable int point,
                                                     @PathVariable int price,
                                                     Principal principal){
        String id=getEmailFromPrincipalOrSession(principal);

        Member member = memberService.SearchIdtoName(id);

        Point p = pointService.paymentPoint(member, price, point);

        RequestPayDto requestDto = paymentService.findRequestDto(p.getId());

        return new ResponseEntity<RequestPayDto>(requestDto, HttpStatus.OK);

    }

    @PostMapping(value = "/payment")
    public  @ResponseBody ResponseEntity<IamportResponse<Payment>> validationPayment(@RequestBody PaymentCallbackRequest request) {

        IamportResponse<Payment> iamportResponse = paymentService.paymentByCallback(request);
        return new ResponseEntity<>(iamportResponse, HttpStatus.OK);
    }


    @GetMapping(value = {"/success-payment/{orderUid}", "/ch"})
    public String successPaymentPage(@PathVariable Optional<String> orderUid) {
        return "redirect:/";
    }

    @GetMapping("/fail-payment/{orderUid}")
    public String failPaymentPage(@PathVariable String orderUid) {
        //파라미터 값이 없으면 null값으로
        paymentService.canclePayment(orderUid);
        pointService.cancelOrder(orderUid);

        return "point/fail-payment";
    }


    @GetMapping(value = "/point/add")
    public String poindAdd (Model model, Principal principal){
        String Login=getEmailFromPrincipalOrSession(principal);
        if (Login == null){
            return "member/login";
        }

        Member member = memberService.memberLoginId(Login);

        Checking checking = checkingService.searchfromMember(member.getId());

        if (checking != null){
            model.addAttribute("errorMessage", "오늘의 출석 체크는 완료 되었습니다!.");
        }

        model.addAttribute("member", member);
        model.addAttribute("checking", checking);


        return "point/Add";
    }



    @PostMapping(value = "/point/add/check/{Id}/{point}")
    public  @ResponseBody ResponseEntity saveChecking(@PathVariable Long Id,
                                                     @PathVariable int point ){
        Member member = memberService.searchMemberId(Id);

        Checking checking = checkingService.saveChecking(member, point);
        return  new ResponseEntity(checking.getId(), HttpStatus.OK);
    }


}

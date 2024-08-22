package com.book.write.controller;

import com.book.write.constant.Order;
import com.book.write.dto.PaymentCallbackRequest;
import com.book.write.dto.RequestPayDto;
import com.book.write.dto.SessionUser;
import com.book.write.entity.Member;
import com.book.write.entity.Point;
import com.book.write.service.MemberService;
import com.book.write.service.PaymentService;
import com.book.write.service.PaymentServiceImpl;
import com.book.write.service.PointService;
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

@Controller
@RequiredArgsConstructor
public class PointController {
    private final MemberService memberService;
    private  final PointService pointService;
    private  final PaymentService paymentService;


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


    @GetMapping("/success-payment/{orderUid}")
    public String successPaymentPage(@PathVariable String orderUid) {
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
    public String poindAdd (){

        return "point/Add";
    }


}

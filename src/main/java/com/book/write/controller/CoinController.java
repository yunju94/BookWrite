package com.book.write.controller;

import com.book.write.dto.SessionUser;
import com.book.write.entity.Member;
import com.book.write.entity.Point;
import com.book.write.service.MemberService;
import com.book.write.service.PointService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@Transactional
@RequiredArgsConstructor
public class CoinController {
    private  final PointService pointService;
    private  final MemberService memberService;


    private final HttpSession httpSession;

    private String getEmailFromPrincipalOrSession(Principal principal) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            return user.getEmail();
        }
        return principal.getName();
    }
    @PostMapping(value = "/coin/add/{coincategory}/{coin}/{coinMoney}")
    public @ResponseBody ResponseEntity coinadd(@PathVariable String coincategory,
                                                @PathVariable double coin,
                                                @PathVariable int coinMoney,
                                                Principal principal){

        String getName=getEmailFromPrincipalOrSession(principal);

        double KDR_coin = 0;
        double YES_coin = 0;

        if (coincategory.equals("KDR")){
            KDR_coin = coin;
        }
        if (coincategory.equals("YES")){
            YES_coin = coin;
        }
        Member member = memberService.memberLoginId(getName);


        pointService.coinChanger(member, KDR_coin, YES_coin, coinMoney);
        return  new ResponseEntity(member.getId(), HttpStatus.OK);
    }



}

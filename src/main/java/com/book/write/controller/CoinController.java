package com.book.write.controller;

import com.book.write.entity.Member;
import com.book.write.entity.Point;
import com.book.write.service.MemberService;
import com.book.write.service.PointService;
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



    @PostMapping(value = "/coin/add/{coincategory}/{coin}/{coinMoney}")
    public @ResponseBody ResponseEntity coinadd(@PathVariable String coincategory,
                                                @PathVariable double coin,
                                                @PathVariable int coinMoney,
                                                Principal principal){
        double KDR_coin = 0;
        double YES_coin = 0;

        if (coincategory.equals("KDR")){
            KDR_coin = coin;
        }
        if (coincategory.equals("YES")){
            YES_coin = coin;
        }
        Member member = memberService.memberLoginId(principal.getName());


        pointService.coinChanger(member, KDR_coin, YES_coin, coinMoney);
        return  new ResponseEntity(member.getId(), HttpStatus.OK);
    }



}

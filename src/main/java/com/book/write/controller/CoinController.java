package com.book.write.controller;

import com.book.write.entity.Member;
import com.book.write.service.MemberService;
import com.book.write.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@Transactional
@RequiredArgsConstructor
public class CoinController {
    private  final PointService pointService;
    private  final MemberService memberService;

    @PostMapping(value = "/coin/KDRadd/{coin}/{coinMoney}")
    public @ResponseBody ResponseEntity KDRcoinadd(@PathVariable int coin,
                                                @PathVariable int coinMoney,
                                                Principal principal){
        Member member = memberService.memberLoginId(principal.getName());
        pointService.KDR_coinChange(member, coin, coinMoney);
        return  new ResponseEntity(member.getId(), HttpStatus.OK);
    }

    @PostMapping(value = "/coin/YESadd/{coin}/{coinMoney}")
    public @ResponseBody ResponseEntity YEScoinadd(@PathVariable int coin,
                                                @PathVariable int coinMoney,
                                                Principal principal){
        Member member = memberService.memberLoginId(principal.getName());
        pointService.YES_coinChange(member, coin, coinMoney);
        return  new ResponseEntity(member.getId(), HttpStatus.OK);
    }



}

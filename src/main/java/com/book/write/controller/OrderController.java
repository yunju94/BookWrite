package com.book.write.controller;

import com.book.write.dto.OrderCoinDto;
import com.book.write.dto.SessionUser;
import com.book.write.entity.Coin;
import com.book.write.entity.Member;
import com.book.write.entity.Point;
import com.book.write.entity.Purchase;
import com.book.write.service.CoinService;
import com.book.write.service.MemberService;
import com.book.write.service.PointService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@Transactional
@RequiredArgsConstructor
public class OrderController {

    private  final MemberService memberService;

    private  final PointService pointService;
    private  final CoinService coinService;

    private final HttpSession httpSession;
    private String getEmailFromPrincipalOrSession(Principal principal) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            return user.getEmail();
        }
        return principal.getName();
    }


    @GetMapping(value = {"/order/list", "/order/list/page"})
    public String orderList(Principal principal, Model model,
                            @PathVariable("page") Optional<Integer> page,
                            OrderCoinDto orderCoinDto,  BindingResult result){
        if (result.hasErrors()) {
            // 오류가 있는 경우 적절한 오류 처리
            System.out.println(result);
            model.addAttribute("errorMessage", "코인 구매 내역이 없습니다.");
            return "redirect:/"; // 오류가 있는 경우 폼 페이지로 돌아감
        }

       String loginId= getEmailFromPrincipalOrSession(principal);
        Member member = memberService.memberLoginId(loginId);
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<OrderCoinDto> coins = coinService.PageCoin(orderCoinDto, member.getId(), pageable);

        System.out.println("coins=====>>>>"+ coins);
        model.addAttribute("coins", coins);
        model.addAttribute("orderCoinDto", orderCoinDto);
        model.addAttribute("pageable", pageable);
        model.addAttribute("maxPage", 10);


        return "order/List";
    }

}

package com.book.write.controller;

import com.book.write.dto.WriteDetailDto;
import com.book.write.entity.Member;
import com.book.write.entity.Write;
import com.book.write.entity.WriteInfo;
import com.book.write.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class WriteDetailController {
    private  final WriteInfoService writeInfoService;
    private  final WriteDetailService writeDetailService;
    private  final PurchanseService purchanseService;
    private  final CoinService coinService;
    private  final MemberService memberService;

    @GetMapping(value = "/detail/new/{id}")
    public String WriteDetailNew(@PathVariable Long id, Model model){
        WriteInfo writeInfo = writeInfoService.SearchWriteInfoId(id);
        WriteDetailDto writeDetailDto = new WriteDetailDto();
        writeDetailDto.setWriteInfo(writeInfo);

        model.addAttribute("writeInfo", writeInfo);
        model.addAttribute("WriteDetailDto", writeDetailDto);
        return "writeDetail/new";
    }

    @PostMapping(value = "/novel/new/add/{id}")
    public  String WriteDetailNewAdd(@Valid WriteDetailDto writeDetailDto ,
                                     @PathVariable Long id){
        writeDetailService.saveWrite(writeDetailDto);

        WriteInfo writeInfo = writeInfoService.SearchWriteInfoId(id);
        writeInfoService.upupdateWriteInfo(writeInfo);

        return "redirect:/";
    }

    @GetMapping(value = "/detail/novel/{id}")
    public  String WriteReadNovel(@PathVariable Long id, Model model){
        Write write = writeDetailService.searchDetailId(id);

        WriteInfo writeInfo = writeInfoService.SearchWriteInfoId(write.getWriteInfo().getId());

        model.addAttribute("writeInfo", writeInfo);
        model.addAttribute("write", write);

        return "writeDetail/novelRead";
    }


    @GetMapping(value = "/Novel/{coin}/Pur/{id}")
    public String PurchanNovel(@PathVariable Long id,
                               @PathVariable String coin,
                               Principal principal, Model model){

        Write write = writeDetailService.searchDetailId(id);
        WriteInfo writeInfo = writeInfoService.SearchWriteInfoId(write.getWriteInfo().getId());
        double KDR_coin=0;
        double YES_coin = 0;
        if (coin.equals("KDR")){
            KDR_coin= 0.7;
        }
        if (coin.equals("YES")){
            YES_coin= 0.5;
        }
        purchanseService.savePur(write, KDR_coin, YES_coin);

        Member member = memberService.memberLoginId(principal.getName());
        coinService.minusCoin(member, KDR_coin, YES_coin);

        model.addAttribute("writeInfo", writeInfo);
        model.addAttribute("write", write);
        return "writeDetail/novelRead";





    }

}

package com.book.write.controller;

import com.book.write.dto.WriteDetailDto;
import com.book.write.entity.*;
import com.book.write.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class WriteDetailController {
    private  final WriteInfoService writeInfoService;
    private  final WriteDetailService writeDetailService;
    private  final PurchanseService purchanseService;
    private  final RentalService rentalService;
    private  final CoinService coinService;
    private  final MemberService memberService;
    private  final  PointService pointService;


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
        System.out.println(writeDetailDto);
        System.out.println("111111111111111111111111111111111111");
        writeDetailService.saveWrite(writeDetailDto);
        System.out.println("222222222222222222222222222222222222222222");
        WriteInfo writeInfo = writeInfoService.SearchWriteInfoId(id);
        System.out.println("33333333333333333333333333333333333");
        writeInfo.setUpdateTime(LocalDateTime.now());


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

    @PostMapping(value="/Novel/detail/{id}")
    public  @ResponseBody ResponseEntity SearchNovelOrder (@PathVariable Long id){
        Write write = writeDetailService.searchDetailId(id);
        if (writeDetailService.searchPur(write)){
            return  new ResponseEntity( write.getId(), HttpStatus.OK );
        }else {
            return  new ResponseEntity( write.getId(), HttpStatus.BAD_REQUEST );
        }


    }



    @PostMapping(value = "/Novel/{coin}/Ren/{id}")
    public @ResponseBody ResponseEntity RentalNovel(@PathVariable String id,
                                                     @PathVariable String coin,
                                                     Principal principal, Model model){

        //문자열로 받은 id를 long으로 변경
        Long writeId= Long.valueOf(id);
        Write write = writeDetailService.searchDetailId(writeId);

        //기본 변수 설정
        double KDR_coin=0;
        double YES_coin = 0;
        //키다리와 예스24일때 결과 값 저장
        if (coin.equals("KDR")){
            KDR_coin= 0.3;
        }
        if (coin.equals("YES")){
            YES_coin= 0.1;
        }

        // 멤버값을 불러 구매이력에 저장
        Member member = memberService.memberLoginId(principal.getName());

        coinService.minusCoin(member, KDR_coin, YES_coin);
        rentalService.save(write);

        //책 회차값 반환
        return new ResponseEntity(write.getId(), HttpStatus.OK);

    }

    @PostMapping(value = "/Novel/{coin}/Pur/{id}")
    public @ResponseBody ResponseEntity PurchanNovel(@PathVariable String id,
                                                     @PathVariable String coin,
                                                     Principal principal, Model model){

    //문자열로 받은 id를 long으로 변경
        Long writeId= Long.valueOf(id);
        Write write = writeDetailService.searchDetailId(writeId);

       //기본 변수 설정
        double KDR_coin=0;
        double YES_coin = 0;
        //키다리와 예스24일때 결과 값 저장
        if (coin.equals("KDR")){
            KDR_coin= 0.7;
        }
        if (coin.equals("YES")){
            YES_coin= 0.5;
        }

        // 멤버값을 불러 구매이력에 저장
        Member member = memberService.memberLoginId(principal.getName());

        coinService.minusCoin(member, KDR_coin, YES_coin);

    //책 회차값 반환
        return new ResponseEntity(write.getId(), HttpStatus.OK);

    }

}

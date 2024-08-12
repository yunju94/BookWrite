package com.book.write.controller;

import com.book.write.dto.WriteDetailDto;
import com.book.write.entity.*;
import com.book.write.repository.WriteDetailRepository;
import com.book.write.repository.WriteInfoRepository;
import com.book.write.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private  final WriteDetailRepository writeDetailRepository;
    private  final WriteInfoRepository writeInfoRepository;

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

        WriteDetail writeDetail= writeDetailService.saveWrite(writeDetailDto);
        WriteInfo writeInfo = writeInfoService.SearchWriteInfoId(id);
        writeInfoRepository.save(WriteInfo.updateWriteDetail(writeInfo, writeDetail));


        return "redirect:/";
    }

    @GetMapping(value = "/detail/novel/{id}")
    public  String WriteReadNovel(@PathVariable Long id, Model model){

        WriteDetail writeDetail = writeDetailService.searchDetailId(id);


        WriteInfo writeInfo = writeInfoService.SearchWriteInfoId(writeDetail.getWriteInfo().getId());

        model.addAttribute("writeInfo", writeInfo);
        model.addAttribute("writeDetail", writeDetail);

        return "writeDetail/novelRead";
    }

    @PostMapping(value="/Novel/detail/{id}")
    public  @ResponseBody ResponseEntity SearchNovelOrder (@PathVariable Long id, Principal principal){

        WriteDetail writeDetail = writeDetailService.searchDetailId(id);
        Member member = memberService.memberLoginId(principal.getName());
        if (member.getId() == writeDetail.getWriteInfo().getMember().getId()){
            return  new ResponseEntity( writeDetail.getId(), HttpStatus.OK );
        }
        if (writeDetailService.searchPur(writeDetail)){
            return  new ResponseEntity( writeDetail.getId(), HttpStatus.OK );
        }else {
            return  new ResponseEntity( "구매 정보가 없습니다.", HttpStatus.BAD_REQUEST );
        }


    }

    @GetMapping(value = "/popup/pur")
    public String popupCoinUse(){
        return "writeDetail/popup";
    }



    @PostMapping(value = "/Novel/{coin}/Ren/{id}")
    public @ResponseBody ResponseEntity RentalNovel(@PathVariable String id,
                                                     @PathVariable String coin,
                                                     Principal principal, Model model){

        //문자열로 받은 id를 long으로 변경
        Long writeId= Long.valueOf(id);
        WriteDetail writeDetail = writeDetailService.searchDetailId(writeId);

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

       // coinService.minusCoin(member, KDR_coin, YES_coin, );
       // rentalService.save(writeDetail);

        //책 회차값 반환
        return new ResponseEntity(writeDetail.getId(), HttpStatus.OK);

    }

    @PostMapping(value = "/Novel/{coin}/Pur/{id}")//코인으로 구매
    public @ResponseBody ResponseEntity PurchanNovel(@PathVariable Long id,
                                                     @PathVariable String coin,
                                                     Principal principal){

        System.out.println("detailId: "+ id + "========>jfkjfdalkjmafd.;1231231");


        WriteDetail writeDetail = writeDetailService.searchDetailId(id);

        //작가 찾기
        WriteInfo writeInfo = writeInfoService.searchDetailId(writeDetail.getWriteInfo().getId());
        System.out.println("writeInfo: "+writeInfo);

        Member Author = memberService.SearchNickName(writeInfo.getMember().getNickname());


       //기본 변수 설정
        double KDR_coin=0;
        double YES_coin = 0;
        // 멤버값을 불러 구매이력에 저장 및 확인
        Member member = memberService.memberLoginId(principal.getName());
        //멤버의 코인 값들을 전부 불러옴.
        List<Coin> coins = coinService.SearchIdtocoin(member.getId());
        //코인 값을 더해서 가격에 비해 낮으면 coin 구매소로 반환

        double totalCoin = 0;
        //키다리와 예스24일때 전체 결과 값 저장
        if (coin.equals("KDR")){
            KDR_coin= 0.7;
            for (Coin coinList: coins){
                totalCoin += coinList.getKDR_coin();//전체 값 더함
            }
            if (totalCoin<KDR_coin){//사려는 금액보다 적으면
                return new ResponseEntity<>("금액이 부족합니다.", HttpStatus.BAD_REQUEST);
            }

        }
        if (coin.equals("YES")){
            YES_coin= 0.5;
            for (Coin coinList: coins){
                totalCoin += coinList.getYES_coin();
            }
            if (totalCoin<YES_coin){
                return new ResponseEntity<>("금액이 부족합니다.", HttpStatus.BAD_REQUEST);
            }
        }
//금액이 사려는 코인보다 많을 경우 코인 내고 구매
        coinService.minusCoin(member, KDR_coin, YES_coin, Author);
      // 구매 이력에 저장
        purchanseService.savePur(writeDetail);
    //책 회차값 반환
        return new ResponseEntity(writeDetail.getId(), HttpStatus.OK);

    }

    @PostMapping(value = "/Novel/{count}/{id}")//들어가면 바로 본 횟수 증가 ajax
    public  @ResponseBody ResponseEntity viewplus (@PathVariable Long id,
                                                   @PathVariable String count){

        WriteDetail writeDetail = writeDetailService.searchDetailId(id);
        if (count.equals("viewCount")){
            writeDetailRepository.save(WriteDetail.updateViewCount(writeDetail));
        }

        WriteInfo writeInfo = writeInfoService.searchDetailId(writeDetail.getWriteInfo().getId());

        writeInfo.setTotalView(writeInfo.getTotalView()+1);
        writeInfoRepository.save(writeInfo);

        return  new ResponseEntity(writeDetail.getId(), HttpStatus.OK);

    }


    @PostMapping(value =  "/novel/heartCount/{str}/{id}")//추천 클릭 시 ajax
    public @ResponseBody ResponseEntity heartCount (@PathVariable String str,
                                                    @PathVariable Long id){

        WriteDetail writeDetail = writeDetailService.searchDetailId(id);
         WriteInfo writeInfo = writeInfoService.searchDetailId(writeDetail.getWriteInfo().getId());

        if (str.equals("plus")){
            writeDetail.setHeart(writeDetail.getHeart()+1);
            writeInfo.setTotalHeart(writeInfo.getTotalHeart()+1);
        }

        if (str.equals("minus")){
            writeDetail.setHeart(writeDetail.getHeart()-1);
            writeInfo.setTotalHeart(writeInfo.getTotalHeart()-1);
        }
        writeDetailRepository.save(writeDetail);
        writeInfoRepository.save(writeInfo);

        return  new ResponseEntity(writeDetail.getHeart(), HttpStatus.OK);
    }

}

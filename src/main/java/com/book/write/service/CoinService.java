package com.book.write.service;

import com.book.write.dto.OrderCoinDto;
import com.book.write.entity.*;
import com.book.write.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CoinService {
   private  final CoinRepository coinRepository;
   private  final PointService pointService;
   public  void  minusCoin(Member member, double KDR_coin, double YES_coin, Member Author, WriteDetail writeDetail){
       Point point = pointService.saveCoin(member);
        Coin coin = Coin.createCoin(member, KDR_coin, YES_coin, point, writeDetail);
       coinRepository.save(coin);


        Point AutoPoint = pointService.saveCoinAuthor(Author);

       Coin coinAuthor = Coin.createAuthorCoin(Author, KDR_coin, YES_coin, AutoPoint);



        coinRepository.save(coinAuthor);


    }

    public List<Coin> SearchIdtocoin(Long memberId){
       return coinRepository.findByMemberId(memberId);
    }

    public Page<OrderCoinDto> PageCoin(OrderCoinDto orderCoinDto,Long memberId,  Pageable pageable){
       return coinRepository.PageCoin(orderCoinDto,memberId,  pageable);
    }
}

package com.book.write.service;

import com.book.write.entity.Coin;
import com.book.write.entity.Member;
import com.book.write.entity.Point;
import com.book.write.entity.Purchase;
import com.book.write.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CoinService {
   private  final CoinRepository coinRepository;
   private  final PointService pointService;
   public  void  minusCoin(Member member, double KDR_coin, double YES_coin){
       Point point = pointService.saveCoin(member);
        Coin coin = Coin.createCoin(member, KDR_coin, YES_coin, point);

        coinRepository.save(coin);


    }

    public List<Coin> SearchIdtocoin(Long memberId){
       return coinRepository.findByMemberId(memberId);
    }
}

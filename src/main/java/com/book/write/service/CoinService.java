package com.book.write.service;

import com.book.write.entity.Coin;
import com.book.write.entity.Member;
import com.book.write.repository.CoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CoinService {
   private  final CoinRepository coinRepository;
    public  void  minusCoin(Member member, double KDR_coin, double YES_coin){
        Coin coin = Coin.createCoin(member, KDR_coin, YES_coin);
        coinRepository.save(coin);
    }
}

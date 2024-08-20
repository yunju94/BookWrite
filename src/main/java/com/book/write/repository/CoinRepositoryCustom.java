package com.book.write.repository;

import com.book.write.dto.OrderCoinDto;
import com.book.write.entity.Coin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CoinRepositoryCustom {

    Page<OrderCoinDto> PageCoin(OrderCoinDto orderCoinDto,Long memberId, Pageable pageable );
}

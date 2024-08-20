package com.book.write.repository;

import com.book.write.constant.PR;
import com.book.write.dto.NovelListDto;
import com.book.write.dto.OrderCoinDto;
import com.book.write.dto.QNovelListDto;
import com.book.write.dto.QOrderCoinDto;
import com.book.write.entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CoinRepositoryCustomImpl implements  CoinRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public CoinRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<OrderCoinDto> PageCoin(OrderCoinDto orderCoinDto, Long memberId, Pageable pageable) {
        QMember member = QMember.member;
        QCoin coin = QCoin.coin;
        QPoint point = QPoint.point1;
        QWriteInfo writeInfo = QWriteInfo.writeInfo;
        QWriteDetail writeDetail = QWriteDetail.writeDetail;

        // QueryDSL을 사용하여 쿼리 작성
        QueryResults<OrderCoinDto> results = queryFactory.select(new QOrderCoinDto(
                        member.id,
                        coin.KDR_coin,
                        coin.YES_coin,
                        writeInfo.title,
                        writeDetail.miniTitle,
                        point.regDate))
                .from(member)
                .leftJoin(coin).on(member.id.eq(coin.member.id)) // left join 추가
                .leftJoin(point).on(coin.point.id.eq(point.id)) // left join 추가
                .leftJoin(writeDetail).on(coin.writeDetail.id.eq(writeDetail.id)) // left join 추가
                .leftJoin(writeInfo).on(writeDetail.writeInfo.id.eq(writeInfo.id)) // left join 추가

                .where(member.id.eq(memberId ))
                .orderBy(point.regDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<OrderCoinDto> content = results.getResults();

        // 전체 레코드 수를 가져옵니다.
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}

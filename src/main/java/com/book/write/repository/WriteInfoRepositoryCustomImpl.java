package com.book.write.repository;

import com.book.write.constant.Category;
import com.book.write.dto.NovelListDto;
import com.book.write.dto.QNovelListDto;
import com.book.write.dto.WriteInfoDto;
import com.book.write.dto.WriteInfoSerchDto;
import com.book.write.entity.QWriteImg;
import com.book.write.entity.QWriteInfo;
import com.book.write.entity.WriteInfo;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WriteInfoRepositoryCustomImpl implements  WriteInfoRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public WriteInfoRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchBymember(Long memberId) {
        return QWriteInfo.writeInfo.member.id.eq(memberId);
    }

    private BooleanExpression searchByLike(String str) {
        return QWriteInfo.writeInfo.title.like("%" + str + "%");
    }

    private BooleanExpression searchByLikewritor(String str) {
        return QWriteInfo.writeInfo.member.nickname.like("%" + str + "%");
    }


    private OrderSpecifier<?> OrderBySearch( Optional<String> orderByFront, Optional<String> orderByBack){
        String orderByFrontValue = orderByFront.orElse("update");
        String orderByBackValue = orderByBack.orElse("desc");

        // 정렬 기준 선택
        if ("update".equals(orderByFrontValue)) {
            return "asc".equals(orderByBackValue) ? QWriteInfo.writeInfo.updateTime.asc() : QWriteInfo.writeInfo.updateTime.desc();
        } else if ("view".equals(orderByFrontValue)) {
            return "asc".equals(orderByBackValue) ? QWriteInfo.writeInfo.totalView.asc() : QWriteInfo.writeInfo.totalView.desc();
        } else if ("heart".equals(orderByFrontValue)) {
            return "asc".equals(orderByBackValue) ? QWriteInfo.writeInfo.totalHeart.asc() : QWriteInfo.writeInfo.totalHeart.desc();
        } else {
            // 기본 정렬 (updateTime.desc()) 설정
            return QWriteInfo.writeInfo.updateTime.desc();
        }
    }


    @Override
    public Page<WriteInfo> getMyWritePage(WriteInfoSerchDto writeInfoSerchDto, Long Id, Pageable pageable) {
        QueryResults<WriteInfo> results = queryFactory.selectFrom(QWriteInfo.writeInfo)
                .where(searchBymember(Id),
                        searchByLike(writeInfoSerchDto.getSearchQuery()),
                        searchByLikewritor(writeInfoSerchDto.getSearchQuery()))
                .orderBy(QWriteInfo.writeInfo.updateTime.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();

        List<WriteInfo> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<NovelListDto> getCategoryPage(WriteInfoDto writeInfoDto, Pageable pageable,
                                              Optional<String> orderByFront, Optional<String> orderByBack) {
        QWriteInfo writeInfo = QWriteInfo.writeInfo;
        QWriteImg writeImg = QWriteImg.writeImg;

        QueryResults<NovelListDto> results = queryFactory.select(new QNovelListDto(
                        writeInfo.member,
                        writeInfo.id,
                        writeInfo.title,
                        writeInfo.category,
                        writeInfo.totalHeart,
                        writeInfo.totalView,
                        writeInfo.writeImg
                ))
                .from(writeInfo)  // 주 테이블
                .join(writeInfo.writeImg)  // 조인 조건 추가
                .where(writeInfo.category.eq(writeInfoDto.getCategory()))
                .where(searchByLike(writeInfoDto.getSearch()))
                .orderBy(OrderBySearch(orderByFront, orderByBack))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults(); // `fetch` 메서드를 사용하여 리스트를 가져옵니다.
        List<NovelListDto> content = results.getResults();
        // 전체 레코드 수를 가져옵니다.
        long total = results.getTotal();  // 전체 레코드 수를 가져옵니다.

        return new PageImpl<>(content, pageable, total);
    }

    @Override//베스트 모음 쿼리문
    public Page<NovelListDto> getBestPage(WriteInfoDto writeInfoDto, Pageable pageable) {
        QWriteInfo writeInfo = QWriteInfo.writeInfo;
        QWriteImg writeImg = QWriteImg.writeImg;

        QueryResults<NovelListDto> results = queryFactory.select(new QNovelListDto(
                        writeInfo.member,
                        writeInfo.id,
                        writeInfo.title,
                        writeInfo.category,
                        writeInfo.totalHeart,
                        writeInfo.totalView,
                        writeInfo.writeImg
                ))
                .from(writeInfo)  // 주 테이블
                .join(writeInfo.writeImg)  // 조인 조건 추가
                .orderBy(writeInfo.totalHeart.desc())
                .offset(pageable.getOffset())
                .limit(Math.min(pageable.getPageSize(), 100))  // 페이지 사이즈와 100 중 작은 값으로 제한
                .fetchResults(); // `fetch` 메서드를 사용하여 리스트를 가져옵니다.
        List<NovelListDto> content = results.getResults();
        // 전체 레코드 수를 가져옵니다.
        long total = results.getTotal();  // 전체 레코드 수를 가져옵니다.

        return new PageImpl<>(content, pageable, total);
    }




}

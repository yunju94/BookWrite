package com.book.write.repository;

import com.book.write.constant.Category;
import com.book.write.dto.WriteInfoSerchDto;
import com.book.write.entity.QWriteInfo;
import com.book.write.entity.WriteInfo;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

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



    @Override
    public Page<WriteInfo> getMyWritePage(WriteInfoSerchDto writeInfoSerchDto, Long Id, Pageable pageable) {
        QueryResults<WriteInfo> results = queryFactory.selectFrom(QWriteInfo.writeInfo)
                .where(searchBymember(Id),
                        searchByLike(writeInfoSerchDto.getSearchQuery()),
                        searchByLikewritor(writeInfoSerchDto.getSearchQuery()))
                .orderBy(QWriteInfo.writeInfo.id.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();

        List<WriteInfo> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}

package com.book.write.repository;

import com.book.write.dto.WriteInfoDto;
import com.book.write.entity.WriteInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface WriteInfoRepository extends JpaRepository<WriteInfo, Long> ,
        QuerydslPredicateExecutor<WriteInfo>, WriteInfoRepositoryCustom{

    List<WriteInfo> findByMemberId(Long memberId);



}

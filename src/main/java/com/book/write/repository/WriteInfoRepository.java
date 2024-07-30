package com.book.write.repository;

import com.book.write.entity.WriteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriteInfoRepository extends JpaRepository<WriteInfo, Long> {

    WriteInfo findByMemberId(Long memberId);
}

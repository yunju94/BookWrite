package com.book.write.repository;

import com.book.write.entity.Checking;
import lombok.Lombok;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CheckingRepository extends JpaRepository<Checking, Long> {

    Checking findByMemberIdAndRegDate(Long memberId, LocalDate RegDate);
}

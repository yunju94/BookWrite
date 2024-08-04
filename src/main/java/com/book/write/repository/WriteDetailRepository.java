package com.book.write.repository;

import com.book.write.entity.Write;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriteDetailRepository extends JpaRepository<Write, Long> {
}

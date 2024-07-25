package com.book.write.repository;

import com.book.write.entity.Fantasy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FantasyRepository extends JpaRepository<Fantasy, Long> {
}

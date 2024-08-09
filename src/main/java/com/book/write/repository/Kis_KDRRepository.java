package com.book.write.repository;

import com.book.write.entity.Kis_KDR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface Kis_KDRRepository extends JpaRepository<Kis_KDR, Long> {
    List<Kis_KDR> findByUpDate(LocalDate date);
}

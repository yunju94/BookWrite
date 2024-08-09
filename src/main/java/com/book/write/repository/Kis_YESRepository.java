package com.book.write.repository;

import com.book.write.entity.Kis_YES;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface Kis_YESRepository extends JpaRepository<Kis_YES, Long> {

    List<Kis_YES> findByUpDate(LocalDate date);
}

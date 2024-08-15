package com.book.write.repository;

import com.book.write.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    Rental findByWriteDetailId(Long writeDetailId);

}

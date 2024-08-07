package com.book.write.repository;

import com.book.write.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchanseRepository extends JpaRepository<Purchase, Long> {

    Purchase findByWriteId(Long writeDetailId);
}

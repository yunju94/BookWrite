package com.book.write.repository;

import com.book.write.entity.WriteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WriteDetailRepository extends JpaRepository<WriteDetail, Long> {


    @Query("SELECT o FROM WriteDetail o WHERE o.writeInfo.id = :writeInfoId AND o.reserveTime <= :now ORDER BY o.reserveTime DESC")
    List<WriteDetail> findByWriteInfoId(@Param("writeInfoId") Long writeInfoId, @Param("now") LocalDateTime now);


}

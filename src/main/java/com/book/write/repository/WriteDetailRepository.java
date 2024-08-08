package com.book.write.repository;

import com.book.write.entity.WriteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WriteDetailRepository extends JpaRepository<WriteDetail, Long> {

    @Query("SELECT o FROM WriteDetail o WHERE o.writeInfo.Id = :writeInfoId ORDER BY o.Id DESC")
    List<WriteDetail> findByWriteInfoId(@Param("writeInfoId") Long writeInfoId);


}

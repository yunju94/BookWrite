package com.book.write.repository;

import com.book.write.entity.Write;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WriteDetailRepository extends JpaRepository<Write, Long> {

    @Query("SELECT o FROM Write o WHERE o.writeInfo.Id = :writeInfoId ORDER BY o.Id DESC")
    List<Write> findByWriteInfoId(@Param("writeInfoId") Long writeInfoId);


}

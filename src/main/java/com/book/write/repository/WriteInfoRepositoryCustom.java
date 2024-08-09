package com.book.write.repository;

import com.book.write.dto.NovelListDto;
import com.book.write.dto.WriteInfoDto;
import com.book.write.dto.WriteInfoSerchDto;
import com.book.write.entity.WriteInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface WriteInfoRepositoryCustom {


   Page<WriteInfo>getMyWritePage(WriteInfoSerchDto writeInfoSerchDto, Long memberId, Pageable pageable);

   Page<NovelListDto> getCategoryPage(WriteInfoDto writeInfoDto, Pageable pageable);
   Page<NovelListDto> getBestPage(WriteInfoDto writeInfoDto, Pageable pageable);

}

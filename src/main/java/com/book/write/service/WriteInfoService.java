package com.book.write.service;

import com.book.write.constant.Category;
import com.book.write.dto.WriteInfoDto;
import com.book.write.entity.WriteInfo;
import com.book.write.repository.FantasyRepository;
import com.book.write.repository.WriteInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteInfoService {
    private  final WriteInfoRepository writeInfoRepository;
    private final FantasyRepository fantasyRepository;


    public WriteInfo SearchMemberId(int Id){
       return writeInfoRepository.findByMemberId(Id);
    }

    public  void save(WriteInfoDto writeInfoDto){
        WriteInfo writeInfo = WriteInfo.createDto(writeInfoDto);
        writeInfoRepository.save(writeInfo);
        if (writeInfoDto.getCategory().equals(Category.Fantasy)){

        }

    }
}

package com.book.write.service;

import com.book.write.constant.Category;
import com.book.write.dto.WriteInfoDto;
import com.book.write.entity.Etc;
import com.book.write.entity.Fantasy;
import com.book.write.entity.RomanceFantasy;
import com.book.write.entity.WriteInfo;
import com.book.write.repository.EtcRepository;
import com.book.write.repository.FantasyRepository;
import com.book.write.repository.RFRepository;
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
    private  final RFRepository rfRepository;
    private  final EtcRepository etcRepository;


    public WriteInfo SearchMemberId(int Id){
       return writeInfoRepository.findByMemberId(Id);
    }

    public  void save(WriteInfoDto writeInfoDto){
        WriteInfo writeInfo = WriteInfo.createDto(writeInfoDto);
        writeInfoRepository.save(writeInfo);

//        if (writeInfoDto.getCategory().equals(Category.Fantasy)){
//            fantasyRepository.save(new Fantasy());
//        }
//        if (writeInfoDto.getCategory().equals(Category.RomanceFantasy)){
//            rfRepository.save(new RomanceFantasy());
//        }
//
//        if (writeInfoDto.getCategory().equals(Category.etc)){
//            etcRepository.save(new Etc());
//        }

    }
}

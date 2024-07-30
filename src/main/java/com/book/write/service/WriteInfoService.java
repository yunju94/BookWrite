package com.book.write.service;

import com.book.write.dto.WriteInfoDto;
import com.book.write.entity.WriteImg;
import com.book.write.entity.WriteInfo;
import com.book.write.repository.WriteImgRepository;
import com.book.write.repository.WriteInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteInfoService {
    private  final WriteInfoRepository writeInfoRepository;
    private  final WriteImgService writeImgService;


    public WriteInfo SearchMemberId(Long Id){
       return writeInfoRepository.findByMemberId(Id);
    }

    public  void save(WriteInfoDto writeInfoDto,  MultipartFile imgFile) throws Exception {
        WriteImg writeImg=writeImgService.createImg(imgFile);

        writeInfoDto.setWriteImg(writeImg);

        WriteInfo writeInfo = WriteInfo.createDto(writeInfoDto);
        writeInfoRepository.save(writeInfo);



    }
}

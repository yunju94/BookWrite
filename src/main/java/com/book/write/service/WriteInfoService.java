package com.book.write.service;

import com.book.write.dto.WriteInfoDto;
import com.book.write.dto.WriteInfoSerchDto;
import com.book.write.entity.WriteImg;
import com.book.write.entity.WriteInfo;
import com.book.write.repository.WriteImgRepository;
import com.book.write.repository.WriteInfoRepository;
import com.book.write.repository.WriteInfoRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteInfoService {
    private  final WriteInfoRepository writeInfoRepository;
    private  final WriteImgService writeImgService;



    public List<WriteInfo> SearchMemberId(Long Id){
       return writeInfoRepository.findByMemberId(Id);
    }

    public  void save(WriteInfoDto writeInfoDto,  MultipartFile imgFile) throws Exception {
        WriteImg writeImg=writeImgService.createImg(imgFile);

        writeInfoDto.setWriteImg(writeImg);

        WriteInfo writeInfo = WriteInfo.createDto(writeInfoDto);
        writeInfoRepository.save(writeInfo);
    }

    public List<WriteInfo> AllSearch(){
        return writeInfoRepository.findAll();
    }

    public Page<WriteInfo> getMyWritePage(WriteInfoSerchDto writeInfoSerchDto, Long memberId, Pageable pageable){
        return writeInfoRepository.getMyWritePage(writeInfoSerchDto, memberId, pageable);
    }

    public WriteInfo SearchWriteInfoId(Long id){
        return  writeInfoRepository.findById(id).orElseThrow();
    }

}

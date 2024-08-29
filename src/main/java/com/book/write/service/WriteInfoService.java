package com.book.write.service;

import com.book.write.dto.NovelListDto;
import com.book.write.dto.WriteDetailDto;
import com.book.write.dto.WriteInfoDto;
import com.book.write.dto.WriteInfoSerchDto;
import com.book.write.entity.WriteDetail;
import com.book.write.entity.WriteImg;
import com.book.write.entity.WriteInfo;
import com.book.write.repository.WriteDetailRepository;
import com.book.write.repository.WriteImgRepository;
import com.book.write.repository.WriteInfoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteInfoService {
    private  final WriteInfoRepository writeInfoRepository;
    private  final WriteImgService writeImgService;
    private  final  WriteImgRepository writeImgRepository;
    private  final WriteDetailRepository writeDetailRepository;



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

    public void updateWriteInfoFromDto(WriteInfoDto writeInfoDto, MultipartFile imgFile) throws Exception {
        WriteInfo writeInfo = writeInfoRepository.findById(writeInfoDto.getId()).orElseThrow();
        writeInfo.setTitle(writeInfoDto.getTitle());
        writeInfo.setDetail(writeInfoDto.getDetail());
        writeInfo.setWriteImg(writeInfoDto.getWriteImg());
        writeInfo.setUpdateTime(LocalDateTime.now());

        writeImgService.updateImg(imgFile, writeInfo, writeInfoDto);
    }

    public  WriteInfoDto searchInfo(Long Id){
        WriteInfo writeInfo = writeInfoRepository.findById(Id).orElseThrow();

        WriteInfoDto writeInfoDto = WriteInfoDto.of(writeInfo);

        WriteImg writeImg = writeImgRepository.findById(writeInfo.getWriteImg().getId()).orElseThrow();
        writeInfoDto.setWriteImg(writeImg);
        return writeInfoDto;
    }

    public  Page<NovelListDto> getCategoryPage(WriteInfoDto writeInfoDto, Pageable pageable, Optional<String> orderByFront, Optional<String> orderByBack){
      return   writeInfoRepository.getCategoryPage(writeInfoDto,pageable, orderByFront,orderByBack);
    }
    public  WriteInfo searchDetailId(Long Detail_InfoId){
        return  writeInfoRepository.findByWriteInfoId(Detail_InfoId);
    }
    public  Page<NovelListDto> getBestPage(WriteInfoDto writeInfoDto, Pageable pageable){
        return   writeInfoRepository.getBestPage(writeInfoDto,pageable );
    }

    public void deleteWriteDetail(Long id){
        LocalDateTime now = LocalDateTime.now();
       List<WriteDetail> writeDetailList =  writeDetailRepository.findByWriteInfoId(id, now);
       for (WriteDetail writeDetail : writeDetailList){
           writeDetailRepository.delete(writeDetail);
       }
       writeInfoRepository.deleteById(id);
    }

    public  List<WriteInfo> findAll(){
       return writeInfoRepository.findAllByOrderByUpdateTimeDesc();
    }

}

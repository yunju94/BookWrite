package com.book.write.service;

import com.book.write.dto.WriteInfoDto;
import com.book.write.entity.WriteImg;
import com.book.write.entity.WriteInfo;
import com.book.write.repository.WriteImgRepository;
import com.book.write.repository.WriteInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteImgService {
    @Value("${itemImgLocation}")
    private String ImgLocation;  ///home/ec2-user/Test/item
    private final FileService fileService;
    private  final WriteImgRepository writeImgRepository;
    private  final WriteInfoRepository writeInfoRepository;

    public  WriteImg  createImg(MultipartFile imgFile)throws Exception{
        WriteImg writeImg = new WriteImg();

        String oriImgName = imgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        if (!StringUtils.isEmpty(oriImgName)){ // oriImgName 문자열로 비어 있지 않으면 실행
            System.out.println("******");
            imgName = fileService.uploadFile(ImgLocation,oriImgName,imgFile.getBytes());
            imgUrl = "/images/item/"+imgName;
        }
        writeImg.uploadImg(oriImgName, imgName, imgUrl);
        writeImgRepository.save(writeImg);
        return writeImg;

    }
    public  void   updateImg(MultipartFile imgFile, WriteInfo writeInfo, WriteInfoDto writeInfoDto) throws Exception {
        if (!imgFile.isEmpty()) {
            //기존의 엔티티 조회
               WriteImg savedItemImg = writeImgRepository.findById(writeInfoDto.getWriteImg().getId())
                    .orElseThrow(EntityNotFoundException::new);//기존 엔티
            if (!StringUtils.isEmpty(savedItemImg.getImgName())) {
                fileService.deleteFile(ImgLocation + "/" + savedItemImg.getImgName());
            }

            String oriImgName = imgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(ImgLocation, oriImgName, imgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;
            savedItemImg.uploadImg(oriImgName, imgName, imgUrl);
        }
    }

    public void  deleteImg(long Infoid){
        WriteInfo writeInfo = writeInfoRepository.findByWriteInfoId(Infoid);
        WriteImg writeImg = writeImgRepository.findById(writeInfo.getWriteImg().getId()).orElseThrow();
        writeImgRepository.delete(writeImg);
    }

}

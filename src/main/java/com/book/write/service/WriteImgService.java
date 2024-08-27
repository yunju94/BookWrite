package com.book.write.service;

import com.book.write.dto.WriteInfoDto;
import com.book.write.entity.WriteImg;
import com.book.write.entity.WriteInfo;
import com.book.write.repository.WriteImgRepository;
import com.book.write.repository.WriteInfoRepository;
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
    public  void  updateImg(MultipartFile imgFile, WriteInfo writeInfo, WriteImg writeImg) throws Exception {
       if (!imgFile.isEmpty()) {// 상품 이미지를 수정 한 경우 상품 이미지 업데이트
           //기존의 엔티티 조회
           WriteImg writeImgs = writeImgRepository.findById(writeImg.getId())
                   .orElseThrow();
           if (!StringUtils.isEmpty(writeImgs.getImgName())) {
               fileService.deleteFile(ImgLocation + "/" + writeImgs.getImgName());
           }

           String oriImgName = imgFile.getOriginalFilename();
           String imgName = fileService.uploadFile(ImgLocation, oriImgName, imgFile.getBytes());
           String imgUrl = "/image/item/" + imgName;
           writeImgs.uploadImg(oriImgName, imgName, imgUrl);

       }
    }

    public void  deleteImg(long Infoid){
        WriteInfo writeInfo = writeInfoRepository.findByWriteInfoId(Infoid);
        WriteImg writeImg = writeImgRepository.findById(writeInfo.getWriteImg().getId()).orElseThrow();
        writeImgRepository.delete(writeImg);
    }

}

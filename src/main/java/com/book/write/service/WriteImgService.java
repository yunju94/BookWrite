package com.book.write.service;

import com.book.write.entity.WriteImg;
import com.book.write.repository.WriteImgRepository;
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
    private String ImgLocation;
    private final FileService fileService;
    private  final WriteImgRepository writeImgRepository;

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

}

package com.book.write.dto;

import com.book.write.constant.Category;
import com.book.write.entity.Member;
import com.book.write.entity.Write;
import com.book.write.entity.WriteImg;
import com.book.write.entity.WriteInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class WriteInfoDto {
    private Long id;

    @NotEmpty(message = "제목은 필수 입력 값입니다.")
    private String title;//제목


    private Category category;//종류

    @NotEmpty(message = "설명은 필수 입력 값입니다.")
    @Length(min = 1, max = 3000, message = "3000자 이하로 입력해주세요.")
    private String detail;//설명


    private Member member;//글쓴이


    private WriteImg writeImg;//이미지


    private Write write;//작성글


    private Long Heart;//관심수

    private Long view;//조회수

    private LocalDateTime regTime;

    private LocalDateTime updateTime;



    //---------------------------------------------//

    private static ModelMapper modelMapper = new ModelMapper();

    public  static  WriteInfoDto of(WriteInfo writeInfo){
        return  modelMapper.map(writeInfo, WriteInfoDto.class);
    }

    //------------------------------------------------------//


}

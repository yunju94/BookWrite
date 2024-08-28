package com.book.write.entity;

import com.book.write.dto.WriteDetailDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "write_detail")
@Getter
@Setter
public class WriteDetail extends BaseEntity{
    @Id
    @Column(name = "write_detail_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String miniTitle;//회차 제목


    @Column(columnDefinition = "TEXT")
    private String miniWrite;//회차 내용

    private int heart;//추천

    private int commentCount;//댓글수

    private int viewCount;//조회수

    private  LocalDate reserveDate;// 예약 날짜

    private LocalDateTime reserveTime;//예약 시간


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "write_info_id", referencedColumnName = "write_info_id")
    private WriteInfo writeInfo;





    public static WriteDetail createWrite(WriteDetailDto writeDetailDto){
  /////////////////////////////////////////////////////////////////////////////
        WriteDetail writeDetail = new WriteDetail();
        writeDetail.setMiniTitle(writeDetailDto.getMiniTitle());
        writeDetail.setMiniWrite(writeDetailDto.getMiniWrite());
        writeDetail.setWriteInfo(writeDetailDto.getWriteInfo());

        writeDetail.setHeart(0);
        writeDetail.setCommentCount(0);

 writeDetail.setReserveDate(writeDetailDto.getReserveDate() == null?  LocalDate.now() : writeDetailDto.getReserveDate());
 writeDetail.setReserveTime(writeDetailDto.getReserveTime() == null?  LocalDateTime.now() : writeDetailDto.getReserveTime());


        return writeDetail;
    }

    public  static  WriteDetail updateViewCount(WriteDetail writeDetail){
        writeDetail.setViewCount(writeDetail.getViewCount()+1);
        return writeDetail;
    }
    public  void  updateSave(WriteDetail writeDetail, WriteDetailDto writeDetailDto, WriteInfo writeInfo){
        writeDetail.setMiniTitle(writeDetailDto.getMiniTitle());
        writeDetail.setMiniWrite(writeDetailDto.getMiniWrite());
        writeDetail.setUpdateTime(LocalDateTime.now());
        writeDetail.setUpdateDate(LocalDate.now());
        writeInfo.setUpdateTime(LocalDateTime.now());
        writeInfo.setUpdateDate(LocalDate.now());
    }


}

package com.book.write.entity;

import com.book.write.constant.Category;
import com.book.write.dto.WriteInfoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "WriteInfo")
@Getter
@Setter
public class WriteInfo extends BaseEntity{
    @Id
    @Column(name = "write_info_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;//제목

    @Enumerated(EnumType.STRING)
    private Category category;//종류

    @Column(columnDefinition = "TEXT")
    private String detail;//설명

    private  int totalView;//전체 조회수

    private  int totalHeart;//전체 추천수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;//글쓴이

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writeImg_id")
    private WriteImg writeImg;//이미지

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "write_id")
    private List<WriteDetail> writeDetail;

    public static WriteInfo createDto(WriteInfoDto writeInfoDto){

        WriteInfo writeInfo = new WriteInfo();
        writeInfo.setMember(writeInfoDto.getMember());
        writeInfo.setCategory(writeInfoDto.getCategory());
        writeInfo.setDetail(writeInfoDto.getDetail());
        writeInfo.setTitle(writeInfoDto.getTitle());
        writeInfo.setWriteImg(writeInfoDto.getWriteImg());
        writeInfo.setTotalHeart(0);
        writeInfo.setTotalView(0);
        writeInfo.setRegDate(LocalDate.now());
        writeInfo.setUpdateDate(LocalDate.now());
        writeInfo.setRegTime(LocalDateTime.now());
        writeInfo.setUpdateTime(LocalDateTime.now());

        return writeInfo;
    }

    public static WriteInfo updateWriteDetail(WriteInfo writeInfo, WriteDetail writeDetail){
        writeInfo.setUpdateTime(LocalDateTime.now());
        writeInfo.setUpdateDate(LocalDate.now());
        return writeInfo;
    }

}

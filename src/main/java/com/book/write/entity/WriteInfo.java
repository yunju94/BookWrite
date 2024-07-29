package com.book.write.entity;

import com.book.write.constant.Category;
import com.book.write.dto.WriteInfoDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "WriteInfo")
@Getter
@Setter
public class WriteInfo {
    @Id
    @Column(name = "writeinfo_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;//제목

    @Enumerated(EnumType.STRING)
    private Category category;//종류

    @Column(columnDefinition = "TEXT")
    private String detail;//설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;//글쓴이

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "writeImg_id")
    private List<WriteImg> writeImg;//이미지





    public static WriteInfo createDto(WriteInfoDto writeInfoDto){

        WriteInfo writeInfo = new WriteInfo();
        writeInfo.setMember(writeInfoDto.getMember());
        writeInfo.setCategory(writeInfoDto.getCategory());
        writeInfo.setDetail(writeInfoDto.getDetail());
        writeInfo.setTitle(writeInfoDto.getTitle());
        return writeInfo;
    }


}

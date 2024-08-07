package com.book.write.entity;

import com.book.write.dto.WriteDetailDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`write`")
@Getter
@Setter
public class Write extends BaseEntity{
    @Id
    @Column(name = "write_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String miniTitle;//회차 제목


    @Column(columnDefinition = "TEXT")
    private String miniWrite;//회차 내용

    private int heart;//추천

    private int commentCount;//댓글수

    private int viewCount;//조회수

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id")
    private List<Coin> coin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writeInfo_id",  nullable = false)
    private WriteInfo writeInfo;

    public static Write createWrite(WriteDetailDto writeDetailDto){
        Write write = new Write();
        write.setMiniTitle(writeDetailDto.getMiniTitle());
        write.setMiniWrite(writeDetailDto.getMiniWrite());
        write.setWriteInfo(writeDetailDto.getWriteInfo());
        write.setRegDate(LocalDate.now());


        return write;
    }


}

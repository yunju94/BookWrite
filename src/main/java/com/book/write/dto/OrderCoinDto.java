package com.book.write.dto;

import com.book.write.constant.Order;
import com.book.write.constant.PR;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderCoinDto {
    private Long id;
    private Double  kdr;//키다리 코인
    private Double  yes;//예스 코인
    //private PR pr;//소장 , 대여
    private String title;//큰 제목
    private String miniTitle;//작은 제목
    private LocalDate pointDate;//구매 일자

    //coin join purchase joint Rental join writeInfo join writeDetail join

    @QueryProjection
    public OrderCoinDto(Long memberId, Double kdr, Double yes, String title,
                        String miniTitle, LocalDate pointDate) {
        this.id = memberId;
        this.kdr = (kdr != null) ? kdr : 0.0;
        this.yes = (yes != null) ? yes : 0.0;
        this.title = title;
        this.miniTitle = miniTitle;
        this.pointDate = pointDate;
    }


}

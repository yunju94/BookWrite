package com.book.write.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RequestPayDto {
    private String orderUid;
    private String itemName;
    private String buyerName;
    private int paymentPrice;
    private String buyerEmail;
    private String buyerTel;

    @Builder
    public RequestPayDto(String orderUid, String itemName, String buyerName, int paymentPrice, String buyerEmail, String buyerTel) {
        this.orderUid = orderUid;
        this.itemName = itemName;
        this.buyerName = buyerName;
        this.paymentPrice = paymentPrice;
        this.buyerEmail = buyerEmail;
        this.buyerTel = buyerTel;
    }
}
package com.test.bob.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfferCreateDto {
    private String nazwa;
    private String opis;
    private BigDecimal stawka;
    private String miasto;
}

package com.test.bob.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class UpdateOfferDto {
    private String nazwa;
    private String krotkiOpis;
    private Double stawka;
    private String miasto;
    private String status;

    private List<Long> removeImageIds;

}

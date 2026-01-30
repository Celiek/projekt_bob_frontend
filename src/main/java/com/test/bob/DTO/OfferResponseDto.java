package com.test.bob.DTO;

import com.test.bob.Entity.Offer;
import com.test.bob.Entity.ZdjecieOferty;
import lombok.Getter;

import java.util.List;

@Getter
public class OfferResponseDto {
    private Long id;
    private String nazwa;
    private String opis;
    private String krotkiOpis;
    private Double stawka;
    private String miasto;
    private String status;
    private List<String> images;
    private String ownerLogin;

    public OfferResponseDto(Offer offer) {
        this.opis = offer.getOpis();
        this.id = offer.getId();
        this.nazwa = offer.getNazwa();
        this.krotkiOpis = offer.getKrotkiOpis();
        this.stawka = offer.getStawka();
        this.miasto = offer.getMiasto();
        this.status = offer.getStatus();
        this.images = offer.getImagePath()
                .stream()
                .map(ZdjecieOferty::getFileKey)
                .toList();
        this.ownerLogin = offer.getOwner() != null
                ? offer.getOwner().getLogin()
                : null;
    }
}

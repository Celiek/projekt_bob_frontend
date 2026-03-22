package com.test.bob.Mappers;

import com.test.bob.DTO.OfferDTO;
import com.test.bob.Entity.Kategoria;
import com.test.bob.Entity.Offer;

import java.util.Collections;

public class OfferMapper {

    public static OfferDTO toDTO(Offer offer) {
        return new OfferDTO(
                offer.getId(),
                offer.getNazwa(),
                offer.getKrotkiOpis(),
                offer.getOpis(),
                offer.getMiasto(),
                offer.getStawka(),
                offer.getOwner().getLogin(),
                offer.getImagePath()
                        .stream().map(img -> img.getFileName())
                        .toList(),
                offer.getKategoria() != null
                        ? Collections.singletonList(offer.getKategoria().getNazwa())
                        : null
        );
    }
}
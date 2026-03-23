package com.test.bob.DTO;

import java.util.List;

public record OfferDTO(
        Long id,
        String nazwa,
        String krotkiOpis,
        String opis,
        String miasto,
        Double stawka,
        String ownerLogin,
        List<String> images,
        List<String> kategoria
) {}

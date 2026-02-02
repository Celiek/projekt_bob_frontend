package com.test.bob.Controller;

import com.test.bob.DTO.OfferCreateDto;
import com.test.bob.DTO.OfferResponseDto;
import com.test.bob.Entity.Offer;
import com.test.bob.Service.MinioService;
import com.test.bob.Service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {
    private final MinioService service;
    private final OfferService offerService;
    @Value("${minio.url}")
    private String imageBaseUrl;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OfferResponseDto> createOffer(
            @RequestPart("offer")OfferCreateDto dto,
            @RequestPart("image")MultipartFile image){

        Offer saved = offerService.createOffer(dto,image);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new OfferResponseDto(saved, imageBaseUrl));
    }

    @GetMapping
    public ResponseEntity<List<OfferResponseDto>> getAllOffer() {
        return ResponseEntity.ok(
                offerService.getAllOffers(imageBaseUrl)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchOffers(
            @RequestParam(required = false) String miasto,
            @RequestParam(required = false) Double minStawka,
            @RequestParam(required = false) Double maxStawka,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size
    ) {
        System.out.println("[DEBUG OFFERCONGTROLLER]");
        System.out.println("miasto=" + miasto);
        System.out.println("min=" + minStawka);
        System.out.println("max=" + maxStawka);
        System.out.println("page=" + page);
        System.out.println("size=" + size);
        return ResponseEntity.ok(
                offerService.getOffersFiltered(
                        miasto,
                        minStawka,
                        maxStawka,
                        page,
                        size,
                        imageBaseUrl
                )
        );
    }
}

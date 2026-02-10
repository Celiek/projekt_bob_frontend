package com.test.bob.Controller;

import com.test.bob.DTO.OfferCreateDto;
import com.test.bob.DTO.OfferResponseDto;
import com.test.bob.DTO.UpdateOfferDto;
import com.test.bob.Entity.Offer;
import com.test.bob.Service.MinioService;
import com.test.bob.Service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
    private String minioUrl;

    @Value("${minio.bucket}")
    private String bucket;

    private String imageBaseUrl() {
        return minioUrl + "/" + bucket;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OfferResponseDto> createOffer(
            @RequestPart("offer")OfferCreateDto dto,
            @RequestPart("image")MultipartFile image){

        Offer saved = offerService.createOffer(dto,image);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new OfferResponseDto(saved, imageBaseUrl()));
    }

    @GetMapping
    public ResponseEntity<List<OfferResponseDto>> getAllOffer() {
        return ResponseEntity.ok(
                offerService.getAllOffers(imageBaseUrl())
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OfferResponseDto>> search(
            @RequestParam(required = false) String miasto,
            @RequestParam(required = false) Double minStawka,
            @RequestParam(required = false) Double maxStawka,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue="desc") String direction,
            @RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        if (miasto != null && miasto.trim().isEmpty()) {
            miasto = null;
        }

        Page<OfferResponseDto> result =
                offerService.getOffersFiltered(
                        miasto,
                        minStawka,
                        maxStawka,
                        page,
                        size,
                        sortBy,
                        direction,
                        imageBaseUrl()
                );

        return ResponseEntity.ok(result);
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<OfferResponseDto > updateOffer(
            @PathVariable Long id,
            @RequestPart("offer") UpdateOfferDto dto,
            @RequestParam(value = "images",required = false) List<MultipartFile> images
    ) {
        Offer updated = offerService.updateOffer(id,dto, images);

        return ResponseEntity.ok(
                new OfferResponseDto(updated,imageBaseUrl())
        );
    }

    @GetMapping("/latest")
    public ResponseEntity<Page<OfferResponseDto>> latestOffers(
            @RequestParam(defaultValue = "0") int pageSize,
            @RequestParam(defaultValue = "25") int size
    ) {
        return ResponseEntity.ok(
                offerService.getLatestOffers(pageSize,size,imageBaseUrl())
        );
    }
}

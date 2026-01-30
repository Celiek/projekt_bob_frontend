package com.test.bob.Controller;

import com.test.bob.DTO.OfferCreateDto;
import com.test.bob.DTO.OfferResponseDto;
import com.test.bob.Service.MinioService;
import com.test.bob.Service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {
    private final MinioService service;
    private final OfferService offerService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createOffer(
            @RequestPart("offer")OfferCreateDto dto,
            @RequestPart("image")MultipartFile image){
        Long offerId = 1L;
        String imagePath = service.uploadOfferImage(offerId, image);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                    "offerId", offerId,
                    "imagePath", imagePath
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<OfferResponseDto>> getAllOffer() {
        return ResponseEntity.ok(
                offerService.getAllOffers()
        );
    }
}

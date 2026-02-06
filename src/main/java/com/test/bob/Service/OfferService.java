package com.test.bob.Service;

import com.test.bob.DTO.OfferCreateDto;
import com.test.bob.DTO.OfferResponseDto;
import com.test.bob.Entity.Offer;
import com.test.bob.Entity.Uzytkownik;
import com.test.bob.Entity.ZdjecieOferty;
import com.test.bob.Repository.OfferRepository;
import com.test.bob.Repository.UzytkownikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository repo;
    private final UzytkownikRepository userRepo;
    private final MinioService service;

    @Transactional
    public Offer createOffer(OfferCreateDto dto,
                             MultipartFile image) {
        String login = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Uzytkownik owner = userRepo.findByLogin(login)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Nie znaleziono użytkownika: " + login
                        )
                );

        Offer offer = new Offer();
        offer.setNazwa(dto.getNazwa());
        offer.setOpis(dto.getOpis());
        offer.setKrotkiOpis(dto.getKrotkiOpis());
        offer.setStawka(dto.getStawka());
        offer.setMiasto(dto.getMiasto());
        offer.setStatus(dto.getStatus());
        offer.setOwner(owner);

        Offer saved = repo.save(offer);

        String fileKey = service.uploadOfferImage(saved.getId(), image);

        ZdjecieOferty zdjecie = new ZdjecieOferty();
        zdjecie.setOffer(saved);
        zdjecie.setFileKey(fileKey);
        zdjecie.setFileName(image.getOriginalFilename());
        zdjecie.setContentType(image.getContentType());
        zdjecie.setPosition(0);
        saved.getImagePath().add(zdjecie);


        return repo.save(saved);
    }

    @Transactional(readOnly = true)
    public List<OfferResponseDto> getAllOffers(String imageBaseUrl) {
        return repo.findAll()
                .stream()
                .map(o -> new OfferResponseDto(o, imageBaseUrl))
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<OfferResponseDto> getOffersFiltered(
            String miasto,
            Double minStawka,
            Double maxStawka,
            int page,
            int size,
            String sortBy,
            String direction,
            String imageBaseUrl
    ) {
        Sort.Direction dir=
                direction.equalsIgnoreCase("asc")
                ? Sort.Direction.ASC:
                        Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(dir, sortBy)
        );

        return repo.findFiltered(
                miasto,
                minStawka,
                maxStawka,
                pageable)
                .map(offer -> new OfferResponseDto(offer,imageBaseUrl));
    }
}

package com.test.bob.Service;

import com.test.bob.DTO.OfferCreateDto;
import com.test.bob.DTO.OfferResponseDto;
import com.test.bob.DTO.UpdateOfferDto;
import com.test.bob.Entity.Offer;
import com.test.bob.Entity.Uzytkownik;
import com.test.bob.Entity.ZdjecieOferty;
import com.test.bob.Repository.OfferRepository;
import com.test.bob.Repository.UzytkownikRepository;
import com.test.bob.exception.OfferNotFoundException;
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

    private OfferResponseDto mapToDto(Offer offer){
        List<String> imageUrls = offer.getImagePath().stream()
                .map(img -> service.getPresignedUrl(img.getFileKey()))
                .toList();
        return new OfferResponseDto(offer,imageUrls);
    }

    @Transactional
    public OfferResponseDto createOffer(OfferCreateDto dto,
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


        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<OfferResponseDto> getAllOffers(String imageBaseUrl) {
        return repo.findAll()
                .stream()
                .map(this::mapToDto)
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
                .map(this::mapToDto);
    }

    @Transactional
    public OfferResponseDto updateOffer(Long offerID,
                             UpdateOfferDto dto,
                             List<MultipartFile> newImages) {

        Offer offer = repo.findById(offerID)
                .orElseThrow(() -> new RuntimeException("Oferta nie istnieje"));

        String login = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        if (!offer.getOwner().getLogin().equals(login)) {
            throw new RuntimeException("Brak uprawnień");
        }

        if (dto.getNazwa() != null) offer.setNazwa(dto.getNazwa());
        if (dto.getKrotkiOpis() != null) offer.setKrotkiOpis(dto.getKrotkiOpis());
        if (dto.getStawka() != null) offer.setStawka(dto.getStawka());
        if (dto.getMiasto() != null) offer.setMiasto(dto.getMiasto());
        if (dto.getStatus() != null) offer.setStatus(dto.getStatus());

        // USUWANIE
        if (dto.getRemoveImageIds() != null && !dto.getRemoveImageIds().isEmpty()) {
            List<ZdjecieOferty> toRemove = offer.getImagePath().stream()
                    .filter(img -> dto.getRemoveImageIds().contains(img.getId()))
                    .toList();

            for (ZdjecieOferty img : toRemove) {
                service.deleteObject(img.getFileKey());
                offer.getImagePath().remove(img);
            }
        }

        // DODAWANIE
        if (newImages != null && !newImages.isEmpty()) {
            int pos = offer.getImagePath().size();
            for (MultipartFile file : newImages) {
                String key = service.uploadOfferImage(offer.getId(), file);

                ZdjecieOferty img = new ZdjecieOferty();
                img.setOffer(offer);
                img.setFileKey(key);
                img.setFileName(file.getOriginalFilename());
                img.setContentType(file.getContentType());
                img.setPosition(pos++);

                offer.getImagePath().add(img);
            }
        }

        return mapToDto(offer);
    }

    public Page<OfferResponseDto> getLatestOffers(
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(
                page,size,Sort.by(Sort.Direction.DESC,"createdAt")
        );

        return repo.findAll(pageable)
                .map( this::mapToDto);
    }

    @Transactional
    public OfferResponseDto getofferById(Long id){
        Offer offer = repo.findById(id)
                .orElseThrow(() ->
                        new OfferNotFoundException(id)
                );
        return mapToDto(offer);
    }

}

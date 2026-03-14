package com.test.bob.Service;

import com.test.bob.DTO.DostepnoscDTO;
import com.test.bob.DTO.DostepnoscUpdateDTO;
import com.test.bob.Entity.Dostepnosc;
import com.test.bob.Entity.Uzytkownik;
import com.test.bob.Repository.DostepnoscRepository;
import com.test.bob.Repository.UzytkownikRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DostepnoscService {
    @Autowired
    private final DostepnoscRepository repo;
    private final UzytkownikRepository userRepo;

    public void ustawDostepnosc(String login, DostepnoscDTO dto){
        Uzytkownik user = userRepo.findByLogin(login)
                .orElseThrow(()-> new RuntimeException("Uztykownik nie istnieje"));
        Dostepnosc d = repo.findByUzytkownik(user)
                .orElse(new Dostepnosc());

        d.setUzytkownik(user);
        d.setPoniedzialek(dto.poniedzialek());
        d.setWtorek(dto.wtorek());
        d.setSroda(dto.sroda());
        d.setCzwartek(dto.czwartek());
        d.setPiatek(dto.piatek());
        d.setSobota(dto.sobota());
        d.setNiedziela(dto.niedziela());

        repo.save(d);

    }

    public Dostepnosc updateDostepnosc(Integer userId,
                                       DostepnoscUpdateDTO dto){

        Dostepnosc dostepnosc = repo
                .findByUzytkownikId(userId)
                .orElseThrow(() -> new RuntimeException("Brak dostepnosci"));

        if (dto.getPoniedzialek() != null)
        dostepnosc.setPoniedzialek(dto.getPoniedzialek());

        if (dto.getWtorek() != null)
            dostepnosc.setWtorek(dto.getWtorek());

        if (dto.getSroda() != null)
            dostepnosc.setSroda(dto.getSroda());

        if (dto.getCzwartek() != null)
            dostepnosc.setCzwartek(dto.getCzwartek());

        if (dto.getPiatek() != null)
            dostepnosc.setPiatek(dto.getPiatek());

        if (dto.getSobota() != null)
            dostepnosc.setSobota(dto.getSobota());

        if (dto.getNiedziela() != null)
            dostepnosc.setNiedziela(dto.getNiedziela());

        return repo.save(dostepnosc);
    }

}

package com.test.bob.Service;

import com.test.bob.DTO.UzytkownikDTO;
import com.test.bob.Entity.Uzytkownik;
import com.test.bob.Repository.UzytkownikRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UzytkownikService {
    @Autowired
    private UzytkownikRepository repo;

    public List<Uzytkownik> findAllUzytkownik() {
        List<Uzytkownik> results = repo.findAllUzytkownik();
        return results;
    }

    @Transactional
    public void addNewUzytkownik(String login,
                                 String haslo,
                                 String email,
                                 String imie,
                                 String nazwisko,
                                 int wiek,
                                 String status,
                                 byte[] zdjecie,
                                 String opis) {
        repo.addNewUzytkownik(login, haslo, email, imie, nazwisko, wiek, status, zdjecie, opis);
    }

    public void editUzytkownik(String login,
                               String haslo,
                               String email,
                               String imie,
                               String nazwisko,
                               int wiek,
                               String status,
                               String opis){
        repo.editUzytkownik(login, haslo, email, imie, nazwisko, wiek, status, opis);
    }

    public void deleteUzytkownik(String login){
        repo.deleteUzytkownik(login);
    }

    public List<UzytkownikDTO>findAll(){
        return StreamSupport.stream(repo.findAll().spliterator(), false)
                .map(p->new UzytkownikDTO(p.getImie(), p.getNazwisko(), p.getStatus(), p.getOpis(),
                        p.getId_dostepnosc(), p.getId_kategorii())).
                        collect(Collectors.toList());
    }
}

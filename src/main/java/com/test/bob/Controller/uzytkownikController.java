package com.test.bob.Controller;

import com.test.bob.DTO.DostepnoscDTO;
import com.test.bob.DTO.DostepnoscUpdateDTO;
import com.test.bob.DTO.OfferDTO;
import com.test.bob.DTO.UzytkownikDTO;
import com.test.bob.Entity.Dostepnosc;
import com.test.bob.Entity.Offer;
import com.test.bob.Entity.Uzytkownik;
import com.test.bob.Mappers.OfferMapper;
import com.test.bob.Repository.UzytkownikRepository;
import com.test.bob.Service.DostepnoscService;
import com.test.bob.Service.UzytkownikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/uzytkownicy")

public class uzytkownikController {
    @Autowired
    private UzytkownikService service;

    @Autowired
    private DostepnoscService dostepnoscService;


    @Autowired
    private UzytkownikRepository repo;


    @GetMapping("/findall")
    public List<Uzytkownik> wildcard() {
        return service.findAllUzytkownik();
    }

    @PutMapping("/me/dostepnosc")
    public String ustawDostepnosc(@RequestBody DostepnoscDTO dto){
        String login = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        dostepnoscService.ustawDostepnosc(login,dto);

        return "Zmieniono dostepnosc";
    }

    @PatchMapping("/me/updatedostepnosc")
    public Dostepnosc updateDostepnosc(@RequestBody DostepnoscUpdateDTO dto){
        String login = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Uzytkownik user = repo.findByLogin(login).orElseThrow();

        return dostepnoscService.updateDostepnosc(user.getId(),dto);
    }

    @PostMapping("/rejestracja")
    public ResponseEntity<?> addNewUzytkownik(@RequestParam String login,
                                              @RequestParam String haslo,
                                              @RequestParam String email,
                                              @RequestParam String imie,
                                              @RequestParam String nazwisko,
                                              @RequestParam int wiek,
                                              @RequestParam String status,
                                              @RequestParam MultipartFile zdjecie,
                                              @RequestParam String opis) {

        try {
            byte[]zdj=zdjecie.getBytes();
            service.addNewUzytkownik(login, haslo, email, imie, nazwisko, wiek, status, zdj, opis);
            return ResponseEntity.ok("Dodano nowego uzytkownika");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystapil blad" + e.getMessage());
        }
    }

    @PutMapping("/edytujuzytkownika")
    public ResponseEntity<String> editUzytkownik(@RequestParam String login,
                                                 @RequestParam String haslo,
                                                 @RequestParam String email,
                                                 @RequestParam String imie,
                                                 @RequestParam String nazwisko,
                                                 @RequestParam int wiek,
                                                 @RequestParam String status,
                                                 @RequestParam String opis) {
        try {
            service.editUzytkownik(login, haslo, email, imie, nazwisko, wiek, status, opis);
            return ResponseEntity.ok("Zedytowawno uzytkownika");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystapil blad" + e.getMessage());
        }
    }

    @PostMapping("/usunuzytkownika")
    public ResponseEntity<String> deleteUzytkownik(@RequestParam String login) {
        try {
            service.deleteUzytkownik(login);
            return ResponseEntity.ok("Usunięto użytkownika");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystapil blad" + e.getMessage());

        }
    }

    //zwraca liste uzytkowników
    @GetMapping("/wyswietl")
    public List<UzytkownikDTO>getAll(){
        try{
            return service.findAll();
        }catch  (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    //zwraca dane użytkownika

    @GetMapping("/me")
    public Uzytkownik me(){
        String login = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return repo.findByLogin(login)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,"Uzytkownik nie zalogowany"));
    }

    @PostMapping("/changerole")
    public String changeRoleToSpecjalistaByLogin(){
        String login = SecurityContextHolder.
                getContext().
                getAuthentication()
                .getName();
        try{
            service.changeRoleToSpecjalistaByLogin(login);
            return ("Zmieniono rolę dla " + login);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //zwraca listę ofert wystawionych przez użytkownika
    @GetMapping("/me/alloffers")
    public List<OfferDTO> getAllOffersByLogin(){
        String login = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        System.out.println("Login uzytkownika"+login);

        List<Offer> oferty =  service.getAllOffersByLogin(login);
        System.out.println("lista ofert " +oferty);
        return oferty.stream()
                .map(OfferMapper::toDTO)
                .toList();
    }

}
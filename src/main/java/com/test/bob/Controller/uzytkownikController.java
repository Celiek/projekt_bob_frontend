package com.test.bob.Controller;

import com.test.bob.DTO.UzytkownikDTO;
import com.test.bob.Entity.Uzytkownik;
import com.test.bob.Service.UzytkownikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/uzytkownicy")

public class uzytkownikController {
    @Autowired
    private UzytkownikService service;

    @GetMapping("/wildcard")
    public List<Uzytkownik> wildcard() {
        return service.findAllUzytkownik();
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

    @GetMapping("/wyswietl")
    public List<UzytkownikDTO>getAll(){
        try{
            return service.findAll();
        }catch  (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
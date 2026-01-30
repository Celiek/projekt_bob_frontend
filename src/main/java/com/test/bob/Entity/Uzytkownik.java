package com.test.bob.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="uzytkownik")
public class Uzytkownik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_uzytkownik")
    private int id_uzytkownik;
    private String login;
    private String haslo;
    private String email;
    private String imie;
    private String nazwisko;
    private int wiek;
    private String status;
    @Column(name = "zdjecie", columnDefinition = "bytea", nullable = true)
    private byte[] zdjecie;
    private String opis;
    private Integer id_dostepnosc;
    private Integer id_kategorii;

}

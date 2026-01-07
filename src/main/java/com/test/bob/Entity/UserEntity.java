package com.test.bob.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="uzytkownik")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id_uzytkownik;
    @Column(unique = true)
    private String login;
    private String haslo;
    @Column(unique = true)
    private String email;
    private String imie;
    private String nazwisko;
    private String Status = "Uzytkownik";
    private int wiek;
    @Lob
    private byte[] zdjecie;
}

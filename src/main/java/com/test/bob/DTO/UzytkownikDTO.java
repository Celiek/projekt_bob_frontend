package com.test.bob.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UzytkownikDTO {
    private String imie;
    private String nazwisko;
    private String Status;
//    private byte[] zdjecie;
    private String opis;
    private Integer id_dostepnosc;
    private Integer id_kategorii;
}

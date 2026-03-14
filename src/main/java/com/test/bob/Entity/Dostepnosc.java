package com.test.bob.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="dostepnosc")
@Getter
@Setter
public class Dostepnosc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDostepnosci;
    private Boolean poniedzialek;
    private Boolean wtorek;
    private Boolean sroda;
    private Boolean czwartek;
    private Boolean piatek;
    private Boolean sobota;
    private Boolean niedziela;
    @OneToOne
    @JoinColumn(name="id_uzytkownika")
    private Uzytkownik uzytkownik;

}


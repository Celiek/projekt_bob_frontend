package com.test.bob.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "kategorie")
@Entity
@Getter
@Setter
public class Kategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_kategorii;

    private String nazwa;

    @ManyToOne
    @JoinColumn(name = "uzytkownik_id")
    private Uzytkownik uzytkownik;

    @OneToMany(mappedBy = "kategoria")
    private List<Offer> oferty = new ArrayList<>();
}

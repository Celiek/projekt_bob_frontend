package com.test.bob.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "oferty")
@Getter
@Setter
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nazwa;
    private String opis;

    @Column(name = "krotki_opis")
    private String krotkiOpis;

    @Column(name = "stawka")
    private Double stawka;

    private String miasto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "owner_id",
            referencedColumnName = "id_uzytkownik")
    private Uzytkownik owner;

    private String status;

    @OneToMany(mappedBy = "offer")
    private List<ZdjecieOferty> imagePath;
}
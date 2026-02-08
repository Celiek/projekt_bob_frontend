package com.test.bob.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "offer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ZdjecieOferty> imagePath = new ArrayList<>();

    @UpdateTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void addImage(ZdjecieOferty image){
        imagePath.add(image);
        image.setOffer(this);
    }

    public void removeOffer(ZdjecieOferty image){
        imagePath.remove(image);
        image.setOffer(null);
    }
}
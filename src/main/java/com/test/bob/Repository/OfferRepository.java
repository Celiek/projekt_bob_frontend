package com.test.bob.Repository;

import com.test.bob.Entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("""
        SELECT o FROM Offer o
        WHERE (:miasto IS NULL OR o.miasto = :miasto)
          AND (:minStawka IS NULL OR o.stawka >= :minStawka)
          AND (:maxStawka IS NULL OR o.stawka <= :maxStawka)
    """)
    Page<Offer> findFiltered(
            @Param("miasto") String miasto,
            @Param("minStawka") Double minStawka,
            @Param("maxStawka") Double maxStawka,
            Pageable pageable
    );
}

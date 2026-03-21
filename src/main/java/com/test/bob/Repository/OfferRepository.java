package com.test.bob.Repository;

import com.test.bob.Entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;


public interface OfferRepository extends JpaRepository<Offer, Long> {
    // dodać kategorię do wyszukiwania
    @Query("""
    SELECT o FROM Offer o
    WHERE (:miasto IS NULL OR :miasto = '' OR o.miasto = :miasto)
    AND (:minStawka IS NULL OR o.stawka >= :minStawka)
    AND (:maxStawka IS NULL OR o.stawka <= :maxStawka)
    """)
    Page<Offer> findFiltered(
            @Param("miasto") String miasto,
            @Param("minStawka") Double minStawka,
            @Param("maxStawka") Double maxStawka,
            Pageable pageable
    );

    @Query("SELECT o FROM Offer o WHERE o.owner.login = :login")
    List<Offer> getAllOffersByLogin(@Param("login")String login);
}

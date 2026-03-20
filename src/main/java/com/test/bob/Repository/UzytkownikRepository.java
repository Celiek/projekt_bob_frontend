package com.test.bob.Repository;

import com.test.bob.Entity.Offer;
import com.test.bob.Entity.Uzytkownik;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UzytkownikRepository extends CrudRepository<Uzytkownik, Integer > {

    @Query(value="select * from uzytkownik", nativeQuery = true)
    List<Uzytkownik> findAllUzytkownik();

    @Transactional
    @Modifying
    @Query(value="insert into uzytkownik (login, haslo, email, imie, nazwisko, wiek, status, zdjecie, opis) " +
            "values(:login, :haslo, :email, :imie, :nazwisko, :wiek, :status, :zdjecie, :opis);", nativeQuery = true)
    void addNewUzytkownik(@Param("login")String login,
                          @Param("haslo")String haslo,
                            @Param("email")String email,
                            @Param("imie")String imie,
                            @Param("nazwisko")String nazwisko,
                            @Param("wiek")int wiek,
                            @Param("status")String status,
                            @Param("zdjecie")byte[] zdjecie,
                            @Param("opis")String opis);


    @Modifying
    @Transactional
    @Query(value = "update uzytkownik set login=COALESCE(:login,login), " +
                                        "haslo=COALESCE(:haslo,haslo), " +
                                        "email=COALESCE(:email,email), " +
                                        "imie=COALESCE(:imie,imie), " +
                                        "nazwisko=COALESCE(:nazwisko,nazwisko), " +
                                        "wiek=COALESCE(:wiek,wiek), " +
                                        "status=COALESCE(:status,status), " +
                                        "opis=COALESCE(:opis,opis)" +
                                        "WHERE login=:login",
            nativeQuery = true)

    void editUzytkownik(@Param("login")String login,
                        @Param("haslo")String haslo,
                        @Param("email")String email,
                        @Param("imie")String imie,
                        @Param("nazwisko")String nazwisko,
                        @Param("wiek")int wiek,
                        @Param("status")String status,
                        @Param("opis")String opis);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM uzytkownik WHERE login=COALESCE(:login,login)", nativeQuery=true)
    void deleteUzytkownik(@Param("login")String login);


    Optional<Uzytkownik> findByLogin(String login);

    boolean existsByLogin(String login);
    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update uzytkownik SET status='specjalist' where login = login",nativeQuery = true)
    boolean changeRoleToSpecjalistaByLogin(@Param("login") String login);

    @Query(value = "Select DISTINCT o FROM Offer o LEFT JOIN FETCH o.imagePath WHERE o.owner.login = :login")
    List<Offer> getAllOffersByLogin(@Param("login")String login);

}



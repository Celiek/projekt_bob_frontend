package com.test.bob.Repository;

import com.test.bob.DTO.RegisterDTO;
import com.test.bob.Entity.Uzytkownik;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface registerRepository extends JpaRepository<RegisterDTO, Integer > {
//    Do poprawienia jeśli użytkonik jeszce nie istnieje to git jak nie to wywal błąd
    @Transactional
    @Modifying
    @Query(value="insert into uzytkownik (login, haslo, email, imie, nazwisko, wiek, status, zdjecie, opis) " +
            "values(:login, :haslo, :email, :imie, :nazwisko, :wiek, :status, :zdjecie);", nativeQuery = true)
    void registerUzytkownik(@Param("login")String login,
                          @Param("haslo")String haslo,
                          @Param("email")String email,
                          @Param("imie")String imie,
                          @Param("nazwisko")String nazwisko,
                          @Param("wiek")int wiek,
                          @Param("status")String status,
                          @Param("zdjecie")byte[] zdjecie);
//zmienic sql'a
    @Transactional
    @Modifying
    @Query(value="insert into uzytkownik (login, haslo, email, imie, nazwisko, wiek, status, zdjecie, opis) " +
            "values(:login, :haslo, :email, :imie, :nazwisko, :wiek, :status, :zdjecie);", nativeQuery = true)
    void changeToFachowiec(@Param("login")String login,
                            @Param("haslo")String haslo,
                            @Param("email")String email,
                            @Param("imie")String imie,
                            @Param("nazwisko")String nazwisko,
                            @Param("wiek")int wiek,
                            @Param("status")String status,
                            @Param("zdjecie")byte[] zdjecie);

    boolean existsByLogin(String login);
    boolean existsByEmail(String email);


    Uzytkownik save(Uzytkownik user);

    Optional<Object> findByLoginOrEmail(@NotBlank(message = "Login jest niepoprawny") String login, @NotBlank(message = "Login jest niepoprawny") String login1);
}

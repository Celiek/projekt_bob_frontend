package com.test.bob.Repository;

import com.test.bob.Entity.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterRepository extends JpaRepository<Uzytkownik, Long> {

    boolean existsByLogin(String login);
    boolean existsByEmail(String email);

    Optional<Uzytkownik> findByLoginOrEmail(String login, String email);

    Optional<Uzytkownik> findByLogin(String login);
}

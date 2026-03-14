package com.test.bob.Repository;

import com.test.bob.Entity.Dostepnosc;
import com.test.bob.Entity.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DostepnoscRepository extends JpaRepository<Dostepnosc,Long> {

    Optional<Dostepnosc> findByUzytkownik(Uzytkownik uzytkownik);

    Optional<Dostepnosc> findByUzytkownikId(Integer id);
}

package com.test.bob.Repository;

import com.test.bob.Entity.Kategoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KategoriaRepository extends JpaRepository<Kategoria, Long> {
    
}

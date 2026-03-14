package com.test.bob.Repository;

import com.test.bob.Entity.ZdjecieOferty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZdjecieOfertyRepository extends JpaRepository<ZdjecieOferty, Long> {

    List<ZdjecieOferty> findByOfferId(Long offerid);

}

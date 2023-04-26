package com.udea.repository;

import com.udea.domain.MateriaSemestre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MateriaSemestre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MateriaSemestreRepository extends JpaRepository<MateriaSemestre, Long> {}

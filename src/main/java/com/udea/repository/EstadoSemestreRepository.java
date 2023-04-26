package com.udea.repository;

import com.udea.domain.EstadoSemestre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EstadoSemestre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoSemestreRepository extends JpaRepository<EstadoSemestre, Long> {}

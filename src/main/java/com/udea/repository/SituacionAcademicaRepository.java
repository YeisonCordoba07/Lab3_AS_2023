package com.udea.repository;

import com.udea.domain.SituacionAcademica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SituacionAcademica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SituacionAcademicaRepository extends JpaRepository<SituacionAcademica, Long> {}

package com.udea.repository;

import com.udea.domain.HistoriaAcademica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HistoriaAcademica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriaAcademicaRepository extends JpaRepository<HistoriaAcademica, Long> {}

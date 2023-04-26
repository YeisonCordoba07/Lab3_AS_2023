package com.udea.repository;

import com.udea.domain.ProgramaAcademico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProgramaAcademico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramaAcademicoRepository extends JpaRepository<ProgramaAcademico, Long> {}

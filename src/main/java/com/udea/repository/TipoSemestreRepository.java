package com.udea.repository;

import com.udea.domain.TipoSemestre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TipoSemestre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoSemestreRepository extends JpaRepository<TipoSemestre, Long> {}

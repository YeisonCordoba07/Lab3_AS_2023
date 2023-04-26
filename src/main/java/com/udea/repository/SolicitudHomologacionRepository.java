package com.udea.repository;

import com.udea.domain.SolicitudHomologacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SolicitudHomologacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SolicitudHomologacionRepository extends JpaRepository<SolicitudHomologacion, Long> {}

package com.udea.repository;

import com.udea.domain.EstadoSolicitud;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EstadoSolicitud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoSolicitudRepository extends JpaRepository<EstadoSolicitud, Long> {}

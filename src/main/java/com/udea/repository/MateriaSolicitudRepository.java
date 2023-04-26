package com.udea.repository;

import com.udea.domain.MateriaSolicitud;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MateriaSolicitud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MateriaSolicitudRepository extends JpaRepository<MateriaSolicitud, Long> {}

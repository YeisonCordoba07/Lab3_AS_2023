package com.udea.repository;

import com.udea.domain.Relacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Relacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelacionRepository extends JpaRepository<Relacion, Long> {}

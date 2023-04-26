package com.udea.repository;

import com.udea.domain.PlanEstudios;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlanEstudios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanEstudiosRepository extends JpaRepository<PlanEstudios, Long> {}

package com.udea.repository;

import com.udea.domain.Tercio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tercio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TercioRepository extends JpaRepository<Tercio, Long> {}

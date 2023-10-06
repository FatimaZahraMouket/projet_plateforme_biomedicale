package com.apbiomedicale.repository;

import com.apbiomedicale.domain.BoitierCapteur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BoitierCapteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoitierCapteurRepository extends JpaRepository<BoitierCapteur, Long> {}

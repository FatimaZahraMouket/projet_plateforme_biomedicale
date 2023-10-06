package com.apbiomedicale.repository;

import com.apbiomedicale.domain.MedecinPatient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MedecinPatient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedecinPatientRepository extends JpaRepository<MedecinPatient, Long> {}

package com.ulb.data.center.repository;

import com.ulb.data.center.domain.DcRegion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DcRegion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DcRegionRepository extends JpaRepository<DcRegion, Long> {

}

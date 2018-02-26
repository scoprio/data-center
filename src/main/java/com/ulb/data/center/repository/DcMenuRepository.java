package com.ulb.data.center.repository;

import com.ulb.data.center.domain.DcMenu;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DcMenu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DcMenuRepository extends JpaRepository<DcMenu, Long> {

}

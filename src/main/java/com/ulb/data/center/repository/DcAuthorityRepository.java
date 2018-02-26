package com.ulb.data.center.repository;

import com.ulb.data.center.domain.DcAuthority;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the DcAuthority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DcAuthorityRepository extends JpaRepository<DcAuthority, Long> {
    @Query("select distinct authority from DcAuthority authority left join fetch authority.dcRegions")
    List<DcAuthority> findAllWithEagerRelationships();

    @Query("select authority from DcAuthority authority left join fetch authority.dcRegions where authority.id =:id")
    DcAuthority findOneWithEagerRelationships(@Param("id") Long id);


    @Query("select authority from DcAuthority authority where authority.name =:name")
    DcAuthority findOneByName(@Param("name") String name);

}

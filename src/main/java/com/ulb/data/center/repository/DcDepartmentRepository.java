package com.ulb.data.center.repository;

import com.ulb.data.center.domain.DcDepartment;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the DcDepartment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DcDepartmentRepository extends JpaRepository<DcDepartment, Long> {
    @Query("select distinct dc_department from DcDepartment dc_department left join fetch dc_department.dcMenus")
    List<DcDepartment> findAllWithEagerRelationships();

    @Query("select dc_department from DcDepartment dc_department left join fetch dc_department.dcMenus where dc_department.id =:id")
    DcDepartment findOneWithEagerRelationships(@Param("id") Long id);

}

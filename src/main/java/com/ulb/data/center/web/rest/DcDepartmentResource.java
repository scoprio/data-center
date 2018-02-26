package com.ulb.data.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ulb.data.center.domain.DcDepartment;

import com.ulb.data.center.repository.DcDepartmentRepository;
import com.ulb.data.center.web.rest.errors.BadRequestAlertException;
import com.ulb.data.center.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DcDepartment.
 */
@RestController
@RequestMapping("/api")
public class DcDepartmentResource {

    private final Logger log = LoggerFactory.getLogger(DcDepartmentResource.class);

    private static final String ENTITY_NAME = "dcDepartment";

    private final DcDepartmentRepository dcDepartmentRepository;

    public DcDepartmentResource(DcDepartmentRepository dcDepartmentRepository) {
        this.dcDepartmentRepository = dcDepartmentRepository;
    }

    /**
     * POST  /dc-departments : Create a new dcDepartment.
     *
     * @param dcDepartment the dcDepartment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dcDepartment, or with status 400 (Bad Request) if the dcDepartment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dc-departments")
    @Timed
    public ResponseEntity<DcDepartment> createDcDepartment(@RequestBody DcDepartment dcDepartment) throws URISyntaxException {
        log.debug("REST request to save DcDepartment : {}", dcDepartment);
        if (dcDepartment.getId() != null) {
            throw new BadRequestAlertException("A new dcDepartment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dcDepartment.setCreateTime(System.currentTimeMillis());
        DcDepartment result = dcDepartmentRepository.save(dcDepartment);
        return ResponseEntity.created(new URI("/api/dc-departments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dc-departments : Updates an existing dcDepartment.
     *
     * @param dcDepartment the dcDepartment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dcDepartment,
     * or with status 400 (Bad Request) if the dcDepartment is not valid,
     * or with status 500 (Internal Server Error) if the dcDepartment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dc-departments")
    @Timed
    public ResponseEntity<DcDepartment> updateDcDepartment(@RequestBody DcDepartment dcDepartment) throws URISyntaxException {
        log.debug("REST request to update DcDepartment : {}", dcDepartment);
        if (dcDepartment.getId() == null) {
            return createDcDepartment(dcDepartment);
        }

        DcDepartment dcDepartmentOld = dcDepartmentRepository.findOneWithEagerRelationships(dcDepartment.getId());
        dcDepartmentOld.setName(dcDepartment.getName());
        dcDepartmentOld.setDescription(dcDepartment.getDescription());
        dcDepartmentOld.setIsEnable(dcDepartment.isEnable());
        dcDepartmentOld.setDcMenus(dcDepartment.getDcMenus());
        dcDepartmentOld.setUpdateTime(System.currentTimeMillis());
        DcDepartment result = dcDepartmentRepository.save(dcDepartmentOld);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dcDepartment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dc-departments : get all the dcDepartments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dcDepartments in body
     */
    @GetMapping("/dc-departments")
    @Timed
    public List<DcDepartment> getAllDcDepartments() {
        log.debug("REST request to get all DcDepartments");
        return dcDepartmentRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /dc-departments/:id : get the "id" dcDepartment.
     *
     * @param id the id of the dcDepartment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dcDepartment, or with status 404 (Not Found)
     */
    @GetMapping("/dc-departments/{id}")
    @Timed
    public ResponseEntity<DcDepartment> getDcDepartment(@PathVariable Long id) {
        log.debug("REST request to get DcDepartment : {}", id);
        DcDepartment dcDepartment = dcDepartmentRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dcDepartment));
    }

    /**
     * DELETE  /dc-departments/:id : delete the "id" dcDepartment.
     *
     * @param id the id of the dcDepartment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dc-departments/{id}")
    @Timed
    public ResponseEntity<Void> deleteDcDepartment(@PathVariable Long id) {
        log.debug("REST request to delete DcDepartment : {}", id);
        dcDepartmentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

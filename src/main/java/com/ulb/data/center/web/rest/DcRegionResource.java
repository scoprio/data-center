package com.ulb.data.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ulb.data.center.domain.DcRegion;

import com.ulb.data.center.repository.DcRegionRepository;
import com.ulb.data.center.web.rest.errors.BadRequestAlertException;
import com.ulb.data.center.web.rest.util.HeaderUtil;
import com.ulb.data.center.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DcRegion.
 */
@RestController
@RequestMapping("/api")
public class DcRegionResource {

    private final Logger log = LoggerFactory.getLogger(DcRegionResource.class);

    private static final String ENTITY_NAME = "dcRegion";

    private final DcRegionRepository dcRegionRepository;

    public DcRegionResource(DcRegionRepository dcRegionRepository) {
        this.dcRegionRepository = dcRegionRepository;
    }

    /**
     * POST  /dc-regions : Create a new dcRegion.
     *
     * @param dcRegion the dcRegion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dcRegion, or with status 400 (Bad Request) if the dcRegion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dc-regions")
    @Timed
    public ResponseEntity<DcRegion> createDcRegion(@RequestBody DcRegion dcRegion) throws URISyntaxException {
        log.debug("REST request to save DcRegion : {}", dcRegion);
        if (dcRegion.getId() != null) {
            throw new BadRequestAlertException("A new dcRegion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DcRegion result = dcRegionRepository.save(dcRegion);
        return ResponseEntity.created(new URI("/api/dc-regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dc-regions : Updates an existing dcRegion.
     *
     * @param dcRegion the dcRegion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dcRegion,
     * or with status 400 (Bad Request) if the dcRegion is not valid,
     * or with status 500 (Internal Server Error) if the dcRegion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dc-regions")
    @Timed
    public ResponseEntity<DcRegion> updateDcRegion(@RequestBody DcRegion dcRegion) throws URISyntaxException {
        log.debug("REST request to update DcRegion : {}", dcRegion);
        if (dcRegion.getId() == null) {
            return createDcRegion(dcRegion);
        }
        DcRegion result = dcRegionRepository.save(dcRegion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dcRegion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dc-regions : get all the dcRegions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dcRegions in body
     */
    @GetMapping("/dc-regions")
    @Timed
    public ResponseEntity<List<DcRegion>> getAllDcRegions(Pageable pageable) {
        log.debug("REST request to get a page of DcRegions");
        Page<DcRegion> page = dcRegionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dc-regions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dc-regions/:id : get the "id" dcRegion.
     *
     * @param id the id of the dcRegion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dcRegion, or with status 404 (Not Found)
     */
    @GetMapping("/dc-regions/{id}")
    @Timed
    public ResponseEntity<DcRegion> getDcRegion(@PathVariable Long id) {
        log.debug("REST request to get DcRegion : {}", id);
        DcRegion dcRegion = dcRegionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dcRegion));
    }

    /**
     * DELETE  /dc-regions/:id : delete the "id" dcRegion.
     *
     * @param id the id of the dcRegion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dc-regions/{id}")
    @Timed
    public ResponseEntity<Void> deleteDcRegion(@PathVariable Long id) {
        log.debug("REST request to delete DcRegion : {}", id);
        dcRegionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

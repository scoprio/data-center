package com.ulb.data.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ulb.data.center.domain.DcAuthority;

import com.ulb.data.center.repository.DcAuthorityRepository;
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
 * REST controller for managing DcAuthority.
 */
@RestController
@RequestMapping("/api")
public class DcAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(DcAuthorityResource.class);

    private static final String ENTITY_NAME = "dcAuthority";

    private final DcAuthorityRepository dcAuthorityRepository;

    public DcAuthorityResource(DcAuthorityRepository dcAuthorityRepository) {
        this.dcAuthorityRepository = dcAuthorityRepository;
    }

    /**
     * POST  /dc-authorities : Create a new dcAuthority.
     *
     * @param dcAuthority the dcAuthority to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dcAuthority, or with status 400 (Bad Request) if the dcAuthority has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dc-authorities")
    @Timed
    public ResponseEntity<DcAuthority> createDcAuthority(@RequestBody DcAuthority dcAuthority) throws URISyntaxException {
        log.debug("REST request to save DcAuthority : {}", dcAuthority);
        if (dcAuthority.getId() != null) {
            throw new BadRequestAlertException("A new dcAuthority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DcAuthority result = dcAuthorityRepository.save(dcAuthority);
        return ResponseEntity.created(new URI("/api/dc-authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dc-authorities : Updates an existing dcAuthority.
     *
     * @param dcAuthority the dcAuthority to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dcAuthority,
     * or with status 400 (Bad Request) if the dcAuthority is not valid,
     * or with status 500 (Internal Server Error) if the dcAuthority couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dc-authorities")
    @Timed
    public ResponseEntity<DcAuthority> updateDcAuthority(@RequestBody DcAuthority dcAuthority) throws URISyntaxException {
        log.debug("REST request to update DcAuthority : {}", dcAuthority);
        if (dcAuthority.getId() == null) {
            return createDcAuthority(dcAuthority);
        }
        DcAuthority result = dcAuthorityRepository.save(dcAuthority);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dcAuthority.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dc-authorities : get all the dcAuthorities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dcAuthorities in body
     */
    @GetMapping("/dc-authorities")
    @Timed
    public ResponseEntity<List<DcAuthority>> getAllDcAuthorities(Pageable pageable) {
        log.debug("REST request to get a page of DcAuthorities");
        Page<DcAuthority> page = dcAuthorityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dc-authorities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dc-authorities/:id : get the "id" dcAuthority.
     *
     * @param id the id of the dcAuthority to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dcAuthority, or with status 404 (Not Found)
     */
    @GetMapping("/dc-authorities/{id}")
    @Timed
    public ResponseEntity<DcAuthority> getDcAuthority(@PathVariable Long id) {
        log.debug("REST request to get DcAuthority : {}", id);
        DcAuthority dcAuthority = dcAuthorityRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dcAuthority));
    }

    /**
     * DELETE  /dc-authorities/:id : delete the "id" dcAuthority.
     *
     * @param id the id of the dcAuthority to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dc-authorities/{id}")
    @Timed
    public ResponseEntity<Void> deleteDcAuthority(@PathVariable Long id) {
        log.debug("REST request to delete DcAuthority : {}", id);
        dcAuthorityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.ulb.data.center.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ulb.data.center.domain.DcMenu;

import com.ulb.data.center.repository.DcMenuRepository;
import com.ulb.data.center.security.SecurityUtils;
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
 * REST controller for managing DcMenu.
 */
@RestController
@RequestMapping("/api")
public class DcMenuResource {

    private final Logger log = LoggerFactory.getLogger(DcMenuResource.class);

    private static final String ENTITY_NAME = "dcMenu";

    private final DcMenuRepository dcMenuRepository;

    public DcMenuResource(DcMenuRepository dcMenuRepository) {
        this.dcMenuRepository = dcMenuRepository;
    }

    /**
     * POST  /dc-menus : Create a new dcMenu.
     *
     * @param dcMenu the dcMenu to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dcMenu, or with status 400 (Bad Request) if the dcMenu has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dc-menus")
    @Timed
    public ResponseEntity<DcMenu> createDcMenu(@RequestBody DcMenu dcMenu) throws URISyntaxException {
        log.debug("REST request to save DcMenu : {}", dcMenu);
        if (dcMenu.getId() != null) {
            throw new BadRequestAlertException("A new dcMenu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dcMenu.setCreateTime(System.currentTimeMillis());
        DcMenu result = dcMenuRepository.save(dcMenu);
        return ResponseEntity.created(new URI("/api/dc-menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dc-menus : Updates an existing dcMenu.
     *
     * @param dcMenu the dcMenu to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dcMenu,
     * or with status 400 (Bad Request) if the dcMenu is not valid,
     * or with status 500 (Internal Server Error) if the dcMenu couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dc-menus")
    @Timed
    public ResponseEntity<DcMenu> updateDcMenu(@RequestBody DcMenu dcMenu) throws URISyntaxException {
        log.debug("REST request to update DcMenu : {}", dcMenu);
        if (dcMenu.getId() == null) {
            return createDcMenu(dcMenu);
        }

        DcMenu dcMenuOld = dcMenuRepository.findOne(dcMenu.getId());
        dcMenuOld.setName(dcMenu.getName());
        dcMenuOld.setDescription(dcMenu.getDescription());
        dcMenuOld.setIsEnable(dcMenu.isEnable());
        dcMenuOld.setParentId(dcMenu.getParentId());
        dcMenuOld.setLevel(dcMenu.getLevel());
        dcMenuOld.setUrl(dcMenu.getUrl());
        dcMenuOld.setUpdateTime(System.currentTimeMillis());

        DcMenu result = dcMenuRepository.save(dcMenuOld);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dcMenu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dc-menus : get all the dcMenus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dcMenus in body
     */
    @GetMapping("/dc-menus")
    @Timed
    public List<DcMenu> getAllDcMenus() {
        log.debug("REST request to get all DcMenus");
        log.info(SecurityUtils.getCurrentUserLogin().get());
        return dcMenuRepository.findAll();
    }

    /**
     * GET  /dc-menus/:id : get the "id" dcMenu.
     *
     * @param id the id of the dcMenu to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dcMenu, or with status 404 (Not Found)
     */
    @GetMapping("/dc-menus/{id}")
    @Timed
    public ResponseEntity<DcMenu> getDcMenu(@PathVariable Long id) {
        log.debug("REST request to get DcMenu : {}", id);
        DcMenu dcMenu = dcMenuRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dcMenu));
    }

    /**
     * DELETE  /dc-menus/:id : delete the "id" dcMenu.
     *
     * @param id the id of the dcMenu to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dc-menus/{id}")
    @Timed
    public ResponseEntity<Void> deleteDcMenu(@PathVariable Long id) {
        log.debug("REST request to delete DcMenu : {}", id);
        dcMenuRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

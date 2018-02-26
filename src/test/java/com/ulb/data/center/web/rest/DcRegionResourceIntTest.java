package com.ulb.data.center.web.rest;

import com.ulb.data.center.DataCenterApp;

import com.ulb.data.center.domain.DcRegion;
import com.ulb.data.center.repository.DcRegionRepository;
import com.ulb.data.center.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ulb.data.center.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DcRegionResource REST controller.
 *
 * @see DcRegionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCenterApp.class)
public class DcRegionResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_REGION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REGION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REGION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_REGION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT = "BBBBBBBBBB";

    private static final String DEFAULT_ADCODE = "AAAAAAAAAA";
    private static final String UPDATED_ADCODE = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIP_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    @Autowired
    private DcRegionRepository dcRegionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDcRegionMockMvc;

    private DcRegion dcRegion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DcRegionResource dcRegionResource = new DcRegionResource(dcRegionRepository);
        this.restDcRegionMockMvc = MockMvcBuilders.standaloneSetup(dcRegionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DcRegion createEntity(EntityManager em) {
        DcRegion dcRegion = new DcRegion()
            .code(DEFAULT_CODE)
            .regionName(DEFAULT_REGION_NAME)
            .regionCode(DEFAULT_REGION_CODE)
            .province(DEFAULT_PROVINCE)
            .city(DEFAULT_CITY)
            .district(DEFAULT_DISTRICT)
            .adcode(DEFAULT_ADCODE)
            .zipCode(DEFAULT_ZIP_CODE)
            .level(DEFAULT_LEVEL)
            .parentId(DEFAULT_PARENT_ID);
        return dcRegion;
    }

    @Before
    public void initTest() {
        dcRegion = createEntity(em);
    }

    @Test
    @Transactional
    public void createDcRegion() throws Exception {
        int databaseSizeBeforeCreate = dcRegionRepository.findAll().size();

        // Create the DcRegion
        restDcRegionMockMvc.perform(post("/api/dc-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dcRegion)))
            .andExpect(status().isCreated());

        // Validate the DcRegion in the database
        List<DcRegion> dcRegionList = dcRegionRepository.findAll();
        assertThat(dcRegionList).hasSize(databaseSizeBeforeCreate + 1);
        DcRegion testDcRegion = dcRegionList.get(dcRegionList.size() - 1);
        assertThat(testDcRegion.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDcRegion.getRegionName()).isEqualTo(DEFAULT_REGION_NAME);
        assertThat(testDcRegion.getRegionCode()).isEqualTo(DEFAULT_REGION_CODE);
        assertThat(testDcRegion.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testDcRegion.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testDcRegion.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testDcRegion.getAdcode()).isEqualTo(DEFAULT_ADCODE);
        assertThat(testDcRegion.getZipCode()).isEqualTo(DEFAULT_ZIP_CODE);
        assertThat(testDcRegion.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testDcRegion.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
    }

    @Test
    @Transactional
    public void createDcRegionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dcRegionRepository.findAll().size();

        // Create the DcRegion with an existing ID
        dcRegion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDcRegionMockMvc.perform(post("/api/dc-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dcRegion)))
            .andExpect(status().isBadRequest());

        // Validate the DcRegion in the database
        List<DcRegion> dcRegionList = dcRegionRepository.findAll();
        assertThat(dcRegionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDcRegions() throws Exception {
        // Initialize the database
        dcRegionRepository.saveAndFlush(dcRegion);

        // Get all the dcRegionList
        restDcRegionMockMvc.perform(get("/api/dc-regions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dcRegion.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].regionName").value(hasItem(DEFAULT_REGION_NAME.toString())))
            .andExpect(jsonPath("$.[*].regionCode").value(hasItem(DEFAULT_REGION_CODE.toString())))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT.toString())))
            .andExpect(jsonPath("$.[*].adcode").value(hasItem(DEFAULT_ADCODE.toString())))
            .andExpect(jsonPath("$.[*].zipCode").value(hasItem(DEFAULT_ZIP_CODE.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())));
    }

    @Test
    @Transactional
    public void getDcRegion() throws Exception {
        // Initialize the database
        dcRegionRepository.saveAndFlush(dcRegion);

        // Get the dcRegion
        restDcRegionMockMvc.perform(get("/api/dc-regions/{id}", dcRegion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dcRegion.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.regionName").value(DEFAULT_REGION_NAME.toString()))
            .andExpect(jsonPath("$.regionCode").value(DEFAULT_REGION_CODE.toString()))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT.toString()))
            .andExpect(jsonPath("$.adcode").value(DEFAULT_ADCODE.toString()))
            .andExpect(jsonPath("$.zipCode").value(DEFAULT_ZIP_CODE.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDcRegion() throws Exception {
        // Get the dcRegion
        restDcRegionMockMvc.perform(get("/api/dc-regions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDcRegion() throws Exception {
        // Initialize the database
        dcRegionRepository.saveAndFlush(dcRegion);
        int databaseSizeBeforeUpdate = dcRegionRepository.findAll().size();

        // Update the dcRegion
        DcRegion updatedDcRegion = dcRegionRepository.findOne(dcRegion.getId());
        // Disconnect from session so that the updates on updatedDcRegion are not directly saved in db
        em.detach(updatedDcRegion);
        updatedDcRegion
            .code(UPDATED_CODE)
            .regionName(UPDATED_REGION_NAME)
            .regionCode(UPDATED_REGION_CODE)
            .province(UPDATED_PROVINCE)
            .city(UPDATED_CITY)
            .district(UPDATED_DISTRICT)
            .adcode(UPDATED_ADCODE)
            .zipCode(UPDATED_ZIP_CODE)
            .level(UPDATED_LEVEL)
            .parentId(UPDATED_PARENT_ID);

        restDcRegionMockMvc.perform(put("/api/dc-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDcRegion)))
            .andExpect(status().isOk());

        // Validate the DcRegion in the database
        List<DcRegion> dcRegionList = dcRegionRepository.findAll();
        assertThat(dcRegionList).hasSize(databaseSizeBeforeUpdate);
        DcRegion testDcRegion = dcRegionList.get(dcRegionList.size() - 1);
        assertThat(testDcRegion.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDcRegion.getRegionName()).isEqualTo(UPDATED_REGION_NAME);
        assertThat(testDcRegion.getRegionCode()).isEqualTo(UPDATED_REGION_CODE);
        assertThat(testDcRegion.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testDcRegion.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDcRegion.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testDcRegion.getAdcode()).isEqualTo(UPDATED_ADCODE);
        assertThat(testDcRegion.getZipCode()).isEqualTo(UPDATED_ZIP_CODE);
        assertThat(testDcRegion.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testDcRegion.getParentId()).isEqualTo(UPDATED_PARENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingDcRegion() throws Exception {
        int databaseSizeBeforeUpdate = dcRegionRepository.findAll().size();

        // Create the DcRegion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDcRegionMockMvc.perform(put("/api/dc-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dcRegion)))
            .andExpect(status().isCreated());

        // Validate the DcRegion in the database
        List<DcRegion> dcRegionList = dcRegionRepository.findAll();
        assertThat(dcRegionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDcRegion() throws Exception {
        // Initialize the database
        dcRegionRepository.saveAndFlush(dcRegion);
        int databaseSizeBeforeDelete = dcRegionRepository.findAll().size();

        // Get the dcRegion
        restDcRegionMockMvc.perform(delete("/api/dc-regions/{id}", dcRegion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DcRegion> dcRegionList = dcRegionRepository.findAll();
        assertThat(dcRegionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DcRegion.class);
        DcRegion dcRegion1 = new DcRegion();
        dcRegion1.setId(1L);
        DcRegion dcRegion2 = new DcRegion();
        dcRegion2.setId(dcRegion1.getId());
        assertThat(dcRegion1).isEqualTo(dcRegion2);
        dcRegion2.setId(2L);
        assertThat(dcRegion1).isNotEqualTo(dcRegion2);
        dcRegion1.setId(null);
        assertThat(dcRegion1).isNotEqualTo(dcRegion2);
    }
}

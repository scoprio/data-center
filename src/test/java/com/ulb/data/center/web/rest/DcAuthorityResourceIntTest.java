package com.ulb.data.center.web.rest;

import com.ulb.data.center.DataCenterApp;

import com.ulb.data.center.domain.DcAuthority;
import com.ulb.data.center.repository.DcAuthorityRepository;
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
 * Test class for the DcAuthorityResource REST controller.
 *
 * @see DcAuthorityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCenterApp.class)
public class DcAuthorityResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_START_DATE = 1L;
    private static final Long UPDATED_START_DATE = 2L;

    private static final Long DEFAULT_END_DATE = 1L;
    private static final Long UPDATED_END_DATE = 2L;

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    @Autowired
    private DcAuthorityRepository dcAuthorityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDcAuthorityMockMvc;

    private DcAuthority dcAuthority;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DcAuthorityResource dcAuthorityResource = new DcAuthorityResource(dcAuthorityRepository);
        this.restDcAuthorityMockMvc = MockMvcBuilders.standaloneSetup(dcAuthorityResource)
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
    public static DcAuthority createEntity(EntityManager em) {
        DcAuthority dcAuthority = new DcAuthority()
            .name(DEFAULT_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .level(DEFAULT_LEVEL);
        return dcAuthority;
    }

    @Before
    public void initTest() {
        dcAuthority = createEntity(em);
    }

    @Test
    @Transactional
    public void createDcAuthority() throws Exception {
        int databaseSizeBeforeCreate = dcAuthorityRepository.findAll().size();

        // Create the DcAuthority
        restDcAuthorityMockMvc.perform(post("/api/dc-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dcAuthority)))
            .andExpect(status().isCreated());

        // Validate the DcAuthority in the database
        List<DcAuthority> dcAuthorityList = dcAuthorityRepository.findAll();
        assertThat(dcAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        DcAuthority testDcAuthority = dcAuthorityList.get(dcAuthorityList.size() - 1);
        assertThat(testDcAuthority.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDcAuthority.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testDcAuthority.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testDcAuthority.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createDcAuthorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dcAuthorityRepository.findAll().size();

        // Create the DcAuthority with an existing ID
        dcAuthority.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDcAuthorityMockMvc.perform(post("/api/dc-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dcAuthority)))
            .andExpect(status().isBadRequest());

        // Validate the DcAuthority in the database
        List<DcAuthority> dcAuthorityList = dcAuthorityRepository.findAll();
        assertThat(dcAuthorityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDcAuthorities() throws Exception {
        // Initialize the database
        dcAuthorityRepository.saveAndFlush(dcAuthority);

        // Get all the dcAuthorityList
        restDcAuthorityMockMvc.perform(get("/api/dc-authorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dcAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.intValue())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }

    @Test
    @Transactional
    public void getDcAuthority() throws Exception {
        // Initialize the database
        dcAuthorityRepository.saveAndFlush(dcAuthority);

        // Get the dcAuthority
        restDcAuthorityMockMvc.perform(get("/api/dc-authorities/{id}", dcAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dcAuthority.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.intValue()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL));
    }

    @Test
    @Transactional
    public void getNonExistingDcAuthority() throws Exception {
        // Get the dcAuthority
        restDcAuthorityMockMvc.perform(get("/api/dc-authorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDcAuthority() throws Exception {
        // Initialize the database
        dcAuthorityRepository.saveAndFlush(dcAuthority);
        int databaseSizeBeforeUpdate = dcAuthorityRepository.findAll().size();

        // Update the dcAuthority
        DcAuthority updatedDcAuthority = dcAuthorityRepository.findOne(dcAuthority.getId());
        // Disconnect from session so that the updates on updatedDcAuthority are not directly saved in db
        em.detach(updatedDcAuthority);
        updatedDcAuthority
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .level(UPDATED_LEVEL);

        restDcAuthorityMockMvc.perform(put("/api/dc-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDcAuthority)))
            .andExpect(status().isOk());

        // Validate the DcAuthority in the database
        List<DcAuthority> dcAuthorityList = dcAuthorityRepository.findAll();
        assertThat(dcAuthorityList).hasSize(databaseSizeBeforeUpdate);
        DcAuthority testDcAuthority = dcAuthorityList.get(dcAuthorityList.size() - 1);
        assertThat(testDcAuthority.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDcAuthority.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testDcAuthority.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testDcAuthority.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingDcAuthority() throws Exception {
        int databaseSizeBeforeUpdate = dcAuthorityRepository.findAll().size();

        // Create the DcAuthority

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDcAuthorityMockMvc.perform(put("/api/dc-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dcAuthority)))
            .andExpect(status().isCreated());

        // Validate the DcAuthority in the database
        List<DcAuthority> dcAuthorityList = dcAuthorityRepository.findAll();
        assertThat(dcAuthorityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDcAuthority() throws Exception {
        // Initialize the database
        dcAuthorityRepository.saveAndFlush(dcAuthority);
        int databaseSizeBeforeDelete = dcAuthorityRepository.findAll().size();

        // Get the dcAuthority
        restDcAuthorityMockMvc.perform(delete("/api/dc-authorities/{id}", dcAuthority.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DcAuthority> dcAuthorityList = dcAuthorityRepository.findAll();
        assertThat(dcAuthorityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DcAuthority.class);
        DcAuthority dcAuthority1 = new DcAuthority();
        dcAuthority1.setId(1L);
        DcAuthority dcAuthority2 = new DcAuthority();
        dcAuthority2.setId(dcAuthority1.getId());
        assertThat(dcAuthority1).isEqualTo(dcAuthority2);
        dcAuthority2.setId(2L);
        assertThat(dcAuthority1).isNotEqualTo(dcAuthority2);
        dcAuthority1.setId(null);
        assertThat(dcAuthority1).isNotEqualTo(dcAuthority2);
    }
}

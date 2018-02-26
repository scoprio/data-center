package com.ulb.data.center.web.rest;

import com.ulb.data.center.DataCenterApp;

import com.ulb.data.center.domain.DcDepartment;
import com.ulb.data.center.repository.DcDepartmentRepository;
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
 * Test class for the DcDepartmentResource REST controller.
 *
 * @see DcDepartmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCenterApp.class)
public class DcDepartmentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_CREATE_TIME = 1L;
    private static final Long UPDATED_CREATE_TIME = 2L;

    private static final Long DEFAULT_UPDATE_TIME = 1L;
    private static final Long UPDATED_UPDATE_TIME = 2L;

    private static final Boolean DEFAULT_IS_ENABLE = false;
    private static final Boolean UPDATED_IS_ENABLE = true;

    @Autowired
    private DcDepartmentRepository dcDepartmentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDcDepartmentMockMvc;

    private DcDepartment dcDepartment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DcDepartmentResource dcDepartmentResource = new DcDepartmentResource(dcDepartmentRepository);
        this.restDcDepartmentMockMvc = MockMvcBuilders.standaloneSetup(dcDepartmentResource)
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
    public static DcDepartment createEntity(EntityManager em) {
        DcDepartment dcDepartment = new DcDepartment()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .isEnable(DEFAULT_IS_ENABLE);
        return dcDepartment;
    }

    @Before
    public void initTest() {
        dcDepartment = createEntity(em);
    }

    @Test
    @Transactional
    public void createDcDepartment() throws Exception {
        int databaseSizeBeforeCreate = dcDepartmentRepository.findAll().size();

        // Create the DcDepartment
        restDcDepartmentMockMvc.perform(post("/api/dc-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dcDepartment)))
            .andExpect(status().isCreated());

        // Validate the DcDepartment in the database
        List<DcDepartment> dcDepartmentList = dcDepartmentRepository.findAll();
        assertThat(dcDepartmentList).hasSize(databaseSizeBeforeCreate + 1);
        DcDepartment testDcDepartment = dcDepartmentList.get(dcDepartmentList.size() - 1);
        assertThat(testDcDepartment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDcDepartment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDcDepartment.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testDcDepartment.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testDcDepartment.isEnable()).isEqualTo(DEFAULT_IS_ENABLE);
    }

    @Test
    @Transactional
    public void createDcDepartmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dcDepartmentRepository.findAll().size();

        // Create the DcDepartment with an existing ID
        dcDepartment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDcDepartmentMockMvc.perform(post("/api/dc-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dcDepartment)))
            .andExpect(status().isBadRequest());

        // Validate the DcDepartment in the database
        List<DcDepartment> dcDepartmentList = dcDepartmentRepository.findAll();
        assertThat(dcDepartmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDcDepartments() throws Exception {
        // Initialize the database
        dcDepartmentRepository.saveAndFlush(dcDepartment);

        // Get all the dcDepartmentList
        restDcDepartmentMockMvc.perform(get("/api/dc-departments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dcDepartment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.intValue())))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getDcDepartment() throws Exception {
        // Initialize the database
        dcDepartmentRepository.saveAndFlush(dcDepartment);

        // Get the dcDepartment
        restDcDepartmentMockMvc.perform(get("/api/dc-departments/{id}", dcDepartment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dcDepartment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.intValue()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.intValue()))
            .andExpect(jsonPath("$.isEnable").value(DEFAULT_IS_ENABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDcDepartment() throws Exception {
        // Get the dcDepartment
        restDcDepartmentMockMvc.perform(get("/api/dc-departments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDcDepartment() throws Exception {
        // Initialize the database
        dcDepartmentRepository.saveAndFlush(dcDepartment);
        int databaseSizeBeforeUpdate = dcDepartmentRepository.findAll().size();

        // Update the dcDepartment
        DcDepartment updatedDcDepartment = dcDepartmentRepository.findOne(dcDepartment.getId());
        // Disconnect from session so that the updates on updatedDcDepartment are not directly saved in db
        em.detach(updatedDcDepartment);
        updatedDcDepartment
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .isEnable(UPDATED_IS_ENABLE);

        restDcDepartmentMockMvc.perform(put("/api/dc-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDcDepartment)))
            .andExpect(status().isOk());

        // Validate the DcDepartment in the database
        List<DcDepartment> dcDepartmentList = dcDepartmentRepository.findAll();
        assertThat(dcDepartmentList).hasSize(databaseSizeBeforeUpdate);
        DcDepartment testDcDepartment = dcDepartmentList.get(dcDepartmentList.size() - 1);
        assertThat(testDcDepartment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDcDepartment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDcDepartment.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testDcDepartment.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testDcDepartment.isEnable()).isEqualTo(UPDATED_IS_ENABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingDcDepartment() throws Exception {
        int databaseSizeBeforeUpdate = dcDepartmentRepository.findAll().size();

        // Create the DcDepartment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDcDepartmentMockMvc.perform(put("/api/dc-departments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dcDepartment)))
            .andExpect(status().isCreated());

        // Validate the DcDepartment in the database
        List<DcDepartment> dcDepartmentList = dcDepartmentRepository.findAll();
        assertThat(dcDepartmentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDcDepartment() throws Exception {
        // Initialize the database
        dcDepartmentRepository.saveAndFlush(dcDepartment);
        int databaseSizeBeforeDelete = dcDepartmentRepository.findAll().size();

        // Get the dcDepartment
        restDcDepartmentMockMvc.perform(delete("/api/dc-departments/{id}", dcDepartment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DcDepartment> dcDepartmentList = dcDepartmentRepository.findAll();
        assertThat(dcDepartmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DcDepartment.class);
        DcDepartment dcDepartment1 = new DcDepartment();
        dcDepartment1.setId(1L);
        DcDepartment dcDepartment2 = new DcDepartment();
        dcDepartment2.setId(dcDepartment1.getId());
        assertThat(dcDepartment1).isEqualTo(dcDepartment2);
        dcDepartment2.setId(2L);
        assertThat(dcDepartment1).isNotEqualTo(dcDepartment2);
        dcDepartment1.setId(null);
        assertThat(dcDepartment1).isNotEqualTo(dcDepartment2);
    }
}

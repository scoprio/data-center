package com.ulb.data.center.web.rest;

import com.ulb.data.center.DataCenterApp;

import com.ulb.data.center.domain.DcMenu;
import com.ulb.data.center.repository.DcMenuRepository;
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
 * Test class for the DcMenuResource REST controller.
 *
 * @see DcMenuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCenterApp.class)
public class DcMenuResourceIntTest {

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

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private DcMenuRepository dcMenuRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDcMenuMockMvc;

    private DcMenu dcMenu;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DcMenuResource dcMenuResource = new DcMenuResource(dcMenuRepository);
        this.restDcMenuMockMvc = MockMvcBuilders.standaloneSetup(dcMenuResource)
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
    public static DcMenu createEntity(EntityManager em) {
        DcMenu dcMenu = new DcMenu()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .isEnable(DEFAULT_IS_ENABLE)
            .parentId(DEFAULT_PARENT_ID)
            .level(DEFAULT_LEVEL)
            .url(DEFAULT_URL);
        return dcMenu;
    }

    @Before
    public void initTest() {
        dcMenu = createEntity(em);
    }

    @Test
    @Transactional
    public void createDcMenu() throws Exception {
        int databaseSizeBeforeCreate = dcMenuRepository.findAll().size();

        // Create the DcMenu
        restDcMenuMockMvc.perform(post("/api/dc-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dcMenu)))
            .andExpect(status().isCreated());

        // Validate the DcMenu in the database
        List<DcMenu> dcMenuList = dcMenuRepository.findAll();
        assertThat(dcMenuList).hasSize(databaseSizeBeforeCreate + 1);
        DcMenu testDcMenu = dcMenuList.get(dcMenuList.size() - 1);
        assertThat(testDcMenu.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDcMenu.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDcMenu.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testDcMenu.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testDcMenu.isEnable()).isEqualTo(DEFAULT_IS_ENABLE);
        assertThat(testDcMenu.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testDcMenu.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testDcMenu.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createDcMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dcMenuRepository.findAll().size();

        // Create the DcMenu with an existing ID
        dcMenu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDcMenuMockMvc.perform(post("/api/dc-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dcMenu)))
            .andExpect(status().isBadRequest());

        // Validate the DcMenu in the database
        List<DcMenu> dcMenuList = dcMenuRepository.findAll();
        assertThat(dcMenuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDcMenus() throws Exception {
        // Initialize the database
        dcMenuRepository.saveAndFlush(dcMenu);

        // Get all the dcMenuList
        restDcMenuMockMvc.perform(get("/api/dc-menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dcMenu.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.intValue())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.intValue())))
            .andExpect(jsonPath("$.[*].isEnable").value(hasItem(DEFAULT_IS_ENABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    @Test
    @Transactional
    public void getDcMenu() throws Exception {
        // Initialize the database
        dcMenuRepository.saveAndFlush(dcMenu);

        // Get the dcMenu
        restDcMenuMockMvc.perform(get("/api/dc-menus/{id}", dcMenu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dcMenu.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.intValue()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.intValue()))
            .andExpect(jsonPath("$.isEnable").value(DEFAULT_IS_ENABLE.booleanValue()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDcMenu() throws Exception {
        // Get the dcMenu
        restDcMenuMockMvc.perform(get("/api/dc-menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDcMenu() throws Exception {
        // Initialize the database
        dcMenuRepository.saveAndFlush(dcMenu);
        int databaseSizeBeforeUpdate = dcMenuRepository.findAll().size();

        // Update the dcMenu
        DcMenu updatedDcMenu = dcMenuRepository.findOne(dcMenu.getId());
        // Disconnect from session so that the updates on updatedDcMenu are not directly saved in db
        em.detach(updatedDcMenu);
        updatedDcMenu
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME)
            .isEnable(UPDATED_IS_ENABLE)
            .parentId(UPDATED_PARENT_ID)
            .level(UPDATED_LEVEL)
            .url(UPDATED_URL);

        restDcMenuMockMvc.perform(put("/api/dc-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDcMenu)))
            .andExpect(status().isOk());

        // Validate the DcMenu in the database
        List<DcMenu> dcMenuList = dcMenuRepository.findAll();
        assertThat(dcMenuList).hasSize(databaseSizeBeforeUpdate);
        DcMenu testDcMenu = dcMenuList.get(dcMenuList.size() - 1);
        assertThat(testDcMenu.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDcMenu.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDcMenu.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testDcMenu.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testDcMenu.isEnable()).isEqualTo(UPDATED_IS_ENABLE);
        assertThat(testDcMenu.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testDcMenu.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testDcMenu.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingDcMenu() throws Exception {
        int databaseSizeBeforeUpdate = dcMenuRepository.findAll().size();

        // Create the DcMenu

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDcMenuMockMvc.perform(put("/api/dc-menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dcMenu)))
            .andExpect(status().isCreated());

        // Validate the DcMenu in the database
        List<DcMenu> dcMenuList = dcMenuRepository.findAll();
        assertThat(dcMenuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDcMenu() throws Exception {
        // Initialize the database
        dcMenuRepository.saveAndFlush(dcMenu);
        int databaseSizeBeforeDelete = dcMenuRepository.findAll().size();

        // Get the dcMenu
        restDcMenuMockMvc.perform(delete("/api/dc-menus/{id}", dcMenu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DcMenu> dcMenuList = dcMenuRepository.findAll();
        assertThat(dcMenuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DcMenu.class);
        DcMenu dcMenu1 = new DcMenu();
        dcMenu1.setId(1L);
        DcMenu dcMenu2 = new DcMenu();
        dcMenu2.setId(dcMenu1.getId());
        assertThat(dcMenu1).isEqualTo(dcMenu2);
        dcMenu2.setId(2L);
        assertThat(dcMenu1).isNotEqualTo(dcMenu2);
        dcMenu1.setId(null);
        assertThat(dcMenu1).isNotEqualTo(dcMenu2);
    }
}

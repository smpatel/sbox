package com.sbox.web.rest;

import com.sbox.SboxApp;

import com.sbox.domain.CommissionGroup;
import com.sbox.repository.CommissionGroupRepository;
import com.sbox.service.dto.CommissionGroupDTO;
import com.sbox.service.mapper.CommissionGroupMapper;
import com.sbox.web.rest.errors.ExceptionTranslator;

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

import static com.sbox.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CommissionGroupResource REST controller.
 *
 * @see CommissionGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SboxApp.class)
public class CommissionGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_COMMISSION = 1D;
    private static final Double UPDATED_COMMISSION = 2D;

    private static final String DEFAULT_AUTHORITY = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORITY = "BBBBBBBBBB";

    @Autowired
    private CommissionGroupRepository commissionGroupRepository;

    @Autowired
    private CommissionGroupMapper commissionGroupMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommissionGroupMockMvc;

    private CommissionGroup commissionGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommissionGroupResource commissionGroupResource = new CommissionGroupResource(commissionGroupRepository, commissionGroupMapper);
        this.restCommissionGroupMockMvc = MockMvcBuilders.standaloneSetup(commissionGroupResource)
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
    public static CommissionGroup createEntity(EntityManager em) {
        CommissionGroup commissionGroup = new CommissionGroup()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .commission(DEFAULT_COMMISSION)
            .authority(DEFAULT_AUTHORITY);
        return commissionGroup;
    }

    @Before
    public void initTest() {
        commissionGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommissionGroup() throws Exception {
        int databaseSizeBeforeCreate = commissionGroupRepository.findAll().size();

        // Create the CommissionGroup
        CommissionGroupDTO commissionGroupDTO = commissionGroupMapper.toDto(commissionGroup);
        restCommissionGroupMockMvc.perform(post("/api/commission-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissionGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the CommissionGroup in the database
        List<CommissionGroup> commissionGroupList = commissionGroupRepository.findAll();
        assertThat(commissionGroupList).hasSize(databaseSizeBeforeCreate + 1);
        CommissionGroup testCommissionGroup = commissionGroupList.get(commissionGroupList.size() - 1);
        assertThat(testCommissionGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCommissionGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCommissionGroup.getCommission()).isEqualTo(DEFAULT_COMMISSION);
        assertThat(testCommissionGroup.getAuthority()).isEqualTo(DEFAULT_AUTHORITY);
    }

    @Test
    @Transactional
    public void createCommissionGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commissionGroupRepository.findAll().size();

        // Create the CommissionGroup with an existing ID
        commissionGroup.setId(1L);
        CommissionGroupDTO commissionGroupDTO = commissionGroupMapper.toDto(commissionGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommissionGroupMockMvc.perform(post("/api/commission-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissionGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommissionGroup in the database
        List<CommissionGroup> commissionGroupList = commissionGroupRepository.findAll();
        assertThat(commissionGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = commissionGroupRepository.findAll().size();
        // set the field null
        commissionGroup.setName(null);

        // Create the CommissionGroup, which fails.
        CommissionGroupDTO commissionGroupDTO = commissionGroupMapper.toDto(commissionGroup);

        restCommissionGroupMockMvc.perform(post("/api/commission-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissionGroupDTO)))
            .andExpect(status().isBadRequest());

        List<CommissionGroup> commissionGroupList = commissionGroupRepository.findAll();
        assertThat(commissionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = commissionGroupRepository.findAll().size();
        // set the field null
        commissionGroup.setDescription(null);

        // Create the CommissionGroup, which fails.
        CommissionGroupDTO commissionGroupDTO = commissionGroupMapper.toDto(commissionGroup);

        restCommissionGroupMockMvc.perform(post("/api/commission-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissionGroupDTO)))
            .andExpect(status().isBadRequest());

        List<CommissionGroup> commissionGroupList = commissionGroupRepository.findAll();
        assertThat(commissionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommissionGroups() throws Exception {
        // Initialize the database
        commissionGroupRepository.saveAndFlush(commissionGroup);

        // Get all the commissionGroupList
        restCommissionGroupMockMvc.perform(get("/api/commission-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commissionGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].authority").value(hasItem(DEFAULT_AUTHORITY.toString())));
    }

    @Test
    @Transactional
    public void getCommissionGroup() throws Exception {
        // Initialize the database
        commissionGroupRepository.saveAndFlush(commissionGroup);

        // Get the commissionGroup
        restCommissionGroupMockMvc.perform(get("/api/commission-groups/{id}", commissionGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commissionGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.commission").value(DEFAULT_COMMISSION.doubleValue()))
            .andExpect(jsonPath("$.authority").value(DEFAULT_AUTHORITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommissionGroup() throws Exception {
        // Get the commissionGroup
        restCommissionGroupMockMvc.perform(get("/api/commission-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommissionGroup() throws Exception {
        // Initialize the database
        commissionGroupRepository.saveAndFlush(commissionGroup);
        int databaseSizeBeforeUpdate = commissionGroupRepository.findAll().size();

        // Update the commissionGroup
        CommissionGroup updatedCommissionGroup = commissionGroupRepository.findOne(commissionGroup.getId());
        // Disconnect from session so that the updates on updatedCommissionGroup are not directly saved in db
        em.detach(updatedCommissionGroup);
        updatedCommissionGroup
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .commission(UPDATED_COMMISSION)
            .authority(UPDATED_AUTHORITY);
        CommissionGroupDTO commissionGroupDTO = commissionGroupMapper.toDto(updatedCommissionGroup);

        restCommissionGroupMockMvc.perform(put("/api/commission-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissionGroupDTO)))
            .andExpect(status().isOk());

        // Validate the CommissionGroup in the database
        List<CommissionGroup> commissionGroupList = commissionGroupRepository.findAll();
        assertThat(commissionGroupList).hasSize(databaseSizeBeforeUpdate);
        CommissionGroup testCommissionGroup = commissionGroupList.get(commissionGroupList.size() - 1);
        assertThat(testCommissionGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCommissionGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCommissionGroup.getCommission()).isEqualTo(UPDATED_COMMISSION);
        assertThat(testCommissionGroup.getAuthority()).isEqualTo(UPDATED_AUTHORITY);
    }

    @Test
    @Transactional
    public void updateNonExistingCommissionGroup() throws Exception {
        int databaseSizeBeforeUpdate = commissionGroupRepository.findAll().size();

        // Create the CommissionGroup
        CommissionGroupDTO commissionGroupDTO = commissionGroupMapper.toDto(commissionGroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommissionGroupMockMvc.perform(put("/api/commission-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissionGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the CommissionGroup in the database
        List<CommissionGroup> commissionGroupList = commissionGroupRepository.findAll();
        assertThat(commissionGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommissionGroup() throws Exception {
        // Initialize the database
        commissionGroupRepository.saveAndFlush(commissionGroup);
        int databaseSizeBeforeDelete = commissionGroupRepository.findAll().size();

        // Get the commissionGroup
        restCommissionGroupMockMvc.perform(delete("/api/commission-groups/{id}", commissionGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommissionGroup> commissionGroupList = commissionGroupRepository.findAll();
        assertThat(commissionGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommissionGroup.class);
        CommissionGroup commissionGroup1 = new CommissionGroup();
        commissionGroup1.setId(1L);
        CommissionGroup commissionGroup2 = new CommissionGroup();
        commissionGroup2.setId(commissionGroup1.getId());
        assertThat(commissionGroup1).isEqualTo(commissionGroup2);
        commissionGroup2.setId(2L);
        assertThat(commissionGroup1).isNotEqualTo(commissionGroup2);
        commissionGroup1.setId(null);
        assertThat(commissionGroup1).isNotEqualTo(commissionGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommissionGroupDTO.class);
        CommissionGroupDTO commissionGroupDTO1 = new CommissionGroupDTO();
        commissionGroupDTO1.setId(1L);
        CommissionGroupDTO commissionGroupDTO2 = new CommissionGroupDTO();
        assertThat(commissionGroupDTO1).isNotEqualTo(commissionGroupDTO2);
        commissionGroupDTO2.setId(commissionGroupDTO1.getId());
        assertThat(commissionGroupDTO1).isEqualTo(commissionGroupDTO2);
        commissionGroupDTO2.setId(2L);
        assertThat(commissionGroupDTO1).isNotEqualTo(commissionGroupDTO2);
        commissionGroupDTO1.setId(null);
        assertThat(commissionGroupDTO1).isNotEqualTo(commissionGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(commissionGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(commissionGroupMapper.fromId(null)).isNull();
    }
}

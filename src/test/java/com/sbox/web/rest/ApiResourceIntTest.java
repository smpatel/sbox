package com.sbox.web.rest;

import com.sbox.SboxApp;

import com.sbox.domain.Api;
import com.sbox.repository.ApiRepository;
import com.sbox.service.dto.ApiDTO;
import com.sbox.service.mapper.ApiMapper;
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
 * Test class for the ApiResource REST controller.
 *
 * @see ApiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SboxApp.class)
public class ApiResourceIntTest {

    private static final Boolean DEFAULT_ENABLE = false;
    private static final Boolean UPDATED_ENABLE = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RECHARGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_RECHARGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_BALANCE_URL = "AAAAAAAAAA";
    private static final String UPDATED_BALANCE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_URL = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SUCCESS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SUCCESS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FAIL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_FAIL_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_API_COMMISSION = 1D;
    private static final Double UPDATED_API_COMMISSION = 2D;

    @Autowired
    private ApiRepository apiRepository;

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApiMockMvc;

    private Api api;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApiResource apiResource = new ApiResource(apiRepository, apiMapper);
        this.restApiMockMvc = MockMvcBuilders.standaloneSetup(apiResource)
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
    public static Api createEntity(EntityManager em) {
        Api api = new Api()
            .enable(DEFAULT_ENABLE)
            .name(DEFAULT_NAME)
            .rechargeUrl(DEFAULT_RECHARGE_URL)
            .balanceUrl(DEFAULT_BALANCE_URL)
            .statusUrl(DEFAULT_STATUS_URL)
            .successCode(DEFAULT_SUCCESS_CODE)
            .failCode(DEFAULT_FAIL_CODE)
            .apiCommission(DEFAULT_API_COMMISSION);
        return api;
    }

    @Before
    public void initTest() {
        api = createEntity(em);
    }

    @Test
    @Transactional
    public void createApi() throws Exception {
        int databaseSizeBeforeCreate = apiRepository.findAll().size();

        // Create the Api
        ApiDTO apiDTO = apiMapper.toDto(api);
        restApiMockMvc.perform(post("/api/apis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isCreated());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeCreate + 1);
        Api testApi = apiList.get(apiList.size() - 1);
        assertThat(testApi.isEnable()).isEqualTo(DEFAULT_ENABLE);
        assertThat(testApi.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApi.getRechargeUrl()).isEqualTo(DEFAULT_RECHARGE_URL);
        assertThat(testApi.getBalanceUrl()).isEqualTo(DEFAULT_BALANCE_URL);
        assertThat(testApi.getStatusUrl()).isEqualTo(DEFAULT_STATUS_URL);
        assertThat(testApi.getSuccessCode()).isEqualTo(DEFAULT_SUCCESS_CODE);
        assertThat(testApi.getFailCode()).isEqualTo(DEFAULT_FAIL_CODE);
        assertThat(testApi.getApiCommission()).isEqualTo(DEFAULT_API_COMMISSION);
    }

    @Test
    @Transactional
    public void createApiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiRepository.findAll().size();

        // Create the Api with an existing ID
        api.setId(1L);
        ApiDTO apiDTO = apiMapper.toDto(api);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiMockMvc.perform(post("/api/apis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiRepository.findAll().size();
        // set the field null
        api.setName(null);

        // Create the Api, which fails.
        ApiDTO apiDTO = apiMapper.toDto(api);

        restApiMockMvc.perform(post("/api/apis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isBadRequest());

        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRechargeUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiRepository.findAll().size();
        // set the field null
        api.setRechargeUrl(null);

        // Create the Api, which fails.
        ApiDTO apiDTO = apiMapper.toDto(api);

        restApiMockMvc.perform(post("/api/apis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isBadRequest());

        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApis() throws Exception {
        // Initialize the database
        apiRepository.saveAndFlush(api);

        // Get all the apiList
        restApiMockMvc.perform(get("/api/apis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(api.getId().intValue())))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].rechargeUrl").value(hasItem(DEFAULT_RECHARGE_URL.toString())))
            .andExpect(jsonPath("$.[*].balanceUrl").value(hasItem(DEFAULT_BALANCE_URL.toString())))
            .andExpect(jsonPath("$.[*].statusUrl").value(hasItem(DEFAULT_STATUS_URL.toString())))
            .andExpect(jsonPath("$.[*].successCode").value(hasItem(DEFAULT_SUCCESS_CODE.toString())))
            .andExpect(jsonPath("$.[*].failCode").value(hasItem(DEFAULT_FAIL_CODE.toString())))
            .andExpect(jsonPath("$.[*].apiCommission").value(hasItem(DEFAULT_API_COMMISSION.doubleValue())));
    }

    @Test
    @Transactional
    public void getApi() throws Exception {
        // Initialize the database
        apiRepository.saveAndFlush(api);

        // Get the api
        restApiMockMvc.perform(get("/api/apis/{id}", api.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(api.getId().intValue()))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.rechargeUrl").value(DEFAULT_RECHARGE_URL.toString()))
            .andExpect(jsonPath("$.balanceUrl").value(DEFAULT_BALANCE_URL.toString()))
            .andExpect(jsonPath("$.statusUrl").value(DEFAULT_STATUS_URL.toString()))
            .andExpect(jsonPath("$.successCode").value(DEFAULT_SUCCESS_CODE.toString()))
            .andExpect(jsonPath("$.failCode").value(DEFAULT_FAIL_CODE.toString()))
            .andExpect(jsonPath("$.apiCommission").value(DEFAULT_API_COMMISSION.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingApi() throws Exception {
        // Get the api
        restApiMockMvc.perform(get("/api/apis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApi() throws Exception {
        // Initialize the database
        apiRepository.saveAndFlush(api);
        int databaseSizeBeforeUpdate = apiRepository.findAll().size();

        // Update the api
        Api updatedApi = apiRepository.findOne(api.getId());
        // Disconnect from session so that the updates on updatedApi are not directly saved in db
        em.detach(updatedApi);
        updatedApi
            .enable(UPDATED_ENABLE)
            .name(UPDATED_NAME)
            .rechargeUrl(UPDATED_RECHARGE_URL)
            .balanceUrl(UPDATED_BALANCE_URL)
            .statusUrl(UPDATED_STATUS_URL)
            .successCode(UPDATED_SUCCESS_CODE)
            .failCode(UPDATED_FAIL_CODE)
            .apiCommission(UPDATED_API_COMMISSION);
        ApiDTO apiDTO = apiMapper.toDto(updatedApi);

        restApiMockMvc.perform(put("/api/apis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isOk());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeUpdate);
        Api testApi = apiList.get(apiList.size() - 1);
        assertThat(testApi.isEnable()).isEqualTo(UPDATED_ENABLE);
        assertThat(testApi.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApi.getRechargeUrl()).isEqualTo(UPDATED_RECHARGE_URL);
        assertThat(testApi.getBalanceUrl()).isEqualTo(UPDATED_BALANCE_URL);
        assertThat(testApi.getStatusUrl()).isEqualTo(UPDATED_STATUS_URL);
        assertThat(testApi.getSuccessCode()).isEqualTo(UPDATED_SUCCESS_CODE);
        assertThat(testApi.getFailCode()).isEqualTo(UPDATED_FAIL_CODE);
        assertThat(testApi.getApiCommission()).isEqualTo(UPDATED_API_COMMISSION);
    }

    @Test
    @Transactional
    public void updateNonExistingApi() throws Exception {
        int databaseSizeBeforeUpdate = apiRepository.findAll().size();

        // Create the Api
        ApiDTO apiDTO = apiMapper.toDto(api);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApiMockMvc.perform(put("/api/apis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apiDTO)))
            .andExpect(status().isCreated());

        // Validate the Api in the database
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteApi() throws Exception {
        // Initialize the database
        apiRepository.saveAndFlush(api);
        int databaseSizeBeforeDelete = apiRepository.findAll().size();

        // Get the api
        restApiMockMvc.perform(delete("/api/apis/{id}", api.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Api> apiList = apiRepository.findAll();
        assertThat(apiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Api.class);
        Api api1 = new Api();
        api1.setId(1L);
        Api api2 = new Api();
        api2.setId(api1.getId());
        assertThat(api1).isEqualTo(api2);
        api2.setId(2L);
        assertThat(api1).isNotEqualTo(api2);
        api1.setId(null);
        assertThat(api1).isNotEqualTo(api2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApiDTO.class);
        ApiDTO apiDTO1 = new ApiDTO();
        apiDTO1.setId(1L);
        ApiDTO apiDTO2 = new ApiDTO();
        assertThat(apiDTO1).isNotEqualTo(apiDTO2);
        apiDTO2.setId(apiDTO1.getId());
        assertThat(apiDTO1).isEqualTo(apiDTO2);
        apiDTO2.setId(2L);
        assertThat(apiDTO1).isNotEqualTo(apiDTO2);
        apiDTO1.setId(null);
        assertThat(apiDTO1).isNotEqualTo(apiDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(apiMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(apiMapper.fromId(null)).isNull();
    }
}

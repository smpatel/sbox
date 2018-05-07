package com.sbox.web.rest;

import com.sbox.SboxApp;

import com.sbox.domain.UserAddon;
import com.sbox.repository.UserAddonRepository;
import com.sbox.service.UserAddonService;
import com.sbox.service.dto.UserAddonDTO;
import com.sbox.service.mapper.UserAddonMapper;
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
 * Test class for the UserAddonResource REST controller.
 *
 * @see UserAddonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SboxApp.class)
public class UserAddonResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final Long DEFAULT_PARENT_USER_ID = 1L;
    private static final Long UPDATED_PARENT_USER_ID = 2L;

    private static final String DEFAULT_SECURITY_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_SECURITY_PASSWORD = "BBBBBBBBBB";

    private static final Long DEFAULT_AUTHORIZED_MOBILE = 1L;
    private static final Long UPDATED_AUTHORIZED_MOBILE = 2L;

    private static final Long DEFAULT_SECONDARY_MOBILE = 1L;
    private static final Long UPDATED_SECONDARY_MOBILE = 2L;

    private static final Integer DEFAULT_ACC_OPEN_FEE = 1;
    private static final Integer UPDATED_ACC_OPEN_FEE = 2;

    private static final Integer DEFAULT_DAILY_RENTAL = 1;
    private static final Integer UPDATED_DAILY_RENTAL = 2;

    private static final Integer DEFAULT_MINIMUM_BALANCE = 1;
    private static final Integer UPDATED_MINIMUM_BALANCE = 2;

    private static final Double DEFAULT_FLAT_COMMISSION = 1D;
    private static final Double UPDATED_FLAT_COMMISSION = 2D;

    private static final String DEFAULT_API_URL = "AAAAAAAAAA";
    private static final String UPDATED_API_URL = "BBBBBBBBBB";

    private static final String DEFAULT_API_RESPONSE_URL = "AAAAAAAAAA";
    private static final String UPDATED_API_RESPONSE_URL = "BBBBBBBBBB";

    @Autowired
    private UserAddonRepository userAddonRepository;

    @Autowired
    private UserAddonMapper userAddonMapper;

    @Autowired
    private UserAddonService userAddonService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserAddonMockMvc;

    private UserAddon userAddon;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserAddonResource userAddonResource = new UserAddonResource(userAddonService);
        this.restUserAddonMockMvc = MockMvcBuilders.standaloneSetup(userAddonResource)
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
    public static UserAddon createEntity(EntityManager em) {
        UserAddon userAddon = new UserAddon()
            .name(DEFAULT_NAME)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .email(DEFAULT_EMAIL)
            .activated(DEFAULT_ACTIVATED)
            .parentUserId(DEFAULT_PARENT_USER_ID)
            .securityPassword(DEFAULT_SECURITY_PASSWORD)
            .authorizedMobile(DEFAULT_AUTHORIZED_MOBILE)
            .secondaryMobile(DEFAULT_SECONDARY_MOBILE)
            .accOpenFee(DEFAULT_ACC_OPEN_FEE)
            .dailyRental(DEFAULT_DAILY_RENTAL)
            .minimumBalance(DEFAULT_MINIMUM_BALANCE)
            .flatCommission(DEFAULT_FLAT_COMMISSION)
            .apiUrl(DEFAULT_API_URL)
            .apiResponseUrl(DEFAULT_API_RESPONSE_URL);
        return userAddon;
    }

    @Before
    public void initTest() {
        userAddon = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAddon() throws Exception {
        int databaseSizeBeforeCreate = userAddonRepository.findAll().size();

        // Create the UserAddon
        UserAddonDTO userAddonDTO = userAddonMapper.toDto(userAddon);
        restUserAddonMockMvc.perform(post("/api/user-addons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddonDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAddon in the database
        List<UserAddon> userAddonList = userAddonRepository.findAll();
        assertThat(userAddonList).hasSize(databaseSizeBeforeCreate + 1);
        UserAddon testUserAddon = userAddonList.get(userAddonList.size() - 1);
        assertThat(testUserAddon.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserAddon.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUserAddon.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUserAddon.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserAddon.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testUserAddon.getParentUserId()).isEqualTo(DEFAULT_PARENT_USER_ID);
        assertThat(testUserAddon.getSecurityPassword()).isEqualTo(DEFAULT_SECURITY_PASSWORD);
        assertThat(testUserAddon.getAuthorizedMobile()).isEqualTo(DEFAULT_AUTHORIZED_MOBILE);
        assertThat(testUserAddon.getSecondaryMobile()).isEqualTo(DEFAULT_SECONDARY_MOBILE);
        assertThat(testUserAddon.getAccOpenFee()).isEqualTo(DEFAULT_ACC_OPEN_FEE);
        assertThat(testUserAddon.getDailyRental()).isEqualTo(DEFAULT_DAILY_RENTAL);
        assertThat(testUserAddon.getMinimumBalance()).isEqualTo(DEFAULT_MINIMUM_BALANCE);
        assertThat(testUserAddon.getFlatCommission()).isEqualTo(DEFAULT_FLAT_COMMISSION);
        assertThat(testUserAddon.getApiUrl()).isEqualTo(DEFAULT_API_URL);
        assertThat(testUserAddon.getApiResponseUrl()).isEqualTo(DEFAULT_API_RESPONSE_URL);
    }

    @Test
    @Transactional
    public void createUserAddonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAddonRepository.findAll().size();

        // Create the UserAddon with an existing ID
        userAddon.setId(1L);
        UserAddonDTO userAddonDTO = userAddonMapper.toDto(userAddon);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAddonMockMvc.perform(post("/api/user-addons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAddon in the database
        List<UserAddon> userAddonList = userAddonRepository.findAll();
        assertThat(userAddonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAddonRepository.findAll().size();
        // set the field null
        userAddon.setName(null);

        // Create the UserAddon, which fails.
        UserAddonDTO userAddonDTO = userAddonMapper.toDto(userAddon);

        restUserAddonMockMvc.perform(post("/api/user-addons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddonDTO)))
            .andExpect(status().isBadRequest());

        List<UserAddon> userAddonList = userAddonRepository.findAll();
        assertThat(userAddonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAddonRepository.findAll().size();
        // set the field null
        userAddon.setUsername(null);

        // Create the UserAddon, which fails.
        UserAddonDTO userAddonDTO = userAddonMapper.toDto(userAddon);

        restUserAddonMockMvc.perform(post("/api/user-addons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddonDTO)))
            .andExpect(status().isBadRequest());

        List<UserAddon> userAddonList = userAddonRepository.findAll();
        assertThat(userAddonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAddonRepository.findAll().size();
        // set the field null
        userAddon.setPassword(null);

        // Create the UserAddon, which fails.
        UserAddonDTO userAddonDTO = userAddonMapper.toDto(userAddon);

        restUserAddonMockMvc.perform(post("/api/user-addons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddonDTO)))
            .andExpect(status().isBadRequest());

        List<UserAddon> userAddonList = userAddonRepository.findAll();
        assertThat(userAddonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAddonRepository.findAll().size();
        // set the field null
        userAddon.setEmail(null);

        // Create the UserAddon, which fails.
        UserAddonDTO userAddonDTO = userAddonMapper.toDto(userAddon);

        restUserAddonMockMvc.perform(post("/api/user-addons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddonDTO)))
            .andExpect(status().isBadRequest());

        List<UserAddon> userAddonList = userAddonRepository.findAll();
        assertThat(userAddonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParentUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAddonRepository.findAll().size();
        // set the field null
        userAddon.setParentUserId(null);

        // Create the UserAddon, which fails.
        UserAddonDTO userAddonDTO = userAddonMapper.toDto(userAddon);

        restUserAddonMockMvc.perform(post("/api/user-addons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddonDTO)))
            .andExpect(status().isBadRequest());

        List<UserAddon> userAddonList = userAddonRepository.findAll();
        assertThat(userAddonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAuthorizedMobileIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAddonRepository.findAll().size();
        // set the field null
        userAddon.setAuthorizedMobile(null);

        // Create the UserAddon, which fails.
        UserAddonDTO userAddonDTO = userAddonMapper.toDto(userAddon);

        restUserAddonMockMvc.perform(post("/api/user-addons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddonDTO)))
            .andExpect(status().isBadRequest());

        List<UserAddon> userAddonList = userAddonRepository.findAll();
        assertThat(userAddonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserAddons() throws Exception {
        // Initialize the database
        userAddonRepository.saveAndFlush(userAddon);

        // Get all the userAddonList
        restUserAddonMockMvc.perform(get("/api/user-addons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAddon.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].parentUserId").value(hasItem(DEFAULT_PARENT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].securityPassword").value(hasItem(DEFAULT_SECURITY_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].authorizedMobile").value(hasItem(DEFAULT_AUTHORIZED_MOBILE.intValue())))
            .andExpect(jsonPath("$.[*].secondaryMobile").value(hasItem(DEFAULT_SECONDARY_MOBILE.intValue())))
            .andExpect(jsonPath("$.[*].accOpenFee").value(hasItem(DEFAULT_ACC_OPEN_FEE)))
            .andExpect(jsonPath("$.[*].dailyRental").value(hasItem(DEFAULT_DAILY_RENTAL)))
            .andExpect(jsonPath("$.[*].minimumBalance").value(hasItem(DEFAULT_MINIMUM_BALANCE)))
            .andExpect(jsonPath("$.[*].flatCommission").value(hasItem(DEFAULT_FLAT_COMMISSION.doubleValue())))
            .andExpect(jsonPath("$.[*].apiUrl").value(hasItem(DEFAULT_API_URL.toString())))
            .andExpect(jsonPath("$.[*].apiResponseUrl").value(hasItem(DEFAULT_API_RESPONSE_URL.toString())));
    }

    @Test
    @Transactional
    public void getUserAddon() throws Exception {
        // Initialize the database
        userAddonRepository.saveAndFlush(userAddon);

        // Get the userAddon
        restUserAddonMockMvc.perform(get("/api/user-addons/{id}", userAddon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userAddon.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.parentUserId").value(DEFAULT_PARENT_USER_ID.intValue()))
            .andExpect(jsonPath("$.securityPassword").value(DEFAULT_SECURITY_PASSWORD.toString()))
            .andExpect(jsonPath("$.authorizedMobile").value(DEFAULT_AUTHORIZED_MOBILE.intValue()))
            .andExpect(jsonPath("$.secondaryMobile").value(DEFAULT_SECONDARY_MOBILE.intValue()))
            .andExpect(jsonPath("$.accOpenFee").value(DEFAULT_ACC_OPEN_FEE))
            .andExpect(jsonPath("$.dailyRental").value(DEFAULT_DAILY_RENTAL))
            .andExpect(jsonPath("$.minimumBalance").value(DEFAULT_MINIMUM_BALANCE))
            .andExpect(jsonPath("$.flatCommission").value(DEFAULT_FLAT_COMMISSION.doubleValue()))
            .andExpect(jsonPath("$.apiUrl").value(DEFAULT_API_URL.toString()))
            .andExpect(jsonPath("$.apiResponseUrl").value(DEFAULT_API_RESPONSE_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserAddon() throws Exception {
        // Get the userAddon
        restUserAddonMockMvc.perform(get("/api/user-addons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAddon() throws Exception {
        // Initialize the database
        userAddonRepository.saveAndFlush(userAddon);
        int databaseSizeBeforeUpdate = userAddonRepository.findAll().size();

        // Update the userAddon
        UserAddon updatedUserAddon = userAddonRepository.findOne(userAddon.getId());
        // Disconnect from session so that the updates on updatedUserAddon are not directly saved in db
        em.detach(updatedUserAddon);
        updatedUserAddon
            .name(UPDATED_NAME)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .activated(UPDATED_ACTIVATED)
            .parentUserId(UPDATED_PARENT_USER_ID)
            .securityPassword(UPDATED_SECURITY_PASSWORD)
            .authorizedMobile(UPDATED_AUTHORIZED_MOBILE)
            .secondaryMobile(UPDATED_SECONDARY_MOBILE)
            .accOpenFee(UPDATED_ACC_OPEN_FEE)
            .dailyRental(UPDATED_DAILY_RENTAL)
            .minimumBalance(UPDATED_MINIMUM_BALANCE)
            .flatCommission(UPDATED_FLAT_COMMISSION)
            .apiUrl(UPDATED_API_URL)
            .apiResponseUrl(UPDATED_API_RESPONSE_URL);
        UserAddonDTO userAddonDTO = userAddonMapper.toDto(updatedUserAddon);

        restUserAddonMockMvc.perform(put("/api/user-addons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddonDTO)))
            .andExpect(status().isOk());

        // Validate the UserAddon in the database
        List<UserAddon> userAddonList = userAddonRepository.findAll();
        assertThat(userAddonList).hasSize(databaseSizeBeforeUpdate);
        UserAddon testUserAddon = userAddonList.get(userAddonList.size() - 1);
        assertThat(testUserAddon.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserAddon.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUserAddon.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserAddon.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserAddon.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testUserAddon.getParentUserId()).isEqualTo(UPDATED_PARENT_USER_ID);
        assertThat(testUserAddon.getSecurityPassword()).isEqualTo(UPDATED_SECURITY_PASSWORD);
        assertThat(testUserAddon.getAuthorizedMobile()).isEqualTo(UPDATED_AUTHORIZED_MOBILE);
        assertThat(testUserAddon.getSecondaryMobile()).isEqualTo(UPDATED_SECONDARY_MOBILE);
        assertThat(testUserAddon.getAccOpenFee()).isEqualTo(UPDATED_ACC_OPEN_FEE);
        assertThat(testUserAddon.getDailyRental()).isEqualTo(UPDATED_DAILY_RENTAL);
        assertThat(testUserAddon.getMinimumBalance()).isEqualTo(UPDATED_MINIMUM_BALANCE);
        assertThat(testUserAddon.getFlatCommission()).isEqualTo(UPDATED_FLAT_COMMISSION);
        assertThat(testUserAddon.getApiUrl()).isEqualTo(UPDATED_API_URL);
        assertThat(testUserAddon.getApiResponseUrl()).isEqualTo(UPDATED_API_RESPONSE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAddon() throws Exception {
        int databaseSizeBeforeUpdate = userAddonRepository.findAll().size();

        // Create the UserAddon
        UserAddonDTO userAddonDTO = userAddonMapper.toDto(userAddon);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserAddonMockMvc.perform(put("/api/user-addons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAddonDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAddon in the database
        List<UserAddon> userAddonList = userAddonRepository.findAll();
        assertThat(userAddonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserAddon() throws Exception {
        // Initialize the database
        userAddonRepository.saveAndFlush(userAddon);
        int databaseSizeBeforeDelete = userAddonRepository.findAll().size();

        // Get the userAddon
        restUserAddonMockMvc.perform(delete("/api/user-addons/{id}", userAddon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserAddon> userAddonList = userAddonRepository.findAll();
        assertThat(userAddonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAddon.class);
        UserAddon userAddon1 = new UserAddon();
        userAddon1.setId(1L);
        UserAddon userAddon2 = new UserAddon();
        userAddon2.setId(userAddon1.getId());
        assertThat(userAddon1).isEqualTo(userAddon2);
        userAddon2.setId(2L);
        assertThat(userAddon1).isNotEqualTo(userAddon2);
        userAddon1.setId(null);
        assertThat(userAddon1).isNotEqualTo(userAddon2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAddonDTO.class);
        UserAddonDTO userAddonDTO1 = new UserAddonDTO();
        userAddonDTO1.setId(1L);
        UserAddonDTO userAddonDTO2 = new UserAddonDTO();
        assertThat(userAddonDTO1).isNotEqualTo(userAddonDTO2);
        userAddonDTO2.setId(userAddonDTO1.getId());
        assertThat(userAddonDTO1).isEqualTo(userAddonDTO2);
        userAddonDTO2.setId(2L);
        assertThat(userAddonDTO1).isNotEqualTo(userAddonDTO2);
        userAddonDTO1.setId(null);
        assertThat(userAddonDTO1).isNotEqualTo(userAddonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userAddonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userAddonMapper.fromId(null)).isNull();
    }
}

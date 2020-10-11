package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SignatureApplicationApp;
import com.mycompany.myapp.domain.SignatureLevel;
import com.mycompany.myapp.repository.SignatureLevelRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SignatureLevelResource} REST controller.
 */
@SpringBootTest(classes = SignatureApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SignatureLevelResourceIT {

    private static final String DEFAULT_ORG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORG_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORG_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL_1 = 1;
    private static final Integer UPDATED_LEVEL_1 = 2;

    private static final Integer DEFAULT_LEVEL_2 = 1;
    private static final Integer UPDATED_LEVEL_2 = 2;

    private static final Integer DEFAULT_LEVEL_3 = 1;
    private static final Integer UPDATED_LEVEL_3 = 2;

    private static final Boolean DEFAULT_COMPLETED = false;
    private static final Boolean UPDATED_COMPLETED = true;

    @Autowired
    private SignatureLevelRepository signatureLevelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSignatureLevelMockMvc;

    private SignatureLevel signatureLevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SignatureLevel createEntity(EntityManager em) {
        SignatureLevel signatureLevel = new SignatureLevel()
            .orgName(DEFAULT_ORG_NAME)
            .orgId(DEFAULT_ORG_ID)
            .level1(DEFAULT_LEVEL_1)
            .level2(DEFAULT_LEVEL_2)
            .level3(DEFAULT_LEVEL_3)
            .completed(DEFAULT_COMPLETED);
        return signatureLevel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SignatureLevel createUpdatedEntity(EntityManager em) {
        SignatureLevel signatureLevel = new SignatureLevel()
            .orgName(UPDATED_ORG_NAME)
            .orgId(UPDATED_ORG_ID)
            .level1(UPDATED_LEVEL_1)
            .level2(UPDATED_LEVEL_2)
            .level3(UPDATED_LEVEL_3)
            .completed(UPDATED_COMPLETED);
        return signatureLevel;
    }

    @BeforeEach
    public void initTest() {
        signatureLevel = createEntity(em);
    }

    @Test
    @Transactional
    public void createSignatureLevel() throws Exception {
        int databaseSizeBeforeCreate = signatureLevelRepository.findAll().size();
        // Create the SignatureLevel
        restSignatureLevelMockMvc.perform(post("/api/signature-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signatureLevel)))
            .andExpect(status().isCreated());

        // Validate the SignatureLevel in the database
        List<SignatureLevel> signatureLevelList = signatureLevelRepository.findAll();
        assertThat(signatureLevelList).hasSize(databaseSizeBeforeCreate + 1);
        SignatureLevel testSignatureLevel = signatureLevelList.get(signatureLevelList.size() - 1);
        assertThat(testSignatureLevel.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testSignatureLevel.getOrgId()).isEqualTo(DEFAULT_ORG_ID);
        assertThat(testSignatureLevel.getLevel1()).isEqualTo(DEFAULT_LEVEL_1);
        assertThat(testSignatureLevel.getLevel2()).isEqualTo(DEFAULT_LEVEL_2);
        assertThat(testSignatureLevel.getLevel3()).isEqualTo(DEFAULT_LEVEL_3);
        assertThat(testSignatureLevel.isCompleted()).isEqualTo(DEFAULT_COMPLETED);
    }

    @Test
    @Transactional
    public void createSignatureLevelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = signatureLevelRepository.findAll().size();

        // Create the SignatureLevel with an existing ID
        signatureLevel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSignatureLevelMockMvc.perform(post("/api/signature-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signatureLevel)))
            .andExpect(status().isBadRequest());

        // Validate the SignatureLevel in the database
        List<SignatureLevel> signatureLevelList = signatureLevelRepository.findAll();
        assertThat(signatureLevelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSignatureLevels() throws Exception {
        // Initialize the database
        signatureLevelRepository.saveAndFlush(signatureLevel);

        // Get all the signatureLevelList
        restSignatureLevelMockMvc.perform(get("/api/signature-levels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(signatureLevel.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME)))
            .andExpect(jsonPath("$.[*].orgId").value(hasItem(DEFAULT_ORG_ID)))
            .andExpect(jsonPath("$.[*].level1").value(hasItem(DEFAULT_LEVEL_1)))
            .andExpect(jsonPath("$.[*].level2").value(hasItem(DEFAULT_LEVEL_2)))
            .andExpect(jsonPath("$.[*].level3").value(hasItem(DEFAULT_LEVEL_3)))
            .andExpect(jsonPath("$.[*].completed").value(hasItem(DEFAULT_COMPLETED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSignatureLevel() throws Exception {
        // Initialize the database
        signatureLevelRepository.saveAndFlush(signatureLevel);

        // Get the signatureLevel
        restSignatureLevelMockMvc.perform(get("/api/signature-levels/{id}", signatureLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(signatureLevel.getId().intValue()))
            .andExpect(jsonPath("$.orgName").value(DEFAULT_ORG_NAME))
            .andExpect(jsonPath("$.orgId").value(DEFAULT_ORG_ID))
            .andExpect(jsonPath("$.level1").value(DEFAULT_LEVEL_1))
            .andExpect(jsonPath("$.level2").value(DEFAULT_LEVEL_2))
            .andExpect(jsonPath("$.level3").value(DEFAULT_LEVEL_3))
            .andExpect(jsonPath("$.completed").value(DEFAULT_COMPLETED.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSignatureLevel() throws Exception {
        // Get the signatureLevel
        restSignatureLevelMockMvc.perform(get("/api/signature-levels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSignatureLevel() throws Exception {
        // Initialize the database
        signatureLevelRepository.saveAndFlush(signatureLevel);

        int databaseSizeBeforeUpdate = signatureLevelRepository.findAll().size();

        // Update the signatureLevel
        SignatureLevel updatedSignatureLevel = signatureLevelRepository.findById(signatureLevel.getId()).get();
        // Disconnect from session so that the updates on updatedSignatureLevel are not directly saved in db
        em.detach(updatedSignatureLevel);
        updatedSignatureLevel
            .orgName(UPDATED_ORG_NAME)
            .orgId(UPDATED_ORG_ID)
            .level1(UPDATED_LEVEL_1)
            .level2(UPDATED_LEVEL_2)
            .level3(UPDATED_LEVEL_3)
            .completed(UPDATED_COMPLETED);

        restSignatureLevelMockMvc.perform(put("/api/signature-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSignatureLevel)))
            .andExpect(status().isOk());

        // Validate the SignatureLevel in the database
        List<SignatureLevel> signatureLevelList = signatureLevelRepository.findAll();
        assertThat(signatureLevelList).hasSize(databaseSizeBeforeUpdate);
        SignatureLevel testSignatureLevel = signatureLevelList.get(signatureLevelList.size() - 1);
        assertThat(testSignatureLevel.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testSignatureLevel.getOrgId()).isEqualTo(UPDATED_ORG_ID);
        assertThat(testSignatureLevel.getLevel1()).isEqualTo(UPDATED_LEVEL_1);
        assertThat(testSignatureLevel.getLevel2()).isEqualTo(UPDATED_LEVEL_2);
        assertThat(testSignatureLevel.getLevel3()).isEqualTo(UPDATED_LEVEL_3);
        assertThat(testSignatureLevel.isCompleted()).isEqualTo(UPDATED_COMPLETED);
    }

    @Test
    @Transactional
    public void updateNonExistingSignatureLevel() throws Exception {
        int databaseSizeBeforeUpdate = signatureLevelRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSignatureLevelMockMvc.perform(put("/api/signature-levels").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(signatureLevel)))
            .andExpect(status().isBadRequest());

        // Validate the SignatureLevel in the database
        List<SignatureLevel> signatureLevelList = signatureLevelRepository.findAll();
        assertThat(signatureLevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSignatureLevel() throws Exception {
        // Initialize the database
        signatureLevelRepository.saveAndFlush(signatureLevel);

        int databaseSizeBeforeDelete = signatureLevelRepository.findAll().size();

        // Delete the signatureLevel
        restSignatureLevelMockMvc.perform(delete("/api/signature-levels/{id}", signatureLevel.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SignatureLevel> signatureLevelList = signatureLevelRepository.findAll();
        assertThat(signatureLevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

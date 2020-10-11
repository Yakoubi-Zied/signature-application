package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.SignatureApplicationApp;
import com.mycompany.myapp.domain.Status;
import com.mycompany.myapp.repository.StatusRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.StatusEnum;
/**
 * Integration tests for the {@link StatusResource} REST controller.
 */
@SpringBootTest(classes = SignatureApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StatusResourceIT {

    private static final StatusEnum DEFAULT_STATUS = StatusEnum.DONE;
    private static final StatusEnum UPDATED_STATUS = StatusEnum.ON_GOING;

    private static final byte[] DEFAULT_EXCEPTION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EXCEPTION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EXCEPTION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EXCEPTION_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatusMockMvc;

    private Status status;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Status createEntity(EntityManager em) {
        Status status = new Status()
            .status(DEFAULT_STATUS)
            .exception(DEFAULT_EXCEPTION)
            .exceptionContentType(DEFAULT_EXCEPTION_CONTENT_TYPE)
            .month(DEFAULT_MONTH)
            .year(DEFAULT_YEAR);
        return status;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Status createUpdatedEntity(EntityManager em) {
        Status status = new Status()
            .status(UPDATED_STATUS)
            .exception(UPDATED_EXCEPTION)
            .exceptionContentType(UPDATED_EXCEPTION_CONTENT_TYPE)
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR);
        return status;
    }

    @BeforeEach
    public void initTest() {
        status = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatus() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();
        // Create the Status
        restStatusMockMvc.perform(post("/api/statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isCreated());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate + 1);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testStatus.getException()).isEqualTo(DEFAULT_EXCEPTION);
        assertThat(testStatus.getExceptionContentType()).isEqualTo(DEFAULT_EXCEPTION_CONTENT_TYPE);
        assertThat(testStatus.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testStatus.getYear()).isEqualTo(DEFAULT_YEAR);
    }

    @Test
    @Transactional
    public void createStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();

        // Create the Status with an existing ID
        status.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusMockMvc.perform(post("/api/statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStatuses() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get all the statusList
        restStatusMockMvc.perform(get("/api/statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(status.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].exceptionContentType").value(hasItem(DEFAULT_EXCEPTION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].exception").value(hasItem(Base64Utils.encodeToString(DEFAULT_EXCEPTION))))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)));
    }
    
    @Test
    @Transactional
    public void getStatus() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        // Get the status
        restStatusMockMvc.perform(get("/api/statuses/{id}", status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(status.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.exceptionContentType").value(DEFAULT_EXCEPTION_CONTENT_TYPE))
            .andExpect(jsonPath("$.exception").value(Base64Utils.encodeToString(DEFAULT_EXCEPTION)))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR));
    }
    @Test
    @Transactional
    public void getNonExistingStatus() throws Exception {
        // Get the status
        restStatusMockMvc.perform(get("/api/statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatus() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status
        Status updatedStatus = statusRepository.findById(status.getId()).get();
        // Disconnect from session so that the updates on updatedStatus are not directly saved in db
        em.detach(updatedStatus);
        updatedStatus
            .status(UPDATED_STATUS)
            .exception(UPDATED_EXCEPTION)
            .exceptionContentType(UPDATED_EXCEPTION_CONTENT_TYPE)
            .month(UPDATED_MONTH)
            .year(UPDATED_YEAR);

        restStatusMockMvc.perform(put("/api/statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatus)))
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testStatus.getException()).isEqualTo(UPDATED_EXCEPTION);
        assertThat(testStatus.getExceptionContentType()).isEqualTo(UPDATED_EXCEPTION_CONTENT_TYPE);
        assertThat(testStatus.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testStatus.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void updateNonExistingStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusMockMvc.perform(put("/api/statuses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStatus() throws Exception {
        // Initialize the database
        statusRepository.saveAndFlush(status);

        int databaseSizeBeforeDelete = statusRepository.findAll().size();

        // Delete the status
        restStatusMockMvc.perform(delete("/api/statuses/{id}", status.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

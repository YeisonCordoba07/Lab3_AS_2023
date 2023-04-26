package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.EstadoSemestre;
import com.udea.repository.EstadoSemestreRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EstadoSemestreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstadoSemestreResourceIT {

    private static final Integer DEFAULT_ID_ESTADO_SEMESTRE = 1;
    private static final Integer UPDATED_ID_ESTADO_SEMESTRE = 2;

    private static final String DEFAULT_STATE_SEMESTRE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_SEMESTRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estado-semestres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstadoSemestreRepository estadoSemestreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstadoSemestreMockMvc;

    private EstadoSemestre estadoSemestre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoSemestre createEntity(EntityManager em) {
        EstadoSemestre estadoSemestre = new EstadoSemestre()
            .idEstadoSemestre(DEFAULT_ID_ESTADO_SEMESTRE)
            .stateSemestre(DEFAULT_STATE_SEMESTRE);
        return estadoSemestre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoSemestre createUpdatedEntity(EntityManager em) {
        EstadoSemestre estadoSemestre = new EstadoSemestre()
            .idEstadoSemestre(UPDATED_ID_ESTADO_SEMESTRE)
            .stateSemestre(UPDATED_STATE_SEMESTRE);
        return estadoSemestre;
    }

    @BeforeEach
    public void initTest() {
        estadoSemestre = createEntity(em);
    }

    @Test
    @Transactional
    void createEstadoSemestre() throws Exception {
        int databaseSizeBeforeCreate = estadoSemestreRepository.findAll().size();
        // Create the EstadoSemestre
        restEstadoSemestreMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoSemestre))
            )
            .andExpect(status().isCreated());

        // Validate the EstadoSemestre in the database
        List<EstadoSemestre> estadoSemestreList = estadoSemestreRepository.findAll();
        assertThat(estadoSemestreList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoSemestre testEstadoSemestre = estadoSemestreList.get(estadoSemestreList.size() - 1);
        assertThat(testEstadoSemestre.getIdEstadoSemestre()).isEqualTo(DEFAULT_ID_ESTADO_SEMESTRE);
        assertThat(testEstadoSemestre.getStateSemestre()).isEqualTo(DEFAULT_STATE_SEMESTRE);
    }

    @Test
    @Transactional
    void createEstadoSemestreWithExistingId() throws Exception {
        // Create the EstadoSemestre with an existing ID
        estadoSemestre.setId(1L);

        int databaseSizeBeforeCreate = estadoSemestreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoSemestreMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoSemestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoSemestre in the database
        List<EstadoSemestre> estadoSemestreList = estadoSemestreRepository.findAll();
        assertThat(estadoSemestreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstadoSemestres() throws Exception {
        // Initialize the database
        estadoSemestreRepository.saveAndFlush(estadoSemestre);

        // Get all the estadoSemestreList
        restEstadoSemestreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoSemestre.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEstadoSemestre").value(hasItem(DEFAULT_ID_ESTADO_SEMESTRE)))
            .andExpect(jsonPath("$.[*].stateSemestre").value(hasItem(DEFAULT_STATE_SEMESTRE)));
    }

    @Test
    @Transactional
    void getEstadoSemestre() throws Exception {
        // Initialize the database
        estadoSemestreRepository.saveAndFlush(estadoSemestre);

        // Get the estadoSemestre
        restEstadoSemestreMockMvc
            .perform(get(ENTITY_API_URL_ID, estadoSemestre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadoSemestre.getId().intValue()))
            .andExpect(jsonPath("$.idEstadoSemestre").value(DEFAULT_ID_ESTADO_SEMESTRE))
            .andExpect(jsonPath("$.stateSemestre").value(DEFAULT_STATE_SEMESTRE));
    }

    @Test
    @Transactional
    void getNonExistingEstadoSemestre() throws Exception {
        // Get the estadoSemestre
        restEstadoSemestreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstadoSemestre() throws Exception {
        // Initialize the database
        estadoSemestreRepository.saveAndFlush(estadoSemestre);

        int databaseSizeBeforeUpdate = estadoSemestreRepository.findAll().size();

        // Update the estadoSemestre
        EstadoSemestre updatedEstadoSemestre = estadoSemestreRepository.findById(estadoSemestre.getId()).get();
        // Disconnect from session so that the updates on updatedEstadoSemestre are not directly saved in db
        em.detach(updatedEstadoSemestre);
        updatedEstadoSemestre.idEstadoSemestre(UPDATED_ID_ESTADO_SEMESTRE).stateSemestre(UPDATED_STATE_SEMESTRE);

        restEstadoSemestreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstadoSemestre.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEstadoSemestre))
            )
            .andExpect(status().isOk());

        // Validate the EstadoSemestre in the database
        List<EstadoSemestre> estadoSemestreList = estadoSemestreRepository.findAll();
        assertThat(estadoSemestreList).hasSize(databaseSizeBeforeUpdate);
        EstadoSemestre testEstadoSemestre = estadoSemestreList.get(estadoSemestreList.size() - 1);
        assertThat(testEstadoSemestre.getIdEstadoSemestre()).isEqualTo(UPDATED_ID_ESTADO_SEMESTRE);
        assertThat(testEstadoSemestre.getStateSemestre()).isEqualTo(UPDATED_STATE_SEMESTRE);
    }

    @Test
    @Transactional
    void putNonExistingEstadoSemestre() throws Exception {
        int databaseSizeBeforeUpdate = estadoSemestreRepository.findAll().size();
        estadoSemestre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoSemestreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadoSemestre.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoSemestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoSemestre in the database
        List<EstadoSemestre> estadoSemestreList = estadoSemestreRepository.findAll();
        assertThat(estadoSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstadoSemestre() throws Exception {
        int databaseSizeBeforeUpdate = estadoSemestreRepository.findAll().size();
        estadoSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoSemestreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoSemestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoSemestre in the database
        List<EstadoSemestre> estadoSemestreList = estadoSemestreRepository.findAll();
        assertThat(estadoSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstadoSemestre() throws Exception {
        int databaseSizeBeforeUpdate = estadoSemestreRepository.findAll().size();
        estadoSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoSemestreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoSemestre)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoSemestre in the database
        List<EstadoSemestre> estadoSemestreList = estadoSemestreRepository.findAll();
        assertThat(estadoSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstadoSemestreWithPatch() throws Exception {
        // Initialize the database
        estadoSemestreRepository.saveAndFlush(estadoSemestre);

        int databaseSizeBeforeUpdate = estadoSemestreRepository.findAll().size();

        // Update the estadoSemestre using partial update
        EstadoSemestre partialUpdatedEstadoSemestre = new EstadoSemestre();
        partialUpdatedEstadoSemestre.setId(estadoSemestre.getId());

        partialUpdatedEstadoSemestre.idEstadoSemestre(UPDATED_ID_ESTADO_SEMESTRE).stateSemestre(UPDATED_STATE_SEMESTRE);

        restEstadoSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoSemestre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstadoSemestre))
            )
            .andExpect(status().isOk());

        // Validate the EstadoSemestre in the database
        List<EstadoSemestre> estadoSemestreList = estadoSemestreRepository.findAll();
        assertThat(estadoSemestreList).hasSize(databaseSizeBeforeUpdate);
        EstadoSemestre testEstadoSemestre = estadoSemestreList.get(estadoSemestreList.size() - 1);
        assertThat(testEstadoSemestre.getIdEstadoSemestre()).isEqualTo(UPDATED_ID_ESTADO_SEMESTRE);
        assertThat(testEstadoSemestre.getStateSemestre()).isEqualTo(UPDATED_STATE_SEMESTRE);
    }

    @Test
    @Transactional
    void fullUpdateEstadoSemestreWithPatch() throws Exception {
        // Initialize the database
        estadoSemestreRepository.saveAndFlush(estadoSemestre);

        int databaseSizeBeforeUpdate = estadoSemestreRepository.findAll().size();

        // Update the estadoSemestre using partial update
        EstadoSemestre partialUpdatedEstadoSemestre = new EstadoSemestre();
        partialUpdatedEstadoSemestre.setId(estadoSemestre.getId());

        partialUpdatedEstadoSemestre.idEstadoSemestre(UPDATED_ID_ESTADO_SEMESTRE).stateSemestre(UPDATED_STATE_SEMESTRE);

        restEstadoSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoSemestre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstadoSemestre))
            )
            .andExpect(status().isOk());

        // Validate the EstadoSemestre in the database
        List<EstadoSemestre> estadoSemestreList = estadoSemestreRepository.findAll();
        assertThat(estadoSemestreList).hasSize(databaseSizeBeforeUpdate);
        EstadoSemestre testEstadoSemestre = estadoSemestreList.get(estadoSemestreList.size() - 1);
        assertThat(testEstadoSemestre.getIdEstadoSemestre()).isEqualTo(UPDATED_ID_ESTADO_SEMESTRE);
        assertThat(testEstadoSemestre.getStateSemestre()).isEqualTo(UPDATED_STATE_SEMESTRE);
    }

    @Test
    @Transactional
    void patchNonExistingEstadoSemestre() throws Exception {
        int databaseSizeBeforeUpdate = estadoSemestreRepository.findAll().size();
        estadoSemestre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estadoSemestre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoSemestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoSemestre in the database
        List<EstadoSemestre> estadoSemestreList = estadoSemestreRepository.findAll();
        assertThat(estadoSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstadoSemestre() throws Exception {
        int databaseSizeBeforeUpdate = estadoSemestreRepository.findAll().size();
        estadoSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoSemestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoSemestre in the database
        List<EstadoSemestre> estadoSemestreList = estadoSemestreRepository.findAll();
        assertThat(estadoSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstadoSemestre() throws Exception {
        int databaseSizeBeforeUpdate = estadoSemestreRepository.findAll().size();
        estadoSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(estadoSemestre))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoSemestre in the database
        List<EstadoSemestre> estadoSemestreList = estadoSemestreRepository.findAll();
        assertThat(estadoSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstadoSemestre() throws Exception {
        // Initialize the database
        estadoSemestreRepository.saveAndFlush(estadoSemestre);

        int databaseSizeBeforeDelete = estadoSemestreRepository.findAll().size();

        // Delete the estadoSemestre
        restEstadoSemestreMockMvc
            .perform(delete(ENTITY_API_URL_ID, estadoSemestre.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstadoSemestre> estadoSemestreList = estadoSemestreRepository.findAll();
        assertThat(estadoSemestreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

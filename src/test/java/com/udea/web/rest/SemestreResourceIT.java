package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.Semestre;
import com.udea.repository.SemestreRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link SemestreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SemestreResourceIT {

    private static final Integer DEFAULT_ID_SEMESTRE = 1;
    private static final Integer UPDATED_ID_SEMESTRE = 2;

    private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_TERMINACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_TERMINACION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_TYPE_SEMESTRE = 1;
    private static final Integer UPDATED_TYPE_SEMESTRE = 2;

    private static final Integer DEFAULT_STATE_SEMESTRE = 1;
    private static final Integer UPDATED_STATE_SEMESTRE = 2;

    private static final String ENTITY_API_URL = "/api/semestres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SemestreRepository semestreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSemestreMockMvc;

    private Semestre semestre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Semestre createEntity(EntityManager em) {
        Semestre semestre = new Semestre()
            .idSemestre(DEFAULT_ID_SEMESTRE)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaTerminacion(DEFAULT_FECHA_TERMINACION)
            .typeSemestre(DEFAULT_TYPE_SEMESTRE)
            .stateSemestre(DEFAULT_STATE_SEMESTRE);
        return semestre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Semestre createUpdatedEntity(EntityManager em) {
        Semestre semestre = new Semestre()
            .idSemestre(UPDATED_ID_SEMESTRE)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaTerminacion(UPDATED_FECHA_TERMINACION)
            .typeSemestre(UPDATED_TYPE_SEMESTRE)
            .stateSemestre(UPDATED_STATE_SEMESTRE);
        return semestre;
    }

    @BeforeEach
    public void initTest() {
        semestre = createEntity(em);
    }

    @Test
    @Transactional
    void createSemestre() throws Exception {
        int databaseSizeBeforeCreate = semestreRepository.findAll().size();
        // Create the Semestre
        restSemestreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(semestre)))
            .andExpect(status().isCreated());

        // Validate the Semestre in the database
        List<Semestre> semestreList = semestreRepository.findAll();
        assertThat(semestreList).hasSize(databaseSizeBeforeCreate + 1);
        Semestre testSemestre = semestreList.get(semestreList.size() - 1);
        assertThat(testSemestre.getIdSemestre()).isEqualTo(DEFAULT_ID_SEMESTRE);
        assertThat(testSemestre.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testSemestre.getFechaTerminacion()).isEqualTo(DEFAULT_FECHA_TERMINACION);
        assertThat(testSemestre.getTypeSemestre()).isEqualTo(DEFAULT_TYPE_SEMESTRE);
        assertThat(testSemestre.getStateSemestre()).isEqualTo(DEFAULT_STATE_SEMESTRE);
    }

    @Test
    @Transactional
    void createSemestreWithExistingId() throws Exception {
        // Create the Semestre with an existing ID
        semestre.setId(1L);

        int databaseSizeBeforeCreate = semestreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSemestreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(semestre)))
            .andExpect(status().isBadRequest());

        // Validate the Semestre in the database
        List<Semestre> semestreList = semestreRepository.findAll();
        assertThat(semestreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSemestres() throws Exception {
        // Initialize the database
        semestreRepository.saveAndFlush(semestre);

        // Get all the semestreList
        restSemestreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(semestre.getId().intValue())))
            .andExpect(jsonPath("$.[*].idSemestre").value(hasItem(DEFAULT_ID_SEMESTRE)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaTerminacion").value(hasItem(DEFAULT_FECHA_TERMINACION.toString())))
            .andExpect(jsonPath("$.[*].typeSemestre").value(hasItem(DEFAULT_TYPE_SEMESTRE)))
            .andExpect(jsonPath("$.[*].stateSemestre").value(hasItem(DEFAULT_STATE_SEMESTRE)));
    }

    @Test
    @Transactional
    void getSemestre() throws Exception {
        // Initialize the database
        semestreRepository.saveAndFlush(semestre);

        // Get the semestre
        restSemestreMockMvc
            .perform(get(ENTITY_API_URL_ID, semestre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(semestre.getId().intValue()))
            .andExpect(jsonPath("$.idSemestre").value(DEFAULT_ID_SEMESTRE))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaTerminacion").value(DEFAULT_FECHA_TERMINACION.toString()))
            .andExpect(jsonPath("$.typeSemestre").value(DEFAULT_TYPE_SEMESTRE))
            .andExpect(jsonPath("$.stateSemestre").value(DEFAULT_STATE_SEMESTRE));
    }

    @Test
    @Transactional
    void getNonExistingSemestre() throws Exception {
        // Get the semestre
        restSemestreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSemestre() throws Exception {
        // Initialize the database
        semestreRepository.saveAndFlush(semestre);

        int databaseSizeBeforeUpdate = semestreRepository.findAll().size();

        // Update the semestre
        Semestre updatedSemestre = semestreRepository.findById(semestre.getId()).get();
        // Disconnect from session so that the updates on updatedSemestre are not directly saved in db
        em.detach(updatedSemestre);
        updatedSemestre
            .idSemestre(UPDATED_ID_SEMESTRE)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaTerminacion(UPDATED_FECHA_TERMINACION)
            .typeSemestre(UPDATED_TYPE_SEMESTRE)
            .stateSemestre(UPDATED_STATE_SEMESTRE);

        restSemestreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSemestre.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSemestre))
            )
            .andExpect(status().isOk());

        // Validate the Semestre in the database
        List<Semestre> semestreList = semestreRepository.findAll();
        assertThat(semestreList).hasSize(databaseSizeBeforeUpdate);
        Semestre testSemestre = semestreList.get(semestreList.size() - 1);
        assertThat(testSemestre.getIdSemestre()).isEqualTo(UPDATED_ID_SEMESTRE);
        assertThat(testSemestre.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testSemestre.getFechaTerminacion()).isEqualTo(UPDATED_FECHA_TERMINACION);
        assertThat(testSemestre.getTypeSemestre()).isEqualTo(UPDATED_TYPE_SEMESTRE);
        assertThat(testSemestre.getStateSemestre()).isEqualTo(UPDATED_STATE_SEMESTRE);
    }

    @Test
    @Transactional
    void putNonExistingSemestre() throws Exception {
        int databaseSizeBeforeUpdate = semestreRepository.findAll().size();
        semestre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSemestreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, semestre.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(semestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Semestre in the database
        List<Semestre> semestreList = semestreRepository.findAll();
        assertThat(semestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSemestre() throws Exception {
        int databaseSizeBeforeUpdate = semestreRepository.findAll().size();
        semestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSemestreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(semestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Semestre in the database
        List<Semestre> semestreList = semestreRepository.findAll();
        assertThat(semestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSemestre() throws Exception {
        int databaseSizeBeforeUpdate = semestreRepository.findAll().size();
        semestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSemestreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(semestre)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Semestre in the database
        List<Semestre> semestreList = semestreRepository.findAll();
        assertThat(semestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSemestreWithPatch() throws Exception {
        // Initialize the database
        semestreRepository.saveAndFlush(semestre);

        int databaseSizeBeforeUpdate = semestreRepository.findAll().size();

        // Update the semestre using partial update
        Semestre partialUpdatedSemestre = new Semestre();
        partialUpdatedSemestre.setId(semestre.getId());

        partialUpdatedSemestre
            .idSemestre(UPDATED_ID_SEMESTRE)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaTerminacion(UPDATED_FECHA_TERMINACION)
            .typeSemestre(UPDATED_TYPE_SEMESTRE);

        restSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSemestre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSemestre))
            )
            .andExpect(status().isOk());

        // Validate the Semestre in the database
        List<Semestre> semestreList = semestreRepository.findAll();
        assertThat(semestreList).hasSize(databaseSizeBeforeUpdate);
        Semestre testSemestre = semestreList.get(semestreList.size() - 1);
        assertThat(testSemestre.getIdSemestre()).isEqualTo(UPDATED_ID_SEMESTRE);
        assertThat(testSemestre.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testSemestre.getFechaTerminacion()).isEqualTo(UPDATED_FECHA_TERMINACION);
        assertThat(testSemestre.getTypeSemestre()).isEqualTo(UPDATED_TYPE_SEMESTRE);
        assertThat(testSemestre.getStateSemestre()).isEqualTo(DEFAULT_STATE_SEMESTRE);
    }

    @Test
    @Transactional
    void fullUpdateSemestreWithPatch() throws Exception {
        // Initialize the database
        semestreRepository.saveAndFlush(semestre);

        int databaseSizeBeforeUpdate = semestreRepository.findAll().size();

        // Update the semestre using partial update
        Semestre partialUpdatedSemestre = new Semestre();
        partialUpdatedSemestre.setId(semestre.getId());

        partialUpdatedSemestre
            .idSemestre(UPDATED_ID_SEMESTRE)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaTerminacion(UPDATED_FECHA_TERMINACION)
            .typeSemestre(UPDATED_TYPE_SEMESTRE)
            .stateSemestre(UPDATED_STATE_SEMESTRE);

        restSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSemestre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSemestre))
            )
            .andExpect(status().isOk());

        // Validate the Semestre in the database
        List<Semestre> semestreList = semestreRepository.findAll();
        assertThat(semestreList).hasSize(databaseSizeBeforeUpdate);
        Semestre testSemestre = semestreList.get(semestreList.size() - 1);
        assertThat(testSemestre.getIdSemestre()).isEqualTo(UPDATED_ID_SEMESTRE);
        assertThat(testSemestre.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testSemestre.getFechaTerminacion()).isEqualTo(UPDATED_FECHA_TERMINACION);
        assertThat(testSemestre.getTypeSemestre()).isEqualTo(UPDATED_TYPE_SEMESTRE);
        assertThat(testSemestre.getStateSemestre()).isEqualTo(UPDATED_STATE_SEMESTRE);
    }

    @Test
    @Transactional
    void patchNonExistingSemestre() throws Exception {
        int databaseSizeBeforeUpdate = semestreRepository.findAll().size();
        semestre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, semestre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(semestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Semestre in the database
        List<Semestre> semestreList = semestreRepository.findAll();
        assertThat(semestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSemestre() throws Exception {
        int databaseSizeBeforeUpdate = semestreRepository.findAll().size();
        semestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(semestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the Semestre in the database
        List<Semestre> semestreList = semestreRepository.findAll();
        assertThat(semestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSemestre() throws Exception {
        int databaseSizeBeforeUpdate = semestreRepository.findAll().size();
        semestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSemestreMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(semestre)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Semestre in the database
        List<Semestre> semestreList = semestreRepository.findAll();
        assertThat(semestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSemestre() throws Exception {
        // Initialize the database
        semestreRepository.saveAndFlush(semestre);

        int databaseSizeBeforeDelete = semestreRepository.findAll().size();

        // Delete the semestre
        restSemestreMockMvc
            .perform(delete(ENTITY_API_URL_ID, semestre.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Semestre> semestreList = semestreRepository.findAll();
        assertThat(semestreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

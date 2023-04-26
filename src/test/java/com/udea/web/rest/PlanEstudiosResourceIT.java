package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.PlanEstudios;
import com.udea.repository.PlanEstudiosRepository;
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
 * Integration tests for the {@link PlanEstudiosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanEstudiosResourceIT {

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODIGO_PROGRAMA = 1;
    private static final Integer UPDATED_CODIGO_PROGRAMA = 2;

    private static final LocalDate DEFAULT_FECHA_APROBACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_APROBACION = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/plan-estudios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanEstudiosRepository planEstudiosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanEstudiosMockMvc;

    private PlanEstudios planEstudios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanEstudios createEntity(EntityManager em) {
        PlanEstudios planEstudios = new PlanEstudios()
            .version(DEFAULT_VERSION)
            .codigoPrograma(DEFAULT_CODIGO_PROGRAMA)
            .fechaAprobacion(DEFAULT_FECHA_APROBACION);
        return planEstudios;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanEstudios createUpdatedEntity(EntityManager em) {
        PlanEstudios planEstudios = new PlanEstudios()
            .version(UPDATED_VERSION)
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA)
            .fechaAprobacion(UPDATED_FECHA_APROBACION);
        return planEstudios;
    }

    @BeforeEach
    public void initTest() {
        planEstudios = createEntity(em);
    }

    @Test
    @Transactional
    void createPlanEstudios() throws Exception {
        int databaseSizeBeforeCreate = planEstudiosRepository.findAll().size();
        // Create the PlanEstudios
        restPlanEstudiosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planEstudios)))
            .andExpect(status().isCreated());

        // Validate the PlanEstudios in the database
        List<PlanEstudios> planEstudiosList = planEstudiosRepository.findAll();
        assertThat(planEstudiosList).hasSize(databaseSizeBeforeCreate + 1);
        PlanEstudios testPlanEstudios = planEstudiosList.get(planEstudiosList.size() - 1);
        assertThat(testPlanEstudios.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testPlanEstudios.getCodigoPrograma()).isEqualTo(DEFAULT_CODIGO_PROGRAMA);
        assertThat(testPlanEstudios.getFechaAprobacion()).isEqualTo(DEFAULT_FECHA_APROBACION);
    }

    @Test
    @Transactional
    void createPlanEstudiosWithExistingId() throws Exception {
        // Create the PlanEstudios with an existing ID
        planEstudios.setId(1L);

        int databaseSizeBeforeCreate = planEstudiosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanEstudiosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planEstudios)))
            .andExpect(status().isBadRequest());

        // Validate the PlanEstudios in the database
        List<PlanEstudios> planEstudiosList = planEstudiosRepository.findAll();
        assertThat(planEstudiosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlanEstudios() throws Exception {
        // Initialize the database
        planEstudiosRepository.saveAndFlush(planEstudios);

        // Get all the planEstudiosList
        restPlanEstudiosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planEstudios.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].codigoPrograma").value(hasItem(DEFAULT_CODIGO_PROGRAMA)))
            .andExpect(jsonPath("$.[*].fechaAprobacion").value(hasItem(DEFAULT_FECHA_APROBACION.toString())));
    }

    @Test
    @Transactional
    void getPlanEstudios() throws Exception {
        // Initialize the database
        planEstudiosRepository.saveAndFlush(planEstudios);

        // Get the planEstudios
        restPlanEstudiosMockMvc
            .perform(get(ENTITY_API_URL_ID, planEstudios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planEstudios.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.codigoPrograma").value(DEFAULT_CODIGO_PROGRAMA))
            .andExpect(jsonPath("$.fechaAprobacion").value(DEFAULT_FECHA_APROBACION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPlanEstudios() throws Exception {
        // Get the planEstudios
        restPlanEstudiosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanEstudios() throws Exception {
        // Initialize the database
        planEstudiosRepository.saveAndFlush(planEstudios);

        int databaseSizeBeforeUpdate = planEstudiosRepository.findAll().size();

        // Update the planEstudios
        PlanEstudios updatedPlanEstudios = planEstudiosRepository.findById(planEstudios.getId()).get();
        // Disconnect from session so that the updates on updatedPlanEstudios are not directly saved in db
        em.detach(updatedPlanEstudios);
        updatedPlanEstudios.version(UPDATED_VERSION).codigoPrograma(UPDATED_CODIGO_PROGRAMA).fechaAprobacion(UPDATED_FECHA_APROBACION);

        restPlanEstudiosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlanEstudios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlanEstudios))
            )
            .andExpect(status().isOk());

        // Validate the PlanEstudios in the database
        List<PlanEstudios> planEstudiosList = planEstudiosRepository.findAll();
        assertThat(planEstudiosList).hasSize(databaseSizeBeforeUpdate);
        PlanEstudios testPlanEstudios = planEstudiosList.get(planEstudiosList.size() - 1);
        assertThat(testPlanEstudios.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPlanEstudios.getCodigoPrograma()).isEqualTo(UPDATED_CODIGO_PROGRAMA);
        assertThat(testPlanEstudios.getFechaAprobacion()).isEqualTo(UPDATED_FECHA_APROBACION);
    }

    @Test
    @Transactional
    void putNonExistingPlanEstudios() throws Exception {
        int databaseSizeBeforeUpdate = planEstudiosRepository.findAll().size();
        planEstudios.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanEstudiosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planEstudios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planEstudios))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanEstudios in the database
        List<PlanEstudios> planEstudiosList = planEstudiosRepository.findAll();
        assertThat(planEstudiosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanEstudios() throws Exception {
        int databaseSizeBeforeUpdate = planEstudiosRepository.findAll().size();
        planEstudios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanEstudiosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(planEstudios))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanEstudios in the database
        List<PlanEstudios> planEstudiosList = planEstudiosRepository.findAll();
        assertThat(planEstudiosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanEstudios() throws Exception {
        int databaseSizeBeforeUpdate = planEstudiosRepository.findAll().size();
        planEstudios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanEstudiosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(planEstudios)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanEstudios in the database
        List<PlanEstudios> planEstudiosList = planEstudiosRepository.findAll();
        assertThat(planEstudiosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanEstudiosWithPatch() throws Exception {
        // Initialize the database
        planEstudiosRepository.saveAndFlush(planEstudios);

        int databaseSizeBeforeUpdate = planEstudiosRepository.findAll().size();

        // Update the planEstudios using partial update
        PlanEstudios partialUpdatedPlanEstudios = new PlanEstudios();
        partialUpdatedPlanEstudios.setId(planEstudios.getId());

        partialUpdatedPlanEstudios.codigoPrograma(UPDATED_CODIGO_PROGRAMA);

        restPlanEstudiosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanEstudios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanEstudios))
            )
            .andExpect(status().isOk());

        // Validate the PlanEstudios in the database
        List<PlanEstudios> planEstudiosList = planEstudiosRepository.findAll();
        assertThat(planEstudiosList).hasSize(databaseSizeBeforeUpdate);
        PlanEstudios testPlanEstudios = planEstudiosList.get(planEstudiosList.size() - 1);
        assertThat(testPlanEstudios.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testPlanEstudios.getCodigoPrograma()).isEqualTo(UPDATED_CODIGO_PROGRAMA);
        assertThat(testPlanEstudios.getFechaAprobacion()).isEqualTo(DEFAULT_FECHA_APROBACION);
    }

    @Test
    @Transactional
    void fullUpdatePlanEstudiosWithPatch() throws Exception {
        // Initialize the database
        planEstudiosRepository.saveAndFlush(planEstudios);

        int databaseSizeBeforeUpdate = planEstudiosRepository.findAll().size();

        // Update the planEstudios using partial update
        PlanEstudios partialUpdatedPlanEstudios = new PlanEstudios();
        partialUpdatedPlanEstudios.setId(planEstudios.getId());

        partialUpdatedPlanEstudios
            .version(UPDATED_VERSION)
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA)
            .fechaAprobacion(UPDATED_FECHA_APROBACION);

        restPlanEstudiosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanEstudios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlanEstudios))
            )
            .andExpect(status().isOk());

        // Validate the PlanEstudios in the database
        List<PlanEstudios> planEstudiosList = planEstudiosRepository.findAll();
        assertThat(planEstudiosList).hasSize(databaseSizeBeforeUpdate);
        PlanEstudios testPlanEstudios = planEstudiosList.get(planEstudiosList.size() - 1);
        assertThat(testPlanEstudios.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testPlanEstudios.getCodigoPrograma()).isEqualTo(UPDATED_CODIGO_PROGRAMA);
        assertThat(testPlanEstudios.getFechaAprobacion()).isEqualTo(UPDATED_FECHA_APROBACION);
    }

    @Test
    @Transactional
    void patchNonExistingPlanEstudios() throws Exception {
        int databaseSizeBeforeUpdate = planEstudiosRepository.findAll().size();
        planEstudios.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanEstudiosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planEstudios.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planEstudios))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanEstudios in the database
        List<PlanEstudios> planEstudiosList = planEstudiosRepository.findAll();
        assertThat(planEstudiosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanEstudios() throws Exception {
        int databaseSizeBeforeUpdate = planEstudiosRepository.findAll().size();
        planEstudios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanEstudiosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(planEstudios))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanEstudios in the database
        List<PlanEstudios> planEstudiosList = planEstudiosRepository.findAll();
        assertThat(planEstudiosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanEstudios() throws Exception {
        int databaseSizeBeforeUpdate = planEstudiosRepository.findAll().size();
        planEstudios.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanEstudiosMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(planEstudios))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanEstudios in the database
        List<PlanEstudios> planEstudiosList = planEstudiosRepository.findAll();
        assertThat(planEstudiosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanEstudios() throws Exception {
        // Initialize the database
        planEstudiosRepository.saveAndFlush(planEstudios);

        int databaseSizeBeforeDelete = planEstudiosRepository.findAll().size();

        // Delete the planEstudios
        restPlanEstudiosMockMvc
            .perform(delete(ENTITY_API_URL_ID, planEstudios.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlanEstudios> planEstudiosList = planEstudiosRepository.findAll();
        assertThat(planEstudiosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.SituacionAcademica;
import com.udea.repository.SituacionAcademicaRepository;
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
 * Integration tests for the {@link SituacionAcademicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SituacionAcademicaResourceIT {

    private static final Integer DEFAULT_ID_SITUACION_ACADEMICA = 1;
    private static final Integer UPDATED_ID_SITUACION_ACADEMICA = 2;

    private static final String DEFAULT_SITUATION_ACADEMICA = "AAAAAAAAAA";
    private static final String UPDATED_SITUATION_ACADEMICA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/situacion-academicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SituacionAcademicaRepository situacionAcademicaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSituacionAcademicaMockMvc;

    private SituacionAcademica situacionAcademica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SituacionAcademica createEntity(EntityManager em) {
        SituacionAcademica situacionAcademica = new SituacionAcademica()
            .idSituacionAcademica(DEFAULT_ID_SITUACION_ACADEMICA)
            .situationAcademica(DEFAULT_SITUATION_ACADEMICA);
        return situacionAcademica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SituacionAcademica createUpdatedEntity(EntityManager em) {
        SituacionAcademica situacionAcademica = new SituacionAcademica()
            .idSituacionAcademica(UPDATED_ID_SITUACION_ACADEMICA)
            .situationAcademica(UPDATED_SITUATION_ACADEMICA);
        return situacionAcademica;
    }

    @BeforeEach
    public void initTest() {
        situacionAcademica = createEntity(em);
    }

    @Test
    @Transactional
    void createSituacionAcademica() throws Exception {
        int databaseSizeBeforeCreate = situacionAcademicaRepository.findAll().size();
        // Create the SituacionAcademica
        restSituacionAcademicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(situacionAcademica))
            )
            .andExpect(status().isCreated());

        // Validate the SituacionAcademica in the database
        List<SituacionAcademica> situacionAcademicaList = situacionAcademicaRepository.findAll();
        assertThat(situacionAcademicaList).hasSize(databaseSizeBeforeCreate + 1);
        SituacionAcademica testSituacionAcademica = situacionAcademicaList.get(situacionAcademicaList.size() - 1);
        assertThat(testSituacionAcademica.getIdSituacionAcademica()).isEqualTo(DEFAULT_ID_SITUACION_ACADEMICA);
        assertThat(testSituacionAcademica.getSituationAcademica()).isEqualTo(DEFAULT_SITUATION_ACADEMICA);
    }

    @Test
    @Transactional
    void createSituacionAcademicaWithExistingId() throws Exception {
        // Create the SituacionAcademica with an existing ID
        situacionAcademica.setId(1L);

        int databaseSizeBeforeCreate = situacionAcademicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSituacionAcademicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(situacionAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the SituacionAcademica in the database
        List<SituacionAcademica> situacionAcademicaList = situacionAcademicaRepository.findAll();
        assertThat(situacionAcademicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSituacionAcademicas() throws Exception {
        // Initialize the database
        situacionAcademicaRepository.saveAndFlush(situacionAcademica);

        // Get all the situacionAcademicaList
        restSituacionAcademicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situacionAcademica.getId().intValue())))
            .andExpect(jsonPath("$.[*].idSituacionAcademica").value(hasItem(DEFAULT_ID_SITUACION_ACADEMICA)))
            .andExpect(jsonPath("$.[*].situationAcademica").value(hasItem(DEFAULT_SITUATION_ACADEMICA)));
    }

    @Test
    @Transactional
    void getSituacionAcademica() throws Exception {
        // Initialize the database
        situacionAcademicaRepository.saveAndFlush(situacionAcademica);

        // Get the situacionAcademica
        restSituacionAcademicaMockMvc
            .perform(get(ENTITY_API_URL_ID, situacionAcademica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(situacionAcademica.getId().intValue()))
            .andExpect(jsonPath("$.idSituacionAcademica").value(DEFAULT_ID_SITUACION_ACADEMICA))
            .andExpect(jsonPath("$.situationAcademica").value(DEFAULT_SITUATION_ACADEMICA));
    }

    @Test
    @Transactional
    void getNonExistingSituacionAcademica() throws Exception {
        // Get the situacionAcademica
        restSituacionAcademicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSituacionAcademica() throws Exception {
        // Initialize the database
        situacionAcademicaRepository.saveAndFlush(situacionAcademica);

        int databaseSizeBeforeUpdate = situacionAcademicaRepository.findAll().size();

        // Update the situacionAcademica
        SituacionAcademica updatedSituacionAcademica = situacionAcademicaRepository.findById(situacionAcademica.getId()).get();
        // Disconnect from session so that the updates on updatedSituacionAcademica are not directly saved in db
        em.detach(updatedSituacionAcademica);
        updatedSituacionAcademica.idSituacionAcademica(UPDATED_ID_SITUACION_ACADEMICA).situationAcademica(UPDATED_SITUATION_ACADEMICA);

        restSituacionAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSituacionAcademica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSituacionAcademica))
            )
            .andExpect(status().isOk());

        // Validate the SituacionAcademica in the database
        List<SituacionAcademica> situacionAcademicaList = situacionAcademicaRepository.findAll();
        assertThat(situacionAcademicaList).hasSize(databaseSizeBeforeUpdate);
        SituacionAcademica testSituacionAcademica = situacionAcademicaList.get(situacionAcademicaList.size() - 1);
        assertThat(testSituacionAcademica.getIdSituacionAcademica()).isEqualTo(UPDATED_ID_SITUACION_ACADEMICA);
        assertThat(testSituacionAcademica.getSituationAcademica()).isEqualTo(UPDATED_SITUATION_ACADEMICA);
    }

    @Test
    @Transactional
    void putNonExistingSituacionAcademica() throws Exception {
        int databaseSizeBeforeUpdate = situacionAcademicaRepository.findAll().size();
        situacionAcademica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituacionAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, situacionAcademica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(situacionAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the SituacionAcademica in the database
        List<SituacionAcademica> situacionAcademicaList = situacionAcademicaRepository.findAll();
        assertThat(situacionAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSituacionAcademica() throws Exception {
        int databaseSizeBeforeUpdate = situacionAcademicaRepository.findAll().size();
        situacionAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituacionAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(situacionAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the SituacionAcademica in the database
        List<SituacionAcademica> situacionAcademicaList = situacionAcademicaRepository.findAll();
        assertThat(situacionAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSituacionAcademica() throws Exception {
        int databaseSizeBeforeUpdate = situacionAcademicaRepository.findAll().size();
        situacionAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituacionAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(situacionAcademica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SituacionAcademica in the database
        List<SituacionAcademica> situacionAcademicaList = situacionAcademicaRepository.findAll();
        assertThat(situacionAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSituacionAcademicaWithPatch() throws Exception {
        // Initialize the database
        situacionAcademicaRepository.saveAndFlush(situacionAcademica);

        int databaseSizeBeforeUpdate = situacionAcademicaRepository.findAll().size();

        // Update the situacionAcademica using partial update
        SituacionAcademica partialUpdatedSituacionAcademica = new SituacionAcademica();
        partialUpdatedSituacionAcademica.setId(situacionAcademica.getId());

        restSituacionAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSituacionAcademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSituacionAcademica))
            )
            .andExpect(status().isOk());

        // Validate the SituacionAcademica in the database
        List<SituacionAcademica> situacionAcademicaList = situacionAcademicaRepository.findAll();
        assertThat(situacionAcademicaList).hasSize(databaseSizeBeforeUpdate);
        SituacionAcademica testSituacionAcademica = situacionAcademicaList.get(situacionAcademicaList.size() - 1);
        assertThat(testSituacionAcademica.getIdSituacionAcademica()).isEqualTo(DEFAULT_ID_SITUACION_ACADEMICA);
        assertThat(testSituacionAcademica.getSituationAcademica()).isEqualTo(DEFAULT_SITUATION_ACADEMICA);
    }

    @Test
    @Transactional
    void fullUpdateSituacionAcademicaWithPatch() throws Exception {
        // Initialize the database
        situacionAcademicaRepository.saveAndFlush(situacionAcademica);

        int databaseSizeBeforeUpdate = situacionAcademicaRepository.findAll().size();

        // Update the situacionAcademica using partial update
        SituacionAcademica partialUpdatedSituacionAcademica = new SituacionAcademica();
        partialUpdatedSituacionAcademica.setId(situacionAcademica.getId());

        partialUpdatedSituacionAcademica
            .idSituacionAcademica(UPDATED_ID_SITUACION_ACADEMICA)
            .situationAcademica(UPDATED_SITUATION_ACADEMICA);

        restSituacionAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSituacionAcademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSituacionAcademica))
            )
            .andExpect(status().isOk());

        // Validate the SituacionAcademica in the database
        List<SituacionAcademica> situacionAcademicaList = situacionAcademicaRepository.findAll();
        assertThat(situacionAcademicaList).hasSize(databaseSizeBeforeUpdate);
        SituacionAcademica testSituacionAcademica = situacionAcademicaList.get(situacionAcademicaList.size() - 1);
        assertThat(testSituacionAcademica.getIdSituacionAcademica()).isEqualTo(UPDATED_ID_SITUACION_ACADEMICA);
        assertThat(testSituacionAcademica.getSituationAcademica()).isEqualTo(UPDATED_SITUATION_ACADEMICA);
    }

    @Test
    @Transactional
    void patchNonExistingSituacionAcademica() throws Exception {
        int databaseSizeBeforeUpdate = situacionAcademicaRepository.findAll().size();
        situacionAcademica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituacionAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, situacionAcademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(situacionAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the SituacionAcademica in the database
        List<SituacionAcademica> situacionAcademicaList = situacionAcademicaRepository.findAll();
        assertThat(situacionAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSituacionAcademica() throws Exception {
        int databaseSizeBeforeUpdate = situacionAcademicaRepository.findAll().size();
        situacionAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituacionAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(situacionAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the SituacionAcademica in the database
        List<SituacionAcademica> situacionAcademicaList = situacionAcademicaRepository.findAll();
        assertThat(situacionAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSituacionAcademica() throws Exception {
        int databaseSizeBeforeUpdate = situacionAcademicaRepository.findAll().size();
        situacionAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituacionAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(situacionAcademica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SituacionAcademica in the database
        List<SituacionAcademica> situacionAcademicaList = situacionAcademicaRepository.findAll();
        assertThat(situacionAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSituacionAcademica() throws Exception {
        // Initialize the database
        situacionAcademicaRepository.saveAndFlush(situacionAcademica);

        int databaseSizeBeforeDelete = situacionAcademicaRepository.findAll().size();

        // Delete the situacionAcademica
        restSituacionAcademicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, situacionAcademica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SituacionAcademica> situacionAcademicaList = situacionAcademicaRepository.findAll();
        assertThat(situacionAcademicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

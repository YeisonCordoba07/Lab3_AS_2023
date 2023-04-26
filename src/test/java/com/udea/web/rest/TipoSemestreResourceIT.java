package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.TipoSemestre;
import com.udea.repository.TipoSemestreRepository;
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
 * Integration tests for the {@link TipoSemestreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoSemestreResourceIT {

    private static final Integer DEFAULT_ID_TIPO_SEMESTRE = 1;
    private static final Integer UPDATED_ID_TIPO_SEMESTRE = 2;

    private static final String DEFAULT_TYPE_SEMESTRE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_SEMESTRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-semestres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoSemestreRepository tipoSemestreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoSemestreMockMvc;

    private TipoSemestre tipoSemestre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoSemestre createEntity(EntityManager em) {
        TipoSemestre tipoSemestre = new TipoSemestre().idTipoSemestre(DEFAULT_ID_TIPO_SEMESTRE).typeSemestre(DEFAULT_TYPE_SEMESTRE);
        return tipoSemestre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoSemestre createUpdatedEntity(EntityManager em) {
        TipoSemestre tipoSemestre = new TipoSemestre().idTipoSemestre(UPDATED_ID_TIPO_SEMESTRE).typeSemestre(UPDATED_TYPE_SEMESTRE);
        return tipoSemestre;
    }

    @BeforeEach
    public void initTest() {
        tipoSemestre = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoSemestre() throws Exception {
        int databaseSizeBeforeCreate = tipoSemestreRepository.findAll().size();
        // Create the TipoSemestre
        restTipoSemestreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoSemestre)))
            .andExpect(status().isCreated());

        // Validate the TipoSemestre in the database
        List<TipoSemestre> tipoSemestreList = tipoSemestreRepository.findAll();
        assertThat(tipoSemestreList).hasSize(databaseSizeBeforeCreate + 1);
        TipoSemestre testTipoSemestre = tipoSemestreList.get(tipoSemestreList.size() - 1);
        assertThat(testTipoSemestre.getIdTipoSemestre()).isEqualTo(DEFAULT_ID_TIPO_SEMESTRE);
        assertThat(testTipoSemestre.getTypeSemestre()).isEqualTo(DEFAULT_TYPE_SEMESTRE);
    }

    @Test
    @Transactional
    void createTipoSemestreWithExistingId() throws Exception {
        // Create the TipoSemestre with an existing ID
        tipoSemestre.setId(1L);

        int databaseSizeBeforeCreate = tipoSemestreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoSemestreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoSemestre)))
            .andExpect(status().isBadRequest());

        // Validate the TipoSemestre in the database
        List<TipoSemestre> tipoSemestreList = tipoSemestreRepository.findAll();
        assertThat(tipoSemestreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTipoSemestres() throws Exception {
        // Initialize the database
        tipoSemestreRepository.saveAndFlush(tipoSemestre);

        // Get all the tipoSemestreList
        restTipoSemestreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoSemestre.getId().intValue())))
            .andExpect(jsonPath("$.[*].idTipoSemestre").value(hasItem(DEFAULT_ID_TIPO_SEMESTRE)))
            .andExpect(jsonPath("$.[*].typeSemestre").value(hasItem(DEFAULT_TYPE_SEMESTRE)));
    }

    @Test
    @Transactional
    void getTipoSemestre() throws Exception {
        // Initialize the database
        tipoSemestreRepository.saveAndFlush(tipoSemestre);

        // Get the tipoSemestre
        restTipoSemestreMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoSemestre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoSemestre.getId().intValue()))
            .andExpect(jsonPath("$.idTipoSemestre").value(DEFAULT_ID_TIPO_SEMESTRE))
            .andExpect(jsonPath("$.typeSemestre").value(DEFAULT_TYPE_SEMESTRE));
    }

    @Test
    @Transactional
    void getNonExistingTipoSemestre() throws Exception {
        // Get the tipoSemestre
        restTipoSemestreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTipoSemestre() throws Exception {
        // Initialize the database
        tipoSemestreRepository.saveAndFlush(tipoSemestre);

        int databaseSizeBeforeUpdate = tipoSemestreRepository.findAll().size();

        // Update the tipoSemestre
        TipoSemestre updatedTipoSemestre = tipoSemestreRepository.findById(tipoSemestre.getId()).get();
        // Disconnect from session so that the updates on updatedTipoSemestre are not directly saved in db
        em.detach(updatedTipoSemestre);
        updatedTipoSemestre.idTipoSemestre(UPDATED_ID_TIPO_SEMESTRE).typeSemestre(UPDATED_TYPE_SEMESTRE);

        restTipoSemestreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoSemestre.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoSemestre))
            )
            .andExpect(status().isOk());

        // Validate the TipoSemestre in the database
        List<TipoSemestre> tipoSemestreList = tipoSemestreRepository.findAll();
        assertThat(tipoSemestreList).hasSize(databaseSizeBeforeUpdate);
        TipoSemestre testTipoSemestre = tipoSemestreList.get(tipoSemestreList.size() - 1);
        assertThat(testTipoSemestre.getIdTipoSemestre()).isEqualTo(UPDATED_ID_TIPO_SEMESTRE);
        assertThat(testTipoSemestre.getTypeSemestre()).isEqualTo(UPDATED_TYPE_SEMESTRE);
    }

    @Test
    @Transactional
    void putNonExistingTipoSemestre() throws Exception {
        int databaseSizeBeforeUpdate = tipoSemestreRepository.findAll().size();
        tipoSemestre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoSemestreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoSemestre.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoSemestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoSemestre in the database
        List<TipoSemestre> tipoSemestreList = tipoSemestreRepository.findAll();
        assertThat(tipoSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoSemestre() throws Exception {
        int databaseSizeBeforeUpdate = tipoSemestreRepository.findAll().size();
        tipoSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoSemestreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoSemestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoSemestre in the database
        List<TipoSemestre> tipoSemestreList = tipoSemestreRepository.findAll();
        assertThat(tipoSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoSemestre() throws Exception {
        int databaseSizeBeforeUpdate = tipoSemestreRepository.findAll().size();
        tipoSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoSemestreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoSemestre)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoSemestre in the database
        List<TipoSemestre> tipoSemestreList = tipoSemestreRepository.findAll();
        assertThat(tipoSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoSemestreWithPatch() throws Exception {
        // Initialize the database
        tipoSemestreRepository.saveAndFlush(tipoSemestre);

        int databaseSizeBeforeUpdate = tipoSemestreRepository.findAll().size();

        // Update the tipoSemestre using partial update
        TipoSemestre partialUpdatedTipoSemestre = new TipoSemestre();
        partialUpdatedTipoSemestre.setId(tipoSemestre.getId());

        partialUpdatedTipoSemestre.idTipoSemestre(UPDATED_ID_TIPO_SEMESTRE).typeSemestre(UPDATED_TYPE_SEMESTRE);

        restTipoSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoSemestre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoSemestre))
            )
            .andExpect(status().isOk());

        // Validate the TipoSemestre in the database
        List<TipoSemestre> tipoSemestreList = tipoSemestreRepository.findAll();
        assertThat(tipoSemestreList).hasSize(databaseSizeBeforeUpdate);
        TipoSemestre testTipoSemestre = tipoSemestreList.get(tipoSemestreList.size() - 1);
        assertThat(testTipoSemestre.getIdTipoSemestre()).isEqualTo(UPDATED_ID_TIPO_SEMESTRE);
        assertThat(testTipoSemestre.getTypeSemestre()).isEqualTo(UPDATED_TYPE_SEMESTRE);
    }

    @Test
    @Transactional
    void fullUpdateTipoSemestreWithPatch() throws Exception {
        // Initialize the database
        tipoSemestreRepository.saveAndFlush(tipoSemestre);

        int databaseSizeBeforeUpdate = tipoSemestreRepository.findAll().size();

        // Update the tipoSemestre using partial update
        TipoSemestre partialUpdatedTipoSemestre = new TipoSemestre();
        partialUpdatedTipoSemestre.setId(tipoSemestre.getId());

        partialUpdatedTipoSemestre.idTipoSemestre(UPDATED_ID_TIPO_SEMESTRE).typeSemestre(UPDATED_TYPE_SEMESTRE);

        restTipoSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoSemestre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoSemestre))
            )
            .andExpect(status().isOk());

        // Validate the TipoSemestre in the database
        List<TipoSemestre> tipoSemestreList = tipoSemestreRepository.findAll();
        assertThat(tipoSemestreList).hasSize(databaseSizeBeforeUpdate);
        TipoSemestre testTipoSemestre = tipoSemestreList.get(tipoSemestreList.size() - 1);
        assertThat(testTipoSemestre.getIdTipoSemestre()).isEqualTo(UPDATED_ID_TIPO_SEMESTRE);
        assertThat(testTipoSemestre.getTypeSemestre()).isEqualTo(UPDATED_TYPE_SEMESTRE);
    }

    @Test
    @Transactional
    void patchNonExistingTipoSemestre() throws Exception {
        int databaseSizeBeforeUpdate = tipoSemestreRepository.findAll().size();
        tipoSemestre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoSemestre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoSemestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoSemestre in the database
        List<TipoSemestre> tipoSemestreList = tipoSemestreRepository.findAll();
        assertThat(tipoSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoSemestre() throws Exception {
        int databaseSizeBeforeUpdate = tipoSemestreRepository.findAll().size();
        tipoSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoSemestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoSemestre in the database
        List<TipoSemestre> tipoSemestreList = tipoSemestreRepository.findAll();
        assertThat(tipoSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoSemestre() throws Exception {
        int databaseSizeBeforeUpdate = tipoSemestreRepository.findAll().size();
        tipoSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoSemestre))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoSemestre in the database
        List<TipoSemestre> tipoSemestreList = tipoSemestreRepository.findAll();
        assertThat(tipoSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoSemestre() throws Exception {
        // Initialize the database
        tipoSemestreRepository.saveAndFlush(tipoSemestre);

        int databaseSizeBeforeDelete = tipoSemestreRepository.findAll().size();

        // Delete the tipoSemestre
        restTipoSemestreMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoSemestre.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoSemestre> tipoSemestreList = tipoSemestreRepository.findAll();
        assertThat(tipoSemestreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

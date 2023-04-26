package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.HistoriaAcademica;
import com.udea.repository.HistoriaAcademicaRepository;
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
 * Integration tests for the {@link HistoriaAcademicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HistoriaAcademicaResourceIT {

    private static final Integer DEFAULT_ID_HISTORIA_ACADEMICA = 1;
    private static final Integer UPDATED_ID_HISTORIA_ACADEMICA = 2;

    private static final String DEFAULT_CEDULA_ESTUDIANTE = "AAAAAAAAAA";
    private static final String UPDATED_CEDULA_ESTUDIANTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_SEMESTRE = 1;
    private static final Integer UPDATED_ID_SEMESTRE = 2;

    private static final Integer DEFAULT_CODIGO_PROGRAMA = 1;
    private static final Integer UPDATED_CODIGO_PROGRAMA = 2;

    private static final Float DEFAULT_PROMEDIO_ACOMULADO = 1F;
    private static final Float UPDATED_PROMEDIO_ACOMULADO = 2F;

    private static final Float DEFAULT_PROMEDIO_SEMESTRE = 1F;
    private static final Float UPDATED_PROMEDIO_SEMESTRE = 2F;

    private static final Integer DEFAULT_ID_TERCIO = 1;
    private static final Integer UPDATED_ID_TERCIO = 2;

    private static final Integer DEFAULT_SITUATION_ACADEMICA = 1;
    private static final Integer UPDATED_SITUATION_ACADEMICA = 2;

    private static final Integer DEFAULT_STATE_SEMESTRE = 1;
    private static final Integer UPDATED_STATE_SEMESTRE = 2;

    private static final String ENTITY_API_URL = "/api/historia-academicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HistoriaAcademicaRepository historiaAcademicaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHistoriaAcademicaMockMvc;

    private HistoriaAcademica historiaAcademica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoriaAcademica createEntity(EntityManager em) {
        HistoriaAcademica historiaAcademica = new HistoriaAcademica()
            .idHistoriaAcademica(DEFAULT_ID_HISTORIA_ACADEMICA)
            .cedulaEstudiante(DEFAULT_CEDULA_ESTUDIANTE)
            .idSemestre(DEFAULT_ID_SEMESTRE)
            .codigoPrograma(DEFAULT_CODIGO_PROGRAMA)
            .promedioAcomulado(DEFAULT_PROMEDIO_ACOMULADO)
            .promedioSemestre(DEFAULT_PROMEDIO_SEMESTRE)
            .idTercio(DEFAULT_ID_TERCIO)
            .situationAcademica(DEFAULT_SITUATION_ACADEMICA)
            .stateSemestre(DEFAULT_STATE_SEMESTRE);
        return historiaAcademica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HistoriaAcademica createUpdatedEntity(EntityManager em) {
        HistoriaAcademica historiaAcademica = new HistoriaAcademica()
            .idHistoriaAcademica(UPDATED_ID_HISTORIA_ACADEMICA)
            .cedulaEstudiante(UPDATED_CEDULA_ESTUDIANTE)
            .idSemestre(UPDATED_ID_SEMESTRE)
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA)
            .promedioAcomulado(UPDATED_PROMEDIO_ACOMULADO)
            .promedioSemestre(UPDATED_PROMEDIO_SEMESTRE)
            .idTercio(UPDATED_ID_TERCIO)
            .situationAcademica(UPDATED_SITUATION_ACADEMICA)
            .stateSemestre(UPDATED_STATE_SEMESTRE);
        return historiaAcademica;
    }

    @BeforeEach
    public void initTest() {
        historiaAcademica = createEntity(em);
    }

    @Test
    @Transactional
    void createHistoriaAcademica() throws Exception {
        int databaseSizeBeforeCreate = historiaAcademicaRepository.findAll().size();
        // Create the HistoriaAcademica
        restHistoriaAcademicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historiaAcademica))
            )
            .andExpect(status().isCreated());

        // Validate the HistoriaAcademica in the database
        List<HistoriaAcademica> historiaAcademicaList = historiaAcademicaRepository.findAll();
        assertThat(historiaAcademicaList).hasSize(databaseSizeBeforeCreate + 1);
        HistoriaAcademica testHistoriaAcademica = historiaAcademicaList.get(historiaAcademicaList.size() - 1);
        assertThat(testHistoriaAcademica.getIdHistoriaAcademica()).isEqualTo(DEFAULT_ID_HISTORIA_ACADEMICA);
        assertThat(testHistoriaAcademica.getCedulaEstudiante()).isEqualTo(DEFAULT_CEDULA_ESTUDIANTE);
        assertThat(testHistoriaAcademica.getIdSemestre()).isEqualTo(DEFAULT_ID_SEMESTRE);
        assertThat(testHistoriaAcademica.getCodigoPrograma()).isEqualTo(DEFAULT_CODIGO_PROGRAMA);
        assertThat(testHistoriaAcademica.getPromedioAcomulado()).isEqualTo(DEFAULT_PROMEDIO_ACOMULADO);
        assertThat(testHistoriaAcademica.getPromedioSemestre()).isEqualTo(DEFAULT_PROMEDIO_SEMESTRE);
        assertThat(testHistoriaAcademica.getIdTercio()).isEqualTo(DEFAULT_ID_TERCIO);
        assertThat(testHistoriaAcademica.getSituationAcademica()).isEqualTo(DEFAULT_SITUATION_ACADEMICA);
        assertThat(testHistoriaAcademica.getStateSemestre()).isEqualTo(DEFAULT_STATE_SEMESTRE);
    }

    @Test
    @Transactional
    void createHistoriaAcademicaWithExistingId() throws Exception {
        // Create the HistoriaAcademica with an existing ID
        historiaAcademica.setId(1L);

        int databaseSizeBeforeCreate = historiaAcademicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHistoriaAcademicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historiaAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoriaAcademica in the database
        List<HistoriaAcademica> historiaAcademicaList = historiaAcademicaRepository.findAll();
        assertThat(historiaAcademicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllHistoriaAcademicas() throws Exception {
        // Initialize the database
        historiaAcademicaRepository.saveAndFlush(historiaAcademica);

        // Get all the historiaAcademicaList
        restHistoriaAcademicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(historiaAcademica.getId().intValue())))
            .andExpect(jsonPath("$.[*].idHistoriaAcademica").value(hasItem(DEFAULT_ID_HISTORIA_ACADEMICA)))
            .andExpect(jsonPath("$.[*].cedulaEstudiante").value(hasItem(DEFAULT_CEDULA_ESTUDIANTE)))
            .andExpect(jsonPath("$.[*].idSemestre").value(hasItem(DEFAULT_ID_SEMESTRE)))
            .andExpect(jsonPath("$.[*].codigoPrograma").value(hasItem(DEFAULT_CODIGO_PROGRAMA)))
            .andExpect(jsonPath("$.[*].promedioAcomulado").value(hasItem(DEFAULT_PROMEDIO_ACOMULADO.doubleValue())))
            .andExpect(jsonPath("$.[*].promedioSemestre").value(hasItem(DEFAULT_PROMEDIO_SEMESTRE.doubleValue())))
            .andExpect(jsonPath("$.[*].idTercio").value(hasItem(DEFAULT_ID_TERCIO)))
            .andExpect(jsonPath("$.[*].situationAcademica").value(hasItem(DEFAULT_SITUATION_ACADEMICA)))
            .andExpect(jsonPath("$.[*].stateSemestre").value(hasItem(DEFAULT_STATE_SEMESTRE)));
    }

    @Test
    @Transactional
    void getHistoriaAcademica() throws Exception {
        // Initialize the database
        historiaAcademicaRepository.saveAndFlush(historiaAcademica);

        // Get the historiaAcademica
        restHistoriaAcademicaMockMvc
            .perform(get(ENTITY_API_URL_ID, historiaAcademica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(historiaAcademica.getId().intValue()))
            .andExpect(jsonPath("$.idHistoriaAcademica").value(DEFAULT_ID_HISTORIA_ACADEMICA))
            .andExpect(jsonPath("$.cedulaEstudiante").value(DEFAULT_CEDULA_ESTUDIANTE))
            .andExpect(jsonPath("$.idSemestre").value(DEFAULT_ID_SEMESTRE))
            .andExpect(jsonPath("$.codigoPrograma").value(DEFAULT_CODIGO_PROGRAMA))
            .andExpect(jsonPath("$.promedioAcomulado").value(DEFAULT_PROMEDIO_ACOMULADO.doubleValue()))
            .andExpect(jsonPath("$.promedioSemestre").value(DEFAULT_PROMEDIO_SEMESTRE.doubleValue()))
            .andExpect(jsonPath("$.idTercio").value(DEFAULT_ID_TERCIO))
            .andExpect(jsonPath("$.situationAcademica").value(DEFAULT_SITUATION_ACADEMICA))
            .andExpect(jsonPath("$.stateSemestre").value(DEFAULT_STATE_SEMESTRE));
    }

    @Test
    @Transactional
    void getNonExistingHistoriaAcademica() throws Exception {
        // Get the historiaAcademica
        restHistoriaAcademicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHistoriaAcademica() throws Exception {
        // Initialize the database
        historiaAcademicaRepository.saveAndFlush(historiaAcademica);

        int databaseSizeBeforeUpdate = historiaAcademicaRepository.findAll().size();

        // Update the historiaAcademica
        HistoriaAcademica updatedHistoriaAcademica = historiaAcademicaRepository.findById(historiaAcademica.getId()).get();
        // Disconnect from session so that the updates on updatedHistoriaAcademica are not directly saved in db
        em.detach(updatedHistoriaAcademica);
        updatedHistoriaAcademica
            .idHistoriaAcademica(UPDATED_ID_HISTORIA_ACADEMICA)
            .cedulaEstudiante(UPDATED_CEDULA_ESTUDIANTE)
            .idSemestre(UPDATED_ID_SEMESTRE)
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA)
            .promedioAcomulado(UPDATED_PROMEDIO_ACOMULADO)
            .promedioSemestre(UPDATED_PROMEDIO_SEMESTRE)
            .idTercio(UPDATED_ID_TERCIO)
            .situationAcademica(UPDATED_SITUATION_ACADEMICA)
            .stateSemestre(UPDATED_STATE_SEMESTRE);

        restHistoriaAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHistoriaAcademica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHistoriaAcademica))
            )
            .andExpect(status().isOk());

        // Validate the HistoriaAcademica in the database
        List<HistoriaAcademica> historiaAcademicaList = historiaAcademicaRepository.findAll();
        assertThat(historiaAcademicaList).hasSize(databaseSizeBeforeUpdate);
        HistoriaAcademica testHistoriaAcademica = historiaAcademicaList.get(historiaAcademicaList.size() - 1);
        assertThat(testHistoriaAcademica.getIdHistoriaAcademica()).isEqualTo(UPDATED_ID_HISTORIA_ACADEMICA);
        assertThat(testHistoriaAcademica.getCedulaEstudiante()).isEqualTo(UPDATED_CEDULA_ESTUDIANTE);
        assertThat(testHistoriaAcademica.getIdSemestre()).isEqualTo(UPDATED_ID_SEMESTRE);
        assertThat(testHistoriaAcademica.getCodigoPrograma()).isEqualTo(UPDATED_CODIGO_PROGRAMA);
        assertThat(testHistoriaAcademica.getPromedioAcomulado()).isEqualTo(UPDATED_PROMEDIO_ACOMULADO);
        assertThat(testHistoriaAcademica.getPromedioSemestre()).isEqualTo(UPDATED_PROMEDIO_SEMESTRE);
        assertThat(testHistoriaAcademica.getIdTercio()).isEqualTo(UPDATED_ID_TERCIO);
        assertThat(testHistoriaAcademica.getSituationAcademica()).isEqualTo(UPDATED_SITUATION_ACADEMICA);
        assertThat(testHistoriaAcademica.getStateSemestre()).isEqualTo(UPDATED_STATE_SEMESTRE);
    }

    @Test
    @Transactional
    void putNonExistingHistoriaAcademica() throws Exception {
        int databaseSizeBeforeUpdate = historiaAcademicaRepository.findAll().size();
        historiaAcademica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoriaAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, historiaAcademica.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historiaAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoriaAcademica in the database
        List<HistoriaAcademica> historiaAcademicaList = historiaAcademicaRepository.findAll();
        assertThat(historiaAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHistoriaAcademica() throws Exception {
        int databaseSizeBeforeUpdate = historiaAcademicaRepository.findAll().size();
        historiaAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoriaAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(historiaAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoriaAcademica in the database
        List<HistoriaAcademica> historiaAcademicaList = historiaAcademicaRepository.findAll();
        assertThat(historiaAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHistoriaAcademica() throws Exception {
        int databaseSizeBeforeUpdate = historiaAcademicaRepository.findAll().size();
        historiaAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoriaAcademicaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(historiaAcademica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistoriaAcademica in the database
        List<HistoriaAcademica> historiaAcademicaList = historiaAcademicaRepository.findAll();
        assertThat(historiaAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHistoriaAcademicaWithPatch() throws Exception {
        // Initialize the database
        historiaAcademicaRepository.saveAndFlush(historiaAcademica);

        int databaseSizeBeforeUpdate = historiaAcademicaRepository.findAll().size();

        // Update the historiaAcademica using partial update
        HistoriaAcademica partialUpdatedHistoriaAcademica = new HistoriaAcademica();
        partialUpdatedHistoriaAcademica.setId(historiaAcademica.getId());

        partialUpdatedHistoriaAcademica
            .idSemestre(UPDATED_ID_SEMESTRE)
            .promedioSemestre(UPDATED_PROMEDIO_SEMESTRE)
            .idTercio(UPDATED_ID_TERCIO)
            .situationAcademica(UPDATED_SITUATION_ACADEMICA);

        restHistoriaAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoriaAcademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistoriaAcademica))
            )
            .andExpect(status().isOk());

        // Validate the HistoriaAcademica in the database
        List<HistoriaAcademica> historiaAcademicaList = historiaAcademicaRepository.findAll();
        assertThat(historiaAcademicaList).hasSize(databaseSizeBeforeUpdate);
        HistoriaAcademica testHistoriaAcademica = historiaAcademicaList.get(historiaAcademicaList.size() - 1);
        assertThat(testHistoriaAcademica.getIdHistoriaAcademica()).isEqualTo(DEFAULT_ID_HISTORIA_ACADEMICA);
        assertThat(testHistoriaAcademica.getCedulaEstudiante()).isEqualTo(DEFAULT_CEDULA_ESTUDIANTE);
        assertThat(testHistoriaAcademica.getIdSemestre()).isEqualTo(UPDATED_ID_SEMESTRE);
        assertThat(testHistoriaAcademica.getCodigoPrograma()).isEqualTo(DEFAULT_CODIGO_PROGRAMA);
        assertThat(testHistoriaAcademica.getPromedioAcomulado()).isEqualTo(DEFAULT_PROMEDIO_ACOMULADO);
        assertThat(testHistoriaAcademica.getPromedioSemestre()).isEqualTo(UPDATED_PROMEDIO_SEMESTRE);
        assertThat(testHistoriaAcademica.getIdTercio()).isEqualTo(UPDATED_ID_TERCIO);
        assertThat(testHistoriaAcademica.getSituationAcademica()).isEqualTo(UPDATED_SITUATION_ACADEMICA);
        assertThat(testHistoriaAcademica.getStateSemestre()).isEqualTo(DEFAULT_STATE_SEMESTRE);
    }

    @Test
    @Transactional
    void fullUpdateHistoriaAcademicaWithPatch() throws Exception {
        // Initialize the database
        historiaAcademicaRepository.saveAndFlush(historiaAcademica);

        int databaseSizeBeforeUpdate = historiaAcademicaRepository.findAll().size();

        // Update the historiaAcademica using partial update
        HistoriaAcademica partialUpdatedHistoriaAcademica = new HistoriaAcademica();
        partialUpdatedHistoriaAcademica.setId(historiaAcademica.getId());

        partialUpdatedHistoriaAcademica
            .idHistoriaAcademica(UPDATED_ID_HISTORIA_ACADEMICA)
            .cedulaEstudiante(UPDATED_CEDULA_ESTUDIANTE)
            .idSemestre(UPDATED_ID_SEMESTRE)
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA)
            .promedioAcomulado(UPDATED_PROMEDIO_ACOMULADO)
            .promedioSemestre(UPDATED_PROMEDIO_SEMESTRE)
            .idTercio(UPDATED_ID_TERCIO)
            .situationAcademica(UPDATED_SITUATION_ACADEMICA)
            .stateSemestre(UPDATED_STATE_SEMESTRE);

        restHistoriaAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHistoriaAcademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHistoriaAcademica))
            )
            .andExpect(status().isOk());

        // Validate the HistoriaAcademica in the database
        List<HistoriaAcademica> historiaAcademicaList = historiaAcademicaRepository.findAll();
        assertThat(historiaAcademicaList).hasSize(databaseSizeBeforeUpdate);
        HistoriaAcademica testHistoriaAcademica = historiaAcademicaList.get(historiaAcademicaList.size() - 1);
        assertThat(testHistoriaAcademica.getIdHistoriaAcademica()).isEqualTo(UPDATED_ID_HISTORIA_ACADEMICA);
        assertThat(testHistoriaAcademica.getCedulaEstudiante()).isEqualTo(UPDATED_CEDULA_ESTUDIANTE);
        assertThat(testHistoriaAcademica.getIdSemestre()).isEqualTo(UPDATED_ID_SEMESTRE);
        assertThat(testHistoriaAcademica.getCodigoPrograma()).isEqualTo(UPDATED_CODIGO_PROGRAMA);
        assertThat(testHistoriaAcademica.getPromedioAcomulado()).isEqualTo(UPDATED_PROMEDIO_ACOMULADO);
        assertThat(testHistoriaAcademica.getPromedioSemestre()).isEqualTo(UPDATED_PROMEDIO_SEMESTRE);
        assertThat(testHistoriaAcademica.getIdTercio()).isEqualTo(UPDATED_ID_TERCIO);
        assertThat(testHistoriaAcademica.getSituationAcademica()).isEqualTo(UPDATED_SITUATION_ACADEMICA);
        assertThat(testHistoriaAcademica.getStateSemestre()).isEqualTo(UPDATED_STATE_SEMESTRE);
    }

    @Test
    @Transactional
    void patchNonExistingHistoriaAcademica() throws Exception {
        int databaseSizeBeforeUpdate = historiaAcademicaRepository.findAll().size();
        historiaAcademica.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHistoriaAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, historiaAcademica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historiaAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoriaAcademica in the database
        List<HistoriaAcademica> historiaAcademicaList = historiaAcademicaRepository.findAll();
        assertThat(historiaAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHistoriaAcademica() throws Exception {
        int databaseSizeBeforeUpdate = historiaAcademicaRepository.findAll().size();
        historiaAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoriaAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historiaAcademica))
            )
            .andExpect(status().isBadRequest());

        // Validate the HistoriaAcademica in the database
        List<HistoriaAcademica> historiaAcademicaList = historiaAcademicaRepository.findAll();
        assertThat(historiaAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHistoriaAcademica() throws Exception {
        int databaseSizeBeforeUpdate = historiaAcademicaRepository.findAll().size();
        historiaAcademica.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHistoriaAcademicaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(historiaAcademica))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HistoriaAcademica in the database
        List<HistoriaAcademica> historiaAcademicaList = historiaAcademicaRepository.findAll();
        assertThat(historiaAcademicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHistoriaAcademica() throws Exception {
        // Initialize the database
        historiaAcademicaRepository.saveAndFlush(historiaAcademica);

        int databaseSizeBeforeDelete = historiaAcademicaRepository.findAll().size();

        // Delete the historiaAcademica
        restHistoriaAcademicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, historiaAcademica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HistoriaAcademica> historiaAcademicaList = historiaAcademicaRepository.findAll();
        assertThat(historiaAcademicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

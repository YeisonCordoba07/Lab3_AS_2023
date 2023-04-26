package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.MateriaSemestre;
import com.udea.repository.MateriaSemestreRepository;
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
 * Integration tests for the {@link MateriaSemestreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MateriaSemestreResourceIT {

    private static final Integer DEFAULT_ID_MATERIA_SEMESTRE = 1;
    private static final Integer UPDATED_ID_MATERIA_SEMESTRE = 2;

    private static final String DEFAULT_CEDULA_ESTUDIANTE = "AAAAAAAAAA";
    private static final String UPDATED_CEDULA_ESTUDIANTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_SEMESTRE = 1;
    private static final Integer UPDATED_ID_SEMESTRE = 2;

    private static final Integer DEFAULT_CODIGO_MATERIA = 1;
    private static final Integer UPDATED_CODIGO_MATERIA = 2;

    private static final Float DEFAULT_NOTA_DEFINITIVA = 1F;
    private static final Float UPDATED_NOTA_DEFINITIVA = 2F;

    private static final String ENTITY_API_URL = "/api/materia-semestres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MateriaSemestreRepository materiaSemestreRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMateriaSemestreMockMvc;

    private MateriaSemestre materiaSemestre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MateriaSemestre createEntity(EntityManager em) {
        MateriaSemestre materiaSemestre = new MateriaSemestre()
            .idMateriaSemestre(DEFAULT_ID_MATERIA_SEMESTRE)
            .cedulaEstudiante(DEFAULT_CEDULA_ESTUDIANTE)
            .idSemestre(DEFAULT_ID_SEMESTRE)
            .codigoMateria(DEFAULT_CODIGO_MATERIA)
            .notaDefinitiva(DEFAULT_NOTA_DEFINITIVA);
        return materiaSemestre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MateriaSemestre createUpdatedEntity(EntityManager em) {
        MateriaSemestre materiaSemestre = new MateriaSemestre()
            .idMateriaSemestre(UPDATED_ID_MATERIA_SEMESTRE)
            .cedulaEstudiante(UPDATED_CEDULA_ESTUDIANTE)
            .idSemestre(UPDATED_ID_SEMESTRE)
            .codigoMateria(UPDATED_CODIGO_MATERIA)
            .notaDefinitiva(UPDATED_NOTA_DEFINITIVA);
        return materiaSemestre;
    }

    @BeforeEach
    public void initTest() {
        materiaSemestre = createEntity(em);
    }

    @Test
    @Transactional
    void createMateriaSemestre() throws Exception {
        int databaseSizeBeforeCreate = materiaSemestreRepository.findAll().size();
        // Create the MateriaSemestre
        restMateriaSemestreMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiaSemestre))
            )
            .andExpect(status().isCreated());

        // Validate the MateriaSemestre in the database
        List<MateriaSemestre> materiaSemestreList = materiaSemestreRepository.findAll();
        assertThat(materiaSemestreList).hasSize(databaseSizeBeforeCreate + 1);
        MateriaSemestre testMateriaSemestre = materiaSemestreList.get(materiaSemestreList.size() - 1);
        assertThat(testMateriaSemestre.getIdMateriaSemestre()).isEqualTo(DEFAULT_ID_MATERIA_SEMESTRE);
        assertThat(testMateriaSemestre.getCedulaEstudiante()).isEqualTo(DEFAULT_CEDULA_ESTUDIANTE);
        assertThat(testMateriaSemestre.getIdSemestre()).isEqualTo(DEFAULT_ID_SEMESTRE);
        assertThat(testMateriaSemestre.getCodigoMateria()).isEqualTo(DEFAULT_CODIGO_MATERIA);
        assertThat(testMateriaSemestre.getNotaDefinitiva()).isEqualTo(DEFAULT_NOTA_DEFINITIVA);
    }

    @Test
    @Transactional
    void createMateriaSemestreWithExistingId() throws Exception {
        // Create the MateriaSemestre with an existing ID
        materiaSemestre.setId(1L);

        int databaseSizeBeforeCreate = materiaSemestreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMateriaSemestreMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiaSemestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the MateriaSemestre in the database
        List<MateriaSemestre> materiaSemestreList = materiaSemestreRepository.findAll();
        assertThat(materiaSemestreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMateriaSemestres() throws Exception {
        // Initialize the database
        materiaSemestreRepository.saveAndFlush(materiaSemestre);

        // Get all the materiaSemestreList
        restMateriaSemestreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materiaSemestre.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMateriaSemestre").value(hasItem(DEFAULT_ID_MATERIA_SEMESTRE)))
            .andExpect(jsonPath("$.[*].cedulaEstudiante").value(hasItem(DEFAULT_CEDULA_ESTUDIANTE)))
            .andExpect(jsonPath("$.[*].idSemestre").value(hasItem(DEFAULT_ID_SEMESTRE)))
            .andExpect(jsonPath("$.[*].codigoMateria").value(hasItem(DEFAULT_CODIGO_MATERIA)))
            .andExpect(jsonPath("$.[*].notaDefinitiva").value(hasItem(DEFAULT_NOTA_DEFINITIVA.doubleValue())));
    }

    @Test
    @Transactional
    void getMateriaSemestre() throws Exception {
        // Initialize the database
        materiaSemestreRepository.saveAndFlush(materiaSemestre);

        // Get the materiaSemestre
        restMateriaSemestreMockMvc
            .perform(get(ENTITY_API_URL_ID, materiaSemestre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materiaSemestre.getId().intValue()))
            .andExpect(jsonPath("$.idMateriaSemestre").value(DEFAULT_ID_MATERIA_SEMESTRE))
            .andExpect(jsonPath("$.cedulaEstudiante").value(DEFAULT_CEDULA_ESTUDIANTE))
            .andExpect(jsonPath("$.idSemestre").value(DEFAULT_ID_SEMESTRE))
            .andExpect(jsonPath("$.codigoMateria").value(DEFAULT_CODIGO_MATERIA))
            .andExpect(jsonPath("$.notaDefinitiva").value(DEFAULT_NOTA_DEFINITIVA.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingMateriaSemestre() throws Exception {
        // Get the materiaSemestre
        restMateriaSemestreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMateriaSemestre() throws Exception {
        // Initialize the database
        materiaSemestreRepository.saveAndFlush(materiaSemestre);

        int databaseSizeBeforeUpdate = materiaSemestreRepository.findAll().size();

        // Update the materiaSemestre
        MateriaSemestre updatedMateriaSemestre = materiaSemestreRepository.findById(materiaSemestre.getId()).get();
        // Disconnect from session so that the updates on updatedMateriaSemestre are not directly saved in db
        em.detach(updatedMateriaSemestre);
        updatedMateriaSemestre
            .idMateriaSemestre(UPDATED_ID_MATERIA_SEMESTRE)
            .cedulaEstudiante(UPDATED_CEDULA_ESTUDIANTE)
            .idSemestre(UPDATED_ID_SEMESTRE)
            .codigoMateria(UPDATED_CODIGO_MATERIA)
            .notaDefinitiva(UPDATED_NOTA_DEFINITIVA);

        restMateriaSemestreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMateriaSemestre.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMateriaSemestre))
            )
            .andExpect(status().isOk());

        // Validate the MateriaSemestre in the database
        List<MateriaSemestre> materiaSemestreList = materiaSemestreRepository.findAll();
        assertThat(materiaSemestreList).hasSize(databaseSizeBeforeUpdate);
        MateriaSemestre testMateriaSemestre = materiaSemestreList.get(materiaSemestreList.size() - 1);
        assertThat(testMateriaSemestre.getIdMateriaSemestre()).isEqualTo(UPDATED_ID_MATERIA_SEMESTRE);
        assertThat(testMateriaSemestre.getCedulaEstudiante()).isEqualTo(UPDATED_CEDULA_ESTUDIANTE);
        assertThat(testMateriaSemestre.getIdSemestre()).isEqualTo(UPDATED_ID_SEMESTRE);
        assertThat(testMateriaSemestre.getCodigoMateria()).isEqualTo(UPDATED_CODIGO_MATERIA);
        assertThat(testMateriaSemestre.getNotaDefinitiva()).isEqualTo(UPDATED_NOTA_DEFINITIVA);
    }

    @Test
    @Transactional
    void putNonExistingMateriaSemestre() throws Exception {
        int databaseSizeBeforeUpdate = materiaSemestreRepository.findAll().size();
        materiaSemestre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMateriaSemestreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materiaSemestre.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materiaSemestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the MateriaSemestre in the database
        List<MateriaSemestre> materiaSemestreList = materiaSemestreRepository.findAll();
        assertThat(materiaSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMateriaSemestre() throws Exception {
        int databaseSizeBeforeUpdate = materiaSemestreRepository.findAll().size();
        materiaSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaSemestreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materiaSemestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the MateriaSemestre in the database
        List<MateriaSemestre> materiaSemestreList = materiaSemestreRepository.findAll();
        assertThat(materiaSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMateriaSemestre() throws Exception {
        int databaseSizeBeforeUpdate = materiaSemestreRepository.findAll().size();
        materiaSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaSemestreMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiaSemestre))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MateriaSemestre in the database
        List<MateriaSemestre> materiaSemestreList = materiaSemestreRepository.findAll();
        assertThat(materiaSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMateriaSemestreWithPatch() throws Exception {
        // Initialize the database
        materiaSemestreRepository.saveAndFlush(materiaSemestre);

        int databaseSizeBeforeUpdate = materiaSemestreRepository.findAll().size();

        // Update the materiaSemestre using partial update
        MateriaSemestre partialUpdatedMateriaSemestre = new MateriaSemestre();
        partialUpdatedMateriaSemestre.setId(materiaSemestre.getId());

        partialUpdatedMateriaSemestre
            .idMateriaSemestre(UPDATED_ID_MATERIA_SEMESTRE)
            .cedulaEstudiante(UPDATED_CEDULA_ESTUDIANTE)
            .notaDefinitiva(UPDATED_NOTA_DEFINITIVA);

        restMateriaSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMateriaSemestre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMateriaSemestre))
            )
            .andExpect(status().isOk());

        // Validate the MateriaSemestre in the database
        List<MateriaSemestre> materiaSemestreList = materiaSemestreRepository.findAll();
        assertThat(materiaSemestreList).hasSize(databaseSizeBeforeUpdate);
        MateriaSemestre testMateriaSemestre = materiaSemestreList.get(materiaSemestreList.size() - 1);
        assertThat(testMateriaSemestre.getIdMateriaSemestre()).isEqualTo(UPDATED_ID_MATERIA_SEMESTRE);
        assertThat(testMateriaSemestre.getCedulaEstudiante()).isEqualTo(UPDATED_CEDULA_ESTUDIANTE);
        assertThat(testMateriaSemestre.getIdSemestre()).isEqualTo(DEFAULT_ID_SEMESTRE);
        assertThat(testMateriaSemestre.getCodigoMateria()).isEqualTo(DEFAULT_CODIGO_MATERIA);
        assertThat(testMateriaSemestre.getNotaDefinitiva()).isEqualTo(UPDATED_NOTA_DEFINITIVA);
    }

    @Test
    @Transactional
    void fullUpdateMateriaSemestreWithPatch() throws Exception {
        // Initialize the database
        materiaSemestreRepository.saveAndFlush(materiaSemestre);

        int databaseSizeBeforeUpdate = materiaSemestreRepository.findAll().size();

        // Update the materiaSemestre using partial update
        MateriaSemestre partialUpdatedMateriaSemestre = new MateriaSemestre();
        partialUpdatedMateriaSemestre.setId(materiaSemestre.getId());

        partialUpdatedMateriaSemestre
            .idMateriaSemestre(UPDATED_ID_MATERIA_SEMESTRE)
            .cedulaEstudiante(UPDATED_CEDULA_ESTUDIANTE)
            .idSemestre(UPDATED_ID_SEMESTRE)
            .codigoMateria(UPDATED_CODIGO_MATERIA)
            .notaDefinitiva(UPDATED_NOTA_DEFINITIVA);

        restMateriaSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMateriaSemestre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMateriaSemestre))
            )
            .andExpect(status().isOk());

        // Validate the MateriaSemestre in the database
        List<MateriaSemestre> materiaSemestreList = materiaSemestreRepository.findAll();
        assertThat(materiaSemestreList).hasSize(databaseSizeBeforeUpdate);
        MateriaSemestre testMateriaSemestre = materiaSemestreList.get(materiaSemestreList.size() - 1);
        assertThat(testMateriaSemestre.getIdMateriaSemestre()).isEqualTo(UPDATED_ID_MATERIA_SEMESTRE);
        assertThat(testMateriaSemestre.getCedulaEstudiante()).isEqualTo(UPDATED_CEDULA_ESTUDIANTE);
        assertThat(testMateriaSemestre.getIdSemestre()).isEqualTo(UPDATED_ID_SEMESTRE);
        assertThat(testMateriaSemestre.getCodigoMateria()).isEqualTo(UPDATED_CODIGO_MATERIA);
        assertThat(testMateriaSemestre.getNotaDefinitiva()).isEqualTo(UPDATED_NOTA_DEFINITIVA);
    }

    @Test
    @Transactional
    void patchNonExistingMateriaSemestre() throws Exception {
        int databaseSizeBeforeUpdate = materiaSemestreRepository.findAll().size();
        materiaSemestre.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMateriaSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materiaSemestre.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materiaSemestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the MateriaSemestre in the database
        List<MateriaSemestre> materiaSemestreList = materiaSemestreRepository.findAll();
        assertThat(materiaSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMateriaSemestre() throws Exception {
        int databaseSizeBeforeUpdate = materiaSemestreRepository.findAll().size();
        materiaSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materiaSemestre))
            )
            .andExpect(status().isBadRequest());

        // Validate the MateriaSemestre in the database
        List<MateriaSemestre> materiaSemestreList = materiaSemestreRepository.findAll();
        assertThat(materiaSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMateriaSemestre() throws Exception {
        int databaseSizeBeforeUpdate = materiaSemestreRepository.findAll().size();
        materiaSemestre.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaSemestreMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materiaSemestre))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MateriaSemestre in the database
        List<MateriaSemestre> materiaSemestreList = materiaSemestreRepository.findAll();
        assertThat(materiaSemestreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMateriaSemestre() throws Exception {
        // Initialize the database
        materiaSemestreRepository.saveAndFlush(materiaSemestre);

        int databaseSizeBeforeDelete = materiaSemestreRepository.findAll().size();

        // Delete the materiaSemestre
        restMateriaSemestreMockMvc
            .perform(delete(ENTITY_API_URL_ID, materiaSemestre.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MateriaSemestre> materiaSemestreList = materiaSemestreRepository.findAll();
        assertThat(materiaSemestreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

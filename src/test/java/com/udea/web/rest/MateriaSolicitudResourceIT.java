package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.MateriaSolicitud;
import com.udea.repository.MateriaSolicitudRepository;
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
 * Integration tests for the {@link MateriaSolicitudResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MateriaSolicitudResourceIT {

    private static final Integer DEFAULT_ID_SOLICITUD = 1;
    private static final Integer UPDATED_ID_SOLICITUD = 2;

    private static final Integer DEFAULT_CODIGO_MATERIA = 1;
    private static final Integer UPDATED_CODIGO_MATERIA = 2;

    private static final Integer DEFAULT_ID_SEMESTRE_PASADA = 1;
    private static final Integer UPDATED_ID_SEMESTRE_PASADA = 2;

    private static final Float DEFAULT_NOTA_DEFINITIVA = 1F;
    private static final Float UPDATED_NOTA_DEFINITIVA = 2F;

    private static final String DEFAULT_CEDULA_ESTUFIANTE = "AAAAAAAAAA";
    private static final String UPDATED_CEDULA_ESTUFIANTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/materia-solicituds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MateriaSolicitudRepository materiaSolicitudRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMateriaSolicitudMockMvc;

    private MateriaSolicitud materiaSolicitud;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MateriaSolicitud createEntity(EntityManager em) {
        MateriaSolicitud materiaSolicitud = new MateriaSolicitud()
            .idSolicitud(DEFAULT_ID_SOLICITUD)
            .codigoMateria(DEFAULT_CODIGO_MATERIA)
            .idSemestrePasada(DEFAULT_ID_SEMESTRE_PASADA)
            .notaDefinitiva(DEFAULT_NOTA_DEFINITIVA)
            .cedulaEstufiante(DEFAULT_CEDULA_ESTUFIANTE);
        return materiaSolicitud;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MateriaSolicitud createUpdatedEntity(EntityManager em) {
        MateriaSolicitud materiaSolicitud = new MateriaSolicitud()
            .idSolicitud(UPDATED_ID_SOLICITUD)
            .codigoMateria(UPDATED_CODIGO_MATERIA)
            .idSemestrePasada(UPDATED_ID_SEMESTRE_PASADA)
            .notaDefinitiva(UPDATED_NOTA_DEFINITIVA)
            .cedulaEstufiante(UPDATED_CEDULA_ESTUFIANTE);
        return materiaSolicitud;
    }

    @BeforeEach
    public void initTest() {
        materiaSolicitud = createEntity(em);
    }

    @Test
    @Transactional
    void createMateriaSolicitud() throws Exception {
        int databaseSizeBeforeCreate = materiaSolicitudRepository.findAll().size();
        // Create the MateriaSolicitud
        restMateriaSolicitudMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiaSolicitud))
            )
            .andExpect(status().isCreated());

        // Validate the MateriaSolicitud in the database
        List<MateriaSolicitud> materiaSolicitudList = materiaSolicitudRepository.findAll();
        assertThat(materiaSolicitudList).hasSize(databaseSizeBeforeCreate + 1);
        MateriaSolicitud testMateriaSolicitud = materiaSolicitudList.get(materiaSolicitudList.size() - 1);
        assertThat(testMateriaSolicitud.getIdSolicitud()).isEqualTo(DEFAULT_ID_SOLICITUD);
        assertThat(testMateriaSolicitud.getCodigoMateria()).isEqualTo(DEFAULT_CODIGO_MATERIA);
        assertThat(testMateriaSolicitud.getIdSemestrePasada()).isEqualTo(DEFAULT_ID_SEMESTRE_PASADA);
        assertThat(testMateriaSolicitud.getNotaDefinitiva()).isEqualTo(DEFAULT_NOTA_DEFINITIVA);
        assertThat(testMateriaSolicitud.getCedulaEstufiante()).isEqualTo(DEFAULT_CEDULA_ESTUFIANTE);
    }

    @Test
    @Transactional
    void createMateriaSolicitudWithExistingId() throws Exception {
        // Create the MateriaSolicitud with an existing ID
        materiaSolicitud.setId(1L);

        int databaseSizeBeforeCreate = materiaSolicitudRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMateriaSolicitudMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiaSolicitud))
            )
            .andExpect(status().isBadRequest());

        // Validate the MateriaSolicitud in the database
        List<MateriaSolicitud> materiaSolicitudList = materiaSolicitudRepository.findAll();
        assertThat(materiaSolicitudList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMateriaSolicituds() throws Exception {
        // Initialize the database
        materiaSolicitudRepository.saveAndFlush(materiaSolicitud);

        // Get all the materiaSolicitudList
        restMateriaSolicitudMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materiaSolicitud.getId().intValue())))
            .andExpect(jsonPath("$.[*].idSolicitud").value(hasItem(DEFAULT_ID_SOLICITUD)))
            .andExpect(jsonPath("$.[*].codigoMateria").value(hasItem(DEFAULT_CODIGO_MATERIA)))
            .andExpect(jsonPath("$.[*].idSemestrePasada").value(hasItem(DEFAULT_ID_SEMESTRE_PASADA)))
            .andExpect(jsonPath("$.[*].notaDefinitiva").value(hasItem(DEFAULT_NOTA_DEFINITIVA.doubleValue())))
            .andExpect(jsonPath("$.[*].cedulaEstufiante").value(hasItem(DEFAULT_CEDULA_ESTUFIANTE)));
    }

    @Test
    @Transactional
    void getMateriaSolicitud() throws Exception {
        // Initialize the database
        materiaSolicitudRepository.saveAndFlush(materiaSolicitud);

        // Get the materiaSolicitud
        restMateriaSolicitudMockMvc
            .perform(get(ENTITY_API_URL_ID, materiaSolicitud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materiaSolicitud.getId().intValue()))
            .andExpect(jsonPath("$.idSolicitud").value(DEFAULT_ID_SOLICITUD))
            .andExpect(jsonPath("$.codigoMateria").value(DEFAULT_CODIGO_MATERIA))
            .andExpect(jsonPath("$.idSemestrePasada").value(DEFAULT_ID_SEMESTRE_PASADA))
            .andExpect(jsonPath("$.notaDefinitiva").value(DEFAULT_NOTA_DEFINITIVA.doubleValue()))
            .andExpect(jsonPath("$.cedulaEstufiante").value(DEFAULT_CEDULA_ESTUFIANTE));
    }

    @Test
    @Transactional
    void getNonExistingMateriaSolicitud() throws Exception {
        // Get the materiaSolicitud
        restMateriaSolicitudMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMateriaSolicitud() throws Exception {
        // Initialize the database
        materiaSolicitudRepository.saveAndFlush(materiaSolicitud);

        int databaseSizeBeforeUpdate = materiaSolicitudRepository.findAll().size();

        // Update the materiaSolicitud
        MateriaSolicitud updatedMateriaSolicitud = materiaSolicitudRepository.findById(materiaSolicitud.getId()).get();
        // Disconnect from session so that the updates on updatedMateriaSolicitud are not directly saved in db
        em.detach(updatedMateriaSolicitud);
        updatedMateriaSolicitud
            .idSolicitud(UPDATED_ID_SOLICITUD)
            .codigoMateria(UPDATED_CODIGO_MATERIA)
            .idSemestrePasada(UPDATED_ID_SEMESTRE_PASADA)
            .notaDefinitiva(UPDATED_NOTA_DEFINITIVA)
            .cedulaEstufiante(UPDATED_CEDULA_ESTUFIANTE);

        restMateriaSolicitudMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMateriaSolicitud.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMateriaSolicitud))
            )
            .andExpect(status().isOk());

        // Validate the MateriaSolicitud in the database
        List<MateriaSolicitud> materiaSolicitudList = materiaSolicitudRepository.findAll();
        assertThat(materiaSolicitudList).hasSize(databaseSizeBeforeUpdate);
        MateriaSolicitud testMateriaSolicitud = materiaSolicitudList.get(materiaSolicitudList.size() - 1);
        assertThat(testMateriaSolicitud.getIdSolicitud()).isEqualTo(UPDATED_ID_SOLICITUD);
        assertThat(testMateriaSolicitud.getCodigoMateria()).isEqualTo(UPDATED_CODIGO_MATERIA);
        assertThat(testMateriaSolicitud.getIdSemestrePasada()).isEqualTo(UPDATED_ID_SEMESTRE_PASADA);
        assertThat(testMateriaSolicitud.getNotaDefinitiva()).isEqualTo(UPDATED_NOTA_DEFINITIVA);
        assertThat(testMateriaSolicitud.getCedulaEstufiante()).isEqualTo(UPDATED_CEDULA_ESTUFIANTE);
    }

    @Test
    @Transactional
    void putNonExistingMateriaSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = materiaSolicitudRepository.findAll().size();
        materiaSolicitud.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMateriaSolicitudMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materiaSolicitud.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materiaSolicitud))
            )
            .andExpect(status().isBadRequest());

        // Validate the MateriaSolicitud in the database
        List<MateriaSolicitud> materiaSolicitudList = materiaSolicitudRepository.findAll();
        assertThat(materiaSolicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMateriaSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = materiaSolicitudRepository.findAll().size();
        materiaSolicitud.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaSolicitudMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materiaSolicitud))
            )
            .andExpect(status().isBadRequest());

        // Validate the MateriaSolicitud in the database
        List<MateriaSolicitud> materiaSolicitudList = materiaSolicitudRepository.findAll();
        assertThat(materiaSolicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMateriaSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = materiaSolicitudRepository.findAll().size();
        materiaSolicitud.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaSolicitudMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materiaSolicitud))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MateriaSolicitud in the database
        List<MateriaSolicitud> materiaSolicitudList = materiaSolicitudRepository.findAll();
        assertThat(materiaSolicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMateriaSolicitudWithPatch() throws Exception {
        // Initialize the database
        materiaSolicitudRepository.saveAndFlush(materiaSolicitud);

        int databaseSizeBeforeUpdate = materiaSolicitudRepository.findAll().size();

        // Update the materiaSolicitud using partial update
        MateriaSolicitud partialUpdatedMateriaSolicitud = new MateriaSolicitud();
        partialUpdatedMateriaSolicitud.setId(materiaSolicitud.getId());

        partialUpdatedMateriaSolicitud.codigoMateria(UPDATED_CODIGO_MATERIA).cedulaEstufiante(UPDATED_CEDULA_ESTUFIANTE);

        restMateriaSolicitudMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMateriaSolicitud.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMateriaSolicitud))
            )
            .andExpect(status().isOk());

        // Validate the MateriaSolicitud in the database
        List<MateriaSolicitud> materiaSolicitudList = materiaSolicitudRepository.findAll();
        assertThat(materiaSolicitudList).hasSize(databaseSizeBeforeUpdate);
        MateriaSolicitud testMateriaSolicitud = materiaSolicitudList.get(materiaSolicitudList.size() - 1);
        assertThat(testMateriaSolicitud.getIdSolicitud()).isEqualTo(DEFAULT_ID_SOLICITUD);
        assertThat(testMateriaSolicitud.getCodigoMateria()).isEqualTo(UPDATED_CODIGO_MATERIA);
        assertThat(testMateriaSolicitud.getIdSemestrePasada()).isEqualTo(DEFAULT_ID_SEMESTRE_PASADA);
        assertThat(testMateriaSolicitud.getNotaDefinitiva()).isEqualTo(DEFAULT_NOTA_DEFINITIVA);
        assertThat(testMateriaSolicitud.getCedulaEstufiante()).isEqualTo(UPDATED_CEDULA_ESTUFIANTE);
    }

    @Test
    @Transactional
    void fullUpdateMateriaSolicitudWithPatch() throws Exception {
        // Initialize the database
        materiaSolicitudRepository.saveAndFlush(materiaSolicitud);

        int databaseSizeBeforeUpdate = materiaSolicitudRepository.findAll().size();

        // Update the materiaSolicitud using partial update
        MateriaSolicitud partialUpdatedMateriaSolicitud = new MateriaSolicitud();
        partialUpdatedMateriaSolicitud.setId(materiaSolicitud.getId());

        partialUpdatedMateriaSolicitud
            .idSolicitud(UPDATED_ID_SOLICITUD)
            .codigoMateria(UPDATED_CODIGO_MATERIA)
            .idSemestrePasada(UPDATED_ID_SEMESTRE_PASADA)
            .notaDefinitiva(UPDATED_NOTA_DEFINITIVA)
            .cedulaEstufiante(UPDATED_CEDULA_ESTUFIANTE);

        restMateriaSolicitudMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMateriaSolicitud.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMateriaSolicitud))
            )
            .andExpect(status().isOk());

        // Validate the MateriaSolicitud in the database
        List<MateriaSolicitud> materiaSolicitudList = materiaSolicitudRepository.findAll();
        assertThat(materiaSolicitudList).hasSize(databaseSizeBeforeUpdate);
        MateriaSolicitud testMateriaSolicitud = materiaSolicitudList.get(materiaSolicitudList.size() - 1);
        assertThat(testMateriaSolicitud.getIdSolicitud()).isEqualTo(UPDATED_ID_SOLICITUD);
        assertThat(testMateriaSolicitud.getCodigoMateria()).isEqualTo(UPDATED_CODIGO_MATERIA);
        assertThat(testMateriaSolicitud.getIdSemestrePasada()).isEqualTo(UPDATED_ID_SEMESTRE_PASADA);
        assertThat(testMateriaSolicitud.getNotaDefinitiva()).isEqualTo(UPDATED_NOTA_DEFINITIVA);
        assertThat(testMateriaSolicitud.getCedulaEstufiante()).isEqualTo(UPDATED_CEDULA_ESTUFIANTE);
    }

    @Test
    @Transactional
    void patchNonExistingMateriaSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = materiaSolicitudRepository.findAll().size();
        materiaSolicitud.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMateriaSolicitudMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materiaSolicitud.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materiaSolicitud))
            )
            .andExpect(status().isBadRequest());

        // Validate the MateriaSolicitud in the database
        List<MateriaSolicitud> materiaSolicitudList = materiaSolicitudRepository.findAll();
        assertThat(materiaSolicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMateriaSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = materiaSolicitudRepository.findAll().size();
        materiaSolicitud.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaSolicitudMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materiaSolicitud))
            )
            .andExpect(status().isBadRequest());

        // Validate the MateriaSolicitud in the database
        List<MateriaSolicitud> materiaSolicitudList = materiaSolicitudRepository.findAll();
        assertThat(materiaSolicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMateriaSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = materiaSolicitudRepository.findAll().size();
        materiaSolicitud.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaSolicitudMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materiaSolicitud))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MateriaSolicitud in the database
        List<MateriaSolicitud> materiaSolicitudList = materiaSolicitudRepository.findAll();
        assertThat(materiaSolicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMateriaSolicitud() throws Exception {
        // Initialize the database
        materiaSolicitudRepository.saveAndFlush(materiaSolicitud);

        int databaseSizeBeforeDelete = materiaSolicitudRepository.findAll().size();

        // Delete the materiaSolicitud
        restMateriaSolicitudMockMvc
            .perform(delete(ENTITY_API_URL_ID, materiaSolicitud.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MateriaSolicitud> materiaSolicitudList = materiaSolicitudRepository.findAll();
        assertThat(materiaSolicitudList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

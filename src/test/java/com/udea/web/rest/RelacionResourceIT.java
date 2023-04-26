package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.Relacion;
import com.udea.repository.RelacionRepository;
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
 * Integration tests for the {@link RelacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RelacionResourceIT {

    private static final Integer DEFAULT_CODIGO_MATERIA = 1;
    private static final Integer UPDATED_CODIGO_MATERIA = 2;

    private static final Integer DEFAULT_CODIGO_MATERIA_RELACIONADA = 1;
    private static final Integer UPDATED_CODIGO_MATERIA_RELACIONADA = 2;

    private static final String DEFAULT_TIPO_RELACION = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_RELACION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/relacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RelacionRepository relacionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelacionMockMvc;

    private Relacion relacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relacion createEntity(EntityManager em) {
        Relacion relacion = new Relacion()
            .codigoMateria(DEFAULT_CODIGO_MATERIA)
            .codigoMateriaRelacionada(DEFAULT_CODIGO_MATERIA_RELACIONADA)
            .tipoRelacion(DEFAULT_TIPO_RELACION);
        return relacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Relacion createUpdatedEntity(EntityManager em) {
        Relacion relacion = new Relacion()
            .codigoMateria(UPDATED_CODIGO_MATERIA)
            .codigoMateriaRelacionada(UPDATED_CODIGO_MATERIA_RELACIONADA)
            .tipoRelacion(UPDATED_TIPO_RELACION);
        return relacion;
    }

    @BeforeEach
    public void initTest() {
        relacion = createEntity(em);
    }

    @Test
    @Transactional
    void createRelacion() throws Exception {
        int databaseSizeBeforeCreate = relacionRepository.findAll().size();
        // Create the Relacion
        restRelacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relacion)))
            .andExpect(status().isCreated());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeCreate + 1);
        Relacion testRelacion = relacionList.get(relacionList.size() - 1);
        assertThat(testRelacion.getCodigoMateria()).isEqualTo(DEFAULT_CODIGO_MATERIA);
        assertThat(testRelacion.getCodigoMateriaRelacionada()).isEqualTo(DEFAULT_CODIGO_MATERIA_RELACIONADA);
        assertThat(testRelacion.getTipoRelacion()).isEqualTo(DEFAULT_TIPO_RELACION);
    }

    @Test
    @Transactional
    void createRelacionWithExistingId() throws Exception {
        // Create the Relacion with an existing ID
        relacion.setId(1L);

        int databaseSizeBeforeCreate = relacionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relacion)))
            .andExpect(status().isBadRequest());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRelacions() throws Exception {
        // Initialize the database
        relacionRepository.saveAndFlush(relacion);

        // Get all the relacionList
        restRelacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoMateria").value(hasItem(DEFAULT_CODIGO_MATERIA)))
            .andExpect(jsonPath("$.[*].codigoMateriaRelacionada").value(hasItem(DEFAULT_CODIGO_MATERIA_RELACIONADA)))
            .andExpect(jsonPath("$.[*].tipoRelacion").value(hasItem(DEFAULT_TIPO_RELACION)));
    }

    @Test
    @Transactional
    void getRelacion() throws Exception {
        // Initialize the database
        relacionRepository.saveAndFlush(relacion);

        // Get the relacion
        restRelacionMockMvc
            .perform(get(ENTITY_API_URL_ID, relacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relacion.getId().intValue()))
            .andExpect(jsonPath("$.codigoMateria").value(DEFAULT_CODIGO_MATERIA))
            .andExpect(jsonPath("$.codigoMateriaRelacionada").value(DEFAULT_CODIGO_MATERIA_RELACIONADA))
            .andExpect(jsonPath("$.tipoRelacion").value(DEFAULT_TIPO_RELACION));
    }

    @Test
    @Transactional
    void getNonExistingRelacion() throws Exception {
        // Get the relacion
        restRelacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRelacion() throws Exception {
        // Initialize the database
        relacionRepository.saveAndFlush(relacion);

        int databaseSizeBeforeUpdate = relacionRepository.findAll().size();

        // Update the relacion
        Relacion updatedRelacion = relacionRepository.findById(relacion.getId()).get();
        // Disconnect from session so that the updates on updatedRelacion are not directly saved in db
        em.detach(updatedRelacion);
        updatedRelacion
            .codigoMateria(UPDATED_CODIGO_MATERIA)
            .codigoMateriaRelacionada(UPDATED_CODIGO_MATERIA_RELACIONADA)
            .tipoRelacion(UPDATED_TIPO_RELACION);

        restRelacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRelacion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRelacion))
            )
            .andExpect(status().isOk());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeUpdate);
        Relacion testRelacion = relacionList.get(relacionList.size() - 1);
        assertThat(testRelacion.getCodigoMateria()).isEqualTo(UPDATED_CODIGO_MATERIA);
        assertThat(testRelacion.getCodigoMateriaRelacionada()).isEqualTo(UPDATED_CODIGO_MATERIA_RELACIONADA);
        assertThat(testRelacion.getTipoRelacion()).isEqualTo(UPDATED_TIPO_RELACION);
    }

    @Test
    @Transactional
    void putNonExistingRelacion() throws Exception {
        int databaseSizeBeforeUpdate = relacionRepository.findAll().size();
        relacion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relacion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRelacion() throws Exception {
        int databaseSizeBeforeUpdate = relacionRepository.findAll().size();
        relacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRelacion() throws Exception {
        int databaseSizeBeforeUpdate = relacionRepository.findAll().size();
        relacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelacionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relacion)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRelacionWithPatch() throws Exception {
        // Initialize the database
        relacionRepository.saveAndFlush(relacion);

        int databaseSizeBeforeUpdate = relacionRepository.findAll().size();

        // Update the relacion using partial update
        Relacion partialUpdatedRelacion = new Relacion();
        partialUpdatedRelacion.setId(relacion.getId());

        partialUpdatedRelacion.codigoMateria(UPDATED_CODIGO_MATERIA);

        restRelacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelacion))
            )
            .andExpect(status().isOk());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeUpdate);
        Relacion testRelacion = relacionList.get(relacionList.size() - 1);
        assertThat(testRelacion.getCodigoMateria()).isEqualTo(UPDATED_CODIGO_MATERIA);
        assertThat(testRelacion.getCodigoMateriaRelacionada()).isEqualTo(DEFAULT_CODIGO_MATERIA_RELACIONADA);
        assertThat(testRelacion.getTipoRelacion()).isEqualTo(DEFAULT_TIPO_RELACION);
    }

    @Test
    @Transactional
    void fullUpdateRelacionWithPatch() throws Exception {
        // Initialize the database
        relacionRepository.saveAndFlush(relacion);

        int databaseSizeBeforeUpdate = relacionRepository.findAll().size();

        // Update the relacion using partial update
        Relacion partialUpdatedRelacion = new Relacion();
        partialUpdatedRelacion.setId(relacion.getId());

        partialUpdatedRelacion
            .codigoMateria(UPDATED_CODIGO_MATERIA)
            .codigoMateriaRelacionada(UPDATED_CODIGO_MATERIA_RELACIONADA)
            .tipoRelacion(UPDATED_TIPO_RELACION);

        restRelacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelacion))
            )
            .andExpect(status().isOk());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeUpdate);
        Relacion testRelacion = relacionList.get(relacionList.size() - 1);
        assertThat(testRelacion.getCodigoMateria()).isEqualTo(UPDATED_CODIGO_MATERIA);
        assertThat(testRelacion.getCodigoMateriaRelacionada()).isEqualTo(UPDATED_CODIGO_MATERIA_RELACIONADA);
        assertThat(testRelacion.getTipoRelacion()).isEqualTo(UPDATED_TIPO_RELACION);
    }

    @Test
    @Transactional
    void patchNonExistingRelacion() throws Exception {
        int databaseSizeBeforeUpdate = relacionRepository.findAll().size();
        relacion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, relacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRelacion() throws Exception {
        int databaseSizeBeforeUpdate = relacionRepository.findAll().size();
        relacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRelacion() throws Exception {
        int databaseSizeBeforeUpdate = relacionRepository.findAll().size();
        relacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelacionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(relacion)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Relacion in the database
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRelacion() throws Exception {
        // Initialize the database
        relacionRepository.saveAndFlush(relacion);

        int databaseSizeBeforeDelete = relacionRepository.findAll().size();

        // Delete the relacion
        restRelacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, relacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Relacion> relacionList = relacionRepository.findAll();
        assertThat(relacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.Materia;
import com.udea.repository.MateriaRepository;
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
 * Integration tests for the {@link MateriaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MateriaResourceIT {

    private static final Integer DEFAULT_CODIGO_MATERIA = 1;
    private static final Integer UPDATED_CODIGO_MATERIA = 2;

    private static final String DEFAULT_NOMBRE_MATERIA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_MATERIA = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDITOS = 1;
    private static final Integer UPDATED_CREDITOS = 2;

    private static final String ENTITY_API_URL = "/api/materias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMateriaMockMvc;

    private Materia materia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materia createEntity(EntityManager em) {
        Materia materia = new Materia()
            .codigoMateria(DEFAULT_CODIGO_MATERIA)
            .nombreMateria(DEFAULT_NOMBRE_MATERIA)
            .creditos(DEFAULT_CREDITOS);
        return materia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materia createUpdatedEntity(EntityManager em) {
        Materia materia = new Materia()
            .codigoMateria(UPDATED_CODIGO_MATERIA)
            .nombreMateria(UPDATED_NOMBRE_MATERIA)
            .creditos(UPDATED_CREDITOS);
        return materia;
    }

    @BeforeEach
    public void initTest() {
        materia = createEntity(em);
    }

    @Test
    @Transactional
    void createMateria() throws Exception {
        int databaseSizeBeforeCreate = materiaRepository.findAll().size();
        // Create the Materia
        restMateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materia)))
            .andExpect(status().isCreated());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeCreate + 1);
        Materia testMateria = materiaList.get(materiaList.size() - 1);
        assertThat(testMateria.getCodigoMateria()).isEqualTo(DEFAULT_CODIGO_MATERIA);
        assertThat(testMateria.getNombreMateria()).isEqualTo(DEFAULT_NOMBRE_MATERIA);
        assertThat(testMateria.getCreditos()).isEqualTo(DEFAULT_CREDITOS);
    }

    @Test
    @Transactional
    void createMateriaWithExistingId() throws Exception {
        // Create the Materia with an existing ID
        materia.setId(1L);

        int databaseSizeBeforeCreate = materiaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMateriaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materia)))
            .andExpect(status().isBadRequest());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMaterias() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get all the materiaList
        restMateriaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materia.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoMateria").value(hasItem(DEFAULT_CODIGO_MATERIA)))
            .andExpect(jsonPath("$.[*].nombreMateria").value(hasItem(DEFAULT_NOMBRE_MATERIA)))
            .andExpect(jsonPath("$.[*].creditos").value(hasItem(DEFAULT_CREDITOS)));
    }

    @Test
    @Transactional
    void getMateria() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        // Get the materia
        restMateriaMockMvc
            .perform(get(ENTITY_API_URL_ID, materia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materia.getId().intValue()))
            .andExpect(jsonPath("$.codigoMateria").value(DEFAULT_CODIGO_MATERIA))
            .andExpect(jsonPath("$.nombreMateria").value(DEFAULT_NOMBRE_MATERIA))
            .andExpect(jsonPath("$.creditos").value(DEFAULT_CREDITOS));
    }

    @Test
    @Transactional
    void getNonExistingMateria() throws Exception {
        // Get the materia
        restMateriaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMateria() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();

        // Update the materia
        Materia updatedMateria = materiaRepository.findById(materia.getId()).get();
        // Disconnect from session so that the updates on updatedMateria are not directly saved in db
        em.detach(updatedMateria);
        updatedMateria.codigoMateria(UPDATED_CODIGO_MATERIA).nombreMateria(UPDATED_NOMBRE_MATERIA).creditos(UPDATED_CREDITOS);

        restMateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMateria.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMateria))
            )
            .andExpect(status().isOk());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
        Materia testMateria = materiaList.get(materiaList.size() - 1);
        assertThat(testMateria.getCodigoMateria()).isEqualTo(UPDATED_CODIGO_MATERIA);
        assertThat(testMateria.getNombreMateria()).isEqualTo(UPDATED_NOMBRE_MATERIA);
        assertThat(testMateria.getCreditos()).isEqualTo(UPDATED_CREDITOS);
    }

    @Test
    @Transactional
    void putNonExistingMateria() throws Exception {
        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();
        materia.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMateria() throws Exception {
        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();
        materia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMateria() throws Exception {
        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();
        materia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMateriaWithPatch() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();

        // Update the materia using partial update
        Materia partialUpdatedMateria = new Materia();
        partialUpdatedMateria.setId(materia.getId());

        partialUpdatedMateria.codigoMateria(UPDATED_CODIGO_MATERIA).nombreMateria(UPDATED_NOMBRE_MATERIA);

        restMateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMateria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMateria))
            )
            .andExpect(status().isOk());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
        Materia testMateria = materiaList.get(materiaList.size() - 1);
        assertThat(testMateria.getCodigoMateria()).isEqualTo(UPDATED_CODIGO_MATERIA);
        assertThat(testMateria.getNombreMateria()).isEqualTo(UPDATED_NOMBRE_MATERIA);
        assertThat(testMateria.getCreditos()).isEqualTo(DEFAULT_CREDITOS);
    }

    @Test
    @Transactional
    void fullUpdateMateriaWithPatch() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();

        // Update the materia using partial update
        Materia partialUpdatedMateria = new Materia();
        partialUpdatedMateria.setId(materia.getId());

        partialUpdatedMateria.codigoMateria(UPDATED_CODIGO_MATERIA).nombreMateria(UPDATED_NOMBRE_MATERIA).creditos(UPDATED_CREDITOS);

        restMateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMateria.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMateria))
            )
            .andExpect(status().isOk());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
        Materia testMateria = materiaList.get(materiaList.size() - 1);
        assertThat(testMateria.getCodigoMateria()).isEqualTo(UPDATED_CODIGO_MATERIA);
        assertThat(testMateria.getNombreMateria()).isEqualTo(UPDATED_NOMBRE_MATERIA);
        assertThat(testMateria.getCreditos()).isEqualTo(UPDATED_CREDITOS);
    }

    @Test
    @Transactional
    void patchNonExistingMateria() throws Exception {
        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();
        materia.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMateria() throws Exception {
        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();
        materia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMateria() throws Exception {
        int databaseSizeBeforeUpdate = materiaRepository.findAll().size();
        materia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMateriaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(materia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Materia in the database
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMateria() throws Exception {
        // Initialize the database
        materiaRepository.saveAndFlush(materia);

        int databaseSizeBeforeDelete = materiaRepository.findAll().size();

        // Delete the materia
        restMateriaMockMvc
            .perform(delete(ENTITY_API_URL_ID, materia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Materia> materiaList = materiaRepository.findAll();
        assertThat(materiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

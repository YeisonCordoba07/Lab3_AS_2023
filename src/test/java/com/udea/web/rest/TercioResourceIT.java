package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.Tercio;
import com.udea.repository.TercioRepository;
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
 * Integration tests for the {@link TercioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TercioResourceIT {

    private static final Integer DEFAULT_ID_TERCIO = 1;
    private static final Integer UPDATED_ID_TERCIO = 2;

    private static final String DEFAULT_TERCIO_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_TERCIO_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tercios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TercioRepository tercioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTercioMockMvc;

    private Tercio tercio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tercio createEntity(EntityManager em) {
        Tercio tercio = new Tercio().idTercio(DEFAULT_ID_TERCIO).tercioDescription(DEFAULT_TERCIO_DESCRIPTION);
        return tercio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tercio createUpdatedEntity(EntityManager em) {
        Tercio tercio = new Tercio().idTercio(UPDATED_ID_TERCIO).tercioDescription(UPDATED_TERCIO_DESCRIPTION);
        return tercio;
    }

    @BeforeEach
    public void initTest() {
        tercio = createEntity(em);
    }

    @Test
    @Transactional
    void createTercio() throws Exception {
        int databaseSizeBeforeCreate = tercioRepository.findAll().size();
        // Create the Tercio
        restTercioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tercio)))
            .andExpect(status().isCreated());

        // Validate the Tercio in the database
        List<Tercio> tercioList = tercioRepository.findAll();
        assertThat(tercioList).hasSize(databaseSizeBeforeCreate + 1);
        Tercio testTercio = tercioList.get(tercioList.size() - 1);
        assertThat(testTercio.getIdTercio()).isEqualTo(DEFAULT_ID_TERCIO);
        assertThat(testTercio.getTercioDescription()).isEqualTo(DEFAULT_TERCIO_DESCRIPTION);
    }

    @Test
    @Transactional
    void createTercioWithExistingId() throws Exception {
        // Create the Tercio with an existing ID
        tercio.setId(1L);

        int databaseSizeBeforeCreate = tercioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTercioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tercio)))
            .andExpect(status().isBadRequest());

        // Validate the Tercio in the database
        List<Tercio> tercioList = tercioRepository.findAll();
        assertThat(tercioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTercios() throws Exception {
        // Initialize the database
        tercioRepository.saveAndFlush(tercio);

        // Get all the tercioList
        restTercioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tercio.getId().intValue())))
            .andExpect(jsonPath("$.[*].idTercio").value(hasItem(DEFAULT_ID_TERCIO)))
            .andExpect(jsonPath("$.[*].tercioDescription").value(hasItem(DEFAULT_TERCIO_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getTercio() throws Exception {
        // Initialize the database
        tercioRepository.saveAndFlush(tercio);

        // Get the tercio
        restTercioMockMvc
            .perform(get(ENTITY_API_URL_ID, tercio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tercio.getId().intValue()))
            .andExpect(jsonPath("$.idTercio").value(DEFAULT_ID_TERCIO))
            .andExpect(jsonPath("$.tercioDescription").value(DEFAULT_TERCIO_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingTercio() throws Exception {
        // Get the tercio
        restTercioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTercio() throws Exception {
        // Initialize the database
        tercioRepository.saveAndFlush(tercio);

        int databaseSizeBeforeUpdate = tercioRepository.findAll().size();

        // Update the tercio
        Tercio updatedTercio = tercioRepository.findById(tercio.getId()).get();
        // Disconnect from session so that the updates on updatedTercio are not directly saved in db
        em.detach(updatedTercio);
        updatedTercio.idTercio(UPDATED_ID_TERCIO).tercioDescription(UPDATED_TERCIO_DESCRIPTION);

        restTercioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTercio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTercio))
            )
            .andExpect(status().isOk());

        // Validate the Tercio in the database
        List<Tercio> tercioList = tercioRepository.findAll();
        assertThat(tercioList).hasSize(databaseSizeBeforeUpdate);
        Tercio testTercio = tercioList.get(tercioList.size() - 1);
        assertThat(testTercio.getIdTercio()).isEqualTo(UPDATED_ID_TERCIO);
        assertThat(testTercio.getTercioDescription()).isEqualTo(UPDATED_TERCIO_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingTercio() throws Exception {
        int databaseSizeBeforeUpdate = tercioRepository.findAll().size();
        tercio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTercioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tercio.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tercio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tercio in the database
        List<Tercio> tercioList = tercioRepository.findAll();
        assertThat(tercioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTercio() throws Exception {
        int databaseSizeBeforeUpdate = tercioRepository.findAll().size();
        tercio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTercioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tercio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tercio in the database
        List<Tercio> tercioList = tercioRepository.findAll();
        assertThat(tercioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTercio() throws Exception {
        int databaseSizeBeforeUpdate = tercioRepository.findAll().size();
        tercio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTercioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tercio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tercio in the database
        List<Tercio> tercioList = tercioRepository.findAll();
        assertThat(tercioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTercioWithPatch() throws Exception {
        // Initialize the database
        tercioRepository.saveAndFlush(tercio);

        int databaseSizeBeforeUpdate = tercioRepository.findAll().size();

        // Update the tercio using partial update
        Tercio partialUpdatedTercio = new Tercio();
        partialUpdatedTercio.setId(tercio.getId());

        partialUpdatedTercio.idTercio(UPDATED_ID_TERCIO).tercioDescription(UPDATED_TERCIO_DESCRIPTION);

        restTercioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTercio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTercio))
            )
            .andExpect(status().isOk());

        // Validate the Tercio in the database
        List<Tercio> tercioList = tercioRepository.findAll();
        assertThat(tercioList).hasSize(databaseSizeBeforeUpdate);
        Tercio testTercio = tercioList.get(tercioList.size() - 1);
        assertThat(testTercio.getIdTercio()).isEqualTo(UPDATED_ID_TERCIO);
        assertThat(testTercio.getTercioDescription()).isEqualTo(UPDATED_TERCIO_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateTercioWithPatch() throws Exception {
        // Initialize the database
        tercioRepository.saveAndFlush(tercio);

        int databaseSizeBeforeUpdate = tercioRepository.findAll().size();

        // Update the tercio using partial update
        Tercio partialUpdatedTercio = new Tercio();
        partialUpdatedTercio.setId(tercio.getId());

        partialUpdatedTercio.idTercio(UPDATED_ID_TERCIO).tercioDescription(UPDATED_TERCIO_DESCRIPTION);

        restTercioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTercio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTercio))
            )
            .andExpect(status().isOk());

        // Validate the Tercio in the database
        List<Tercio> tercioList = tercioRepository.findAll();
        assertThat(tercioList).hasSize(databaseSizeBeforeUpdate);
        Tercio testTercio = tercioList.get(tercioList.size() - 1);
        assertThat(testTercio.getIdTercio()).isEqualTo(UPDATED_ID_TERCIO);
        assertThat(testTercio.getTercioDescription()).isEqualTo(UPDATED_TERCIO_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingTercio() throws Exception {
        int databaseSizeBeforeUpdate = tercioRepository.findAll().size();
        tercio.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTercioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tercio.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tercio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tercio in the database
        List<Tercio> tercioList = tercioRepository.findAll();
        assertThat(tercioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTercio() throws Exception {
        int databaseSizeBeforeUpdate = tercioRepository.findAll().size();
        tercio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTercioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tercio))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tercio in the database
        List<Tercio> tercioList = tercioRepository.findAll();
        assertThat(tercioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTercio() throws Exception {
        int databaseSizeBeforeUpdate = tercioRepository.findAll().size();
        tercio.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTercioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tercio)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tercio in the database
        List<Tercio> tercioList = tercioRepository.findAll();
        assertThat(tercioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTercio() throws Exception {
        // Initialize the database
        tercioRepository.saveAndFlush(tercio);

        int databaseSizeBeforeDelete = tercioRepository.findAll().size();

        // Delete the tercio
        restTercioMockMvc
            .perform(delete(ENTITY_API_URL_ID, tercio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tercio> tercioList = tercioRepository.findAll();
        assertThat(tercioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

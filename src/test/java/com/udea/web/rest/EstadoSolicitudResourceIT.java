package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.EstadoSolicitud;
import com.udea.repository.EstadoSolicitudRepository;
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
 * Integration tests for the {@link EstadoSolicitudResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstadoSolicitudResourceIT {

    private static final Integer DEFAULT_ID_ESTADO_SOLICITUD = 1;
    private static final Integer UPDATED_ID_ESTADO_SOLICITUD = 2;

    private static final String DEFAULT_STATE_SOLICITUD = "AAAAAAAAAA";
    private static final String UPDATED_STATE_SOLICITUD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estado-solicituds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstadoSolicitudRepository estadoSolicitudRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstadoSolicitudMockMvc;

    private EstadoSolicitud estadoSolicitud;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoSolicitud createEntity(EntityManager em) {
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud()
            .idEstadoSolicitud(DEFAULT_ID_ESTADO_SOLICITUD)
            .stateSolicitud(DEFAULT_STATE_SOLICITUD);
        return estadoSolicitud;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoSolicitud createUpdatedEntity(EntityManager em) {
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud()
            .idEstadoSolicitud(UPDATED_ID_ESTADO_SOLICITUD)
            .stateSolicitud(UPDATED_STATE_SOLICITUD);
        return estadoSolicitud;
    }

    @BeforeEach
    public void initTest() {
        estadoSolicitud = createEntity(em);
    }

    @Test
    @Transactional
    void createEstadoSolicitud() throws Exception {
        int databaseSizeBeforeCreate = estadoSolicitudRepository.findAll().size();
        // Create the EstadoSolicitud
        restEstadoSolicitudMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoSolicitud))
            )
            .andExpect(status().isCreated());

        // Validate the EstadoSolicitud in the database
        List<EstadoSolicitud> estadoSolicitudList = estadoSolicitudRepository.findAll();
        assertThat(estadoSolicitudList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoSolicitud testEstadoSolicitud = estadoSolicitudList.get(estadoSolicitudList.size() - 1);
        assertThat(testEstadoSolicitud.getIdEstadoSolicitud()).isEqualTo(DEFAULT_ID_ESTADO_SOLICITUD);
        assertThat(testEstadoSolicitud.getStateSolicitud()).isEqualTo(DEFAULT_STATE_SOLICITUD);
    }

    @Test
    @Transactional
    void createEstadoSolicitudWithExistingId() throws Exception {
        // Create the EstadoSolicitud with an existing ID
        estadoSolicitud.setId(1L);

        int databaseSizeBeforeCreate = estadoSolicitudRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoSolicitudMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoSolicitud))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoSolicitud in the database
        List<EstadoSolicitud> estadoSolicitudList = estadoSolicitudRepository.findAll();
        assertThat(estadoSolicitudList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstadoSolicituds() throws Exception {
        // Initialize the database
        estadoSolicitudRepository.saveAndFlush(estadoSolicitud);

        // Get all the estadoSolicitudList
        restEstadoSolicitudMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoSolicitud.getId().intValue())))
            .andExpect(jsonPath("$.[*].idEstadoSolicitud").value(hasItem(DEFAULT_ID_ESTADO_SOLICITUD)))
            .andExpect(jsonPath("$.[*].stateSolicitud").value(hasItem(DEFAULT_STATE_SOLICITUD)));
    }

    @Test
    @Transactional
    void getEstadoSolicitud() throws Exception {
        // Initialize the database
        estadoSolicitudRepository.saveAndFlush(estadoSolicitud);

        // Get the estadoSolicitud
        restEstadoSolicitudMockMvc
            .perform(get(ENTITY_API_URL_ID, estadoSolicitud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadoSolicitud.getId().intValue()))
            .andExpect(jsonPath("$.idEstadoSolicitud").value(DEFAULT_ID_ESTADO_SOLICITUD))
            .andExpect(jsonPath("$.stateSolicitud").value(DEFAULT_STATE_SOLICITUD));
    }

    @Test
    @Transactional
    void getNonExistingEstadoSolicitud() throws Exception {
        // Get the estadoSolicitud
        restEstadoSolicitudMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstadoSolicitud() throws Exception {
        // Initialize the database
        estadoSolicitudRepository.saveAndFlush(estadoSolicitud);

        int databaseSizeBeforeUpdate = estadoSolicitudRepository.findAll().size();

        // Update the estadoSolicitud
        EstadoSolicitud updatedEstadoSolicitud = estadoSolicitudRepository.findById(estadoSolicitud.getId()).get();
        // Disconnect from session so that the updates on updatedEstadoSolicitud are not directly saved in db
        em.detach(updatedEstadoSolicitud);
        updatedEstadoSolicitud.idEstadoSolicitud(UPDATED_ID_ESTADO_SOLICITUD).stateSolicitud(UPDATED_STATE_SOLICITUD);

        restEstadoSolicitudMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstadoSolicitud.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEstadoSolicitud))
            )
            .andExpect(status().isOk());

        // Validate the EstadoSolicitud in the database
        List<EstadoSolicitud> estadoSolicitudList = estadoSolicitudRepository.findAll();
        assertThat(estadoSolicitudList).hasSize(databaseSizeBeforeUpdate);
        EstadoSolicitud testEstadoSolicitud = estadoSolicitudList.get(estadoSolicitudList.size() - 1);
        assertThat(testEstadoSolicitud.getIdEstadoSolicitud()).isEqualTo(UPDATED_ID_ESTADO_SOLICITUD);
        assertThat(testEstadoSolicitud.getStateSolicitud()).isEqualTo(UPDATED_STATE_SOLICITUD);
    }

    @Test
    @Transactional
    void putNonExistingEstadoSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = estadoSolicitudRepository.findAll().size();
        estadoSolicitud.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoSolicitudMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadoSolicitud.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoSolicitud))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoSolicitud in the database
        List<EstadoSolicitud> estadoSolicitudList = estadoSolicitudRepository.findAll();
        assertThat(estadoSolicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstadoSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = estadoSolicitudRepository.findAll().size();
        estadoSolicitud.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoSolicitudMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoSolicitud))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoSolicitud in the database
        List<EstadoSolicitud> estadoSolicitudList = estadoSolicitudRepository.findAll();
        assertThat(estadoSolicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstadoSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = estadoSolicitudRepository.findAll().size();
        estadoSolicitud.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoSolicitudMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoSolicitud))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoSolicitud in the database
        List<EstadoSolicitud> estadoSolicitudList = estadoSolicitudRepository.findAll();
        assertThat(estadoSolicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstadoSolicitudWithPatch() throws Exception {
        // Initialize the database
        estadoSolicitudRepository.saveAndFlush(estadoSolicitud);

        int databaseSizeBeforeUpdate = estadoSolicitudRepository.findAll().size();

        // Update the estadoSolicitud using partial update
        EstadoSolicitud partialUpdatedEstadoSolicitud = new EstadoSolicitud();
        partialUpdatedEstadoSolicitud.setId(estadoSolicitud.getId());

        partialUpdatedEstadoSolicitud.stateSolicitud(UPDATED_STATE_SOLICITUD);

        restEstadoSolicitudMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoSolicitud.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstadoSolicitud))
            )
            .andExpect(status().isOk());

        // Validate the EstadoSolicitud in the database
        List<EstadoSolicitud> estadoSolicitudList = estadoSolicitudRepository.findAll();
        assertThat(estadoSolicitudList).hasSize(databaseSizeBeforeUpdate);
        EstadoSolicitud testEstadoSolicitud = estadoSolicitudList.get(estadoSolicitudList.size() - 1);
        assertThat(testEstadoSolicitud.getIdEstadoSolicitud()).isEqualTo(DEFAULT_ID_ESTADO_SOLICITUD);
        assertThat(testEstadoSolicitud.getStateSolicitud()).isEqualTo(UPDATED_STATE_SOLICITUD);
    }

    @Test
    @Transactional
    void fullUpdateEstadoSolicitudWithPatch() throws Exception {
        // Initialize the database
        estadoSolicitudRepository.saveAndFlush(estadoSolicitud);

        int databaseSizeBeforeUpdate = estadoSolicitudRepository.findAll().size();

        // Update the estadoSolicitud using partial update
        EstadoSolicitud partialUpdatedEstadoSolicitud = new EstadoSolicitud();
        partialUpdatedEstadoSolicitud.setId(estadoSolicitud.getId());

        partialUpdatedEstadoSolicitud.idEstadoSolicitud(UPDATED_ID_ESTADO_SOLICITUD).stateSolicitud(UPDATED_STATE_SOLICITUD);

        restEstadoSolicitudMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoSolicitud.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstadoSolicitud))
            )
            .andExpect(status().isOk());

        // Validate the EstadoSolicitud in the database
        List<EstadoSolicitud> estadoSolicitudList = estadoSolicitudRepository.findAll();
        assertThat(estadoSolicitudList).hasSize(databaseSizeBeforeUpdate);
        EstadoSolicitud testEstadoSolicitud = estadoSolicitudList.get(estadoSolicitudList.size() - 1);
        assertThat(testEstadoSolicitud.getIdEstadoSolicitud()).isEqualTo(UPDATED_ID_ESTADO_SOLICITUD);
        assertThat(testEstadoSolicitud.getStateSolicitud()).isEqualTo(UPDATED_STATE_SOLICITUD);
    }

    @Test
    @Transactional
    void patchNonExistingEstadoSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = estadoSolicitudRepository.findAll().size();
        estadoSolicitud.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoSolicitudMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estadoSolicitud.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoSolicitud))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoSolicitud in the database
        List<EstadoSolicitud> estadoSolicitudList = estadoSolicitudRepository.findAll();
        assertThat(estadoSolicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstadoSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = estadoSolicitudRepository.findAll().size();
        estadoSolicitud.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoSolicitudMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoSolicitud))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoSolicitud in the database
        List<EstadoSolicitud> estadoSolicitudList = estadoSolicitudRepository.findAll();
        assertThat(estadoSolicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstadoSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = estadoSolicitudRepository.findAll().size();
        estadoSolicitud.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoSolicitudMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoSolicitud))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoSolicitud in the database
        List<EstadoSolicitud> estadoSolicitudList = estadoSolicitudRepository.findAll();
        assertThat(estadoSolicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstadoSolicitud() throws Exception {
        // Initialize the database
        estadoSolicitudRepository.saveAndFlush(estadoSolicitud);

        int databaseSizeBeforeDelete = estadoSolicitudRepository.findAll().size();

        // Delete the estadoSolicitud
        restEstadoSolicitudMockMvc
            .perform(delete(ENTITY_API_URL_ID, estadoSolicitud.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstadoSolicitud> estadoSolicitudList = estadoSolicitudRepository.findAll();
        assertThat(estadoSolicitudList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

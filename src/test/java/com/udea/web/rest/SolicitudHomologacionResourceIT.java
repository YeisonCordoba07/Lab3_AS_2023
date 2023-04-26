package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.SolicitudHomologacion;
import com.udea.repository.SolicitudHomologacionRepository;
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
 * Integration tests for the {@link SolicitudHomologacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SolicitudHomologacionResourceIT {

    private static final Integer DEFAULT_ID_SOLICITUD = 1;
    private static final Integer UPDATED_ID_SOLICITUD = 2;

    private static final Integer DEFAULT_STATE_SOLICITUD = 1;
    private static final Integer UPDATED_STATE_SOLICITUD = 2;

    private static final Integer DEFAULT_CODIGO_PROGRAMA = 1;
    private static final Integer UPDATED_CODIGO_PROGRAMA = 2;

    private static final LocalDate DEFAULT_FECHA_SOLICITUD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SOLICITUD = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/solicitud-homologacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SolicitudHomologacionRepository solicitudHomologacionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSolicitudHomologacionMockMvc;

    private SolicitudHomologacion solicitudHomologacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SolicitudHomologacion createEntity(EntityManager em) {
        SolicitudHomologacion solicitudHomologacion = new SolicitudHomologacion()
            .idSolicitud(DEFAULT_ID_SOLICITUD)
            .stateSolicitud(DEFAULT_STATE_SOLICITUD)
            .codigoPrograma(DEFAULT_CODIGO_PROGRAMA)
            .fechaSolicitud(DEFAULT_FECHA_SOLICITUD)
            .comentario(DEFAULT_COMENTARIO);
        return solicitudHomologacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SolicitudHomologacion createUpdatedEntity(EntityManager em) {
        SolicitudHomologacion solicitudHomologacion = new SolicitudHomologacion()
            .idSolicitud(UPDATED_ID_SOLICITUD)
            .stateSolicitud(UPDATED_STATE_SOLICITUD)
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA)
            .fechaSolicitud(UPDATED_FECHA_SOLICITUD)
            .comentario(UPDATED_COMENTARIO);
        return solicitudHomologacion;
    }

    @BeforeEach
    public void initTest() {
        solicitudHomologacion = createEntity(em);
    }

    @Test
    @Transactional
    void createSolicitudHomologacion() throws Exception {
        int databaseSizeBeforeCreate = solicitudHomologacionRepository.findAll().size();
        // Create the SolicitudHomologacion
        restSolicitudHomologacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(solicitudHomologacion))
            )
            .andExpect(status().isCreated());

        // Validate the SolicitudHomologacion in the database
        List<SolicitudHomologacion> solicitudHomologacionList = solicitudHomologacionRepository.findAll();
        assertThat(solicitudHomologacionList).hasSize(databaseSizeBeforeCreate + 1);
        SolicitudHomologacion testSolicitudHomologacion = solicitudHomologacionList.get(solicitudHomologacionList.size() - 1);
        assertThat(testSolicitudHomologacion.getIdSolicitud()).isEqualTo(DEFAULT_ID_SOLICITUD);
        assertThat(testSolicitudHomologacion.getStateSolicitud()).isEqualTo(DEFAULT_STATE_SOLICITUD);
        assertThat(testSolicitudHomologacion.getCodigoPrograma()).isEqualTo(DEFAULT_CODIGO_PROGRAMA);
        assertThat(testSolicitudHomologacion.getFechaSolicitud()).isEqualTo(DEFAULT_FECHA_SOLICITUD);
        assertThat(testSolicitudHomologacion.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
    }

    @Test
    @Transactional
    void createSolicitudHomologacionWithExistingId() throws Exception {
        // Create the SolicitudHomologacion with an existing ID
        solicitudHomologacion.setId(1L);

        int databaseSizeBeforeCreate = solicitudHomologacionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicitudHomologacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(solicitudHomologacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the SolicitudHomologacion in the database
        List<SolicitudHomologacion> solicitudHomologacionList = solicitudHomologacionRepository.findAll();
        assertThat(solicitudHomologacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSolicitudHomologacions() throws Exception {
        // Initialize the database
        solicitudHomologacionRepository.saveAndFlush(solicitudHomologacion);

        // Get all the solicitudHomologacionList
        restSolicitudHomologacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicitudHomologacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].idSolicitud").value(hasItem(DEFAULT_ID_SOLICITUD)))
            .andExpect(jsonPath("$.[*].stateSolicitud").value(hasItem(DEFAULT_STATE_SOLICITUD)))
            .andExpect(jsonPath("$.[*].codigoPrograma").value(hasItem(DEFAULT_CODIGO_PROGRAMA)))
            .andExpect(jsonPath("$.[*].fechaSolicitud").value(hasItem(DEFAULT_FECHA_SOLICITUD.toString())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)));
    }

    @Test
    @Transactional
    void getSolicitudHomologacion() throws Exception {
        // Initialize the database
        solicitudHomologacionRepository.saveAndFlush(solicitudHomologacion);

        // Get the solicitudHomologacion
        restSolicitudHomologacionMockMvc
            .perform(get(ENTITY_API_URL_ID, solicitudHomologacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(solicitudHomologacion.getId().intValue()))
            .andExpect(jsonPath("$.idSolicitud").value(DEFAULT_ID_SOLICITUD))
            .andExpect(jsonPath("$.stateSolicitud").value(DEFAULT_STATE_SOLICITUD))
            .andExpect(jsonPath("$.codigoPrograma").value(DEFAULT_CODIGO_PROGRAMA))
            .andExpect(jsonPath("$.fechaSolicitud").value(DEFAULT_FECHA_SOLICITUD.toString()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO));
    }

    @Test
    @Transactional
    void getNonExistingSolicitudHomologacion() throws Exception {
        // Get the solicitudHomologacion
        restSolicitudHomologacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSolicitudHomologacion() throws Exception {
        // Initialize the database
        solicitudHomologacionRepository.saveAndFlush(solicitudHomologacion);

        int databaseSizeBeforeUpdate = solicitudHomologacionRepository.findAll().size();

        // Update the solicitudHomologacion
        SolicitudHomologacion updatedSolicitudHomologacion = solicitudHomologacionRepository.findById(solicitudHomologacion.getId()).get();
        // Disconnect from session so that the updates on updatedSolicitudHomologacion are not directly saved in db
        em.detach(updatedSolicitudHomologacion);
        updatedSolicitudHomologacion
            .idSolicitud(UPDATED_ID_SOLICITUD)
            .stateSolicitud(UPDATED_STATE_SOLICITUD)
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA)
            .fechaSolicitud(UPDATED_FECHA_SOLICITUD)
            .comentario(UPDATED_COMENTARIO);

        restSolicitudHomologacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSolicitudHomologacion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSolicitudHomologacion))
            )
            .andExpect(status().isOk());

        // Validate the SolicitudHomologacion in the database
        List<SolicitudHomologacion> solicitudHomologacionList = solicitudHomologacionRepository.findAll();
        assertThat(solicitudHomologacionList).hasSize(databaseSizeBeforeUpdate);
        SolicitudHomologacion testSolicitudHomologacion = solicitudHomologacionList.get(solicitudHomologacionList.size() - 1);
        assertThat(testSolicitudHomologacion.getIdSolicitud()).isEqualTo(UPDATED_ID_SOLICITUD);
        assertThat(testSolicitudHomologacion.getStateSolicitud()).isEqualTo(UPDATED_STATE_SOLICITUD);
        assertThat(testSolicitudHomologacion.getCodigoPrograma()).isEqualTo(UPDATED_CODIGO_PROGRAMA);
        assertThat(testSolicitudHomologacion.getFechaSolicitud()).isEqualTo(UPDATED_FECHA_SOLICITUD);
        assertThat(testSolicitudHomologacion.getComentario()).isEqualTo(UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    void putNonExistingSolicitudHomologacion() throws Exception {
        int databaseSizeBeforeUpdate = solicitudHomologacionRepository.findAll().size();
        solicitudHomologacion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicitudHomologacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, solicitudHomologacion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(solicitudHomologacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the SolicitudHomologacion in the database
        List<SolicitudHomologacion> solicitudHomologacionList = solicitudHomologacionRepository.findAll();
        assertThat(solicitudHomologacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSolicitudHomologacion() throws Exception {
        int databaseSizeBeforeUpdate = solicitudHomologacionRepository.findAll().size();
        solicitudHomologacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitudHomologacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(solicitudHomologacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the SolicitudHomologacion in the database
        List<SolicitudHomologacion> solicitudHomologacionList = solicitudHomologacionRepository.findAll();
        assertThat(solicitudHomologacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSolicitudHomologacion() throws Exception {
        int databaseSizeBeforeUpdate = solicitudHomologacionRepository.findAll().size();
        solicitudHomologacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitudHomologacionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(solicitudHomologacion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SolicitudHomologacion in the database
        List<SolicitudHomologacion> solicitudHomologacionList = solicitudHomologacionRepository.findAll();
        assertThat(solicitudHomologacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSolicitudHomologacionWithPatch() throws Exception {
        // Initialize the database
        solicitudHomologacionRepository.saveAndFlush(solicitudHomologacion);

        int databaseSizeBeforeUpdate = solicitudHomologacionRepository.findAll().size();

        // Update the solicitudHomologacion using partial update
        SolicitudHomologacion partialUpdatedSolicitudHomologacion = new SolicitudHomologacion();
        partialUpdatedSolicitudHomologacion.setId(solicitudHomologacion.getId());

        partialUpdatedSolicitudHomologacion
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA)
            .fechaSolicitud(UPDATED_FECHA_SOLICITUD)
            .comentario(UPDATED_COMENTARIO);

        restSolicitudHomologacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSolicitudHomologacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSolicitudHomologacion))
            )
            .andExpect(status().isOk());

        // Validate the SolicitudHomologacion in the database
        List<SolicitudHomologacion> solicitudHomologacionList = solicitudHomologacionRepository.findAll();
        assertThat(solicitudHomologacionList).hasSize(databaseSizeBeforeUpdate);
        SolicitudHomologacion testSolicitudHomologacion = solicitudHomologacionList.get(solicitudHomologacionList.size() - 1);
        assertThat(testSolicitudHomologacion.getIdSolicitud()).isEqualTo(DEFAULT_ID_SOLICITUD);
        assertThat(testSolicitudHomologacion.getStateSolicitud()).isEqualTo(DEFAULT_STATE_SOLICITUD);
        assertThat(testSolicitudHomologacion.getCodigoPrograma()).isEqualTo(UPDATED_CODIGO_PROGRAMA);
        assertThat(testSolicitudHomologacion.getFechaSolicitud()).isEqualTo(UPDATED_FECHA_SOLICITUD);
        assertThat(testSolicitudHomologacion.getComentario()).isEqualTo(UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    void fullUpdateSolicitudHomologacionWithPatch() throws Exception {
        // Initialize the database
        solicitudHomologacionRepository.saveAndFlush(solicitudHomologacion);

        int databaseSizeBeforeUpdate = solicitudHomologacionRepository.findAll().size();

        // Update the solicitudHomologacion using partial update
        SolicitudHomologacion partialUpdatedSolicitudHomologacion = new SolicitudHomologacion();
        partialUpdatedSolicitudHomologacion.setId(solicitudHomologacion.getId());

        partialUpdatedSolicitudHomologacion
            .idSolicitud(UPDATED_ID_SOLICITUD)
            .stateSolicitud(UPDATED_STATE_SOLICITUD)
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA)
            .fechaSolicitud(UPDATED_FECHA_SOLICITUD)
            .comentario(UPDATED_COMENTARIO);

        restSolicitudHomologacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSolicitudHomologacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSolicitudHomologacion))
            )
            .andExpect(status().isOk());

        // Validate the SolicitudHomologacion in the database
        List<SolicitudHomologacion> solicitudHomologacionList = solicitudHomologacionRepository.findAll();
        assertThat(solicitudHomologacionList).hasSize(databaseSizeBeforeUpdate);
        SolicitudHomologacion testSolicitudHomologacion = solicitudHomologacionList.get(solicitudHomologacionList.size() - 1);
        assertThat(testSolicitudHomologacion.getIdSolicitud()).isEqualTo(UPDATED_ID_SOLICITUD);
        assertThat(testSolicitudHomologacion.getStateSolicitud()).isEqualTo(UPDATED_STATE_SOLICITUD);
        assertThat(testSolicitudHomologacion.getCodigoPrograma()).isEqualTo(UPDATED_CODIGO_PROGRAMA);
        assertThat(testSolicitudHomologacion.getFechaSolicitud()).isEqualTo(UPDATED_FECHA_SOLICITUD);
        assertThat(testSolicitudHomologacion.getComentario()).isEqualTo(UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    void patchNonExistingSolicitudHomologacion() throws Exception {
        int databaseSizeBeforeUpdate = solicitudHomologacionRepository.findAll().size();
        solicitudHomologacion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicitudHomologacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, solicitudHomologacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(solicitudHomologacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the SolicitudHomologacion in the database
        List<SolicitudHomologacion> solicitudHomologacionList = solicitudHomologacionRepository.findAll();
        assertThat(solicitudHomologacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSolicitudHomologacion() throws Exception {
        int databaseSizeBeforeUpdate = solicitudHomologacionRepository.findAll().size();
        solicitudHomologacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitudHomologacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(solicitudHomologacion))
            )
            .andExpect(status().isBadRequest());

        // Validate the SolicitudHomologacion in the database
        List<SolicitudHomologacion> solicitudHomologacionList = solicitudHomologacionRepository.findAll();
        assertThat(solicitudHomologacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSolicitudHomologacion() throws Exception {
        int databaseSizeBeforeUpdate = solicitudHomologacionRepository.findAll().size();
        solicitudHomologacion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitudHomologacionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(solicitudHomologacion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SolicitudHomologacion in the database
        List<SolicitudHomologacion> solicitudHomologacionList = solicitudHomologacionRepository.findAll();
        assertThat(solicitudHomologacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSolicitudHomologacion() throws Exception {
        // Initialize the database
        solicitudHomologacionRepository.saveAndFlush(solicitudHomologacion);

        int databaseSizeBeforeDelete = solicitudHomologacionRepository.findAll().size();

        // Delete the solicitudHomologacion
        restSolicitudHomologacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, solicitudHomologacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SolicitudHomologacion> solicitudHomologacionList = solicitudHomologacionRepository.findAll();
        assertThat(solicitudHomologacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

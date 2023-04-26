package com.udea.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.udea.IntegrationTest;
import com.udea.domain.Estudiante;
import com.udea.repository.EstudianteRepository;
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
 * Integration tests for the {@link EstudianteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstudianteResourceIT {

    private static final String DEFAULT_CEDULA = "AAAAAAAAAA";
    private static final String UPDATED_CEDULA = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO_INSTITUCIONAL = "AAAAAAAAAA";
    private static final String UPDATED_CORREO_INSTITUCIONAL = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO_PERSONAL = "AAAAAAAAAA";
    private static final String UPDATED_CORREO_PERSONAL = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final Integer DEFAULT_ESTRATO = 1;
    private static final Integer UPDATED_ESTRATO = 2;

    private static final LocalDate DEFAULT_FECHA_INGRESO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INGRESO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODIGO_PROGRAMA = 1;
    private static final Integer UPDATED_CODIGO_PROGRAMA = 2;

    private static final String ENTITY_API_URL = "/api/estudiantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstudianteMockMvc;

    private Estudiante estudiante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estudiante createEntity(EntityManager em) {
        Estudiante estudiante = new Estudiante()
            .cedula(DEFAULT_CEDULA)
            .nombre(DEFAULT_NOMBRE)
            .apellido(DEFAULT_APELLIDO)
            .correoInstitucional(DEFAULT_CORREO_INSTITUCIONAL)
            .correoPersonal(DEFAULT_CORREO_PERSONAL)
            .celular(DEFAULT_CELULAR)
            .estrato(DEFAULT_ESTRATO)
            .fechaIngreso(DEFAULT_FECHA_INGRESO)
            .version(DEFAULT_VERSION)
            .codigoPrograma(DEFAULT_CODIGO_PROGRAMA);
        return estudiante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estudiante createUpdatedEntity(EntityManager em) {
        Estudiante estudiante = new Estudiante()
            .cedula(UPDATED_CEDULA)
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .correoInstitucional(UPDATED_CORREO_INSTITUCIONAL)
            .correoPersonal(UPDATED_CORREO_PERSONAL)
            .celular(UPDATED_CELULAR)
            .estrato(UPDATED_ESTRATO)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .version(UPDATED_VERSION)
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA);
        return estudiante;
    }

    @BeforeEach
    public void initTest() {
        estudiante = createEntity(em);
    }

    @Test
    @Transactional
    void createEstudiante() throws Exception {
        int databaseSizeBeforeCreate = estudianteRepository.findAll().size();
        // Create the Estudiante
        restEstudianteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudiante)))
            .andExpect(status().isCreated());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeCreate + 1);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getCedula()).isEqualTo(DEFAULT_CEDULA);
        assertThat(testEstudiante.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEstudiante.getApellido()).isEqualTo(DEFAULT_APELLIDO);
        assertThat(testEstudiante.getCorreoInstitucional()).isEqualTo(DEFAULT_CORREO_INSTITUCIONAL);
        assertThat(testEstudiante.getCorreoPersonal()).isEqualTo(DEFAULT_CORREO_PERSONAL);
        assertThat(testEstudiante.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testEstudiante.getEstrato()).isEqualTo(DEFAULT_ESTRATO);
        assertThat(testEstudiante.getFechaIngreso()).isEqualTo(DEFAULT_FECHA_INGRESO);
        assertThat(testEstudiante.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testEstudiante.getCodigoPrograma()).isEqualTo(DEFAULT_CODIGO_PROGRAMA);
    }

    @Test
    @Transactional
    void createEstudianteWithExistingId() throws Exception {
        // Create the Estudiante with an existing ID
        estudiante.setId(1L);

        int databaseSizeBeforeCreate = estudianteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstudianteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudiante)))
            .andExpect(status().isBadRequest());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstudiantes() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        // Get all the estudianteList
        restEstudianteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estudiante.getId().intValue())))
            .andExpect(jsonPath("$.[*].cedula").value(hasItem(DEFAULT_CEDULA)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido").value(hasItem(DEFAULT_APELLIDO)))
            .andExpect(jsonPath("$.[*].correoInstitucional").value(hasItem(DEFAULT_CORREO_INSTITUCIONAL)))
            .andExpect(jsonPath("$.[*].correoPersonal").value(hasItem(DEFAULT_CORREO_PERSONAL)))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].estrato").value(hasItem(DEFAULT_ESTRATO)))
            .andExpect(jsonPath("$.[*].fechaIngreso").value(hasItem(DEFAULT_FECHA_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].codigoPrograma").value(hasItem(DEFAULT_CODIGO_PROGRAMA)));
    }

    @Test
    @Transactional
    void getEstudiante() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        // Get the estudiante
        restEstudianteMockMvc
            .perform(get(ENTITY_API_URL_ID, estudiante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estudiante.getId().intValue()))
            .andExpect(jsonPath("$.cedula").value(DEFAULT_CEDULA))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellido").value(DEFAULT_APELLIDO))
            .andExpect(jsonPath("$.correoInstitucional").value(DEFAULT_CORREO_INSTITUCIONAL))
            .andExpect(jsonPath("$.correoPersonal").value(DEFAULT_CORREO_PERSONAL))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR))
            .andExpect(jsonPath("$.estrato").value(DEFAULT_ESTRATO))
            .andExpect(jsonPath("$.fechaIngreso").value(DEFAULT_FECHA_INGRESO.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.codigoPrograma").value(DEFAULT_CODIGO_PROGRAMA));
    }

    @Test
    @Transactional
    void getNonExistingEstudiante() throws Exception {
        // Get the estudiante
        restEstudianteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstudiante() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();

        // Update the estudiante
        Estudiante updatedEstudiante = estudianteRepository.findById(estudiante.getId()).get();
        // Disconnect from session so that the updates on updatedEstudiante are not directly saved in db
        em.detach(updatedEstudiante);
        updatedEstudiante
            .cedula(UPDATED_CEDULA)
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .correoInstitucional(UPDATED_CORREO_INSTITUCIONAL)
            .correoPersonal(UPDATED_CORREO_PERSONAL)
            .celular(UPDATED_CELULAR)
            .estrato(UPDATED_ESTRATO)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .version(UPDATED_VERSION)
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA);

        restEstudianteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstudiante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEstudiante))
            )
            .andExpect(status().isOk());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getCedula()).isEqualTo(UPDATED_CEDULA);
        assertThat(testEstudiante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEstudiante.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testEstudiante.getCorreoInstitucional()).isEqualTo(UPDATED_CORREO_INSTITUCIONAL);
        assertThat(testEstudiante.getCorreoPersonal()).isEqualTo(UPDATED_CORREO_PERSONAL);
        assertThat(testEstudiante.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testEstudiante.getEstrato()).isEqualTo(UPDATED_ESTRATO);
        assertThat(testEstudiante.getFechaIngreso()).isEqualTo(UPDATED_FECHA_INGRESO);
        assertThat(testEstudiante.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testEstudiante.getCodigoPrograma()).isEqualTo(UPDATED_CODIGO_PROGRAMA);
    }

    @Test
    @Transactional
    void putNonExistingEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();
        estudiante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstudianteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estudiante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estudiante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();
        estudiante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudianteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estudiante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();
        estudiante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudianteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudiante)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstudianteWithPatch() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();

        // Update the estudiante using partial update
        Estudiante partialUpdatedEstudiante = new Estudiante();
        partialUpdatedEstudiante.setId(estudiante.getId());

        partialUpdatedEstudiante
            .cedula(UPDATED_CEDULA)
            .apellido(UPDATED_APELLIDO)
            .celular(UPDATED_CELULAR)
            .estrato(UPDATED_ESTRATO)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .version(UPDATED_VERSION);

        restEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstudiante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstudiante))
            )
            .andExpect(status().isOk());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getCedula()).isEqualTo(UPDATED_CEDULA);
        assertThat(testEstudiante.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testEstudiante.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testEstudiante.getCorreoInstitucional()).isEqualTo(DEFAULT_CORREO_INSTITUCIONAL);
        assertThat(testEstudiante.getCorreoPersonal()).isEqualTo(DEFAULT_CORREO_PERSONAL);
        assertThat(testEstudiante.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testEstudiante.getEstrato()).isEqualTo(UPDATED_ESTRATO);
        assertThat(testEstudiante.getFechaIngreso()).isEqualTo(UPDATED_FECHA_INGRESO);
        assertThat(testEstudiante.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testEstudiante.getCodigoPrograma()).isEqualTo(DEFAULT_CODIGO_PROGRAMA);
    }

    @Test
    @Transactional
    void fullUpdateEstudianteWithPatch() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();

        // Update the estudiante using partial update
        Estudiante partialUpdatedEstudiante = new Estudiante();
        partialUpdatedEstudiante.setId(estudiante.getId());

        partialUpdatedEstudiante
            .cedula(UPDATED_CEDULA)
            .nombre(UPDATED_NOMBRE)
            .apellido(UPDATED_APELLIDO)
            .correoInstitucional(UPDATED_CORREO_INSTITUCIONAL)
            .correoPersonal(UPDATED_CORREO_PERSONAL)
            .celular(UPDATED_CELULAR)
            .estrato(UPDATED_ESTRATO)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .version(UPDATED_VERSION)
            .codigoPrograma(UPDATED_CODIGO_PROGRAMA);

        restEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstudiante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstudiante))
            )
            .andExpect(status().isOk());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
        Estudiante testEstudiante = estudianteList.get(estudianteList.size() - 1);
        assertThat(testEstudiante.getCedula()).isEqualTo(UPDATED_CEDULA);
        assertThat(testEstudiante.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testEstudiante.getApellido()).isEqualTo(UPDATED_APELLIDO);
        assertThat(testEstudiante.getCorreoInstitucional()).isEqualTo(UPDATED_CORREO_INSTITUCIONAL);
        assertThat(testEstudiante.getCorreoPersonal()).isEqualTo(UPDATED_CORREO_PERSONAL);
        assertThat(testEstudiante.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testEstudiante.getEstrato()).isEqualTo(UPDATED_ESTRATO);
        assertThat(testEstudiante.getFechaIngreso()).isEqualTo(UPDATED_FECHA_INGRESO);
        assertThat(testEstudiante.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testEstudiante.getCodigoPrograma()).isEqualTo(UPDATED_CODIGO_PROGRAMA);
    }

    @Test
    @Transactional
    void patchNonExistingEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();
        estudiante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estudiante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estudiante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();
        estudiante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estudiante))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstudiante() throws Exception {
        int databaseSizeBeforeUpdate = estudianteRepository.findAll().size();
        estudiante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudianteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(estudiante))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estudiante in the database
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstudiante() throws Exception {
        // Initialize the database
        estudianteRepository.saveAndFlush(estudiante);

        int databaseSizeBeforeDelete = estudianteRepository.findAll().size();

        // Delete the estudiante
        restEstudianteMockMvc
            .perform(delete(ENTITY_API_URL_ID, estudiante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estudiante> estudianteList = estudianteRepository.findAll();
        assertThat(estudianteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

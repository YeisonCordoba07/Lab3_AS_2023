package com.udea.web.rest;

import com.udea.domain.HistoriaAcademica;
import com.udea.repository.HistoriaAcademicaRepository;
import com.udea.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.udea.domain.HistoriaAcademica}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HistoriaAcademicaResource {

    private final Logger log = LoggerFactory.getLogger(HistoriaAcademicaResource.class);

    private static final String ENTITY_NAME = "historiaAcademica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HistoriaAcademicaRepository historiaAcademicaRepository;

    public HistoriaAcademicaResource(HistoriaAcademicaRepository historiaAcademicaRepository) {
        this.historiaAcademicaRepository = historiaAcademicaRepository;
    }

    /**
     * {@code POST  /historia-academicas} : Create a new historiaAcademica.
     *
     * @param historiaAcademica the historiaAcademica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new historiaAcademica, or with status {@code 400 (Bad Request)} if the historiaAcademica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/historia-academicas")
    public ResponseEntity<HistoriaAcademica> createHistoriaAcademica(@RequestBody HistoriaAcademica historiaAcademica)
        throws URISyntaxException {
        log.debug("REST request to save HistoriaAcademica : {}", historiaAcademica);
        if (historiaAcademica.getId() != null) {
            throw new BadRequestAlertException("A new historiaAcademica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HistoriaAcademica result = historiaAcademicaRepository.save(historiaAcademica);
        return ResponseEntity
            .created(new URI("/api/historia-academicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /historia-academicas/:id} : Updates an existing historiaAcademica.
     *
     * @param id the id of the historiaAcademica to save.
     * @param historiaAcademica the historiaAcademica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historiaAcademica,
     * or with status {@code 400 (Bad Request)} if the historiaAcademica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the historiaAcademica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/historia-academicas/{id}")
    public ResponseEntity<HistoriaAcademica> updateHistoriaAcademica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HistoriaAcademica historiaAcademica
    ) throws URISyntaxException {
        log.debug("REST request to update HistoriaAcademica : {}, {}", id, historiaAcademica);
        if (historiaAcademica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historiaAcademica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historiaAcademicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HistoriaAcademica result = historiaAcademicaRepository.save(historiaAcademica);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historiaAcademica.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /historia-academicas/:id} : Partial updates given fields of an existing historiaAcademica, field will ignore if it is null
     *
     * @param id the id of the historiaAcademica to save.
     * @param historiaAcademica the historiaAcademica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated historiaAcademica,
     * or with status {@code 400 (Bad Request)} if the historiaAcademica is not valid,
     * or with status {@code 404 (Not Found)} if the historiaAcademica is not found,
     * or with status {@code 500 (Internal Server Error)} if the historiaAcademica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/historia-academicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HistoriaAcademica> partialUpdateHistoriaAcademica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody HistoriaAcademica historiaAcademica
    ) throws URISyntaxException {
        log.debug("REST request to partial update HistoriaAcademica partially : {}, {}", id, historiaAcademica);
        if (historiaAcademica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, historiaAcademica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!historiaAcademicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HistoriaAcademica> result = historiaAcademicaRepository
            .findById(historiaAcademica.getId())
            .map(existingHistoriaAcademica -> {
                if (historiaAcademica.getIdHistoriaAcademica() != null) {
                    existingHistoriaAcademica.setIdHistoriaAcademica(historiaAcademica.getIdHistoriaAcademica());
                }
                if (historiaAcademica.getCedulaEstudiante() != null) {
                    existingHistoriaAcademica.setCedulaEstudiante(historiaAcademica.getCedulaEstudiante());
                }
                if (historiaAcademica.getIdSemestre() != null) {
                    existingHistoriaAcademica.setIdSemestre(historiaAcademica.getIdSemestre());
                }
                if (historiaAcademica.getCodigoPrograma() != null) {
                    existingHistoriaAcademica.setCodigoPrograma(historiaAcademica.getCodigoPrograma());
                }
                if (historiaAcademica.getPromedioAcomulado() != null) {
                    existingHistoriaAcademica.setPromedioAcomulado(historiaAcademica.getPromedioAcomulado());
                }
                if (historiaAcademica.getPromedioSemestre() != null) {
                    existingHistoriaAcademica.setPromedioSemestre(historiaAcademica.getPromedioSemestre());
                }
                if (historiaAcademica.getIdTercio() != null) {
                    existingHistoriaAcademica.setIdTercio(historiaAcademica.getIdTercio());
                }
                if (historiaAcademica.getSituationAcademica() != null) {
                    existingHistoriaAcademica.setSituationAcademica(historiaAcademica.getSituationAcademica());
                }
                if (historiaAcademica.getStateSemestre() != null) {
                    existingHistoriaAcademica.setStateSemestre(historiaAcademica.getStateSemestre());
                }

                return existingHistoriaAcademica;
            })
            .map(historiaAcademicaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, historiaAcademica.getId().toString())
        );
    }

    /**
     * {@code GET  /historia-academicas} : get all the historiaAcademicas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of historiaAcademicas in body.
     */
    @GetMapping("/historia-academicas")
    public List<HistoriaAcademica> getAllHistoriaAcademicas() {
        log.debug("REST request to get all HistoriaAcademicas");
        return historiaAcademicaRepository.findAll();
    }

    /**
     * {@code GET  /historia-academicas/:id} : get the "id" historiaAcademica.
     *
     * @param id the id of the historiaAcademica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the historiaAcademica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/historia-academicas/{id}")
    public ResponseEntity<HistoriaAcademica> getHistoriaAcademica(@PathVariable Long id) {
        log.debug("REST request to get HistoriaAcademica : {}", id);
        Optional<HistoriaAcademica> historiaAcademica = historiaAcademicaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(historiaAcademica);
    }

    /**
     * {@code DELETE  /historia-academicas/:id} : delete the "id" historiaAcademica.
     *
     * @param id the id of the historiaAcademica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/historia-academicas/{id}")
    public ResponseEntity<Void> deleteHistoriaAcademica(@PathVariable Long id) {
        log.debug("REST request to delete HistoriaAcademica : {}", id);
        historiaAcademicaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

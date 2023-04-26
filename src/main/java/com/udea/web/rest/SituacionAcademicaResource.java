package com.udea.web.rest;

import com.udea.domain.SituacionAcademica;
import com.udea.repository.SituacionAcademicaRepository;
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
 * REST controller for managing {@link com.udea.domain.SituacionAcademica}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SituacionAcademicaResource {

    private final Logger log = LoggerFactory.getLogger(SituacionAcademicaResource.class);

    private static final String ENTITY_NAME = "situacionAcademica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SituacionAcademicaRepository situacionAcademicaRepository;

    public SituacionAcademicaResource(SituacionAcademicaRepository situacionAcademicaRepository) {
        this.situacionAcademicaRepository = situacionAcademicaRepository;
    }

    /**
     * {@code POST  /situacion-academicas} : Create a new situacionAcademica.
     *
     * @param situacionAcademica the situacionAcademica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new situacionAcademica, or with status {@code 400 (Bad Request)} if the situacionAcademica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/situacion-academicas")
    public ResponseEntity<SituacionAcademica> createSituacionAcademica(@RequestBody SituacionAcademica situacionAcademica)
        throws URISyntaxException {
        log.debug("REST request to save SituacionAcademica : {}", situacionAcademica);
        if (situacionAcademica.getId() != null) {
            throw new BadRequestAlertException("A new situacionAcademica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SituacionAcademica result = situacionAcademicaRepository.save(situacionAcademica);
        return ResponseEntity
            .created(new URI("/api/situacion-academicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /situacion-academicas/:id} : Updates an existing situacionAcademica.
     *
     * @param id the id of the situacionAcademica to save.
     * @param situacionAcademica the situacionAcademica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situacionAcademica,
     * or with status {@code 400 (Bad Request)} if the situacionAcademica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the situacionAcademica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/situacion-academicas/{id}")
    public ResponseEntity<SituacionAcademica> updateSituacionAcademica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SituacionAcademica situacionAcademica
    ) throws URISyntaxException {
        log.debug("REST request to update SituacionAcademica : {}, {}", id, situacionAcademica);
        if (situacionAcademica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, situacionAcademica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!situacionAcademicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SituacionAcademica result = situacionAcademicaRepository.save(situacionAcademica);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, situacionAcademica.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /situacion-academicas/:id} : Partial updates given fields of an existing situacionAcademica, field will ignore if it is null
     *
     * @param id the id of the situacionAcademica to save.
     * @param situacionAcademica the situacionAcademica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situacionAcademica,
     * or with status {@code 400 (Bad Request)} if the situacionAcademica is not valid,
     * or with status {@code 404 (Not Found)} if the situacionAcademica is not found,
     * or with status {@code 500 (Internal Server Error)} if the situacionAcademica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/situacion-academicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SituacionAcademica> partialUpdateSituacionAcademica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SituacionAcademica situacionAcademica
    ) throws URISyntaxException {
        log.debug("REST request to partial update SituacionAcademica partially : {}, {}", id, situacionAcademica);
        if (situacionAcademica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, situacionAcademica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!situacionAcademicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SituacionAcademica> result = situacionAcademicaRepository
            .findById(situacionAcademica.getId())
            .map(existingSituacionAcademica -> {
                if (situacionAcademica.getIdSituacionAcademica() != null) {
                    existingSituacionAcademica.setIdSituacionAcademica(situacionAcademica.getIdSituacionAcademica());
                }
                if (situacionAcademica.getSituationAcademica() != null) {
                    existingSituacionAcademica.setSituationAcademica(situacionAcademica.getSituationAcademica());
                }

                return existingSituacionAcademica;
            })
            .map(situacionAcademicaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, situacionAcademica.getId().toString())
        );
    }

    /**
     * {@code GET  /situacion-academicas} : get all the situacionAcademicas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of situacionAcademicas in body.
     */
    @GetMapping("/situacion-academicas")
    public List<SituacionAcademica> getAllSituacionAcademicas() {
        log.debug("REST request to get all SituacionAcademicas");
        return situacionAcademicaRepository.findAll();
    }

    /**
     * {@code GET  /situacion-academicas/:id} : get the "id" situacionAcademica.
     *
     * @param id the id of the situacionAcademica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the situacionAcademica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/situacion-academicas/{id}")
    public ResponseEntity<SituacionAcademica> getSituacionAcademica(@PathVariable Long id) {
        log.debug("REST request to get SituacionAcademica : {}", id);
        Optional<SituacionAcademica> situacionAcademica = situacionAcademicaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(situacionAcademica);
    }

    /**
     * {@code DELETE  /situacion-academicas/:id} : delete the "id" situacionAcademica.
     *
     * @param id the id of the situacionAcademica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/situacion-academicas/{id}")
    public ResponseEntity<Void> deleteSituacionAcademica(@PathVariable Long id) {
        log.debug("REST request to delete SituacionAcademica : {}", id);
        situacionAcademicaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

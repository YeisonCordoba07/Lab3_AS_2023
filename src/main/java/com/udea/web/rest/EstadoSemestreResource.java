package com.udea.web.rest;

import com.udea.domain.EstadoSemestre;
import com.udea.repository.EstadoSemestreRepository;
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
 * REST controller for managing {@link com.udea.domain.EstadoSemestre}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstadoSemestreResource {

    private final Logger log = LoggerFactory.getLogger(EstadoSemestreResource.class);

    private static final String ENTITY_NAME = "estadoSemestre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoSemestreRepository estadoSemestreRepository;

    public EstadoSemestreResource(EstadoSemestreRepository estadoSemestreRepository) {
        this.estadoSemestreRepository = estadoSemestreRepository;
    }

    /**
     * {@code POST  /estado-semestres} : Create a new estadoSemestre.
     *
     * @param estadoSemestre the estadoSemestre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoSemestre, or with status {@code 400 (Bad Request)} if the estadoSemestre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-semestres")
    public ResponseEntity<EstadoSemestre> createEstadoSemestre(@RequestBody EstadoSemestre estadoSemestre) throws URISyntaxException {
        log.debug("REST request to save EstadoSemestre : {}", estadoSemestre);
        if (estadoSemestre.getId() != null) {
            throw new BadRequestAlertException("A new estadoSemestre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoSemestre result = estadoSemestreRepository.save(estadoSemestre);
        return ResponseEntity
            .created(new URI("/api/estado-semestres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-semestres/:id} : Updates an existing estadoSemestre.
     *
     * @param id the id of the estadoSemestre to save.
     * @param estadoSemestre the estadoSemestre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoSemestre,
     * or with status {@code 400 (Bad Request)} if the estadoSemestre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoSemestre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-semestres/{id}")
    public ResponseEntity<EstadoSemestre> updateEstadoSemestre(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstadoSemestre estadoSemestre
    ) throws URISyntaxException {
        log.debug("REST request to update EstadoSemestre : {}, {}", id, estadoSemestre);
        if (estadoSemestre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoSemestre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoSemestreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstadoSemestre result = estadoSemestreRepository.save(estadoSemestre);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoSemestre.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estado-semestres/:id} : Partial updates given fields of an existing estadoSemestre, field will ignore if it is null
     *
     * @param id the id of the estadoSemestre to save.
     * @param estadoSemestre the estadoSemestre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoSemestre,
     * or with status {@code 400 (Bad Request)} if the estadoSemestre is not valid,
     * or with status {@code 404 (Not Found)} if the estadoSemestre is not found,
     * or with status {@code 500 (Internal Server Error)} if the estadoSemestre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estado-semestres/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstadoSemestre> partialUpdateEstadoSemestre(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstadoSemestre estadoSemestre
    ) throws URISyntaxException {
        log.debug("REST request to partial update EstadoSemestre partially : {}, {}", id, estadoSemestre);
        if (estadoSemestre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoSemestre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoSemestreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstadoSemestre> result = estadoSemestreRepository
            .findById(estadoSemestre.getId())
            .map(existingEstadoSemestre -> {
                if (estadoSemestre.getIdEstadoSemestre() != null) {
                    existingEstadoSemestre.setIdEstadoSemestre(estadoSemestre.getIdEstadoSemestre());
                }
                if (estadoSemestre.getStateSemestre() != null) {
                    existingEstadoSemestre.setStateSemestre(estadoSemestre.getStateSemestre());
                }

                return existingEstadoSemestre;
            })
            .map(estadoSemestreRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoSemestre.getId().toString())
        );
    }

    /**
     * {@code GET  /estado-semestres} : get all the estadoSemestres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoSemestres in body.
     */
    @GetMapping("/estado-semestres")
    public List<EstadoSemestre> getAllEstadoSemestres() {
        log.debug("REST request to get all EstadoSemestres");
        return estadoSemestreRepository.findAll();
    }

    /**
     * {@code GET  /estado-semestres/:id} : get the "id" estadoSemestre.
     *
     * @param id the id of the estadoSemestre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoSemestre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-semestres/{id}")
    public ResponseEntity<EstadoSemestre> getEstadoSemestre(@PathVariable Long id) {
        log.debug("REST request to get EstadoSemestre : {}", id);
        Optional<EstadoSemestre> estadoSemestre = estadoSemestreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estadoSemestre);
    }

    /**
     * {@code DELETE  /estado-semestres/:id} : delete the "id" estadoSemestre.
     *
     * @param id the id of the estadoSemestre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-semestres/{id}")
    public ResponseEntity<Void> deleteEstadoSemestre(@PathVariable Long id) {
        log.debug("REST request to delete EstadoSemestre : {}", id);
        estadoSemestreRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

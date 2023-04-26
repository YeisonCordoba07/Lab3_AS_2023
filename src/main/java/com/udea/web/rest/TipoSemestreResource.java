package com.udea.web.rest;

import com.udea.domain.TipoSemestre;
import com.udea.repository.TipoSemestreRepository;
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
 * REST controller for managing {@link com.udea.domain.TipoSemestre}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoSemestreResource {

    private final Logger log = LoggerFactory.getLogger(TipoSemestreResource.class);

    private static final String ENTITY_NAME = "tipoSemestre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoSemestreRepository tipoSemestreRepository;

    public TipoSemestreResource(TipoSemestreRepository tipoSemestreRepository) {
        this.tipoSemestreRepository = tipoSemestreRepository;
    }

    /**
     * {@code POST  /tipo-semestres} : Create a new tipoSemestre.
     *
     * @param tipoSemestre the tipoSemestre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoSemestre, or with status {@code 400 (Bad Request)} if the tipoSemestre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-semestres")
    public ResponseEntity<TipoSemestre> createTipoSemestre(@RequestBody TipoSemestre tipoSemestre) throws URISyntaxException {
        log.debug("REST request to save TipoSemestre : {}", tipoSemestre);
        if (tipoSemestre.getId() != null) {
            throw new BadRequestAlertException("A new tipoSemestre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoSemestre result = tipoSemestreRepository.save(tipoSemestre);
        return ResponseEntity
            .created(new URI("/api/tipo-semestres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-semestres/:id} : Updates an existing tipoSemestre.
     *
     * @param id the id of the tipoSemestre to save.
     * @param tipoSemestre the tipoSemestre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoSemestre,
     * or with status {@code 400 (Bad Request)} if the tipoSemestre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoSemestre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-semestres/{id}")
    public ResponseEntity<TipoSemestre> updateTipoSemestre(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoSemestre tipoSemestre
    ) throws URISyntaxException {
        log.debug("REST request to update TipoSemestre : {}, {}", id, tipoSemestre);
        if (tipoSemestre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoSemestre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoSemestreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoSemestre result = tipoSemestreRepository.save(tipoSemestre);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoSemestre.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-semestres/:id} : Partial updates given fields of an existing tipoSemestre, field will ignore if it is null
     *
     * @param id the id of the tipoSemestre to save.
     * @param tipoSemestre the tipoSemestre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoSemestre,
     * or with status {@code 400 (Bad Request)} if the tipoSemestre is not valid,
     * or with status {@code 404 (Not Found)} if the tipoSemestre is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoSemestre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-semestres/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoSemestre> partialUpdateTipoSemestre(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoSemestre tipoSemestre
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoSemestre partially : {}, {}", id, tipoSemestre);
        if (tipoSemestre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoSemestre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoSemestreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoSemestre> result = tipoSemestreRepository
            .findById(tipoSemestre.getId())
            .map(existingTipoSemestre -> {
                if (tipoSemestre.getIdTipoSemestre() != null) {
                    existingTipoSemestre.setIdTipoSemestre(tipoSemestre.getIdTipoSemestre());
                }
                if (tipoSemestre.getTypeSemestre() != null) {
                    existingTipoSemestre.setTypeSemestre(tipoSemestre.getTypeSemestre());
                }

                return existingTipoSemestre;
            })
            .map(tipoSemestreRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoSemestre.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-semestres} : get all the tipoSemestres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoSemestres in body.
     */
    @GetMapping("/tipo-semestres")
    public List<TipoSemestre> getAllTipoSemestres() {
        log.debug("REST request to get all TipoSemestres");
        return tipoSemestreRepository.findAll();
    }

    /**
     * {@code GET  /tipo-semestres/:id} : get the "id" tipoSemestre.
     *
     * @param id the id of the tipoSemestre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoSemestre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-semestres/{id}")
    public ResponseEntity<TipoSemestre> getTipoSemestre(@PathVariable Long id) {
        log.debug("REST request to get TipoSemestre : {}", id);
        Optional<TipoSemestre> tipoSemestre = tipoSemestreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoSemestre);
    }

    /**
     * {@code DELETE  /tipo-semestres/:id} : delete the "id" tipoSemestre.
     *
     * @param id the id of the tipoSemestre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-semestres/{id}")
    public ResponseEntity<Void> deleteTipoSemestre(@PathVariable Long id) {
        log.debug("REST request to delete TipoSemestre : {}", id);
        tipoSemestreRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

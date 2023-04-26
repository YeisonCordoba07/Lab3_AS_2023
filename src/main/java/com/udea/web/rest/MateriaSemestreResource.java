package com.udea.web.rest;

import com.udea.domain.MateriaSemestre;
import com.udea.repository.MateriaSemestreRepository;
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
 * REST controller for managing {@link com.udea.domain.MateriaSemestre}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MateriaSemestreResource {

    private final Logger log = LoggerFactory.getLogger(MateriaSemestreResource.class);

    private static final String ENTITY_NAME = "materiaSemestre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MateriaSemestreRepository materiaSemestreRepository;

    public MateriaSemestreResource(MateriaSemestreRepository materiaSemestreRepository) {
        this.materiaSemestreRepository = materiaSemestreRepository;
    }

    /**
     * {@code POST  /materia-semestres} : Create a new materiaSemestre.
     *
     * @param materiaSemestre the materiaSemestre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materiaSemestre, or with status {@code 400 (Bad Request)} if the materiaSemestre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/materia-semestres")
    public ResponseEntity<MateriaSemestre> createMateriaSemestre(@RequestBody MateriaSemestre materiaSemestre) throws URISyntaxException {
        log.debug("REST request to save MateriaSemestre : {}", materiaSemestre);
        if (materiaSemestre.getId() != null) {
            throw new BadRequestAlertException("A new materiaSemestre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MateriaSemestre result = materiaSemestreRepository.save(materiaSemestre);
        return ResponseEntity
            .created(new URI("/api/materia-semestres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /materia-semestres/:id} : Updates an existing materiaSemestre.
     *
     * @param id the id of the materiaSemestre to save.
     * @param materiaSemestre the materiaSemestre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materiaSemestre,
     * or with status {@code 400 (Bad Request)} if the materiaSemestre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materiaSemestre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/materia-semestres/{id}")
    public ResponseEntity<MateriaSemestre> updateMateriaSemestre(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MateriaSemestre materiaSemestre
    ) throws URISyntaxException {
        log.debug("REST request to update MateriaSemestre : {}, {}", id, materiaSemestre);
        if (materiaSemestre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materiaSemestre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materiaSemestreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MateriaSemestre result = materiaSemestreRepository.save(materiaSemestre);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materiaSemestre.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /materia-semestres/:id} : Partial updates given fields of an existing materiaSemestre, field will ignore if it is null
     *
     * @param id the id of the materiaSemestre to save.
     * @param materiaSemestre the materiaSemestre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materiaSemestre,
     * or with status {@code 400 (Bad Request)} if the materiaSemestre is not valid,
     * or with status {@code 404 (Not Found)} if the materiaSemestre is not found,
     * or with status {@code 500 (Internal Server Error)} if the materiaSemestre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/materia-semestres/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MateriaSemestre> partialUpdateMateriaSemestre(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MateriaSemestre materiaSemestre
    ) throws URISyntaxException {
        log.debug("REST request to partial update MateriaSemestre partially : {}, {}", id, materiaSemestre);
        if (materiaSemestre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materiaSemestre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materiaSemestreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MateriaSemestre> result = materiaSemestreRepository
            .findById(materiaSemestre.getId())
            .map(existingMateriaSemestre -> {
                if (materiaSemestre.getIdMateriaSemestre() != null) {
                    existingMateriaSemestre.setIdMateriaSemestre(materiaSemestre.getIdMateriaSemestre());
                }
                if (materiaSemestre.getCedulaEstudiante() != null) {
                    existingMateriaSemestre.setCedulaEstudiante(materiaSemestre.getCedulaEstudiante());
                }
                if (materiaSemestre.getIdSemestre() != null) {
                    existingMateriaSemestre.setIdSemestre(materiaSemestre.getIdSemestre());
                }
                if (materiaSemestre.getCodigoMateria() != null) {
                    existingMateriaSemestre.setCodigoMateria(materiaSemestre.getCodigoMateria());
                }
                if (materiaSemestre.getNotaDefinitiva() != null) {
                    existingMateriaSemestre.setNotaDefinitiva(materiaSemestre.getNotaDefinitiva());
                }

                return existingMateriaSemestre;
            })
            .map(materiaSemestreRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materiaSemestre.getId().toString())
        );
    }

    /**
     * {@code GET  /materia-semestres} : get all the materiaSemestres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materiaSemestres in body.
     */
    @GetMapping("/materia-semestres")
    public List<MateriaSemestre> getAllMateriaSemestres() {
        log.debug("REST request to get all MateriaSemestres");
        return materiaSemestreRepository.findAll();
    }

    /**
     * {@code GET  /materia-semestres/:id} : get the "id" materiaSemestre.
     *
     * @param id the id of the materiaSemestre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materiaSemestre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/materia-semestres/{id}")
    public ResponseEntity<MateriaSemestre> getMateriaSemestre(@PathVariable Long id) {
        log.debug("REST request to get MateriaSemestre : {}", id);
        Optional<MateriaSemestre> materiaSemestre = materiaSemestreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(materiaSemestre);
    }

    /**
     * {@code DELETE  /materia-semestres/:id} : delete the "id" materiaSemestre.
     *
     * @param id the id of the materiaSemestre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/materia-semestres/{id}")
    public ResponseEntity<Void> deleteMateriaSemestre(@PathVariable Long id) {
        log.debug("REST request to delete MateriaSemestre : {}", id);
        materiaSemestreRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

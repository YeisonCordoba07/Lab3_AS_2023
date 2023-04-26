package com.udea.web.rest;

import com.udea.domain.MateriaSolicitud;
import com.udea.repository.MateriaSolicitudRepository;
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
 * REST controller for managing {@link com.udea.domain.MateriaSolicitud}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MateriaSolicitudResource {

    private final Logger log = LoggerFactory.getLogger(MateriaSolicitudResource.class);

    private static final String ENTITY_NAME = "materiaSolicitud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MateriaSolicitudRepository materiaSolicitudRepository;

    public MateriaSolicitudResource(MateriaSolicitudRepository materiaSolicitudRepository) {
        this.materiaSolicitudRepository = materiaSolicitudRepository;
    }

    /**
     * {@code POST  /materia-solicituds} : Create a new materiaSolicitud.
     *
     * @param materiaSolicitud the materiaSolicitud to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materiaSolicitud, or with status {@code 400 (Bad Request)} if the materiaSolicitud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/materia-solicituds")
    public ResponseEntity<MateriaSolicitud> createMateriaSolicitud(@RequestBody MateriaSolicitud materiaSolicitud)
        throws URISyntaxException {
        log.debug("REST request to save MateriaSolicitud : {}", materiaSolicitud);
        if (materiaSolicitud.getId() != null) {
            throw new BadRequestAlertException("A new materiaSolicitud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MateriaSolicitud result = materiaSolicitudRepository.save(materiaSolicitud);
        return ResponseEntity
            .created(new URI("/api/materia-solicituds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /materia-solicituds/:id} : Updates an existing materiaSolicitud.
     *
     * @param id the id of the materiaSolicitud to save.
     * @param materiaSolicitud the materiaSolicitud to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materiaSolicitud,
     * or with status {@code 400 (Bad Request)} if the materiaSolicitud is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materiaSolicitud couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/materia-solicituds/{id}")
    public ResponseEntity<MateriaSolicitud> updateMateriaSolicitud(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MateriaSolicitud materiaSolicitud
    ) throws URISyntaxException {
        log.debug("REST request to update MateriaSolicitud : {}, {}", id, materiaSolicitud);
        if (materiaSolicitud.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materiaSolicitud.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materiaSolicitudRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MateriaSolicitud result = materiaSolicitudRepository.save(materiaSolicitud);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materiaSolicitud.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /materia-solicituds/:id} : Partial updates given fields of an existing materiaSolicitud, field will ignore if it is null
     *
     * @param id the id of the materiaSolicitud to save.
     * @param materiaSolicitud the materiaSolicitud to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materiaSolicitud,
     * or with status {@code 400 (Bad Request)} if the materiaSolicitud is not valid,
     * or with status {@code 404 (Not Found)} if the materiaSolicitud is not found,
     * or with status {@code 500 (Internal Server Error)} if the materiaSolicitud couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/materia-solicituds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MateriaSolicitud> partialUpdateMateriaSolicitud(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MateriaSolicitud materiaSolicitud
    ) throws URISyntaxException {
        log.debug("REST request to partial update MateriaSolicitud partially : {}, {}", id, materiaSolicitud);
        if (materiaSolicitud.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materiaSolicitud.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materiaSolicitudRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MateriaSolicitud> result = materiaSolicitudRepository
            .findById(materiaSolicitud.getId())
            .map(existingMateriaSolicitud -> {
                if (materiaSolicitud.getIdSolicitud() != null) {
                    existingMateriaSolicitud.setIdSolicitud(materiaSolicitud.getIdSolicitud());
                }
                if (materiaSolicitud.getCodigoMateria() != null) {
                    existingMateriaSolicitud.setCodigoMateria(materiaSolicitud.getCodigoMateria());
                }
                if (materiaSolicitud.getIdSemestrePasada() != null) {
                    existingMateriaSolicitud.setIdSemestrePasada(materiaSolicitud.getIdSemestrePasada());
                }
                if (materiaSolicitud.getNotaDefinitiva() != null) {
                    existingMateriaSolicitud.setNotaDefinitiva(materiaSolicitud.getNotaDefinitiva());
                }
                if (materiaSolicitud.getCedulaEstufiante() != null) {
                    existingMateriaSolicitud.setCedulaEstufiante(materiaSolicitud.getCedulaEstufiante());
                }

                return existingMateriaSolicitud;
            })
            .map(materiaSolicitudRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materiaSolicitud.getId().toString())
        );
    }

    /**
     * {@code GET  /materia-solicituds} : get all the materiaSolicituds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materiaSolicituds in body.
     */
    @GetMapping("/materia-solicituds")
    public List<MateriaSolicitud> getAllMateriaSolicituds() {
        log.debug("REST request to get all MateriaSolicituds");
        return materiaSolicitudRepository.findAll();
    }

    /**
     * {@code GET  /materia-solicituds/:id} : get the "id" materiaSolicitud.
     *
     * @param id the id of the materiaSolicitud to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materiaSolicitud, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/materia-solicituds/{id}")
    public ResponseEntity<MateriaSolicitud> getMateriaSolicitud(@PathVariable Long id) {
        log.debug("REST request to get MateriaSolicitud : {}", id);
        Optional<MateriaSolicitud> materiaSolicitud = materiaSolicitudRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(materiaSolicitud);
    }

    /**
     * {@code DELETE  /materia-solicituds/:id} : delete the "id" materiaSolicitud.
     *
     * @param id the id of the materiaSolicitud to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/materia-solicituds/{id}")
    public ResponseEntity<Void> deleteMateriaSolicitud(@PathVariable Long id) {
        log.debug("REST request to delete MateriaSolicitud : {}", id);
        materiaSolicitudRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

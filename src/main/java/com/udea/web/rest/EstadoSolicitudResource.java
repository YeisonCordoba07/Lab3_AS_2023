package com.udea.web.rest;

import com.udea.domain.EstadoSolicitud;
import com.udea.repository.EstadoSolicitudRepository;
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
 * REST controller for managing {@link com.udea.domain.EstadoSolicitud}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstadoSolicitudResource {

    private final Logger log = LoggerFactory.getLogger(EstadoSolicitudResource.class);

    private static final String ENTITY_NAME = "estadoSolicitud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoSolicitudRepository estadoSolicitudRepository;

    public EstadoSolicitudResource(EstadoSolicitudRepository estadoSolicitudRepository) {
        this.estadoSolicitudRepository = estadoSolicitudRepository;
    }

    /**
     * {@code POST  /estado-solicituds} : Create a new estadoSolicitud.
     *
     * @param estadoSolicitud the estadoSolicitud to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoSolicitud, or with status {@code 400 (Bad Request)} if the estadoSolicitud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-solicituds")
    public ResponseEntity<EstadoSolicitud> createEstadoSolicitud(@RequestBody EstadoSolicitud estadoSolicitud) throws URISyntaxException {
        log.debug("REST request to save EstadoSolicitud : {}", estadoSolicitud);
        if (estadoSolicitud.getId() != null) {
            throw new BadRequestAlertException("A new estadoSolicitud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoSolicitud result = estadoSolicitudRepository.save(estadoSolicitud);
        return ResponseEntity
            .created(new URI("/api/estado-solicituds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-solicituds/:id} : Updates an existing estadoSolicitud.
     *
     * @param id the id of the estadoSolicitud to save.
     * @param estadoSolicitud the estadoSolicitud to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoSolicitud,
     * or with status {@code 400 (Bad Request)} if the estadoSolicitud is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoSolicitud couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-solicituds/{id}")
    public ResponseEntity<EstadoSolicitud> updateEstadoSolicitud(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstadoSolicitud estadoSolicitud
    ) throws URISyntaxException {
        log.debug("REST request to update EstadoSolicitud : {}, {}", id, estadoSolicitud);
        if (estadoSolicitud.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoSolicitud.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoSolicitudRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstadoSolicitud result = estadoSolicitudRepository.save(estadoSolicitud);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoSolicitud.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estado-solicituds/:id} : Partial updates given fields of an existing estadoSolicitud, field will ignore if it is null
     *
     * @param id the id of the estadoSolicitud to save.
     * @param estadoSolicitud the estadoSolicitud to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoSolicitud,
     * or with status {@code 400 (Bad Request)} if the estadoSolicitud is not valid,
     * or with status {@code 404 (Not Found)} if the estadoSolicitud is not found,
     * or with status {@code 500 (Internal Server Error)} if the estadoSolicitud couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estado-solicituds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstadoSolicitud> partialUpdateEstadoSolicitud(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstadoSolicitud estadoSolicitud
    ) throws URISyntaxException {
        log.debug("REST request to partial update EstadoSolicitud partially : {}, {}", id, estadoSolicitud);
        if (estadoSolicitud.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoSolicitud.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoSolicitudRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstadoSolicitud> result = estadoSolicitudRepository
            .findById(estadoSolicitud.getId())
            .map(existingEstadoSolicitud -> {
                if (estadoSolicitud.getIdEstadoSolicitud() != null) {
                    existingEstadoSolicitud.setIdEstadoSolicitud(estadoSolicitud.getIdEstadoSolicitud());
                }
                if (estadoSolicitud.getStateSolicitud() != null) {
                    existingEstadoSolicitud.setStateSolicitud(estadoSolicitud.getStateSolicitud());
                }

                return existingEstadoSolicitud;
            })
            .map(estadoSolicitudRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoSolicitud.getId().toString())
        );
    }

    /**
     * {@code GET  /estado-solicituds} : get all the estadoSolicituds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoSolicituds in body.
     */
    @GetMapping("/estado-solicituds")
    public List<EstadoSolicitud> getAllEstadoSolicituds() {
        log.debug("REST request to get all EstadoSolicituds");
        return estadoSolicitudRepository.findAll();
    }

    /**
     * {@code GET  /estado-solicituds/:id} : get the "id" estadoSolicitud.
     *
     * @param id the id of the estadoSolicitud to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoSolicitud, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-solicituds/{id}")
    public ResponseEntity<EstadoSolicitud> getEstadoSolicitud(@PathVariable Long id) {
        log.debug("REST request to get EstadoSolicitud : {}", id);
        Optional<EstadoSolicitud> estadoSolicitud = estadoSolicitudRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estadoSolicitud);
    }

    /**
     * {@code DELETE  /estado-solicituds/:id} : delete the "id" estadoSolicitud.
     *
     * @param id the id of the estadoSolicitud to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-solicituds/{id}")
    public ResponseEntity<Void> deleteEstadoSolicitud(@PathVariable Long id) {
        log.debug("REST request to delete EstadoSolicitud : {}", id);
        estadoSolicitudRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

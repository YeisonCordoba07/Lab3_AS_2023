package com.udea.web.rest;

import com.udea.domain.Relacion;
import com.udea.repository.RelacionRepository;
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
 * REST controller for managing {@link com.udea.domain.Relacion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RelacionResource {

    private final Logger log = LoggerFactory.getLogger(RelacionResource.class);

    private static final String ENTITY_NAME = "relacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RelacionRepository relacionRepository;

    public RelacionResource(RelacionRepository relacionRepository) {
        this.relacionRepository = relacionRepository;
    }

    /**
     * {@code POST  /relacions} : Create a new relacion.
     *
     * @param relacion the relacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new relacion, or with status {@code 400 (Bad Request)} if the relacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/relacions")
    public ResponseEntity<Relacion> createRelacion(@RequestBody Relacion relacion) throws URISyntaxException {
        log.debug("REST request to save Relacion : {}", relacion);
        if (relacion.getId() != null) {
            throw new BadRequestAlertException("A new relacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Relacion result = relacionRepository.save(relacion);
        return ResponseEntity
            .created(new URI("/api/relacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /relacions/:id} : Updates an existing relacion.
     *
     * @param id the id of the relacion to save.
     * @param relacion the relacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relacion,
     * or with status {@code 400 (Bad Request)} if the relacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the relacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/relacions/{id}")
    public ResponseEntity<Relacion> updateRelacion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Relacion relacion
    ) throws URISyntaxException {
        log.debug("REST request to update Relacion : {}, {}", id, relacion);
        if (relacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Relacion result = relacionRepository.save(relacion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /relacions/:id} : Partial updates given fields of an existing relacion, field will ignore if it is null
     *
     * @param id the id of the relacion to save.
     * @param relacion the relacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated relacion,
     * or with status {@code 400 (Bad Request)} if the relacion is not valid,
     * or with status {@code 404 (Not Found)} if the relacion is not found,
     * or with status {@code 500 (Internal Server Error)} if the relacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/relacions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Relacion> partialUpdateRelacion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Relacion relacion
    ) throws URISyntaxException {
        log.debug("REST request to partial update Relacion partially : {}, {}", id, relacion);
        if (relacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, relacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!relacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Relacion> result = relacionRepository
            .findById(relacion.getId())
            .map(existingRelacion -> {
                if (relacion.getCodigoMateria() != null) {
                    existingRelacion.setCodigoMateria(relacion.getCodigoMateria());
                }
                if (relacion.getCodigoMateriaRelacionada() != null) {
                    existingRelacion.setCodigoMateriaRelacionada(relacion.getCodigoMateriaRelacionada());
                }
                if (relacion.getTipoRelacion() != null) {
                    existingRelacion.setTipoRelacion(relacion.getTipoRelacion());
                }

                return existingRelacion;
            })
            .map(relacionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, relacion.getId().toString())
        );
    }

    /**
     * {@code GET  /relacions} : get all the relacions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of relacions in body.
     */
    @GetMapping("/relacions")
    public List<Relacion> getAllRelacions() {
        log.debug("REST request to get all Relacions");
        return relacionRepository.findAll();
    }

    /**
     * {@code GET  /relacions/:id} : get the "id" relacion.
     *
     * @param id the id of the relacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the relacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/relacions/{id}")
    public ResponseEntity<Relacion> getRelacion(@PathVariable Long id) {
        log.debug("REST request to get Relacion : {}", id);
        Optional<Relacion> relacion = relacionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(relacion);
    }

    /**
     * {@code DELETE  /relacions/:id} : delete the "id" relacion.
     *
     * @param id the id of the relacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/relacions/{id}")
    public ResponseEntity<Void> deleteRelacion(@PathVariable Long id) {
        log.debug("REST request to delete Relacion : {}", id);
        relacionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.udea.web.rest;

import com.udea.domain.Tercio;
import com.udea.repository.TercioRepository;
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
 * REST controller for managing {@link com.udea.domain.Tercio}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TercioResource {

    private final Logger log = LoggerFactory.getLogger(TercioResource.class);

    private static final String ENTITY_NAME = "tercio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TercioRepository tercioRepository;

    public TercioResource(TercioRepository tercioRepository) {
        this.tercioRepository = tercioRepository;
    }

    /**
     * {@code POST  /tercios} : Create a new tercio.
     *
     * @param tercio the tercio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tercio, or with status {@code 400 (Bad Request)} if the tercio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tercios")
    public ResponseEntity<Tercio> createTercio(@RequestBody Tercio tercio) throws URISyntaxException {
        log.debug("REST request to save Tercio : {}", tercio);
        if (tercio.getId() != null) {
            throw new BadRequestAlertException("A new tercio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tercio result = tercioRepository.save(tercio);
        return ResponseEntity
            .created(new URI("/api/tercios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tercios/:id} : Updates an existing tercio.
     *
     * @param id the id of the tercio to save.
     * @param tercio the tercio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tercio,
     * or with status {@code 400 (Bad Request)} if the tercio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tercio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tercios/{id}")
    public ResponseEntity<Tercio> updateTercio(@PathVariable(value = "id", required = false) final Long id, @RequestBody Tercio tercio)
        throws URISyntaxException {
        log.debug("REST request to update Tercio : {}, {}", id, tercio);
        if (tercio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tercio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tercioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Tercio result = tercioRepository.save(tercio);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tercio.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tercios/:id} : Partial updates given fields of an existing tercio, field will ignore if it is null
     *
     * @param id the id of the tercio to save.
     * @param tercio the tercio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tercio,
     * or with status {@code 400 (Bad Request)} if the tercio is not valid,
     * or with status {@code 404 (Not Found)} if the tercio is not found,
     * or with status {@code 500 (Internal Server Error)} if the tercio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tercios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tercio> partialUpdateTercio(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tercio tercio
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tercio partially : {}, {}", id, tercio);
        if (tercio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tercio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tercioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tercio> result = tercioRepository
            .findById(tercio.getId())
            .map(existingTercio -> {
                if (tercio.getIdTercio() != null) {
                    existingTercio.setIdTercio(tercio.getIdTercio());
                }
                if (tercio.getTercioDescription() != null) {
                    existingTercio.setTercioDescription(tercio.getTercioDescription());
                }

                return existingTercio;
            })
            .map(tercioRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tercio.getId().toString())
        );
    }

    /**
     * {@code GET  /tercios} : get all the tercios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tercios in body.
     */
    @GetMapping("/tercios")
    public List<Tercio> getAllTercios() {
        log.debug("REST request to get all Tercios");
        return tercioRepository.findAll();
    }

    /**
     * {@code GET  /tercios/:id} : get the "id" tercio.
     *
     * @param id the id of the tercio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tercio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tercios/{id}")
    public ResponseEntity<Tercio> getTercio(@PathVariable Long id) {
        log.debug("REST request to get Tercio : {}", id);
        Optional<Tercio> tercio = tercioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tercio);
    }

    /**
     * {@code DELETE  /tercios/:id} : delete the "id" tercio.
     *
     * @param id the id of the tercio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tercios/{id}")
    public ResponseEntity<Void> deleteTercio(@PathVariable Long id) {
        log.debug("REST request to delete Tercio : {}", id);
        tercioRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

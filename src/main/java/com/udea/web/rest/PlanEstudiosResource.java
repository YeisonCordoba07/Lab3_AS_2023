package com.udea.web.rest;

import com.udea.domain.PlanEstudios;
import com.udea.repository.PlanEstudiosRepository;
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
 * REST controller for managing {@link com.udea.domain.PlanEstudios}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PlanEstudiosResource {

    private final Logger log = LoggerFactory.getLogger(PlanEstudiosResource.class);

    private static final String ENTITY_NAME = "planEstudios";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanEstudiosRepository planEstudiosRepository;

    public PlanEstudiosResource(PlanEstudiosRepository planEstudiosRepository) {
        this.planEstudiosRepository = planEstudiosRepository;
    }

    /**
     * {@code POST  /plan-estudios} : Create a new planEstudios.
     *
     * @param planEstudios the planEstudios to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planEstudios, or with status {@code 400 (Bad Request)} if the planEstudios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plan-estudios")
    public ResponseEntity<PlanEstudios> createPlanEstudios(@RequestBody PlanEstudios planEstudios) throws URISyntaxException {
        log.debug("REST request to save PlanEstudios : {}", planEstudios);
        if (planEstudios.getId() != null) {
            throw new BadRequestAlertException("A new planEstudios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanEstudios result = planEstudiosRepository.save(planEstudios);
        return ResponseEntity
            .created(new URI("/api/plan-estudios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plan-estudios/:id} : Updates an existing planEstudios.
     *
     * @param id the id of the planEstudios to save.
     * @param planEstudios the planEstudios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planEstudios,
     * or with status {@code 400 (Bad Request)} if the planEstudios is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planEstudios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plan-estudios/{id}")
    public ResponseEntity<PlanEstudios> updatePlanEstudios(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlanEstudios planEstudios
    ) throws URISyntaxException {
        log.debug("REST request to update PlanEstudios : {}, {}", id, planEstudios);
        if (planEstudios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planEstudios.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planEstudiosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlanEstudios result = planEstudiosRepository.save(planEstudios);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planEstudios.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plan-estudios/:id} : Partial updates given fields of an existing planEstudios, field will ignore if it is null
     *
     * @param id the id of the planEstudios to save.
     * @param planEstudios the planEstudios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planEstudios,
     * or with status {@code 400 (Bad Request)} if the planEstudios is not valid,
     * or with status {@code 404 (Not Found)} if the planEstudios is not found,
     * or with status {@code 500 (Internal Server Error)} if the planEstudios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plan-estudios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanEstudios> partialUpdatePlanEstudios(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlanEstudios planEstudios
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanEstudios partially : {}, {}", id, planEstudios);
        if (planEstudios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planEstudios.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planEstudiosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanEstudios> result = planEstudiosRepository
            .findById(planEstudios.getId())
            .map(existingPlanEstudios -> {
                if (planEstudios.getVersion() != null) {
                    existingPlanEstudios.setVersion(planEstudios.getVersion());
                }
                if (planEstudios.getCodigoPrograma() != null) {
                    existingPlanEstudios.setCodigoPrograma(planEstudios.getCodigoPrograma());
                }
                if (planEstudios.getFechaAprobacion() != null) {
                    existingPlanEstudios.setFechaAprobacion(planEstudios.getFechaAprobacion());
                }

                return existingPlanEstudios;
            })
            .map(planEstudiosRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planEstudios.getId().toString())
        );
    }

    /**
     * {@code GET  /plan-estudios} : get all the planEstudios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planEstudios in body.
     */
    @GetMapping("/plan-estudios")
    public List<PlanEstudios> getAllPlanEstudios() {
        log.debug("REST request to get all PlanEstudios");
        return planEstudiosRepository.findAll();
    }

    /**
     * {@code GET  /plan-estudios/:id} : get the "id" planEstudios.
     *
     * @param id the id of the planEstudios to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planEstudios, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plan-estudios/{id}")
    public ResponseEntity<PlanEstudios> getPlanEstudios(@PathVariable Long id) {
        log.debug("REST request to get PlanEstudios : {}", id);
        Optional<PlanEstudios> planEstudios = planEstudiosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(planEstudios);
    }

    /**
     * {@code DELETE  /plan-estudios/:id} : delete the "id" planEstudios.
     *
     * @param id the id of the planEstudios to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plan-estudios/{id}")
    public ResponseEntity<Void> deletePlanEstudios(@PathVariable Long id) {
        log.debug("REST request to delete PlanEstudios : {}", id);
        planEstudiosRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

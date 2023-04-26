package com.udea.web.rest;

import com.udea.domain.ProgramaAcademico;
import com.udea.repository.ProgramaAcademicoRepository;
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
 * REST controller for managing {@link com.udea.domain.ProgramaAcademico}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProgramaAcademicoResource {

    private final Logger log = LoggerFactory.getLogger(ProgramaAcademicoResource.class);

    private static final String ENTITY_NAME = "programaAcademico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramaAcademicoRepository programaAcademicoRepository;

    public ProgramaAcademicoResource(ProgramaAcademicoRepository programaAcademicoRepository) {
        this.programaAcademicoRepository = programaAcademicoRepository;
    }

    /**
     * {@code POST  /programa-academicos} : Create a new programaAcademico.
     *
     * @param programaAcademico the programaAcademico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programaAcademico, or with status {@code 400 (Bad Request)} if the programaAcademico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/programa-academicos")
    public ResponseEntity<ProgramaAcademico> createProgramaAcademico(@RequestBody ProgramaAcademico programaAcademico)
        throws URISyntaxException {
        log.debug("REST request to save ProgramaAcademico : {}", programaAcademico);
        if (programaAcademico.getId() != null) {
            throw new BadRequestAlertException("A new programaAcademico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProgramaAcademico result = programaAcademicoRepository.save(programaAcademico);
        return ResponseEntity
            .created(new URI("/api/programa-academicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /programa-academicos/:id} : Updates an existing programaAcademico.
     *
     * @param id the id of the programaAcademico to save.
     * @param programaAcademico the programaAcademico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programaAcademico,
     * or with status {@code 400 (Bad Request)} if the programaAcademico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programaAcademico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/programa-academicos/{id}")
    public ResponseEntity<ProgramaAcademico> updateProgramaAcademico(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProgramaAcademico programaAcademico
    ) throws URISyntaxException {
        log.debug("REST request to update ProgramaAcademico : {}, {}", id, programaAcademico);
        if (programaAcademico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programaAcademico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programaAcademicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProgramaAcademico result = programaAcademicoRepository.save(programaAcademico);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programaAcademico.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /programa-academicos/:id} : Partial updates given fields of an existing programaAcademico, field will ignore if it is null
     *
     * @param id the id of the programaAcademico to save.
     * @param programaAcademico the programaAcademico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programaAcademico,
     * or with status {@code 400 (Bad Request)} if the programaAcademico is not valid,
     * or with status {@code 404 (Not Found)} if the programaAcademico is not found,
     * or with status {@code 500 (Internal Server Error)} if the programaAcademico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/programa-academicos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProgramaAcademico> partialUpdateProgramaAcademico(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProgramaAcademico programaAcademico
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProgramaAcademico partially : {}, {}", id, programaAcademico);
        if (programaAcademico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, programaAcademico.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!programaAcademicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProgramaAcademico> result = programaAcademicoRepository
            .findById(programaAcademico.getId())
            .map(existingProgramaAcademico -> {
                if (programaAcademico.getCodigoPrograma() != null) {
                    existingProgramaAcademico.setCodigoPrograma(programaAcademico.getCodigoPrograma());
                }
                if (programaAcademico.getNombrePrograma() != null) {
                    existingProgramaAcademico.setNombrePrograma(programaAcademico.getNombrePrograma());
                }
                if (programaAcademico.getDuracion() != null) {
                    existingProgramaAcademico.setDuracion(programaAcademico.getDuracion());
                }
                if (programaAcademico.getVigencia() != null) {
                    existingProgramaAcademico.setVigencia(programaAcademico.getVigencia());
                }

                return existingProgramaAcademico;
            })
            .map(programaAcademicoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programaAcademico.getId().toString())
        );
    }

    /**
     * {@code GET  /programa-academicos} : get all the programaAcademicos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programaAcademicos in body.
     */
    @GetMapping("/programa-academicos")
    public List<ProgramaAcademico> getAllProgramaAcademicos() {
        log.debug("REST request to get all ProgramaAcademicos");
        return programaAcademicoRepository.findAll();
    }

    /**
     * {@code GET  /programa-academicos/:id} : get the "id" programaAcademico.
     *
     * @param id the id of the programaAcademico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programaAcademico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/programa-academicos/{id}")
    public ResponseEntity<ProgramaAcademico> getProgramaAcademico(@PathVariable Long id) {
        log.debug("REST request to get ProgramaAcademico : {}", id);
        Optional<ProgramaAcademico> programaAcademico = programaAcademicoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(programaAcademico);
    }

    /**
     * {@code DELETE  /programa-academicos/:id} : delete the "id" programaAcademico.
     *
     * @param id the id of the programaAcademico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/programa-academicos/{id}")
    public ResponseEntity<Void> deleteProgramaAcademico(@PathVariable Long id) {
        log.debug("REST request to delete ProgramaAcademico : {}", id);
        programaAcademicoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

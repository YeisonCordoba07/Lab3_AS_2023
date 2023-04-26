package com.udea.web.rest;

import com.udea.domain.SolicitudHomologacion;
import com.udea.repository.SolicitudHomologacionRepository;
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
 * REST controller for managing {@link com.udea.domain.SolicitudHomologacion}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SolicitudHomologacionResource {

    private final Logger log = LoggerFactory.getLogger(SolicitudHomologacionResource.class);

    private static final String ENTITY_NAME = "solicitudHomologacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolicitudHomologacionRepository solicitudHomologacionRepository;

    public SolicitudHomologacionResource(SolicitudHomologacionRepository solicitudHomologacionRepository) {
        this.solicitudHomologacionRepository = solicitudHomologacionRepository;
    }

    /**
     * {@code POST  /solicitud-homologacions} : Create a new solicitudHomologacion.
     *
     * @param solicitudHomologacion the solicitudHomologacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solicitudHomologacion, or with status {@code 400 (Bad Request)} if the solicitudHomologacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solicitud-homologacions")
    public ResponseEntity<SolicitudHomologacion> createSolicitudHomologacion(@RequestBody SolicitudHomologacion solicitudHomologacion)
        throws URISyntaxException {
        log.debug("REST request to save SolicitudHomologacion : {}", solicitudHomologacion);
        if (solicitudHomologacion.getId() != null) {
            throw new BadRequestAlertException("A new solicitudHomologacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolicitudHomologacion result = solicitudHomologacionRepository.save(solicitudHomologacion);
        return ResponseEntity
            .created(new URI("/api/solicitud-homologacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solicitud-homologacions/:id} : Updates an existing solicitudHomologacion.
     *
     * @param id the id of the solicitudHomologacion to save.
     * @param solicitudHomologacion the solicitudHomologacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicitudHomologacion,
     * or with status {@code 400 (Bad Request)} if the solicitudHomologacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solicitudHomologacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solicitud-homologacions/{id}")
    public ResponseEntity<SolicitudHomologacion> updateSolicitudHomologacion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SolicitudHomologacion solicitudHomologacion
    ) throws URISyntaxException {
        log.debug("REST request to update SolicitudHomologacion : {}, {}", id, solicitudHomologacion);
        if (solicitudHomologacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, solicitudHomologacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!solicitudHomologacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SolicitudHomologacion result = solicitudHomologacionRepository.save(solicitudHomologacion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, solicitudHomologacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /solicitud-homologacions/:id} : Partial updates given fields of an existing solicitudHomologacion, field will ignore if it is null
     *
     * @param id the id of the solicitudHomologacion to save.
     * @param solicitudHomologacion the solicitudHomologacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicitudHomologacion,
     * or with status {@code 400 (Bad Request)} if the solicitudHomologacion is not valid,
     * or with status {@code 404 (Not Found)} if the solicitudHomologacion is not found,
     * or with status {@code 500 (Internal Server Error)} if the solicitudHomologacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/solicitud-homologacions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SolicitudHomologacion> partialUpdateSolicitudHomologacion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SolicitudHomologacion solicitudHomologacion
    ) throws URISyntaxException {
        log.debug("REST request to partial update SolicitudHomologacion partially : {}, {}", id, solicitudHomologacion);
        if (solicitudHomologacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, solicitudHomologacion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!solicitudHomologacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SolicitudHomologacion> result = solicitudHomologacionRepository
            .findById(solicitudHomologacion.getId())
            .map(existingSolicitudHomologacion -> {
                if (solicitudHomologacion.getIdSolicitud() != null) {
                    existingSolicitudHomologacion.setIdSolicitud(solicitudHomologacion.getIdSolicitud());
                }
                if (solicitudHomologacion.getStateSolicitud() != null) {
                    existingSolicitudHomologacion.setStateSolicitud(solicitudHomologacion.getStateSolicitud());
                }
                if (solicitudHomologacion.getCodigoPrograma() != null) {
                    existingSolicitudHomologacion.setCodigoPrograma(solicitudHomologacion.getCodigoPrograma());
                }
                if (solicitudHomologacion.getFechaSolicitud() != null) {
                    existingSolicitudHomologacion.setFechaSolicitud(solicitudHomologacion.getFechaSolicitud());
                }
                if (solicitudHomologacion.getComentario() != null) {
                    existingSolicitudHomologacion.setComentario(solicitudHomologacion.getComentario());
                }

                return existingSolicitudHomologacion;
            })
            .map(solicitudHomologacionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, solicitudHomologacion.getId().toString())
        );
    }

    /**
     * {@code GET  /solicitud-homologacions} : get all the solicitudHomologacions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solicitudHomologacions in body.
     */
    @GetMapping("/solicitud-homologacions")
    public List<SolicitudHomologacion> getAllSolicitudHomologacions() {
        log.debug("REST request to get all SolicitudHomologacions");
        return solicitudHomologacionRepository.findAll();
    }

    /**
     * {@code GET  /solicitud-homologacions/:id} : get the "id" solicitudHomologacion.
     *
     * @param id the id of the solicitudHomologacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solicitudHomologacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solicitud-homologacions/{id}")
    public ResponseEntity<SolicitudHomologacion> getSolicitudHomologacion(@PathVariable Long id) {
        log.debug("REST request to get SolicitudHomologacion : {}", id);
        Optional<SolicitudHomologacion> solicitudHomologacion = solicitudHomologacionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(solicitudHomologacion);
    }

    /**
     * {@code DELETE  /solicitud-homologacions/:id} : delete the "id" solicitudHomologacion.
     *
     * @param id the id of the solicitudHomologacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solicitud-homologacions/{id}")
    public ResponseEntity<Void> deleteSolicitudHomologacion(@PathVariable Long id) {
        log.debug("REST request to delete SolicitudHomologacion : {}", id);
        solicitudHomologacionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

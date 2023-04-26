package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SolicitudHomologacion.
 */
@Entity
@Table(name = "solicitud_homologacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SolicitudHomologacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_solicitud")
    private Integer idSolicitud;

    @Column(name = "state_solicitud")
    private Integer stateSolicitud;

    @Column(name = "codigo_programa")
    private Integer codigoPrograma;

    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud;

    @Column(name = "comentario")
    private String comentario;

    @OneToMany(mappedBy = "solicitudHomologacion")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "materia", "solicitudHomologacion", "estudiante" }, allowSetters = true)
    private Set<MateriaSolicitud> materiaSolicituds = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "solicitudHomologacions", "planEstudios", "estudiantes" }, allowSetters = true)
    private ProgramaAcademico programaAcademico;

    @ManyToOne
    @JsonIgnoreProperties(value = { "solicitudHomologacions" }, allowSetters = true)
    private EstadoSolicitud estadoSolicitud;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SolicitudHomologacion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdSolicitud() {
        return this.idSolicitud;
    }

    public SolicitudHomologacion idSolicitud(Integer idSolicitud) {
        this.setIdSolicitud(idSolicitud);
        return this;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getStateSolicitud() {
        return this.stateSolicitud;
    }

    public SolicitudHomologacion stateSolicitud(Integer stateSolicitud) {
        this.setStateSolicitud(stateSolicitud);
        return this;
    }

    public void setStateSolicitud(Integer stateSolicitud) {
        this.stateSolicitud = stateSolicitud;
    }

    public Integer getCodigoPrograma() {
        return this.codigoPrograma;
    }

    public SolicitudHomologacion codigoPrograma(Integer codigoPrograma) {
        this.setCodigoPrograma(codigoPrograma);
        return this;
    }

    public void setCodigoPrograma(Integer codigoPrograma) {
        this.codigoPrograma = codigoPrograma;
    }

    public LocalDate getFechaSolicitud() {
        return this.fechaSolicitud;
    }

    public SolicitudHomologacion fechaSolicitud(LocalDate fechaSolicitud) {
        this.setFechaSolicitud(fechaSolicitud);
        return this;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getComentario() {
        return this.comentario;
    }

    public SolicitudHomologacion comentario(String comentario) {
        this.setComentario(comentario);
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Set<MateriaSolicitud> getMateriaSolicituds() {
        return this.materiaSolicituds;
    }

    public void setMateriaSolicituds(Set<MateriaSolicitud> materiaSolicituds) {
        if (this.materiaSolicituds != null) {
            this.materiaSolicituds.forEach(i -> i.setSolicitudHomologacion(null));
        }
        if (materiaSolicituds != null) {
            materiaSolicituds.forEach(i -> i.setSolicitudHomologacion(this));
        }
        this.materiaSolicituds = materiaSolicituds;
    }

    public SolicitudHomologacion materiaSolicituds(Set<MateriaSolicitud> materiaSolicituds) {
        this.setMateriaSolicituds(materiaSolicituds);
        return this;
    }

    public SolicitudHomologacion addMateriaSolicitud(MateriaSolicitud materiaSolicitud) {
        this.materiaSolicituds.add(materiaSolicitud);
        materiaSolicitud.setSolicitudHomologacion(this);
        return this;
    }

    public SolicitudHomologacion removeMateriaSolicitud(MateriaSolicitud materiaSolicitud) {
        this.materiaSolicituds.remove(materiaSolicitud);
        materiaSolicitud.setSolicitudHomologacion(null);
        return this;
    }

    public ProgramaAcademico getProgramaAcademico() {
        return this.programaAcademico;
    }

    public void setProgramaAcademico(ProgramaAcademico programaAcademico) {
        this.programaAcademico = programaAcademico;
    }

    public SolicitudHomologacion programaAcademico(ProgramaAcademico programaAcademico) {
        this.setProgramaAcademico(programaAcademico);
        return this;
    }

    public EstadoSolicitud getEstadoSolicitud() {
        return this.estadoSolicitud;
    }

    public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public SolicitudHomologacion estadoSolicitud(EstadoSolicitud estadoSolicitud) {
        this.setEstadoSolicitud(estadoSolicitud);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SolicitudHomologacion)) {
            return false;
        }
        return id != null && id.equals(((SolicitudHomologacion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SolicitudHomologacion{" +
            "id=" + getId() +
            ", idSolicitud=" + getIdSolicitud() +
            ", stateSolicitud=" + getStateSolicitud() +
            ", codigoPrograma=" + getCodigoPrograma() +
            ", fechaSolicitud='" + getFechaSolicitud() + "'" +
            ", comentario='" + getComentario() + "'" +
            "}";
    }
}

package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EstadoSolicitud.
 */
@Entity
@Table(name = "estado_solicitud")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadoSolicitud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_estado_solicitud")
    private Integer idEstadoSolicitud;

    @Column(name = "state_solicitud")
    private String stateSolicitud;

    @OneToMany(mappedBy = "estadoSolicitud")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "materiaSolicituds", "programaAcademico", "estadoSolicitud" }, allowSetters = true)
    private Set<SolicitudHomologacion> solicitudHomologacions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EstadoSolicitud id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdEstadoSolicitud() {
        return this.idEstadoSolicitud;
    }

    public EstadoSolicitud idEstadoSolicitud(Integer idEstadoSolicitud) {
        this.setIdEstadoSolicitud(idEstadoSolicitud);
        return this;
    }

    public void setIdEstadoSolicitud(Integer idEstadoSolicitud) {
        this.idEstadoSolicitud = idEstadoSolicitud;
    }

    public String getStateSolicitud() {
        return this.stateSolicitud;
    }

    public EstadoSolicitud stateSolicitud(String stateSolicitud) {
        this.setStateSolicitud(stateSolicitud);
        return this;
    }

    public void setStateSolicitud(String stateSolicitud) {
        this.stateSolicitud = stateSolicitud;
    }

    public Set<SolicitudHomologacion> getSolicitudHomologacions() {
        return this.solicitudHomologacions;
    }

    public void setSolicitudHomologacions(Set<SolicitudHomologacion> solicitudHomologacions) {
        if (this.solicitudHomologacions != null) {
            this.solicitudHomologacions.forEach(i -> i.setEstadoSolicitud(null));
        }
        if (solicitudHomologacions != null) {
            solicitudHomologacions.forEach(i -> i.setEstadoSolicitud(this));
        }
        this.solicitudHomologacions = solicitudHomologacions;
    }

    public EstadoSolicitud solicitudHomologacions(Set<SolicitudHomologacion> solicitudHomologacions) {
        this.setSolicitudHomologacions(solicitudHomologacions);
        return this;
    }

    public EstadoSolicitud addSolicitudHomologacion(SolicitudHomologacion solicitudHomologacion) {
        this.solicitudHomologacions.add(solicitudHomologacion);
        solicitudHomologacion.setEstadoSolicitud(this);
        return this;
    }

    public EstadoSolicitud removeSolicitudHomologacion(SolicitudHomologacion solicitudHomologacion) {
        this.solicitudHomologacions.remove(solicitudHomologacion);
        solicitudHomologacion.setEstadoSolicitud(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoSolicitud)) {
            return false;
        }
        return id != null && id.equals(((EstadoSolicitud) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoSolicitud{" +
            "id=" + getId() +
            ", idEstadoSolicitud=" + getIdEstadoSolicitud() +
            ", stateSolicitud='" + getStateSolicitud() + "'" +
            "}";
    }
}

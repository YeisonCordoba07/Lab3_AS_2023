package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EstadoSemestre.
 */
@Entity
@Table(name = "estado_semestre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadoSemestre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_estado_semestre")
    private Integer idEstadoSemestre;

    @Column(name = "state_semestre")
    private String stateSemestre;

    @OneToMany(mappedBy = "estadoSemestre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "materiaSemestres", "historiaAcademicas", "tipoSemestre", "estadoSemestre" }, allowSetters = true)
    private Set<Semestre> semestres = new HashSet<>();

    @OneToMany(mappedBy = "estadoSemestre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estudiante", "semestre", "situacionAcademica", "tercio", "estadoSemestre" }, allowSetters = true)
    private Set<HistoriaAcademica> historiaAcademicas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EstadoSemestre id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdEstadoSemestre() {
        return this.idEstadoSemestre;
    }

    public EstadoSemestre idEstadoSemestre(Integer idEstadoSemestre) {
        this.setIdEstadoSemestre(idEstadoSemestre);
        return this;
    }

    public void setIdEstadoSemestre(Integer idEstadoSemestre) {
        this.idEstadoSemestre = idEstadoSemestre;
    }

    public String getStateSemestre() {
        return this.stateSemestre;
    }

    public EstadoSemestre stateSemestre(String stateSemestre) {
        this.setStateSemestre(stateSemestre);
        return this;
    }

    public void setStateSemestre(String stateSemestre) {
        this.stateSemestre = stateSemestre;
    }

    public Set<Semestre> getSemestres() {
        return this.semestres;
    }

    public void setSemestres(Set<Semestre> semestres) {
        if (this.semestres != null) {
            this.semestres.forEach(i -> i.setEstadoSemestre(null));
        }
        if (semestres != null) {
            semestres.forEach(i -> i.setEstadoSemestre(this));
        }
        this.semestres = semestres;
    }

    public EstadoSemestre semestres(Set<Semestre> semestres) {
        this.setSemestres(semestres);
        return this;
    }

    public EstadoSemestre addSemestre(Semestre semestre) {
        this.semestres.add(semestre);
        semestre.setEstadoSemestre(this);
        return this;
    }

    public EstadoSemestre removeSemestre(Semestre semestre) {
        this.semestres.remove(semestre);
        semestre.setEstadoSemestre(null);
        return this;
    }

    public Set<HistoriaAcademica> getHistoriaAcademicas() {
        return this.historiaAcademicas;
    }

    public void setHistoriaAcademicas(Set<HistoriaAcademica> historiaAcademicas) {
        if (this.historiaAcademicas != null) {
            this.historiaAcademicas.forEach(i -> i.setEstadoSemestre(null));
        }
        if (historiaAcademicas != null) {
            historiaAcademicas.forEach(i -> i.setEstadoSemestre(this));
        }
        this.historiaAcademicas = historiaAcademicas;
    }

    public EstadoSemestre historiaAcademicas(Set<HistoriaAcademica> historiaAcademicas) {
        this.setHistoriaAcademicas(historiaAcademicas);
        return this;
    }

    public EstadoSemestre addHistoriaAcademica(HistoriaAcademica historiaAcademica) {
        this.historiaAcademicas.add(historiaAcademica);
        historiaAcademica.setEstadoSemestre(this);
        return this;
    }

    public EstadoSemestre removeHistoriaAcademica(HistoriaAcademica historiaAcademica) {
        this.historiaAcademicas.remove(historiaAcademica);
        historiaAcademica.setEstadoSemestre(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoSemestre)) {
            return false;
        }
        return id != null && id.equals(((EstadoSemestre) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoSemestre{" +
            "id=" + getId() +
            ", idEstadoSemestre=" + getIdEstadoSemestre() +
            ", stateSemestre='" + getStateSemestre() + "'" +
            "}";
    }
}

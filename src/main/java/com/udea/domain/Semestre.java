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
 * A Semestre.
 */
@Entity
@Table(name = "semestre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Semestre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_semestre")
    private Integer idSemestre;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_terminacion")
    private LocalDate fechaTerminacion;

    @Column(name = "type_semestre")
    private Integer typeSemestre;

    @Column(name = "state_semestre")
    private Integer stateSemestre;

    @OneToMany(mappedBy = "semestre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "materia", "estudiante", "semestre" }, allowSetters = true)
    private Set<MateriaSemestre> materiaSemestres = new HashSet<>();

    @OneToMany(mappedBy = "semestre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estudiante", "semestre", "situacionAcademica", "tercio", "estadoSemestre" }, allowSetters = true)
    private Set<HistoriaAcademica> historiaAcademicas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "semestres" }, allowSetters = true)
    private TipoSemestre tipoSemestre;

    @ManyToOne
    @JsonIgnoreProperties(value = { "semestres", "historiaAcademicas" }, allowSetters = true)
    private EstadoSemestre estadoSemestre;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Semestre id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdSemestre() {
        return this.idSemestre;
    }

    public Semestre idSemestre(Integer idSemestre) {
        this.setIdSemestre(idSemestre);
        return this;
    }

    public void setIdSemestre(Integer idSemestre) {
        this.idSemestre = idSemestre;
    }

    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }

    public Semestre fechaInicio(LocalDate fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaTerminacion() {
        return this.fechaTerminacion;
    }

    public Semestre fechaTerminacion(LocalDate fechaTerminacion) {
        this.setFechaTerminacion(fechaTerminacion);
        return this;
    }

    public void setFechaTerminacion(LocalDate fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    public Integer getTypeSemestre() {
        return this.typeSemestre;
    }

    public Semestre typeSemestre(Integer typeSemestre) {
        this.setTypeSemestre(typeSemestre);
        return this;
    }

    public void setTypeSemestre(Integer typeSemestre) {
        this.typeSemestre = typeSemestre;
    }

    public Integer getStateSemestre() {
        return this.stateSemestre;
    }

    public Semestre stateSemestre(Integer stateSemestre) {
        this.setStateSemestre(stateSemestre);
        return this;
    }

    public void setStateSemestre(Integer stateSemestre) {
        this.stateSemestre = stateSemestre;
    }

    public Set<MateriaSemestre> getMateriaSemestres() {
        return this.materiaSemestres;
    }

    public void setMateriaSemestres(Set<MateriaSemestre> materiaSemestres) {
        if (this.materiaSemestres != null) {
            this.materiaSemestres.forEach(i -> i.setSemestre(null));
        }
        if (materiaSemestres != null) {
            materiaSemestres.forEach(i -> i.setSemestre(this));
        }
        this.materiaSemestres = materiaSemestres;
    }

    public Semestre materiaSemestres(Set<MateriaSemestre> materiaSemestres) {
        this.setMateriaSemestres(materiaSemestres);
        return this;
    }

    public Semestre addMateriaSemestre(MateriaSemestre materiaSemestre) {
        this.materiaSemestres.add(materiaSemestre);
        materiaSemestre.setSemestre(this);
        return this;
    }

    public Semestre removeMateriaSemestre(MateriaSemestre materiaSemestre) {
        this.materiaSemestres.remove(materiaSemestre);
        materiaSemestre.setSemestre(null);
        return this;
    }

    public Set<HistoriaAcademica> getHistoriaAcademicas() {
        return this.historiaAcademicas;
    }

    public void setHistoriaAcademicas(Set<HistoriaAcademica> historiaAcademicas) {
        if (this.historiaAcademicas != null) {
            this.historiaAcademicas.forEach(i -> i.setSemestre(null));
        }
        if (historiaAcademicas != null) {
            historiaAcademicas.forEach(i -> i.setSemestre(this));
        }
        this.historiaAcademicas = historiaAcademicas;
    }

    public Semestre historiaAcademicas(Set<HistoriaAcademica> historiaAcademicas) {
        this.setHistoriaAcademicas(historiaAcademicas);
        return this;
    }

    public Semestre addHistoriaAcademica(HistoriaAcademica historiaAcademica) {
        this.historiaAcademicas.add(historiaAcademica);
        historiaAcademica.setSemestre(this);
        return this;
    }

    public Semestre removeHistoriaAcademica(HistoriaAcademica historiaAcademica) {
        this.historiaAcademicas.remove(historiaAcademica);
        historiaAcademica.setSemestre(null);
        return this;
    }

    public TipoSemestre getTipoSemestre() {
        return this.tipoSemestre;
    }

    public void setTipoSemestre(TipoSemestre tipoSemestre) {
        this.tipoSemestre = tipoSemestre;
    }

    public Semestre tipoSemestre(TipoSemestre tipoSemestre) {
        this.setTipoSemestre(tipoSemestre);
        return this;
    }

    public EstadoSemestre getEstadoSemestre() {
        return this.estadoSemestre;
    }

    public void setEstadoSemestre(EstadoSemestre estadoSemestre) {
        this.estadoSemestre = estadoSemestre;
    }

    public Semestre estadoSemestre(EstadoSemestre estadoSemestre) {
        this.setEstadoSemestre(estadoSemestre);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Semestre)) {
            return false;
        }
        return id != null && id.equals(((Semestre) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Semestre{" +
            "id=" + getId() +
            ", idSemestre=" + getIdSemestre() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaTerminacion='" + getFechaTerminacion() + "'" +
            ", typeSemestre=" + getTypeSemestre() +
            ", stateSemestre=" + getStateSemestre() +
            "}";
    }
}

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
 * A PlanEstudios.
 */
@Entity
@Table(name = "plan_estudios")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanEstudios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "version")
    private String version;

    @Column(name = "codigo_programa")
    private Integer codigoPrograma;

    @Column(name = "fecha_aprobacion")
    private LocalDate fechaAprobacion;

    @OneToMany(mappedBy = "planEstudios")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "materiaSolicituds", "materiaSemestres", "historiaAcademicas", "programaAcademico", "planEstudios" },
        allowSetters = true
    )
    private Set<Estudiante> estudiantes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "solicitudHomologacions", "planEstudios", "estudiantes" }, allowSetters = true)
    private ProgramaAcademico programaAcademico;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlanEstudios id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return this.version;
    }

    public PlanEstudios version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getCodigoPrograma() {
        return this.codigoPrograma;
    }

    public PlanEstudios codigoPrograma(Integer codigoPrograma) {
        this.setCodigoPrograma(codigoPrograma);
        return this;
    }

    public void setCodigoPrograma(Integer codigoPrograma) {
        this.codigoPrograma = codigoPrograma;
    }

    public LocalDate getFechaAprobacion() {
        return this.fechaAprobacion;
    }

    public PlanEstudios fechaAprobacion(LocalDate fechaAprobacion) {
        this.setFechaAprobacion(fechaAprobacion);
        return this;
    }

    public void setFechaAprobacion(LocalDate fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public Set<Estudiante> getEstudiantes() {
        return this.estudiantes;
    }

    public void setEstudiantes(Set<Estudiante> estudiantes) {
        if (this.estudiantes != null) {
            this.estudiantes.forEach(i -> i.setPlanEstudios(null));
        }
        if (estudiantes != null) {
            estudiantes.forEach(i -> i.setPlanEstudios(this));
        }
        this.estudiantes = estudiantes;
    }

    public PlanEstudios estudiantes(Set<Estudiante> estudiantes) {
        this.setEstudiantes(estudiantes);
        return this;
    }

    public PlanEstudios addEstudiante(Estudiante estudiante) {
        this.estudiantes.add(estudiante);
        estudiante.setPlanEstudios(this);
        return this;
    }

    public PlanEstudios removeEstudiante(Estudiante estudiante) {
        this.estudiantes.remove(estudiante);
        estudiante.setPlanEstudios(null);
        return this;
    }

    public ProgramaAcademico getProgramaAcademico() {
        return this.programaAcademico;
    }

    public void setProgramaAcademico(ProgramaAcademico programaAcademico) {
        this.programaAcademico = programaAcademico;
    }

    public PlanEstudios programaAcademico(ProgramaAcademico programaAcademico) {
        this.setProgramaAcademico(programaAcademico);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanEstudios)) {
            return false;
        }
        return id != null && id.equals(((PlanEstudios) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanEstudios{" +
            "id=" + getId() +
            ", version='" + getVersion() + "'" +
            ", codigoPrograma=" + getCodigoPrograma() +
            ", fechaAprobacion='" + getFechaAprobacion() + "'" +
            "}";
    }
}

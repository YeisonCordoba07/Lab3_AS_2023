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
 * A ProgramaAcademico.
 */
@Entity
@Table(name = "programa_academico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProgramaAcademico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_programa")
    private Integer codigoPrograma;

    @Column(name = "nombre_programa")
    private String nombrePrograma;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "vigencia")
    private LocalDate vigencia;

    @OneToMany(mappedBy = "programaAcademico")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "materiaSolicituds", "programaAcademico", "estadoSolicitud" }, allowSetters = true)
    private Set<SolicitudHomologacion> solicitudHomologacions = new HashSet<>();

    @OneToMany(mappedBy = "programaAcademico")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estudiantes", "programaAcademico" }, allowSetters = true)
    private Set<PlanEstudios> planEstudios = new HashSet<>();

    @OneToMany(mappedBy = "programaAcademico")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "materiaSolicituds", "materiaSemestres", "historiaAcademicas", "programaAcademico", "planEstudios" },
        allowSetters = true
    )
    private Set<Estudiante> estudiantes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProgramaAcademico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoPrograma() {
        return this.codigoPrograma;
    }

    public ProgramaAcademico codigoPrograma(Integer codigoPrograma) {
        this.setCodigoPrograma(codigoPrograma);
        return this;
    }

    public void setCodigoPrograma(Integer codigoPrograma) {
        this.codigoPrograma = codigoPrograma;
    }

    public String getNombrePrograma() {
        return this.nombrePrograma;
    }

    public ProgramaAcademico nombrePrograma(String nombrePrograma) {
        this.setNombrePrograma(nombrePrograma);
        return this;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public Integer getDuracion() {
        return this.duracion;
    }

    public ProgramaAcademico duracion(Integer duracion) {
        this.setDuracion(duracion);
        return this;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public LocalDate getVigencia() {
        return this.vigencia;
    }

    public ProgramaAcademico vigencia(LocalDate vigencia) {
        this.setVigencia(vigencia);
        return this;
    }

    public void setVigencia(LocalDate vigencia) {
        this.vigencia = vigencia;
    }

    public Set<SolicitudHomologacion> getSolicitudHomologacions() {
        return this.solicitudHomologacions;
    }

    public void setSolicitudHomologacions(Set<SolicitudHomologacion> solicitudHomologacions) {
        if (this.solicitudHomologacions != null) {
            this.solicitudHomologacions.forEach(i -> i.setProgramaAcademico(null));
        }
        if (solicitudHomologacions != null) {
            solicitudHomologacions.forEach(i -> i.setProgramaAcademico(this));
        }
        this.solicitudHomologacions = solicitudHomologacions;
    }

    public ProgramaAcademico solicitudHomologacions(Set<SolicitudHomologacion> solicitudHomologacions) {
        this.setSolicitudHomologacions(solicitudHomologacions);
        return this;
    }

    public ProgramaAcademico addSolicitudHomologacion(SolicitudHomologacion solicitudHomologacion) {
        this.solicitudHomologacions.add(solicitudHomologacion);
        solicitudHomologacion.setProgramaAcademico(this);
        return this;
    }

    public ProgramaAcademico removeSolicitudHomologacion(SolicitudHomologacion solicitudHomologacion) {
        this.solicitudHomologacions.remove(solicitudHomologacion);
        solicitudHomologacion.setProgramaAcademico(null);
        return this;
    }

    public Set<PlanEstudios> getPlanEstudios() {
        return this.planEstudios;
    }

    public void setPlanEstudios(Set<PlanEstudios> planEstudios) {
        if (this.planEstudios != null) {
            this.planEstudios.forEach(i -> i.setProgramaAcademico(null));
        }
        if (planEstudios != null) {
            planEstudios.forEach(i -> i.setProgramaAcademico(this));
        }
        this.planEstudios = planEstudios;
    }

    public ProgramaAcademico planEstudios(Set<PlanEstudios> planEstudios) {
        this.setPlanEstudios(planEstudios);
        return this;
    }

    public ProgramaAcademico addPlanEstudios(PlanEstudios planEstudios) {
        this.planEstudios.add(planEstudios);
        planEstudios.setProgramaAcademico(this);
        return this;
    }

    public ProgramaAcademico removePlanEstudios(PlanEstudios planEstudios) {
        this.planEstudios.remove(planEstudios);
        planEstudios.setProgramaAcademico(null);
        return this;
    }

    public Set<Estudiante> getEstudiantes() {
        return this.estudiantes;
    }

    public void setEstudiantes(Set<Estudiante> estudiantes) {
        if (this.estudiantes != null) {
            this.estudiantes.forEach(i -> i.setProgramaAcademico(null));
        }
        if (estudiantes != null) {
            estudiantes.forEach(i -> i.setProgramaAcademico(this));
        }
        this.estudiantes = estudiantes;
    }

    public ProgramaAcademico estudiantes(Set<Estudiante> estudiantes) {
        this.setEstudiantes(estudiantes);
        return this;
    }

    public ProgramaAcademico addEstudiante(Estudiante estudiante) {
        this.estudiantes.add(estudiante);
        estudiante.setProgramaAcademico(this);
        return this;
    }

    public ProgramaAcademico removeEstudiante(Estudiante estudiante) {
        this.estudiantes.remove(estudiante);
        estudiante.setProgramaAcademico(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgramaAcademico)) {
            return false;
        }
        return id != null && id.equals(((ProgramaAcademico) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgramaAcademico{" +
            "id=" + getId() +
            ", codigoPrograma=" + getCodigoPrograma() +
            ", nombrePrograma='" + getNombrePrograma() + "'" +
            ", duracion=" + getDuracion() +
            ", vigencia='" + getVigencia() + "'" +
            "}";
    }
}

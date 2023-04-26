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
 * A Estudiante.
 */
@Entity
@Table(name = "estudiante")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cedula")
    private String cedula;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "correo_institucional")
    private String correoInstitucional;

    @Column(name = "correo_personal")
    private String correoPersonal;

    @Column(name = "celular")
    private String celular;

    @Column(name = "estrato")
    private Integer estrato;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "version")
    private String version;

    @Column(name = "codigo_programa")
    private Integer codigoPrograma;

    @OneToMany(mappedBy = "estudiante")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "materia", "solicitudHomologacion", "estudiante" }, allowSetters = true)
    private Set<MateriaSolicitud> materiaSolicituds = new HashSet<>();

    @OneToMany(mappedBy = "estudiante")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "materia", "estudiante", "semestre" }, allowSetters = true)
    private Set<MateriaSemestre> materiaSemestres = new HashSet<>();

    @OneToMany(mappedBy = "estudiante")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estudiante", "semestre", "situacionAcademica", "tercio", "estadoSemestre" }, allowSetters = true)
    private Set<HistoriaAcademica> historiaAcademicas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "solicitudHomologacions", "planEstudios", "estudiantes" }, allowSetters = true)
    private ProgramaAcademico programaAcademico;

    @ManyToOne
    @JsonIgnoreProperties(value = { "estudiantes", "programaAcademico" }, allowSetters = true)
    private PlanEstudios planEstudios;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Estudiante id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCedula() {
        return this.cedula;
    }

    public Estudiante cedula(String cedula) {
        this.setCedula(cedula);
        return this;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Estudiante nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public Estudiante apellido(String apellido) {
        this.setApellido(apellido);
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreoInstitucional() {
        return this.correoInstitucional;
    }

    public Estudiante correoInstitucional(String correoInstitucional) {
        this.setCorreoInstitucional(correoInstitucional);
        return this;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getCorreoPersonal() {
        return this.correoPersonal;
    }

    public Estudiante correoPersonal(String correoPersonal) {
        this.setCorreoPersonal(correoPersonal);
        return this;
    }

    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    public String getCelular() {
        return this.celular;
    }

    public Estudiante celular(String celular) {
        this.setCelular(celular);
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Integer getEstrato() {
        return this.estrato;
    }

    public Estudiante estrato(Integer estrato) {
        this.setEstrato(estrato);
        return this;
    }

    public void setEstrato(Integer estrato) {
        this.estrato = estrato;
    }

    public LocalDate getFechaIngreso() {
        return this.fechaIngreso;
    }

    public Estudiante fechaIngreso(LocalDate fechaIngreso) {
        this.setFechaIngreso(fechaIngreso);
        return this;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getVersion() {
        return this.version;
    }

    public Estudiante version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getCodigoPrograma() {
        return this.codigoPrograma;
    }

    public Estudiante codigoPrograma(Integer codigoPrograma) {
        this.setCodigoPrograma(codigoPrograma);
        return this;
    }

    public void setCodigoPrograma(Integer codigoPrograma) {
        this.codigoPrograma = codigoPrograma;
    }

    public Set<MateriaSolicitud> getMateriaSolicituds() {
        return this.materiaSolicituds;
    }

    public void setMateriaSolicituds(Set<MateriaSolicitud> materiaSolicituds) {
        if (this.materiaSolicituds != null) {
            this.materiaSolicituds.forEach(i -> i.setEstudiante(null));
        }
        if (materiaSolicituds != null) {
            materiaSolicituds.forEach(i -> i.setEstudiante(this));
        }
        this.materiaSolicituds = materiaSolicituds;
    }

    public Estudiante materiaSolicituds(Set<MateriaSolicitud> materiaSolicituds) {
        this.setMateriaSolicituds(materiaSolicituds);
        return this;
    }

    public Estudiante addMateriaSolicitud(MateriaSolicitud materiaSolicitud) {
        this.materiaSolicituds.add(materiaSolicitud);
        materiaSolicitud.setEstudiante(this);
        return this;
    }

    public Estudiante removeMateriaSolicitud(MateriaSolicitud materiaSolicitud) {
        this.materiaSolicituds.remove(materiaSolicitud);
        materiaSolicitud.setEstudiante(null);
        return this;
    }

    public Set<MateriaSemestre> getMateriaSemestres() {
        return this.materiaSemestres;
    }

    public void setMateriaSemestres(Set<MateriaSemestre> materiaSemestres) {
        if (this.materiaSemestres != null) {
            this.materiaSemestres.forEach(i -> i.setEstudiante(null));
        }
        if (materiaSemestres != null) {
            materiaSemestres.forEach(i -> i.setEstudiante(this));
        }
        this.materiaSemestres = materiaSemestres;
    }

    public Estudiante materiaSemestres(Set<MateriaSemestre> materiaSemestres) {
        this.setMateriaSemestres(materiaSemestres);
        return this;
    }

    public Estudiante addMateriaSemestre(MateriaSemestre materiaSemestre) {
        this.materiaSemestres.add(materiaSemestre);
        materiaSemestre.setEstudiante(this);
        return this;
    }

    public Estudiante removeMateriaSemestre(MateriaSemestre materiaSemestre) {
        this.materiaSemestres.remove(materiaSemestre);
        materiaSemestre.setEstudiante(null);
        return this;
    }

    public Set<HistoriaAcademica> getHistoriaAcademicas() {
        return this.historiaAcademicas;
    }

    public void setHistoriaAcademicas(Set<HistoriaAcademica> historiaAcademicas) {
        if (this.historiaAcademicas != null) {
            this.historiaAcademicas.forEach(i -> i.setEstudiante(null));
        }
        if (historiaAcademicas != null) {
            historiaAcademicas.forEach(i -> i.setEstudiante(this));
        }
        this.historiaAcademicas = historiaAcademicas;
    }

    public Estudiante historiaAcademicas(Set<HistoriaAcademica> historiaAcademicas) {
        this.setHistoriaAcademicas(historiaAcademicas);
        return this;
    }

    public Estudiante addHistoriaAcademica(HistoriaAcademica historiaAcademica) {
        this.historiaAcademicas.add(historiaAcademica);
        historiaAcademica.setEstudiante(this);
        return this;
    }

    public Estudiante removeHistoriaAcademica(HistoriaAcademica historiaAcademica) {
        this.historiaAcademicas.remove(historiaAcademica);
        historiaAcademica.setEstudiante(null);
        return this;
    }

    public ProgramaAcademico getProgramaAcademico() {
        return this.programaAcademico;
    }

    public void setProgramaAcademico(ProgramaAcademico programaAcademico) {
        this.programaAcademico = programaAcademico;
    }

    public Estudiante programaAcademico(ProgramaAcademico programaAcademico) {
        this.setProgramaAcademico(programaAcademico);
        return this;
    }

    public PlanEstudios getPlanEstudios() {
        return this.planEstudios;
    }

    public void setPlanEstudios(PlanEstudios planEstudios) {
        this.planEstudios = planEstudios;
    }

    public Estudiante planEstudios(PlanEstudios planEstudios) {
        this.setPlanEstudios(planEstudios);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Estudiante)) {
            return false;
        }
        return id != null && id.equals(((Estudiante) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Estudiante{" +
            "id=" + getId() +
            ", cedula='" + getCedula() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", correoInstitucional='" + getCorreoInstitucional() + "'" +
            ", correoPersonal='" + getCorreoPersonal() + "'" +
            ", celular='" + getCelular() + "'" +
            ", estrato=" + getEstrato() +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", version='" + getVersion() + "'" +
            ", codigoPrograma=" + getCodigoPrograma() +
            "}";
    }
}

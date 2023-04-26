package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MateriaSemestre.
 */
@Entity
@Table(name = "materia_semestre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MateriaSemestre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_materia_semestre")
    private Integer idMateriaSemestre;

    @Column(name = "cedula_estudiante")
    private String cedulaEstudiante;

    @Column(name = "id_semestre")
    private Integer idSemestre;

    @Column(name = "codigo_materia")
    private Integer codigoMateria;

    @Column(name = "nota_definitiva")
    private Float notaDefinitiva;

    @ManyToOne
    @JsonIgnoreProperties(value = { "materiaSolicituds", "relacions", "materiaSemestres" }, allowSetters = true)
    private Materia materia;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "materiaSolicituds", "materiaSemestres", "historiaAcademicas", "programaAcademico", "planEstudios" },
        allowSetters = true
    )
    private Estudiante estudiante;

    @ManyToOne
    @JsonIgnoreProperties(value = { "materiaSemestres", "historiaAcademicas", "tipoSemestre", "estadoSemestre" }, allowSetters = true)
    private Semestre semestre;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MateriaSemestre id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdMateriaSemestre() {
        return this.idMateriaSemestre;
    }

    public MateriaSemestre idMateriaSemestre(Integer idMateriaSemestre) {
        this.setIdMateriaSemestre(idMateriaSemestre);
        return this;
    }

    public void setIdMateriaSemestre(Integer idMateriaSemestre) {
        this.idMateriaSemestre = idMateriaSemestre;
    }

    public String getCedulaEstudiante() {
        return this.cedulaEstudiante;
    }

    public MateriaSemestre cedulaEstudiante(String cedulaEstudiante) {
        this.setCedulaEstudiante(cedulaEstudiante);
        return this;
    }

    public void setCedulaEstudiante(String cedulaEstudiante) {
        this.cedulaEstudiante = cedulaEstudiante;
    }

    public Integer getIdSemestre() {
        return this.idSemestre;
    }

    public MateriaSemestre idSemestre(Integer idSemestre) {
        this.setIdSemestre(idSemestre);
        return this;
    }

    public void setIdSemestre(Integer idSemestre) {
        this.idSemestre = idSemestre;
    }

    public Integer getCodigoMateria() {
        return this.codigoMateria;
    }

    public MateriaSemestre codigoMateria(Integer codigoMateria) {
        this.setCodigoMateria(codigoMateria);
        return this;
    }

    public void setCodigoMateria(Integer codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public Float getNotaDefinitiva() {
        return this.notaDefinitiva;
    }

    public MateriaSemestre notaDefinitiva(Float notaDefinitiva) {
        this.setNotaDefinitiva(notaDefinitiva);
        return this;
    }

    public void setNotaDefinitiva(Float notaDefinitiva) {
        this.notaDefinitiva = notaDefinitiva;
    }

    public Materia getMateria() {
        return this.materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public MateriaSemestre materia(Materia materia) {
        this.setMateria(materia);
        return this;
    }

    public Estudiante getEstudiante() {
        return this.estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public MateriaSemestre estudiante(Estudiante estudiante) {
        this.setEstudiante(estudiante);
        return this;
    }

    public Semestre getSemestre() {
        return this.semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public MateriaSemestre semestre(Semestre semestre) {
        this.setSemestre(semestre);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MateriaSemestre)) {
            return false;
        }
        return id != null && id.equals(((MateriaSemestre) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MateriaSemestre{" +
            "id=" + getId() +
            ", idMateriaSemestre=" + getIdMateriaSemestre() +
            ", cedulaEstudiante='" + getCedulaEstudiante() + "'" +
            ", idSemestre=" + getIdSemestre() +
            ", codigoMateria=" + getCodigoMateria() +
            ", notaDefinitiva=" + getNotaDefinitiva() +
            "}";
    }
}

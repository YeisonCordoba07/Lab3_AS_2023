package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MateriaSolicitud.
 */
@Entity
@Table(name = "materia_solicitud")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MateriaSolicitud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_solicitud")
    private Integer idSolicitud;

    @Column(name = "codigo_materia")
    private Integer codigoMateria;

    @Column(name = "id_semestre_pasada")
    private Integer idSemestrePasada;

    @Column(name = "nota_definitiva")
    private Float notaDefinitiva;

    @Column(name = "cedula_estufiante")
    private String cedulaEstufiante;

    @ManyToOne
    @JsonIgnoreProperties(value = { "materiaSolicituds", "relacions", "materiaSemestres" }, allowSetters = true)
    private Materia materia;

    @ManyToOne
    @JsonIgnoreProperties(value = { "materiaSolicituds", "programaAcademico", "estadoSolicitud" }, allowSetters = true)
    private SolicitudHomologacion solicitudHomologacion;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "materiaSolicituds", "materiaSemestres", "historiaAcademicas", "programaAcademico", "planEstudios" },
        allowSetters = true
    )
    private Estudiante estudiante;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MateriaSolicitud id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdSolicitud() {
        return this.idSolicitud;
    }

    public MateriaSolicitud idSolicitud(Integer idSolicitud) {
        this.setIdSolicitud(idSolicitud);
        return this;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getCodigoMateria() {
        return this.codigoMateria;
    }

    public MateriaSolicitud codigoMateria(Integer codigoMateria) {
        this.setCodigoMateria(codigoMateria);
        return this;
    }

    public void setCodigoMateria(Integer codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public Integer getIdSemestrePasada() {
        return this.idSemestrePasada;
    }

    public MateriaSolicitud idSemestrePasada(Integer idSemestrePasada) {
        this.setIdSemestrePasada(idSemestrePasada);
        return this;
    }

    public void setIdSemestrePasada(Integer idSemestrePasada) {
        this.idSemestrePasada = idSemestrePasada;
    }

    public Float getNotaDefinitiva() {
        return this.notaDefinitiva;
    }

    public MateriaSolicitud notaDefinitiva(Float notaDefinitiva) {
        this.setNotaDefinitiva(notaDefinitiva);
        return this;
    }

    public void setNotaDefinitiva(Float notaDefinitiva) {
        this.notaDefinitiva = notaDefinitiva;
    }

    public String getCedulaEstufiante() {
        return this.cedulaEstufiante;
    }

    public MateriaSolicitud cedulaEstufiante(String cedulaEstufiante) {
        this.setCedulaEstufiante(cedulaEstufiante);
        return this;
    }

    public void setCedulaEstufiante(String cedulaEstufiante) {
        this.cedulaEstufiante = cedulaEstufiante;
    }

    public Materia getMateria() {
        return this.materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public MateriaSolicitud materia(Materia materia) {
        this.setMateria(materia);
        return this;
    }

    public SolicitudHomologacion getSolicitudHomologacion() {
        return this.solicitudHomologacion;
    }

    public void setSolicitudHomologacion(SolicitudHomologacion solicitudHomologacion) {
        this.solicitudHomologacion = solicitudHomologacion;
    }

    public MateriaSolicitud solicitudHomologacion(SolicitudHomologacion solicitudHomologacion) {
        this.setSolicitudHomologacion(solicitudHomologacion);
        return this;
    }

    public Estudiante getEstudiante() {
        return this.estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public MateriaSolicitud estudiante(Estudiante estudiante) {
        this.setEstudiante(estudiante);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MateriaSolicitud)) {
            return false;
        }
        return id != null && id.equals(((MateriaSolicitud) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MateriaSolicitud{" +
            "id=" + getId() +
            ", idSolicitud=" + getIdSolicitud() +
            ", codigoMateria=" + getCodigoMateria() +
            ", idSemestrePasada=" + getIdSemestrePasada() +
            ", notaDefinitiva=" + getNotaDefinitiva() +
            ", cedulaEstufiante='" + getCedulaEstufiante() + "'" +
            "}";
    }
}

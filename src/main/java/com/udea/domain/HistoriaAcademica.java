package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HistoriaAcademica.
 */
@Entity
@Table(name = "historia_academica")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HistoriaAcademica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_historia_academica")
    private Integer idHistoriaAcademica;

    @Column(name = "cedula_estudiante")
    private String cedulaEstudiante;

    @Column(name = "id_semestre")
    private Integer idSemestre;

    @Column(name = "codigo_programa")
    private Integer codigoPrograma;

    @Column(name = "promedio_acomulado")
    private Float promedioAcomulado;

    @Column(name = "promedio_semestre")
    private Float promedioSemestre;

    @Column(name = "id_tercio")
    private Integer idTercio;

    @Column(name = "situation_academica")
    private Integer situationAcademica;

    @Column(name = "state_semestre")
    private Integer stateSemestre;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "materiaSolicituds", "materiaSemestres", "historiaAcademicas", "programaAcademico", "planEstudios" },
        allowSetters = true
    )
    private Estudiante estudiante;

    @ManyToOne
    @JsonIgnoreProperties(value = { "materiaSemestres", "historiaAcademicas", "tipoSemestre", "estadoSemestre" }, allowSetters = true)
    private Semestre semestre;

    @ManyToOne
    @JsonIgnoreProperties(value = { "historiaAcademicas" }, allowSetters = true)
    private SituacionAcademica situacionAcademica;

    @ManyToOne
    @JsonIgnoreProperties(value = { "historiaAcademicas" }, allowSetters = true)
    private Tercio tercio;

    @ManyToOne
    @JsonIgnoreProperties(value = { "semestres", "historiaAcademicas" }, allowSetters = true)
    private EstadoSemestre estadoSemestre;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HistoriaAcademica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdHistoriaAcademica() {
        return this.idHistoriaAcademica;
    }

    public HistoriaAcademica idHistoriaAcademica(Integer idHistoriaAcademica) {
        this.setIdHistoriaAcademica(idHistoriaAcademica);
        return this;
    }

    public void setIdHistoriaAcademica(Integer idHistoriaAcademica) {
        this.idHistoriaAcademica = idHistoriaAcademica;
    }

    public String getCedulaEstudiante() {
        return this.cedulaEstudiante;
    }

    public HistoriaAcademica cedulaEstudiante(String cedulaEstudiante) {
        this.setCedulaEstudiante(cedulaEstudiante);
        return this;
    }

    public void setCedulaEstudiante(String cedulaEstudiante) {
        this.cedulaEstudiante = cedulaEstudiante;
    }

    public Integer getIdSemestre() {
        return this.idSemestre;
    }

    public HistoriaAcademica idSemestre(Integer idSemestre) {
        this.setIdSemestre(idSemestre);
        return this;
    }

    public void setIdSemestre(Integer idSemestre) {
        this.idSemestre = idSemestre;
    }

    public Integer getCodigoPrograma() {
        return this.codigoPrograma;
    }

    public HistoriaAcademica codigoPrograma(Integer codigoPrograma) {
        this.setCodigoPrograma(codigoPrograma);
        return this;
    }

    public void setCodigoPrograma(Integer codigoPrograma) {
        this.codigoPrograma = codigoPrograma;
    }

    public Float getPromedioAcomulado() {
        return this.promedioAcomulado;
    }

    public HistoriaAcademica promedioAcomulado(Float promedioAcomulado) {
        this.setPromedioAcomulado(promedioAcomulado);
        return this;
    }

    public void setPromedioAcomulado(Float promedioAcomulado) {
        this.promedioAcomulado = promedioAcomulado;
    }

    public Float getPromedioSemestre() {
        return this.promedioSemestre;
    }

    public HistoriaAcademica promedioSemestre(Float promedioSemestre) {
        this.setPromedioSemestre(promedioSemestre);
        return this;
    }

    public void setPromedioSemestre(Float promedioSemestre) {
        this.promedioSemestre = promedioSemestre;
    }

    public Integer getIdTercio() {
        return this.idTercio;
    }

    public HistoriaAcademica idTercio(Integer idTercio) {
        this.setIdTercio(idTercio);
        return this;
    }

    public void setIdTercio(Integer idTercio) {
        this.idTercio = idTercio;
    }

    public Integer getSituationAcademica() {
        return this.situationAcademica;
    }

    public HistoriaAcademica situationAcademica(Integer situationAcademica) {
        this.setSituationAcademica(situationAcademica);
        return this;
    }

    public void setSituationAcademica(Integer situationAcademica) {
        this.situationAcademica = situationAcademica;
    }

    public Integer getStateSemestre() {
        return this.stateSemestre;
    }

    public HistoriaAcademica stateSemestre(Integer stateSemestre) {
        this.setStateSemestre(stateSemestre);
        return this;
    }

    public void setStateSemestre(Integer stateSemestre) {
        this.stateSemestre = stateSemestre;
    }

    public Estudiante getEstudiante() {
        return this.estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public HistoriaAcademica estudiante(Estudiante estudiante) {
        this.setEstudiante(estudiante);
        return this;
    }

    public Semestre getSemestre() {
        return this.semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public HistoriaAcademica semestre(Semestre semestre) {
        this.setSemestre(semestre);
        return this;
    }

    public SituacionAcademica getSituacionAcademica() {
        return this.situacionAcademica;
    }

    public void setSituacionAcademica(SituacionAcademica situacionAcademica) {
        this.situacionAcademica = situacionAcademica;
    }

    public HistoriaAcademica situacionAcademica(SituacionAcademica situacionAcademica) {
        this.setSituacionAcademica(situacionAcademica);
        return this;
    }

    public Tercio getTercio() {
        return this.tercio;
    }

    public void setTercio(Tercio tercio) {
        this.tercio = tercio;
    }

    public HistoriaAcademica tercio(Tercio tercio) {
        this.setTercio(tercio);
        return this;
    }

    public EstadoSemestre getEstadoSemestre() {
        return this.estadoSemestre;
    }

    public void setEstadoSemestre(EstadoSemestre estadoSemestre) {
        this.estadoSemestre = estadoSemestre;
    }

    public HistoriaAcademica estadoSemestre(EstadoSemestre estadoSemestre) {
        this.setEstadoSemestre(estadoSemestre);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistoriaAcademica)) {
            return false;
        }
        return id != null && id.equals(((HistoriaAcademica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HistoriaAcademica{" +
            "id=" + getId() +
            ", idHistoriaAcademica=" + getIdHistoriaAcademica() +
            ", cedulaEstudiante='" + getCedulaEstudiante() + "'" +
            ", idSemestre=" + getIdSemestre() +
            ", codigoPrograma=" + getCodigoPrograma() +
            ", promedioAcomulado=" + getPromedioAcomulado() +
            ", promedioSemestre=" + getPromedioSemestre() +
            ", idTercio=" + getIdTercio() +
            ", situationAcademica=" + getSituationAcademica() +
            ", stateSemestre=" + getStateSemestre() +
            "}";
    }
}

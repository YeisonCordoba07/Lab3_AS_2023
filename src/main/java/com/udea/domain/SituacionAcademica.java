package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SituacionAcademica.
 */
@Entity
@Table(name = "situacion_academica")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SituacionAcademica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_situacion_academica")
    private Integer idSituacionAcademica;

    @Column(name = "situation_academica")
    private String situationAcademica;

    @OneToMany(mappedBy = "situacionAcademica")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estudiante", "semestre", "situacionAcademica", "tercio", "estadoSemestre" }, allowSetters = true)
    private Set<HistoriaAcademica> historiaAcademicas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SituacionAcademica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdSituacionAcademica() {
        return this.idSituacionAcademica;
    }

    public SituacionAcademica idSituacionAcademica(Integer idSituacionAcademica) {
        this.setIdSituacionAcademica(idSituacionAcademica);
        return this;
    }

    public void setIdSituacionAcademica(Integer idSituacionAcademica) {
        this.idSituacionAcademica = idSituacionAcademica;
    }

    public String getSituationAcademica() {
        return this.situationAcademica;
    }

    public SituacionAcademica situationAcademica(String situationAcademica) {
        this.setSituationAcademica(situationAcademica);
        return this;
    }

    public void setSituationAcademica(String situationAcademica) {
        this.situationAcademica = situationAcademica;
    }

    public Set<HistoriaAcademica> getHistoriaAcademicas() {
        return this.historiaAcademicas;
    }

    public void setHistoriaAcademicas(Set<HistoriaAcademica> historiaAcademicas) {
        if (this.historiaAcademicas != null) {
            this.historiaAcademicas.forEach(i -> i.setSituacionAcademica(null));
        }
        if (historiaAcademicas != null) {
            historiaAcademicas.forEach(i -> i.setSituacionAcademica(this));
        }
        this.historiaAcademicas = historiaAcademicas;
    }

    public SituacionAcademica historiaAcademicas(Set<HistoriaAcademica> historiaAcademicas) {
        this.setHistoriaAcademicas(historiaAcademicas);
        return this;
    }

    public SituacionAcademica addHistoriaAcademica(HistoriaAcademica historiaAcademica) {
        this.historiaAcademicas.add(historiaAcademica);
        historiaAcademica.setSituacionAcademica(this);
        return this;
    }

    public SituacionAcademica removeHistoriaAcademica(HistoriaAcademica historiaAcademica) {
        this.historiaAcademicas.remove(historiaAcademica);
        historiaAcademica.setSituacionAcademica(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SituacionAcademica)) {
            return false;
        }
        return id != null && id.equals(((SituacionAcademica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SituacionAcademica{" +
            "id=" + getId() +
            ", idSituacionAcademica=" + getIdSituacionAcademica() +
            ", situationAcademica='" + getSituationAcademica() + "'" +
            "}";
    }
}

package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TipoSemestre.
 */
@Entity
@Table(name = "tipo_semestre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoSemestre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_tipo_semestre")
    private Integer idTipoSemestre;

    @Column(name = "type_semestre")
    private String typeSemestre;

    @OneToMany(mappedBy = "tipoSemestre")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "materiaSemestres", "historiaAcademicas", "tipoSemestre", "estadoSemestre" }, allowSetters = true)
    private Set<Semestre> semestres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TipoSemestre id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdTipoSemestre() {
        return this.idTipoSemestre;
    }

    public TipoSemestre idTipoSemestre(Integer idTipoSemestre) {
        this.setIdTipoSemestre(idTipoSemestre);
        return this;
    }

    public void setIdTipoSemestre(Integer idTipoSemestre) {
        this.idTipoSemestre = idTipoSemestre;
    }

    public String getTypeSemestre() {
        return this.typeSemestre;
    }

    public TipoSemestre typeSemestre(String typeSemestre) {
        this.setTypeSemestre(typeSemestre);
        return this;
    }

    public void setTypeSemestre(String typeSemestre) {
        this.typeSemestre = typeSemestre;
    }

    public Set<Semestre> getSemestres() {
        return this.semestres;
    }

    public void setSemestres(Set<Semestre> semestres) {
        if (this.semestres != null) {
            this.semestres.forEach(i -> i.setTipoSemestre(null));
        }
        if (semestres != null) {
            semestres.forEach(i -> i.setTipoSemestre(this));
        }
        this.semestres = semestres;
    }

    public TipoSemestre semestres(Set<Semestre> semestres) {
        this.setSemestres(semestres);
        return this;
    }

    public TipoSemestre addSemestre(Semestre semestre) {
        this.semestres.add(semestre);
        semestre.setTipoSemestre(this);
        return this;
    }

    public TipoSemestre removeSemestre(Semestre semestre) {
        this.semestres.remove(semestre);
        semestre.setTipoSemestre(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoSemestre)) {
            return false;
        }
        return id != null && id.equals(((TipoSemestre) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoSemestre{" +
            "id=" + getId() +
            ", idTipoSemestre=" + getIdTipoSemestre() +
            ", typeSemestre='" + getTypeSemestre() + "'" +
            "}";
    }
}

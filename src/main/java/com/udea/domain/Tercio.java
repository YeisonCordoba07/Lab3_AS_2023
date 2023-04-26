package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tercio.
 */
@Entity
@Table(name = "tercio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tercio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_tercio")
    private Integer idTercio;

    @Column(name = "tercio_description")
    private String tercioDescription;

    @OneToMany(mappedBy = "tercio")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "estudiante", "semestre", "situacionAcademica", "tercio", "estadoSemestre" }, allowSetters = true)
    private Set<HistoriaAcademica> historiaAcademicas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tercio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdTercio() {
        return this.idTercio;
    }

    public Tercio idTercio(Integer idTercio) {
        this.setIdTercio(idTercio);
        return this;
    }

    public void setIdTercio(Integer idTercio) {
        this.idTercio = idTercio;
    }

    public String getTercioDescription() {
        return this.tercioDescription;
    }

    public Tercio tercioDescription(String tercioDescription) {
        this.setTercioDescription(tercioDescription);
        return this;
    }

    public void setTercioDescription(String tercioDescription) {
        this.tercioDescription = tercioDescription;
    }

    public Set<HistoriaAcademica> getHistoriaAcademicas() {
        return this.historiaAcademicas;
    }

    public void setHistoriaAcademicas(Set<HistoriaAcademica> historiaAcademicas) {
        if (this.historiaAcademicas != null) {
            this.historiaAcademicas.forEach(i -> i.setTercio(null));
        }
        if (historiaAcademicas != null) {
            historiaAcademicas.forEach(i -> i.setTercio(this));
        }
        this.historiaAcademicas = historiaAcademicas;
    }

    public Tercio historiaAcademicas(Set<HistoriaAcademica> historiaAcademicas) {
        this.setHistoriaAcademicas(historiaAcademicas);
        return this;
    }

    public Tercio addHistoriaAcademica(HistoriaAcademica historiaAcademica) {
        this.historiaAcademicas.add(historiaAcademica);
        historiaAcademica.setTercio(this);
        return this;
    }

    public Tercio removeHistoriaAcademica(HistoriaAcademica historiaAcademica) {
        this.historiaAcademicas.remove(historiaAcademica);
        historiaAcademica.setTercio(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tercio)) {
            return false;
        }
        return id != null && id.equals(((Tercio) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tercio{" +
            "id=" + getId() +
            ", idTercio=" + getIdTercio() +
            ", tercioDescription='" + getTercioDescription() + "'" +
            "}";
    }
}

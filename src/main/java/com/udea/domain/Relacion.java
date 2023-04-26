package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Relacion.
 */
@Entity
@Table(name = "relacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Relacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_materia")
    private Integer codigoMateria;

    @Column(name = "codigo_materia_relacionada")
    private Integer codigoMateriaRelacionada;

    @Column(name = "tipo_relacion")
    private String tipoRelacion;

    @ManyToOne
    @JsonIgnoreProperties(value = { "materiaSolicituds", "relacions", "materiaSemestres" }, allowSetters = true)
    private Materia materia;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Relacion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoMateria() {
        return this.codigoMateria;
    }

    public Relacion codigoMateria(Integer codigoMateria) {
        this.setCodigoMateria(codigoMateria);
        return this;
    }

    public void setCodigoMateria(Integer codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public Integer getCodigoMateriaRelacionada() {
        return this.codigoMateriaRelacionada;
    }

    public Relacion codigoMateriaRelacionada(Integer codigoMateriaRelacionada) {
        this.setCodigoMateriaRelacionada(codigoMateriaRelacionada);
        return this;
    }

    public void setCodigoMateriaRelacionada(Integer codigoMateriaRelacionada) {
        this.codigoMateriaRelacionada = codigoMateriaRelacionada;
    }

    public String getTipoRelacion() {
        return this.tipoRelacion;
    }

    public Relacion tipoRelacion(String tipoRelacion) {
        this.setTipoRelacion(tipoRelacion);
        return this;
    }

    public void setTipoRelacion(String tipoRelacion) {
        this.tipoRelacion = tipoRelacion;
    }

    public Materia getMateria() {
        return this.materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Relacion materia(Materia materia) {
        this.setMateria(materia);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Relacion)) {
            return false;
        }
        return id != null && id.equals(((Relacion) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Relacion{" +
            "id=" + getId() +
            ", codigoMateria=" + getCodigoMateria() +
            ", codigoMateriaRelacionada=" + getCodigoMateriaRelacionada() +
            ", tipoRelacion='" + getTipoRelacion() + "'" +
            "}";
    }
}

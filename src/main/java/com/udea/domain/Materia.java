package com.udea.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Materia.
 */
@Entity
@Table(name = "materia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_materia")
    private Integer codigoMateria;

    @Column(name = "nombre_materia")
    private String nombreMateria;

    @Column(name = "creditos")
    private Integer creditos;

    @OneToMany(mappedBy = "materia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "materia", "solicitudHomologacion", "estudiante" }, allowSetters = true)
    private Set<MateriaSolicitud> materiaSolicituds = new HashSet<>();

    @OneToMany(mappedBy = "materia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "materia" }, allowSetters = true)
    private Set<Relacion> relacions = new HashSet<>();

    @OneToMany(mappedBy = "materia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "materia", "estudiante", "semestre" }, allowSetters = true)
    private Set<MateriaSemestre> materiaSemestres = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Materia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoMateria() {
        return this.codigoMateria;
    }

    public Materia codigoMateria(Integer codigoMateria) {
        this.setCodigoMateria(codigoMateria);
        return this;
    }

    public void setCodigoMateria(Integer codigoMateria) {
        this.codigoMateria = codigoMateria;
    }

    public String getNombreMateria() {
        return this.nombreMateria;
    }

    public Materia nombreMateria(String nombreMateria) {
        this.setNombreMateria(nombreMateria);
        return this;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public Integer getCreditos() {
        return this.creditos;
    }

    public Materia creditos(Integer creditos) {
        this.setCreditos(creditos);
        return this;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public Set<MateriaSolicitud> getMateriaSolicituds() {
        return this.materiaSolicituds;
    }

    public void setMateriaSolicituds(Set<MateriaSolicitud> materiaSolicituds) {
        if (this.materiaSolicituds != null) {
            this.materiaSolicituds.forEach(i -> i.setMateria(null));
        }
        if (materiaSolicituds != null) {
            materiaSolicituds.forEach(i -> i.setMateria(this));
        }
        this.materiaSolicituds = materiaSolicituds;
    }

    public Materia materiaSolicituds(Set<MateriaSolicitud> materiaSolicituds) {
        this.setMateriaSolicituds(materiaSolicituds);
        return this;
    }

    public Materia addMateriaSolicitud(MateriaSolicitud materiaSolicitud) {
        this.materiaSolicituds.add(materiaSolicitud);
        materiaSolicitud.setMateria(this);
        return this;
    }

    public Materia removeMateriaSolicitud(MateriaSolicitud materiaSolicitud) {
        this.materiaSolicituds.remove(materiaSolicitud);
        materiaSolicitud.setMateria(null);
        return this;
    }

    public Set<Relacion> getRelacions() {
        return this.relacions;
    }

    public void setRelacions(Set<Relacion> relacions) {
        if (this.relacions != null) {
            this.relacions.forEach(i -> i.setMateria(null));
        }
        if (relacions != null) {
            relacions.forEach(i -> i.setMateria(this));
        }
        this.relacions = relacions;
    }

    public Materia relacions(Set<Relacion> relacions) {
        this.setRelacions(relacions);
        return this;
    }

    public Materia addRelacion(Relacion relacion) {
        this.relacions.add(relacion);
        relacion.setMateria(this);
        return this;
    }

    public Materia removeRelacion(Relacion relacion) {
        this.relacions.remove(relacion);
        relacion.setMateria(null);
        return this;
    }

    public Set<MateriaSemestre> getMateriaSemestres() {
        return this.materiaSemestres;
    }

    public void setMateriaSemestres(Set<MateriaSemestre> materiaSemestres) {
        if (this.materiaSemestres != null) {
            this.materiaSemestres.forEach(i -> i.setMateria(null));
        }
        if (materiaSemestres != null) {
            materiaSemestres.forEach(i -> i.setMateria(this));
        }
        this.materiaSemestres = materiaSemestres;
    }

    public Materia materiaSemestres(Set<MateriaSemestre> materiaSemestres) {
        this.setMateriaSemestres(materiaSemestres);
        return this;
    }

    public Materia addMateriaSemestre(MateriaSemestre materiaSemestre) {
        this.materiaSemestres.add(materiaSemestre);
        materiaSemestre.setMateria(this);
        return this;
    }

    public Materia removeMateriaSemestre(MateriaSemestre materiaSemestre) {
        this.materiaSemestres.remove(materiaSemestre);
        materiaSemestre.setMateria(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Materia)) {
            return false;
        }
        return id != null && id.equals(((Materia) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Materia{" +
            "id=" + getId() +
            ", codigoMateria=" + getCodigoMateria() +
            ", nombreMateria='" + getNombreMateria() + "'" +
            ", creditos=" + getCreditos() +
            "}";
    }
}

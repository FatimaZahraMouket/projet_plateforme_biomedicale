package com.apbiomedicale.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Boitier.
 */
@Entity
@Table(name = "boitier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Boitier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "ref")
    private String ref;

    @Column(name = "nbr_branche")
    private Integer nbrBranche;

    @ManyToOne
    @JsonIgnoreProperties(value = { "videos", "boitiers" }, allowSetters = true)
    private Camera camera;

    @OneToMany(mappedBy = "boitiers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "boitiers", "capteurs" }, allowSetters = true)
    private Set<BoitierCapteur> boitierCapteurs = new HashSet<>();

    @OneToMany(mappedBy = "boitiers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "boitiers", "patients" }, allowSetters = true)
    private Set<BoitierPatient> boitierPatients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Boitier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Boitier type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRef() {
        return this.ref;
    }

    public Boitier ref(String ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Integer getNbrBranche() {
        return this.nbrBranche;
    }

    public Boitier nbrBranche(Integer nbrBranche) {
        this.setNbrBranche(nbrBranche);
        return this;
    }

    public void setNbrBranche(Integer nbrBranche) {
        this.nbrBranche = nbrBranche;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Boitier camera(Camera camera) {
        this.setCamera(camera);
        return this;
    }

    public Set<BoitierCapteur> getBoitierCapteurs() {
        return this.boitierCapteurs;
    }

    public void setBoitierCapteurs(Set<BoitierCapteur> boitierCapteurs) {
        if (this.boitierCapteurs != null) {
            this.boitierCapteurs.forEach(i -> i.setBoitiers(null));
        }
        if (boitierCapteurs != null) {
            boitierCapteurs.forEach(i -> i.setBoitiers(this));
        }
        this.boitierCapteurs = boitierCapteurs;
    }

    public Boitier boitierCapteurs(Set<BoitierCapteur> boitierCapteurs) {
        this.setBoitierCapteurs(boitierCapteurs);
        return this;
    }

    public Boitier addBoitierCapteurs(BoitierCapteur boitierCapteur) {
        this.boitierCapteurs.add(boitierCapteur);
        boitierCapteur.setBoitiers(this);
        return this;
    }

    public Boitier removeBoitierCapteurs(BoitierCapteur boitierCapteur) {
        this.boitierCapteurs.remove(boitierCapteur);
        boitierCapteur.setBoitiers(null);
        return this;
    }

    public Set<BoitierPatient> getBoitierPatients() {
        return this.boitierPatients;
    }

    public void setBoitierPatients(Set<BoitierPatient> boitierPatients) {
        if (this.boitierPatients != null) {
            this.boitierPatients.forEach(i -> i.setBoitiers(null));
        }
        if (boitierPatients != null) {
            boitierPatients.forEach(i -> i.setBoitiers(this));
        }
        this.boitierPatients = boitierPatients;
    }

    public Boitier boitierPatients(Set<BoitierPatient> boitierPatients) {
        this.setBoitierPatients(boitierPatients);
        return this;
    }

    public Boitier addBoitierPatients(BoitierPatient boitierPatient) {
        this.boitierPatients.add(boitierPatient);
        boitierPatient.setBoitiers(this);
        return this;
    }

    public Boitier removeBoitierPatients(BoitierPatient boitierPatient) {
        this.boitierPatients.remove(boitierPatient);
        boitierPatient.setBoitiers(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Boitier)) {
            return false;
        }
        return id != null && id.equals(((Boitier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Boitier{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", ref='" + getRef() + "'" +
            ", nbrBranche=" + getNbrBranche() +
            "}";
    }
}

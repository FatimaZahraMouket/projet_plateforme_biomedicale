package com.apbiomedicale.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Camera.
 */
@Entity
@Table(name = "camera")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Camera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "resolution")
    private String resolution;

    @OneToMany(mappedBy = "camera")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "camera" }, allowSetters = true)
    private Set<Video> videos = new HashSet<>();

    @OneToMany(mappedBy = "camera")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "camera", "boitierCapteurs", "boitierPatients" }, allowSetters = true)
    private Set<Boitier> boitiers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Camera id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Camera nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getResolution() {
        return this.resolution;
    }

    public Camera resolution(String resolution) {
        this.setResolution(resolution);
        return this;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Set<Video> getVideos() {
        return this.videos;
    }

    public void setVideos(Set<Video> videos) {
        if (this.videos != null) {
            this.videos.forEach(i -> i.setCamera(null));
        }
        if (videos != null) {
            videos.forEach(i -> i.setCamera(this));
        }
        this.videos = videos;
    }

    public Camera videos(Set<Video> videos) {
        this.setVideos(videos);
        return this;
    }

    public Camera addVideos(Video video) {
        this.videos.add(video);
        video.setCamera(this);
        return this;
    }

    public Camera removeVideos(Video video) {
        this.videos.remove(video);
        video.setCamera(null);
        return this;
    }

    public Set<Boitier> getBoitiers() {
        return this.boitiers;
    }

    public void setBoitiers(Set<Boitier> boitiers) {
        if (this.boitiers != null) {
            this.boitiers.forEach(i -> i.setCamera(null));
        }
        if (boitiers != null) {
            boitiers.forEach(i -> i.setCamera(this));
        }
        this.boitiers = boitiers;
    }

    public Camera boitiers(Set<Boitier> boitiers) {
        this.setBoitiers(boitiers);
        return this;
    }

    public Camera addBoitier(Boitier boitier) {
        this.boitiers.add(boitier);
        boitier.setCamera(this);
        return this;
    }

    public Camera removeBoitier(Boitier boitier) {
        this.boitiers.remove(boitier);
        boitier.setCamera(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Camera)) {
            return false;
        }
        return id != null && id.equals(((Camera) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Camera{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", resolution='" + getResolution() + "'" +
            "}";
    }
}

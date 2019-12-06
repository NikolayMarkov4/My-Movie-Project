package org.softuni.mymoviemaster.domain.entities;

import org.softuni.mymoviemaster.domain.enums.Genre;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie extends BaseEntity {
    private String name;
    private String description;
    private String photo;
    private String premiereDate;
    private Director director;
    private List<Actor> actors;
    private Genre genre;
    private Integer movieMinutes;

    public Movie() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "director_id", referencedColumnName = "id", nullable = false)
    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "movies_actors",
            joinColumns = @JoinColumn(
                    name = "movie_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "actor_id",
                    referencedColumnName = "id"
            )
    )
    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Column(name="premiere_date", updatable=false, nullable=false)
    public String getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(String premiereDate) {
        this.premiereDate = premiereDate;
    }

    @Column(name="photo", updatable=true, nullable=false)
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    @Column(name = "movie_minutes", nullable = false)
    public Integer getMovieMinutes() {
        return movieMinutes;
    }

    public void setMovieMinutes(Integer movieMinutes) {
        this.movieMinutes = movieMinutes;
    }
}
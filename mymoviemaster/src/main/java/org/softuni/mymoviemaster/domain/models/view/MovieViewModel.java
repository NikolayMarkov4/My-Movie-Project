package org.softuni.mymoviemaster.domain.models.view;

import org.softuni.mymoviemaster.domain.entities.Actor;

import java.util.Arrays;
import java.util.List;

public class MovieViewModel {
    private String id;
    private String name;
    private String photo;
    private String genre;
    private String description;
    private List<ActorFullViewModel> actors;
    private String premiereDate;
    private String directorName;
    private Integer movieMinutes;


    public MovieViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getMovieMinutes() {
        return movieMinutes;
    }

    public void setMovieMinutes(Integer movieMinutes) {
        this.movieMinutes = movieMinutes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ActorFullViewModel> getActors() {
        return actors;
    }

    public void setActors(List<ActorFullViewModel> actors) {
        this.actors = actors;
    }

    public String getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(String premiereDate) {
        this.premiereDate = premiereDate;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

}

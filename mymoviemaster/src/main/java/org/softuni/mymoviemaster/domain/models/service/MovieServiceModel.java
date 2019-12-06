package org.softuni.mymoviemaster.domain.models.service;

import org.softuni.mymoviemaster.domain.entities.Actor;
import org.softuni.mymoviemaster.domain.entities.Director;

import java.util.List;

public class MovieServiceModel extends BaseServiceModel {
    private String name;
    private String description;
    private String premiereDate;
    private String photo;
    private DirectorServiceModel director;
    private List<ActorServiceModel> actors;
    private String genre;
    private Integer movieMinutes;

    public MovieServiceModel() {
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(String premiereDate) {
        this.premiereDate = premiereDate;
    }

    public DirectorServiceModel getDirector() {
        return director;
    }

    public void setDirector(DirectorServiceModel director) {
        this.director = director;
    }

    public List<ActorServiceModel> getActors() {
        return actors;
    }

    public void setActors(List<ActorServiceModel> actors) {
        this.actors = actors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getMovieMinutes() {
        return movieMinutes;
    }

    public void setMovieMinutes(Integer movieMinutes) {
        this.movieMinutes = movieMinutes;
    }
}

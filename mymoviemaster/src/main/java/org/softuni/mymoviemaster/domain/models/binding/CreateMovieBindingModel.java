package org.softuni.mymoviemaster.domain.models.binding;

import org.softuni.mymoviemaster.domain.entities.Actor;
import org.softuni.mymoviemaster.domain.entities.Director;
import org.softuni.mymoviemaster.domain.enums.Genre;
import org.softuni.mymoviemaster.domain.models.service.ActorServiceModel;
import org.softuni.mymoviemaster.domain.models.service.DirectorServiceModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CreateMovieBindingModel {
    private String name;
    private String description;
    private MultipartFile photo;
    private String premiereDate;
    private Director director;
    private List<Actor> actors;
    private Genre genre;
    private Integer movieMinutes;

    public CreateMovieBindingModel() {
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

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public String getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(String premiereDate) {
        this.premiereDate = premiereDate;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public Integer getMovieMinutes() {
        return movieMinutes;
    }

    public void setMovieMinutes(Integer movieMinutes) {
        this.movieMinutes = movieMinutes;
    }
}

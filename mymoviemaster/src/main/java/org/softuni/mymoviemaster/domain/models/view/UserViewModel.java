package org.softuni.mymoviemaster.domain.models.view;

import org.softuni.mymoviemaster.domain.entities.Movie;

import java.util.List;

public class UserViewModel {
    private String id;
    private String username;
    private String email;
    private List<Movie> movies;

    public UserViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}

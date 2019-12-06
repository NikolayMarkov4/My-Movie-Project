package org.softuni.mymoviemaster.domain.models.service;

import java.util.List;
import java.util.Set;

public class UserServiceModel extends BaseServiceModel {
    private String username;
    private String password;
    private String email;
    private List<MovieServiceModel> movies;

    private Set<RoleServiceModel> authorities;

    public UserServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }

    public List<MovieServiceModel> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieServiceModel> movies) {
        this.movies = movies;
    }
}

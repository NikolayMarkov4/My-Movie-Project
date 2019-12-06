package org.softuni.mymoviemaster.domain.models.service;

public class DirectorServiceModel  extends BaseServiceModel{
    private String name;
    private String biography;
    private String photo;

    public DirectorServiceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

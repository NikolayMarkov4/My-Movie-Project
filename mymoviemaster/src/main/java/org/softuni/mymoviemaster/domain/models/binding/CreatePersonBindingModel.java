package org.softuni.mymoviemaster.domain.models.binding;

import org.springframework.web.multipart.MultipartFile;

public class CreatePersonBindingModel {
    private String name;
    private String biography;
    private MultipartFile photo;

    public CreatePersonBindingModel() {
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

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
}

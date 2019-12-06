package org.softuni.mymoviemaster.domain.models.view;

import java.util.Arrays;
import java.util.List;

public class DirectorViewModel {
    private String id;
    private String photo;
    private String name;
    private String biography;
    private String shortBiography;

    public DirectorViewModel() {
        this.setBiography(this.getBiography());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photoUrl) {
        this.photo = photoUrl;
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

    public String getShortBiography() {
        return shortBiography;
    }

    public void setShortBiography(String shortBiography) {
        List<String> shortSentence = Arrays.asList(shortBiography.split("\\."));

        for (int i = 0; i < 2; i++) {
            this.shortBiography +=shortSentence.get(i);
        }

        this.shortBiography += "...";
    }
}

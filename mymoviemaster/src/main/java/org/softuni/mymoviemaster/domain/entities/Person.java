package org.softuni.mymoviemaster.domain.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@MappedSuperclass
public abstract class Person {
    private String id;
    private String name;
    private String biography;
    private String photo;

    public Person() {
    }

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(
            name = "uuid-string",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "biography", nullable = false, columnDefinition = "TEXT")
    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Column(name = "photo", nullable = false)
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photoLink) {
        this.photo = photoLink;
    }
}

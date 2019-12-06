package org.softuni.mymoviemaster.domain.models.view;

public class ActorViewModel {
    private String id;
    private String photo;
    private String name;

    public ActorViewModel() {
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
}

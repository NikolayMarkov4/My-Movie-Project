package org.softuni.mymoviemaster.service;

import org.softuni.mymoviemaster.domain.entities.Actor;
import org.softuni.mymoviemaster.domain.models.service.ActorServiceModel;
import org.softuni.mymoviemaster.domain.models.view.ActorViewModel;
import org.softuni.mymoviemaster.domain.models.view.MovieViewModel;

import java.util.Collection;
import java.util.List;

public interface ActorService {
    ActorServiceModel addActor(ActorServiceModel actorServiceModel);
    List<ActorServiceModel> findAllActors();
    ActorServiceModel findActorById(String id);
    ActorServiceModel deleteActor(String id);
    ActorServiceModel editActor(String id, ActorServiceModel actorServiceModel);
    List<MovieViewModel> getAllMoviesForActor(String id);
    List<ActorServiceModel> findAllLimitFive();
}

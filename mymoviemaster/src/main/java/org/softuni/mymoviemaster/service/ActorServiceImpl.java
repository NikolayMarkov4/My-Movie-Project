package org.softuni.mymoviemaster.service;

import org.modelmapper.ModelMapper;
import org.softuni.mymoviemaster.domain.entities.Actor;
import org.softuni.mymoviemaster.domain.models.service.ActorServiceModel;
import org.softuni.mymoviemaster.domain.models.view.MovieViewModel;
import org.softuni.mymoviemaster.repository.ActorRepository;
import org.softuni.mymoviemaster.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ActorServiceImpl(ActorRepository actorRepository, MovieRepository movieRepository, ModelMapper modelMapper) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ActorServiceModel addActor(ActorServiceModel actorServiceModel) {
        Actor actor = this.modelMapper.map(actorServiceModel, Actor.class);

        return this.modelMapper.map(this.actorRepository.saveAndFlush(actor), ActorServiceModel.class);
    }

    @Override
    public List<ActorServiceModel> findAllActors() {
        return this.actorRepository.findAll()
                .stream()
                .map(actor -> this.modelMapper.map(actor, ActorServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ActorServiceModel findActorById(String id) {
        Actor actor = this.actorRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return this.modelMapper.map(actor, ActorServiceModel.class);
    }


    @Override
    public ActorServiceModel deleteActor(String id) {
        Actor actor = this.actorRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        this.actorRepository.delete(actor);

        return this.modelMapper.map(actor, ActorServiceModel.class);
    }

    @Override
    public ActorServiceModel editActor(String id, ActorServiceModel actorServiceModel) {
        Actor actor = this.actorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Something went wrong"));

        actor.setName(actorServiceModel.getName());
        actor.setBiography(actorServiceModel.getBiography());
        actor.setPhoto(actorServiceModel.getPhoto());

        this.actorRepository.saveAndFlush(actor);

        return this.modelMapper.map(actor, ActorServiceModel.class);
    }

    @Override
    public List<MovieViewModel> getAllMoviesForActor(String id) {

        List<MovieViewModel> actorMovies = this.movieRepository.getAllMoviesForActor(id)
                .stream()
                .map(movie -> this.modelMapper.map(movie, MovieViewModel.class))
                .collect(Collectors.toList());

        return actorMovies;
    }

    @Override
    public List<ActorServiceModel> findAllLimitFive() {
        List<ActorServiceModel> actorServiceModels = this.actorRepository.findFirst10Actors()
                .stream()
                .map(actor -> this.modelMapper.map(actor, ActorServiceModel.class))
                .collect(Collectors.toList());

        return actorServiceModels;
    }
}

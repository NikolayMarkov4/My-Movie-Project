package org.softuni.mymoviemaster.service;

import org.modelmapper.ModelMapper;
import org.softuni.mymoviemaster.domain.entities.Actor;
import org.softuni.mymoviemaster.domain.entities.Director;
import org.softuni.mymoviemaster.domain.entities.Movie;
import org.softuni.mymoviemaster.domain.enums.Genre;
import org.softuni.mymoviemaster.domain.models.binding.CreateMovieBindingModel;
import org.softuni.mymoviemaster.domain.models.service.MovieServiceModel;
import org.softuni.mymoviemaster.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;
    private final DirectorService directorService;
    private final ActorService actorService;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, ModelMapper modelMapper, DirectorService directorService, ActorService actorService, CloudinaryService cloudinaryService) {
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
        this.directorService = directorService;
        this.actorService = actorService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public List<MovieServiceModel> findAllMoviesForUser(String id) {
        return this.movieRepository.getAllMoviesForActor(id)
                .stream()
                .map(movie -> this.modelMapper.map(movie, MovieServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public MovieServiceModel addMovie(CreateMovieBindingModel model) throws IOException {
        MovieServiceModel movieModel = this.modelMapper.map(model, MovieServiceModel.class);

        movieModel.setPhoto(this.cloudinaryService.uploadImage(model.getPhoto()));

        Movie movie = this.modelMapper.map(movieModel, Movie.class);

        return this.modelMapper.map(this.movieRepository.saveAndFlush(movie), MovieServiceModel.class);
    }

    @Override
    public List<MovieServiceModel> getAllMovies() {
        return this.movieRepository.findAll()
                .stream()
                .map(movie -> this.modelMapper.map(movie, MovieServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public MovieServiceModel findMovieById(String id) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        MovieServiceModel movieServiceModel = this.modelMapper.map(movie, MovieServiceModel.class);

        return movieServiceModel;
    }

    @Override
    public MovieServiceModel deleteMovie(String id) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        MovieServiceModel movieServiceModel = this.modelMapper.map(movie, MovieServiceModel.class);
        this.movieRepository.delete(movie);

        return movieServiceModel;
    }

    @Override
    public MovieServiceModel editMovie(String id, MovieServiceModel model) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        movie.setName(model.getName());

        movie.setActors(model.getActors()
                .stream()
                .map(actorServiceModel -> this.modelMapper.map(actorServiceModel, Actor.class))
                .collect(Collectors.toList()));

        movie.setDirector(this.modelMapper.map(model.getDirector(), Director.class));

        movie.setDescription(model.getDescription());

        movie.setGenre(Genre.valueOf(model.getGenre()));

        movie.setMovieMinutes(model.getMovieMinutes());

        movie.setPhoto(model.getPhoto());

        movie.setPremiereDate(model.getPremiereDate());

        Movie movieToReturn = this.movieRepository.saveAndFlush(movie);

        //TODO: make a builder design pattern for the edit

        return this.modelMapper.map(movieToReturn, MovieServiceModel.class);
    }

    @Override
    public List<MovieServiceModel> findAllLimitFive() {
        List<MovieServiceModel> movieServiceModels = this.movieRepository.findAllLimitFive()
                .stream()
                .map(movie -> this.modelMapper.map(movie, MovieServiceModel.class))
                .collect(Collectors.toList());

        return movieServiceModels;
    }

}

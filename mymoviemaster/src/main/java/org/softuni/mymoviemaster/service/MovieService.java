package org.softuni.mymoviemaster.service;

import org.softuni.mymoviemaster.domain.models.binding.CreateMovieBindingModel;
import org.softuni.mymoviemaster.domain.models.service.MovieServiceModel;
import org.softuni.mymoviemaster.domain.models.view.ActorViewModel;

import java.io.IOException;
import java.util.List;

public interface MovieService  {
    List<MovieServiceModel> findAllMoviesForUser(String id);
    MovieServiceModel addMovie(CreateMovieBindingModel model) throws IOException;
    List<MovieServiceModel> getAllMovies();
    MovieServiceModel findMovieById(String id);
    MovieServiceModel deleteMovie(String id);
    MovieServiceModel editMovie(String id, MovieServiceModel model);
    List<MovieServiceModel> findAllLimitFive();
}

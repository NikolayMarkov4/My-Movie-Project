package org.softuni.mymoviemaster.service;

import org.softuni.mymoviemaster.domain.entities.Director;
import org.softuni.mymoviemaster.domain.models.service.DirectorServiceModel;
import org.softuni.mymoviemaster.domain.models.view.MovieViewModel;

import java.util.List;

public interface DirectorService {
    DirectorServiceModel addActor(DirectorServiceModel directorServiceModel);
    List<DirectorServiceModel> findAllDirectors();
    DirectorServiceModel findDirectorById(String id);
    DirectorServiceModel findDirectorByName(String name);
    DirectorServiceModel deleteDirector(String id);
    DirectorServiceModel editDirector(String id, DirectorServiceModel directorServiceModel);
    List<MovieViewModel> getAllMoviesForDirector(String id);
    List<DirectorServiceModel> findAllLimitFive();
}

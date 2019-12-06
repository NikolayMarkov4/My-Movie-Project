package org.softuni.mymoviemaster.service;

import org.modelmapper.ModelMapper;
import org.softuni.mymoviemaster.domain.entities.Director;
import org.softuni.mymoviemaster.domain.models.service.DirectorServiceModel;
import org.softuni.mymoviemaster.domain.models.view.DirectorViewModel;
import org.softuni.mymoviemaster.domain.models.view.MovieViewModel;
import org.softuni.mymoviemaster.repository.ActorRepository;
import org.softuni.mymoviemaster.repository.DirectorRepository;
import org.softuni.mymoviemaster.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;
    private final MovieRepository movieRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DirectorServiceImpl(DirectorRepository directorRepository, MovieRepository movieRepository, ModelMapper modelMapper) {
        this.directorRepository = directorRepository;
        this.movieRepository = movieRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DirectorServiceModel addActor(DirectorServiceModel directorServiceModel) {
        Director director = this.modelMapper.map(directorServiceModel, Director.class);

        return this.modelMapper.map(this.directorRepository.saveAndFlush(director), DirectorServiceModel.class);
    }

    @Override
    public List<DirectorServiceModel> findAllDirectors() {
        return this.directorRepository.findAll()
                .stream()
                .map(director -> this.modelMapper.map(director, DirectorServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public DirectorServiceModel findDirectorById(String id) {
        Director director = this.directorRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return this.modelMapper.map(director, DirectorServiceModel.class);
    }


    @Override
    public DirectorServiceModel findDirectorByName(String name) {
        Director director = this.directorRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new);

        return this.modelMapper.map(director, DirectorServiceModel.class);
    }
    @Override
    public DirectorServiceModel deleteDirector(String id) {
        Director director = this.directorRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        this.directorRepository.delete(director);

        return this.modelMapper.map(director, DirectorServiceModel.class);
    }

    @Override
    public DirectorServiceModel editDirector(String id, DirectorServiceModel directorServiceModel) {
        Director director = this.directorRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        director.setName(directorServiceModel.getName());
        director.setBiography(directorServiceModel.getBiography());
        director.setPhoto(directorServiceModel.getPhoto());

        return this.modelMapper.map(this.directorRepository.saveAndFlush(director), DirectorServiceModel.class);
    }

    @Override
    public List<MovieViewModel> getAllMoviesForDirector(String id) {
        return this.movieRepository.getAllByDirectorId(id)
                .stream()
                .map(movie -> this.modelMapper.map(movie, MovieViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DirectorServiceModel> findAllLimitFive() {
        List<DirectorServiceModel> directorServiceModels = this.directorRepository.findAllLimitFive()
                .stream()
                .map(actor -> this.modelMapper.map(actor, DirectorServiceModel.class))
                .collect(Collectors.toList());

        return directorServiceModels;
    }
}

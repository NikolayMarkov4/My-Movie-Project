package org.softuni.mymoviemaster.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.mymoviemaster.domain.models.binding.CreateMovieBindingModel;
import org.softuni.mymoviemaster.domain.models.service.ActorServiceModel;
import org.softuni.mymoviemaster.domain.models.service.DirectorServiceModel;
import org.softuni.mymoviemaster.domain.models.service.MovieServiceModel;
import org.softuni.mymoviemaster.domain.models.service.UserServiceModel;
import org.softuni.mymoviemaster.domain.models.view.DirectorViewModel;
import org.softuni.mymoviemaster.domain.models.view.MovieViewModel;
import org.softuni.mymoviemaster.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movies")
public class MovieController extends BaseController {
    private final UserService userService;
    private final MovieService movieService;
    private final DirectorService directorService;
    private final ActorService actorService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public MovieController(UserService userService, MovieService movieService, DirectorService directorService, ActorService actorService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.userService = userService;
        this.movieService = movieService;
        this.directorService = directorService;
        this.actorService = actorService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/movieAdminSettings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getAdminMoviesView(ModelAndView modelAndView) {
        List<MovieViewModel> movies = this.movieService.getAllMovies()
                .stream().map(movieServiceModel -> this.modelMapper.map(movieServiceModel, MovieViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("movies", movies);

        return super.view("movies/movieAdminSettings", modelAndView);
    }

    @GetMapping("/adminCreateMovie")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addMovieView(ModelAndView modelAndView) {
        List<DirectorServiceModel> directorServiceModels = this.directorService.findAllDirectors();
        List<ActorServiceModel> actorServiceModels = this.actorService.findAllActors();

        modelAndView.addObject("actors", actorServiceModels);
        modelAndView.addObject("directors", directorServiceModels);

        return super.view("movies/adminCreateMovie", modelAndView);
    }

    @PostMapping("/adminCreateMovie")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addMovieConfirm(@ModelAttribute CreateMovieBindingModel model) throws IOException {
        this.movieService.addMovie(model);

        return super.redirect("/home");
    }

    @GetMapping("/movieDelete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteMovie(@PathVariable String id, ModelAndView modelAndView) {
        MovieServiceModel movieServiceModel = this.movieService.findMovieById(id);
        MovieViewModel movieModel = this.modelMapper.map(movieServiceModel, MovieViewModel.class);

        modelAndView.addObject("movie", movieModel);
        modelAndView.addObject("movieId", id);

        return super.view("movies/movieDelete", modelAndView);
    }

    @PostMapping("/movieDelete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteMovieConfirm(@PathVariable String id) {
        this.movieService.deleteMovie(id);

        return super.redirect("/home");
    }

    @GetMapping("/movieEdit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView movieEdit(@PathVariable String id, ModelAndView modelAndView) {
        MovieServiceModel movieServiceModel = this.movieService.findMovieById(id);
        CreateMovieBindingModel model = this.modelMapper.map(movieServiceModel, CreateMovieBindingModel.class);
        List<DirectorServiceModel> directorServiceModels = this.directorService.findAllDirectors();
        List<ActorServiceModel> actorServiceModels = this.actorService.findAllActors();

        modelAndView.addObject("actors", actorServiceModels);
        modelAndView.addObject("directors", directorServiceModels);
        modelAndView.addObject("movie", model);
        modelAndView.addObject("movieId", id);

        return super.view("movies/movieEdit", modelAndView);
    }

    @PostMapping("/movieEdit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView movieEditConfirm(@PathVariable String id, @ModelAttribute CreateMovieBindingModel model) throws IOException {
        MovieServiceModel movieServiceModel = this.modelMapper.map(model, MovieServiceModel.class);

        movieServiceModel.setPhoto(this.cloudinaryService.uploadImage(model.getPhoto()));

        this.movieService.editMovie(id, movieServiceModel);

        return super.redirect("/home");
    }

    @GetMapping("/movieDetails/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsMovie(@PathVariable String id, ModelAndView modelAndView) {
        MovieViewModel movieViewModel = this.modelMapper.map(this.movieService.findMovieById(id), MovieViewModel.class);
        DirectorViewModel directorViewModel = this.modelMapper
                .map(this.directorService.findDirectorByName(movieViewModel.getDirectorName()), DirectorViewModel.class);

        modelAndView.addObject("actors", movieViewModel.getActors());
        modelAndView.addObject("movie", movieViewModel);
        modelAndView.addObject("director", directorViewModel);

        return super.view("/movies/movieDetails", modelAndView);
    }

    @PostMapping("/movieDetails/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView movieAddToWatchList(@PathVariable String id, ModelAndView modelAndView, Principal principal) {
        UserServiceModel userServiceModel = this.userService.findUserByUserName(principal.getName());
        MovieServiceModel movieServiceModel = this.modelMapper.map(this.movieService.findMovieById(id), MovieServiceModel.class);

        this.userService.addMovieToWatchList(movieServiceModel, userServiceModel);

        return super.view("/home", modelAndView);
    }

    @GetMapping("/allMovies")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView movieSeeAll(ModelAndView modelAndView) {
        List<MovieViewModel> allMovies = this.movieService.getAllMovies()
                .stream()
                .map(movieServiceModel -> this.modelMapper.map(movieServiceModel, MovieViewModel.class))
                .collect(Collectors.toList());

        List<String> shortDescriptions;

        modelAndView.addObject("movies", allMovies);

        return this.view("movies/allMovies", modelAndView);
    }

}

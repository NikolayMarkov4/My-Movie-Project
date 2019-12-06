package org.softuni.mymoviemaster.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.mymoviemaster.domain.models.view.ActorViewModel;
import org.softuni.mymoviemaster.domain.models.view.DirectorViewModel;
import org.softuni.mymoviemaster.domain.models.view.MovieViewModel;
import org.softuni.mymoviemaster.service.ActorService;
import org.softuni.mymoviemaster.service.DirectorService;
import org.softuni.mymoviemaster.service.MovieService;
import org.softuni.mymoviemaster.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {
    private final ActorService actorService;
    private final MovieService movieService;
    private final DirectorService directorService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(ActorService actorService, MovieService movieService, DirectorService directorService, ModelMapper modelMapper) {
        this.actorService = actorService;
        this.movieService = movieService;
        this.directorService = directorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Home")
    public ModelAndView index() {
        return super.view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView) {
        List<ActorViewModel> actorViewModels = this.actorService.findAllLimitFive()
                .stream()
                .map(actorServiceModel -> this.modelMapper.map(actorServiceModel, ActorViewModel.class))
                .collect(Collectors.toList());

        List<MovieViewModel> movieViewModels = this.movieService.findAllLimitFive()
                .stream()
                .map(movieServiceModel -> this.modelMapper.map(movieServiceModel, MovieViewModel.class))
                .collect(Collectors.toList());

        List<DirectorViewModel> directorViewModels = this.directorService.findAllLimitFive()
                .stream()
                .map(directorServiceModel -> this.modelMapper.map(directorServiceModel, DirectorViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("movies", movieViewModels);
        modelAndView.addObject("actors", actorViewModels);
        modelAndView.addObject("directors", directorViewModels);

        return super.view("home", modelAndView);
    }
}

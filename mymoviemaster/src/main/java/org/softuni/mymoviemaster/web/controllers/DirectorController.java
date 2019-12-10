package org.softuni.mymoviemaster.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.mymoviemaster.domain.models.binding.CreatePersonBindingModel;
import org.softuni.mymoviemaster.domain.models.service.DirectorServiceModel;
import org.softuni.mymoviemaster.domain.models.view.DirectorViewModel;
import org.softuni.mymoviemaster.domain.models.view.MovieViewModel;
import org.softuni.mymoviemaster.service.CloudinaryService;
import org.softuni.mymoviemaster.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/directors")
public class DirectorController extends BaseController{
    private final DirectorService directorService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public DirectorController(DirectorService directorService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.directorService = directorService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/directorAdminSettings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getDirectorAdminSettingsView(ModelAndView modelAndView){
        List<DirectorViewModel> directors = this.directorService.findAllDirectors()
                .stream()
                .map(directorServiceModel -> this.modelMapper.map(directorServiceModel, DirectorViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("directors", directors);

        return super.view("directors/directorAdminSettings", modelAndView);
    }

    @GetMapping("/adminCreateDirector")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addDirector() {
        return super.view("directors/adminCreateDirector");
    }

    @PostMapping("/adminCreateDirector")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addDirectorConfirm(@ModelAttribute CreatePersonBindingModel model) throws IOException {
        DirectorServiceModel directorModel = this.modelMapper.map(model, DirectorServiceModel.class);

        directorModel.setPhoto(this.cloudinaryService.uploadImage(model.getPhoto()));

        this.directorService.addActor(directorModel);

        return super.redirect("/home");
    }

    @GetMapping("/directorDelete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteDirector(@PathVariable String id, ModelAndView modelAndView) {
        DirectorServiceModel director = this.directorService.findDirectorById(id);
        CreatePersonBindingModel model = this.modelMapper.map(director, CreatePersonBindingModel.class);

        modelAndView.addObject("director", model);
        modelAndView.addObject("directorId", id);

        return super.view("directors/directorDelete", modelAndView);
    }

    @PostMapping("/directorDelete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteDirectorConfirm(@PathVariable String id) {
        this.directorService.deleteDirector(id);

        return super.redirect("/home");
    }

    @GetMapping("/directorEdit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView directorEdit(@PathVariable String id, ModelAndView modelAndView) {
        DirectorServiceModel director = this.directorService.findDirectorById(id);
        CreatePersonBindingModel model = this.modelMapper.map(director, CreatePersonBindingModel.class);

        modelAndView.addObject("director", model);
        modelAndView.addObject("directorId", id);

        return super.view("directors/directorEdit", modelAndView);
    }

    @PostMapping("/directorEdit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView directorEditConfirm(@PathVariable String id, @ModelAttribute CreatePersonBindingModel model) throws IOException {
        DirectorServiceModel directorServiceModel = this.modelMapper.map(model, DirectorServiceModel.class);
        directorServiceModel.setPhoto(this.cloudinaryService.uploadImage(model.getPhoto()));

        this.directorService.editDirector(id, directorServiceModel);

        return super.redirect("/home");
    }

    @GetMapping("/directorDetails/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsDirector(@PathVariable String id, ModelAndView modelAndView) {
        DirectorViewModel directorFullViewModel = this.modelMapper.map(this.directorService.findDirectorById(id), DirectorViewModel.class);

        List<MovieViewModel> actorMovies = this.directorService.getAllMoviesForDirector(id)
                .stream()
                .map(movie -> this.modelMapper.map(movie, MovieViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("movies", actorMovies);
        modelAndView.addObject("director", directorFullViewModel);

        return super.view("/directors/directorDetails", modelAndView);
    }

    @GetMapping("/allDirectors")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView seeAllDirectors( ModelAndView modelAndView) {
        List<DirectorViewModel> directors = this.directorService.findAllDirectors()
                .stream()
                .map(directorServiceModel -> this.modelMapper.map(directorServiceModel, DirectorViewModel.class))
                .collect(Collectors.toList());

        directors.forEach(directorViewModel -> directorViewModel.setShortBiography(directorViewModel.getBiography()));

        modelAndView.addObject("directors", directors);

        return super.view("/directors/allDirectors", modelAndView);
    }
}

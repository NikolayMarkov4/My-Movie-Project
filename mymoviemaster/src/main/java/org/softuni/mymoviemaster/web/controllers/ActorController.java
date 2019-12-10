package org.softuni.mymoviemaster.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.mymoviemaster.domain.models.binding.CreatePersonBindingModel;
import org.softuni.mymoviemaster.domain.models.service.ActorServiceModel;
import org.softuni.mymoviemaster.domain.models.view.ActorFullViewModel;
import org.softuni.mymoviemaster.domain.models.view.ActorViewModel;
import org.softuni.mymoviemaster.domain.models.view.MovieViewModel;
import org.softuni.mymoviemaster.service.ActorService;
import org.softuni.mymoviemaster.service.CloudinaryService;
import org.softuni.mymoviemaster.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/actors")
public class ActorController extends BaseController {
    private final ActorService actorService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public ActorController(ActorService actorService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
        this.actorService = actorService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/actorAdminSettings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("ActorAdmin")
    public ModelAndView adminGetActorsView(ModelAndView modelAndView) {
        List<ActorViewModel> actors = this.actorService.findAllActors()
                .stream().map(actor -> this.modelMapper.map(actor, ActorViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("actors", actors);

        return super.view("actors/actorAdminSettings", modelAndView);
    }

    @GetMapping("/adminCreateActor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addActor() {
        return super.view("actors/adminCreateActor");
    }

    @PostMapping("/adminCreateActor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addActorConfirm(@ModelAttribute CreatePersonBindingModel model) throws IOException {
        ActorServiceModel actorModel = this.modelMapper.map(model, ActorServiceModel.class);

        actorModel.setPhoto(this.cloudinaryService.uploadImage(model.getPhoto()));

        this.actorService.addActor(actorModel);

        return super.redirect("/home");
    }

    @GetMapping("/actorDelete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteActor(@PathVariable String id, ModelAndView modelAndView) {
        ActorServiceModel actor = this.actorService.findActorById(id);
        CreatePersonBindingModel model = this.modelMapper.map(actor, CreatePersonBindingModel.class);

        modelAndView.addObject("actor", model);
        modelAndView.addObject("actorId", id);

        return super.view("actors/actorDelete", modelAndView);
    }

    @PostMapping("/actorDelete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteActorConfirm(@PathVariable String id) {
        this.actorService.deleteActor(id);

        return super.redirect("/home");
    }

    @GetMapping("/actorEdit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView actorEdit(@PathVariable String id, ModelAndView modelAndView) {
        ActorServiceModel actor = this.actorService.findActorById(id);
        CreatePersonBindingModel model = this.modelMapper.map(actor, CreatePersonBindingModel.class);

        modelAndView.addObject("actor", model);
        modelAndView.addObject("actorId", id);

        return super.view("actors/actorEdit", modelAndView);
    }

    @PostMapping("/actorEdit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editActorConfirm(@PathVariable String id, @ModelAttribute CreatePersonBindingModel model) throws IOException {
        ActorServiceModel actorServiceModel = this.modelMapper.map(model, ActorServiceModel.class);
        actorServiceModel.setPhoto(this.cloudinaryService.uploadImage(model.getPhoto()));

        this.actorService.editActor(id, actorServiceModel);

        return super.redirect("/home");
    }

    @GetMapping("/actorDetails/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsActor(@PathVariable String id, ModelAndView modelAndView) {
        ActorFullViewModel actorFullViewModel = this.modelMapper.map(this.actorService.findActorById(id), ActorFullViewModel.class);

        List<MovieViewModel> actorMovies = this.actorService.getAllMoviesForActor(id)
                .stream()
                .map(movie -> this.modelMapper.map(movie, MovieViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("movies", actorMovies);
        modelAndView.addObject("actor", actorFullViewModel);

        return super.view("/actors/actorDetails", modelAndView);
    }
    @GetMapping("/allActors")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView seeAllDirectors( ModelAndView modelAndView) {
        List<ActorViewModel> actors = this.actorService.findAllActors()
                .stream()
                .map(directorServiceModel -> this.modelMapper.map(directorServiceModel, ActorViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("actors", actors);

        return super.view("/actors/allActors", modelAndView);
    }

}

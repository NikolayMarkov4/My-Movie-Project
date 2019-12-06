package org.softuni.mymoviemaster.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.mymoviemaster.domain.models.binding.UserRegisterBindingModel;
import org.softuni.mymoviemaster.domain.models.service.UserServiceModel;
import org.softuni.mymoviemaster.domain.models.view.UserViewModel;
import org.softuni.mymoviemaster.service.UserService;
import org.softuni.mymoviemaster.validation.userValidation.UserRegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserRegisterValidator userRegisterValidator;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, UserRegisterValidator userRegisterValidator) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userRegisterValidator = userRegisterValidator;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(ModelAndView modelAndView, @ModelAttribute(name = "model") UserRegisterBindingModel model) {
        modelAndView.addObject("model", model);

        return super.view("users/register", modelAndView);
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(ModelAndView modelAndView, @ModelAttribute(name = "model") UserRegisterBindingModel model
            , BindingResult bindingResult) {

        this.userRegisterValidator.validate(model, bindingResult);

        if (bindingResult.hasErrors()) {
            model.setPassword(null);
            model.setConfirmPassword(null);
            modelAndView.addObject("model", model);

            return super.view("users/register", modelAndView);
        }

        UserServiceModel userServiceModel = this.modelMapper.map(model, UserServiceModel.class);
        this.userService.registerUser(userServiceModel);

        return super.redirect("/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        return super.view("users/login");
    }


    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/allUsersAdminSettings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView adminAllUsersView(ModelAndView modelAndView, Authentication authentication){
        List<UserViewModel> allUsers = this.userService.findAllUsers()
                .stream()
                .map(user -> this.modelMapper.map(user, UserViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("users", allUsers);

        return super.view("users/allUsersAdminSettings",modelAndView);
    }

    @GetMapping("/userDelete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView userDelete(@PathVariable String id, ModelAndView modelAndView){
        UserViewModel user = this.modelMapper.map(this.userService.findUserById(id), UserViewModel.class);

        modelAndView.addObject("userId", id);
        modelAndView.addObject("user", user);

        return super.view("users/userDelete",modelAndView);
    }

    @PostMapping("/userDelete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView userDeleteConfirm(@PathVariable String id) {
        this.userService.deleteUser(id);

        return super.redirect("/home");
    }

    @GetMapping("/userProfile/{id}")
    public ModelAndView userDetailsPage(@PathVariable String id, ModelAndView modelAndView){
        UserViewModel userViewModel = this.modelMapper.map(this.userService.findUserById(id), UserViewModel.class);

        modelAndView.addObject("user", userViewModel);
        modelAndView.addObject("movies", userViewModel.getMovies());

        return super.view("users/userProfile",modelAndView);
    }
}
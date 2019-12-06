package org.softuni.mymoviemaster.service;

import org.softuni.mymoviemaster.domain.models.service.MovieServiceModel;
import org.softuni.mymoviemaster.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUserName(String username);

    List<UserServiceModel> findAllUsers();

    UserServiceModel findUserById(String id);

    UserServiceModel deleteUser(String id);

    UserServiceModel addMovieToWatchList(MovieServiceModel movieServiceModel, UserServiceModel userServiceModel);
}

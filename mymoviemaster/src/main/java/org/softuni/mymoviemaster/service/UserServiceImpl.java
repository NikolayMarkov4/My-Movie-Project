package org.softuni.mymoviemaster.service;

import org.modelmapper.ModelMapper;
import org.softuni.mymoviemaster.domain.entities.User;
import org.softuni.mymoviemaster.domain.models.service.MovieServiceModel;
import org.softuni.mymoviemaster.domain.models.service.UserServiceModel;
import org.softuni.mymoviemaster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        this.roleService.seedRolesInDb();
        if (this.userRepository.count() == 0) {
            userServiceModel.setAuthorities(this.roleService.findAllRoles());
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        }


        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserByUserName(String username) {
        return this.userRepository.findByUsername(username)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new Error("Username not found!"));
    }

    @Override
    public List<UserServiceModel> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel findUserById(String id) {
        return this.userRepository.findById(id)
                .map(u -> this.modelMapper.map(u, UserServiceModel.class))
                .orElseThrow(() -> new Error("Username not found!"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }


    @Override
    public UserServiceModel deleteUser(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        this.userRepository.delete(user);

        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel addMovieToWatchList(MovieServiceModel movieServiceModel, UserServiceModel userServiceModel) {
        userServiceModel.getMovies().add(movieServiceModel);

        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
        return userServiceModel;
    }
}

package org.softuni.mymoviemaster.sheduleJobs.implementation;

import org.modelmapper.ModelMapper;
import org.softuni.mymoviemaster.domain.entities.User;
import org.softuni.mymoviemaster.service.EmailService;
import org.softuni.mymoviemaster.service.UserService;
import org.softuni.mymoviemaster.sheduleJobs.constants.EmailConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@EnableAsync
@Component
public class EmailScheduleJob {
    private final UserService userService;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    @Autowired
    public EmailScheduleJob(UserService userService, EmailService emailService, ModelMapper modelMapper) {
        this.userService = userService;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
    }

//    @Async
//    @Scheduled(cron = "0 0/1 * 1/1 * ?")
//    public void emailScheduledJob() {
//        List<User> users = userService.findAllUsers()
//                .stream()
//                .map(userServiceModel -> this.modelMapper.map(userServiceModel, User.class))
//                .collect(Collectors.toList());
//
//        users.forEach(user -> emailService.sendSimpleMessage(
//                user.getEmail(), EmailConstants.THANK_YOU_MESSAGE_TITLE, EmailConstants.THANK_YOU_MESSAGE_TEXT));
//    }
// Testing the emailing service for demo ---- the next one is greetings email for christmas and new year.

    @Async
    @Scheduled(cron = "0 0 12 24 12 ?")
    public void christmasScheduleJob() {
        List<User> users = userService.findAllUsers()
                .stream()
                .map(userServiceModel -> this.modelMapper.map(userServiceModel, User.class))
                .collect(Collectors.toList());

        users.forEach(user -> emailService.sendSimpleMessage(
                user.getEmail(), EmailConstants.CHRISTMAS_MESSAGE_TITLE, EmailConstants.CHRISTMAS_MESSAGE_TEXT));
    }
}

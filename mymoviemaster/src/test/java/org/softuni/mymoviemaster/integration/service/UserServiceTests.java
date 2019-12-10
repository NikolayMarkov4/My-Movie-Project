package org.softuni.mymoviemaster.integration.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.softuni.mymoviemaster.domain.entities.User;
import org.softuni.mymoviemaster.domain.models.service.UserServiceModel;
import org.softuni.mymoviemaster.integration.base.ServiceTestBase;
import org.softuni.mymoviemaster.repository.UserRepository;
import org.softuni.mymoviemaster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests extends ServiceTestBase {
    @MockBean
    UserRepository usersRepository;

    @Autowired
    UserService service;
}

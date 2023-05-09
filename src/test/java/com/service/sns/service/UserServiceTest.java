package com.service.sns.service;

import com.service.sns.exception.SnsApplicationException;
import com.service.sns.fixture.UserEntityFixture;
import com.service.sns.model.entity.UserEntity;
import com.service.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void successJoin() {
        String userName = "user";
        String password = "pass";

        UserEntity fixture = UserEntityFixture.get(userName, password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        Assertions.assertDoesNotThrow(() -> userService.join(userName, password));
    }

    @Test
    void alreadyExistUserJoin() {
        String userName = "user";
        String password = "pass";

        UserEntity fixture = UserEntityFixture.get(userName, password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));
        SnsApplicationException e = Assertions.assertThrows(SnsApplicationException.class, () ->  userService.join(userName,password));
    }

    @Test
    void successLogin() {
        String userName = "user";
        String password = "pass";

        UserEntity fixture = UserEntityFixture.get(userName, password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.of(fixture));
        Assertions.assertDoesNotThrow(() -> userService.login(userName, password));
    }

    @Test
    void notFoundUserLogin() {
        String userName = "user";
        String password = "pass";

        UserEntity fixture = UserEntityFixture.get(userName, password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, password));
    }

    @Test
    void invalidPasswordLogin() {
        String userName = "user";
        String password = "pass";
        String wrongPassword = "pass11";

        UserEntity fixture = UserEntityFixture.get(userName, password);

        when(userEntityRepository.findByUserName(userName)).thenReturn(Optional.empty());
        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(userName, wrongPassword));
    }
}
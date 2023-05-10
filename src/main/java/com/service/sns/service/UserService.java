package com.service.sns.service;

import com.service.sns.exception.SnsApplicationException;
import com.service.sns.model.User;
import com.service.sns.model.entity.UserEntity;
import com.service.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;

    public User signUp(String userName, String password) {

        userEntityRepository.findByUserName(userName)
                .ifPresent(x -> {
                    throw new SnsApplicationException();
                });

        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName,password));
        return User.fromEntity(userEntity);
    }

    public String login(String userName, String password) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(SnsApplicationException::new);

        if (!userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException();
        }

        return "";
    }
}

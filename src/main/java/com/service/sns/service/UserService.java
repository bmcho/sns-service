package com.service.sns.service;

import com.service.sns.exception.ErrorCode;
import com.service.sns.exception.SnsApplicationException;
import com.service.sns.model.User;
import com.service.sns.model.entity.UserEntity;
import com.service.sns.repository.UserEntityRepository;
import com.service.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key")
    private String secretKey;
    @Value("${jwt.token.expired-time-ms")
    private long expiredTimeMs;

    @Transactional
    public User signUp(String userName, String password) {

        userEntityRepository.findByUserName(userName).ifPresent(x -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", userName));
        });

        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password)));
        return User.fromEntity(userEntity);
    }

    public String login(String userName, String password) {
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded",userName)));

        if (!userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException();
        }
        return JwtTokenUtils.generateToken(userName, secretKey, expiredTimeMs);
    }
}

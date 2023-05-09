package com.service.sns.fixture;

import com.service.sns.model.entity.UserEntity;

public class UserEntityFixture {

    public static UserEntity get(String userName, String password) {
        UserEntity result = new UserEntity();
        result.setId(1L);
        result.setUserName(userName);
        result.setPassword(password);

        return result;
    }
}

package com.service.sns.fixture;

import com.service.sns.model.entity.PostEntity;
import com.service.sns.model.entity.UserEntity;

public class PostEntityFixture {

    public static PostEntity get(String userName, int postId) {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUserName(userName);

        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId);
        return result;
    }
}

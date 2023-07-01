package com.service.sns.fixture;

import com.service.sns.model.entity.PostEntity;
import com.service.sns.model.entity.UserEntity;

public class PostEntityFixture {

    public static PostEntity get(String userName, int postId, int userId) {
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUserName(userName);

        PostEntity result = new PostEntity();
        result.setUser(user);
        result.setId(postId);
        return result;
    }
}

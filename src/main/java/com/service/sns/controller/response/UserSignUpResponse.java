package com.service.sns.controller.response;

import com.service.sns.model.User;
import com.service.sns.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignUpResponse {

    private int id;
    private String userName;
    private UserRole userRoleType;

    public static UserSignUpResponse fromUser(User user) {
        return new UserSignUpResponse(
                user.getId(),
                user.getUsername(),
                user.getUserRoleType()
        );
    }
}

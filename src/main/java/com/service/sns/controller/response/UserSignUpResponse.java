package com.service.sns.controller.response;

import com.service.sns.model.User;
import com.service.sns.model.enums.UserRole;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserSignUpResponse {

    private Long id;
    private String userName;
    private UserRole userRoleType;

    public static UserSignUpResponse fromUser(User user) {
        return new UserSignUpResponse(
                user.getId(),
                user.getUserName(),
                user.getUserRoleType()
        );
    }
}

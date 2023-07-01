package com.service.sns.controller.response;

import com.service.sns.model.User;
import com.service.sns.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {

    private int id;
    private String userName;
    private UserRole userRoleType;

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getUserRoleType()
        );
    }
}

package com.service.sns.model;

import com.service.sns.model.entity.UserEntity;
import com.service.sns.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class User {
    private Long id;
    private String userName;
    private String password;

    private UserRole userRoleType;

    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static User fromEntity(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUserName(),
                entity.getPassword(),
                entity.getRoleType(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeleteAt()
        );
    }
}

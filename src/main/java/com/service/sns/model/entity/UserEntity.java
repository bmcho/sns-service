package com.service.sns.model.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
@Data
public class UserEntity {

    @Id
    private Long id;
    @Column(name = "user_name")
    private String userName;
    private String password;

}

package com.javalin.spring.dto;

import com.javalin.spring.database.entity.Role;
import jakarta.validation.constraints.Email;
import lombok.Value;

import java.util.UUID;

@Value
public class UserCreateOauthDto {
    @Email
    String email;

    Role role;

    String password = "123";


}

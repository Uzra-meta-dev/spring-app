package com.javalin.spring.mapper;

import com.javalin.spring.database.entity.Company;
import com.javalin.spring.database.entity.Role;
import com.javalin.spring.database.entity.User;
import com.javalin.spring.database.repository.CompanyRepository;
import com.javalin.spring.dto.UserCreateEditDto;
import com.javalin.spring.dto.UserCreateOauthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class UserCreateOathMapper implements Mapper<UserCreateOauthDto, User> {


    @Override
    public User map(UserCreateOauthDto object) {
        return new User(
                null,
                object.getEmail(),
                object.getPassword(),
                null,
                null,
                null,
                null,
                object.getRole(),
                null,
                null
        );
    }
}

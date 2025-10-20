package com.javalin.spring.database.repository;

import com.javalin.spring.database.entity.Role;
import com.javalin.spring.database.entity.User;
import com.javalin.spring.dto.PersonalInfo;
import com.javalin.spring.dto.UserFilter;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter (UserFilter filter);

    List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role);

    void updateCompanyAndRole(List<User> users);

    void updateCompanyAndRoleNamed(List<User> users);
}

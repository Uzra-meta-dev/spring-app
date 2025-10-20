package com.javalin.spring.integration.service;

import com.javalin.spring.database.entity.Role;
import com.javalin.spring.dto.UserCreateEditDto;
import com.javalin.spring.dto.UserReadDto;
import com.javalin.spring.integration.IntegrationTestBase;
import com.javalin.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    private final static Long USER_1 = 1L;
    private final static Integer COMPANY_1 = 1;
    private final UserService userService;

    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll();
        assertThat(result).hasSize(5);
    }

    @Test
    void findById() {
        Optional<UserReadDto> maybeUser = userService.findById(USER_1);
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals("ivan@gmail.com", user.getUsername()));
    }

    @Test
    void create() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                "test",
                LocalDate.now(),
                "Test",
                "Test",
                Role.ADMIN,
                COMPANY_1,
                new MockMultipartFile("test", new byte[0])

        );
        UserReadDto actualResult = userService.create(userDto);
        assertEquals(userDto.getUsername(), actualResult.getUsername());
        assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
        assertEquals(userDto.getFirstname(), actualResult.getFirstname());
        assertEquals(userDto.getLastname(), actualResult.getLastname());
        assertEquals(userDto.getRole(), actualResult.getRole());
        assertEquals(userDto.getCompanyId(), actualResult.getCompany().id());

    }

    @Test
    void update() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                "test",
                LocalDate.now(),
                "Test",
                "Test",
                Role.ADMIN,
                COMPANY_1,
                new MockMultipartFile("test", new byte[0])

        );
        Optional<UserReadDto> actualDto = userService.update(USER_1, userDto);
        assertTrue(actualDto.isPresent());
        actualDto.ifPresent(actualResult -> {
                    assertEquals(userDto.getUsername(), actualResult.getUsername());
                    assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
                    assertEquals(userDto.getFirstname(), actualResult.getFirstname());
                    assertEquals(userDto.getLastname(), actualResult.getLastname());
                    assertEquals(userDto.getRole(), actualResult.getRole());
                    assertEquals(userDto.getCompanyId(), actualResult.getCompany().id());
                }
        );

    }

    @Test
    void delete(){
        assertFalse(userService.delete(-12312L));
        assertTrue(userService.delete(USER_1));
    }


}

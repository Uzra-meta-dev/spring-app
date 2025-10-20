package com.javalin.spring.service;

import com.javalin.spring.database.entity.Company;
import com.javalin.spring.database.repository.CompanyRepository;
import com.javalin.spring.dto.CompanyReadDto;
import com.javalin.spring.listener.entity.EntityEvent;
import lombok.val;
import org.apache.el.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    private static final Integer COMPANY_ID = 1;

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private  UserService userService;
    @Mock
    private  ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void custom(){
        System.out.println(Instant.now());
        System.out.println(LocalDateTime.now());
    }

    @Test
    void checkDuplicateSymbols(){
        String s = "vasldkuh ;alsvkne nviopusehi njk";
        s.chars()
                .mapToObj(value->(char)value)
                .collect(groupingBy(value->value, counting()));
    }
    @Test
    void checkDuplicateSymbols1(){
        String s = "vasldkuh ;alsvkne nviopusehi njk";
        Map<Character, Integer> chars = new HashMap<>();
        s.chars()
                .mapToObj(c-> (char)c)
                .forEach(character ->
                        chars.compute(character, (k, v) ->
                                (v == null)?1 : v +1));


    }

    @Test
    void findById() {
//        doReturn(Optional.of(new Company(COMPANY_ID, null, Collections.EMPTY_MAP)))
//                .when(companyRepository).findById(COMPANY_ID);

        var actualResult = companyService.findById(COMPANY_ID);

        assertTrue(actualResult.isPresent());


        var expectedResult = new CompanyReadDto(COMPANY_ID, null);
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));

        verify(eventPublisher).publishEvent(any(EntityEvent.class));
        verifyNoInteractions(userService);
    }
}
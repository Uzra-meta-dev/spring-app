package com.javalin.spring.database.repository;

import com.javalin.spring.database.entity.Role;
import com.javalin.spring.database.entity.User;
import com.javalin.spring.dto.PersonalInfo2;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends
        JpaRepository<User, Long>,
        FilterUserRepository,
        RevisionRepository<User, Long, Integer>,
        QuerydslPredicateExecutor<User> {

    @Query("select u from User u " +
            "where u.firstname ilike %:firstname% " +
            "and u.lastname ilike %:lastname%")
    List<User> findAllBy(
            @Param("firstname")
            String firstname,
            @Param("lastname")
            String lastname);

    @Query(value = "SELECT * FROM users WHERE username = :username",
            nativeQuery = true
    )
    List<User> findAllByUsername (String username);

    @Modifying(
            clearAutomatically = true
    )
    @Query("UPDATE User u " +
            "SET u.role = :role " +
            "WHERE u.id in (:ids)")
    int updateRole(Role role, Long... ids);

    Optional<User> findTopByOrderByIdDesc();

    @QueryHints(
            @QueryHint(name = "org.hibernate.fetchSize", value = "50")
    )
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<User> findTop3ByBirthDateBefore(LocalDate birthDate, Sort sort);

//    @EntityGraph("User.company")
    @EntityGraph(attributePaths = {
            "company", "company.locales"
    })
    @Query(value = "select u from User u",
    countQuery = "select count (distinct u.firstname) from User u")
    Page<User> findAllBy(Pageable pageable);

//    List<PersonalInfo> findAllByCompanyId(Integer id);

    @Query(nativeQuery = true,
    value = "select firstname," +
            " lastname," +
            " birth_date" +
            " from users where company_id = :id")
    List <PersonalInfo2> findAllByCompanyId(@Param("id") Integer id);

    Optional<User> findByUsername(String username);
}

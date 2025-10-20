package com.javalin.spring.database.repository;

import com.javalin.spring.database.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.expression.spel.ast.OpAnd;

import java.util.List;
import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company, Integer> {

    //Optional, entity, future
//    @Query(name = "Company.findByName")
    @Query ("select c from Company c " +
            "join fetch c.locales cl" +
            " where c.name = :name2")
    Optional<Company> findByName(@Param("name2") String name);

    //Collection, Stream(Batch, close)
    List<Company> findByNameContainingIgnoreCase(String name);


    List<Company> findAllByNameContainingIgnoreCase(String name);
}

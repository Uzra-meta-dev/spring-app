package com.javalin.spring;

import com.javalin.spring.config.DatabaseProperties;
import liquibase.exception.CommandExecutionException;
import liquibase.exception.DatabaseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.io.PrintWriter;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationRunner {


    public static void main(String[] args) throws SQLException, DatabaseException, CommandExecutionException {
        var context = SpringApplication.run(ApplicationRunner.class, args);
        var bean = context.getBean("pool1");
        System.out.println(context.getBeanDefinitionNames());
        System.out.println(context.getBean(DatabaseProperties.class));
        System.out.println("!!!APPLICATION STARTED!!!");





//        var dataSource = context.getBean(DataSource.class);
//        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));
//        Liquibase liquibase = new Liquibase("db/changelog/db.changelog-master.yaml", new ClassLoaderResourceAccessor(), database);
//        liquibase.generateChangeLog(null, null, System.out);




//        String value = "hello";
//        System.out.println(CharSequence.class.isAssignableFrom(value.getClass()));
//        System.out.println();
//
//       try(var context = new AnnotationConfigApplicationContext();) {
//           context.register(ApplicationConfiguration.class);
//           context.getEnvironment().setActiveProfiles("web", "prod");
//           context.refresh();
//           var connectionPool = context.getBean("pool1", ConnectionPool.class);
//           System.out.println(connectionPool);
//           var companyService = context.getBean("comService", CompanyService.class);
//           System.out.println(companyService.findById(1));
//       }
    }


}

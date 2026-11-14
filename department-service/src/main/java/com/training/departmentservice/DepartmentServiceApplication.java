package com.training.departmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class DepartmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DepartmentServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner checkDatabase(DataSource dataSource) {
        return args -> {
            try (Connection connection = dataSource.getConnection()) {
                System.out.println("Oracle Connected Successfully!");
                System.out.println("Database: "
                        + connection.getMetaData().getDatabaseProductName());
                System.out.println("User: "
                        + connection.getMetaData().getUserName());
            }
        };
    }
}

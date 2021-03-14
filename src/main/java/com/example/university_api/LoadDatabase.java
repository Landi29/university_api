package com.example.university_api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Setup of the in memory database by adding data.
 * In a real production application, the database would instead be a relational or non relational database
 * that is saved to disk and thus persisted between server shutdown, restart, update and sessions.
 */
@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private Professor professor1 = new Professor("Hans Jensen", "Database", "Big Data",
            "hjsen@universitet.dk");

    private Professor professor2 = new Professor("Kurt Larsen", "Programming Languages",
            "Functional programming", "klsen@universitet.dk");
    @Bean
    CommandLineRunner initDatabase(ProfessorRepository repository){
        List<String> courses = new ArrayList<>();
        courses.add("Imperative programming");
        courses.add("Functional programming");
        professor2.setCourses(courses);

        return args -> {
            log.info("Preloading " + repository.save(professor1));
            log.info("Preloading " + repository.save(professor2));
        };
    }
}

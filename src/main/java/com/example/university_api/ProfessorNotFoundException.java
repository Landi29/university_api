package com.example.university_api;

public class ProfessorNotFoundException extends RuntimeException{
    ProfessorNotFoundException(Long id){
        super("Could not find professor with id: " + id);
    }
}

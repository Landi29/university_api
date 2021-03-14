package com.example.university_api;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * The ProfessorController class handles HTTP requests to the /professors endpoint
 * and maps requests to methods.
 */
@RestController
public class ProfessorController {

    private final ProfessorRepository repository;

    ProfessorController(ProfessorRepository repository) {
        this.repository = repository;
    }

    // Get all professors.
    @GetMapping("/professors")
    List<Professor> all(){
        return repository.findAll();
    }

    // Get a specific professor.
    @GetMapping("/professors/{id}")
    Professor one(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ProfessorNotFoundException(id));
    }

    // Get the list of courses of a specific professor.
    @GetMapping("/professors/{id}/courses")
    List<String> getCourses(@PathVariable Long id) {
        if(repository.existsById(id)){
            return repository.findById(id).get().getCourses();
        }
        else
            throw new ProfessorNotFoundException(id);
    }

    // Add a new professor
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/professors")
    Professor addProfessor(@RequestBody Professor newProfessor){
        return repository.save(newProfessor);
    }

    // Update an existing professor.
    @PutMapping("/professors/{id}")
    Professor replaceProfessor(@RequestBody Professor newProfessor, @PathVariable Long id) {
        return repository.findById(id)
                .map(professor -> {
                    professor.setName(newProfessor.getName());
                    professor.setDepartment(newProfessor.getDepartment());
                    professor.setSpecialization(newProfessor.getSpecialization());
                    professor.setEmail(newProfessor.getEmail());
                    return repository.save(professor);
                })
                .orElseGet(() -> {
                    newProfessor.setId(id);
                    return repository.save(newProfessor);
                });
    }

    // Delete a professor.
    @DeleteMapping("/professors/{id}")
    void deleteProfessor(@PathVariable Long id){
        repository.deleteById(id);
    }
}

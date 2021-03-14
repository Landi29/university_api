package com.example.university_api;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * The Professor class represents a professor at a university.
 * A professor consists of a name, a department, a specialization and an email as well as a unique id.
 * Furthermore, a professor has a list of courses which includes the courses currently teached by the professor.
 * The professor class is marked as an Entity so that it can be saved in a JPA repository.
 */

@Entity
public class Professor {

    private @Id @GeneratedValue Long id;
    private String name;
    private String department;
    private String specialization;
    private String email;
    @ElementCollection
    private List<String> courses;

    Professor() {}

    Professor(String name, String department, String specialization, String email) {

        this.name = name;
        this.department = department;
        this.specialization = specialization;
        this.email = email;
        this.courses = new ArrayList<>();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDepartment(){
        return this.department;
    }

    public String getSpecialization(){
        return this.department;
    }

    public String getEmail(){
        return this.email;
    }

    public List<String> getCourses(){
        return this.courses;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public void setSpecialization(String specialization){
        this.specialization = specialization;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public void setCourses(List<String> courses){
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Professor))
            return false;
        Professor professor = (Professor) o;
        return Objects.equals(this.id, professor.id) && Objects.equals(this.name, professor.name)
                && Objects.equals(this.department, professor.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.department, this.specialization, this.email);
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' +
                ", department='" + this.department + '\'' +
                ", specialization='" + this.specialization +  '\'' +
                ", email='" + this.email +  '\'' + '}';
    }

}

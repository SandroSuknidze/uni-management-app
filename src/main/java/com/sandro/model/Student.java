package com.sandro.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private final String facNumber;
    private final List<Course> courses = new ArrayList<>();

    public Student(Long id, String firstName, String lastName, String facNumber) {
        super(id, firstName, lastName);
        this.facNumber = facNumber;
    }

    public void addCourse(Course course) {
        if (courses.size() >= 10) {
            throw new IllegalStateException("Cannot enroll in more than 10 courses.");
        }
        courses.add(course);
    }

    public void deleteCourse(Course course) {
        courses.removeIf(c -> c.getName().equals(course.getName()));
    }

    @Override
    public String toString() {
        return "Student{id=" + getId() + ", firstName='" + getFirstName() + "', lastName='" + getLastName() + "', facNumber=" + facNumber + "}";
    }

}

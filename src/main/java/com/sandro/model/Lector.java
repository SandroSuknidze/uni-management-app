package com.sandro.model;

import java.util.ArrayList;
import java.util.List;

public class Lector extends User {

    private final LectorType lectorType;
    private final List<Course> courses = new ArrayList<>();


    public Lector(Long id, String firstName, String lastName, LectorType lectorType) {
        super(id, firstName, lastName);
        this.lectorType = lectorType;
    }

    public void addCourse(Course course) {
        if (courses.size() >= 4) {
            throw new IllegalStateException("A lector cannot have more than 4 courses.");
        }
        courses.add(course);
    }

    public void deleteCourse(Course course) {
        courses.removeIf(c -> c.getName().equals(course.getName()));
    }

    public LectorType getLectorType() {
        return lectorType;
    }

    @Override
    public String toString() {
        return "Lector{" +
                "id=" + getId() +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", lectorType=" + lectorType +
                ", courses=" + courses +
                '}';
    }

}

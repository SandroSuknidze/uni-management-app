package com.sandro.model;

import com.sandro.exception.MaxStudentsReachedException;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private final String name;
    private final List<Student> students = new ArrayList<>();
    private Lector assistant;
    private Lector lector;


    public Course(String name) {
        this.name = name;
    }

    public Course(String name, Lector assistant, Lector lector) {
        this.name = name;
        this.assistant = assistant;
        this.lector = lector;
    }

    public void addStudent(Student student) {
        if (students.size() >= 30) {
            throw new MaxStudentsReachedException("Cannot add more than 30 students.");
        }
        students.add(student);
    }

    public void deleteStudent(Long studentId) {
        students.removeIf(student -> student.getId().equals(studentId));
    }

    public void setAssistant(Lector assistant) {
        this.assistant = assistant;
    }

    public void setLector(Lector lector) {
        this.lector = lector;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", students=" + students +
                ", assistant=" + assistant +
                ", lector=" + lector +
                '}';
    }
}

package com.sandro.service;

import com.sandro.exception.CourseNotFoundException;
import com.sandro.exception.LectorNotFoundException;
import com.sandro.exception.StudentNotFoundException;
import com.sandro.model.Course;
import com.sandro.model.Lector;
import com.sandro.model.LectorType;
import com.sandro.model.Student;

import java.util.ArrayList;
import java.util.List;

public class UniManagementImpl implements UniManagement {

    private final List<Course> courses = new ArrayList<>();
    private final Student[] students = new Student[1000];
    private final List<Lector> lectors = new ArrayList<>();

    private int lastUsedStudentIndex = 0;


    @Override
    public Course createCourse(String courseName) {
        if (courses.size() >= 10) {
            System.err.println("Cannot create more than 10 courses.");
            return null;
        }
        if (courses.stream().anyMatch(course -> course.getName().equals(courseName))) {
            System.err.println("Course with this name already exists.");
            return null;
        }
        Course course = new Course(courseName);
        courses.add(course);
        return course;
    }

    @Override
    public boolean deleteCourse(String courseName) throws CourseNotFoundException {
        Course course = courses.stream()
                .filter(c -> c.getName().equals(courseName))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException("Course not found: " + courseName));

        return courses.remove(course);
    }

    @Override
    public Student createStudent(Long id, String firstName, String lastName, String facNumber) {
        if (lastUsedStudentIndex >= 1000) {
            System.err.println("Cannot add more than 1000 students.");
            return null;
        }
        for (int i = 0; i < lastUsedStudentIndex; i++) {
            if (students[i].getId().equals(id)) {
                System.err.println("Student with this ID already exists.");
                return null;
            }
        }
        Student student = new Student(id, firstName, lastName, facNumber);
        students[lastUsedStudentIndex++] = student;
        return student;
    }

    @Override
    public boolean deleteStudent(Long id) throws StudentNotFoundException {
        for (int i = 0; i < lastUsedStudentIndex; i++) {
            if (students[i].getId().equals(id)) {
                for (int j = i; j < lastUsedStudentIndex - 1; j++) {
                    students[j] = students[j + 1];
                }
                students[--lastUsedStudentIndex] = null;
                return true;
            }
        }
        throw new StudentNotFoundException("Student not found with ID: " + id);
    }

    @Override
    public Lector createAssistant(Long id, String firstName, String lastName) {
        if (lectors.stream().anyMatch(lector -> lector.getId().equals(id))) {
            System.err.println("Assistant with this ID already exists.");
            return null;
        }
        Lector assistant = new Lector(id, firstName, lastName, LectorType.ASSISTANT);
        lectors.add(assistant);
        return assistant;
    }

    @Override
    public Lector createProfessor(Long id, String firstName, String lastName, String lectorType) {
        if (lectors.stream().anyMatch(lector -> lector.getId().equals(id))) {
            System.err.println("Assistant with this ID already exists.");
            return null;
        }
        try {
            LectorType type = LectorType.valueOf(lectorType.toUpperCase());
            Lector assistant = new Lector(id, firstName, lastName, type);
            lectors.add(assistant);
            return assistant;
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid lector type: " + lectorType);
        }

    }

    @Override
    public boolean deleteAssistant(Long id) {
        return lectors.removeIf(lector -> lector.getId().equals(id) && lector.getLectorType() == LectorType.ASSISTANT);
    }

    @Override
    public boolean assignAssistantToCourse(Lector assistant, Course course) {
        if (assistant.getLectorType() == LectorType.ASSISTANT) {
            course.setAssistant(assistant);
            return true;
        }
        return false;
    }

    @Override
    public boolean assignProfessorToCourse(Lector professor, Course course) {
        course.setLector(professor);
        return true;
    }

    @Override
    public boolean assignStudentToCourse(Student student, Course course) {
        try {
            course.addStudent(student);
            student.addCourse(course);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean assignStudentsToCourse(Student[] students, Course course) {
        for (Student student : students) {
            if (!assignStudentToCourse(student, course)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean removeStudentFromCourse(Student student, Course course) {
        try {
            course.deleteStudent(student.getId());
            student.deleteCourse(course);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    @Override
    public Student getStudentById(Long studentId) throws StudentNotFoundException {
        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }

        throw new StudentNotFoundException("Student with id " + studentId + " not found.");
    }

    @Override
    public Course getCourseByName(String courseName) throws CourseNotFoundException{
        return courses.stream()
                .filter(course -> course.getName().equalsIgnoreCase(courseName))
                .findFirst()
                .orElseThrow(() -> new CourseNotFoundException("Course not found with name: " + courseName));
    }

    @Override
    public Lector getLectorById(Long lectorId) throws LectorNotFoundException {
        return lectors.stream()
                .filter(lector -> lector.getId().equals(lectorId))
                .findFirst()
                .orElseThrow(() -> new LectorNotFoundException("Lector not found with id: " + lectorId));
    }





    @Override
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < lastUsedStudentIndex; i++) {
            studentList.add(students[i]);
        }
        return studentList;
    }

    @Override
    public List<Course> getAllCourses() {
        return courses;
    }

    @Override
    public List<Lector> getAllLectors() {
        return lectors;
    }

}

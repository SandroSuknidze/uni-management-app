package com.sandro;

import com.sandro.model.Course;
import com.sandro.model.Lector;
import com.sandro.model.LectorType;
import com.sandro.model.Student;
import com.sandro.service.UniManagement;
import com.sandro.service.UniManagementImpl;
import com.sandro.util.ArgumentParser;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UniManagement uniManagement = new UniManagementImpl();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to University Management System. Type 'exit' to quit.");

        while (true) {
            System.out.print("> ");
            command = scanner.nextLine();
            if (command.equalsIgnoreCase("exit")) {
                break;
            }
            handleCommand(command, uniManagement);
        }
    }

    private static void handleCommand(String command, UniManagement uniManagement) {
        String[] tokens = command.split("\\s+");
        try {
            switch (tokens[0]) {
                case ArgumentParser.CREATE_STUDENT:
                    uniManagement.createStudent(Long.parseLong(tokens[1]), tokens[2], tokens[3], String.valueOf(Long.parseLong(tokens[4])));
                    System.out.println("Student created.");
                    break;
                case ArgumentParser.CREATE_COURSE:
                    uniManagement.createCourse(tokens[1]);
                    System.out.println("Course created.");
                    break;
                case ArgumentParser.ASSIGN_ASSISTANT_TO_COURSE:
                    Lector assistant = new Lector(Long.parseLong(tokens[1]), tokens[2], tokens[3], LectorType.ASSISTANT);
                    Course courseForAssistant = uniManagement.createCourse(tokens[4]);
                    uniManagement.assignAssistantToCourse(assistant, courseForAssistant);
                    System.out.println("Assistant assigned.");
                    break;
                case ArgumentParser.DELETE_STUDENT:
                    uniManagement.deleteStudent(Long.parseLong(tokens[1]));
                    System.out.println("Student deleted.");
                    break;
                case ArgumentParser.ASSIGN_STUDENT_TO_COURSE: {
                    Student student = uniManagement.getStudentById(Long.valueOf(tokens[1]));
                    Course course = uniManagement.getCourseByName(tokens[2]);
                    uniManagement.assignStudentToCourse(student, course);
                    System.out.println("Student assigned to course.");
                }
                break;
                case ArgumentParser.ASSIGN_PROFESSOR_TO_COURSE: {
                    Lector lector = uniManagement.getLectorById(Long.parseLong(tokens[1]));
                    Course course = uniManagement.getCourseByName(tokens[2]);
                    uniManagement.assignProfessorToCourse(lector, course);
                    System.out.println("Lector assigned to course.");
                }
                break;

                case ArgumentParser.REMOVE_STUDENT_FROM_COURSE:
                    Student studentToRemove = uniManagement.getStudentById(Long.parseLong(tokens[1]));
                    Course courseToRemove = uniManagement.getCourseByName(tokens[2]);
                    uniManagement.removeStudentFromCourse(studentToRemove, courseToRemove);
                    System.out.println("Student removed from course.");
                    break;

                case ArgumentParser.DELETE_COURSE:
                    uniManagement.deleteCourse(tokens[1]);
                    System.out.println("Course removed.");
                    break;

                case ArgumentParser.CREATE_ASSISTANT:
                    uniManagement.createAssistant(Long.valueOf(tokens[1]), tokens[2], tokens[3]);
                    System.out.println("Assistant created.");
                    break;

                case ArgumentParser.DELETE_ASSISTANT:
                    uniManagement.deleteAssistant(Long.valueOf(tokens[1]));
                    System.out.println("Assistant deleted.");
                    break;

                case ArgumentParser.ASSIGN_STUDENTS_TO_COURSE:
                    Course targetCourse = uniManagement.createCourse(tokens[1]);
                    Student[] studentsArray = new Student[tokens.length - 2];
                    for (int i = 2; i < tokens.length; i++) {
                        studentsArray[i - 2] = uniManagement.createStudent(Long.parseLong(tokens[i]), "Temp", "Student", "123");
                    }
                    if (uniManagement.assignStudentsToCourse(studentsArray, targetCourse)) {
                        System.out.println("All students assigned successfully.");
                    } else {
                        System.out.println("Failed to assign all students to the course.");
                    }
                    break;
                case ArgumentParser.CREATE_PROFESSOR:
                    uniManagement.createProfessor(Long.valueOf(tokens[1]), tokens[2], tokens[3], tokens[4]);
                    System.out.println("Lector created.");
                    break;


                case ArgumentParser.GET_ALL_COURSES:
                    List<Course> courses = uniManagement.getAllCourses();
                    if (courses.isEmpty()) {
                        System.out.println("No Courses found.");
                    } else {
                        courses.forEach(System.out::println);
                    }
                    break;

                case ArgumentParser.GET_ALL_STUDENTS:
                    List<Student> students = uniManagement.getAllStudents();
                    if (students.isEmpty()) {
                        System.out.println("No students found.");
                    } else {
                        students.forEach(System.out::println);
                    }
                    break;

                case ArgumentParser.GET_ALL_LECTORS:
                    List<Lector> lectors = uniManagement.getAllLectors();
                    if (lectors.isEmpty()) {
                        System.out.println("No Lectors found.");
                    } else {
                        lectors.forEach(System.out::println);
                    }
                    break;
                default:
                    System.out.println("Unknown command.");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
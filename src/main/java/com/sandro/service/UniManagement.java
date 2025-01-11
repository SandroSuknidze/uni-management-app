package com.sandro.service;

import com.sandro.exception.CourseNotFoundException;
import com.sandro.exception.StudentNotFoundException;
import com.sandro.model.Course;
import com.sandro.model.Lector;
import com.sandro.model.LectorType;
import com.sandro.model.Student;

import java.util.List;

public interface UniManagement {

    /**
     * Create a new course with name courseName and return it
     *
     * @return new instance of course with the passed name if it's created or
     * null in another case. A course will be created only if it already does not exist
     * another course with the same courseName
     */
    Course createCourse(String courseName);

    /**
     * Delete a course with name courseName
     *
     * @return <code>true</code> only in case the course with passed name was removed
     */
    boolean deleteCourse(String courseName) throws CourseNotFoundException;

    /**
     * Create and return new instance of Student with the passed arguments and initial state
     * of the student
     *
     * @return new instance of a student identified with the passed ID, if already does not
     * exist,
     * and the other arguments as initial state if it's created or
     * null in another case
     */
    Student createStudent(Long id, String firstName, String lastName, String facNumber);

    /**
     * Delete a student with the passed ID
     *
     * @return <code>true</code> only in cae the student was removed
     */
    boolean deleteStudent(Long id) throws StudentNotFoundException;

    /**
     * Create a new assistant in the university without passed arguments as initial state
     *
     * @return newly created professor assistant identified without passed ID, if already does
     * not exist with that ID
     */
    Lector createAssistant(Long id, String firstName, String lastName);
    Lector createProfessor(Long id, String firstName, String lastName, String lectorType);

    /**
     * Delete a professor assistant with the passed ID if such exists
     *
     * @return <code>true</code> ONLY in case the assistant was removed
     */
    boolean deleteAssistant(Long id);

    /**
     * Assign an assistant to a course
     *
     * @return <code>true</code> ONLY n case the assistant was successfully assigned to
     * the course
     */
    boolean assignAssistantToCourse(Lector assistant, Course course);

    /**
     * Assign a professor to a course
     *
     * @return <code>true</code> ONLY n case the professor was successfully assigned to
     * the course
     */
    boolean assignProfessorToCourse(Lector professor, Course course);

    /**
     * Add a student to a course
     *
     * @return <code.true</code> ONLY inca se the student is successfully added to the
     * course
     */
    boolean assignStudentToCourse(Student student, Course course);

    /**
     * Add all students to a course
     *
     * @return <code>true</code> ONLY inc ase all students are added to a course
     */
    boolean assignStudentsToCourse(Student[] students, Course course);

    /**
     * Remove a student from a course
     *
     * @return <code>true</code> only in case the student was successfully removed from a
     * course
     */
    boolean removeStudentFromCourse(Student student, Course course);

    Student getStudentById(Long studentId);
    Course getCourseByName(String courseName);
    Lector getLectorById(Long lectorId);



    List<Student> getAllStudents();
    List<Course> getAllCourses();
    List<Lector> getAllLectors();


}

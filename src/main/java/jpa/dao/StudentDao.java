package jpa.dao;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

import java.util.List;

public interface StudentDao {
    public List<Student> getAllStudents();
    public Student getStudentByEmail(String email);

    boolean validateStudent(String sEmail, String sPass);

    void registerStudentToCourse(String email, Integer cId);

    public List<Course> getStudentCourse(String email);

}

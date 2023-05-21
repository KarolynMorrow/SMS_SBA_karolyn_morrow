package jpa.service;

import jpa.entitymodels.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    //test to make sure the users email input is in the proper format
    @Test
    void verify_student_email_input_is_proper_format() {
        StudentService service = new StudentService();
        Student student = new Student();
        String studentEmail = student.getsEmail();
        Student student1 = service.getStudentByEmail("cjaulme9@bing.com");
        String regexPattern = "^(.+)@(\\S+)$";
        Boolean properEmail = studentEmail.contains(regexPattern);

        Assertions.assertEquals(student1, properEmail);

    }
}
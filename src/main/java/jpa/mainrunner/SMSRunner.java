package jpa.mainrunner;

import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import jpa.service.CourseService;
import jpa.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class SMSRunner {
    private Scanner input;

    private CourseService courseService;
    private StudentService studentService;
    private Student currentStudent;

    public SMSRunner() {
        input = new Scanner(System.in);
        courseService = new CourseService();
        studentService = new StudentService();
    }

    public static void main(String[] args) {
        SMSRunner sms = new SMSRunner();
        sms.run();

    }
    //Two options: Menuoption1: Verify student is in db based on email and password, then print registered classes
    //then prompt student to either register for another class or logout
    //if Studentoption1:register is chosen print all available courses verifying student is not already registered in chosen class
    //then reprint registered classes and sign out
    //if Studentoption2 logout with an appropriate message
    //Menuoption2: end program with appropriate message

    private void run() {
        System.out.println("Are you a(n)\n1. Student\n2. Quit");
        int num = input.nextInt();
        System.out.println("Option " + num + " chosen");

        if (num == 1) {
            if (student()) {
                studentRegistration();
            }
        } else {
            quit();
        }
    }

    //Obtain username and pass and verify student is in DB
    private boolean student() {
        boolean isStudent = false;
        System.out.println("Enter your email: ");
        String email = input.next();
        System.out.println("Enter your password:");
        String password = input.next();
        isStudent = studentService.validateStudent(email, password); //changes isStudent to true if validateStudent() passes

        Student student = null;
        if (isStudent) {
            student = studentService.getStudentByEmail(email);
            currentStudent = student;
        }
        if (student != null && isStudent) {
            List<Course> courses = studentService.getStudentCourse(email);
            System.out.println("My classes:");
            for (Course course : courses) {
                System.out.println(course);
            }
        } else {
            System.out.println("User validation failed.");
            quit();
        }
        return isStudent;

    }

    //prompt student to either register for another class or logout

    //then reprint registered classes and sign out
    private void studentRegistration() {
        System.out.println("\n1: Register for classes\n2: Logout\nPlease choose an option:");
        int num = input.nextInt();
        if (num == 1) {
            List<Course> allCourses = courseService.getAllCourses();
//            List<Course> studentCourses = studentService.getStudentCourse(currentStudent.getsEmail());
//            allCourses.removeAll(studentCourses);
            System.out.printf("%-5s%-35s%-10s\n", "ID", "Course", "Instructor");
            for (Course course : allCourses) {
                System.out.printf("%-5s%-35s%-10s\n", course.getcId(), course.getcName(), course.getcInstructorName());
                ;
            }
            System.out.println("End of courses.\n");
            System.out.println("Please enter a course number: ");
            int courseNum = input.nextInt();
            Course newCourse = courseService.getAllCourses().get(courseNum - 1);
            System.out.printf("Course chosen:\n%-5s%-35s%-10s\n", newCourse.getcId(), newCourse.getcName(), newCourse.getcInstructorName());
            studentService.registerStudentToCourse(currentStudent.getsEmail(), newCourse.getcId());

            Student temp = studentService.getStudentByEmail(currentStudent.getsEmail());
            StudentService tempHolder = new StudentService();
            List<Course> tempCourses = tempHolder.getStudentCourse(temp.getsEmail());

            System.out.println("Updated Registration: ");
            for (Course course : tempCourses) {
                System.out.printf("%-5s%-35s%-10s\n", course.getcId(), course.getcName(), course.getcInstructorName());
            }

        } else {
            logout();
        }
    }

    private void logout() {
        System.out.println("You have logged out");

    }

    private void quit() {
        System.out.println("Program has quit");
    }
}

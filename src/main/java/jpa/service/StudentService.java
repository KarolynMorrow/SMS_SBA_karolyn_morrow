package jpa.service;

import jpa.dao.StudentDao;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentDao {
    @Override
    public List<Student> getAllStudents() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            List<Student> students = session.createNativeQuery("Select * from student", Student.class).getResultList();
            return students;
        } catch (NoResultException e) {
            return null;
        } finally {
            tx.commit();
            factory.close();
            session.close();
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Student student = session.find(Student.class, email);
            return student;
        } catch (NoResultException | NullPointerException exception) {
            System.out.println("No student found with provided credentials");
        } finally {
            tx.commit();
            factory.close();
            session.close();
        }
        return null;
    }

    @Override
    public boolean validateStudent(String sEmail, String sPass) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Student findStudent = session.find(Student.class, sEmail);

            if (findStudent.getsPass().equals(sPass)) {
                return true;
            } else {
                return false;
            }
        } catch (NoResultException | NullPointerException e) {
            System.out.println("Credentials are invalid");
        } finally {
            tx.commit();
            factory.close();
            session.close();

        }
        return false;
    }

    //if Studentoption1:register is chosen print all available courses verifying student
    // is not already registered in chosen class
    @Override
    public void registerStudentToCourse(String sEmail, Integer cId) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Student student = session.get(Student.class, sEmail); //get student based on (email) id
            List<Course> studentCourseList = getStudentCourse(sEmail); //get courses of registered student
            if (studentCourseList.contains(cId)) {
                System.out.println("Student is already registered for course");
            } else {
                Course chosenCourse = session.get(Course.class, cId); //get course
                studentCourseList.add(chosenCourse);//and add it to registered course list
                student.setsCourses(studentCourseList);
                session.saveOrUpdate(studentCourseList);
            }

        } catch (NullPointerException | NoResultException exception) {
            System.out.println("Unable to register to this course");

        } finally {
            tx.commit();
            factory.close();
            session.close();
        }
    }

    @Override
    public List<Course> getStudentCourse(String email) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            return session.get(Student.class, email).getsCourses();
        } catch (NullPointerException e) {
            System.out.println("Student is not registered for any classes.");
        } finally {
            tx.commit();
            factory.close();
            session.close();
        }

        return null;
    }
}

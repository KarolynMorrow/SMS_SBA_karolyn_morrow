package jpa.service;

import jpa.dao.CourseDao;
import jpa.entitymodels.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;
import java.util.List;

public class CourseService implements CourseDao {
    @Override
    public List<Course> getAllCourses() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            List<Course> courseList = session.createNativeQuery("Select * from course", Course.class).getResultList();
            return courseList;

        }catch(NoResultException e){
            return null;
        } finally {
            tx.commit();
            factory.close();
            session.close();
        }

    }
}

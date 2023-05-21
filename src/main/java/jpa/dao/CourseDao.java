package jpa.dao;

import jpa.entitymodels.Course;

import java.util.List;

public interface CourseDao {
    List<Course> getAllCourses();
}

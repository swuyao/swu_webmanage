package com.pjb.springbootjjwt.mapper;

import com.pjb.springbootjjwt.entity.Course;
import com.pjb.springbootjjwt.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author yc
 * @date 2018-07-08 20:44
 */
public interface CourseMapper {
    Course findByCoursename(@Param("coursename") String coursename);
    String findByCourseid(@Param("courseid") int courseid);
    List<Course> findAllCourse();
    boolean addCourse(Course course);
    boolean deleteCourse(@Param("number") String number);
    boolean updateCourseInfo(Course course);
    boolean updateCoursePosttime(Course course);
    boolean adminUpdateCoursePosttime(Date posttime);
    List<Student> findStudentByCourseid(@Param("courseid") int courseid);
    Integer findCourseidBycoursename(@Param("coursename") String coursename);
}

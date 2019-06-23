package com.pjb.springbootjjwt.mapper;

import com.pjb.springbootjjwt.entity.Course;
import com.pjb.springbootjjwt.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import com.pjb.springbootjjwt.entity.Student;

import java.util.List;

/**
 * @author yc
 * @date 2018-07-08 20:44
 */
public interface TeacherMapper {
    List<Teacher> findByTeaname(@Param("teaname") String teaname);
    Teacher findByTeanumber(@Param("teachernumber") String teachernumber);
    List<Teacher> findAllTeacher();
    boolean addTeacher(Teacher teacher);
    boolean deleteTeacher(@Param("teachernumber") String teachernumber);
    boolean updateTeacherCourse(Teacher teacher);//还需更改在course表中的值***********************************
    boolean updateTeacher(Teacher teacher);
    List<Student> findMyStu(@Param("teaname") String teaname);
    int selectByStunumberReturnCount(String teachernumber);
    List<Course> findCourseByTeachernumber(@Param("teachernumber") String teachernumber);
}

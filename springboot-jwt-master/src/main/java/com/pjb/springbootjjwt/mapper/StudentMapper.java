package com.pjb.springbootjjwt.mapper;

import com.pjb.springbootjjwt.entity.Course;
import com.pjb.springbootjjwt.entity.Sc;
import com.pjb.springbootjjwt.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yc
 * @date 2018-07-08 20:44
 */
public interface StudentMapper {
    Student findByStuname(@Param("stuname") String stuname);
    Student findByStudentnumber(Student student);
    boolean addStudent(Student student);
    boolean deleteStudent(String studentnumber);
    boolean updateStudentTeacher(@Param("studentnumber") String studentnumber,@Param("zdteacher") Integer zdteacher);
    List<Student> findAllStudent();
    boolean updateStudentInfo(Student student);
    int selectByStunumberReturnCount(String studentnumber);
    List<Course> findStudentNotPost(String studentnumber);

}

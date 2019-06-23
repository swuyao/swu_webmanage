package com.pjb.springbootjjwt.mapper;

import com.pjb.springbootjjwt.entity.Sc;
import com.pjb.springbootjjwt.entity.ScAndCourse;
import com.pjb.springbootjjwt.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yc
 * @date 2018-07-08 20:44
 */
public interface ScMapper {
    List<Sc> findByStu(@Param("studentnumber") String studentnumber);
    List<Sc> findByStuCou(@Param("studentnumber") String studentnumber,@Param("courseid") Integer courseid);
    List<ScAndCourse> findALLByStudentnumber(@Param("studentnumber") String studentnumber);

    boolean addSc(Sc sc);//选课
    boolean score(Sc sc);//评分
    Sc showScore(Student student);
    boolean homework(Sc sc);
    boolean setState(Sc sc);
    boolean wirteRemark(Sc sc);
    Integer findNotPost(@Param("courseid") Integer courseid);
    Integer showstudentcount(@Param("courseid") Integer courseid);

    List<String> findNotPostStudentNumber(@Param("courseid") Integer courseid);
}


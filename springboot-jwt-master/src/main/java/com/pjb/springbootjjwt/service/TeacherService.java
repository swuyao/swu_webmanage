package com.pjb.springbootjjwt.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.entity.Course;
import com.pjb.springbootjjwt.entity.Student;
import com.pjb.springbootjjwt.entity.Teacher;
import com.pjb.springbootjjwt.mapper.TeacherMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yc
 * @date 2018-07-08 20:52
 */
@Service("TeacherService")
public class TeacherService {
    @Autowired
    TeacherMapper teacherMapper;

    public List<Teacher> findByTeaname( String teaname){return teacherMapper.findByTeaname(teaname);}
    public List<Course> findCourseByTeachernumber(String teachernumber){return teacherMapper.findCourseByTeachernumber(teachernumber);}//找到老师的对应课程
    public Teacher findByTeanumber( String teachernumber){return teacherMapper.findByTeanumber(teachernumber);}
    public List<Student> findMyStu(@Param("teaname") String teaname){return teacherMapper.findMyStu(teaname);}

    public Map<String, Object> findAllTeacher(int pageNum, int pageSize,String teachernumber,String deletenum, String teaname,
                                              String updateTeacherCoursenumber,Integer newteacourse) throws Exception{
        if(("1".equals(deletenum)&&!"".equals(teachernumber)))//删除
        {

            boolean deleteresult=teacherMapper.deleteTeacher(teachernumber);
            Map<String,Object> map=new HashMap<>();
            map.put("data",deleteresult);
            return map;
        }

        else if(!"".equals(teachernumber)&&"".equals(deletenum)&&"".equals(updateTeacherCoursenumber))//搜索
        {
            Teacher userlist=teacherMapper.findByTeanumber(teachernumber);
            Map<String,Object> map=new HashMap<>();
            map.put("data",userlist);
            return map;
        }
        else if(!"".equals(teaname))
        {
            List<Teacher> userlist=teacherMapper.findByTeaname(teaname);
            Map<String,Object> map=new HashMap<>();
            map.put("data",userlist);
            return map;
        }
        else if(!"".equals(updateTeacherCoursenumber)&&!"".equals(newteacourse)&&!"".equals(teachernumber))//更新课程****此处不明bug
        {
            Teacher teacher=new Teacher();
            teacher.setTeachernumber(teachernumber);
            teacher.setTeacourse(newteacourse);
            boolean updateresult=teacherMapper.updateTeacherCourse(teacher);
            Map<String,Object> map=new HashMap<>();
            map.put("data",updateresult);
            return map;

        }

       else
        {
            Page page=PageHelper.startPage(pageNum, pageSize,true);
            List<Teacher> userlist=teacherMapper.findAllTeacher();
            System.out.println(userlist);
            Map<String,Object> map=new HashMap<>();
            map.put("code" ,0);
            map.put("count", page.getTotal());
            map.put("nowPage", pageNum);
            map.put("data", userlist);
            return map;

        }
        }
    public boolean addTeacher(Teacher teacher)
    {return teacherMapper.addTeacher(teacher);}

    public boolean deleteTeacher(String teachernumber)
    {return teacherMapper.deleteTeacher(teachernumber);}

    public boolean updateTeacherCourse(Teacher teacher)
    {return teacherMapper.updateTeacherCourse(teacher);}

}

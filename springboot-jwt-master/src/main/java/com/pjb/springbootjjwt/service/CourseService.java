package com.pjb.springbootjjwt.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.entity.Course;
import com.pjb.springbootjjwt.entity.Student;
import com.pjb.springbootjjwt.entity.User;
import com.pjb.springbootjjwt.mapper.CourseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yc
 * @date 2018-07-08 20:52
 */
@Service("CourseService")
public class CourseService {
    @Autowired
    CourseMapper courseMapper;

    public Course findByCoursename( String coursename){return courseMapper.findByCoursename(coursename);}
    public String findByCourseid(int courseid){return courseMapper.findByCourseid(courseid);}
    public  boolean addCourse(Course course){return courseMapper.addCourse(course);}

    public List<Student> findStudentByCourseid( int courseid){return courseMapper.findStudentByCourseid(courseid);};
    public boolean updateCoursePosttime(Course course){return courseMapper.updateCoursePosttime(course);};
    public boolean adminUpdateCoursePosttime(Date posttime){return courseMapper.adminUpdateCoursePosttime(posttime);};


    public Map<String, Object> findAllCourse(int pageNum, int pageSize,String coursename,String number,
                                             String deletenumber,String addnumber,String updatenumber,String newcoursename,String newskteacher,
                                             String newnumber) throws Exception{
        if(!"".equals(coursename)&&"".equals(deletenumber))
        {

            Course userlist=courseMapper.findByCoursename(coursename);
            Map<String,Object> map=new HashMap<>();
            map.put("code" ,0);
            map.put("data",userlist);
            return map;

        }
        else if(!"".equals(coursename)&&!"".equals(deletenumber))
        {
            boolean coursedeleteresult=courseMapper.deleteCourse(number);
            Map<String,Object> map=new HashMap<>();
            map.put("code" ,0);
            map.put("data",coursedeleteresult);
            return map;
        }
        else if(!"".equals(newcoursename)&&!"".equals(updatenumber))
        {
            Course course=new Course();
            course.setCoursename(newcoursename);
            course.setNumber(number);
            boolean courseupdateresult=courseMapper.updateCourseInfo(course);
            Map<String,Object> map=new HashMap<>();
            map.put("code" ,0);
            map.put("data",courseupdateresult);
            return map;
        }
        else if(!"".equals(newcoursename)&&!"".equals(addnumber)&&!"".equals(newskteacher)&&!"".equals(newnumber))
        {
            Course course=new Course();
            course.setCoursename(newcoursename);
            course.setNumber(newnumber);
            course.setSkteacher(newskteacher);
            boolean courseaddresult=courseMapper.addCourse(course);
            Map<String,Object> map=new HashMap<>();
            map.put("code" ,0);
            map.put("data",courseaddresult);
            return map;
        }

        else
        {
            Page page=PageHelper.startPage(pageNum, pageSize,true);
            List<Course> userlist=courseMapper.findAllCourse();
            System.out.println(userlist);
            Map<String,Object> map=new HashMap<>();
            map.put("code" ,0);
            System.out.print(page.getTotal());
            map.put("count", page.getTotal());
            map.put("nowPage", pageNum);
            map.put("data", userlist);
            return map;

        }

    }
    public boolean deleteCourse( String number){return courseMapper.deleteCourse(number);}


    public boolean updateCourseInfo(Course course){return courseMapper.updateCourseInfo(course);}
    public Integer findCourseidBycoursename( String coursename)
    {
        return courseMapper.findCourseidBycoursename(coursename);
    }



}

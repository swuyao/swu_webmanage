package com.pjb.springbootjjwt.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.annotation.UserLoginToken;
import com.pjb.springbootjjwt.entity.Course;
import com.pjb.springbootjjwt.entity.Student;
import com.pjb.springbootjjwt.entity.Teacher;
import com.pjb.springbootjjwt.service.CourseService;
import com.pjb.springbootjjwt.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yc
 * @date 2018-07-08 20:45
 */
@RestController
@RequestMapping("course")

public class CourseApi {
    @Autowired
    CourseService courseService;
    @Autowired
    TeacherService teacherService;


    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

        //转换日期
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }




    @UserLoginToken
    @PostMapping("/addCourse")//添加课程
    public Map<String, Object> addCourse(@RequestBody Course course) {
        System.out.print("添加课程");

        Map<String,Object> map=new HashMap<>();
        boolean resultadd = courseService.addCourse(course);
        map.put("code",0);
        map.put("添加课程结果",resultadd);
        Teacher teacher=new Teacher();
        teacher.setTeachernumber(course.getSkteacher());
        teacher.setTeacourse(course.getCourseid());
        boolean resultupdate=teacherService.updateTeacherCourse(teacher);
        map.put("更改教师课程结果",resultupdate);
        return map;
    }



    @UserLoginToken
    @GetMapping("/findByCoursename")//根据课程名查找课程
    public Object findByCoursename(String coursename) {
        JSONObject results = new JSONObject();
        Course courseone= courseService.findByCoursename(coursename);

        results.put("courseone", courseone);
        return results;
    }

    @UserLoginToken
    @GetMapping("/findByCourseid")//根据课程名查找课程
    public Object findByCourseid(int courseid) {

        String coursename= courseService.findByCourseid(courseid);


        return coursename;
    }

    @UserLoginToken
    @GetMapping("/updateCoursePosttime")//更新课程提交时间
    public boolean updateCoursePosttime(Integer courseid,Date posttime) {

        Course course=new Course();
        course.setCourseid(courseid);
        course.setPosttime(posttime);
        boolean result= courseService.updateCoursePosttime(course);


        return result;
    }




    @UserLoginToken
    @PostMapping("/updateCourseInfo")//更新课程
    public boolean updateCourseInfo(@RequestBody Course course) {
        boolean results;
        results =courseService.updateCourseInfo(course);

        return results;
    }

    @UserLoginToken
    @GetMapping("/findAllCourse")//查找所有课程
    public Map<String, Object> findAllCourse(int pageNum, int pageSize,String coursename,String number,
                                             String deletenumber,String addnumber,String updatenumber,String newcoursename,String newskteacher,
                                             String newnumber) throws Exception {


        return courseService.findAllCourse(pageNum, pageSize,coursename,number,deletenumber,addnumber,updatenumber,newcoursename,newskteacher,newnumber);
    }


    @UserLoginToken
    @GetMapping("/deleteCourse")//根据课程编号删除课程
    public boolean deleteCourse(String number) {
        boolean result;
        result = courseService.deleteCourse(number);
        return result;
    }




    @UserLoginToken
    @GetMapping("/findStudentByCourseid")//根据课程id查找课程的所有学生
    public Map<String, Object> findStudentByCourseid(int courseid,int pageNum,int pageSize) throws Exception {
        Page page= PageHelper.startPage(pageNum, pageSize,true);
        List<Student> students=courseService.findStudentByCourseid(courseid);

        Map<String,Object> map=new HashMap<>();
        map.put("code" ,0);
        map.put("count", page.getTotal());
        map.put("nowPage", pageNum);
        map.put("data",students);
        return map;
    }








}
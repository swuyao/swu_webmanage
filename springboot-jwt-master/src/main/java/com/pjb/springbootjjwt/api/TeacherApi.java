package com.pjb.springbootjjwt.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.annotation.UserLoginToken;
import com.pjb.springbootjjwt.entity.Course;
import com.pjb.springbootjjwt.entity.Student;
import com.pjb.springbootjjwt.entity.Teacher;
import com.pjb.springbootjjwt.service.TeacherService;
import com.pjb.springbootjjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yc
 * @date 2018-07-08 20:45
 */
@RestController
@RequestMapping("teacher")

public class TeacherApi {
    @Autowired
    TeacherService teacherService;
    @Autowired
    UserService userService;





    @UserLoginToken
    @PostMapping("/addTeacher")//添加老师
    public void addTeacher(@RequestBody Teacher teacher) {
        System.out.print("添加老师");
        boolean result;
        result = teacherService.addTeacher(teacher);
        System.out.println(result);
    }

    @UserLoginToken
    @GetMapping("/findByTeanumber")//根据卡号查找老师
    public Object findByTeanumber(String teachernumber) {
        JSONObject results = new JSONObject();
        Teacher teacherone = teacherService.findByTeanumber(teachernumber);

        results.put("teacherone", teacherone);
        return results;
    }

    @UserLoginToken
    @GetMapping("/findteacherByToken")//根据token查找老师
    public Object findteacherByToken( @RequestHeader(name="token")String token){

        JSONObject results = new JSONObject();
        Teacher teacher=new Teacher();
        teacher.setTeachernumber(userService.selectStunumberBytoken(token));
        System.out.print(teacher.getTeachernumber());
        Teacher teacher1=teacherService.findByTeanumber(teacher.getTeachernumber());
        results.put("data",teacher1);
        return results;
    }
    @UserLoginToken
    @GetMapping("/findByTeaname")//根据用户名查找老师
    public Object findByTeaname(String teaname) {
        JSONObject results = new JSONObject();
        List<Teacher> teacherone = teacherService.findByTeaname(teaname);

        results.put("teacherone", teacherone);
        return results;
    }





    @UserLoginToken
    @PostMapping("/updateTeacherCourse")//更新老师课程
    public boolean updateTeacherCourse(@RequestBody Teacher teacher) {
        boolean results;
        results =teacherService.updateTeacherCourse(teacher);

        return results;
    }

    @UserLoginToken
    @GetMapping("/findAllTeacher")//查找所有老师
    public Map<String, Object> findAllUser(int pageNum, int pageSize,String teachernumber,String deletenum, String teaname,
                                           String updateTeacherCoursenumber,Integer newteacourse) throws Exception {


        return teacherService.findAllTeacher(pageNum, pageSize,teachernumber,deletenum,teaname,updateTeacherCoursenumber,newteacourse);
    }


    @UserLoginToken
    @PostMapping("/deleteTeacher")//根据教师卡号删除用户
    public boolean deleteTeacher(String teachernumber) {
        boolean result;
        result = teacherService.deleteTeacher(teachernumber );
        return result;
    }

    @UserLoginToken
    @GetMapping("/findMyStu")//查找老师的所有学生
    public List<Student> findMyStu(@RequestHeader(name="token")String token) throws Exception {
        String teaname=userService.selectNamerBytoken(token);
        System.out.println(teaname);
        return teacherService.findMyStu(teaname);
    }

    @UserLoginToken
    @GetMapping("/findCourseByTeachernumber")//根据token查找老师的课程
    public Map<String, Object> findCourseByTeachernumber(@RequestHeader(name="token")String token,int pageNum,int pageSize) throws Exception {
        Page page= PageHelper.startPage(pageNum, pageSize,true);
        String teachernumber=userService.selectStunumberBytoken(token);
        System.out.println(teachernumber);
        List<Course> courses=teacherService.findCourseByTeachernumber(teachernumber);
        Map<String,Object> map=new HashMap<>();
        map.put("code" ,0);
        map.put("count", page.getTotal());
        map.put("nowPage", pageNum);
        map.put("data",courses);
        return map;
    }













}
package com.pjb.springbootjjwt.api;

/**
 * Created by yaocong on 2018/12/16.
 */
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.annotation.UserLoginToken;
import com.pjb.springbootjjwt.entity.Course;
import com.pjb.springbootjjwt.entity.Sc;
import com.pjb.springbootjjwt.entity.Student;
import com.pjb.springbootjjwt.service.CourseService;
import com.pjb.springbootjjwt.service.TokenService;
import com.pjb.springbootjjwt.service.StudentService;
import com.pjb.springbootjjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("student")
public class StudentApi {
    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @UserLoginToken
    @PostMapping("/addStudent")
    public void addStudent(@RequestBody Student student){
        System.out.print("添加学生");
        boolean result;
        result=studentService.addStudent(student);
        System.out.println(result);
    }

    @UserLoginToken
    @GetMapping("/findByStudentnumber")
    public Object findByStudentnumber( Student students){
        System.out.print("根据校园卡号查找学生");
        JSONObject results = new JSONObject();
        Student student=studentService.findByStudentnumber(students);
        results.put("student",student);
        return results;
    }

    @UserLoginToken
    @GetMapping("/findstudentByToken")
    public Object findstudentByToken( @RequestHeader(name="token")String token){

        JSONObject results = new JSONObject();
        Student students=new Student();
        students.setStudentnumber(userService.selectStunumberBytoken(token));
        System.out.print(students.getStudentnumber());
        Student student=studentService.findByStudentnumber(students);
        results.put("student",student);
        return results;
    }

    @UserLoginToken
    @GetMapping("/findByStuname")
    public Object findByStuname( String stuname){
        System.out.print("查找姓名学生");
        JSONObject results = new JSONObject();
        Student students=studentService.findByStuname(stuname);
        results.put("student",students);
        return results;}


    @UserLoginToken
    @GetMapping("/updateStudentTeacher")//更新学生的指导老师
    public boolean updateStudentTeacher(String studentnumber, Integer zdteacher) {
        boolean results;
        results =studentService.updateStudentTeacher(studentnumber,zdteacher);

        return results;
    }

    @UserLoginToken
    @PostMapping("/updateStudentInfo")//更新学生信息
    public boolean updateStudentInfo(@RequestBody Student student) {
        boolean results;
        results = studentService.updateStudentInfo(student);

        return results;
    }

    @UserLoginToken
    @GetMapping("/findAllStudent")//查找所有学生信息
    public Map<String, Object> findAllUser(int pageNum, int pageSize,String studentnumber,String deletenum) throws Exception {


        return studentService.findAllStudent(pageNum, pageSize,studentnumber,deletenum);
    }

    @UserLoginToken
    @GetMapping("/findStudentNotPost")//查找学生还没有提交作业的信息
    public Map<String, Object> findStudentNotPost(@RequestHeader(name="token")String token) throws Exception {
        Map<String,Object> map=new HashMap<>();
        List<Course> courses=studentService.findStudentNotPost(userService.selectStunumberBytoken(token));
        map.put("code",0);
        map.put("date",courses);
        return map;

    }

    @UserLoginToken
    @GetMapping("/deleteStudent")//根据学生卡号删除用户
    public boolean deleteStudent(String studentnumber) {
        boolean result;
        result = studentService.deleteStudent(studentnumber);
        return result;
    }




}

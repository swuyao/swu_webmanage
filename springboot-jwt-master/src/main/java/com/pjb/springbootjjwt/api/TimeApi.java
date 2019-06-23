package com.pjb.springbootjjwt.api;

import com.alibaba.fastjson.JSONObject;
import com.pjb.springbootjjwt.annotation.UserLoginToken;
import com.pjb.springbootjjwt.entity.Time;
import com.pjb.springbootjjwt.service.CourseService;
import com.pjb.springbootjjwt.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yc
 * @date 2018-07-08 20:45
 */
@RestController
@RequestMapping("time")

public class TimeApi {
    @Autowired
    TimeService timeService;
    @Autowired
    CourseService courseService;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

        //转换日期
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }
    //提交作业的时间
    @UserLoginToken
    @PostMapping("/showposttime")
    public Date showposttime(){
        return timeService.showPostTime();
    }

    @UserLoginToken
    @PostMapping("/showtheistime")
    public Date showtheistime(){
        return timeService.showTheisTime();
    }


    @UserLoginToken
    @PostMapping("/showaddresstime")
    public Date showaddresstime(){
        return timeService.showAddressTime();
    }

    @UserLoginToken
    @GetMapping("/addposttime")
    public boolean addposttime(Date posttime){

        return timeService.addPostTime(posttime);


    }



    @UserLoginToken
    @GetMapping("/addtheistime")
    public boolean addtheistime(Date theistime){
        return timeService.addTheisTime(theistime);
    }

    @UserLoginToken
    @GetMapping("/addaddresstime")
    public boolean addaddresstime(Date addresstime){
        return timeService.addAddressTime(addresstime);
    }

    @UserLoginToken
    @GetMapping("/adminUpdateCoursePosttime")//管理员统一设置作业提交时间
    public boolean adminUpdateCoursePosttime(Date posttime){
            timeService.addAddressTime(posttime);
        return courseService.adminUpdateCoursePosttime(posttime);


    }




}
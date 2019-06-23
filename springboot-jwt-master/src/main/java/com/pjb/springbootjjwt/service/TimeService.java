package com.pjb.springbootjjwt.service;

import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.entity.Time;
import com.pjb.springbootjjwt.mapper.TeacherMapper;
import com.pjb.springbootjjwt.mapper.TimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yc
 * @date 2018-07-08 20:52
 */
@Service("TimeService")
public class TimeService {
    @Autowired
    TimeMapper timeMapper;

    public Date showPostTime(){ return timeMapper.showPostTime();};
    public Date showTheisTime(){ return timeMapper.showTheisTime();};
    public Date showAddressTime(){ return timeMapper.showAddressTime();};

    public boolean addPostTime(Date posttime){return timeMapper.addPostTime(posttime);}
    public boolean addTheisTime(Date theistime){return timeMapper.addTheisTime(theistime);}
    public boolean addAddressTime(Date addresstime){return timeMapper.addAddressTime(addresstime);}

}

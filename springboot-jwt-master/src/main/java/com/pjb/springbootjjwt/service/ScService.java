package com.pjb.springbootjjwt.service;

import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.entity.Sc;
import com.pjb.springbootjjwt.entity.ScAndCourse;
import com.pjb.springbootjjwt.entity.Student;
import com.pjb.springbootjjwt.mapper.ScMapper;
import com.pjb.springbootjjwt.mapper.StudentMapper;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yc
 * @date 2018-07-08 20:52
 */
@Service("ScService")
public class ScService {
    @Autowired
    ScMapper scMapper;



    public  List<Sc> findByStu(int pageNum, int pageSize,String studentnumber) throws Exception{

        PageHelper.startPage(pageNum, pageSize);
        List<Sc> titlelist=scMapper.findByStu(studentnumber);
        return titlelist;

    }

    public  List<Sc> findByStuCou(String studentnumber,Integer courseid) throws Exception{

        PageHelper.startPage(1, 1);
        List<Sc> titlelist=scMapper.findByStuCou(studentnumber,courseid);
        return titlelist;

    }

    public List<ScAndCourse> findALLByStudentnumber(String studentnumber){
        return scMapper.findALLByStudentnumber(studentnumber);}

    public boolean addSc(Sc sc){return scMapper.addSc(sc);}

    public Integer findNotPost( Integer courseid){return scMapper.findNotPost(courseid);};
    public List<String> findNotPostStudentNumber( Integer courseid){return scMapper.findNotPostStudentNumber(courseid);};
    public  Integer showstudentcount( Integer courseid){return scMapper.showstudentcount(courseid);}//每一门课程的总人数
    public boolean score(Sc sc){return scMapper.score(sc);}

    public Sc showScore(Student student){return scMapper.showScore(student);}

    public boolean homework(Sc sc){
        return scMapper.homework(sc);

    }
    public boolean setState(Sc sc){
        return scMapper.setState(sc);

    }
    public boolean writeRemark(Sc sc){
        return scMapper.wirteRemark(sc);

    }

}

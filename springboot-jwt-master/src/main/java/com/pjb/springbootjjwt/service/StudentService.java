package com.pjb.springbootjjwt.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.entity.Course;
import com.pjb.springbootjjwt.entity.Sc;
import com.pjb.springbootjjwt.entity.Student;
import com.pjb.springbootjjwt.mapper.StudentMapper;
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
@Service("StudentService")
public class StudentService {
    @Autowired
    StudentMapper studentMapper;

    public Student findByStudentnumber(Student student) {
        return studentMapper.findByStudentnumber(student);
    }
    public Student findByStuname(String stuname){
        return studentMapper.findByStuname(stuname);
    }
    public boolean addStudent(Student student){return studentMapper.addStudent(student);};
    public boolean deleteStudent(String studentnumber){return studentMapper.deleteStudent(studentnumber);};
    public boolean updateStudentTeacher(String studentnumber, Integer zdteacher){return studentMapper.updateStudentTeacher(studentnumber,zdteacher);};

    public boolean updateStudentInfo(Student student){return studentMapper.updateStudentInfo(student);};
    public List<Course> findStudentNotPost(String studentnumber){return studentMapper.findStudentNotPost(studentnumber);};



    public Map<String, Object> findAllStudent(int pageNum, int pageSize,String studentnumber,String deletenum) throws Exception{

          if("1".equals(deletenum)&&!"".equals(studentnumber))
        {
            boolean deleteresult=studentMapper.deleteStudent(studentnumber);
            Map<String,Object> map=new HashMap<>();
            map.put("删除结果",deleteresult);
            return map;
        }

        else if(!"".equals(studentnumber))
        {
            Student students=new Student();
            students.setStudentnumber(studentnumber);
            Student userlist=studentMapper.findByStudentnumber(students);
            Map<String,Object> map=new HashMap<>();
            map.put("data",userlist);
            return map;


        }

        else
        {
            Page page=PageHelper.startPage(pageNum, pageSize,true);
            List<Student> studentlist=studentMapper.findAllStudent();
            System.out.println(studentlist);
            Map<String,Object> map=new HashMap<>();
            map.put("code" ,0);
            map.put("count", page.getTotal());
            map.put("nowPage", pageNum);
            map.put("data", studentlist);
            return map;


        }}

}

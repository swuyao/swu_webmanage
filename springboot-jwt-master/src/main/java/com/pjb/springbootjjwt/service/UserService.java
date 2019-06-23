package com.pjb.springbootjjwt.service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.pjb.springbootjjwt.common.MyException;
import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.entity.User;
import com.pjb.springbootjjwt.mapper.UserMapper;
import com.pjb.springbootjjwt.entity.Student;
import com.pjb.springbootjjwt.mapper.StudentMapper;
import com.pjb.springbootjjwt.entity.Teacher;
import com.pjb.springbootjjwt.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.io.InputStream;
import java.util.*;



/**
 * @author yc
 * @date 2018-07-08 20:52
 */
@Service("UserService")
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    TeacherMapper teacherMapper;

    public String selectStunumberBytoken( String token){ //根据token查找校园卡号
        System.out.println(token);
        return userMapper.selectStunumberBytoken(token);
    };

    public String selectRoleBytoken( String token){ //根据token查找ROLE
        System.out.println(token);
        return userMapper.selectRoleBytoken(token);
    };

    public String selectNamerBytoken( String token){ //根据token姓名
        System.out.println(token);
        return userMapper.selectNameBytoken(token);
    };

    public boolean updatetoken(String stunumber,String token){return userMapper.updatetoken(stunumber,token);}

    public User findByUsername(String username){
        return userMapper.findByUsername(username);
    }

    public User findByStunumber(String stunumber) {
        return userMapper.findByStunumber(stunumber);
    }


    public User findByUsernames(User user){
        return userMapper.findByStunumber(user.getStunumber());
    }

    public boolean adduser(User user){
        String role=user.getRole();
        System.out.print(role);
        String name=user.getUsername();
        String numbers=user.getStunumber();

        Student student1=new Student();
        student1.setStudentnumber(numbers);
        student1.setStuname(name);


        Teacher teacher1=new Teacher();
        teacher1.setTeaname(name);
        teacher1.setTeachernumber(numbers);
        teacher1.setTeacourse(0);
         boolean result=userMapper.adduser(user);
         if("student".equals(role)){
             studentMapper.addStudent(student1);
             System.out.print("成功添加一名学生");
         }
        if("skteacher".equals(role)||"zdteacher".equals(role)){
            teacherMapper.addTeacher(teacher1);
            System.out.print("成功添加一名老师");
        }
        return result;
    };

    public boolean Resetpassword( String stunumber, String newpassword)
    {
        return userMapper.Resetpassword(stunumber,newpassword);
    }

    public Map<String, Object> findByRole(int pageNum, int pageSize,String role) throws Exception{

        Page page=PageHelper.startPage(pageNum, pageSize,true);
        List<User> userlist=userMapper.findByRole(role);
        System.out.println(userlist);
        Map<String,Object> map=new HashMap<>();
        map.put("code" ,0);
        map.put("count", page.getTotal());
        map.put("nowPage", pageNum);
        map.put("data", userlist);
        return map;



    }

  // public List<User> findByStunumberAndIDcar(User user) {return userMapper.findByStunumberAndIDcar(user.getIdcards(),user.getStunumber());}
    public List<User> findByStunumberAndIDcars(String stunumber,String idcards) {return userMapper.findByStunumberAndIDcar(stunumber,idcards);}

    public boolean	Updateuserrole( String stunumber, String role)
    {
        return userMapper.Updateuserrole(stunumber,role);
    }

    public Map<String, Object> findAllUser(int pageNum, int pageSize,String stunumber,String role,String deletenumber,String updaterolenumber,
                                           String addnumber,String updatenumber,String newrole,String newstunumber,String newusername,String newidcard) throws Exception{
        if(!"".equals(stunumber)&&"".equals(deletenumber)&&"".equals(updaterolenumber)&&"".equals(updatenumber))
        {
            User userlist=userMapper.findByStunumber(stunumber);


            List<User> oneuserinfo=new ArrayList<User>();
            oneuserinfo.add(userlist);



            Map<String,Object> map=new HashMap<>();
            map.put("code",0);
            map.put("count",1);
            map.put("data",oneuserinfo);
            return map;

        }
        else if(!"".equals(role))
        {
            return findByRole(pageNum,pageSize,role);
        }
        else if(!"".equals(stunumber)&&!"".equals(deletenumber))//删除
        {
            boolean deleteresult=userMapper.deleteUser(stunumber);
            Map<String,Object> map=new HashMap<>();
            map.put("data",deleteresult);
            return map;
        }
        else if(!"".equals(stunumber)&&!"".equals(updaterolenumber))
        {
            boolean updateroleresult=userMapper.Updateuserrole(stunumber,newrole);
            Map<String,Object> map=new HashMap<>();
            map.put("data",updateroleresult);
            return map;
        }
        else if(!"".equals(addnumber)&&!"".equals(newstunumber)&&!"".equals(newusername)&&!"".equals(newidcard))//增加
        {
            User user=new User();
            user.setStunumber(newstunumber);
            user.setIdcards(newidcard);
            user.setUsername(newusername);
            boolean addresult=userMapper.adduser(user);
            Map<String,Object> map=new HashMap<>();
            map.put("data",addresult);
            return map;
        }
        else if(!"".equals(updatenumber)&&!"".equals(stunumber)&&!"".equals(newusername)&&!"".equals(newidcard))//更新用户
        {
            User user=new User();
            user.setStunumber(stunumber);
            user.setIdcards(newidcard);
            user.setUsername(newusername);
            user.setRole(newrole);
            boolean updateresult=userMapper.updateuser(user);
            Map<String,Object> map=new HashMap<>();
            map.put("data",updateresult);
            return map;
        }
        else
        {
            Page page=PageHelper.startPage(pageNum, pageSize,true);
            List<User> userlist=userMapper.findAllUser();
            System.out.println(userlist);
            Map<String,Object> map=new HashMap<>();
            map.put("code" ,0);
            map.put("count", page.getTotal());
            map.put("nowPage", pageNum);
            map.put("data", userlist);
            return map;


        }

    }

    public boolean deleteUser(String stunumber){return userMapper.deleteUser(stunumber);}



    //导入excel

     public Object batchImport(String fileName, MultipartFile file) throws Exception{
             JSONObject jsonObject = new JSONObject();
             boolean notNull = false;
             List<User> userList = new ArrayList<User>();
             if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
                 jsonObject.put("message", "上传文件格式不正确");
                 return jsonObject;
                 //throw new MyException("上传文件格式不正确");//判断文件格式

             }
             boolean isExcel2003 = true;
             if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
                 isExcel2003 = false;
             }
             InputStream is = file.getInputStream();
             Workbook wb = null;
             if (isExcel2003) {
                 wb = new HSSFWorkbook(is);
             } else {
                 wb = new XSSFWorkbook(is);
             }
             Sheet sheet = wb.getSheetAt(0);
             if(sheet!=null){
                 notNull = true;
             }
             User user;
             for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                 Row row = sheet.getRow(r);
                 if (row == null){
                     continue;
                 }

                 user = new User();

                 row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                 String stunumber = row.getCell(0).getStringCellValue();
                 if(stunumber==null || stunumber.isEmpty()){
                     jsonObject.put("message", "导入失败(第"+(r+1)+"行,学号未填写)");
                     return jsonObject;
                     //throw new MyException("导入失败(第"+(r+1)+"行,学号未填写)");
                 }

                 row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                 String username = row.getCell(1).getStringCellValue();
                 if(username==null || username.isEmpty()){
                     jsonObject.put("message", "导入失败(第"+(r+1)+"行,姓名未填写)");
                     return jsonObject;
                     //throw new MyException("导入失败(第"+(r+1)+"行,姓名未填写)");
                 }

                 row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                 String idcards = row.getCell(2).getStringCellValue();
                 if(idcards==null){
                     jsonObject.put("message", "导入失败(第"+(r+1)+"行,身份证号码未填写)");
                     return jsonObject;
                     //throw new MyException("导入失败(第"+(r+1)+"行,身份证号码未填写)");
                 }

                 row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                 String role = row.getCell(3).getStringCellValue();
                 if(role==null){
                     jsonObject.put("message", "导入失败(第"+(r+1)+"行,角色未填写)");
                     return jsonObject;
                    // throw new MyException("导入失败(第"+(r+1)+"行,身份证号码未填写)");
                 }

                /** row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                 String teacourseS = row.getCell(4).getStringCellValue();
                 Integer teacourse=Integer.parseInt(teacourseS);
                 if(teacourseS==null){
                     throw new MyException("导入失败(第"+(r+1)+"行,教师课程未填写)");
                 }**/


                 user.setStunumber(stunumber);
                 user.setUsername(username);
                 user.setIdcards(idcards);
                 user.setRole(role);

                 userList.add(user);

                 if("student".equals(role)){
                     Student student=new Student();
                     student.setStudentnumber(stunumber);
                     student.setStuname(username);
                     int cnt = studentMapper.selectByStunumberReturnCount(stunumber);
                     if (cnt == 0) {
                         studentMapper.addStudent(student);
                         System.out.println(" 插入学生"+userList);
                     } else {
                         studentMapper.updateStudentInfo(student);
                         System.out.println(" 更新 学生"+userList);
                     }


                 }
                 if("zdteacher".equals(role)||"skteacher".equals(role))
                 {
                     Teacher teacher=new Teacher();
                     teacher.setTeachernumber(stunumber);
                     teacher.setTeaname(username);
                    // teacher.setTeacourse(teacourse);
                     int cnt = teacherMapper.selectByStunumberReturnCount(stunumber);
                     if (cnt == 0) {
                         teacherMapper.addTeacher(teacher);
                         System.out.println(" 插入老师 "+userList);
                     } else {
                         teacherMapper.updateTeacher(teacher);
                         System.out.println(" 更新老师 "+userList);
                     }


                 }
             }
             for (User userResord : userList) {
                 String stunumber = userResord.getStunumber();
                 String role=userResord.getRole();
                 int cnt = userMapper.selectByStunumberReturnCount(stunumber);
                 if (cnt == 0) {
                     userMapper.adduser(userResord);
                     System.out.println(" 插入 "+userResord);
                 } else {
                     userMapper.Updateuserrole(stunumber,role);
                     System.out.println(" 更新 "+userResord);
                 }
             }
            jsonObject.put("message","上传成功！！");

             return jsonObject;
         };






}

package com.pjb.springbootjjwt.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.annotation.UserLoginToken;
import com.pjb.springbootjjwt.entity.*;
import com.pjb.springbootjjwt.service.DownloadService;
import com.pjb.springbootjjwt.service.ScService;
import com.pjb.springbootjjwt.service.UserService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yc
 * @date 2018-07-08 20:45
 */
@RestController
@RequestMapping("sc")

public class ScApi {

    @Autowired
    ScService scService;
    @Autowired
    UserService userService;
    @Autowired
    DownloadService downloadService;




    @UserLoginToken
    @GetMapping("/addSc")//选课
    public boolean addSc(@RequestHeader(name=" token") String token,Integer courseid) {
       // System.out.print("选课");
        boolean result;
        Sc sc=new Sc();
        String studentnumber=userService.selectStunumberBytoken(token);
        sc.setStudentnumber(studentnumber);
        sc.setCourseid(courseid);
        result = scService.addSc(sc);
       return result;
    }




    @UserLoginToken
    @GetMapping("/findByStu")//查找该学生的所有选课
    public List<Sc> findAllCourse(int pageNum, int pageSize,String studentnumber ) throws Exception {


        return scService.findByStu(pageNum, pageSize,studentnumber);
    }

    @UserLoginToken
    @GetMapping("/findByStuCou")//核对学生和课程
    public List<Sc> findByStuCou(int pageNum, int pageSize,String studentnumber ,Integer courseid) throws Exception {


        return scService.findByStuCou(studentnumber,courseid);
    }





    @UserLoginToken
    @PostMapping("/score")//评分
    public  Map<String, Object> score(@RequestBody Sc sc) {
        boolean results;

        results =scService.score(sc);
        Map<String,Object> map=new HashMap<>();
        map.put("code" ,0);
        //map.put("courseid",courseid);
        map.put("message",results);
        return map;
    }

    @UserLoginToken
    @PostMapping("/showScore")//展示
    public Sc showScore(Student student) {
        System.out.print(student.getStudentnumber());
        return scService.showScore(student);
    }



    @UserLoginToken
    @PostMapping("/writeRemark")//写评语
    public boolean writeRemark(@RequestBody Sc sc) {


        return scService.writeRemark(sc);
    }

    @UserLoginToken
    @GetMapping("/findNotPost")//查找没有交作业的人数和学号
    public Map<String, Object> findNotPost(Integer courseid) {
        Integer Notpostnumber=scService.findNotPost(courseid);
        List<String> notpoststudentnumber=scService.findNotPostStudentNumber(courseid);
        Map<String,Object> map=new HashMap<>();
        map.put("code" ,0);

        map.put("Notpostnumber", Notpostnumber);

        map.put("data",notpoststudentnumber);
        return map;
    }

    @UserLoginToken
    @GetMapping("/findALLByStudentnumber")//根据token查找学生的课程
    public Map<String, Object> findALLByStudentnumber(@RequestHeader(name="token")String token, int pageNum, int pageSize) throws Exception {
        Page page= PageHelper.startPage(pageNum, pageSize,true);
        String studentnumber=userService.selectStunumberBytoken(token);
        System.out.println(studentnumber);
        List<ScAndCourse> sc=scService.findALLByStudentnumber(studentnumber);
        //String coursename=courseService.findByCourseid(courseid);
        Map<String,Object> map=new HashMap<>();
        map.put("code" ,0);
        map.put("count", page.getTotal());
        map.put("nowPage", pageNum);
        //map.put("courseid",courseid);
        map.put("data",sc);
        return map;
    }


    @GetMapping(value = "/ScExcelDownloads")
    public void downloadAllClassmate(HttpServletResponse response,Integer courseid) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("成绩表");
        List<DownloadAndSc> homeworkList = downloadService.showHomeworkList(courseid);
        String fileName = "score" + ".xls";//设置要导出的文件的名字
        // 新增数据行，并且设置单元格数据
        int rowNum = 1; String[] headers = { "学号", "姓名", "状态", "作业","评语","评分"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text); }
        //在表中存放查询到的数据放入对应的列
        for (DownloadAndSc downloadAndSc : homeworkList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(downloadAndSc.getFilemaker());
            row1.createCell(1).setCellValue(downloadAndSc.getFilemakername());
            row1.createCell(2).setCellValue(downloadAndSc.getState());
            row1.createCell(3).setCellValue(downloadAndSc.getFilename());
            row1.createCell(4).setCellValue(downloadAndSc.getRemark());
            row1.createCell(5).setCellValue(downloadAndSc.getScore());
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer(); workbook.write(response.getOutputStream());
    }









}
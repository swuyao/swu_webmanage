package com.pjb.springbootjjwt.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.annotation.UserLoginToken;
import com.pjb.springbootjjwt.entity.*;
import com.pjb.springbootjjwt.service.DownloadService;
import com.pjb.springbootjjwt.service.NoticeService;
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
@RequestMapping("notice")

public class NoticeApi {
    @Autowired
    NoticeService noticeService;
    @Autowired
    UserService userService;
    @Autowired
    DownloadService downloadService;



    @UserLoginToken
    @PostMapping("/addNotice")
    public boolean addNotice(@RequestBody Notice notice){
        return noticeService.addNotice(notice);
    }

    @UserLoginToken
    @GetMapping("/findAllTitle")//推送消息
    public Map<String, Object> findAllTitle(int pageNum, int pageSize,String role) throws Exception {

        return noticeService.findAllTitle(pageNum, pageSize);
    }

    @UserLoginToken
    @GetMapping("/findByTitle")//根据标题查找通知内容
    public Object findByTitle(String title) {
        JSONObject results = new JSONObject();
        Notice notice= noticeService.findByTitle(title);

        results.put("通知内容", notice);
        return results;
    }

    @UserLoginToken
    @PostMapping("/findNewestNotice")//查找最新通知
    public Object findNewestNotice() {
        JSONObject results = new JSONObject();
        Notice notice= noticeService.findNewestNotice();

        results.put("最新通知", notice);
        return results;
    }

    @UserLoginToken
    @GetMapping("/deleteNotice")//根据标题删除通知
    public boolean deleteTeacher(String title) {
        boolean result;
        result = noticeService.deleteNotice(title);
        return result;
    }

    @UserLoginToken
    @GetMapping("/showNoticeByRole")//根据角色推送消息
    public Map<String, Object> showNoticeByRole(int pageNum, int pageSize,@RequestHeader(name="token" )String token) throws Exception {
        String role=userService.selectRoleBytoken(token);

        Page page= PageHelper.startPage(pageNum, pageSize,true);
        List<Notice> titlelist=noticeService.showNoticeByRole(role);
        Map<String,Object> map=new HashMap<>();
        map.put("code" ,0);
        System.out.print(page.getTotal());
        map.put("count", page.getTotal());
        map.put("nowPage", pageNum);
        map.put("data", titlelist);
        return map;

    }

}
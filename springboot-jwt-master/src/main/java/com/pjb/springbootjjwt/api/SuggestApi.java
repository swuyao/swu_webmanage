package com.pjb.springbootjjwt.api;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pjb.springbootjjwt.annotation.UserLoginToken;
import com.pjb.springbootjjwt.entity.Notice;
import com.pjb.springbootjjwt.entity.suggest;
import com.pjb.springbootjjwt.service.ScService;
import com.pjb.springbootjjwt.service.SuggestService;
import com.pjb.springbootjjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yc
 * @date 2018-07-08 20:45
 */
@RestController
@RequestMapping("suggest")

public class SuggestApi {
    @Autowired
    SuggestService suggestService;
    @Autowired
    UserService userService;



    @UserLoginToken
    @PostMapping("/sentSuggest")
    public boolean sentSuggest(@RequestBody String suggest,@RequestHeader(name="token") String token){
        suggest suggest1=new suggest();
        suggest1.setName(userService.selectNamerBytoken(token));
        suggest1.setSuggest(suggest);
        suggest1.setRole(userService.selectRoleBytoken(token));
        return suggestService.sentSuggest(suggest1);
    }

    @UserLoginToken
    @PostMapping("/looklook")
    public  Map<String, Object> looklook(int pageNum, int pageSize) throws Exception{

        Page page= PageHelper.startPage(pageNum, pageSize,true);
        List<suggest> suggestlist=suggestService.looklook();

        Map<String,Object> map=new HashMap<>();
        map.put("code" ,0);

        map.put("count", page.getTotal());
        map.put("nowPage", pageNum);
        map.put("data", suggestlist);
        return map;

    }

}
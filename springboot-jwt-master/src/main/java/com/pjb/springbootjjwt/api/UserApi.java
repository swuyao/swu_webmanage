package com.pjb.springbootjjwt.api;

import com.alibaba.fastjson.JSONObject;
import com.pjb.springbootjjwt.annotation.UserLoginToken;
import com.pjb.springbootjjwt.entity.User;
import com.pjb.springbootjjwt.service.TokenService;
import com.pjb.springbootjjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Map;

/**
 * @author yc
 * @date 2018-07-08 20:45
 */
@RestController
@RequestMapping("user")//登录
public class UserApi {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    //登录
    @PostMapping("/login")
    public Object login(@RequestBody User user) {
        JSONObject jsonObject = new JSONObject();
        User userForBase = userService.findByUsernames(user);

        if (userForBase == null) {
            jsonObject.put("message", "登录失败,用户不存在");
            return jsonObject;
        } else {
            if (!userForBase.getPassword().equals(user.getPassword())) {
                jsonObject.put("message", "登录失败,密码错误");
                return jsonObject;
            } else {
                // String role=user.getRole();
                String token = tokenService.getToken(userForBase);
                userService.updatetoken(user.getStunumber(),token);
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                //System.out.println(user.getRole());
                return jsonObject;
            }
        }
    }


    @UserLoginToken
    @PostMapping("/a")//测试token
    public Object insertoneuser() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "測試成功");
        return jsonObject;
    }

    @UserLoginToken
    @PostMapping("/adduser")//注册用户
    public boolean adduser(@RequestBody User user) {
        System.out.print("添加用户");
        boolean result;
        result = userService.adduser(user);
        System.out.println(result);
        return result;
    }

    @UserLoginToken
    @GetMapping("/findByUsername")//根据用户名查找
    public Object findByUsername(String username) {
        JSONObject results = new JSONObject();
        User useraa = userService.findByUsername(username);
        System.out.println(username);
        results.put("useraa", useraa);
        return results;
    }

    @UserLoginToken
    @GetMapping("/findByStunumber")//根据校园卡号查找
    public Object findByStunumber(@RequestHeader(name="token")String token) {
        JSONObject results = new JSONObject();
        String stunumber=userService.selectStunumberBytoken(token);
       //System.out.println(token);
        User useraa = userService.findByStunumber(stunumber);
        System.out.println(stunumber);
        results.put("useraa", useraa);
        return results;
    }

    @UserLoginToken
    @GetMapping("/findByRole")//查找所有同类用户
    public Map<String, Object> findByRole(int pageNum, int pageSize,String role) throws Exception {


        return userService.findByRole(pageNum, pageSize,role);
    }




    @UserLoginToken
    @PostMapping("/Resetpassword")//重设密码
    public Object Resetpassword(@RequestBody User users) {
        JSONObject jsonObject = new JSONObject();
        JSONObject results = new JSONObject();
        String idcards = users.getIdcards();
        String stunumber = users.getStunumber();
        List<User> userForBases = userService.findByStunumberAndIDcars(stunumber, idcards);
        results.put("test", userForBases);


        if ("".equals(userForBases)) {//如果根据用户卡号和身份证号找不到用户
            // System.out.println(users.getStunumber());
            //System.out.println(users.getIDcard());
            //System.out.println(users.getPassword());
            jsonObject.put("message", "更改失败,用户不存在,请检查您的校园卡账号和身份证号码");

            return jsonObject;
        } else {

            if (!userForBases.get(0).getPassword().equals(users.getPassword())) {//核对用户信息
                jsonObject.put("message", "更改失败,原密码错误");
                return jsonObject;
            } else {
                // String role=user.getRole();
                boolean chageuser = userService.Resetpassword(users.getStunumber(), users.getNewpassword());//核对原密码
                jsonObject.put("userinfo", chageuser);
                //System.out.println(user.getRole());
                return jsonObject;
            }
        }
    }


    @UserLoginToken
    @PostMapping("/findByStunumberAndIDcar")//根据身份证号码和校园卡号查询用户信息
    public Object findByStunumberAndIDcar() {
        JSONObject results = new JSONObject();
        List<User> useraa = userService.findByStunumberAndIDcars("222016321072029", "50023555555555");
        String aa = useraa.get(0).getPassword();
        results.put("useraa", useraa);
        results.put("aa", aa);
        return results;
    }

    @UserLoginToken
    @GetMapping("/Updateuserrole")//更新用户权限
    public boolean Updateuserrole(String stunumber, String role) {
        boolean results;
        results = userService.Updateuserrole(stunumber, role);

        return results;
    }

    @UserLoginToken
    @GetMapping("/findAllUser")//查找所有用户
    public Map<String, Object> findAllUser(int pageNum, int pageSize,String stunumber,String role,String deletenumber,String updaterolenumber,
    String addnumber,String updatenumber,String newrole,String newstunumber,String newusername,String newidcard) throws Exception {


        return userService.findAllUser(pageNum, pageSize,stunumber,role,deletenumber,updaterolenumber,addnumber,
                updatenumber,newrole,newstunumber,newusername,newidcard);
    }


    @UserLoginToken
    @PostMapping("/deleteUser")//更具校园卡号删除用户
    public boolean deleteUser(String stunumber) {
        boolean result;
        result = userService.deleteUser(stunumber);
        return result;
    }

    @UserLoginToken
    @PostMapping("/import")//excel导入用户信息
    public Object addUser(@RequestParam("file") MultipartFile file) {
        boolean a = false;
        Object jsonObject = new JSONObject();
        String fileName = file.getOriginalFilename();
        try {
            jsonObject = userService.batchImport(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }












}
package com.example.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.AppException;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.util.AJAXRespnose;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author sourire
 * @since 2019-08-16
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/doUserAdd")
    public @ResponseBody Map<String, Object> doUserAdd(User user){
        //默认密码为123456
        user.setPassword(MD5Util.encode("123456"));
        //默认生效
        user.setStatus("1");
        //加入时间为当前时间
        user.setCreateTime(new Date());
        boolean isSave = userService.save(user);
        Map<String, Object> map = new HashMap<>();
        map.put("isSave",isSave);
        if(isSave){
            map.put("msg","保存成功！");
        }else{
            map.put("msg","保存失败，请联系管理员");
        }

        return map;
    }

    @GetMapping("/delUser/{id}")
    public Map<String,Object> delUser(@PathVariable String id){
        boolean isRemove = userService.removeById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("isRemove",isRemove);
        if(isRemove){
            map.put("msg","删除成功！");
        }else{
            map.put("msg","删除失败，请联系管理员");
        }

        return map;
    }
    @PostMapping("/batchDel")
    public Map<String,Object> batchDel(@RequestBody String ids){
        Map<String,Object> map = new HashMap<>();
        boolean isSuccess = false;
        try {

            JSONArray json = JSONArray.parseArray(ids);
            ArrayList<Long> list = new ArrayList<>();
            for (int i = 0; i < json.size(); i++) {
                list.add(Long.parseLong((String)json.get(i)));
            }

            isSuccess = userService.removeByIds(list);
            map.put("msg","批量删除成功");
            map.put("code","1");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg","批量删除失败，"+e.getMessage());
            map.put("code","0");
        }
        return map;
    }

    @PostMapping("/checkPWD")
    public boolean checkPWD(String userId,String oldpassword){

        User user = userService.getById(userId);
        if(MD5Util.encode(oldpassword).equals(user.getPassword())){
            return true;
        }else{
            return false;
        }

    }

    @PostMapping("/doChangePWD")
    public AJAXRespnose doChangePWD(@RequestBody String jsonStr) throws AppException {
        JSONObject json = JSON.parseObject(jsonStr);
        Map<String,Object> map = new HashMap<>();
        try {
            User user = new User().setPassword(MD5Util.encode((String)json.get("pwd")));
            user.setUserId(Long.parseLong((String) json.get("id")));
            boolean b = userService.updateById(user);
            String msg = "修改密码成功,新密码是："+(String)json.get("pwd");
            return new AJAXRespnose().success().message(msg);
        } catch (Exception e) {
            e.printStackTrace();
            //throw new AppException("重置密码失败！");
            return new AJAXRespnose().fail();
        }
    }

    @PostMapping("doUserEdit")
    public AJAXRespnose doUserEdit(User user){
        try {
            userService.updateById(user);
            return new AJAXRespnose().success().message("修改用户信息成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new AJAXRespnose().fail().message("修改用户信息失败，"+e.getMessage());
        }

    }

}


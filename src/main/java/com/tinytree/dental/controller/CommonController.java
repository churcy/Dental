package com.tinytree.dental.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class CommonController{

    @ResponseBody
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String hello(){
        JSONObject jsonObject = new JSONObject();
        try{
            String hello = "HELLO,Welcome to My Page!";
            jsonObject.put("Welcome",hello);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/common/systemTime",method = RequestMethod.GET)
    public String getSystemTime(){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("status","000000000");
            long systemTime = new Date().getTime();
            jsonObject.put("systemTime",systemTime);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}

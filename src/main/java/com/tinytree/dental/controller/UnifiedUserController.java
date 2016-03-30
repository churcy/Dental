package com.tinytree.dental.controller;

import com.tinytree.dental.entity.User;
import com.tinytree.dental.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by 重 on 2016/3/29.
 */
@Controller
public class UnifiedUserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/uui/getUser",method = RequestMethod.GET)
    public String getUser(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String status = "";
        try{
            String id = request.getParameter("id");
            User user = userService.get(id);
            jsonObject.put("user",user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/uui/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String status = "";
        try{
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            List<Map<String, Object>> users = userService.getByConditions(null, username, password);
            JSONArray jsonArray = new JSONArray(users);
            jsonObject.put("users",jsonArray);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}

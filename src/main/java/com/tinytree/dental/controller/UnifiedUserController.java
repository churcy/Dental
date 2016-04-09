package com.tinytree.dental.controller;

import com.tinytree.dental.entity.User;
import com.tinytree.dental.service.GroupMemberService;
import com.tinytree.dental.service.GroupService;
import com.tinytree.dental.service.UserService;
import com.tinytree.dental.service.impl.UserServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 重 on 2016/3/29.
 */
@Controller
public class UnifiedUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMemberService groupMemberService;

    @ResponseBody
    @RequestMapping(value = "/uui/getUser", method = RequestMethod.GET)
    public String getUser(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String status = "";
        try {
            String id = request.getParameter("id");
            User user = userService.get(id);
            jsonObject.put("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/uui/login", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String login(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String status = "";
        try {
            do {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                //List<Map<String, Object>> users = userService.getByConditions(null, username, password);
                List<Map<String, Object>> users = userService.login(username, password);
                if(users.size()!=1){
                    break;
                }
                jsonObject.put("status", "000");
                jsonObject.put("user", users.get(0));
            } while (false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/uui/getFriends", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getFriends(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String status = "";
        try {

            String id = request.getParameter("id");
            String password = request.getParameter("password");
            List<Map<String, Object>> users = userService.getByConditions(null, null, null);
            JSONArray jsonArray = new JSONArray(users);
            jsonObject.put("status", "000");
            jsonObject.put("users", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/uui/getGroups", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getGroups(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String status = "";
        try {
            String id = request.getParameter("id");
            String userId = request.getParameter("userId");
            String groupName = request.getParameter("groupName");
            List<Map<String, Object>> groups = groupService.getByConditions(null, userId, null);
            JSONArray jsonArray = new JSONArray(groups);
            jsonObject.put("groups", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/uui/getGroupMembers", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String getGroupMembers(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String status = "";
        try {
            String userId = request.getParameter("userId");
            List<Map<String, Object>> groupMembers = groupMemberService.getFreindByUserId(userId);
            Map<String, List<Map<String, Object>>> groupAndMember = new HashMap<>();
            List<Map<String, List<Map<String, Object>>>> groupMembersReturn = new ArrayList<>();
            for (Map<String, Object> groupAndMembers : groupMembers) {
                String groupName = (String) groupAndMembers.get("groupName");
                List<Map<String, Object>> members = new ArrayList<>();
                if (groupAndMember.get(groupName) == null) {
                    members.add(groupAndMembers);
                    groupAndMember.put(groupName, members);
                } else {
                    groupAndMember.get(groupName).add(groupAndMembers);
                }
            }
            groupMembersReturn.add(groupAndMember);
            JSONArray jsonArray = new JSONArray(groupMembersReturn);
            jsonObject.put("groupMembers", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    public static void main(String[] args){

    }
}

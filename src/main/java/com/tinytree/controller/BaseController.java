package com.tinytree.controller;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description:Controller基类
 * @ClassName: BaseController
 * @Author：tangyang
 * @Date 2016-02-15
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public class BaseController {

	protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    protected HttpSession session;
    
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;  
        this.session = request.getSession();  
    }
    
    protected String json(JSONObject json){
    	try {
    		response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
}

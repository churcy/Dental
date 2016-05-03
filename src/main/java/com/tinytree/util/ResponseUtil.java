package com.tinytree.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * @Description：response工具类
 * @ClassName: ResponseUtil
 * @Author：tangyang
 * @Date：2016-02-15
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class ResponseUtil 
{
	public static void send2Client(Map<String, Object> jsonMap,String token,HttpServletResponse response)
	{ 
		do{
			if(jsonMap == null || response == null)
			{
				break;
			}
			String content = JSONObject.fromObject(jsonMap).toString();
			//return send2Client(content,token,response);
			send2Client(content,token,response);
		}while(false);
		/**原代码
		 * if(jsonMap == null || response == null)
			{
				break;
			}
			String content = JSONObject.fromObject(jsonMap).toString();
			//return send2Client(content,token,response);
			renturn send2Client(content,token,response);
		 */
		
	}
	
	public static void send2Client(String content,String token,HttpServletResponse response)
	{	
		/**
		 * 原代码返回null
		 */
		try {
			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("token", token);
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
			//response.get
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return null;
	}
}

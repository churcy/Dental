package com.tinytree.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kungfu.dental.entity.AppVersion;
import com.kungfu.dental.entity.GroupInfo;
import com.kungfu.dental.entity.GroupMembers;
import com.kungfu.dental.entity.Weather;
import com.kungfu.dental.service.AppVersionService;
import com.kungfu.dental.service.FriendManageService;
import com.kungfu.dental.service.GroupInfoService;
import com.kungfu.dental.service.GroupMembersService;
import com.kungfu.dental.service.ImageVersionService;
import com.kungfu.dental.service.SMSService;
import com.kungfu.dental.service.WeatherService;
import com.kungfu.dental.util.DateUtils;
import com.kungfu.dental.util.GlobalUtil;
import com.kungfu.dental.util.RandomUtil;
/**
 * @Description:通用服务接口
 * @ClassName: CommonController
 * @Author：zhengzhong
 * @Date 2016-1-6
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Controller
public class CommonController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	@Autowired
	private WeatherService weatherService;
	@Autowired
	private SMSService smsService;
	@Autowired
	private AppVersionService appVersionService;
	@Autowired
	private ImageVersionService imageVersionService;
	
	/**
	 * 提供天气信息
	 * @param request 主要是从request中获取城市city参数
	 * @return 状态码和天气信息
	 * @throws IOException
	 * @throws JSONException
	 */
	@RequestMapping(value = "/common/weather",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String weather(HttpServletRequest request){
		String status= "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String city = (String) request.getParameter("city");
				if(StringUtils.isBlank(city)){
					status = GlobalUtil.PARAM_ERROR;
					jsonObject.put("status", status);
					logger.error("param error,city is null");
					break;
				}
				Weather weather = weatherService.getWeaterInfo(city);
				JSONObject json = JSONObject.fromObject(weather);
				status = GlobalUtil.SUCCESS;
				jsonObject.put("status", status);
				jsonObject.put("weatherInfo", json);
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			jsonObject.put("status", status);
			logger.error("error:"+e.toString());
		}
		return json(jsonObject);
	}
	
	/**
	 * 获取系统时间
	 * @param request 从request获取时间格式可以为空 为空时 默认格式为"yyyyMMdd HH:mm:ss"
	 * @return 系统时间 和 状态码
	 * @throws IOException 
	 */
	@RequestMapping(value = "/common/systemTime",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String getSystemTime(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String status= "";
		String time="";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String pattern = (String) request.getParameter("pattern");
				if(StringUtils.isBlank(pattern)){
					pattern = GlobalUtil.DATETIME_PATTERN;
				}
				Date date = new Date();
				time = DateUtils.format2String(date,pattern);
				status = GlobalUtil.SUCCESS;
				jsonObject.put("status", status);
				jsonObject.put("time", time);
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			jsonObject.put("status", status);
			logger.error("error:"+e.toString());
		}
		
		return json(jsonObject);
	}
	
	/**
	 * 发送短信验证码
	 * @param request 从request中获取用户手机号
	 * @return 返回 状态码 
	 */
	@RequestMapping(value = "/common/smsCode",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String sendSMSCode(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String userPhone = (String) request.getParameter("userPhone");
				if(StringUtils.isBlank(userPhone)){
					status = GlobalUtil.PARAM_ERROR;
					jsonObject.put("status", status);
					logger.error("param error,userPhone is null");
					break;
				}
				String verificationCode = RandomUtil.createVerificationCode(6);
				boolean type = smsService.sendSMSCode(userPhone, verificationCode);
				if(type){
					status = GlobalUtil.SUCCESS;
				}else{
					status = GlobalUtil.SYS_ERROR;
				}
				jsonObject.put("status", status);
			}while(false);
		}catch(Exception e){
			status = "901";
			jsonObject.put("status", status);
			logger.error("error:"+e.toString());
		}
		return json(jsonObject);
	}
	
	/**
	 * 获取app版本信息
	 * @param request 从request中获取 用户的版本id  app类型和名称
	 * @return 返回 状态码 和 更新信息
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/common/version",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String getVersion(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String version = (String) request.getParameter("version");
				Integer appType =  Integer.valueOf(request.getParameter("appType"));
				Integer appName = Integer.valueOf(request.getParameter("appName"));
				int upgradeInfo = 0;
				if(StringUtils.isBlank(version)||StringUtils.isBlank(String.valueOf(appType))||StringUtils.isBlank(String.valueOf(appName))){
					status = GlobalUtil.PARAM_ERROR;
					jsonObject.put("status", status);
					logger.error("param error ,params is null");
					break;
				}
				AppVersion appVersion = appVersionService.getLatestAppVerison(appType,appName);
				if(appVersion == null){
					break;
				}
				String versionNo = appVersion.getVersionNo();
				if(!version.equals(versionNo)){
					upgradeInfo = appVersion.getType();
					
				}else {
					upgradeInfo = 2;
				}
				jsonObject.put("version", versionNo);
				jsonObject.put("upgradeInfo", upgradeInfo);
				status = GlobalUtil.SUCCESS;
				jsonObject.put("status", status);
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			jsonObject.put("status", status);
			logger.error("error:"+e.toString());
		}
		return json(jsonObject);
	}
	/**
	 * 获取版本号 和 图片信息
	 * @param request 从request中获取  app类型和名称 以及usedBy:用途  0:代表作为开机欢迎页 1:代表首页轮播
	 * @return 返回 状态码 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/common/imgInfo",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String getImgInfo(HttpServletRequest request){
		String status = "";
		String imageVersion = null;
		JSONObject jsonObject =new JSONObject();
		List<String> imgUrlList = new ArrayList<String>();
		try{
			do{
				Integer appType =  Integer.valueOf(request.getParameter("appType"));
				Integer appName = Integer.valueOf(request.getParameter("appName")); 
				Integer usedBy = Integer.valueOf(request.getParameter("usedBy")); 
				if(StringUtils.isBlank(String.valueOf(usedBy))||StringUtils.isBlank(String.valueOf(appType))||StringUtils.isBlank(String.valueOf(appName))){
					status = GlobalUtil.PARAM_ERROR;
					jsonObject.put("status", status);
					logger.error("param error ,params is null");
					break;
				}
				imgUrlList = imageVersionService.getImages(appName, appType, usedBy);
				imageVersion = imageVersionService.getVersion(appName, appType, usedBy);
				JSONArray json = JSONArray.fromObject(imgUrlList);
				jsonObject.put("imgUrlList", json);
				jsonObject.put("version", imageVersion);
				jsonObject.put("status", GlobalUtil.SUCCESS);
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			jsonObject.put("status", status);
			logger.error("error:"+e.toString());
		}
		return json(jsonObject);
	}
	
}

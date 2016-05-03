package com.tinytree.controller;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kungfu.dental.entity.FeedBack;
import com.kungfu.dental.entity.User;
import com.kungfu.dental.service.FeedBackService;
import com.kungfu.dental.service.UserService;
import com.kungfu.dental.util.GlobalUtil;
/**
 * @Description:用户意见反馈接口
 * @ClassName: FeedBackController
 * @Author：zhengzhong
 * @Date 2016-1-31
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Controller
public class FeedBackController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(FeedBackController.class);
	@Autowired
	private FeedBackService feedBackService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/uui/userFeedBack" ,method = {RequestMethod.POST},produces ="text/plain;charset=UTF-8")
	public String createFeedBack(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject = new JSONObject();
		try{
			do{
				String fbContent = URLDecoder.decode(request.getParameter("fbContent"),"UTF-8");
				String fbTime = request.getParameter("fbTime");
				String accountId = request.getParameter("accountId");
				if(StringUtils.isBlank(accountId)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error, param is null");
					break;
				}
				FeedBack entity = new FeedBack();
				/*if(StringUtils.isNotBlank(fbTime)){
					Date fbDateTime = DateUtils.format2Date(fbTime, GlobalUtil.DATETIME_PATTERN);
					entity.setFbTime(fbDateTime);
				}*/
				if(StringUtils.isNotBlank(fbContent)){
					entity.setFbContent(fbContent);
				}
				User user = userService.getByAccountId(accountId);
				if(user == null){
					status = GlobalUtil.USER_ERROR;
					break;
				}
				String userId = user.getId();
				entity.setUserId(userId);
				feedBackService.save(entity);
				status = GlobalUtil.SUCCESS;
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	@RequestMapping()
	public String getFeedBackDetail(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject = new JSONObject();
		try{
			do{
				String accountId = request.getParameter("accountId");
				String date = request.getParameter("dateTime");
				if(StringUtils.isBlank(accountId)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error, param is null");
					break;
				}
				 Map<String, Object> rsult = feedBackService.getByUserId(accountId, date);
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
}

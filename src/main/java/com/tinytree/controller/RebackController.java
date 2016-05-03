package com.tinytree.controller;

import com.tinytree.entity.*;
import com.tinytree.service.*;
import com.tinytree.util.DateUtils;
import com.tinytree.util.GlobalUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

/**
 * @Description:回访管理接口
 * @ClassName: RebackController
 * @Author：tangyang
 * @Date 2016-1-23
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Controller
public class RebackController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(RebackController.class);
	
	@Autowired
	private ReturnVisitService returnVisitService;
	
	@Autowired
	private ReturnVisitRecordService returnVisitRecordService;
	
	/**
	 * 回访列表
	 * @param visitorId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reback/{visitorId}/rebackList",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String rebackList(@PathVariable String visitorId,HttpServletRequest request){
		
		String startPage = request.getParameter("startPage");
		String pageSize = request.getParameter("pageSize");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String visitStatus = request.getParameter("visitStatus");
		String status= GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(startPage,pageSize,visitorId)){
					logger.warn("[startPage,pageSize,startDate,endDate,visitorId]");
					logger.warn("$:"+startPage);
					logger.warn("$:"+pageSize);
					logger.warn("$:"+startDate);
					logger.warn("$:"+endDate);
					logger.warn("$:"+visitorId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Date dStartDate = null;
				Date dEndDate = null;
				if(StringUtils.isNotBlank(startDate)){
					dStartDate = DateUtils.format2Date(startDate, GlobalUtil.DATE_PATTERN);
				}
				
				if(StringUtils.isNotBlank(endDate)){
					dEndDate = DateUtils.format2Date(endDate, GlobalUtil.DATE_PATTERN);
				}
				
				int iStartPage = Integer.parseInt(startPage);
				int iPageSize = Integer.parseInt(pageSize);
				
				List list = returnVisitService.getRebackList(visitorId, iStartPage, iPageSize, dStartDate, dEndDate,visitStatus);
				if(list == null || list.isEmpty()){
					break;
				}
				
				List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
				for(Object obj : list){
					Map<String, Object> jsonMap = (Map<String, Object>) obj;
					Map<String,String> map = new HashMap<>();
					map.put("id", (String) jsonMap.get("id"));
					map.put("userId", (String) jsonMap.get("userId"));
					map.put("bookId", (String) jsonMap.get("bookId"));
					map.put("realName", (String) jsonMap.get("realName"));
					map.put("accountId", (String) jsonMap.get("accountId"));
					map.put("visitorName", (String) jsonMap.get("visitorName"));
					map.put("visitTime", DateUtils.format2String((Date)(jsonMap.get("visitTime")), GlobalUtil.DATE_PATTERN));
					map.put("medicalCreateTime",DateUtils.format2String((Date)(jsonMap.get("medicalCreateTime")), GlobalUtil.DATETIME_PATTERN));
					retList.add(map);
				}
				jsonObject.put("rebackList", JSONArray.fromObject(retList));
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}

	/**
	 * 時間查詢回访列表
	 * @param visitorId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reback/{visitorId}/rebackListByDate",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String rebackListByDate(@PathVariable String visitorId,HttpServletRequest request){
		
		String startPage = request.getParameter("startPage");
		String pageSize = request.getParameter("pageSize");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String visitStatus = request.getParameter("visitStatus");
		String status= GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(startPage,pageSize,visitorId)){
					logger.warn("[startPage,pageSize,startDate,endDate,visitorId]");
					logger.warn("$:"+startPage);
					logger.warn("$:"+pageSize);
					logger.warn("$:"+startDate);
					logger.warn("$:"+endDate);
					logger.warn("$:"+visitorId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Date dStartDate = null;
				Date dEndDate = null;
				if(StringUtils.isNotBlank(startDate)){
					dStartDate = DateUtils.format2Date(startDate, GlobalUtil.DATE_PATTERN);
				}
				
				if(StringUtils.isNotBlank(endDate)){
					dEndDate = DateUtils.format2Date(endDate, GlobalUtil.DATE_PATTERN);
				}
				
				int iStartPage = Integer.parseInt(startPage);
				int iPageSize = Integer.parseInt(pageSize);
				
				List list = returnVisitService.getRebackListByDate(visitorId, iStartPage, iPageSize, dStartDate, dEndDate,visitStatus);
				if(list == null || list.isEmpty()){
					break;
				}
				
				List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
				for(Object obj : list){
					Map<String, Object> jsonMap = (Map<String, Object>) obj;
					Map<String,String> map = new HashMap<>();
					map.put("id", (String) jsonMap.get("id"));
					map.put("userId", (String) jsonMap.get("userId"));
					map.put("bookId", (String) jsonMap.get("bookId"));
					map.put("realName", (String) jsonMap.get("realName"));
					map.put("accountId", (String) jsonMap.get("accountId"));
					map.put("visitorName", (String) jsonMap.get("visitorName"));
					map.put("visitTime", DateUtils.format2String((Date)(jsonMap.get("visitTime")), GlobalUtil.DATE_PATTERN));
					map.put("medicalCreateTime",DateUtils.format2String((Date)(jsonMap.get("medicalCreateTime")), GlobalUtil.DATETIME_PATTERN));
					retList.add(map);
				}
				jsonObject.put("rebackList", JSONArray.fromObject(retList));
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 创建回访详情
	 * @param rebackId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reback/{rebackId}/rebackRecord",method = {RequestMethod.POST},produces ="text/plain;charset=UTF-8")
	public String rebackRecord(@PathVariable String rebackId,HttpServletRequest request){
		String content = request.getParameter("content");
		String result = request.getParameter("result");
		String visitTime = request.getParameter("visitTime");
		
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(content,result,visitTime,rebackId)){
					logger.warn("[content,result,visitTime,rebackId]");
					logger.warn("$:"+content);
					logger.warn("$:"+result);
					logger.warn("$:"+visitTime);
					logger.warn("$:"+rebackId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Date dVisitTime = DateUtils.format2Date(visitTime, GlobalUtil.DATETIME_MINUTE_PATTERN);
				
				ReturnVisitRecord record = new ReturnVisitRecord();
				if(content != null){
					content = URLDecoder.decode("content","UTF-8");
					record.setContent(content);
				}
				if(result != null){
					result = URLDecoder.decode("result","UTF-8");
					record.setResult(result);
				}
				record.setReturnVisitId(rebackId);
				record.setVisitTime(dVisitTime);
				returnVisitRecordService.save(record);
				ReturnVisit visit = returnVisitService.get(rebackId);
				visit.setStatus(1);
				returnVisitService.update(visit);
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 回访记录详情列表
	 * @param rebackId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/reback/{userId}/rebackDetailList",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String rebackDetailList(@PathVariable String userId,HttpServletRequest request){
		String startPage = request.getParameter("startPage");
		String pageSize = request.getParameter("pageSize");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		String status= GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(startPage,pageSize,userId)){
					logger.warn("[startPage,pageSize,startDate,endDate,rebackId]");
					logger.warn("$:"+startPage);
					logger.warn("$:"+pageSize);
					logger.warn("$:"+startDate);
					logger.warn("$:"+endDate);
					logger.warn("$:"+userId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Date dStartDate = null;
				Date dEndDate = null;
				if(StringUtils.isNotBlank(startDate)){
					dStartDate = DateUtils.format2Date(startDate, GlobalUtil.DATE_PATTERN);
				}
				
				if(StringUtils.isNotBlank(endDate)){
					dEndDate = DateUtils.format2Date(endDate, GlobalUtil.DATE_PATTERN);
				}
				
				int iStartPage = Integer.parseInt(startPage);
				int iPageSize = Integer.parseInt(pageSize);
				
				List list = returnVisitService.getRebackDetails(userId, iStartPage, iPageSize, dStartDate, dEndDate);
				if(list == null || list.isEmpty()){
					break;
				}
				
				List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
				for(Object obj : list){
					Map<String, Object> jsonMap = (Map<String, Object>) obj;
					Map<String,String> map = new HashMap<>();
					map.put("id", (String) jsonMap.get("id"));
					map.put("content", (String) jsonMap.get("content"));
					map.put("result", (String) jsonMap.get("result"));
					map.put("visitTime", DateUtils.format2String((Date)(jsonMap.get("visitTime")), GlobalUtil.DATE_PATTERN));
					retList.add(map);
				}
				jsonObject.put("rebackRecordList", JSONArray.fromObject(retList));
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	@RequestMapping(value = "/reback/{rebackId}/rebackDetail",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String rebackDetail(@PathVariable String rebackId,HttpServletRequest request){
		String status = "";
		JSONObject jsonObject = new JSONObject();
		try{
			do{
				if(StringUtils.isBlank(rebackId)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				Map<String, Object> result = returnVisitService.getRebackDetail(rebackId);
				if(result == null){
					status = GlobalUtil.REBACK_DETAIL_ERROR;
					break;
				}
				String visitTime = DateUtils.format2String((Date)result.get("visitTime"), GlobalUtil.DATETIME_MINUTE_PATTERN);
				result.remove("visitTime");
				result.put("visitTime", visitTime);
				JSONObject object = JSONObject.fromObject(result);
				jsonObject.put("rebackRecordDetail", object);
				status = GlobalUtil.SUCCESS;
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
}

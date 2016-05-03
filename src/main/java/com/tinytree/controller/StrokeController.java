package com.tinytree.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kungfu.dental.entity.Account;
import com.kungfu.dental.entity.Doctor;
import com.kungfu.dental.entity.ReserveInfo;
import com.kungfu.dental.entity.Stroke;
import com.kungfu.dental.service.AccountService;
import com.kungfu.dental.service.DoctorService;
import com.kungfu.dental.service.ReserveInfoService;
import com.kungfu.dental.service.StrokeService;
import com.kungfu.dental.service.impl.DoctorServiceImpl;
import com.kungfu.dental.util.DateUtils;
import com.kungfu.dental.util.GlobalUtil;

/**
 * @Description:日程管理接口
 * @ClassName: StrokeController
 * @Author：tangyang
 * @Date 2016-1-26
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Controller
public class StrokeController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(StrokeController.class);
	
	@Autowired
	private StrokeService strokeService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ReserveInfoService reserveInfoService;
	
	@Autowired
	private DoctorService doctorService;
	
	/**
	 * 创建日程
	 * @param accountId
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/stroke/{accountId}/stroke",method = {RequestMethod.POST},produces ="text/plain;charset=UTF-8")
	public String createStroke(@PathVariable String accountId,HttpServletRequest request) throws UnsupportedEncodingException{
		String subject = URLDecoder.decode(request.getParameter("subject"),"UTF-8");;//主题
		String place = URLDecoder.decode(request.getParameter("place"),"UTF-8");//地点
		String remark = URLDecoder.decode(request.getParameter("remark"),"UTF-8");//备注
		String time = URLDecoder.decode(request.getParameter("time"),"UTF-8");//时间
		String repeat = request.getParameter("repeat");//重复
		String status= GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(subject,place,remark,time,repeat,accountId)){
					logger.warn("[subject,place,remark,time,repeat,accountId]");
					logger.warn("$:"+subject);
					logger.warn("$:"+place);
					logger.warn("$:"+remark);
					logger.warn("$:"+time);
					logger.warn("$:"+repeat);
					logger.warn("$:"+accountId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Account account = accountService.get(accountId);
				if(account == null){
					status = GlobalUtil.ACCOUNT_ERROR;
					break;
				}
				
				int iRepeat = Integer.valueOf(repeat);
				Date dTime = DateUtils.format2Date(time, GlobalUtil.DATETIME_PATTERN);
				Stroke stroke = new Stroke();
				stroke.setAccountId(accountId);
				stroke.setPlace(place);
				stroke.setRemark(remark);
				stroke.setRepeat(iRepeat);
				stroke.setSubject(subject);
				strokeService.saveStroke(stroke, dTime);
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 编辑行程
	 * @param accountId
	 * @param strokeId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stroke/{accountId}/{strokeId}/stroke",method = {RequestMethod.POST},produces ="text/plain;charset=UTF-8")
	public String modifyStroke(@PathVariable String accountId,@PathVariable String strokeId,HttpServletRequest request){
		String status= GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				String subject = URLDecoder.decode(request.getParameter("subject"),"UTF-8");//主题
				String place = URLDecoder.decode(request.getParameter("place"),"UTF-8");//地点
				String remark = URLDecoder.decode(request.getParameter("remark"),"UTF-8");//备注
				String time = URLDecoder.decode(request.getParameter("time"),"UTF-8");//时间
				String repeat = request.getParameter("repeat");//重复
				if(StringUtils.isAnyBlank(accountId,strokeId)){
					logger.warn("[subject,place,remark,time,repeat,accountId,strokeId]");
					logger.warn("$:"+subject);
					logger.warn("$:"+place);
					logger.warn("$:"+remark);
					logger.warn("$:"+time);
					logger.warn("$:"+repeat);
					logger.warn("$:"+accountId);
					logger.warn("$:"+strokeId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Stroke stroke = strokeService.get(strokeId);
				if(stroke == null){
					status = GlobalUtil.STROKE_ID_ERROR;
					break;
				}
				
				if(!accountId.equals(stroke.getAccountId())){
					status = GlobalUtil.STROKE_ACCOUNTID_ERROR;
					break;
				}
				
				if(StringUtils.isNotBlank(repeat)){
					int iRepeat = Integer.valueOf(repeat);
					stroke.setRepeat(iRepeat);
				}
				
				if(StringUtils.isNotBlank(time)){
					Date dTime = DateUtils.format2Date(time, GlobalUtil.DATETIME_PATTERN);
					stroke.setTime(dTime);
				}
				
				if(StringUtils.isNotBlank(place)){
					stroke.setPlace(place);
				}
				
				if(StringUtils.isNotBlank(remark)){
					stroke.setRemark(remark);
				}
				
				if(StringUtils.isNotBlank(subject)){
					stroke.setSubject(subject);
				}
				
				strokeService.updateStroke(stroke);
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 天视图查询
	 * @param accountId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/day/{accountId}/stroke",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String getStrokeByDay(@PathVariable String accountId,HttpServletRequest request){
		String date = request.getParameter("date");
		String type = request.getParameter("type");
		
		String status= GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(accountId,date,type)){
					logger.warn("[date,type,accountId]");
					logger.warn("$:"+date);
					logger.warn("$:"+type);
					logger.warn("$:"+accountId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Date dDate = DateUtils.format2Date(date, GlobalUtil.DATE_PATTERN);
				int iType = Integer.valueOf(type);
				if(iType < 0 || iType > 2){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Doctor doctor = doctorService.getByAccountId(accountId);
				if(doctor == null){
					status = GlobalUtil.DOCTOR_ERROR;
					break;
				}
				
				List<Stroke> strokes = null;
				List<Map<String, String>> reserveInfo = null;
						
				if(iType == 0 || iType == 2){
					strokes = strokeService.getStrokes(accountId, dDate);
				}
				
				if(iType == 0 || iType == 1){
					reserveInfo = reserveInfoService.findReserveInfos(doctor.getId(), dDate);
				}
				
				if(strokes != null && !strokes.isEmpty()){
					jsonObject.put("stroke",JSONObject.fromObject(castStrokes(strokes)));
				}
				
				if(reserveInfo != null && !reserveInfo.isEmpty()){
					jsonObject.put("reserve",JSONObject.fromObject(castReserveInfo(reserveInfo)));
				}
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 周视图日程查询
	 * @param accountId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/week/{accountId}/stroke",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String getStrokeByWeek(@PathVariable String accountId,HttpServletRequest request){
		String type = request.getParameter("type");
		String stratDate = request.getParameter("stratDate");
		String endDate = request.getParameter("endDate");
		
		String status= GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(accountId,stratDate,endDate,type)){
					logger.warn("[stratDate,endDate,type,accountId]");
					logger.warn("$:"+stratDate);
					logger.warn("$:"+endDate);
					logger.warn("$:"+type);
					logger.warn("$:"+accountId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Date dStratDate = DateUtils.format2Date(stratDate, GlobalUtil.DATE_PATTERN);
				Date dEndDate = DateUtils.format2Date(endDate, GlobalUtil.DATE_PATTERN);
				int iType = Integer.valueOf(type);
				if(iType < 0 || iType > 2){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Doctor doctor = doctorService.getByAccountId(accountId);
				if(doctor == null){
					status = GlobalUtil.DOCTOR_ERROR;
					break;
				}
				
				List<Stroke> strokes = null;
				List<ReserveInfo> reserveInfos = null;
						
				if(iType == 0 || iType == 2){
					strokes = strokeService.getStrokes(accountId);
				}
				
				if(iType == 0 || iType == 1){
					reserveInfos = reserveInfoService.findReserveList(doctor.getId(), dStratDate, dEndDate);
				}
				
				if(strokes != null && !strokes.isEmpty()){
					jsonObject.put("stroke",JSONObject.fromObject(strokeService.castCountByWeek(strokes, dStratDate, dEndDate)));
				}
				
				if(reserveInfos != null && !reserveInfos.isEmpty()){
					jsonObject.put("reserve",JSONObject.fromObject(reserveInfoService.castCountByWeek(reserveInfos)));
				}
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 月视图日程查询
	 * @param accountId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/month/{accountId}/stroke",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String getStrokeByMonth(@PathVariable String accountId,HttpServletRequest request){
		String type = request.getParameter("type");
		String stratDate = request.getParameter("stratDate");
		String endDate = request.getParameter("endDate");
		
		String status= GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(accountId,stratDate,endDate,type)){
					logger.warn("[stratDate,endDate,type,accountId]");
					logger.warn("$:"+stratDate);
					logger.warn("$:"+endDate);
					logger.warn("$:"+type);
					logger.warn("$:"+accountId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Date dStratDate = DateUtils.format2Date(stratDate, GlobalUtil.DATE_PATTERN);
				Date dEndDate = DateUtils.format2Date(endDate, GlobalUtil.DATE_PATTERN);
				int iType = Integer.valueOf(type);
				if(iType < 0 || iType > 2){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Doctor doctor = doctorService.getByAccountId(accountId);
				if(doctor == null){
					status = GlobalUtil.DOCTOR_ERROR;
					break;
				}
				
				List<Stroke> strokes = null;
				List<ReserveInfo> reserveInfos = null;
						
				if(iType == 0 || iType == 2){
					strokes = strokeService.getStrokes(accountId);
				}
				
				if(iType == 0 || iType == 1){
					reserveInfos = reserveInfoService.findReserveList(doctor.getId(), dStratDate, dEndDate);
				}
				
				if(strokes != null && !strokes.isEmpty()){
					jsonObject.put("stroke",JSONObject.fromObject(strokeService.castCountByMonth(strokes, dStratDate, dEndDate)));
				}
				
				if(reserveInfos != null && !reserveInfos.isEmpty()){
					jsonObject.put("reserve",JSONObject.fromObject(reserveInfoService.castCountByMonth(reserveInfos)));
				}
				
				
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	
	private Map<String,List<Map<String,String>>> castReserveInfo(List<Map<String, String>> list) throws ParseException{
		if(list == null || list.isEmpty()){
			return null;
		}
		
		Map<String,List<Map<String,String>>> map = new HashMap<String,List<Map<String,String>>>();
		for(Map<String, String> reserveMap:list){
			Date date = DateUtils.format2Date(reserveMap.get("startTime"), GlobalUtil.DATETIME_PATTERN);
			int hour = DateUtils.getFiled(date, Calendar.HOUR_OF_DAY);
			List<Map<String,String>> reserves = map.get(String.valueOf(hour));
			if(reserves == null){
				reserves = new ArrayList<>();
				reserves.add(reserveMap);
				map.put(String.valueOf(hour), reserves);
			}else{
				reserves.add(reserveMap);
			}
		}
		return map;
	}
	
	private Map<String,List<Map<String,String>>> castStrokes(List<Stroke> list){
		if(list == null || list.isEmpty()){
			return null;
		}
		
		Map<String,List<Map<String,String>>> map = new HashMap<String,List<Map<String,String>>>();
		for(Stroke stroke:list){
			Map<String,String> strokeMap = new HashMap<String,String>();
			strokeMap.put("subject", stroke.getSubject());
			strokeMap.put("place", stroke.getPlace());
			strokeMap.put("remark", stroke.getRemark());
			strokeMap.put("time", DateUtils.format2String(stroke.getTimeByday(), GlobalUtil.TIME_PATTERN));
			strokeMap.put("id", stroke.getId());
			List<Map<String,String>> strokes = map.get(String.valueOf(stroke.getHour()));
			if(strokes == null){
				strokes = new ArrayList<>();
				strokes.add(strokeMap);
				map.put(String.valueOf(stroke.getHour()), strokes);
			}else{
				strokes.add(strokeMap);
			}
		}
		
		return map;
	}
	
}

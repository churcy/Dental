package com.tinytree.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.tinytree.bean.PagerEx;
import com.tinytree.bean.PagerParam;
import com.tinytree.bean.PagerParam.ConditionType;
import com.kungfu.dental.entity.Account;
import com.kungfu.dental.entity.MedicalNode;
import com.kungfu.dental.entity.MedicalRecord;
import com.kungfu.dental.entity.MedicalRecordBook;
import com.kungfu.dental.entity.MedicalTemplate;
import com.kungfu.dental.entity.ReturnVisit;
import com.kungfu.dental.entity.User;
import com.kungfu.dental.entity.VisitInfo;
import com.kungfu.dental.service.AccountService;
import com.kungfu.dental.service.DoctorService;
import com.kungfu.dental.service.FriendManageService;
import com.kungfu.dental.service.MedicalNodeService;
import com.kungfu.dental.service.MedicalRecordBookService;
import com.kungfu.dental.service.MedicalRecordService;
import com.kungfu.dental.service.MedicalTemplateService;
import com.kungfu.dental.service.ReturnVisitService;
import com.kungfu.dental.service.UserService;
import com.kungfu.dental.service.VisitInfoService;
import com.kungfu.dental.util.DateUtils;
import com.kungfu.dental.util.GlobalUtil;

/**
 * @Description:病历管理接口
 * @ClassName: MedicalRecordController
 * @Author：tangyang
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Controller
public class MedicalRecordController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);
	
	@Autowired
	private VisitInfoService visitInfoService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private MedicalRecordBookService medicalRecordBookService;
	
	@Autowired
	private MedicalRecordService medicalRecordService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReturnVisitService returnVisitService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private MedicalNodeService medicalNodeService;
	
	@Autowired
	private MedicalTemplateService medicalTemplateService;
	
	@Autowired
	private FriendManageService friendManageService;
	
	/**
	 * 创建就诊记录
	 * @param accountId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/{accountId}/visit",method = {RequestMethod.POST},produces ="text/plain;charset=UTF-8")
	public String createVisit(@PathVariable String accountId,HttpServletRequest request){
		String status= "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String visitDate = request.getParameter("visitDate");
				String doctor = request.getParameter("doctor");
				String hospital = request.getParameter("hospital");
				String desc = request.getParameter("desc");
				if(doctor != null){
					doctor = URLDecoder.decode(doctor,"UTF-8");
				}
				if(hospital != null){
					hospital = URLDecoder.decode(hospital,"UTF-8");
				}
				if(desc != null){
					desc = URLDecoder.decode(desc,"UTF-8");
				}
				if(StringUtils.isAnyBlank(visitDate,accountId)){
					logger.warn("[visitDate,doctor,hospital,desc,accountId]");
					logger.warn("$:"+visitDate);
					logger.warn("$:"+doctor);
					logger.warn("$:"+hospital);
					logger.warn("$:"+desc);
					logger.warn("$:"+accountId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Account user = accountService.get(accountId);
				if(user == null){
					logger.warn("user is not in :"+accountId);
					status = GlobalUtil.ACCOUNT_ERROR;
					break;
				}
				
				Date date = DateUtils.format2Date(visitDate, GlobalUtil.DATE_PATTERN);
				VisitInfo visitInfo = new VisitInfo();
				visitInfo.setUserId(accountId);
				visitInfo.setVisitDate(date);
				visitInfo.setVisitDesc(desc);
				visitInfo.setDoctor(doctor);
				visitInfo.setHospital(hospital);
				visitInfoService.save(visitInfo);
				
				status = GlobalUtil.SUCCESS;
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 查询就诊记录
	 * @param accountId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/{accountId}/visit",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String visitRecordList(@PathVariable String accountId,HttpServletRequest request){
		String startPage = request.getParameter("startPage");
		String pageSize = request.getParameter("pageSize");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		String status= "";
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(startPage,pageSize,accountId)){
					logger.warn("[startPage,pageSize,startDate,endDate,accountId]");
					logger.warn("$:"+startPage);
					logger.warn("$:"+pageSize);
					logger.warn("$:"+startDate);
					logger.warn("$:"+endDate);
					logger.warn("$:"+accountId);
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
				
				PagerEx page = visitInfoService.getVisitInfoList(accountId, iStartPage, iPageSize, dStartDate, dEndDate);
				List list = page.getList();
				if(list == null || list.isEmpty()){
					jsonObject.put("visitList", "");
					status = GlobalUtil.SUCCESS;
					break;
				}
				
				List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
				for(Object obj : list){
					Map<String, String> jsonMap = new HashMap<String, String>();
					VisitInfo visitInfo = (VisitInfo) obj;
					jsonMap.put("visitId", visitInfo.getId());
					jsonMap.put("visitDate", DateUtils.format2String(visitInfo.getVisitDate(), GlobalUtil.DATE_PATTERN));
					jsonMap.put("visitDesc", visitInfo.getVisitDesc());
					retList.add(jsonMap);
				}
				
				jsonObject.put("count", page.getTotalCount());
				jsonObject.put("pageCount", page.getPageCount());
				jsonObject.put("visitList", JSONArray.fromObject(retList));
				status = GlobalUtil.SUCCESS;
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 就诊记录详情
	 * @param visitId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/{visitId}/visitDetail",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String visitRecordDetail(@PathVariable String visitId,HttpServletRequest request){
		String status= "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				if(StringUtils.isBlank(visitId)){
					logger.warn("[visitId]");
					logger.warn("$:"+visitId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				VisitInfo visitInfo = visitInfoService.get(visitId);
				if(visitInfo == null){
					status = GlobalUtil.VISITINFO_ERROR;
					break;
				}
				String visitDate = DateUtils.format2String(visitInfo.getVisitDate(), GlobalUtil.DATE_PATTERN);
				JSONObject object = JSONObject.fromObject(visitInfo);
				object.put("visitDate", visitDate);
				jsonObject.put("visitInfo", object);
				status = GlobalUtil.SUCCESS;
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error("error" +e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	@RequestMapping(value = "/medicalRecord/visitDetail",method = {RequestMethod.POST},produces ="text/plain;charset=UTF-8")
	public String modifyVisitRecord(HttpServletRequest request){
		String status= "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String visitId = request.getParameter("visitId");
				String visitDate= request.getParameter("visitDate");
				String doctor = request.getParameter("doctor");
				String hospital = request.getParameter("hospital");
				String visitDesc = request.getParameter("visitDesc");
				if(StringUtils.isBlank(visitId)){
					logger.warn("[visitId]");
					logger.warn("$:"+visitId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				VisitInfo visitInfo = visitInfoService.get(visitId);
				if(visitInfo == null){
					status = GlobalUtil.VISITINFO_ERROR;
					break;
				}
				if(StringUtils.isNotBlank(visitDate) ){
					Date newVisitDate = DateUtils.format2Date(visitDate, GlobalUtil.DATE_PATTERN);
					visitInfo.setVisitDate(newVisitDate);
				}
				if(StringUtils.isNotBlank(doctor)){
					doctor = URLDecoder.decode(doctor, "UTF-8");
					visitInfo.setDoctor(doctor);
				}
				if(StringUtils.isNotBlank(hospital)){
					hospital = URLDecoder.decode(hospital, "UTF-8");
					visitInfo.setHospital(hospital);
				}
				if(StringUtils.isNotBlank(visitDesc)){
					visitDesc = URLDecoder.decode(visitDesc, "UTF-8");
					visitInfo.setVisitDesc(visitDesc);
				}
				visitInfoService.update(visitInfo);
				status = GlobalUtil.SUCCESS;
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			e.printStackTrace();
			logger.error("error" +e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 注册用户创建病历
	 * @param userId
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/medicalRecord/{userId}/medicalRecord",method = {RequestMethod.PUT},produces ="text/plain;charset=UTF-8")
	public String createMedical(@PathVariable String userId,HttpServletRequest request) throws UnsupportedEncodingException{
		String doctorId = request.getParameter("doctorId");
		request.setCharacterEncoding("UTF-8");  
		String allergy = URLDecoder.decode(request.getParameter("allergy"), "UTF-8") ;
		String complaint = URLDecoder.decode(request.getParameter("complaint"), "UTF-8") ;
		String historyPresentIllness = URLDecoder.decode(request.getParameter("historyPresentIllness"), "UTF-8") ;
		String historyPastIllness = URLDecoder.decode(request.getParameter("historyPastIllness"), "UTF-8");
		String inspect = URLDecoder.decode(request.getParameter("inspect"), "UTF-8");
		String inspectNo = request.getParameter("inspectNo");
		String assistantInspect = URLDecoder.decode(request.getParameter("assistantInspect"), "UTF-8");
		String assistantInspectNo = request.getParameter("assistantInspectNo");
		String diagnosis = URLDecoder.decode(request.getParameter("diagnosis"), "UTF-8");
		String diagnosisNo = request.getParameter("diagnosisNo");
		String treatmentPlan = URLDecoder.decode(request.getParameter("treatmentPlan"), "UTF-8");
		String treatmentPlanNo = request.getParameter("treatmentPlanNo");
		String treatment = URLDecoder.decode(request.getParameter("treatment"), "UTF-8");
		String treatmentNo = request.getParameter("treatmentNo");
		String isReback = request.getParameter("isReback");
		String rebackDate = request.getParameter("rebackDate");
		
		String status= GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(userId,doctorId,complaint,historyPresentIllness,historyPastIllness,inspect,inspectNo,assistantInspect,assistantInspectNo,diagnosis,diagnosisNo,treatmentPlan,treatmentPlanNo,treatment,treatmentNo)){
					logger.warn("[userId,doctorId,complaint,historyPresentIllness,historyPastIllness,inspect,inspectNo,assistantInspect,assistantInspectNo,diagnosis,diagnosisNo,treatmentPlan,treatmentPlanNo,treatment,treatmentNo]");
					logger.warn("$:"+userId);
					logger.warn("$:"+doctorId);
					logger.warn("$:"+complaint);
					logger.warn("$:"+historyPresentIllness);
					logger.warn("$:"+historyPastIllness);
					logger.warn("$:"+inspect);
					logger.warn("$:"+inspectNo);
					logger.warn("$:"+assistantInspect);
					logger.warn("$:"+diagnosis);
					logger.warn("$:"+diagnosisNo);
					logger.warn("$:"+treatmentPlan);
					logger.warn("$:"+treatmentPlanNo);
					logger.warn("$:"+treatment);
					logger.warn("$:"+treatmentNo);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				User user = userService.get(userId);
				if(user == null){
					logger.warn("user is not in :"+userId);
					status = GlobalUtil.ACCOUNT_ERROR;
					break;
				}
				
				Account doctor = accountService.get(doctorId);
				if(doctor == null){
					logger.warn("doctor is not in :"+doctorId);
					status = GlobalUtil.ACCOUNT_ERROR;
					break;
				}
				
				MedicalRecord medicalRecord = new MedicalRecord();
				medicalRecord.setAssistantInspect(assistantInspect);
				medicalRecord.setAssistantInspectNo(assistantInspectNo);
				medicalRecord.setComplaint(complaint);
				medicalRecord.setDiagnosis(diagnosis);
				medicalRecord.setDiagnosisNo(diagnosisNo);
				medicalRecord.setHistoryPastIllness(historyPastIllness);
				medicalRecord.setHistoryPresentIllness(historyPresentIllness);
				medicalRecord.setInspect(inspect);
				medicalRecord.setInspectNo(inspectNo);
				medicalRecord.setTreatment(treatment);
				medicalRecord.setTreatmentNo(treatmentNo);
				medicalRecord.setTreatmentPlan(treatmentPlan);
				medicalRecord.setTreatmentPlanNo(treatmentPlanNo);
				medicalRecordService.createMedicalRecord(medicalRecord, userId, doctorId, allergy);
				
				addReback(isReback,DateUtils.format2Date(rebackDate, GlobalUtil.DATE_PATTERN),medicalRecord,userId,doctorId);
				
				status = GlobalUtil.SUCCESS;
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	private void addReback(String isReback,Date rebackDate,MedicalRecord medicalRecord,String userId,String doctorId){
		if(StringUtils.isAnyBlank(isReback,userId,doctorId)||rebackDate==null||medicalRecord==null){
			return;
		}
		
		if(!"1".equals(isReback)){
			return;
		}
		
		ReturnVisit returnVisit = new ReturnVisit();
		returnVisit.setUserId(userId);
		returnVisit.setMedicalRecordId(medicalRecord.getId());
		returnVisit.setStatus(0);
		returnVisit.setVisitorId(doctorId);
		returnVisit.setVisitTime(rebackDate);
		returnVisitService.save(returnVisit);
	}
	
	/**
	 * 非注册用户创建病历
	 * @param doctorId
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/medicalRecord/{accountId}/medicalRecordTmp",method = {RequestMethod.PUT},produces ="text/plain;charset=UTF-8")
	public String createMedicalTmp(@PathVariable String accountId,HttpServletRequest request) throws UnsupportedEncodingException{
		String groupId = request.getParameter("groupId");
		String realName = URLDecoder.decode(request.getParameter("realName"), "UTF-8");
		String gender = request.getParameter("gender");
		String marital = request.getParameter("marital");
		String phone = request.getParameter("phone");
		String birthday = request.getParameter("birthday");
		String allergy = URLDecoder.decode(request.getParameter("allergy"), "UTF-8") ;
		String complaint = URLDecoder.decode(request.getParameter("complaint"), "UTF-8") ;
		String historyPresentIllness = URLDecoder.decode(request.getParameter("historyPresentIllness"), "UTF-8") ;
		String historyPastIllness = URLDecoder.decode(request.getParameter("historyPastIllness"), "UTF-8");
		String inspect = URLDecoder.decode(request.getParameter("inspect"), "UTF-8");
		String inspectNo = request.getParameter("inspectNo");
		String assistantInspect = URLDecoder.decode(request.getParameter("assistantInspect"), "UTF-8");
		String assistantInspectNo = request.getParameter("assistantInspectNo");
		String diagnosis = URLDecoder.decode(request.getParameter("diagnosis"), "UTF-8");
		String diagnosisNo = request.getParameter("diagnosisNo");
		String treatmentPlan = URLDecoder.decode(request.getParameter("treatmentPlan"), "UTF-8");
		String treatmentPlanNo = request.getParameter("treatmentPlanNo");
		String treatment = URLDecoder.decode(request.getParameter("treatment"), "UTF-8");
		String treatmentNo = request.getParameter("treatmentNo");
		String isReback = request.getParameter("isReback");
		String rebackDate = request.getParameter("rebackDate");
		
		String status= "";
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(groupId,phone,realName,gender,marital,accountId,complaint,historyPresentIllness,historyPastIllness,inspect,inspectNo,assistantInspect,assistantInspectNo,diagnosis,diagnosisNo,treatmentPlan,treatmentPlanNo,treatment,treatmentNo)){
					logger.warn("[groupId,birthday,phone,realName,gender,marital,doctorId,complaint,historyPresentIllness,historyPastIllness,inspect,inspectNo,assistantInspect,assistantInspectNo,diagnosis,diagnosisNo,treatmentPlan,treatmentPlanNo,treatment,treatmentNo]");
					logger.warn("$:"+groupId);
					logger.warn("$:"+birthday);
					logger.warn("$:"+phone);
					logger.warn("$:"+realName);
					logger.warn("$:"+gender);
					logger.warn("$:"+marital);
					logger.warn("$:"+accountId);
					logger.warn("$:"+complaint);
					logger.warn("$:"+historyPresentIllness);
					logger.warn("$:"+historyPastIllness);
					logger.warn("$:"+inspect);
					logger.warn("$:"+inspectNo);
					logger.warn("$:"+assistantInspect);
					logger.warn("$:"+diagnosis);
					logger.warn("$:"+diagnosisNo);
					logger.warn("$:"+treatmentPlan);
					logger.warn("$:"+treatmentPlanNo);
					logger.warn("$:"+treatment);
					logger.warn("$:"+treatmentNo);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Account doctor = accountService.get(accountId);
				if(doctor == null){
					logger.warn("doctor is not in :"+accountId);
					status = GlobalUtil.ACCOUNT_ERROR;
					break;
				}
				
				User user = new User();
				user.setBirthday(DateUtils.format2Date(birthday, GlobalUtil.DATE_PATTERN));
				user.setMobile(phone);
				user.setGender(Integer.valueOf(gender));
				user.setMarital(Integer.valueOf(marital));
				user.setRealName(realName);
				
				
				MedicalRecord medicalRecord = new MedicalRecord();
				medicalRecord.setAssistantInspect(assistantInspect);
				medicalRecord.setAssistantInspectNo(assistantInspectNo);
				medicalRecord.setComplaint(complaint);
				medicalRecord.setDiagnosis(diagnosis);
				medicalRecord.setDiagnosisNo(diagnosisNo);
				medicalRecord.setHistoryPastIllness(historyPastIllness);
				medicalRecord.setHistoryPresentIllness(historyPresentIllness);
				medicalRecord.setInspect(inspect);
				medicalRecord.setInspectNo(inspectNo);
				medicalRecord.setTreatment(treatment);
				medicalRecord.setTreatmentNo(treatmentNo);
				medicalRecord.setTreatmentPlan(treatmentPlan);
				medicalRecord.setTreatmentPlanNo(treatmentPlanNo);
				medicalRecordService.createMedicalRecordTmp(medicalRecord,accountId,user,allergy,groupId);
				addReback(isReback,DateUtils.format2Date(rebackDate, GlobalUtil.DATE_PATTERN),medicalRecord,user.getId(),accountId);
				status = GlobalUtil.SUCCESS;
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 病历本列表
	 * @param doctorId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/{accountId}/medicalBookList",method = {RequestMethod.GET})
	public String medicalBookList(@PathVariable String accountId,HttpServletRequest request){
		String startPage = request.getParameter("startPage");
		String pageSize = request.getParameter("pageSize");
		String startCreateDate = request.getParameter("startCreateDate");
		String endCreateDate = request.getParameter("endCreateDate");
		String startVisitDate = request.getParameter("startVisitDate");
		String endVisitDate = request.getParameter("endVisitDate");
		
		String datePatten = request.getParameter("datePatten");
		
		String status= "";
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(startPage,pageSize,accountId)){
					logger.warn("[startPage,pageSize,doctorId]");
					logger.warn("$:"+startPage);
					logger.warn("$:"+pageSize);
					logger.warn("$:"+accountId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				if(StringUtils.isBlank(datePatten)){
					datePatten = GlobalUtil.DATE_PATTERN;
				}
				
				Date dStartDate = StringUtils.isBlank(startCreateDate)?null:DateUtils.format2Date(startCreateDate, datePatten);
				Date dEndDate = StringUtils.isBlank(endCreateDate)?null:DateUtils.format2Date(endCreateDate, datePatten);;
				
				Date dStartVisitDate = StringUtils.isBlank(startVisitDate)?null:DateUtils.format2Date(startVisitDate, datePatten);;
				Date dEndVisitDate = StringUtils.isBlank(startVisitDate)?null:DateUtils.format2Date(endVisitDate, datePatten);;
				
				int iStartPage = Integer.parseInt(startPage);
				int iPageSize = Integer.parseInt(pageSize);
				
				List<PagerParam> params = new ArrayList<>();
				PagerParam doctorIdParam = new PagerParam();
				//郑重添加
				String doctorId = doctorService.getByAccountId(accountId).getId();
				doctorIdParam.setParam(ConditionType.equal, "doctor_id", doctorId);
				params.add(doctorIdParam);
				
				if(dStartDate!=null){
					PagerParam param = new PagerParam();
					param.setParam(ConditionType.greaterThanOrEqual, "create_date", dStartDate);
					params.add(param);
				}
				if(dEndDate!=null){
					PagerParam param = new PagerParam();
					param.setParam(ConditionType.LessThenOrEqual, "create_date", dEndDate);
					params.add(param);
				}
				
				if(dStartVisitDate!=null){
					PagerParam param = new PagerParam();
					param.setParam(ConditionType.greaterThanOrEqual, "last_visit_date", dStartVisitDate);
					params.add(param);
				}
				if(dEndVisitDate!=null){
					PagerParam param = new PagerParam();
					param.setParam(ConditionType.LessThenOrEqual, "last_visit_date", dEndVisitDate);
					params.add(param);
				}
				
				Long count = null;
				Integer pageCount = null;
				List<MedicalRecordBook> list = null;
				
				PagerEx page = new PagerEx();
				page.setPageNumber(iStartPage);
				page.setPageSize(iPageSize);
				page.setParams(params);
				page = medicalRecordBookService.findByPager(page);
				count = page.getTotalCount();
				pageCount = page.getPageCount();
				list = page.getList();
				
				if(list == null || list.isEmpty()){
					status = GlobalUtil.SUCCESS;
					break;
				}
				
				List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
				for(MedicalRecordBook book : list){
					Map<String, String> jsonMap = new HashMap<String, String>();
					String realName = book.getUser().getRealName();
					String patientUserId = book.getUser().getId();
					String gender = String.valueOf(book.getUser().getGender());
					String phone = book.getUser().getMobile();
					String doctorFindId = book.getDoctorId();
					String doctorAccountId = doctorService.get(doctorFindId).getAccount().getId();
					String doctorName = userService.getByAccountId(doctorAccountId).getRealName();
					jsonMap.put("doctorName", doctorName);
					jsonMap.put("patientName", realName);
					jsonMap.put("patientUserId", patientUserId);
					jsonMap.put("gender", gender);
					jsonMap.put("phone", phone);
					jsonMap.put("visitDate", DateUtils.format2String(book.getLastVisitDate(), GlobalUtil.DATE_PATTERN));
					jsonMap.put("medicalBookId", book.getId());
					jsonMap.put("allergy", book.getAllergy());
					retList.add(jsonMap);
				}
				
				jsonObject.put("count", count);
				jsonObject.put("pageCount", pageCount);
				jsonObject.put("bookList", JSONArray.fromObject(retList));
				status = GlobalUtil.SUCCESS;
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
	 * 病历本详情
	 * @param medicalBookId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecordBook/{medicalBookId}",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String medicalBookDetail(@PathVariable String medicalBookId,HttpServletRequest request){
		String startPage = request.getParameter("startPage");
		String pageSize = request.getParameter("pageSize");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String datePatten = request.getParameter("datePatten");
		
		String status= GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(startPage,pageSize,medicalBookId)){
					logger.warn("[startPage,pageSize,startDate,endDate,medicalBookId]");
					logger.warn("$:"+startPage);
					logger.warn("$:"+pageSize);
					logger.warn("$:"+startDate);
					logger.warn("$:"+endDate);
					logger.warn("$:"+medicalBookId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				MedicalRecordBook book = medicalRecordBookService.get(medicalBookId);
				if(book == null){
					break;
				}
				
				Map<String, String> bookMap = new HashMap<String, String>();
				bookMap.put("realName", book.getUser().getRealName());
				bookMap.put("gender", String.valueOf(book.getUser().getGender()));
				bookMap.put("phone", book.getUser().getMobile());
				bookMap.put("visitDate", DateUtils.format2String(book.getLastVisitDate(), GlobalUtil.DATE_PATTERN));
				bookMap.put("medicalBookId", book.getId());
				
				jsonObject.put("medicalBook", JSONObject.fromObject(bookMap));
				
				if(StringUtils.isBlank(datePatten)){
					datePatten = GlobalUtil.DATE_PATTERN;
				}
				
				Date dStartDate = null;
				Date dEndDate = null;
				if(StringUtils.isNotBlank(startDate)){
					dStartDate = DateUtils.format2Date(startDate, datePatten);
				}
				
				if(StringUtils.isNotBlank(endDate)){
					dEndDate = DateUtils.format2Date(endDate, datePatten);
				}
				
				int iStartPage = Integer.parseInt(startPage);
				int iPageSize = Integer.parseInt(pageSize);
				
				PagerEx page = new PagerEx();
				page.setPageNumber(iStartPage);
				page.setPageSize(iPageSize);
				List<PagerParam> params = new ArrayList<>();
				PagerParam bookIdParam = new PagerParam();
				bookIdParam.setParam(ConditionType.equal, "book_id", medicalBookId);
				params.add(bookIdParam);
				if(dStartDate!=null){
					PagerParam startParam = new PagerParam();
					startParam.setParam(ConditionType.greaterThanOrEqual, "create_date", dStartDate);
					params.add(startParam);
				}
				if(dEndDate!=null){
					PagerParam endParam = new PagerParam();
					endParam.setParam(ConditionType.LessThenOrEqual, "create_date", dEndDate);
					params.add(endParam);
				}
				
				page.setParams(params);
				
				page = medicalRecordService.findByPager(page);
				List list = page.getList();
				if(list == null || list.isEmpty()){
					break;
				}
				
				List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
				for(Object obj : list){
					Map<String, String> jsonMap = new HashMap<String, String>();
					MedicalRecord record = (MedicalRecord) obj;
					jsonMap.put("id", record.getId());
					jsonMap.put("complaint", record.getComplaint());
					jsonMap.put("historyPresentIllness", record.getHistoryPresentIllness());
					jsonMap.put("historyPastIllness", record.getHistoryPastIllness());
					jsonMap.put("inspect", record.getInspect());
					jsonMap.put("inspectNo", record.getInspectNo());
					jsonMap.put("assistantInspect", record.getAssistantInspect());
					jsonMap.put("assistantInspectNo", record.getAssistantInspectNo());
					jsonMap.put("diagnosis", record.getDiagnosis());
					jsonMap.put("diagnosisNo", record.getDiagnosisNo());
					jsonMap.put("treatmentPlan", record.getTreatmentPlan());
					jsonMap.put("treatmentPlanNo", record.getTreatmentPlanNo());
					jsonMap.put("treatment", record.getTreatment());
					jsonMap.put("treatmentNo", record.getTreatmentNo());
					jsonMap.put("uri", record.getUri());
					jsonMap.put("time", DateUtils.format2String(record.getCreateDate(), GlobalUtil.DATE_PATTERN));
					retList.add(jsonMap);
				}
				jsonObject.put("medicalList", JSONArray.fromObject(retList));
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 用户的病历本记录
	 * @param accountId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/{accountId}/medicalBookListUser",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String medicalBookListUser(@PathVariable String accountId,HttpServletRequest request){
		
		String status= GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(accountId)){
					logger.warn("[accountId]");
					logger.warn("$:"+accountId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				Account account = accountService.get(accountId);
				if(account == null){
					status = GlobalUtil.ACCOUNT_ERROR;
					break;
				}
				
				User user = userService.getByAccountId(accountId);
				if(user == null){
					status = GlobalUtil.ACCOUNT_ERROR;
					break;
				}
				
				List list = medicalRecordBookService.getBookListByUser(user.getId());
				jsonObject.put("medicalBookList", JSONArray.fromObject(list));
				
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 用户病历信息列表
	 * @param accountId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/{bookId}/medicalList",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String medicalList(@PathVariable String bookId,HttpServletRequest request){
		String startPage = request.getParameter("startPage");
		String pageSize = request.getParameter("pageSize");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String datePatten = request.getParameter("datePatten");
		
		String status= GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(startPage,pageSize,bookId)){
					logger.warn("[startPage,pageSize,startDate,endDate,bookId]");
					logger.warn("$:"+startPage);
					logger.warn("$:"+pageSize);
					logger.warn("$:"+startDate);
					logger.warn("$:"+endDate);
					logger.warn("$:"+bookId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				if(StringUtils.isBlank(datePatten)){
					datePatten = GlobalUtil.DATE_PATTERN;
				}
				
				Date dStartDate = null;
				Date dEndDate = null;
				if(StringUtils.isNotBlank(startDate)){
					dStartDate = DateUtils.format2Date(startDate, datePatten);
				}
				
				if(StringUtils.isNotBlank(endDate)){
					dEndDate = DateUtils.format2Date(endDate, datePatten);
				}
				
				int iStartPage = Integer.parseInt(startPage);
				int iPageSize = Integer.parseInt(pageSize);
				
				PagerEx page = new PagerEx();
				page.setPageNumber(iStartPage);
				page.setPageSize(iPageSize);
				List<PagerParam> params = new ArrayList<>();
				PagerParam bookIdParam = new PagerParam();
				bookIdParam.setParam(ConditionType.equal, "book_id", bookId);
				params.add(bookIdParam);
				List<Map<String, Object>> result = medicalRecordService.findMedicalRecordList(bookId,dStartDate,dEndDate);
				if(result == null || result.isEmpty()){
					break;
				}
				for(Map<String, Object> map :result){
					String medicalCreateTime = DateUtils.format2String((Date)map.get("medicalCreateTime"),GlobalUtil.DATETIME_PATTERN);
					map.remove("medicalCreateTime");
					map.put("medicalCreateTime", medicalCreateTime);
					String visitStatus = String.valueOf(map.get("visitStatus"));
					String returnId = (String) map.get("returnId");
					if(StringUtils.isBlank(returnId)){
						map.put("returnId", "null");
					}
					map.remove("visitStatus");
					map.put("visitStatus",visitStatus);
				}
				jsonObject.put("medicalList", JSONArray.fromObject(result));
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 病历详情
	 * @param medicalId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/{medicalId}",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String medicalDetail(@PathVariable String medicalId,HttpServletRequest request){
		String status= GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		
		try{
			do{
				if(StringUtils.isAnyBlank(medicalId)){
					logger.warn("[medicalId]");
					logger.warn("$:"+medicalId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				MedicalRecord medicalRecord = medicalRecordService.get(medicalId);
				if(medicalRecord == null){
					break;
				}
				JSONObject object= JSONObject.fromObject(medicalRecord);
				object.remove("createDate");
				object.remove("modifdDate");
				/*String allergy = medicalRecordBookService.get(medicalRecord.getBookId()).getAllergy();
				object.put("allergy", allergy);*/
				jsonObject.put("medical", object);
				
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			logger.error(e.getMessage());
		}
		
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 查找根节点
	 * @param medicalId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/getRootNodes",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String getRootNodes(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject = new JSONObject();
		try{
			do{
				List<Map<String, Object>> rootNodesList = medicalNodeService.getRootNodes();
				jsonObject.put("rootNodesList", rootNodesList);
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			e.printStackTrace();
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}

	/**
	 * 查找父节点
	 * @param medicalId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/getParentNode",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String getParentNode(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject = new JSONObject();
		try{
			do{
				String nodeId = request.getParameter("nodeId");
				MedicalNode medicalNode = medicalNodeService.getParentNode(nodeId);
				JSONObject object = JSONObject.fromObject(medicalNode);
				object.remove("createDate");
				object.remove("modifyDate");
				jsonObject.put("parentNode", object);
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			e.printStackTrace();
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 查找子节点
	 * @param medicalId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/getChildNodes",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String getChildNodes(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject = new JSONObject();
		try{
			do{
				String nodeId = request.getParameter("nodeId");
				List<Map<String, Object>> medicalNodes = medicalNodeService.getChildNodes(nodeId);
				JSONArray object = JSONArray.fromObject(medicalNodes);
				jsonObject.put("childNodes", object);
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			e.printStackTrace();
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 删除节点
	 * @param medicalId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/deleteNode",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String deleteNode(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject = new JSONObject();
		try{
			do{
				String nodeId = request.getParameter("nodeId");
				List<Map<String, Object>> medicalNodes = medicalNodeService.getChildNodes(nodeId);
				int size = medicalNodes.size();
				if(size != 0){
					status = GlobalUtil.CHILD_NODE_EXIST;
					break;
				}
				medicalNodeService.delete(nodeId);
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			e.printStackTrace();
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 创建节点
	 * @param medicalId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/createMedicalNode",method = {RequestMethod.POST},produces ="text/plain;charset=UTF-8")
	public String createMedicalNode(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject = new JSONObject();
		try{
			do{
				String parentNodeId = request.getParameter("parentNodeId");
				String docotorId = request.getParameter("docotorId");
				String nodeName = URLDecoder.decode(request.getParameter("nodeName"),"UTF-8");
				if(StringUtils.isAnyBlank(nodeName)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				MedicalNode medicalNode = new MedicalNode();
				int nodeLevel = 0;
				if(parentNodeId!=null){
					MedicalNode parentNode = medicalNodeService.getById(parentNodeId);
					nodeLevel = parentNode.getNodeLevel();
				}
				medicalNode.setNodeLevle(nodeLevel+1);
				medicalNode.setParentNodeId(parentNodeId);
				medicalNode.setNodeName(nodeName);
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 创建病历模板
	 * @param medicalId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/createMedicalTemplate",method = {RequestMethod.POST},produces ="text/plain;charset=UTF-8")
	@Transactional 
	public String createMedicalTemplate(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject = new JSONObject();
		try{
			do{
				String nodeId = request.getParameter("nodeId");
				String doctorId = request.getParameter("doctorId");
 				String complaint = URLDecoder.decode(request.getParameter("complaint"), "UTF-8") ;
				String historyPresentIllness = URLDecoder.decode(request.getParameter("historyPresentIllness"), "UTF-8") ;
				String historyPastIllness = URLDecoder.decode(request.getParameter("historyPastIllness"), "UTF-8");
				String inspect = URLDecoder.decode(request.getParameter("inspect"), "UTF-8");
				String assistantInspect = URLDecoder.decode(request.getParameter("assistantInspect"), "UTF-8");
				String diagnosis = URLDecoder.decode(request.getParameter("diagnosis"), "UTF-8");
				String treatmentPlan = URLDecoder.decode(request.getParameter("treatmentPlan"), "UTF-8");
				String treatment = URLDecoder.decode(request.getParameter("treatment"), "UTF-8");
				String orders = URLDecoder.decode(request.getParameter("orders"),"UTF-8");
				if(StringUtils.isAnyBlank(nodeId)){
					logger.warn("[nodeId]");
					logger.warn("$:"+nodeId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				List<Map<String,Object>> medicalTemplates = medicalTemplateService.getByConditions(null,nodeId);
				int size = medicalTemplates.size();
				if(size != 0){
					status = GlobalUtil.MEDICAL_TEMPLATE_EXIST;
					break;
				}
				MedicalTemplate medicalTemplate = new MedicalTemplate();
				medicalTemplate.setNodeId(nodeId);
				if(StringUtils.isNotBlank(assistantInspect)){
					medicalTemplate.setAssistantInspect(assistantInspect);
				}
				if(StringUtils.isNotBlank(complaint)){
					medicalTemplate.setComplaint(complaint);
				}
				if(StringUtils.isNotBlank(diagnosis)){
					medicalTemplate.setDiagnosis(diagnosis);
				}
				if(StringUtils.isNotBlank(historyPastIllness)){
					medicalTemplate.setHistoryPastIllness(historyPastIllness);
				}
				if(StringUtils.isNotBlank(historyPresentIllness)){
					medicalTemplate.setHistoryPresentIllness(historyPresentIllness);
				}
				if(StringUtils.isNotBlank(inspect)){
					medicalTemplate.setInspect(inspect);
				}
				if(StringUtils.isNotBlank(orders)){
					medicalTemplate.setOrders(orders);
				}
				if(StringUtils.isNotBlank(treatment)){
					medicalTemplate.setTreatment(treatment);
				}
				if(StringUtils.isNotBlank(treatmentPlan)){
					medicalTemplate.setTreatmentPlan(treatmentPlan);
				}
				medicalTemplateService.createMedicalTemplate(medicalTemplate);
				List<Map<String,Object>> medicalTemplates2 = medicalTemplateService.getByConditions(null,nodeId);
				int size2 = medicalTemplates2.size();
				MedicalNode medicalNode = medicalNodeService.getById(nodeId);
				medicalNode.setNodeType(1);
				if(size2 != 0 && size2 == 1){
					Map<String, Object> map = medicalTemplates2.get(0);
					String tempalteId = (String) map.get("id");
					medicalNode.setTemplateId(tempalteId);
				}
				medicalNodeService.update(medicalNode);	
				
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 获取病历模板
	 * @param medicalId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/getMedicalTemplate",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String getMedicalTemplate(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject = new JSONObject();
		try{
			do{
				String templateId = request.getParameter("templateId");
				if(StringUtils.isAnyBlank(templateId)){
					logger.warn("[templateId]");
					logger.warn("$:"+templateId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				MedicalTemplate medicalTemplate = medicalTemplateService.getById(templateId);
				JSONObject object = JSONObject.fromObject(medicalTemplate);
				object.remove("createDate");
				object.remove("modifyDate");
				jsonObject.put("medicalTemplate", object);
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 删除病历模板
	 * @param medicalId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/deleteMedicalTemplate",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String deleteMedicalTemplate(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject = new JSONObject();
		try{
			do{
				String templateId = request.getParameter("templateId");
				if(StringUtils.isAnyBlank(templateId)){
					logger.warn("[templateId]");
					logger.warn("$:"+templateId);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				MedicalTemplate medicalTemplate = medicalTemplateService.get(templateId);
				if(medicalTemplate == null){
					status = GlobalUtil.MEDICAL_TEMPLATE_NOT_EXIST;
					break;
				}
				String nodeId = medicalTemplate.getNodeId();
				medicalTemplateService.delete(templateId);
				MedicalNode medicalNode = medicalNodeService.get(nodeId);
				medicalNode.setNodeType(0);
				medicalNodeService.update(medicalNode);
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 修改病历模板
	 * @param medicalId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/medicalRecord/modifyMedicalTemplate",method = {RequestMethod.POST},produces ="text/plain;charset=UTF-8")
	public String modifyMedicalTemplate(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject = new JSONObject();
		try{
			do{
				String nodeId = request.getParameter("nodeId");
				String templateId = request.getParameter("templateId");
 				String complaint = URLDecoder.decode(request.getParameter("complaint"), "UTF-8") ;
				String historyPresentIllness = URLDecoder.decode(request.getParameter("historyPresentIllness"), "UTF-8") ;
				String historyPastIllness = URLDecoder.decode(request.getParameter("historyPastIllness"), "UTF-8");
				String inspect = URLDecoder.decode(request.getParameter("inspect"), "UTF-8");
				String assistantInspect = URLDecoder.decode(request.getParameter("assistantInspect"), "UTF-8");
				String diagnosis = URLDecoder.decode(request.getParameter("diagnosis"), "UTF-8");
				String treatmentPlan = URLDecoder.decode(request.getParameter("treatmentPlan"), "UTF-8");
				String treatment = URLDecoder.decode(request.getParameter("treatment"), "UTF-8");
				String orders = URLDecoder.decode(request.getParameter("orders"),"UTF-8");
				if(StringUtils.isAnyBlank(complaint,historyPresentIllness,historyPastIllness,inspect,assistantInspect,diagnosis,treatmentPlan,treatment,orders)){
					logger.warn("[nodeId,complaint,historyPresentIllness,historyPastIllness,inspect,assistantInspect,diagnosis,treatmentPlan,treatment,orders]");
					logger.warn("$:"+nodeId);
					logger.warn("$:"+complaint);
					logger.warn("$:"+historyPresentIllness);
					logger.warn("$:"+historyPastIllness);
					logger.warn("$:"+inspect);
					logger.warn("$:"+assistantInspect);
					logger.warn("$:"+diagnosis);
					logger.warn("$:"+treatmentPlan);
					logger.warn("$:"+treatment);
					logger.warn("$:"+orders);
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				MedicalTemplate medicalTemplate = medicalTemplateService.get(templateId);
				if(StringUtils.isNotBlank(assistantInspect)){
					medicalTemplate.setAssistantInspect(assistantInspect);
				}
				if(StringUtils.isNotBlank(complaint)){
					medicalTemplate.setComplaint(complaint);
				}
				if(StringUtils.isNotBlank(diagnosis)){
					medicalTemplate.setDiagnosis(diagnosis);
				}
				if(StringUtils.isNotBlank(historyPastIllness)){
					medicalTemplate.setHistoryPastIllness(historyPastIllness);
				}
				if(StringUtils.isNotBlank(historyPresentIllness)){
					medicalTemplate.setHistoryPresentIllness(historyPresentIllness);
				}
				if(StringUtils.isNotBlank(inspect)){
					medicalTemplate.setInspect(inspect);
				}
				if(StringUtils.isNotBlank(orders)){
					medicalTemplate.setOrders(orders);
				}
				if(StringUtils.isNotBlank(treatment)){
					medicalTemplate.setTreatment(treatment);
				}
				if(StringUtils.isNotBlank(treatmentPlan)){
					medicalTemplate.setTreatmentPlan(treatmentPlan);
				}
				medicalTemplateService.update(medicalTemplate);
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
}

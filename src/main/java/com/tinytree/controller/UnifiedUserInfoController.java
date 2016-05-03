package com.tinytree.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.StatusType;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.tinytree.bean.Pager;
import com.kungfu.dental.entity.Account;
import com.kungfu.dental.entity.Department;
import com.kungfu.dental.entity.DocSysSetting;
import com.kungfu.dental.entity.Doctor;
import com.kungfu.dental.entity.GroupInfo;
import com.kungfu.dental.entity.GroupMembers;
import com.kungfu.dental.entity.Hospital;
import com.kungfu.dental.entity.Position;
import com.kungfu.dental.entity.User;
import com.kungfu.dental.entity.UserSysSetting;
import com.kungfu.dental.service.AccountService;
import com.kungfu.dental.service.DepartmentService;
import com.kungfu.dental.service.DoctorService;
import com.kungfu.dental.service.FriendManageService;
import com.kungfu.dental.service.GroupInfoService;
import com.kungfu.dental.service.GroupMembersService;
import com.kungfu.dental.service.HospitalSerivce;
import com.kungfu.dental.service.PositionService;
import com.kungfu.dental.service.ReserveInfoService;
import com.kungfu.dental.service.ReturnVisitService;
import com.kungfu.dental.service.RongCloudService;
import com.kungfu.dental.service.ShortMessageService;
import com.kungfu.dental.service.UserService;
import com.kungfu.dental.util.DateUtils;
import com.kungfu.dental.util.GlobalUtil;
import com.kungfu.dental.util.ValidatorUtils;

/**
 * @Description:用户管理服务接口
 * @ClassName: UnifiedUserInfoController
 * @Author：zhengzhong
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Controller 
public class UnifiedUserInfoController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(UnifiedUserInfoController.class);
	@Autowired
	private ReserveInfoService reserveInfoService;
	@Autowired
	private ReturnVisitService returnVisitService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ShortMessageService shortMessageService;
	@Autowired
	private UserService userService;
	@Autowired 
	private DoctorService doctorService;
	@Autowired
	private HospitalSerivce hospitalSerivce;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private FriendManageService friendManageService;
	@Autowired
	private GroupMembersService groupMembersService;
	@Autowired
	private GroupInfoService groupInfoService;
	@Autowired
	private RongCloudService rongCloudService;
	
	/**
	 * 处理用户注册
	 * @param request
	 * @return 状态码 成功失败等
	 */
	@RequestMapping(value ="/uui/user",method = RequestMethod.POST,produces ="text/plain;charset=UTF-8")
	public String userReg(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		Account account = new Account();
		User user = new User();
		try{
			do{
				String userName =URLDecoder.decode(request.getParameter("userName"),"UTF-8");
				String userPhone = request.getParameter("userPhone");
				String smsCode = request.getParameter("smsCode");
				String password = request.getParameter("password");
				String appType = request.getParameter("appType");
				if(StringUtils.isAnyBlank(userName,userPhone,smsCode,password,appType)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,params is null");
					break;
				}
	
				if(!"999999".equals(smsCode)){
					int messageSatus = shortMessageService.checkCode(userPhone, smsCode);
					if(messageSatus==0){
						status = GlobalUtil.CODE_ERROR;
						logger.error("code error ,code is not exist");
						break;
					}
					if(messageSatus==1){
						status = GlobalUtil.CODE_USED_ERROR;
						logger.error("code error ,code is used or overdue");
						break;
					}
					if(messageSatus==2){
						status = GlobalUtil.CODE_OVERDUE_ERROR;
						logger.error("code error ,code is overdue now");
						break;
					}
				}
				//查询用户表 手机号有  并且 accountId 不为空  则手机号已经使用了
				account.setLoginName(userName);
				account.setPassword(password);
				account.setStatus(0);
				user.setMobile(userPhone);
				//int regStatusMobile = userService.authMobile(userPhone);
				int regStatusName = accountService.authLoginName(userName);
				if(regStatusName==0){
					status = GlobalUtil.USERNAME_EXIST_ERROR;
					logger.error("username error ,username is exist");
					break;
				}
				accountService.save(account);
				String accountId = account.getId();
				user.setAccount(account);
				String defaultGroup = GlobalUtil.DEFAULT_GROUP;
				userService.saveAndCreateDefaultGroup(user,defaultGroup);
				status = GlobalUtil.SUCCESS;
				jsonObject.put("accountId", accountId);
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 处理用户修改密码
	 * @param request
	 * @return 状态码 成功失败等
	 */
	@RequestMapping(value ="/uui/passwd",method = RequestMethod.POST,produces ="text/plain;charset=UTF-8")
	public String changePasswd(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String userPhone = request.getParameter("phone");
				String smsCode = request.getParameter("smsCode");
				String password = request.getParameter("password");
				if(StringUtils.isAnyBlank(userPhone,smsCode,password)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,params is null");
					break;
				}
				User user = userService.findRegUserByMobile(userPhone);
				if(user == null ){
					status = GlobalUtil.PHONE_ERROR;
					logger.error("phone error ,phone is not exist");
					break;
				}
				if(!"999999".equals(smsCode)){
					int messageSatus = shortMessageService.checkCode(userPhone, smsCode);
					if(messageSatus==0){
						status = GlobalUtil.CODE_ERROR;
						logger.error("code error ,code is not exist");
						break;
					}
					if(messageSatus==1){
						status = GlobalUtil.CODE_USED_ERROR;
						logger.error("code error ,code is used or overdue");
						break;
					}
					if(messageSatus==2){
						status = GlobalUtil.CODE_OVERDUE_ERROR;
						logger.error("code error ,code is overdue now");
						break;
					}
				}
				Account account = user.getAccount();
				account.setPassword(password);
				account.setStatus(0);
				accountService.update(account);
				status = GlobalUtil.SUCCESS;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 修改手机号
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/phone",method = RequestMethod.POST,produces ="text/plain;charset=UTF-8")
	public String changePhone(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String orgPhone = request.getParameter("orgPhone");
				String smsCode = request.getParameter("smsCode");
				String phone = request.getParameter("phone");
				if(StringUtils.isAnyBlank(orgPhone,smsCode,phone)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,params is null");
					break;
				}
				if(!"999999".equals(smsCode)){
					int messageSatus = shortMessageService.checkCode(phone, smsCode);
					if(messageSatus==0){
						status = GlobalUtil.CODE_ERROR;
						logger.error("code error ,code is not exist");
						break;
					}
					if(messageSatus==1){
						status = GlobalUtil.CODE_USED_ERROR;
						logger.error("code error ,code is used or overdue");
						break;
					}
					if(messageSatus==2){
						status = GlobalUtil.CODE_OVERDUE_ERROR;
						logger.error("code error ,code is overdue now");
						break;
					}
				}
				User user = userService.findRegUserByMobile(orgPhone);
				if(user == null ){
					status = GlobalUtil.ORGPHONE_NOT_ERROR;
					logger.error("orgPhone error ,orgPhone is not exist");
					break;
				}
				User userNew = userService.findRegUserByMobile(phone);
				if(userNew != null ){
					status = GlobalUtil.USERPHONE_EXIST_ERROR;
					logger.error("userphone error ,userphone is exist");
					break;
				}
				user.setMobile(phone);
				userService.update(user);
				status = GlobalUtil.SUCCESS;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
		
	}
	/**
	 * 个人信息设置
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/userInfo/{accountId}",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String setUserInfo(@PathVariable("accountId") String accountId, HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String realName = request.getParameter("realName");
				String sex = request.getParameter("sex");
				
				String marriage = request.getParameter("marriage");
				String birthday = request.getParameter("birthday");
				String qq = request.getParameter("qq");
				String addr = request.getParameter("addr");
				if(StringUtils.isAnyBlank(accountId,realName,sex,birthday)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,param is null");
					break;
				}
				Account account = accountService.get(accountId);
				if(account==null){
					status = GlobalUtil.ACCOUNT_ERROR;
					logger.error("account error ,account is not exits");
					break;
				}
				User userR = userService.getByAccountId(accountId);
				userR.setAccount(account);
				userR.setRealName(realName);
				if(StringUtils.isNotBlank(sex)){
					userR.setGender(Integer.valueOf(sex));
				}
				if(StringUtils.isNotBlank(marriage)){
					userR.setMarital(Integer.valueOf(marriage));
				}
				userR.setBirthday(DateUtils.format2Date(birthday,GlobalUtil.DATE_PATTERN));
				
				if(StringUtils.isNotBlank(qq)){
					userR.setQq(Integer.valueOf(qq));
				}
				if(addr!=null){
					userR.setAddress(addr);
				}
				userService.update(userR);
				status = GlobalUtil.SUCCESS;
				jsonObject.put("accountId", accountId);
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 修改个人信息
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/userInfo/{userId}",method = RequestMethod.POST,produces ="text/plain;charset=UTF-8")
	public String modifyUserInfo(@PathVariable("userId") String userId,HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String realName = URLDecoder.decode(request.getParameter("realName"),"UTF-8");
				String sex = request.getParameter("sex");
				String marriage = request.getParameter("marriage");
				String birthday = request.getParameter("birthday");
				String qq = request.getParameter("qq");
				String addr = request.getParameter("addr");
				if(StringUtils.isAnyBlank(userId,birthday)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,param is null");
					break;
				}
				Account account = accountService.get(userId);
				if(account==null){
					status = GlobalUtil.ACCOUNT_ERROR;
					logger.error("account error ,account is not exits");
					break;
				}
				User user = userService.getByAccountId(userId);
				if(user==null){
					status = GlobalUtil.USER_ERROR;
					logger.error("user error ,user is not exits");
					break;
				}
				if(realName!=null){
					user.setRealName(realName);
				}
				if(StringUtils.isNotBlank(sex)){
					user.setGender(Integer.valueOf(sex));
				}
				if(StringUtils.isNotBlank(marriage)){
					user.setMarital(Integer.valueOf(marriage));
				}
				user.setBirthday(DateUtils.format2Date(birthday,GlobalUtil.DATE_PATTERN));
				if(StringUtils.isNotBlank(qq)){
					user.setQq(Integer.valueOf(qq));
				}
				if(addr!=null){
					user.setAddress(addr);
				}
				userService.modifyUserInfo(user);
				status = GlobalUtil.SUCCESS;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 设置头像
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/userAvatar/{accountId}",method = RequestMethod.POST,produces ="text/plain;charset=UTF-8")
	public String setAvatar(@PathVariable("accountId") String accountId,@RequestParam(value="userImg", required=false)MultipartFile userImg,
			HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				if(StringUtils.isAnyBlank(accountId)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,param is null");
					break;
				}
				String opt = request.getParameter("opt");
				String absolutePath = userService.getFilePath().get(0).toString();
				String relativePath = userService.getFilePath().get(1);
				User user = userService.getByAccountId(accountId);
				if(user==null){
					status = GlobalUtil.USER_ERROR;
					logger.error("user error ,user is not exist");
					break;
				}
				if(userImg==null){
					status = GlobalUtil.USERIMG_ERROR;
					logger.error("userImg error ,userImg is null");
					break;
				}
				String userImgName = userImg.getOriginalFilename();
				String userId = user.getId();
				String[] names = userImgName.split("\\.");
				//"_"+DateUtils.format2String(new Date(), GlobalUtil.DATE_PATTERN)
				String systemName = userId+"."+names[1];
				File imgFile = new File(absolutePath+relativePath,systemName);
				userImg.transferTo(imgFile);
				user.setAvatar(systemName);
				userService.update(user);
				status = GlobalUtil.SUCCESS;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 获取头像
	 * @param request
	 * @return 状态码  
	 * @throws IOException 
	 */
	@RequestMapping(value ="/uui/userAvatar/{accountId}",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public void getAvatar(@PathVariable("accountId") String accountId,HttpServletRequest request,HttpServletResponse response) throws IOException{
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				if(StringUtils.isAnyBlank(accountId)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,param is null");
					break;
				}
				String absolutePath = userService.getFilePath().get(0);
				String relativePath = userService.getFilePath().get(1);
				User user = userService.getByAccountId(accountId);
				if(user==null){
					status = GlobalUtil.USER_ERROR;
					logger.error("user error ,user is not exist");
					break;
				}
				String systemAvatarName = user.getAvatar();
				if(systemAvatarName == null){
					status = GlobalUtil.USERIMG_ERROR;
					break;
					
				}
				File file = new File(absolutePath+systemAvatarName);
				FileInputStream fileInputStream = new FileInputStream(file);
				OutputStream stream = response.getOutputStream();
				byte[]bytes=new byte[1024];
				int numReadByte=0;
			    while((numReadByte=fileInputStream.read(bytes,0,1024))>0){
			    	stream.write(bytes, 0, numReadByte);
			    }
			    stream.flush();
			    stream.close();
			    fileInputStream.close();
				status = GlobalUtil.SUCCESS;
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			e.printStackTrace();
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		json(jsonObject);
	}

	/**
	 * 查询个人信息
	 * @param request
	 * @return 状态码  个人信息
	 */
	@RequestMapping(value ="/uui/userInfo",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String queryUserInfo(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String accountId = request.getParameter("accountId");
				if(StringUtils.isAnyBlank(accountId)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,param is null");
					break;
				}
				User user = userService.getByAccountId(accountId);
				if(user==null){
					status = GlobalUtil.USER_ERROR;
					logger.error("user error ,user is not exits");
					break;
				}
				JSONObject object = JSONObject.fromObject(user);
				object.remove("createDate");
				object.remove("modifyDate");
				object.remove("account");
				String birt = DateUtils.format2String(user.getBirthday(), GlobalUtil.DATE_PATTERN);
				object.put("birthday",birt);
				if(StringUtils.isNotBlank(birt)){
					int age = DateUtils.getAge(birt);
					object.put("age",age);
				}
				status = GlobalUtil.SUCCESS;
				jsonObject.put("userInfo", object);
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 查询个人信息
	 * @param request
	 * @return 状态码  个人信息
	 */
	@RequestMapping(value ="/uui/userInfoById",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String queryUserInfoById(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String userId = request.getParameter("userId");
				if(StringUtils.isAnyBlank(userId)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,param is null");
					break;
				}
				Map<String, Object> user = userService.getByUserId(userId);
				if(user==null){
					status = GlobalUtil.USER_ERROR;
					logger.error("user error ,user is not exits");
					break;
				}
				JSONObject object = JSONObject.fromObject(user);
				String allergy = (String) user.get("allergy");
				String birt = DateUtils.format2String((Date)user.get("birthday"), GlobalUtil.DATE_PATTERN);
				if(StringUtils.isBlank(allergy)){
					object.put("allergy","null");
				}
				object.put("birthday",birt);
				object.put("age", DateUtils.getAge(birt));
				status = GlobalUtil.SUCCESS;
				jsonObject.put("userInfo", object);
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 查询注册状态差
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/user",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String queryReg(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String userName = request.getParameter("userName");
				String userPhone = request.getParameter("userPhone");
				if(StringUtils.isAnyBlank(userPhone,userName)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,params is null");
					break;
				}
				int regStatusMobile = userService.authMobile(userPhone);
				int regStatusName = accountService.authLoginName(userName);
				if(regStatusName==0){
					status = GlobalUtil.USERNAME_EXIST_ERROR;
					logger.error("username error ,username is exist");
					break;
				}
				if(regStatusMobile==0){
					status = GlobalUtil.USERPHONE_EXIST_ERROR;
					logger.error("userphone error ,userphone is exist");
					break;
				}
				status = GlobalUtil.SUCCESS;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 修改个人信息
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/login",method = RequestMethod.POST,produces ="text/plain;charset=UTF-8")
	public String login(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String userId = URLDecoder.decode(request.getParameter("userId"),"UTF-8");
				String password = request.getParameter("password");
				if(StringUtils.isAnyBlank(userId,password)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,params is null");
					break;
				}
				boolean flag = ValidatorUtils.isMobile(userId);
				User user = null;
				Account account = new Account();
				String realPassword;
				String accountId;
				String realName;
				String userCopyId;
				if(flag){
					user = userService.findRegUserByMobile(userId);
					if(user == null){
						status = GlobalUtil.PHONE_ERROR;
						break;
					}
					userCopyId = user.getId();
					realName = user.getRealName();
					accountId = user.getAccount().getId();
					realPassword = user.getAccount().getPassword();
				}else{
					account = accountService.getByUserName(userId);
					if(account == null){
						status = GlobalUtil.LONGIN_ERROR;
						break;
					}
					accountId = account.getId();
					realPassword = account.getPassword();
					User userCopy = userService.getByAccountId(accountId);
					realName = userCopy.getRealName();
					userCopyId  = userCopy.getId();
				}
				
				if(realPassword == null){
					status = GlobalUtil.PASSWORD_ERROR;
					logger.error("longin error,password is error ");
					break;
				}
				if(realName == null){
					status = GlobalUtil.USER_ERROR;
					jsonObject.put("accountId", accountId);
					jsonObject.put("userId", userCopyId);
					jsonObject.put("realName", realName);
					logger.error("longin error,loginInfo is not exist ");
					break;
				}
				if(!realPassword.equals(password)){
					status = GlobalUtil.PASSWORD_ERROR;
					logger.error("password error,password is wrong ");
					break;
				}
				Doctor doctor = doctorService.getByAccountId(accountId);
				int userStatus = 0;
				int hospitalStatus = 0;
				if(doctor != null){
					String doctorId = doctor.getId();
					jsonObject.put("doctorId", doctorId);
					int verifiedStatus = doctor.getVerifiedStatus();
					userStatus = verifiedStatus;
					Hospital hospital = doctor.getHospital();
					hospitalStatus = hospital.getVerifiedStatus();
				} else {
					hospitalStatus = 3;
					userStatus = 3;
				}
				jsonObject.put("hospitalStatus", hospitalStatus);
				jsonObject.put("userStatus", userStatus);
				jsonObject.put("accountId", accountId);
				jsonObject.put("userId", userCopyId);
				jsonObject.put("realName", realName);
				status = GlobalUtil.SUCCESS;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 创建医生
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/physician",method = RequestMethod.POST,produces ="text/plain;charset=UTF-8")
	public String createPhysician(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String accountId = request.getParameter("accountId");
				String phyType = request.getParameter("phyType");
				String positionId = request.getParameter("positionId");
				String authType = request.getParameter("authType");
				String hospitalId = request.getParameter("hospitalId");
				String deptId = request.getParameter("deptId");
				String authPhyId = request.getParameter("authPhyId");
				String duties = request.getParameter("duties");
				String introduction = request.getParameter("introduction");
				String specialty = request.getParameter("specialty");
				if(duties !=null){
					duties = URLDecoder.decode(duties,"UTF-8");
				}
				if(introduction !=null){
					introduction = URLDecoder.decode(introduction,"UTF-8");
				}
				if(specialty !=null){
					specialty = URLDecoder.decode(duties,"UTF-8");
				}
				if(StringUtils.isAnyBlank(accountId,phyType,positionId,authType,hospitalId,deptId)){
					status = GlobalUtil.PARAM_ERROR;
					logger.warn("[accountId,phyType,positionId,authType,hospitalId,deptId]");
					logger.warn("$:"+accountId);
					logger.warn("$:"+phyType);
					logger.warn("$:"+positionId);
					logger.warn("$:"+authType);
					logger.warn("$:"+hospitalId);
					logger.warn("$:"+deptId);
					logger.error("param error ,params is null");
					break;
				}
				Doctor doctor = new Doctor();
				User user = userService.getByAccountId(accountId);
				Doctor sysDoctor = doctorService.getByAccountId(accountId);
				if(sysDoctor != null){
					status = GlobalUtil.DOCTOR_EXIST_ERROR;
					break;
				}
				if(user == null){
					status = GlobalUtil.USER_ERROR;
					logger.error("user error,user is not exist ");
					break;
				}
				Account account = accountService.get(accountId);
				if(account == null){
					status = GlobalUtil.ACCOUNT_ERROR;
					logger.error("account error,account is not exist ");
					break;
				}
				Hospital hospital = hospitalSerivce.get(hospitalId);
				if(hospital==null){
					status = GlobalUtil.HOSPITAL_ERROR;
					logger.error("hospital error ,hospital is not exist");
					break;
				}
				Department department = departmentService.get(deptId);
				if(department==null){
					status = GlobalUtil.DEPARTMENT_ERROR;
					logger.error("doctor error ,doctor is not exist");
					break;
				}
				Position position = positionService.get(positionId);
				if(position==null){
					status = GlobalUtil.POSITION_ERROR;
					logger.error("position error ,position is not exist");
					break;
				}
				doctor.setDepartment(department);
				doctor.setHospital(hospital);
				doctor.setAccount(account);
				doctor.setType(Integer.valueOf(phyType));
				doctor.setPosition(position);
				doctor.setVerifiedType(Integer.valueOf(authType));
				doctor.setDuties(duties);
				doctor.setBrief(introduction);
				doctor.setAssistVerifiedAccountId(authPhyId);
				doctor.setSpecialty(specialty);
				doctorService.save(doctor);
				jsonObject.put("doctorId", doctor.getId());
				status = GlobalUtil.SUCCESS;
				}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}

	/**
	 * 预约医师列表
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/physician",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String physicianList(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String hospitalId = request.getParameter("hospitalId");
				String type = request.getParameter("type");
				String filter = request.getParameter("filter");
				String startPage = request.getParameter("startPage");
				String pageSize = request.getParameter("pageSize");
				if(StringUtils.isAnyBlank(startPage,pageSize)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,params is null");
					break;
				}
				List<Map<String, Object>> doctors = doctorService.findByConditions(hospitalId,type,startPage,pageSize);
				JSONArray doctorsList = JSONArray.fromObject(doctors);
				status = GlobalUtil.SUCCESS;
				jsonObject.put("doctorsList",doctorsList);
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 修改医生职业信息
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/physicianInfo",method = RequestMethod.POST,produces ="text/plain;charset=UTF-8")
	public String modifyDoctorInfo(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String accountId = request.getParameter("accountId");
				String phyType = request.getParameter("phyType");
				String positionId = request.getParameter("positionId");
				String authType = request.getParameter("authType");
				String hospitalId = request.getParameter("hospitalId");
				String deptId = request.getParameter("deptId");
				String authPhyId = request.getParameter("authPhyId");
				String duties = request.getParameter("duties");
				String brief = request.getParameter("brief");
				String specialty = request.getParameter("specialty");
				if(duties !=null){
					duties = URLDecoder.decode(duties,"UTF-8");
				}
				if(brief !=null){
					brief = URLDecoder.decode(brief,"UTF-8");
				}
				if(specialty !=null){
					specialty = URLDecoder.decode(specialty,"UTF-8");
				}
				if(StringUtils.isAnyBlank(accountId)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,params is null");
					break;
				}
				Doctor doctor = doctorService.getByAccountId(accountId);
				if(doctor == null){
					status = GlobalUtil.DOCTOR_ERROR;
					logger.error("doctor error ,doctor is not exist");
					break;
				}
				if(duties != null){
					doctor.setDuties(duties);
				}
				if(specialty != null){
					doctor.setSpecialty(specialty);
				}
				if(phyType != null){
					doctor.setType(Integer.valueOf(phyType));
				}
				if(deptId != null){
					Department department = departmentService.get(deptId);
					doctor.setDepartment(department);
				}
				
				if(hospitalId!=null){
					Hospital hospital = hospitalSerivce.get(hospitalId);
					doctor.setHospital(hospital);
				}
				if(positionId!=null){
					Position position = positionService.get(positionId);
					doctor.setPosition(position);
				}
				if(authPhyId != null){
					doctor.setAssistVerifiedAccountId(authPhyId);
				}
				if(authType != null){
					doctor.setVerifiedType(Integer.valueOf(authType));
				}
				if(brief!=null){
					doctor.setBrief(brief);
				}
				doctorService.update(doctor);
				status = GlobalUtil.SUCCESS;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 医师详细信息
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/physician/{accountId}",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String physicianDetail(@PathVariable("accountId") String accountId,HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				if(StringUtils.isBlank(accountId)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,params is null");
					break;
				}
				Doctor doctor = doctorService.getByAccountId(accountId);
				if(doctor==null){
					status = GlobalUtil.DOCTOR_ERROR;
					logger.error("doctor error ,doctor is not exist");
					break;
				}
				status = GlobalUtil.SUCCESS;
				JSONObject object = JSONObject.fromObject(doctor);
				Hospital hospital = doctor.getHospital();
				String hospitalName = hospital.getName();
				String hospitalMobile= hospital.getMobile();
				String hospitalTel= hospital.getTelephone();
				String hospitalAddr = hospital.getAddress();
			    String departmentName = doctor.getDepartment().getName();
				String positionName = doctor.getPosition().getName();
				String doctorAccountId = doctor.getAccount().getId();
				User userDoctor = userService.getByAccountId(doctorAccountId);
				int doctorGender = userDoctor.getGender();
				String doctorName = userDoctor.getRealName();
				String doctorMobile = userDoctor.getMobile();
				String doctorAdd = userDoctor.getAddress();
				String birthday = DateUtils.format2String(userDoctor.getBirthday(), GlobalUtil.DATE_PATTERN);
				int age = DateUtils.getAge(birthday);
				object.remove("createDate");
				object.remove("modifyDate");
				object.remove("hospital");
				object.remove("department");
				object.remove("position");
				object.remove("account");
				object.put("doctorMobile",doctorMobile);
				object.put("doctorGender",doctorGender);
				object.put("doctorAdd",doctorAdd);
				object.put("doctorName",doctorName);
				object.put("hospitalName",hospitalName);
				object.put("hospitalMobile",hospitalMobile);
				object.put("hospitalTel",hospitalTel);
				object.put("hospitalAddr",hospitalAddr);
				object.put("departmentName",departmentName);
				object.put("positionName",positionName);
				object.put("birthday",birthday);
				object.put("age",age);
				jsonObject.put("physicianInfo",object);
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 医院/诊所列表
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/hospital",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String hospitalList(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String startPage = request.getParameter("startPage");
				String pageSize = request.getParameter("pageSize");
				if(StringUtils.isAnyBlank(startPage,pageSize)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,params is null");
					break;
				}
				List<Map<String,Object>> hospitals = hospitalSerivce.findByConditions(startPage,pageSize);
				JSONArray hospitalList = JSONArray.fromObject(hospitals);
				status = GlobalUtil.SUCCESS;
				jsonObject.put("hospitalList", hospitalList);
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 患者列表
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/reserve/patient ",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String patientList(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String userCheck = request.getParameter("userCheck");
				if(StringUtils.isAnyBlank(userCheck)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,params is null");
					break;
				}
				boolean flag = ValidatorUtils.isMobile(userCheck);
				Pager pager = new Pager();
				User user = new User();
				if(flag){
					pager.setProperty("mobile");
					pager.setKeyword(userCheck);
				}else{
					pager.setProperty("real_name");
					pager.setKeyword(userCheck);
				}
				List<User> users = userService.findByPager(pager).getList();
				if(users == null){
					status = GlobalUtil.USER_ERROR;
					break;
				}
				user = users.get(0);
				Account account = user.getAccount();
				String userAccountId =null;
				if(account != null){
					userAccountId=account.getId();
				}
				String userId = user.getId();
				String realName = user.getRealName();
				String mobile = user.getMobile();
				Date birthday = user.getBirthday();
				int gender = user.getGender();
				JSONObject object = new JSONObject();
				object.put("userId", userId);
				object.put("userAccountId", userAccountId);
				object.put("realName", realName);
				object.put("mobile", mobile);
				object.put("birthday", DateUtils.format2String(birthday, GlobalUtil.DATE_PATTERN));
				object.put("gender", gender);
				jsonObject.put("userInfo", object);
				status = GlobalUtil.SUCCESS;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 创建医院
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/hospital ",method = RequestMethod.POST,produces ="text/plain;charset=UTF-8")
	public String createHospital(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String hosName = URLDecoder.decode(request.getParameter("hosName"),"UTF-8");
				String hosAddr = URLDecoder.decode(request.getParameter("hosAddr"),"UTF-8");
				String hosPhone = request.getParameter("hosPhone");
				if(StringUtils.isAnyBlank(hosName,hosAddr,hosPhone)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,params is null");
					break;
				}
				Hospital hospital = new Hospital();
				Pager pager = new Pager();
				pager.setKeyword(hosName);
				pager.setProperty("name");
				List list = hospitalSerivce.findByPager(pager).getList();
				if(list != null){
					status = GlobalUtil.HOSPITAL_EXIST_ERROR;
					break;
				}
				//hospital.setBusinessLicenseSrc("123456");
				hospital.setName(hosName);
				hospital.setTelephone(hosPhone);
				hospital.setAddress(hosAddr);
				hospitalSerivce.setHospitalInfo(hospital);
				status = GlobalUtil.SUCCESS;
				jsonObject.put("hospitalId", hospital.getId());
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			e.printStackTrace();
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 部门列表
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/department",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String departmentList(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String startPage = request.getParameter("startPage");
				String pageSize = request.getParameter("pageSize");
				if(StringUtils.isAnyBlank(startPage,pageSize)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,params is null");
					break;
				}
				List<Department> departments = departmentService.findByConditions(startPage,pageSize);
				JSONArray departmentList = JSONArray.fromObject(departments);
				status = GlobalUtil.SUCCESS;
				jsonObject.put("departmentList", departmentList);
				status = GlobalUtil.SUCCESS;
				}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 职位列表
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/position",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String positonList(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String startPage = request.getParameter("startPage");
				String pageSize = request.getParameter("pageSize");
				if(StringUtils.isAnyBlank(startPage,pageSize)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,params is null");
					break;
				}
				List<Position> positions = positionService.findByConditions(startPage,pageSize);
				JSONArray positionList = JSONArray.fromObject(positions);
				status = GlobalUtil.SUCCESS;
				jsonObject.put("positionList", positionList);
				}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 上传医师认证文件
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/uploadDoctorFile",method = RequestMethod.POST,produces ="text/plain;charset=UTF-8")
	public String uploadDoctorFile(@RequestParam(value="phyLic", required=false)MultipartFile phyLic,
			@RequestParam(value="phyCard", required=false)MultipartFile phyCard,HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String accountId = request.getParameter("accountId");
				if(StringUtils.isBlank(accountId)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,param is null");
					break;
				}
				String absolutePath = userService.getFilePath().get(0);
				String relativePath = userService.getFilePath().get(1);
				Doctor doctor = doctorService.getByAccountId(accountId);
				if(doctor == null){
					status = GlobalUtil.DOCTOR_ERROR;
					logger.error("doctor error ,doctor is not exist");
					break;
				}  
				String doctorId = doctor.getId();
				if(phyLic != null){
		
					String phyLicName = phyLic.getOriginalFilename();
					String[] names = phyLicName.split("\\.");
					String systemName = doctorId+"_"+"phyLic"+"."+names[1];
					File file1 = new File(absolutePath+relativePath,systemName);
					phyLic.transferTo(file1);
					doctor.setDocCertificateSrc(systemName);
				}
				
				if(phyCard != null){
					String phyCardName = phyCard.getOriginalFilename();
					String[] names = phyCardName.split("\\.");
					String systemName = doctorId+"_"+"phyCard"+"."+names[1];
					File file2 = new File(absolutePath+relativePath,systemName);
					phyCard.transferTo(file2);
					doctor.setWorkPermitSrc(systemName);
				}
				doctorService.update(doctor);
				status = GlobalUtil.SUCCESS;
				}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 上传医院认证文件
	 * @param request
	 * @return 状态码  
	 */
	@RequestMapping(value ="/uui/uploadHospitalFile",method = RequestMethod.POST,produces ="text/plain;charset=UTF-8")
	public String uploadHospitalFile(@RequestParam(value="businessLic", required=false)MultipartFile businessLic,
			@RequestParam(value="hygieneLic", required=false)MultipartFile hygieneLic,HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{  
				String hospitalId = request.getParameter("hospitalId");
				if(StringUtils.isBlank(hospitalId)){
					status = GlobalUtil.PARAM_ERROR;
					logger.error("param error ,param is null");
					break;
				}
				String absolutePath = userService.getFilePath().get(0);
				String relativePath = userService.getFilePath().get(1);
				Hospital hospital = hospitalSerivce.get(hospitalId);
				if(hospital == null){
					status = GlobalUtil.HOSPITAL_ERROR;
					logger.error("hospital error ,hospital is not exist");
					break;
				}
				if(businessLic != null){
					String businessLicName = businessLic.getOriginalFilename();
					String[] names = businessLicName.split("\\.");
					String systemName = hospitalId+"_"+"businessLic"+"."+names[1];
					File file1 = new File(absolutePath+relativePath,systemName);
					businessLic.transferTo(file1);
					hospital.setBusinessLicenseSrc(systemName);
				}
				if(hygieneLic != null){
					String hygieneLicName = hygieneLic.getOriginalFilename();
					String[] names = hygieneLicName.split("\\.");
					String systemName = hospitalId+"_"+"hygieneLic"+"."+names[1];
					File file2 = new File(absolutePath+relativePath,systemName);
					hygieneLic.transferTo(file2);
					hospital.setHealthPermitsSrc(systemName);
				}
				hospitalSerivce.update(hospital);
				status = GlobalUtil.SUCCESS;
				}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 添加好友分组
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uui/addGroup",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String addGroup(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String status = GlobalUtil.SUCCESS;
		try{
			do{
				String groupName = URLDecoder.decode(request.getParameter("groupName"),"UTF-8");
				String userId = request.getParameter("userId");
				//String groupId = request.getParameter("groupId");
				if(StringUtils.isAnyBlank(groupName,userId)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				GroupInfo groupInfo = new GroupInfo();
				groupInfo.setName(groupName);
				groupInfo.setUserId(userId);
				int flag = friendManageService.addGroup(groupName,userId);
				if(flag == 0){
					status = GlobalUtil.GROUP_EXIST;
					break;
				}
				if(flag == 2){
					status = GlobalUtil.CRUD_ERROR;
					break;
				}
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 删除好友分组
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uui/deleteGroup",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String deleteGroup(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String status = GlobalUtil.SUCCESS;
		try{
			do{
				String groupId = request.getParameter("groupId");
				String userId = request.getParameter("userId");
				//String groupId = request.getParameter("groupId");
				if(StringUtils.isAnyBlank(groupId,userId)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				int flag = friendManageService.deleteGroup(userId,groupId);
				if(flag == 2){
					status = GlobalUtil.CRUD_ERROR;
					break;
				}
				if(flag == 0){
					status = GlobalUtil.GROUP_ERROR;
					break;
				}
				if(flag == 3){
					status = GlobalUtil.DEFAULT_GROUP_ERROR;
					break;
				}
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 修改好友分组
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uui/modifyGroup",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String modifyGroup(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String status = GlobalUtil.SUCCESS;
		try{
			do{
				String groupName = request.getParameter("groupName");
				String userId = request.getParameter("userId");
				String newName = request.getParameter("newName");
				if(StringUtils.isAnyBlank(groupName,userId,newName)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				int flag = friendManageService.modifyGroup(groupName,newName,userId);
				if(flag == 2){
					status = GlobalUtil.CRUD_ERROR;
					break;
				}
				if(flag == 0){
					status = GlobalUtil.GROUP_ERROR;
					break;
				}
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
	/**
	 * 获取好友分组列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uui/getGroupList",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String getGroupList(HttpServletRequest request){

		JSONObject jsonObject = new JSONObject();
		String status = GlobalUtil.SUCCESS;
		try{
			do{
				String userId = request.getParameter("userId");
				if(StringUtils.isAnyBlank(userId)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				List<Map<String, Object>> list = friendManageService.getGroupList(userId);
				if(list!=null){
					for(Map<String, Object> map:list){
						String groupId = (String) map.get("groupId");
						List<Map<String, Object>> result = groupMembersService.getByConditons(groupId, null, null,1);
						int size = result.size();
						map.put("size", size);
					}
					JSONArray array = JSONArray.fromObject(list);
					jsonObject.put("groupList", array);
				}
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}

	/**
	 * 获取好友列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uui/getFriendList",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String getFriendList(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String status = GlobalUtil.SUCCESS;
		try{
			do{
				String userId = request.getParameter("userId");
				String systemType = request.getParameter("systemType");
				String registerType = request.getParameter("registerType");
				if(StringUtils.isAnyBlank(userId)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				List<Map<String, Object>> result = friendManageService.getFriendList(userId,systemType,registerType);
				Map<String, List> mapList = new HashMap<String, List>();
				for(Map<String, Object> map : result){
					String groupName = (String) map.get("groupName");
					List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
					Object friendType = map.get("friendType");
					int type ;
					if(friendType != null){
						type = (int)friendType ;
						if(type == 1){
				    		String friendName = (String) map.get("friendName");
					    	friendName = friendName+"医师";
					    	map.remove("friendName");
					    	map.put("friendName", friendName);
				    	}
					}
					List listFriend = mapList.get(groupName);
				    if(listFriend==null){
				    	listMap.add(map);
				    	mapList.put(groupName,listMap);
				    }else{
				    	listFriend.add(map);
				    }
				}
				jsonObject.put("friendList", mapList);
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			e.printStackTrace();
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 查找好友
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uui/getFriend",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String getFriend(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String status = GlobalUtil.SUCCESS;
		try{
			do{
				String userId = request.getParameter("userId");
				String name1 = request.getParameter("name");
				String name = null;
				if(StringUtils.isNotBlank(name1)){
					name = URLDecoder.decode(name1,"UTF-8");
				}
				
				String phone = request.getParameter("phone");
				List<Map<String, String>> result = userService.getByConditions(userId, name, phone);
				int size = result.size();
				if(size == 0){
					status = GlobalUtil.FRIEND_ERROR;
				}
				JSONArray jsonArray = new JSONArray();
				for(Map<String, String> map :result){
					if(map.get("accountId")!=null){
						jsonArray.add(map);
					}
				}
				jsonObject.put("userList", jsonArray);
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			e.printStackTrace();
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 查找好友
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uui/getFriendByGroupId",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String getFriendByGroupId(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String status = GlobalUtil.SUCCESS;
		try{
			do{
				String groupId = request.getParameter("groupId");
				List<Map<String, Object>> groupMembers = groupMembersService.getByConditons(groupId, null, null, 3);
				JSONArray jsonArray = JSONArray.fromObject(groupMembers);
				jsonObject.put("groupMembers", jsonArray);
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			e.printStackTrace();
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 添加好友
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uui/addFriend",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String addFriend(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String status = GlobalUtil.SUCCESS;
		try{
			do{
				String fromUserId = request.getParameter("fromUserId");
				String groupId = request.getParameter("groupId");
				String userId = request.getParameter("userId");
				String registerType = request.getParameter("registerType");
				if(StringUtils.isAnyBlank(userId)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				if(StringUtils.isAnyBlank(groupId)){
					List<Map<String, Object>> result= groupInfoService.getByConditons(fromUserId, null,GlobalUtil.DEFAULT_GROUP);
					int size = result.size();
					if(size == 0){
						break;
					}
					groupId = (String) result.get(0).get("groupId");
				}
				int flag = friendManageService.addFriend(groupId, userId);
				if(flag == 3 ){
					status = GlobalUtil.FRIEND_AUTH_ERROR;
					break;
				}
				if(flag == 2 ){
					status = GlobalUtil.CRUD_ERROR;
					break;
				}
				if(flag == 0){
					status = GlobalUtil.FRIEND_EXIST;
					break;
				}
				if(!StringUtils.isAnyBlank(registerType)&&registerType.equals("1")){
					List<Map<String, Object>> groupMemberList = groupMembersService.getByConditons(groupId, userId, null, 0);
					Map<String, Object> map = groupMemberList.get(0);
					String groupMebId = (String) map.get("groupMebId");
					GroupMembers members = groupMembersService.get(groupMebId);
					members.setRegisterType(Integer.valueOf(registerType));
					groupMembersService.update(members);
				}
				
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			jsonObject.put("status", status);
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 移动好友
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uui/moveFriend",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String moveFriend(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String status = GlobalUtil.SUCCESS;
		try{
			do{
				String groupId = request.getParameter("groupId");
				String fromUserId = request.getParameter("fromUserId");
				String toUserId = request.getParameter("toUserId");
				int flag = friendManageService.moveFriend(fromUserId,groupId,toUserId);
				if(flag == 2 ){
					status = GlobalUtil.CRUD_ERROR;
					break;
				}
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			jsonObject.put("status", status);
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 删除好友
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uui/deleteFriend",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String deleteFriend(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String status = GlobalUtil.SUCCESS;
		try{
			do{
				String groupId = request.getParameter("groupId");
				String fromUserId = request.getParameter("fromUserId");
				String toUserId = request.getParameter("toUserId");
				if(StringUtils.isAnyBlank(fromUserId,toUserId,groupId)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				List<Map<String, Object>> groupMembers = groupMembersService.getFriend(fromUserId, toUserId);
				int size = groupMembers.size();
				if(size == 0 | size >1){
					status = GlobalUtil.FRIEND_ERROR;
					break;
				}
				int flag = friendManageService.deleteFriend(groupId, toUserId, null);
				List<Map<String, Object>> groupList = groupInfoService.getByConditons(toUserId, null, null);
				int groupListSize = groupList.size();
				if(groupListSize != 0){
					String groupMemberId = (String) friendManageService.getFriend(toUserId, fromUserId).get("groupMemberId");
					String friendGroupId = groupMembersService.get(groupMemberId).getGroupId();
					friendManageService.deleteFriend(friendGroupId, fromUserId, null);
				}
				
				if(flag == 2){
					status = GlobalUtil.CRUD_ERROR;
					break;
				}
				if(flag == 0){
					status = GlobalUtil.FRIEND_ERROR;
					break;
				}
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			jsonObject.put("status", status);
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 检查好友是否存在
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uui/checkFriend",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String checkFriend(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String status = GlobalUtil.SUCCESS;
		try{
			do{
				String friendUserId = request.getParameter("friendUserId");
				String userId = request.getParameter("userId");
				if(StringUtils.isAnyBlank(userId,friendUserId)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				int flag = friendManageService.checkFriend(userId, friendUserId);
				if(flag == 1){
					status = GlobalUtil.FRIEND_EXIST;
					break;
				}
				if(flag == 2){
					status = GlobalUtil.CRUD_ERROR;
					break;
				}
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			jsonObject.put("status", status);
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 检查分组是否存在
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uui/checkGroup",method = {RequestMethod.GET},produces ="text/plain;charset=UTF-8")
	public String checkGroup(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		String status = "";
		try{
			do{
				String groupName = request.getParameter("groupName");
				String userId = request.getParameter("userId");
				if(StringUtils.isAnyBlank(userId,groupName)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				int flag = friendManageService.checkGroup(userId, groupName, null);
				if(flag == 1){
					status = GlobalUtil.GROUP_EXIST;
					break;
				}
				if(flag == 2){
					status = GlobalUtil.CRUD_ERROR;
					break;
				}
			}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			jsonObject.put("status", status);
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 获取系统设置
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/uui/getSysSetting",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String getSysSetting(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String userId = request.getParameter("userId");
				String type = request.getParameter("type");
				if(StringUtils.isAnyBlank(userId,type)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				List<Map<String, Object>> result = null;
				if(type!=null&&Integer.valueOf(type)==0){
					result = doctorService.getSysSetting(userId);
				}
				if(type!=null&&Integer.valueOf(type)==1){
					result = userService.getSysSetting(userId);
				}
				
				jsonObject.put("sysSettingList", result);
				}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	@RequestMapping(value ="/uui/authenticateFriend",method = RequestMethod.GET
			,produces ="text/plain;charset=UTF-8")
	public String authenticateFriend(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String fromUserId = request.getParameter("fromUserId");
				String toUserId = request.getParameter("toUserId");
				String type = request.getParameter("type");
				if(StringUtils.isAnyBlank(fromUserId,toUserId,type)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				
				}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}

	/**
	 * 添加系统设置
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/uui/SysSetting",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String SysSetting(HttpServletRequest request){
		String status = "";
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String userId = request.getParameter("userId");
				String type = request.getParameter("type");
				String reserveStatus = request.getParameter("reserveStatus");
				String reserveRemind= request.getParameter("reserveRemind");
				String pushRemind = request.getParameter("pushRemind");
				String smsRemind= request.getParameter("smsRemind");
				if(StringUtils.isAnyBlank(userId,type)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				if(type!=null&&Integer.valueOf(type)==0){
				
					DocSysSetting docSysSetting = new DocSysSetting();
					if(reserveStatus!=null){
						docSysSetting.setReserveStatus(Integer.valueOf(reserveStatus));
					}
					if(reserveRemind!=null){
						docSysSetting.setReserveRemind(Integer.valueOf(reserveRemind));
					}
					if(pushRemind!=null){
						docSysSetting.setPushRemind(Integer.valueOf(pushRemind));
					}
					if(smsRemind!=null){
						docSysSetting.setSmsRemind(Integer.valueOf(smsRemind));
					}
					docSysSetting.setUserId(userId);
					doctorService.saveSysSetting(docSysSetting);
				}
				if(type!=null&&Integer.valueOf(type)==1){
					UserSysSetting sysSetting = new UserSysSetting();
					if(reserveRemind!=null){
						sysSetting.setReserveRemind(Integer.valueOf(reserveRemind));
					}
					if(pushRemind!=null){
						sysSetting.setPushRemind(Integer.valueOf(pushRemind));
					}
					if(smsRemind!=null){
						sysSetting.setSmsRemind(Integer.valueOf(smsRemind));
					}
					sysSetting.setUserId(userId);
					userService.saveSysSetting(sysSetting);
				}
				}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 添加系统设置
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/uui/modifySysSetting",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String modifySysSetting(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String userId = request.getParameter("userId");
				String type = request.getParameter("type");
				String reserveStatus = request.getParameter("reserveStatus");
				String reserveRemind= request.getParameter("reserveRemind");
				String pushRemind = request.getParameter("pushRemind");
				String smsRemind= request.getParameter("smsRemind");
				if(StringUtils.isAnyBlank(userId,type)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				if(type!=null&&Integer.valueOf(type)==0){
				
					DocSysSetting docSysSetting = doctorService.getSysSettingByCondition(userId);
					if(reserveStatus!=null){
						docSysSetting.setReserveStatus(Integer.valueOf(reserveStatus));
					}
					if(reserveRemind!=null){
						docSysSetting.setReserveRemind(Integer.valueOf(reserveRemind));
					}
					if(pushRemind!=null){
						docSysSetting.setPushRemind(Integer.valueOf(pushRemind));
					}
					if(smsRemind!=null){
						docSysSetting.setSmsRemind(Integer.valueOf(smsRemind));
					}
					docSysSetting.setUserId(userId);
					doctorService.modifySysSetting(docSysSetting);
				}
				if(type!=null&&Integer.valueOf(type)==1){
					UserSysSetting sysSetting = userService.getSysSettingByCondition(userId);
					if(reserveRemind!=null){
						sysSetting.setReserveRemind(Integer.valueOf(reserveRemind));
					}
					if(pushRemind!=null){
						sysSetting.setPushRemind(Integer.valueOf(pushRemind));
					}
					if(smsRemind!=null){
						sysSetting.setSmsRemind(Integer.valueOf(smsRemind));
					}
					sysSetting.setUserId(userId);
					userService.modifySysSetting(sysSetting);
				}
				}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 获取聊天Token
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/uui/getChatToken",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String getChatToken(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String userId = request.getParameter("userId");
				String userName = request.getParameter("userName");
				if(userName != null){
					userName = URLDecoder.decode(userName,"UTF-8");
				}
				String portraitUri = request.getParameter("portraitUri");
				if(StringUtils.isAnyBlank(userId,userName)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				String token = rongCloudService.getToken(userId, userName, portraitUri);
				JSONObject object = JSONObject.fromObject(token);
				String realToken = (String) object.get("token");
				jsonObject.put("token", realToken);
				}while(false);
		}catch(Exception e){
			status = GlobalUtil.SYS_ERROR;
			e.printStackTrace();
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 好友验证消息列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/uui/getAuthFriendMessage",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String getAuthFriendMessage(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String userId = request.getParameter("userId");
				if(StringUtils.isAnyBlank(userId)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				List<Map<String, Object>> result = groupMembersService.getByConditons(null, userId, null,0);
				JSONArray array = JSONArray.fromObject(result);
				jsonObject.put("authMessageList", array);
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 好友验证
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/uui/authFriend",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String authFriend(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject =new JSONObject();
		try{
			do{
				String fromUserId = request.getParameter("fromUserId");
				String fromUserName = request.getParameter("fromUserName");
				String groupId = request.getParameter("groupId");
				String toUserId = request.getParameter("toUserId");
				String messageType = request.getParameter("messageType");
				String message = request.getParameter("message");
				String extraMessage = request.getParameter("extraMessage");
				if(StringUtils.isAnyBlank(fromUserId,toUserId,messageType,fromUserName)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				int type = Integer.valueOf(messageType);
				if(StringUtils.isBlank(extraMessage)){
					extraMessage = "\"\"";
				}
				if(StringUtils.isBlank(message)){
					message = "\"\"";
				}
				//String content = "{"+"operation:"+" "+","+"sourceUserId:"+fromUserId+","+"targetUserId:"+toUserId+","+"message:"+message+","+"extra:"+extraMessage+"}";
				String content = "{"+"\"content\":"+extraMessage+","+"\"extra\":"+message+"}";
				if(type == 0){
					content = "{"+"\"content\":"+"我是"+extraMessage+","+"\"extra\":"+message+"}";
					rongCloudService.sendSystemMessage("好友请求", toUserId, "RC:TxtMsg", content);
				}
				if(type ==1){
					List<Map<String,Object>>  result = groupMembersService.getFriend(toUserId, fromUserId);
					int size = result.size();
					if(size == 0){
						break;
					}
					Map<String, Object> friendInfo = result.get(0);
					String groupMemberId = (String) friendInfo.get("groupMemberId");
					if(message.equals("refuse")){
						message = "拒绝";
						groupMembersService.delete(groupMemberId);
					}
					if(message.equals("agree")){
						GroupMembers groupMembers = groupMembersService.get(groupMemberId);
						groupMembers.setStatus(1);
						groupMembersService.update(groupMembers);
						if(StringUtils.isAnyBlank(groupId)){
							List<Map<String, Object>> friendList= groupInfoService.getByConditons(fromUserId, null, GlobalUtil.DEFAULT_GROUP);
							int friendSize = friendList.size();
							if(friendSize == 0){
								break;
							}
							groupId = (String) friendList.get(0).get("groupId");
						}
						friendManageService.addFriend(groupId, toUserId);
						Map<String, Object> friendMap = friendManageService.getFriend(fromUserId, toUserId);
						String myGroupMemberId=(String) friendMap.get("groupMemberId");
						GroupMembers myGroupMember = groupMembersService.get(myGroupMemberId);
						myGroupMember.setStatus(1);
						groupMembersService.update(myGroupMember);
						message = "同意";
					}
					content = "{"+"\"content\":"+fromUserName+message+"您的请求!"+","+"\"extra\":"+message+"}";
					rongCloudService.sendSystemMessage("验证结果", toUserId, "RC:TxtMsg", content);
				}
				}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
			logger.error("error:"+e.toString());
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 今日工作
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/uui/todayWorks",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String todayWorks(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject = new JSONObject();
		try{
			do{
				String accountId = request.getParameter("accountId");
				String userId = request.getParameter("userId");
				if(StringUtils.isAnyBlank(accountId,userId)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				Doctor doctor = doctorService.getByAccountId(accountId);
				if(doctor==null){
					status = GlobalUtil.DOCTOR_ERROR;
					break;
				}
				String doctorId = doctor.getId();
				String startDate = DateUtils.format2String(new Date(), GlobalUtil.DATE_PATTERN);
				List<Map<String, String>> reserves = reserveInfoService.findReserveListByDate(doctorId, "1", "100", startDate);
				int reserveCount = 0;
				int firendCount = 0;
				int returnCount = 0;
				int patientsCount = 0;
				if(reserves != null){
					reserveCount = reserves.size();
				}
				List<Map<String, Object>> friends = friendManageService.getFriendList(userId, "1", null);
				if(friends!=null){
					patientsCount = friends.size();
					for(Map<String, Object> map:friends){
						Integer friendStatus = (Integer) map.get("registerType");
						if(friendStatus != null && friendStatus==1){
							firendCount = firendCount+1;
						}
					}
				}
				List returns = returnVisitService.getRebackListByDate(accountId, 0, 100, new Date(), null, "0");
				if(returns!=null){
					returnCount = returns.size();
				}
				jsonObject.put("returnCount", returnCount);
				jsonObject.put("reserveCount", reserveCount);
				jsonObject.put("patientsCount", patientsCount);
				jsonObject.put("firendCount", firendCount);
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	/**
	 * 患者
	 * @param request
	 * @return
	 */
	@RequestMapping(value ="/uui/patientPlans",method = RequestMethod.GET,produces ="text/plain;charset=UTF-8")
	public String patientPlans(HttpServletRequest request){
		String status = GlobalUtil.SUCCESS;
		JSONObject jsonObject = new JSONObject();
		try{
			do{
				String accountId = request.getParameter("accountId");
				String userId = request.getParameter("userId");
				if(StringUtils.isAnyBlank(accountId,userId)){
					status = GlobalUtil.PARAM_ERROR;
					break;
				}
				User user = userService.getByAccountId(accountId);
				if(user==null){
					status = GlobalUtil.USER_ERROR;
					break;
				}
				String startDate = DateUtils.format2String(new Date(), GlobalUtil.DATE_PATTERN);
				List<Map<String, String>> reserves = reserveInfoService.findReserveListUser(accountId, "1", "100", startDate, null);
				int reserveCount = 0;
				if(reserves != null){
					reserveCount = reserves.size();
				}
				int doctorConunt = 0;
				Map<String ,String> doctors = new HashMap<>();
				for(Map<String, String> map : reserves){
					String doctorId = map.get("doctorId");
					if(doctorId!=null){
						doctors.put(doctorId, map.get("realName"));
					}
				}
				doctorConunt = doctors.size();
				jsonObject.put("reserveCount", reserveCount);
				jsonObject.put("doctorConunt", doctorConunt);
				
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			status = GlobalUtil.SYS_ERROR;
		}
		jsonObject.put("status", status);
		return json(jsonObject);
	}
	
}

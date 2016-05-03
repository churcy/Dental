package com.tinytree.handler;

import com.tinytree.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description:消息提醒异步处理程序
 * @ClassName: RemindHandler
 * @Author：tangyang
 * @Date 2016-2-2
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Component
public class RemindHandler extends MessageHandler{
	private static final Logger logger = LoggerFactory.getLogger(RemindHandler.class);
	
	@Autowired
	private SMSService smsService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private DoctorService doctorService;

	@Override
	public void execute(List<Object> list) throws Exception {
		// TODO Auto-generated method stub
		
	}

/*	@Override
	public void execute(List<Object> list) throws Exception {
		if(list == null || list.isEmpty()){
			return;
		}
		
		for(Object obj:list){
			if(!(obj instanceof CacheObject)){
				continue;
			}
			
			CacheObject cacheObject = (CacheObject) obj;
			switch(cacheObject.getType()){
				case RemindBean.DOCTOR_RESERVE_REMIND:
					doDoctorReserveRemind(cacheObject);
					break;
				case RemindBean.DOCTOR_STROKE_REMIND:
					doDoctorStrokeRemind(cacheObject);
					break;
				case RemindBean.PATIENT_RESERVE_REMIND:
					doPatientReserveRemind(cacheObject);
					break;
				default:
					break;
			}
		}
	}*/
	
	/*private void doDoctorReserveRemind(CacheObject cacheObject) throws Exception{
		if(cacheObject == null){
			return;
		}
		
		Object obj = cacheObject.getValue();
		if(!(obj instanceof RemindBean)){
			return;
		}
		
		RemindBean remindBean = (RemindBean) obj;
		smsService.doctorReserveSMS(remindBean.getMobile(), (Integer) remindBean.getParams()[0]);
		try {
			jPushService.doctorReservePush(remindBean.getAccountId(), (Integer) remindBean.getParams()[0]);
		} catch (APIConnectionException e) {
			logger.error(e.toString());
		} catch (APIRequestException e) {
			logger.error(e.toString());
		}
	}
	
	private void doDoctorStrokeRemind(CacheObject cacheObject){
		if(cacheObject == null){
			return;
		}
		
		Stroke stroke = (Stroke) cacheObject.getValue();
		User user = userService.getByAccountId(stroke.getAccountId());
		smsService.doctorStrokeSMS(user.getMobile(), DateUtils.format2String(stroke.getTimeByday(), GlobalUtil.TIME_PATTERN), stroke.getSubject());
		try {
			jPushService.doctotStrokePush(stroke.getAccountId(), DateUtils.format2String(stroke.getTimeByday(), GlobalUtil.TIME_PATTERN), stroke.getSubject());
		} catch (APIConnectionException e) {
			logger.error(e.toString());
		} catch (APIRequestException e) {
			logger.error(e.toString());
		}
	}
	
	private void doPatientReserveRemind(CacheObject cacheObject) throws Exception{
		if(cacheObject == null){
			return;
		}
		
		ReserveInfo reserveInfo = (ReserveInfo) cacheObject.getValue();
		User user = userService.getByAccountId(reserveInfo.getUserId());
		User doctor = userService.findUserByDoctorId(reserveInfo.getDoctorId());
		smsService.patientReserveSMS(user.getMobile(), DateUtils.format2String(reserveInfo.getStartTime(), GlobalUtil.TIME_PATTERN), doctor.getRealName());
		try {
			jPushService.patientReservePush(reserveInfo.getUserId(), DateUtils.format2String(reserveInfo.getStartTime(), GlobalUtil.TIME_PATTERN), doctor.getRealName());
		} catch (APIConnectionException e) {
			logger.error(e.toString());
		} catch (APIRequestException e) {
			logger.error(e.toString());
		}
	}
*/
}

package com.tinytree.job;

import com.tinytree.bean.RemindBean;
import com.tinytree.cache.CacheManager;
import com.tinytree.cache.CacheObject;
import com.tinytree.cache.CachePool;
import com.tinytree.cache.CacheTimeoutListener;
import com.tinytree.entity.ReserveInfo;
import com.tinytree.entity.Stroke;
import com.tinytree.service.ReserveInfoService;
import com.tinytree.service.StrokeService;
import com.tinytree.util.DateUtils;
import com.tinytree.util.Message;
import com.tinytree.util.MessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:提醒功能定时任务
 * @ClassName: RemindJob
 * @Author：tangyang
 * @Date 2016-2-2
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Component
public class RemindJob implements CacheTimeoutListener{

	private static final Logger logger = LoggerFactory.getLogger(RemindJob.class);
	
	private static final String REMIND_NAME_SPACE = "remind";
	
	private static boolean isInit = false;
	
	private static CachePool cachePool = null;
	
	@Autowired
	private ReserveInfoService reserveInfoService;
	
	@Autowired
	private StrokeService strokeService;
	
	public void windUp() {
		logger.info("RemindJob start");
		if(!isInit){
			logger.info("RemindJob init");
			init();
			isInit = true;
		}
		
		Date today = new Date();
		List<Map<String, Object>> list = reserveInfoService.findReserveForDoctorByDate(today);
		if(list != null && !list.isEmpty()){
			sendForDoctorReserve(list);
		}
		
		List<ReserveInfo> reserves = reserveInfoService.findReserveListByDate2(today);
		if(reserves != null && !reserves.isEmpty()){
			sendForPatientReserve(reserves);
		}
		
		List<Stroke> strokes = strokeService.getStrokes(today);
		if(strokes != null && !strokes.isEmpty()){
			sendForDoctorStroke(strokes);
		}
		
		logger.info("RemindJob end");
	}
	
	private void init(){
		cachePool = CacheManager.getInstance().createCachePool(REMIND_NAME_SPACE);
		cachePool.addListener(this);
	}
	
	private void sendForDoctorReserve(List<Map<String, Object>> list){
		for(Map<String,Object> map : list){
			RemindBean remindBean = new RemindBean();
			remindBean.setMobile((String) map.get("mobile"));
			remindBean.setAccountId((String) map.get("accountId"));
			remindBean.setType(RemindBean.DOCTOR_RESERVE_REMIND);
			Object[] params = new Object[]{(Integer) map.get("count")};
			remindBean.setParams(params);
			remindBean.setRemindType(RemindBean.REMIND_TYPE_ALL);
			CacheObject object = new CacheObject(remindBean, 15*60);
			object.setType(RemindBean.DOCTOR_RESERVE_REMIND);
			cachePool.put(object);
		}
	}
	
	private void sendForPatientReserve(List<ReserveInfo> list){
		for(ReserveInfo reserveInfo:list){
			Date startTime = reserveInfo.getStartTime();
			CacheObject object = new CacheObject(reserveInfo,(DateUtils.currentSecond2Now(startTime)-60*60));
			object.setType(RemindBean.PATIENT_RESERVE_REMIND);
			cachePool.put(object);
		}
	}
	
	private void sendForDoctorStroke(List<Stroke> strokes){
		for(Stroke stroke:strokes){
			Date time = stroke.getTimeByday();
			CacheObject object = new CacheObject(stroke,(DateUtils.currentSecond2Now(time)-15*60));
			object.setType(RemindBean.DOCTOR_STROKE_REMIND);
			cachePool.put(object);
		}
	}

	@Override
	public void callback(CacheObject source) {
		Message message = new Message(Message.REMIND, source);
		MessageQueue.getInstance().add(message);
	}
}

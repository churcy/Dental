package com.tinytree.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.tinytree.bean.Pager;
import com.tinytree.bean.PagerEx;
import com.tinytree.dao.ReserveInfoDao;
import com.tinytree.entity.ReserveInfo;
import com.kungfu.dental.service.ReserveInfoService;
import com.kungfu.dental.util.DateUtils;
import com.kungfu.dental.util.GlobalUtil;

/**
 * @Description:预约信息服务实现类
 * @ClassName: ReserveInfoServiceImpl
 * @Author：tangyang
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("reserveInfoService")
public class ReserveInfoServiceImpl extends BaseServiceImpl<ReserveInfo, String> implements ReserveInfoService{

	@Autowired
	private ReserveInfoDao reserveInfoDao;
	
	@Autowired
	public void setBaseDao(ReserveInfoDao reserveInfoDao) {
		super.setBaseDao(reserveInfoDao);
	}

	@Override
	public Integer getReserveInfoCount(String doctorId, Date startTime) {
		return reserveInfoDao.countByDoctorId(doctorId, DateUtils.format2String(startTime, GlobalUtil.DATE_PATTERN));
		//DateUtils.format2String(startTime, GlobalUtil.DATE_PATTERN)
	}

	@Override
	public List<Map<String, String>> findReserveList(String doctorId,
			String startPage, String pageSize, String startDate,
			String endDate) {
		if(startPage!=null&pageSize!=null){
			int pagerNumber = Integer.valueOf(startPage);
			int pageSizeE = Integer.valueOf(pageSize);
			PageHelper.startPage(pagerNumber,pageSizeE);
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("doctorId", doctorId);
		if(startDate!=null){
			map.put("startDate", startDate);
		}
		List<Map<String, String>> result = reserveInfoDao.findReserveList(map);
		return result;
	}
	@Override
	public List<Map<String, String>> findReserveListUser(String accountId,
			String startPage, String pageSize, String startDate, String endDate) {
		// TODO Auto-generated method stub
		int pagerNumber = Integer.valueOf(startPage);
		int pageSizeE = Integer.valueOf(pageSize);
		PageHelper.startPage(pagerNumber,pageSizeE);
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountId", accountId);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		List<Map<String, String>> result = reserveInfoDao.findReserveListUser(map);
		return result;
	}

	@Override
	public Map<String, Object> findReserveDetail(String reserveId) {
		Map<String, Object> result = reserveInfoDao.findReserveDetail(reserveId);
		return result;
	}

	@Override
	public List<Map<String, String>> findReserveInfos(String doctorId, Date startTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("doctorId", doctorId);
		map.put("startDate", DateUtils.format2String(startTime, GlobalUtil.DATE_PATTERN));
		List<Map<String, String>> result = reserveInfoDao.findReserveListByDate(map);
		return result;
	}

	@Override
	public List<ReserveInfo> findReserveList(String doctorId, Date startTime,
			Date endTime) {
		return reserveInfoDao.findReserveListByDoctor(doctorId, DateUtils.format2String(startTime, "yyyy-MM-dd"), DateUtils.format2String(endTime, "yyyy-MM-dd"));
		//return reserveInfoDao.findReserveListByDoctor(doctorId, startTime,endTime);
	}

	@Override
	public Map<String, Map<String,String>> castCountByWeek(List<ReserveInfo> reserveInfos) {
		if(reserveInfos == null || reserveInfos.isEmpty()){
			return null;
		}
		
		Map<String,Map<String,String>> map = new HashMap<>();
		for(ReserveInfo reserveInfo:reserveInfos){
			Integer hour = DateUtils.getFiled(reserveInfo.getStartTime(),Calendar.HOUR_OF_DAY);
			Map<String,String> subMap = map.get(String.valueOf(hour));
			if(subMap == null){
				subMap = new HashMap<>();
				subMap.put(String.valueOf(DateUtils.getWeekDay(reserveInfo.getStartTime())), 1+"");
			}
			else{
				String count = subMap.get(String.valueOf(DateUtils.getWeekDay(reserveInfo.getStartTime())));
				if(StringUtils.isBlank(count)){
					subMap.put(String.valueOf(DateUtils.getWeekDay(reserveInfo.getStartTime())), 1+"");
				}else{
					subMap.put(String.valueOf(DateUtils.getWeekDay(reserveInfo.getStartTime())),(Integer.valueOf(count)+1)+"");
				}
			}
			map.put(String.valueOf(hour), subMap);
		}
		return map;
	}

	@Override
	public Map<String, String> castCountByMonth(List<ReserveInfo> reserveInfos) {
		if(reserveInfos == null || reserveInfos.isEmpty()){
			return null;
		}
		
		Map<String,String> map = new HashMap<>();
		for(ReserveInfo reserveInfo:reserveInfos){
			Integer day = DateUtils.getFiled(reserveInfo.getStartTime(),Calendar.DAY_OF_MONTH);
			String count = map.get(String.valueOf(day));
			if(StringUtils.isBlank(count)){
				map.put(String.valueOf(day), 1+"");
			}else{
				map.put(String.valueOf(day), (Integer.valueOf(count)+1)+"");
			}
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> findReserveForDoctorByDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> findReserveListByDate(String doctorId,
			String startPage, String pageSize, String startDate) {
		int pagerNumber = Integer.valueOf(startPage);
		int pageSizeE = Integer.valueOf(pageSize);
		PageHelper.startPage(pagerNumber,pageSizeE);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("doctorId", doctorId);
		if(startDate!=null){
			map.put("startDate", startDate);
		}

		List<Map<String, String>> result = reserveInfoDao.findReserveListByDate(map);
		return result;
	}

	@Override
	public List<ReserveInfo> findReserveListByDate2(Date today) {
		// TODO Auto-generated method stub
		return null;
	}

}

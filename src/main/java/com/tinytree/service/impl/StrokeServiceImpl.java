package com.tinytree.service.impl;

import com.tinytree.bean.PagerEx;
import com.tinytree.bean.PagerParam;
import com.tinytree.bean.PagerParam.ConditionType;
import com.tinytree.dao.StrokeDao;
import com.tinytree.entity.Stroke;
import com.tinytree.service.StrokeService;
import com.tinytree.util.DateUtils;
import com.tinytree.util.GlobalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description:日程服务实现类
 * @ClassName: StrokeServiceImpl
 * @Author：tangyang
 * @Date 2016-1-25
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("strokeService")
public class StrokeServiceImpl extends BaseServiceImpl<Stroke, String> implements StrokeService {
	@Autowired
	private StrokeDao strokeDao;
	
	@Autowired
	public void setBaseDao(StrokeDao strokeDao) {
		super.setBaseDao(strokeDao);
	}

	@Override
	@Transactional
	public void saveStroke(Stroke stroke,Date date) {
		setStroke(stroke,date);
		super.save(stroke);
	}
	
	@Transactional
	public void updateStroke(Stroke stroke){
		setStroke(stroke,stroke.getTime());
		super.update(stroke);
	}
	
	private void setStroke(Stroke stroke,Date date){
		if(stroke == null){
			return;
		}
		
		switch(stroke.getRepeat()){
			case 0:{
				stroke.setTime(date);
				stroke.setTimeByday(date);
			}
				break;
			case 1:{
				stroke.setTimeByday(date);
			}
				break;
			case 2:{
				stroke.setTimeByday(date);
				stroke.setTimeByweek(DateUtils.getWeekDay(date));
			}
				break;
			case 3:{
				stroke.setTimeByday(date);
				stroke.setTimeBymonth(DateUtils.getFiled(date,Calendar.DAY_OF_MONTH));
			}
				break;
			case 4:{
				stroke.setTimeByday(date);
				stroke.setTimeBymonth(DateUtils.getFiled(date,Calendar.DAY_OF_MONTH));
				stroke.setTimeByyear(DateUtils.getFiled(date,Calendar.MONTH)+1);
			}
				break;
			default:
				stroke.setTime(date);
				break;
		}
		
		stroke.setHour(DateUtils.getFiled(date,Calendar.HOUR_OF_DAY));
	}

	@Override
	public List<Stroke> getStrokes(String accountId, Date date) throws Exception {
		
		List<Stroke> fixedStrokes = getFixedStrokes(accountId, date);
		List<Stroke> dayStrokes = getStrokesDay(accountId);
		List<Stroke> weekStrokes = getStrokeWeek(accountId, date);
		List<Stroke> monthStrokes = getStrokeMonth(accountId, date);
		List<Stroke> yearStrokes = getStrokeYear(accountId, date);
		return mergeList(fixedStrokes,dayStrokes,weekStrokes,monthStrokes,yearStrokes);
	}
	
	@Override
	public List<Stroke> getFixedStrokes(String accountId, Date date) throws Exception {
		if(StringUtils.isBlank(accountId) || date == null){
			return null;
		}
		
		Map map = new HashMap<>();
		map.put("accountId", accountId);
		map.put("date", DateUtils.format2String(date, GlobalUtil.DATE_PATTERN));
		return strokeDao.getFixedStrokes(map);
	}
	
	private List<Stroke> mergeList(List<Stroke> ...strokes){
		if(strokes == null || strokes.length == 0){
			return null;
		}
		
		List<Stroke> list = new ArrayList<>();
		for(List<Stroke> strokeList:strokes){
			if(strokeList != null && !strokeList.isEmpty()){
				list.addAll(strokeList);
			}
		}
		
		return list;
	}
	
	private List<Stroke> getStrokeYear(String accountId,Date date) throws Exception{
		if(StringUtils.isBlank(accountId) || date == null){
			return null;
		}
		
		Integer day = DateUtils.getFiled(date, Calendar.DAY_OF_MONTH);
		Integer month = DateUtils.getFiled(date, Calendar.MONTH)+1;
		
		PagerEx page = new PagerEx();
		List<PagerParam> params = new ArrayList<>();
		PagerParam accountParam = new PagerParam();
		accountParam.setParam(ConditionType.equal, "account_id", accountId);
		params.add(accountParam);
		
		PagerParam repeatParam = new PagerParam();
		repeatParam.setParam(ConditionType.equal, "repeat_type", 4);
		params.add(repeatParam);
		
		PagerParam monthParam = new PagerParam();
		monthParam.setParam(ConditionType.equal, "time_bymonth", day);
		params.add(monthParam);
		
		PagerParam yearParam = new PagerParam();
		yearParam.setParam(ConditionType.equal, "time_byyear", month);
		params.add(yearParam);
		
		page.setParams(params);
		page.setPageSize(1000);
		
		page = super.findByPager(page);
		
		return page.getList();
	}
	
	private List<Stroke> getStrokeMonth(String accountId,Date date) throws Exception{
		if(StringUtils.isBlank(accountId) || date == null){
			return null;
		}
		
		Integer day = DateUtils.getFiled(date, Calendar.DAY_OF_MONTH);
		
		PagerEx page = new PagerEx();
		List<PagerParam> params = new ArrayList<>();
		PagerParam accountParam = new PagerParam();
		accountParam.setParam(ConditionType.equal, "account_id", accountId);
		params.add(accountParam);
		
		PagerParam repeatParam = new PagerParam();
		repeatParam.setParam(ConditionType.equal, "repeat_type", 3);
		params.add(repeatParam);
		
		PagerParam monthParam = new PagerParam();
		monthParam.setParam(ConditionType.equal, "time_bymonth", day);
		params.add(monthParam);
		
		page.setParams(params);
		page.setPageSize(1000);
		
		page = super.findByPager(page);
		
		return page.getList();
	}
	
	private List<Stroke> getStrokeWeek(String accountId,Date date) throws Exception{
		if(StringUtils.isBlank(accountId) || date == null){
			return null;
		}
		
		Integer week = DateUtils.getWeekDay(date);
		PagerEx page = new PagerEx();
		List<PagerParam> params = new ArrayList<>();
		PagerParam accountParam = new PagerParam();
		accountParam.setParam(ConditionType.equal, "account_id", accountId);
		params.add(accountParam);
		
		PagerParam repeatParam = new PagerParam();
		repeatParam.setParam(ConditionType.equal, "repeat_type", 2);
		params.add(repeatParam);
		
		PagerParam weekParam = new PagerParam();
		weekParam.setParam(ConditionType.equal, "time_byweek", week);
		params.add(weekParam);
		
		page.setParams(params);
		page.setPageSize(1000);
		
		page = super.findByPager(page);
		
		return page.getList();
	}
	
	private List<Stroke> getStrokesDay(String accountId) throws Exception{
		PagerEx page = new PagerEx();
		List<PagerParam> params = new ArrayList<>();
		PagerParam accountParam = new PagerParam();
		accountParam.setParam(ConditionType.equal, "account_id", accountId);
		params.add(accountParam);
		
		PagerParam repeatParam = new PagerParam();
		repeatParam.setParam(ConditionType.equal, "repeat_type", 1);
		params.add(repeatParam);
		
		page.setParams(params);
		page.setPageSize(1000);
		
		page = super.findByPager(page);
		
		return page.getList();
	}

	@Override
	public List<Stroke> getStrokes(String accountId) {
		
		return strokeDao.getStrokes(accountId);
	}

	@Override
	public Map<String, Map<String,String>> castCountByWeek(List<Stroke> strokes,
			Date startTime, Date endTime) {
		if(strokes == null || strokes.isEmpty() || startTime == null || endTime == null){
			return null;
		}
		
		Map<String,Map<String,String>> map = new HashMap<>();
		for(Stroke stroke:strokes){
			if(!checkStroke(stroke, startTime, endTime)){
				continue;
			}
			
			Integer hour = stroke.getHour();
			Map<String,String> subMap = map.get(String.valueOf(hour));
			if(subMap == null){
				subMap = new HashMap<>();
				if(stroke.getRepeat() == 1){
					for(int week:DateUtils.WEEK_DAYS){
						subMap.put(String.valueOf(week), 1+"");
					}
				}else{
					subMap.put(String.valueOf(stroke.getTimeByweek()), 1+"");
				}
				
				map.put(String.valueOf(hour), subMap);
			}else{
				if(stroke.getRepeat() == 1){
					for(int week:DateUtils.WEEK_DAYS){
						String count = subMap.get(String.valueOf(week));
						if(StringUtils.isBlank(count)){
							subMap.put(String.valueOf(week), 1+"");
						}else{
							subMap.put(String.valueOf(week),(Integer.valueOf(count)+1)+"");
						}
					}
				}else{
					String count = subMap.get(String.valueOf(stroke.getTimeByweek()));
					if(StringUtils.isBlank(count)){
						subMap.put(String.valueOf(stroke.getTimeByweek()), 1+"");
					}else{
						subMap.put(String.valueOf(stroke.getTimeByweek()),(Integer.valueOf(count)+1)+"");
					}
				}
			}
			
		}
		
		return map;
	}
	
	@Override
	public Map<String, String> castCountByMonth(
			List<Stroke> strokes, Date startTime, Date endTime) {
		if(strokes == null || strokes.isEmpty() || startTime == null || endTime == null){
			return null;
		}
		
		Map<String,String> map = new HashMap<>();
		for(Stroke stroke:strokes){
			if(!checkStroke(stroke, startTime, endTime)){
				continue;
			}
			
			if(stroke.getRepeat() == 1){
				for(int i = DateUtils.getFiled(startTime, Calendar.DAY_OF_MONTH);i<DateUtils.getFiled(endTime, Calendar.DAY_OF_MONTH);i++){
					String count = map.get(String.valueOf(i));
					if(StringUtils.isBlank(count)){
						map.put(String.valueOf(i), 1+"");
					}else{
						map.put(String.valueOf(i),(Integer.valueOf(count)+1)+"");
					}
				}
			}else if(stroke.getRepeat() == 2){
				for(int i = DateUtils.getFiled(startTime, Calendar.DAY_OF_MONTH);i<DateUtils.getFiled(endTime, Calendar.DAY_OF_MONTH);i++){
					
					GregorianCalendar cal = new GregorianCalendar();
					cal.setTime(startTime);
					cal.set(Calendar.DAY_OF_MONTH, i);
					if(!DateUtils.checkWeek(cal.getTime(), stroke.getTimeByweek())){
						continue;
					}
					
					String count = map.get(String.valueOf(i));
					if(StringUtils.isBlank(count)){
						map.put(String.valueOf(i), 1+"");
					}else{
						map.put(String.valueOf(i),(Integer.valueOf(count)+1)+"");
					}
				}
			}else{
				String count = map.get(String.valueOf(getDay(stroke)));
				if(StringUtils.isBlank(count)){
					map.put(String.valueOf(getDay(stroke)), 1+"");
				}else{
					map.put(String.valueOf(getDay(stroke)), (Integer.valueOf(count)+1)+"");
				}
			}
			
		}
		return map;
	}
	
	private Integer getDay(Stroke stroke){
		switch(stroke.getRepeat()){
			case 0:return DateUtils.getFiled(stroke.getTime(), Calendar.DAY_OF_MONTH);
			case 3:
			case 4:return stroke.getTimeBymonth();
		}
			
		return null;
	}
	
	private boolean checkStroke(Stroke stroke,Date startDate,Date endDate){
		boolean flag = false;
		switch(stroke.getRepeat()){
			case 0:{
				Date date = stroke.getTime();
				if(startDate.before(date)&&endDate.after(date)){
					stroke.setTimeByweek(DateUtils.getWeekDay(date));
					stroke.setTimeBymonth(DateUtils.getFiled(date, Calendar.DAY_OF_MONTH));
					flag = true;
				}
			}
				break;
			case 1:
			case 2:{
				flag = true;
			}
				break;
			case 3:{
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(startDate);
				cal.set(Calendar.DAY_OF_MONTH, stroke.getTimeBymonth());
				Date tmpDate = cal.getTime();
				if(startDate.before(tmpDate)&&endDate.after(tmpDate)){
					flag = true;
					stroke.setTimeByweek(DateUtils.getWeekDay(tmpDate));
				}else{
					flag = false;
				}
			}
				break;
			case 4:{
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(startDate);
				cal.set(Calendar.DAY_OF_MONTH, stroke.getTimeBymonth());
				cal.set(Calendar.MONTH, stroke.getTimeByyear()-1);
				Date dateByYear = cal.getTime();
				if(startDate.before(dateByYear)&&endDate.after(dateByYear)){
					flag = true;
					stroke.setTimeByweek(DateUtils.getWeekDay(dateByYear));
				}else{
					flag = false;
				}
			}
				break;
		}
		
		return flag;
	}

	@Override
	public List<Stroke> getStrokes(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}

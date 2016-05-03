package com.tinytree.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tinytree.dao.ReserveSettingDao;
import com.tinytree.entity.ReserveSetting;
import com.kungfu.dental.service.ReserveSettingService;

/**
 * @Description:预约设置服务实现类
 * @ClassName: ReserveSettingServiceImpl
 * @Author：tangyang
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("reserveSettingService")
public class ReserveSettingServiceImpl extends BaseServiceImpl<ReserveSetting, String> implements ReserveSettingService{

	@Autowired
	private ReserveSettingDao reserveSettingDao;
	
	@Autowired
	public void setBaseDao(ReserveSettingDao reserveSettingDao) {
		super.setBaseDao(reserveSettingDao);
	}

	@Override
	@Transactional
	public void saveAndUpdate(List<ReserveSetting> entitys) {
		if(entitys == null || entitys.isEmpty()){
			return;
		}
		
		List<ReserveSetting> addRepeatEntitys = new ArrayList<ReserveSetting>();
		List<ReserveSetting> updateRepeatEntitys = new ArrayList<ReserveSetting>();
		List<ReserveSetting> list = reserveSettingDao.get(entitys.get(0).getDoctorId());
		
		for(ReserveSetting entity:entitys){
			addRepeatEntitys.add(entity);
			if(isUpdate(entity,list)){
				addRepeatEntitys.remove(entity);
				updateRepeatEntitys.add(entity);
				continue;
			}
			//addRepeatEntitys.add(entity);
		}
		
	/*	if(!addRepeatEntitys.isEmpty()){
			super.save(addRepeatEntitys);
		}*/
		if(!addRepeatEntitys.isEmpty()){
			super.save(addRepeatEntitys);
		}
		if(!updateRepeatEntitys.isEmpty()){
			super.update(updateRepeatEntitys);
		}
		
	}
	
	private boolean isUpdate(ReserveSetting entity,List<ReserveSetting> entitys){
		if(entity == null || entitys == null || entitys.isEmpty()){
			return false;
		}
		
		for(ReserveSetting reserveSetting:entitys){
			if(entity.getWeekDay() == reserveSetting.getWeekDay()){
				entity.setId(reserveSetting.getId());
				return true;
			}
		}
		
		return false;
	}

}

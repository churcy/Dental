package com.tinytree.service;

import java.util.List;

import com.tinytree.entity.ReserveSetting;

/**
 * @Description:预约设置服务接口
 * @ClassName: ReserveSettingService
 * @Author：tangyang
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface ReserveSettingService extends BaseService<ReserveSetting, String>{
	
	/**
	 * 保存和修改预约设置
	 * @param entitys
	 */
	public void saveAndUpdate(List<ReserveSetting> entitys);
}

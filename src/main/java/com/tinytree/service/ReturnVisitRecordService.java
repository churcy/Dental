package com.tinytree.service;

import com.tinytree.entity.ReturnVisitRecord;

/**
 * @Description:回访记录详情服务接口
 * @ClassName: ReturnVisitRecordService
 * @Author：tangyang
 * @Date 2016-1-23
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface ReturnVisitRecordService extends BaseService<ReturnVisitRecord, String>{
	/**
	 * 创建回访记录
	 * @param record
	 */
	public void createReturnVisitRecord(ReturnVisitRecord record);
}

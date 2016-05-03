package com.tinytree.dao;

import java.util.List;

import com.kungfu.dental.entity.MedicalRecordBook;

/**
 * @Description:注册用户病历本数据处理接口
 * @ClassName: MedicalRecordBookDao
 * @Author：tangyang
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface MedicalRecordBookDao extends BaseDao<MedicalRecordBook>{
	
	public List getBookListByUser(String userId);
	
	public List getBookList(String doctorAccountId);
}

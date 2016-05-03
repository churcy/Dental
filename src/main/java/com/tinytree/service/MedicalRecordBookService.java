package com.tinytree.service;

import java.util.Date;
import java.util.List;

import com.tinytree.bean.PagerEx;
import com.tinytree.entity.MedicalRecordBook;

/**
 * @Description:注册用户病历本服务接口
 * @ClassName: MedicalRecordBookService
 * @Author：tangyang
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface MedicalRecordBookService extends BaseService<MedicalRecordBook, String>{

	/**
	 * 查询用户病历本是否存在并获取
	 * @param userId
	 * @return
	 */
	public MedicalRecordBook getBookById(String userId,String doctorId) throws Exception;
	
	/**
	 * 获取注册用户病历本列表
	 * @param doctorId
	 * @return
	 * @throws Exception
	 */
	public PagerEx getBookByDoctor(String doctorId,int startPage,int pageSize,Date startDate,Date endDate) throws Exception;
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public List getBookListByUser(String userId);
	
	/**
	 * 医生查看病历本列表
	 * @param doctorAccountId
	 * @return
	 */
	public List getBookList(String doctorAccountId);
}

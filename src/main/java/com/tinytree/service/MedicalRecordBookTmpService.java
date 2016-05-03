package com.tinytree.service;

import com.tinytree.entity.MedicalRecordBookTmp;

/**
 * @Description:非注册用户病历本服务接口
 * @ClassName: MedicalRecordBookTmpService
 * @Author：tangyang
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface MedicalRecordBookTmpService extends BaseService<MedicalRecordBookTmp, String>{
	
	/**
	 * 查询非注册用户病历本
	 * @param phone
	 * @param doctorId
	 * @return
	 * @throws Exception
	 */
	public MedicalRecordBookTmp getBook(String phone,String doctorId) throws Exception;
}

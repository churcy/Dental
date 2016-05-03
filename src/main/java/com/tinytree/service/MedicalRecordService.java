package com.tinytree.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tinytree.entity.MedicalRecord;
import com.tinytree.entity.User;

/**
 * @Description:病历服务接口
 * @ClassName: MedicalRecordService
 * @Author：tangyang
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface MedicalRecordService extends BaseService<MedicalRecord, String>{
	
	/**
	 * 创建病历
	 * @param medicalRecord
	 * @param userId
	 * @param doctorId
	 * @throws Exception
	 */
	public void createMedicalRecord(MedicalRecord medicalRecord,String userId,String doctorId,String allergy) throws Exception;
	
	/**
	 * 非注册用户创建病历
	 * @param medicalRecord
	 * @param groupId 
	 * @param bookTmp
	 * @throws Exception
	 */
	public void createMedicalRecordTmp(MedicalRecord medicalRecord,String doctorId,User user,String allergy, String groupId) throws Exception;

	/**
	 * 条件查询 病历列表
	 * @param bookId
	 * @param dStartDate
	 * @param dEndDate
	 * @return
	 */
	public List<Map<String, Object>> findMedicalRecordList(String bookId, Date dStartDate,
			Date dEndDate);
	
}

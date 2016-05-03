package com.tinytree.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tinytree.dao.MedicalRecordDao;
import com.tinytree.entity.MedicalRecord;
import com.tinytree.entity.MedicalRecordBook;
import com.tinytree.entity.User;
import com.kungfu.dental.service.DoctorService;
import com.kungfu.dental.service.FriendManageService;
import com.kungfu.dental.service.MedicalRecordBookService;
import com.kungfu.dental.service.MedicalRecordService;
import com.kungfu.dental.service.UserService;

/**
 * @Description:病历服务实现
 * @ClassName: MedicalRecordServiceImpl
 * @Author：tangyang
 * @Date 2016-1-21
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("medicalRecordServiceImpl")
public class MedicalRecordServiceImpl extends BaseServiceImpl<MedicalRecord, String> implements MedicalRecordService{
	
	@Autowired
	private MedicalRecordDao medicalRecordDao;
	
	@Autowired
	private MedicalRecordBookService medicalRecordBookService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private FriendManageService friendManageService;
	
	@Autowired
	public void setBaseDao(MedicalRecordDao medicalRecordDao) {
		super.setBaseDao(medicalRecordDao);
	}

	@Override
	@Transactional
	public void createMedicalRecord(MedicalRecord medicalRecord, String userId,
			String doctorId,String allergy) throws Exception {
		if(medicalRecord == null || StringUtils.isAnyBlank(userId,doctorId)){
			return;
		}
		
		boolean modify = true;
		String doctorKey = doctorService.getByAccountId(doctorId).getId();
		MedicalRecordBook book = medicalRecordBookService.getBookById(userId,doctorKey);
		User user = new User();
		user.setId(userId);
		if(book == null){
			book = new MedicalRecordBook();
			book.setAllergy(allergy);
			book.setDoctorId(doctorKey);
			book.setUser(user);
			book.setLastVisitDate(new Date());
			medicalRecordBookService.save(book);
			modify = false;
		}
		
		medicalRecord.setBookId(book.getId());
		super.save(medicalRecord);
		
		if(modify){
			book.setLastVisitDate(new Date());
			medicalRecordBookService.update(book);
		}
	}

	@Override
	@Transactional
	public void createMedicalRecordTmp(MedicalRecord medicalRecord,String accountId,
			User user,String allergy,String groupId) throws Exception {
		if(medicalRecord == null || user == null){
			return;
		}
		userService.save(user);
		MedicalRecordBook book = new MedicalRecordBook();
		book.setAllergy(allergy);
		String doctorKey = doctorService.getByAccountId(accountId).getId();
		book.setDoctorId(doctorKey);
		book.setUser(user);
		book.setLastVisitDate(new Date());
		medicalRecordBookService.save(book);
		friendManageService.addFriend(groupId, user.getId());
		medicalRecord.setBookId(book.getId());
		super.save(medicalRecord);
	}

	@Override
	public List<Map<String, Object>> findMedicalRecordList(String bookId,
			Date dStartDate, Date dEndDate) {
		Map<String, Object> map  = new HashMap<String,Object>();
		map.put("bookId", bookId);
		map.put("dStartDate", dStartDate);
		map.put("dEndDate", dEndDate);
		List<Map<String, Object>> result = medicalRecordDao.findMedicalRecordList(map);
		return result;
	}
	
	
}

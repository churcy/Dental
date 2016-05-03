package com.tinytree.service.impl;

import com.github.pagehelper.PageHelper;

import com.tinytree.dao.DocSysSettingDao;
import com.tinytree.dao.DoctorDao;
import com.tinytree.entity.DocSysSetting;
import com.tinytree.entity.Doctor;
import com.tinytree.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description:医生信息相关服务实现
 * @ClassName: DoctorServiceImpl
 * @Author：zhengzhong
 * @Date 2016-1-19
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service
public class DoctorServiceImpl extends BaseServiceImpl<Doctor, String> implements DoctorService {
	
	@Autowired
	private DoctorDao doctorDao;
	
	@Autowired
	private DocSysSettingDao docSysSettingDao;
	
	public void setDoctorDao(DoctorDao doctorDao){
		this.doctorDao = doctorDao;
	}
	
	@Resource
	public void setBaseDao(DoctorDao doctorDao){
		super.setBaseDao(doctorDao);
	}

	@Override
	public Doctor getById(String id) {
		Doctor doctor = doctorDao.getById(id);
		System.out.println(doctor);
		return doctor;
	}

	@Override
	public List<Map<String, Object>> findByConditions(String hospitalId,String type,String startPage,String pageSize) throws Exception {
		
		PageHelper.startPage(Integer.valueOf(startPage),Integer.valueOf(pageSize));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hospitalId", hospitalId);
		map.put("type", type);
		List<Map<String, Object>> result = doctorDao.findByConditions(map);
		return result;
	}

	@Override
	public Doctor getByAccountId(String accountId) {
		Doctor doctor = doctorDao.getByAccountId(accountId);
		return doctor;
	}

	@Override
	public List<Map<String, Object>> getByUserId(String userId) {
		List<Map<String, Object>> list= doctorDao.getByUserId(userId);
		return list;
	}

	@Override
	public List<Map<String, Object>> getSysSetting(String userId) {
		Map<String, Object> map =  new HashMap<String, Object>();
		map.put("userId", userId);
		List<Map<String, Object>> result = docSysSettingDao.getSysSetting(map);
		return result;
	}

	@Override
	public int modifySysSetting(DocSysSetting docSysSetting) {
		docSysSettingDao.updateByPrimaryKey(docSysSetting);
		return 0;
	}

	@Override
	public int saveSysSetting(DocSysSetting docSysSetting) {
		docSysSettingDao.insert(docSysSetting);
		return 0;
	}

	@Override
	public DocSysSetting getSysSettingByCondition(String userId) {
		Map<String, Object> map  = new HashMap<String,Object>();
		map.put("userId", userId);
		List<Map<String, Object>> result = docSysSettingDao.getSysSetting(map);
		String key = (String) result.get(0).get("id");
		DocSysSetting docSysSetting = docSysSettingDao.selectByPrimaryKey(key);
		return docSysSetting;
	}
	
}

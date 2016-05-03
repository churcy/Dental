package com.tinytree.service.impl;

import com.github.pagehelper.PageHelper;
import com.tinytree.dao.HospitalDao;
import com.tinytree.entity.Hospital;
import com.tinytree.service.HospitalSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
/**
 * @Description:医院信息相关服务实现
 * @ClassName: HospitalSerivceImpl
 * @Author：zhengzhong
 * @Date 2016-1-19
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service
public class HospitalSerivceImpl extends BaseServiceImpl<Hospital, String> implements HospitalSerivce {
	@Autowired
	private HospitalDao hospitalDao;
	
	public void setHospitalDao(HospitalDao hospitalDao){
		this.hospitalDao = hospitalDao;
	}

	@Resource
	public void setBaseDao(HospitalDao hospitalDao){
		super.setBaseDao(hospitalDao);
	}
	@Override
	public List<Map<String,Object>> findByConditions(String startPage,String pageSize) throws Exception {
		int pageNum = Integer.valueOf(startPage);
		int pageSizeLocal = Integer.valueOf(pageSize);
		PageHelper.startPage(pageNum, pageSizeLocal);
		List<Map<String,Object>> list = hospitalDao.getAll();
		return list;
	}

	@Override
	public void setHospitalInfo(Hospital hospital) {
		
		super.save(hospital);
	}

}

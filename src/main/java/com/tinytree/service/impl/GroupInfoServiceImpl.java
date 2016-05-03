package com.tinytree.service.impl;

import com.tinytree.bean.PagerEx;
import com.tinytree.bean.PagerParam;
import com.tinytree.bean.PagerParam.ConditionType;
import com.tinytree.dao.GroupInfoDao;
import com.tinytree.entity.GroupInfo;
import com.tinytree.service.GroupInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupInfoServiceImpl extends BaseServiceImpl<GroupInfo, String> implements GroupInfoService {
	
	@Autowired
	private GroupInfoDao groupInfoDao;
	
	@Autowired
	public void setBaseDao(GroupInfoDao groupInfoDao){
		super.setBaseDao(groupInfoDao);
	}

	@Override
	public List<Map<String, Object>> getByConditons(String userId,
			String groupId, String groupName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("groupId", groupId);
		map.put("groupName", groupName);
		List<Map<String, Object>> result = groupInfoDao.getByConditions(map);
		return result;
	}

	@Override
	public List<GroupInfo> getGroupByConditions(String userId, String groupId,
			String groupName) throws Exception {
		PagerEx pagerEx = new PagerEx();
		List<PagerParam> params = new ArrayList<>();
		if(StringUtils.isNotBlank(groupName)){
			PagerParam pagerParam = new PagerParam();
			pagerParam.setParam(ConditionType.equal, "name", groupName);
			params.add(pagerParam);
		}
		if(StringUtils.isNotBlank(userId)){
			PagerParam pagerParam = new PagerParam();
			pagerParam.setParam(ConditionType.equal, "user_id", userId);
			params.add(pagerParam);
		}
		if(StringUtils.isNotBlank(groupId)){
			PagerParam pagerParam = new PagerParam();
			pagerParam.setParam(ConditionType.equal, "id", groupId);
			params.add(pagerParam);
		}
		pagerEx.setParams(params);
		List<GroupInfo> list = this.findByPager(pagerEx).getList();
		return list;
	}
	
}

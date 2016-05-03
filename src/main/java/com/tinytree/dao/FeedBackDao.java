package com.tinytree.dao;

import java.util.Map;

import com.kungfu.dental.entity.FeedBack;
/**
 * @Description:用户意见反馈dao
 * @ClassName: FeedBackDao
 * @Author：zhengzhong
 * @Date 2016-1-31
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface FeedBackDao extends BaseDao<FeedBack>{
	
	public Map<String, Object> getByUserId(Map<String, Object> map);
	
}

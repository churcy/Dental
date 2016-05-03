package com.tinytree.service.impl;

import com.tinytree.dao.FeedBackDao;
import com.tinytree.entity.FeedBack;
import com.tinytree.service.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description:用户意见反馈接口实现
 * @ClassName: FeedBackImpl
 * @Author：zhengzhong
 * @Date 2016-1-31
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service
public class FeedBackImpl extends BaseServiceImpl<FeedBack, String> implements FeedBackService {

	@Autowired
	private FeedBackDao feedBackDao;
	
	public void setFeedBackDao(FeedBackDao feedBackDao){
		this.feedBackDao = feedBackDao;
	}

	@Resource
	public void setBaseDao(FeedBackDao feedBackDao){
		super.setBaseDao(feedBackDao);
	}
	@Override
	public Map<String, Object> getByUserId(String userId,String date) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> result = feedBackDao.getByUserId(map);
		return result;
	}

}

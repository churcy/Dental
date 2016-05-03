package com.tinytree.service;

import java.util.Map;

import com.tinytree.entity.FeedBack;

public interface FeedBackService extends BaseService<FeedBack, String>{

	public Map<String, Object> getByUserId(String userId,String date);
}

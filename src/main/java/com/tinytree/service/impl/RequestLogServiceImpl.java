package com.tinytree.service.impl;
import com.tinytree.service.RequestLogService;
import com.tinytree.dao.RequestLogDao;
import com.tinytree.entity.RequestLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:请求数据服务实现类
 * @ClassName: RequestLogServiceImpl
 * @Author：zhengzhong
 * @Date 2016-1-6
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service
public class RequestLogServiceImpl extends BaseServiceImpl<RequestLog, String> implements RequestLogService{

	@Autowired
	private RequestLogDao requestLogDao;
	

	public void setRequestLogDao(RequestLogDao requestLogDao) {
		this.requestLogDao = requestLogDao;
	}
	@Autowired
	public void setBaseDao(RequestLogDao requestLogDao) {
		super.setBaseDao(requestLogDao);
	}
	
}

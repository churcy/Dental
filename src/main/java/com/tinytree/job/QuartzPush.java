package com.tinytree.job;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import com.tinytree.service.JPushService;
import com.tinytree.service.impl.JPushServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class QuartzPush implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("★★★★★★★★★★★ "+"The QuartzPush Execute "+"★★★★★★★★★★★");
		try {
			JPushServiceImpl jPushService = new JPushServiceImpl();
			boolean flag = jPushService.push("2","到底要干嘛？");
			System.out.println("push over");
			System.out.println("This is push return : "+flag);
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIRequestException e) {
			e.printStackTrace();
		}
	}

}

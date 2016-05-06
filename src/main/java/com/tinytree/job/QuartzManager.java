package com.tinytree.job;


import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.core.jmx.CronTriggerSupport;
import org.quartz.core.jmx.JobDetailSupport;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.spi.OperableTrigger;

public class QuartzManager {
	   private static SchedulerFactory sf = new StdSchedulerFactory();
	     
	   /** 
	    *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
		* @Map  参数
	    * @param attr name 任务名
		* @param attr jobName
		* @param attr jobGroup
	    * @param attr group 任务组
		* @param attr description 描述
		* @param attr jobClass job的classname
		* @param attr jobDataMap
		* @param attr durability 使用时间 耐久性
		* @param attr shouldRecover
	    * @param attr cronExpression
		* @param attr timeZone 时区
	    * @throws SchedulerException 
	    * @throws ParseException 
	    * @throws  
	    */  
	   public static void addJob(Map<String ,Object> attr)
			   throws SchedulerException, ParseException, ClassNotFoundException {
	       Scheduler sched = sf.getScheduler();

	       JobDetail jobDetail = JobDetailSupport.newJobDetail(attr);

		   Trigger trigger = CronTriggerSupport.newTrigger(attr);
		   //触发器时间设定
		   sched.scheduleJob(jobDetail,trigger);
	       //启动  
	       if(!sched.isShutdown())  
	          sched.start();  
	   }  

}

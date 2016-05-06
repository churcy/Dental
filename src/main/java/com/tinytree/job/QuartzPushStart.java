package com.tinytree.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QuartzPushStart {
	 public static void main(String[] args) {  
	        // TODO Auto-generated method stub  
	        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
	        Date d = new Date();  
	        String returnstr = DateFormat.format(d);          
	          
	        QuartzPush job = new QuartzPush();  
	        String job_name ="11";  
	        try {
				System.out.println("★★★★★★★★★★★ "+"The QuartzPush Strat,Date is " +returnstr +" ★★★★★★★★★★★");
				Class jobClass = Class.forName("com.tinytree.job.QuartzPush");
				Map<String ,Object> map = new HashMap<>();
				map.put("name","testname");
				map.put("jobName","testname");
				map.put("jobGroup","group");
				map.put("group","group");
				map.put("jobClass","com.tinytree.job.QuartzPush");
				//String cronExpression = "0 37 16 ? * *";
				String cronExpression = "0/10 * * * * ?";//"0 37 16 ? * *"
				map.put("cronExpression",cronExpression);

	            QuartzManager.addJob(map);

	              
	        }  catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
}

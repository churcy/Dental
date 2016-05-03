package com.tinytree.service;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
/**
 * @Description:极光推送服务
 * @ClassName: JPushServiceImpl
 * @Author：zhengzhong
 * @Date 2016-1-6
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface JPushService {
	
	/**
	 * 发送推送消息 成功为true 失败false
	 * @param type INT 类型 0:表示平台为android 1:表示平台为IOS 2:表示所有平台
	 * @param message 推送的消息内容
	 * @return TODO
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public boolean push(String type,String message) throws APIConnectionException, APIRequestException;
	/**
	 * 发送推送消息 成功为true 失败false
	 * @param type INT 类型 0:表示平台为android 1:表示平台为IOS 2:表示所有平台
	 * @param message:推送的消息内容  title:推送的消息标题 (针对android)
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public boolean push(String type,String message,String title) throws APIConnectionException, APIRequestException;

	/**
	 * 发送推送消息 成功为true 失败false
	 * @param receiver 用户的别名
	 * @param message 推送的消息内容  
	 * @throws APIConnectionException
	 * @throws APIRequestException
	 */
	public boolean pushPresonal(String receiver,String message) throws APIConnectionException, APIRequestException;

	 /**
	 * @param accountId 用户账户id
	 * @param tempType 0:医生预约 (params为{count})
	 * 				   1:患者预约 (params为{doctorName,time})
	 * 			       2:医生行程(params为{time,title})
	 * @param params 
	 * @return
	 * @throws Exception 
	 */
	public boolean tempPush(String accountId,int tempType,Object[] params) throws Exception;

}

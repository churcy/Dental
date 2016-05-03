package com.tinytree.service;

import com.tinytree.entity.ShortMessage;

/**
 * @Description:发送短信服务
 * @ClassName: SMSService
 * @Author：zhengzhong
 * @Date 2016-1-6
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface SMSService extends BaseService<ShortMessage, String>{
	/**
	 * @param phone 用户手机号
	 * @param verificationCode 验证码
	 * @return 成功true 失败 false
	 * @throws Exception
	 */
	public boolean sendSMSCode(String phone,String verificationCode) throws Exception;
	
	/**
	 * @param mobile
	 * @param tempType 0:医生预约(params为{count") 
	 * 				   1:患者预约(params为{doctorName,time})
	 *		           2:医生行程(params为{time,title})
	 * @param params 
	 * @return
	 * @throws Exception 
	 */
	public boolean tempSMS(String mobile,int tempType,Object[] params) throws Exception;

}

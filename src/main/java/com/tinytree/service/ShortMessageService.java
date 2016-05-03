package com.tinytree.service;

import com.tinytree.entity.ShortMessage;
/**
 * @Description:短信内容相关服务
 * @ClassName: ShortMessageService
 * @Author：zhengzhong
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface ShortMessageService extends BaseService<ShortMessage, String>{
	
	/**
	 * 根据验证码和手机号查询短信信息
	 * @param userPhone
	 * @param verificationCode
	 * @return ShortMessage
	 */
	public ShortMessage findShortMessageByUserPhoneAndCode(String userPhone,String verificationCode);

	/**
	 * 根据验证码和手机号查询短信 验证码状态 并且设置验证码状态
	 * @param userPhone
	 * @param smsCode
	 * @return 0:数据库没有 对应的数据 
	 * 		   1:数据库中相应短信验证码已使用或者过期 (即为不可用)
	 * 		   2:当前时间该短信验证码已过期(同时置为过期状态)
	 * 	       3:短信验证码可用(同时设置为已使用)
	 */
	public int checkCode(String userPhone, String smsCode);
}

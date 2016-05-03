package com.tinytree.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinytree.dao.ShortMessageDao;
import com.tinytree.entity.ShortMessage;
import com.kungfu.dental.service.SMSService;
import com.kungfu.dental.templateSms.SMSManager;

/**
 * @Description:发送短信服务实现
 * @ClassName: SMSServiceImpl
 * @Author：zhengzhong
 * @Date 2016-1-6
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("smsService")
public class SMSServiceImpl extends BaseServiceImpl<ShortMessage, String>implements SMSService {

	@Autowired
	private ShortMessageDao shortMessageDao;
	
	public void setShortMessageDao(ShortMessageDao shortMessageDao) {
		this.shortMessageDao = shortMessageDao;
	}

	@Resource
	public void setBaseDao(ShortMessageDao shortMessageDao) {
		super.setBaseDao(shortMessageDao);
	}
	/**
	 * 发送短信验证码 
	 * phone 用户手机号
	 * verificationCode 验证码
	 * 发送失败 返回false 成功返回 true 并且保存数据库
	 */
	@Override
	public boolean sendSMSCode(String phone,String verificationCode) throws Exception {
	
		SMSManager manager = SMSManager.getInstance("sn");
		String []params = new String[]{verificationCode};
		boolean flag = manager.sendSMS(phone, params);
		String expTime = manager.getSMSExpTime();
		if(flag){
			ShortMessage shortMessage = new ShortMessage();
			shortMessage.setUserPhone(phone);
			shortMessage.setVerificationCode(verificationCode);
			shortMessage.setExpirationTime(expTime);
			shortMessage.setStatus(0);
			shortMessageDao.insert(shortMessage);
		}
		return flag;
	}

	@Override
	public boolean tempSMS(String mobile, int tempType, Object[] params) throws Exception {
		// TODO Auto-generated method stub
		SMSManager manager = SMSManager.getInstance("st");
		boolean flag = manager.sendTempSMS(mobile,tempType, params);
		return flag;
	}

}

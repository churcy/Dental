package com.tinytree.service.impl;

import com.tinytree.dao.ShortMessageDao;
import com.tinytree.entity.ShortMessage;
import com.tinytree.service.ShortMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description:发送短信服务实现
 * @ClassName: SMSServiceImpl
 * @Author：zhengzhong
 * @Date 2016-1-6
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("shortMessageService")
public class ShortMessageServiceImpl extends BaseServiceImpl<ShortMessage, String>implements ShortMessageService {

	@Autowired
	private ShortMessageDao shortMessageDao;
	
	public void setShortMessageDao(ShortMessageDao shortMessageDao) {
		this.shortMessageDao = shortMessageDao;
	}

	@Resource
	public void setBaseDao(ShortMessageDao shortMessageDao) {
		super.setBaseDao(shortMessageDao);
	}

	@Override
	public ShortMessage findShortMessageByUserPhoneAndCode(String userPhone,
			String verificationCode) {
		ShortMessage shortMessage = shortMessageDao.findShortMessageByUserPhoneAndCode(userPhone, verificationCode);
		return shortMessage;
	}

	@Override
	public int checkCode(String userPhone, String smsCode) {
		ShortMessage shortMessage = shortMessageDao.findShortMessageByUserPhoneAndCode(userPhone, smsCode);
		int expTime = Integer.valueOf(shortMessage.getExpirationTime());
		Date createDate = shortMessage.getCreateDate();
		long dateTimeNow = new Date().getTime();
		long dateTimeOut = new Date(createDate.getTime()+expTime*60*1000).getTime();
		int messageStatus = shortMessage.getStatus();
		int returnStatus;
		do{
			if(shortMessage==null){
				returnStatus = 0;
				break;
			}
			if(messageStatus==1||messageStatus==2){
				returnStatus = 1;
				break;
			}
			if(dateTimeNow>dateTimeOut){
				returnStatus = 2;
				shortMessage.setStatus(2);
				shortMessageDao.updateByPrimaryKey(shortMessage);
				break;
			}
			returnStatus = 3;
			shortMessage.setStatus(1);
			shortMessageDao.updateByPrimaryKey(shortMessage);
		}while(false);
		return returnStatus;
	}
}

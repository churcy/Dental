package com.tinytree.templateSms;

import java.util.Date;

import com.google.gson.Gson;
import com.tinytree.bean.SMSBody;

public class TemplateSMSST extends SMS {

	@Override
	public String sendTempSMS(String mobile, int tempType, Object[] params) throws Exception {
		String body = setRequestBody(mobile,tempType,params);
		// 获取时间戳
		String timestamp = SMSSupport.dateToStr(new Date(), "yyyyMMddHHmmss");// 获取时间戳
		String url = createUrl(body, timestamp);
		return sendSMS(timestamp, url, body);
	}
	
	// 设置request请求参数
	public static String setRequestBody(String phone, int tempType,Object[] params) {
		SMSBody templateSMS = new SMSBody();
		templateSMS.setAppId(smsBean.getAppId());
		templateSMS.setTo(phone);
		switch (tempType) {
		case 0:
			templateSMS.setTemplateId(smsBean.getTemplateDocResId());
			templateSMS.setParam((String)params[0]);
			break;
		case 1:
			templateSMS.setTemplateId(smsBean.getTemplatePatResId());
			templateSMS.setParam(params[0] + "," + params[1]);
			break;
		case 2:
			templateSMS.setTemplateId(smsBean.getTemplateDocStrokeId());
			templateSMS.setParam(params[0] + "," + params[1]);
			break;
		}
		Gson gson = new Gson();
		String body = gson.toJson(templateSMS);
		return body;
	}

	// 拼装请求url
	public static String createUrl(String body, String timestamp)
			throws Exception {
		String signature = SMSSupport.getSignature(smsBean.getAccountSid(),
				smsBean.getAuthToken(), timestamp);
		String url = SMSSupport.createUrl(smsBean, signature);
		return url;
	}

	@Override
	public String sendSMS(String phone, Object[] params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}

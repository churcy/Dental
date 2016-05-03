package com.tinytree.templateSms;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:发送短信控制
 * @ClassName: SmsManager
 * @Author：zhengzhong
 * @Date 2016-1-4
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public class SMSManager {
	
	Logger logger = LoggerFactory.getLogger(SMSManager.class);
	
	private static final String SMS_SN = "sn";//表示验证码
	
	private static final String SMS_TEMP = "st";//表示模板短信
	
	private SMS sms;
	
	private SMSManager(){}
	
	private SMSManager(String type){
		if(SMS_SN.equalsIgnoreCase(type)){
			sms = new TemplateSMSSN();
		}
		if(SMS_TEMP.equalsIgnoreCase(type)){
			sms = new TemplateSMSST();
		}
/*		if(SMS_DOC_RES.equalsIgnoreCase(type)){
			sms = new TemplateDoctorReserve();
		}
		if(SMS_PAT_RES.equalsIgnoreCase(type)){
			sms = new TemplatePatientReserve();
		}
		if(SMS_DOC_STROKE.equalsIgnoreCase(type)){
			sms = new TemplateDoctorStroke();
		}*/
	}
	
	private static class SMSManagerHodler{
		private static SMSManager snInstance = new SMSManager(SMS_SN);
		private static SMSManager stInstance = new SMSManager(SMS_TEMP);
	}
	
	public static SMSManager getInstance(String type){
		if(SMS_SN.equalsIgnoreCase(type)){
			return SMSManagerHodler.snInstance;
		} 
		if(SMS_TEMP.equalsIgnoreCase(type)){
			return SMSManagerHodler.stInstance;
		} 
		else{
			return SMSManagerHodler.snInstance;
		}
	}
	
	/**
	 * @param phone 用户手机号
	 * @param params 请求参数  随机验证码 或者其他参数
	 * @return boolean 发送成功返回true 否则false
	 * @throws Exception
	 */
	public  boolean sendSMS(String phone,Object[] params) throws Exception{
		
		String responseInfo = sms.sendSMS(phone, params);
		JSONObject jsonObj = JSONObject.fromObject(responseInfo);
		// 得到指定json key对象的value对象
		JSONObject respObj = (JSONObject) jsonObj.get("resp");
		String respCode = respObj.get("respCode").toString();
		if(StringUtils.isNotBlank(respCode)&&respCode.equals("000000")){
			return true;
		}else{
			logger.error("发送失败 respCode: "+respCode+"手机号码为 "+ phone);
			return false;
		}
	}

	public boolean sendTempSMS(String mobile,int tempType, Object[] params) throws Exception {
		String responseInfo = sms.sendTempSMS(mobile,tempType, params);
		JSONObject jsonObj = JSONObject.fromObject(responseInfo);
		// 得到指定json key对象的value对象
		JSONObject respObj = (JSONObject) jsonObj.get("resp");
		String respCode = respObj.get("respCode").toString();
		if(StringUtils.isNotBlank(respCode)&&respCode.equals("000000")){
			return true;
		}else{
			logger.error("发送失败 respCode: "+respCode+"手机号码为 "+ mobile);
			return false;
		}
	}
	
	public String getSMSExpTime(){
		
		String expTime = sms.smsBean.getExpTime();
		return expTime;
		
	}
	
	public static void main(String[] args) throws Exception {
		SMSManager manager = SMSManager.getInstance("st");
		//TemplatePatientReserve patientReserve = new TemplatePatientReserve();
		//patientReserve.sendSMS("18627170706", new String[]{"zz","xxxx"});
		//manager.sendSMS("18627170706",new String[]{"123456"});
		manager.sendTempSMS("18064117551", 0, new String[]{"2"});
		//TemplateSMSST templateSMSST = new TemplateSMSST();
		//templateSMSST.sendTempSMS("18064117551", 1, new String[]{"1","2"});
	}

}

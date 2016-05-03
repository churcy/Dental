package com.tinytree.templateSms;

import com.tinytree.bean.SMSBody;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description:发送短信验证码实现
 * @ClassName: TemplateSMSSN
 * @Author：zhengzhong
 * @Date 2016-1-4
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功
 *
 */
public class TemplateSMSSN extends SMS{
	
	@Override
	public String sendSMS(String phone, Object[] params) throws Exception {
	    Map<String, String> snMap = new HashMap<String, String>();
	    snMap.put("tel",phone);
	    snMap.put("expTime", smsBean.getExpTime());
	    snMap.put("sn",(String) params[0]);
	    String body = setRequestBody(snMap);
	    //获取时间戳
	    String timestamp = SMSSupport.dateToStr(new Date(), "yyyyMMddHHmmss");//获取时间戳
	    String url = createUrl(body,timestamp);

		return sendSMS(timestamp,url,body);
	}
	
	//设置request请求参数
	public static String setRequestBody(Map<String, String> map){
        SMSBody templateSMS=new SMSBody();
        templateSMS.setAppId(smsBean.getAppId());
        templateSMS.setTo(map.get("tel"));
        templateSMS.setTemplateId(smsBean.getTemplateSNId());
        templateSMS.setParam(map.get("sn")+","+map.get("expTime"));
        String body = JSONObject.fromObject(templateSMS).toString();
        return body;
	}
	//拼装请求url
	public static String createUrl(String body,String timestamp) throws Exception{
	    String signature =SMSSupport.getSignature(smsBean.getAccountSid(),smsBean.getAuthToken(),timestamp);
	    String url = SMSSupport.createUrl(smsBean, signature);
	    return url;
	}

	@Override
	public String sendTempSMS(String mobile, int tempType, Object[] params) {
		// TODO Auto-generated method stub
		return null;
	}

}

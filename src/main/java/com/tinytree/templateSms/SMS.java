package com.tinytree.templateSms;

import com.tinytree.bean.SMSBean;
import com.tinytree.util.LoadProperty2Bean;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:发送短信抽象类,提供基本服务
 * @ClassName: SMS
 * @Author：zhengzhong
 * @Date 2016-1-4
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public abstract class SMS {
	
	protected  static final SMSBean smsBean = (SMSBean) LoadProperty2Bean.getInstance().getBean(LoadProperty2Bean.SMS);
	
	/**
	 * @param phone 用户手机号
	 * @param params 验证码或者其他请求参数
	 * @return response状态码
	 * @throws Exception
	 * 供子类实现 作为发送短信
	 */
	public abstract String sendSMS(String phone,Object[] params) throws Exception;
	
	public abstract String sendTempSMS(String mobile, int tempType, Object[] params) throws Exception;
	Logger logger = LoggerFactory.getLogger(SMS.class);
	
	protected String sendSMS(String timestamp,String url,String body){
		//map.get();
        String result = "";
        DefaultHttpClient httpclient=new DefaultHttpClient();
        try {
    	    body="{\"templateSMS\":"+body+"}";
            HttpResponse response = SMSSupport.postForSMS(smsBean.getAccountSid(),timestamp, url, body);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
                logger.debug("templateSMS Response content is: " + result);
            }
          // 确保HTTP响应内容全部被读出或者内容流被关闭

       } catch (Exception e) {
                 e.printStackTrace();
       } finally{
               //关闭连接
               httpclient.getConnectionManager().shutdown();
       }
       return result;
	}

}

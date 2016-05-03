package com.tinytree.templateSms;

import com.tinytree.bean.SMSBean;
import com.tinytree.encry.EncryptManager;
import com.tinytree.util.LoadProperty;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:发送短信辅助类
 * @ClassName: SmsSupport
 * @Author：zhengzhong
 * @Date 2016-1-4
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功
 *
 */
public class SMSSupport {
	//生成时间戳
    public static String dateToStr(Date date,String pattern) {
           if (date == null || date.equals(""))
                 return null;
           SimpleDateFormat formatter = new SimpleDateFormat(pattern);
           return formatter.format(date);
    } 
    //获取sign
	public static String getSignature(String accountSid, String authToken,
			String timestamp) throws Exception {
		String sig = accountSid + authToken + timestamp;
		String signature = md5Digest(sig);
	/*	EncryptManager encryptManager = EncryptManager.getInstance("MD5");
		String signature = encryptManager.parseByte2HexStr(encryptManager.encrypt(sig));*/
		return signature;
	}
	//设置SmsBean属性
	public  static SMSBean setProperty(){
		SMSBean bean = SMSBean.getInstance();
		String propertyName = "smsInfoConfig.properties";
		LoadProperty loadProperty = new LoadProperty();
		Properties properties  = loadProperty.load(propertyName);
		bean.setExpTime((String) properties.get("expTime"));
		bean.setUrl((String) properties.get("url"));
		bean.setAccountSid((String) properties.get("accountSid"));
		bean.setAuthToken((String) properties.get("authToken"));
		bean.setAppId((String) properties.get("appId"));
		bean.setTemplateSNId((String) properties.get("templateSNId"));
		bean.setVersion((String) properties.get("version"));
		bean.setServer((String) properties.get("server"));
		return bean;
	}
	
	/**
	 * 创建URL模板 
	 *主要是用于云之迅SMS功能URL
	 */
	public static String createUrl(SMSBean smsBean, String signature){
		
		String url = smsBean.getUrl();
	    String[] urlPattern = {"\\{[1]}","\\{[2]}","\\{[3]}","\\{[4]}"}; 
	    String[] params = {smsBean.getServer(), smsBean.getVersion() ,smsBean.getAccountSid(),signature}; 
	    for(int i=0;i<urlPattern.length;i++){
	    	Pattern  pattern = Pattern.compile(urlPattern[i]); 
	   	    Matcher matcher = pattern.matcher(url); 
	   	    if(matcher.find()){
	   	    	url = matcher.replaceAll(params[i]);
	   	   }
	    }
		return url;
	}
	
	/**
	 * POST请求
	 *主要是用于云之迅SMS功能
	 */
	public static HttpResponse postForSMS(String accountSid,String timestamp,String url,String body) throws Exception{
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		//EncryptUtil encryptUtil = new EncryptUtil();
		httppost.setHeader("Accept", "application/json");
		httppost.setHeader("Content-Type", "application/json"+";charset=utf-8");
		String src = accountSid + ":" + timestamp;
		String auth = EncryptManager.base64Encoder(src);
		httppost.setHeader("Authorization", auth);
		BasicHttpEntity requestBody = new BasicHttpEntity();
        requestBody.setContent(new ByteArrayInputStream(body.getBytes("UTF-8")));
        requestBody.setContentLength(body.getBytes("UTF-8").length);
        httppost.setEntity(requestBody);
        // 执行客户端请求
		HttpResponse response = httpclient.execute(httppost);
		return response;
	}
	

	
    public static String md5Digest(String src) throws Exception {  
        // 定义数字签名方法, 可用：MD5, SHA-1  
        MessageDigest md = MessageDigest.getInstance("MD5");  
        byte[] b = md.digest(src.getBytes("utf-8"));  
        return byte2HexStr(b);  
     } 
    /** 
     * 字节数组转化为大写16进制字符串 
     * @param b 
     * @return 
     */  
    private static String byte2HexStr(byte[] b) {  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < b.length; i++) {  
            String s = Integer.toHexString(b[i] & 0xFF);  
            if (s.length() == 1) {  
                sb.append("0");  
            }  
            sb.append(s.toUpperCase());  
        }  
        return sb.toString();  
    }  
}

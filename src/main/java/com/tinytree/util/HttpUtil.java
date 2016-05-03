package com.tinytree.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
/**
 * @Description:发送http请求工具类
 * @ClassName: HttpUtil
 * @Author：zhengzhong
 * @Date 2015-12-31
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功
 *
 */
public class HttpUtil {
	

	/**
	 * GET请求
	 * @param getUrl
	 * @throws IOException
	 * @return 提取HTTP响应报文包体，以字符串形式返回
	 */
	public static String httpGet(String getUrl,Map<String, String> getHeaders,String params) throws IOException { 
		URL getURL = new URL(getUrl+"?"+params); 
		HttpURLConnection connection = (HttpURLConnection) getURL.openConnection(); 

        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
		if(getHeaders != null) {
			for(String pKey : getHeaders.keySet()) {
				connection.setRequestProperty(pKey, getHeaders.get(pKey));
			}
		}
		connection.connect();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder sbStr = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) { 
			sbStr.append(line); 
		} 
		bufferedReader.close();
		connection.disconnect(); 
		return new String(sbStr.toString().getBytes(),"utf-8");
	}
	/**
	 * POST请求
	 * @param postUrl
	 * @param postHeaders
	 * @param postEntity
	 * @throws IOException
	 * @return 提取HTTP响应报文包体，以字符串形式返回
	 */
	public static String httpPost(String postUrl,Map<String, String> postHeaders, String postEntity) throws IOException {
		
		URL postURL = new URL(postUrl); 
		HttpURLConnection httpURLConnection = (HttpURLConnection) postURL.openConnection(); 
		httpURLConnection.setDoOutput(true);                 
		httpURLConnection.setDoInput(true); 
		httpURLConnection.setRequestMethod("POST"); 
		httpURLConnection.setUseCaches(false); 
		httpURLConnection.setInstanceFollowRedirects(true);
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		if(postHeaders != null) {
			for(String pKey : postHeaders.keySet()) {
				httpURLConnection.setRequestProperty(pKey, postHeaders.get(pKey));
			}
		}
		if(postEntity != null) {
			DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream()); 
			out.writeBytes(postEntity); 
			out.flush(); 
			out.close(); // flush and close 
		}
		//connection.connect(); 
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())); 
		StringBuilder sbStr = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) { 
			sbStr.append(line); 
		} 
		bufferedReader.close();
		httpURLConnection.disconnect(); 
		return new String(sbStr.toString().getBytes(),"utf-8");
	} 
	
	public static String httpPut(String putUrl,Map<String, String> putHeaders,String putEntity) throws IOException{
		URL putURL = new URL(putUrl); 
		HttpURLConnection httpURLConnection = (HttpURLConnection) putURL.openConnection(); 
		httpURLConnection.setDoOutput(true);                 
		httpURLConnection.setDoInput(true); 
		httpURLConnection.setRequestMethod("PUT"); 
		httpURLConnection.setUseCaches(false); 
		httpURLConnection.setInstanceFollowRedirects(true);
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		if(putHeaders != null) {
			for(String pKey : putHeaders.keySet()) {
				httpURLConnection.setRequestProperty(pKey, putHeaders.get(pKey));
			}
		}
		if(putEntity != null) {
			DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream()); 
			out.writeBytes(putEntity); 
			out.flush(); 
			out.close(); // flush and close 
		}
		//connection.connect(); 
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())); 
		StringBuilder sbStr = new StringBuilder();
		String line;
		while ((line = bufferedReader.readLine()) != null) { 
			sbStr.append(line); 
		} 
		bufferedReader.close();
		httpURLConnection.disconnect(); 
		return new String(sbStr.toString().getBytes(),"utf-8");
	}
}

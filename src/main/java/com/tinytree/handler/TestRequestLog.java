package com.tinytree.handler;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
/**
 * @Description：测试模拟http多线程并发请求
 * @ClassName: TestRequestLog
 * @Author：zhengzhong
 * @Date 2015-12-29
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功
 *
 */
public class TestRequestLog extends Thread {

	public void run(){
		
		TestRequestLog.sendPost();
		
	}
	
	public static void sendPost(){

		Random random = new Random();
		int number = random.nextInt();
	    URL url;
	    String userAgent = "userAgent";
		try {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        String resToken = null;
	        String[] ret = new String[2];
			url = new URL("http://127.0.0.1:8080/console/emps");
		    URLConnection URLconnection = url.openConnection();   
		    URLconnection.setRequestProperty("accept", "*/*");
		    URLconnection.setRequestProperty("connection", "Keep-Alive");
		    URLconnection.setRequestProperty("user-agent",userAgent+number);
		    URLconnection.setRequestProperty("token",number+"token");
		   // URLconnection.set("opt", number+"option");
		    String opt = "opt=" +number+"opt";
		 // 发送POST请求必须设置如下两行
		    URLconnection.setDoOutput(true);
		    URLconnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(URLconnection.getOutputStream());
            // 发送请求参数
            out.print(opt);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(URLconnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            
            //Map headers = conn.getHeaderFields();
            resToken = URLconnection.getHeaderField("token");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	public static void main(String[] args) throws InterruptedException {
		Thread threads[] = new Thread[10];
		for(int i = 0; i < threads.length; i++){
			threads[i] = new TestRequestLog();
		}
		for(int i = 0; i < threads.length; i++){
			threads[i].start();
			
		}
		
	}

}

package com.tinytree.filter;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @Description：response伪装类，用于截取controller中的执行结果
 * @ClassName: ResponseWrapper
 * @Author：tangyang
 * @Date：2016-02-15
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class ResponseWrapper extends HttpServletResponseWrapper
{
	private String content;
	
	private PrintWriter writer;
	
	public ResponseWrapper(HttpServletResponse response) 
	{
		super(response);
		writer = new MyWriter(new ByteArrayOutputStream());
	}
	
	public PrintWriter getWriter()
	{
		return writer;
	}
	
	public String result()
	{
		return content;
	}
	
	private class MyWriter extends PrintWriter
	{
		 
		public MyWriter(ByteArrayOutputStream out)
		{
			super(out);
		}

		public void close() 
		{
		}

		public void flush() 
		{
			
		}

		public void write(String string) 
		{
			content = string;
		}
		
	}

}

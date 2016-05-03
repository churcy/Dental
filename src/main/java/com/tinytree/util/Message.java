package com.tinytree.util;

import java.io.Serializable;

/**
 * @Description：消息封装类
 * @ClassName: Message
 * @Author：zhengzhong
 * @Date 2015-12-28
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功
 *
 */
public class Message implements Serializable
{
	private static final long serialVersionUID = 7325400138314185868L;
	
	public static final String REQUEST = "REQUEST";
	
	public static final String REMIND = "REMIND";
	
	/**
	 * 消息类型 
	 */
	private String messageType;
	
	/**
	 * 消息附件
	 */
	private Object source;
	
	public Message(String messageType,Object source)
	{
		this.messageType = messageType;
		this.source = source;
	}
	
	public String getMessageType() 
	{
		return messageType;
	}

	public Object getSource()
	{
		return source;
	}

}

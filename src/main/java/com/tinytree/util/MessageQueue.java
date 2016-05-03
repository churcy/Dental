package com.tinytree.util;

import java.util.concurrent.ArrayBlockingQueue;


/**
 * @Description：消息队列
 * @ClassName: MessageQueue
 * @Author：zhengzhong
 * @Date 2015-12-28
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功
 *
 */
public class MessageQueue 
{
	/**
	 * 队列容量，最大容量为5W
	 */
	private ArrayBlockingQueue<Message> queue = new ArrayBlockingQueue<Message>(50*1000);
	
	private final static MessageQueue instance = new MessageQueue();
	
	private MessageQueue()
	{
		
	}
	
	public static MessageQueue getInstance()
	{
		return instance;
	}
	
	public void add(Message message)
	{
		try 
		{
			queue.put(message);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public Message take()
	{
		try 
		{
			return queue.take();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}

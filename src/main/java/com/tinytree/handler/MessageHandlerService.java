package com.tinytree.handler;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kungfu.dental.util.Message;
import com.kungfu.dental.util.MessageQueue;


/**
 * @Description：消息处理服务
 * @ClassName: MessageHandlerService
 * @Author：zhengzhong
 * @Date 2015-12-29
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功
 *
 */
@Component
public class MessageHandlerService
{
	@Autowired
	private ConcurrentHashMap<String,MessageHandler> messageHandlerMap;
	
	private ExecutorService threadPool;
	
	private static boolean isInit = false;
	
	public MessageHandlerService()
	{
	}
	
	public void init()
	{
		if(!isInit)
		{
			initReceiveThread();
			initThreadPool();
			isInit = true;
		}
		
	}
	
	private void initReceiveThread()
	{
		new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{
				while(true)
				{
					Message message = MessageQueue.getInstance().take();
					if(message == null)
					{
						continue;
					}
					
					MessageHandler messageHandler = messageHandlerMap.get(message.getMessageType());
					if(messageHandler != null)
					{
						messageHandler.sendMessage(message);
					}
				}
			}
		}).start();
	}
	
	private void initThreadPool()
	{
		threadPool = Executors.newCachedThreadPool();
		Collection<MessageHandler> values = messageHandlerMap.values();
		for(Iterator<MessageHandler> it = values.iterator();it.hasNext();)
		{
			MessageHandler messageHandler = it.next();
			if(messageHandler != null)
			{
				threadPool.execute(messageHandler);
			}
		}
	}
}

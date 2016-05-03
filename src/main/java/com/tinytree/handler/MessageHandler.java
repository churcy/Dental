package com.tinytree.handler;

import com.tinytree.util.Message;

import java.util.LinkedList;
import java.util.List;


/**
 * @Description：消息处理抽象类
 * @ClassName: MessageHandler
 * @Author：zhengzhong
 * @Date 2015-12-29
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功
 *
 */
public abstract class MessageHandler implements Runnable
{
	private boolean isRun = true;
	private List<Object> privateQueue = new LinkedList<Object>();
	
	/**
	 * 消息处理的执行方法，由子类实现
	 * @param list Message消息,用于批量执行任务
	 * @throws Exception 
	 */
	public abstract void execute(List<Object> list) throws Exception;
	
	/**
	 * 添加message附件
	 * @param source
	 */
	public synchronized void sendMessage(Message message)
	{
		privateQueue.add(message);
		this.notify();
	}
	
	/**
	 * 批量取消息附件，如果取不到此方法会阻塞
	 * @param count
	 * @return
	 */
	private synchronized List<Object> take(int count)
	{
		while(privateQueue.isEmpty())
		{
			try 
			{
				this.wait(2*1000);//等待2s
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		int size = privateQueue.size() < count ? privateQueue.size():count;
		List<Object> list = privateQueue.subList(0, size);
		List reList = new LinkedList<Object>(list);
		privateQueue.removeAll(reList);
		return reList;
	}
	
	@Override
	public final void run()
	{
		while(isRun)
		{
			List<Object> list = take(10);
			try {
				execute(list);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try 
			{
				Thread.sleep(500);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
}

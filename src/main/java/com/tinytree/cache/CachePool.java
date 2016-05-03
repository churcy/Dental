package com.tinytree.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @Description：缓存池
 * @ClassName: CachePool
 * @Author：tangyang
 * @Date：2015-12-24
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class CachePool extends TimerTask{
	
	private static final Logger logger = LoggerFactory.getLogger(CachePool.class);
	
	private CacheQueue[] queues;
	
	private int cacheSize;
	
	private String nameSpace;
	
	private Timer timer;
	
	private List<CacheTimeoutListener> listeners = new ArrayList<>();
	
	protected CachePool(String nameSpace,int cacheSize){
		this.nameSpace = nameSpace;
		this.cacheSize = cacheSize;
		initCacheQueue(cacheSize);
		initLogTimer();
	}

	private void initCacheQueue(int size){
		queues = new CacheQueue[size];
		for(int i = 0;i < queues.length;i++){
			queues[i] = new CacheQueue(this);
		}
	}
	
	private void initLogTimer(){
		timer = new Timer();
		timer.schedule(this, new Date(),60*1000L);
	}
	
	/**
	 * 注册缓存超时监听
	 * @param listener
	 */
	public void addListener(CacheTimeoutListener listener){
		if(!listeners.contains(listener)){
			listeners.add(listener);
		}
	}
	
	/**
	 * 移除缓存超时监听
	 * @param listener
	 */
	public void deleteListener(CacheTimeoutListener listener){
		if(listeners.contains(listener)){
			listeners.remove(listener);
		}
	}
	
	public void put(CacheObject object){
		getQueue(object.getKey()).put(object);
	}
	
	public synchronized void update(CacheObject srcObject,CacheObject destObject){
		getQueue(srcObject.getKey()).remove(srcObject);
		getQueue(destObject.getKey()).put(destObject);
	}
	
	public CacheObject find(String key){
		return getQueue(key).find(key);
	}
	
	private CacheQueue getQueue(String arg0){
		int code = arg0.hashCode();
		
		//沉余哈希算法取队列
		if(queues != null && queues.length != 0){
			int index = Math.abs(code%cacheSize);
			return queues[index];
		}
		return null;
	}
	
	protected void callBack(CacheObject object){
		for(CacheTimeoutListener listener:listeners){
			listener.callback(object);
		}
	}

	@Override
	public void run() {
		StringBuffer buffer = new StringBuffer();
		for(int i = 0;i < queues.length;i++){
			CacheQueue queue = queues[i];
			buffer.append(queue.size()).append("|");
		}
		logger.debug("["+nameSpace+"] size:"+queues.length+"\n"+buffer.toString());
	}

}

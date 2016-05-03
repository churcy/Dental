package com.tinytree.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description：缓存队列
 * @ClassName: CacheQueue
 * @Author：tangyang
 * @Date：2015-12-24
 * (变更历史)
 *	
 * tangyang	2016/02/01 进行了重构，支持通用Object的缓存使用
 *
 */
public class CacheQueue {
	
	private static final Logger logger = LoggerFactory.getLogger(CacheQueue.class);
	
	private DelayQueue<CacheObject> queue = null;
	
	private ConcurrentHashMap<String, CacheObject> map = null;
	
	private CachePool cachePool;
	
	protected CacheQueue(CachePool cachePool){
		queue = new DelayQueue<CacheObject>();
		map = new ConcurrentHashMap<>();
		this.cachePool = cachePool;
		initTimeOutThread();
	}
	
	protected CacheObject find(String key){
		if(key == null || "".equals(key)) return null;
		return map.get(key);
	}
	
	protected synchronized void put(CacheObject object){
		if(object == null){
			return;
		}
		
		queue.put(object);
		map.put(object.getKey(), object);
	}
	
	protected void remove(CacheObject object){
		if(object == null) return;
		remove(object.getKey());
	}
	
	protected synchronized void remove(String key){
		if(key == null) return;
		CacheObject object = map.remove(key);
		if(object == null) return;
		
		queue.remove(object);
	}
	
	protected int size(){
		return queue.size();
	}
	
	private void initTimeOutThread(){
		new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						CacheObject object = queue.take();
						if(object == null){
							continue;
						}
						map.remove(object.getKey());
						long startTime = object.getStartTime();
						long endTime = System.currentTimeMillis();
						logger.debug("key timeout:"+object.getKey());
						logger.debug("time:"+(endTime-startTime));
						cachePool.callBack(object);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	

}

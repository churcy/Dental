package com.tinytree.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description：缓存管理器
 * @ClassName: CacheObject
 * @Author：tangyang
 * @Date：2016-2-1
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class CacheManager {
	
	private ConcurrentHashMap<String,CachePool> cacheMap = new ConcurrentHashMap<>();
	
	private CacheManager(){}
	
	private static class CacheManagerHolder{
		private static CacheManager instance = new CacheManager();
	}
	
	public static CacheManager getInstance(){
		return CacheManagerHolder.instance;
	}
	
	/**
	 * 创建一个缓存池，如果已有则直接返回
	 * @param nameSpace，缓存命名空间，该函数将为每一个命名空间单独建立专属缓存池，不同命名空间不会共用一个缓存池
	 * @param poolSize,缓存池内部缓存列队个数，内部使用沉余哈希算法进行分流
	 * @return
	 */
	public CachePool createCachePool(String nameSpace,int poolSize){
		CachePool pool = cacheMap.get(nameSpace);
		if(pool == null){
			pool = new CachePool(nameSpace,poolSize); 
			cacheMap.put(nameSpace, pool);
		}
		
		return pool;
	}
	
	/**
	 * 创建一个缓存池，如果已有则直接返回
	 * @param nameSpace 缓存命名空间，该函数将为每一个命名空间单独建立专属缓存池，不同命名空间不会共用一个缓存池，内部缓存队列默认为10个
	 * @return
	 */
	public CachePool createCachePool(String nameSpace){
		return createCachePool(nameSpace, 10);
	}
}

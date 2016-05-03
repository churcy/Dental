/**
 * 
 */
package com.tinytree.cache;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.kungfu.dental.util.RandomUtil;

/**
 * @Description：缓存对象包装类
 * @ClassName: CacheObject
 * @Author：tangyang
 * @Date：2016-2-1
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class CacheObject implements Delayed{
	
	private long timeout = 0;
	
	private long startTime = 0;

	private String key;
	
	private Object value;
	
	private Integer type = -1;
	
	/**
	 * 
	 * @param source 缓存的具体对象
	 * @param timeout 超时时间，单位秒
	 */
	public CacheObject(Object source,long timeout){
		this(null,source,timeout);
	}
	
	/**
	 * 
	 * @param key缓存的key
	 * @param source 缓存的具体对象
	 * @param timeout 超时时间，单位秒
	 */
	public CacheObject(String key,Object source,long timeout){
		this.value = source;
		this.key = StringUtils.isBlank(key)?RandomUtil.uuid():key;
		this.timeout = timeout;
		this.startTime = System.currentTimeMillis();
	}
	
	@Override
	public int compareTo(Delayed o) {
		if(o == null) return 0;
		if(o == this) return 0;
		if(o instanceof CacheObject){
			CacheObject obj = (CacheObject)o;
			return startTime - obj.startTime > 0? 1:-1;
		}
		long diff = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return diff > 0 ? 1:diff == 0? 0:-1;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		long time = unit.convert((TimeUnit.MILLISECONDS.convert(timeout, TimeUnit.SECONDS))-
				(System.currentTimeMillis()-startTime)
				,TimeUnit.MILLISECONDS);
		return time;
	}
	
	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public long getStartTime() {
		return startTime;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 重置超时时间
	 */
	public void reset(){
		startTime = System.currentTimeMillis();
	}

}

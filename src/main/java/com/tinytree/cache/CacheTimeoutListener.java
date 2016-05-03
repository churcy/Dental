package com.tinytree.cache;


public interface CacheTimeoutListener {

	/**
	 * 超时通知
	 * @param object
	 */
	public void callback(CacheObject source);
}

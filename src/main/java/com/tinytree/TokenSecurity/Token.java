package com.tinytree.TokenSecurity;

import java.util.UUID;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Description：TOKEN
 * @ClassName: Token
 * @Author：tangyang
 * @Date：2015-12-24
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class Token{

	public static final long MAX_LIVE_TIME = 60*5L;//延迟时间5分钟
	
	private String value = null;
	
	public Token(){
		value = UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public String getValue(){
		return value;
	}
	
}

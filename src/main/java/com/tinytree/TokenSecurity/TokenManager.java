package com.tinytree.TokenSecurity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.tinytree.cache.CacheManager;
import com.tinytree.cache.CacheObject;
import com.tinytree.cache.CachePool;
import com.tinytree.encry.EncryptManager;
import com.tinytree.exception.EncryptException;
import com.tinytree.exception.ErrorTokenException;
import com.kungfu.dental.util.DateUtils;



/**
 * @Description：TOKEN管理
 * @ClassName: TokenManager
 * @Author：tangyang
 * @Date：2015-12-24
 * (变更历史)
 *	
 * tangyang	2016/02/01 重构了TokenManager代码，将token缓存独立出来，更具通用性
 *
 */
public class TokenManager {

	private static final String INIT_TOKEN = "kongfuDental";
	
	private static final String TOKEN_NAME_SPACE = "token";
	
	private static CachePool cachePool = CacheManager.getInstance().createCachePool(TOKEN_NAME_SPACE);
	
	private TokenManager(){}
	
	private static class TokenManagerHolder{
		private static TokenManager instance = new TokenManager();
	}
	
	public static TokenManager getInstance(){
		return TokenManagerHolder.instance;
	}
	
	/**
	 * 创建TOKEN
	 * @param inToken
	 * @return
	 * @throws ErrorTokenException
	 */
	public String createToken(String inToken) throws ErrorTokenException{
		if(inToken == null){
			return null;
		}
		
		if(checkToken(inToken)){
			Token token = new Token();
			CacheObject object = new CacheObject(token.getValue(), token, Token.MAX_LIVE_TIME);
			
			cachePool.put(object);
			return token.getValue();
		}
		else{
			throw new ErrorTokenException("ERROR IN TOKEN:"+inToken);
		}
	}
	
	/**
	 * 判断token是否超时
	 * @param inToken
	 * @return true:超时  false:没有超时
	 */
	public boolean isTimeout(String inToken){
		CacheObject object = cachePool.find(inToken);
		if(object == null){
			return true;
		}
		
		return false;
	}
	
	private boolean checkToken(String token){
		Date date = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		return checkToken(token,cal,true);
	}
	
	private boolean checkToken(String token,GregorianCalendar cal,boolean isFirst){
		String time = DateUtils.format2String(cal.getTime(), "MMdd");
		String key = null;
		String tokenValue = null;
		try {
			key = EncryptManager.getInstance(EncryptManager.MD5_TYPE).encrypt(time+INIT_TOKEN).substring(1,17);
			tokenValue = EncryptManager.getInstance(EncryptManager.AES_TYPE).decrypt(token, key);
		} 
		catch (EncryptException e) {
			e.printStackTrace();
		}
		
		if((time+INIT_TOKEN).equals(tokenValue)){
			return true;
		}
		else if(isFirst){
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int min = cal.get(Calendar.MINUTE);
			if(hour == 0 && min < 10){
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-1);
				return checkToken(token, cal,false);
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
		
	}
	
	/**
	 * 重置token的有效时间
	 * @param inToken
	 * @return
	 */
	public Token reset(String inToken){
		CacheObject object = cachePool.find(inToken);
		if(object != null){
			object.reset();
		}
		
		return (Token) object.getValue();
	}
	
}

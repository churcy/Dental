package com.tinytree.exception;

/**
 * @Description：封装异常基类
 * @ClassName: BaseException
 * @Author：tangyang
 * @Date：2015-12-24
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class BaseException extends Exception{

	private static final long serialVersionUID = -7440186613255476351L;

	private String message = "";
	
	private Exception e = null;
	
	public BaseException(){
		super();
	}
	
	public BaseException(String message){
		super(message);
		this.message = message;
	}
	
	public BaseException(String message,Throwable tx){
		super(message,tx);
		this.message = message;
	}
	
	public BaseException(String message,Exception e){
		super(message,e);
		this.message = message;
		this.e = e;
	}
	
	public String getMessage(){
		return message;
	}
	
	public String toString(){
		return message;
	}
	
	public Exception getException(){
		if(e != null){
			return e;
		}
		
		return this;
	}
}

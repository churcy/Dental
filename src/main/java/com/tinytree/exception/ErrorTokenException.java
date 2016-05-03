package com.tinytree.exception;

/**
 * @Description：TOKEN异常
 * @ClassName: ErrorTokenException
 * @Author：tangyang
 * @Date：2015-12-24
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class ErrorTokenException extends BaseException{

	private static final long serialVersionUID = 4218659079075935659L;

	public ErrorTokenException(String message){
		super(message);
	}
}

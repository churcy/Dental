package com.tinytree.interceptor;

import com.tinytree.entity.RequestLog;
import com.tinytree.util.GlobalUtil;
import com.tinytree.util.Message;
import com.tinytree.util.MessageQueue;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @Description：request请求参数拦截器
 * @ClassName: RequetsInterceptor
 * @Author：zhengzhong
 * @Date 2015-12-28
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功
 *
 */
public class RequetsInterceptor extends HandlerInterceptorAdapter{

	
	private ThreadLocal<Long> requestLogMap = new ThreadLocal<Long>();

	/**
	 * 在目标方法被调用前执行
	 * 返回true 继续调用后续的拦截器和目标方法
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.时间 time
		requestLogMap.set(System.currentTimeMillis());
		return true;
	}
	/**
	 * 调用目标方法之后,但渲染视图之前调用
	 * 可以对请求域的数据请求锦绣修改
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		long openingTime = requestLogMap.get();
		//2.操作系统  opertating systyem
		String agent = request.getHeader(GlobalUtil.USER_AGENT);
		
/*		String[] osList = agent.split("\\(");osListSub[0]
		String[] osListSub = osList[1].split("\\)");*/
		long closeingTime = System.currentTimeMillis();
		long consumeTime = closeingTime-openingTime;

		RequestLog requestLog = new RequestLog();
		requestLog.setConsumeTime(consumeTime);
		requestLog.setOpertatSystem(agent);
		requestLog.setIp(request.getRemoteAddr());
		requestLog.setToken((String) request.getHeader(GlobalUtil.TOKEN));
		requestLog.setOption((String) request.getParameter(GlobalUtil.OPT));
		Message message = new Message("REQUEST", requestLog);
		MessageQueue.getInstance().add(message);
	}
	/**
	 * 渲染视图之后本调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		//requestService.test();
	}
}

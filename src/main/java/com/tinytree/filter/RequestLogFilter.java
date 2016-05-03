package com.tinytree.filter;

import com.tinytree.entity.RequestLog;
import com.tinytree.util.GlobalUtil;
import com.tinytree.util.Message;
import com.tinytree.util.MessageQueue;
import com.tinytree.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @Description：访问日志过滤器
 * @ClassName: RequestLogFilter
 * @Author：tangyang
 * @Date：2016-02-15
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class RequestLogFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		ResponseWrapper responseWrapper = new ResponseWrapper(response);
		long startTime = System.currentTimeMillis();
		String opt = request.getParameter("opt");
		if(StringUtils.isBlank(opt)){
			return;
		}
		arg2.doFilter(request, responseWrapper);
		long entTime = System.currentTimeMillis();
		
		long consumeTime = entTime-startTime;
		String agent = request.getHeader(GlobalUtil.USER_AGENT);
		String content = responseWrapper.result();
		ResponseUtil.send2Client(content, null, response);
		JSONObject jsonObject = JSONObject.fromObject(content);
		RequestLog requestLog = new RequestLog();
		requestLog.setConsumeTime(consumeTime);
		requestLog.setOpertatSystem(agent);
		requestLog.setRequestTime(new Date());
		requestLog.setIp(request.getRemoteAddr());
		requestLog.setToken((String) request.getHeader(GlobalUtil.TOKEN));
		requestLog.setOption((String) request.getParameter(GlobalUtil.OPT));
		requestLog.setStatus((String) jsonObject.get("status"));
		Message  message = new Message("REQUEST", requestLog);
		MessageQueue.getInstance().add(message);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
}

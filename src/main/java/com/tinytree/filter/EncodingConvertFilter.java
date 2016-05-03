package com.tinytree.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description：拦截器 - 编码格式转换
 * @ClassName: EncodingConvertFilter
 * @Author：tangyang
 * @Date：2015-1-12
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */

public class EncodingConvertFilter implements Filter {

	private static final String FROM_ENCODING_PARAMETER_NAME = "fromEncoding";
	private static final String TO_ENCODING_PARAMETER_NAME = "toEncoding";
	
	private String fromEncoding = "ISO-8859-1";
	private String toEncoding = "UTF-8";
	
	public void init(FilterConfig filterConfig) {
		String fromEncodingParameter = filterConfig.getInitParameter(FROM_ENCODING_PARAMETER_NAME);
		String toEncodingParameter = filterConfig.getInitParameter(TO_ENCODING_PARAMETER_NAME);
		if (fromEncodingParameter != null) {
			fromEncoding = fromEncodingParameter;
		}
		if (toEncodingParameter != null) {
			toEncoding = toEncodingParameter;
		}
	}

	@SuppressWarnings({"unchecked" })
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		if (request.getMethod().equalsIgnoreCase("GET")) {
			Iterator iterator = request.getParameterMap().values().iterator();
			while(iterator.hasNext()) {
				String[] parames = (String[])iterator.next();
				for (int i = 0; i < parames.length; i++) {
					System.out.println("before"+parames[i]);
					try {
						parames[i] = new String(parames[i].getBytes(), toEncoding);
						System.out.println("after"+parames[i]);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		if (request.getMethod().equalsIgnoreCase("GET")) {
			Iterator iterator = request.getParameterMap().values().iterator();
			while(iterator.hasNext()) {
				String[] parames = (String[])iterator.next();
				for (int i = 0; i < parames.length; i++) {
					String string = parames[i];
					System.out.println(string);
				}
			}
		}
		
		chain.doFilter(servletRequest, servletResponse);
		
	}

	public void destroy() {}

}
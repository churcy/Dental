package com.tinytree.handler;

import com.tinytree.entity.RequestLog;
import com.tinytree.service.RequestLogService;
import com.tinytree.util.Message;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;



/**
 * @Description：异步消息处理
 * @ClassName: RequestLogHandler
 * @Author：zhengzhong
 * @Date 2015-12-28
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功
 *
 */
@Component
public class RequestLogHandler extends MessageHandler{
	
	@Resource
	private RequestLogService requestLogService;
	
	org.slf4j.Logger logger = LoggerFactory.getLogger(RequestLogHandler.class);
	
	@Override
	public void execute(List<Object> list)
	{
		if(list == null || list.isEmpty())
		{
			return;
		}
		
		//List<RequestLog> entitys = new ArrayList<RequestLog>();
		for(Object obj:list)
		{
			if(obj instanceof Message)
			{
				Message message = (Message)obj;
				//entitys.add((RequestLog)message.getSource());
				RequestLog log = (RequestLog)message.getSource();
				logger.info(log.toString());

			}
		}
		//requestLogService.save(entitys);
	}

}

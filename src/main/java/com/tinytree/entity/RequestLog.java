package com.tinytree.entity;

import com.tinytree.util.DateUtils;
import com.tinytree.util.GlobalUtil;
import org.apache.ibatis.type.Alias;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @Description:request请求基本信息
 * @ClassName: RequestLog
 * @Author：zhengzhong
 * @Date 2016-01-26
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
@Alias(value="request_log")
public class RequestLog extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1592737225614443181L;
	private String opertatSystem;//浏览器操作系统信息
	private long consumeTime;//接口方法执行时间
	private String ip;//用户的ip地址
	private String token;//用户的token数据
	private String opt;//调用接口的名称
	
	private Date requestTime;//接受请求的时间
	
	private String status;//请求执行结果状态
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getOption() {
		return opt;
	}
	public void setOption(String option) {
		this.opt = option;
	}
	public String getOpertatSystem() {
		return opertatSystem;
	}
	public void setOpertatSystem(String opertatSystem) {
		this.opertatSystem = opertatSystem;
	}
	public long getConsumeTime() {
		return consumeTime;
	}
	public void setConsumeTime(long consumeTime) {
		this.consumeTime = consumeTime;
	}
	
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
//		return "RequestLog [opertatSystem=" + opertatSystem + ", consumeTime="
//				+ consumeTime + ", ip=" + ip + ", token=" + token + ", option="
//				+ opt +",requestTime="+DateUtils.format2String(requestTime, GlobalUtil.DATETIME_PATTERN)
//				+", status="+status+"]";
		return "[requestlog]:|"+ DateUtils.format2String(requestTime, GlobalUtil.DATETIME_PATTERN)+"|"+opertatSystem+"|"+ip+"|"+
				opt+"|"+consumeTime+"|"+status+"|"+token;
	}
}

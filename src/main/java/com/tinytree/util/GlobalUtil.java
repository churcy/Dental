package com.tinytree.util;

public class GlobalUtil {
	
	public static final String TOKEN = "token";
	
	public static final String OPT = "opt";
	
	public static final String USER_AGENT = "User-Agent";
	
	public static final String APPKEY = "RC-App-Key";
	
	public static final String NONCE = "RC-Nonce";
	
	public static final String TIMESTAMP = "RC-Timestamp";
	
	public static final String SIGNATURE = "RC-Signature";
	
	public static final String UTF8 = "UTF-8";
	
	public static final String SUCCESS = "000";//成功
	
	public static final String SYS_ERROR = "901";//系统错误
	
	public static final String PARAM_ERROR = "00003";//请求参数错误
	
	public static final String USERNAME_EXIST_ERROR = "02001";//用户名存在
	
	public static final String USERPHONE_EXIST_ERROR = "02002";//手机号存在
	
	public static final String USERPHONE_NOT_ERROR = "02003";//手机号不存在
	
	public static final String ORGPHONE_NOT_ERROR = "02004";//原手机号不存在
	
	public static final String ACCOUNT_ERROR = "02005";//用户不存在(没有这个账号)
	
	public static final String ACCOUNT_SET_ERROR = "02006";//账号已有对应的用户信息
	
	public static final String USER_ERROR = "02007";//账号没有对应的用户信息
	
	public static final String PASSWORD_ERROR = "02008";//密码错误
	
	public static final String LONGIN_ERROR = "02009";//用户名或手机号不存在
	
	public static final String PHONE_ERROR = "02010";//系统中没有该手机号对应的数据
	
	public static final String CODE_ERROR = "02011";//验证码错误
	
	public static final String CODE_USED_ERROR = "02012";//验证码已使用
	
	public static final String CODE_OVERDUE_ERROR = "02013";//验证码已过期
	
	public static final String USERIMG_ERROR = "02014";//用户头像为空
	
	public static final String DOCTOR_ERROR = "02016";//医师id不存在
	
	public static final String DEPARTMENT_ERROR = "02017";//部门不存在
	
	public static final String HOSPITAL_ERROR = "02018";//医院不存在
	
	public static final String POSITION_ERROR = "02019";//职位不存在
	
	public static final String VISITINFO_ERROR = "02020";//就诊记录不存在
	
	public static final String REBACK_DETAIL_ERROR = "02021";//回访记录不存在
	
	public static final String GROUP_EXIST = "02022";//好友分组已存在
	
	public static final String FRIEND_EXIST = "02023";//好友已存在
	
	public static final String CRUD_ERROR = "02024";//数据库操作失败
	
	public static final String FRIEND_ERROR = "02025";//好友不存在或者有重复
	
	public static final String GROUP_ERROR = "02026";//分组不存在或者有重复
	
	public static final String FRIEND_AUTH_ERROR = "02027";//好友未通过验证
	
	public static final String CHILD_NODE_EXIST = "02028";//改节点还有子节点,不能删除
	
	public static final String MEDICAL_TEMPLATE_EXIST = "02029";//改节点已经存在模板
	
	public static final String MEDICAL_TEMPLATE_NOT_EXIST = "02030";//节点模板不存在
	
	public static final String DEFAULT_GROUP_ERROR = "02031";//分组为默认分组
	
	public static final String DOCTOR_EXIST_ERROR = "02032";//医师已存在
	
	public static final String HOSPITAL_EXIST_ERROR = "02033";//医院已存在
	
	public static final String DATETIME_PATTERN = "yyyyMMdd HH:mm:ss";
	
	public static final String DATE_PATTERN = "yyyyMMdd";
	
	public static final String DATETIME_MINUTE_PATTERN = "yyyyMMdd HH:mm";
	
	public static final String TIME_PATTERN = "HH:mm:ss";
	
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9])";//正则表达式：验证手机号
    
    public static final String RESERVE_OVER = "03001";//预约失效
    
    public static final String RESERVE_TIME_ERROR = "03002";//预约时间错误
    
    public static final String RESERVE_FULL = "03003";//预约已满

    public static final String STROKE_ID_ERROR = "06001";//行程ID不存在
    
    public static final String STROKE_ACCOUNTID_ERROR = "06002";//行程ID和账户ID不存在
    
    public static final String DOC_RES_MESSAGE = "您好，您今天有param0个预约，请您合理安排时间，祝你工作愉快！";
    
    public static final String PAT_RES_MESSAGE = "您好，您预约param0医生，预约时间是param1，请您及时就医，祝您生活愉快！";
   
    public static final String DOC_STR_MESSAGE = "您好，您在param0有一个行程安排，行程主题是param1！";

	public static final String DEFAULT_GROUP = "我的好友";

	

}

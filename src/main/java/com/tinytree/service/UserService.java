package com.tinytree.service;

import java.util.List;
import java.util.Map;

import com.tinytree.entity.DocSysSetting;
import com.tinytree.entity.User;
import com.tinytree.entity.UserSysSetting;
/**
 * @Description:用户信息相关服务
 * @ClassName: UserService
 * @Author：zhengzhong
 * @Date 2016-1-13
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface UserService extends BaseService<User, String>{

	/**
	 * 根据userId 查询用户信息
	 * @param userId
	 * @return 用户信息
	 */
	public Map<String, Object> getByUserId(String userId);

	/**
	 * 根据accountId 查询用户信息
	 * @param accountId
	 * @return
	 */
	public User getByAccountId(String accountId);
	/**
	 * 根据姓名 手机号等查询用户信息
	 * @param userId name phone
	 * @return
	 */
	public List<Map<String, String>> getByConditions(String userId,String name,String phone);

	/**
	 * 设置用户信息
	 * @param user
	 */
	public void setUserInfo(User user);

	/**
	 * 更新用户信息
	 * @param user
	 */
	public void modifyUserInfo(User user);

	/**
	 * @return 返回配置文件中的(0)绝对路径 和(1)相对路径
	 */
	public List<String> getFilePath();

	/**
	 * 根据手机号判断手机号是否使用
	 * @param userPhone
	 * @return
	 * @throws Exception 
	 */
	public int authMobile(String userPhone) throws Exception;
	
	/**
	 * 根据手机号查找注册的用户
	 * @param mobile
	 * @return
	 * @throws Exception 
	 */
	public User findRegUserByMobile(String mobile) throws Exception;
	
	/**
	 * 根据医生查询用户
	 * @param doctorId
	 * @return
	 */
	public User findUserByDoctorId(String doctorId);
	
	/**
	 * 获取系统设置信息
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> getSysSetting(String userId);

	/**
	 * 修改系统设置信息
	 * @param userSysSetting
	 * @return
	 */
	public int modifySysSetting(UserSysSetting userSysSetting);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public UserSysSetting getSysSettingByCondition(String userId);

	/**
	 * 保存系统设置信息
	 * @param userSysSetting
	 * @return
	 */
	public int saveSysSetting(UserSysSetting userSysSetting);

	/**
	 * 创建用户和默认分组
	 * @param user
	 * @param defaultGroup
	 * @param userId
	 * @return 2:数据库操作失败
	 */
	public int saveAndCreateDefaultGroup(User user, String defaultGroup);

}

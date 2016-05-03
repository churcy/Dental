package com.tinytree.dao;


import com.tinytree.entity.ShortMessage;
import org.springframework.stereotype.Repository;

/**
 * @Description:短信相关数据库操作接口
 * @ClassName: ShortMessageDao
 * @Author：zhengzhong
 * @Date 2016-1-8
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Repository
public interface ShortMessageDao extends  BaseDao<ShortMessage>{
	
	/**
	 * 根据手机号和验证码 查询 验证码的短信信息
	 * @param userPhone
	 * @param verificationCode
	 * @return
	 */
	//@Select("SELECT * FROM short_message WHERE user_phone = #{0} AND verification_code = #{1}")
	public ShortMessage findShortMessageByUserPhoneAndCode(String userPhone,String verificationCode);
}

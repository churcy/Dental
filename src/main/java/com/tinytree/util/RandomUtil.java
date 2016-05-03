package com.tinytree.util;

import java.util.Random;
import java.util.UUID;

/**
 * @Description：验证码生成代码,随机数
 * @ClassName: RandomUtil
 * @Author：zhengzhong
 * @Date 2015-12-30
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功
 *
 */
public class RandomUtil {

	public static String createVerificationCode(int number){
		
		Random random = new Random();
		String result = "";
		for (int i = 0; i < number; i++) {
			result += random.nextInt(10);
		}
		return result;
	}
	
	/**
	 * 获取UUID值
	 * @return
	 */
	public static String uuid(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(createVerificationCode(6));
	}
}

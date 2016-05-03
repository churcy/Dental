package com.tinytree.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @Description：反射处理工具类
 * @ClassName: DateUtils
 * @Author：tangyang
 * @Date：2015-12-24
 * (变更历史)
 *	
 * 如：eric	2015/01/08 修改了删除功能
 *
 */
public class ReflectUtil {
	public static Class load(String className) throws ClassNotFoundException{
		if(!StringUtils.isNotEmpty(className)){
			return null;
		}
		
		Class cla = Class.forName(className);
		return cla;
	}
	
	public static Object loadAndNewInstance(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		Class cla = load(className);
		if(cla == null){
			return null;
		}
		
		Object obj = cla.newInstance();
		return obj;
	}
	
	public static List<Field> getAllFields(Class cla){
		if(cla == null){
			return null;
		}
		
		Field[] fields = null;
		Field[] supFields = null;
		List<Field> list = new ArrayList<Field>();
		
		fields = cla.getDeclaredFields();
		
		Class supCla = cla.getSuperclass();
		if(supCla != null){
			supFields = supCla.getDeclaredFields();
		}
		
		for(int i = 0;i < fields.length;i++){
			list.add(fields[i]);
		}
		
		for(int j = 0;j < supFields.length;j++){
			list.add(supFields[j]);
		}
		
		return list;
	}
	
	/**
	 * 获取对象所有的属性字段（包括其父类）
	 * @param obj
	 * @return
	 */
	public static List<Field> getAllFields(Object obj){
		if(obj == null){
			return null;
		}
		
		Class cla = obj.getClass();
		return getAllFields(cla);
	}
	
	public static void setField(Object obj,Field field,Object value) throws IllegalArgumentException, IllegalAccessException{
		if(obj == null || field == null||value == null){
			return;
		}
		
		field.setAccessible(true);
		field.set(obj, value);
		field.setAccessible(false);
	}
}

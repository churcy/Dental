package com.tinytree.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tinytree.bean.RongCloudBean;
import com.tinytree.bean.UriBean;
import com.tinytree.bean.WeatherBean;

public class LoadProperty2Bean {
	private static final String [] BEAN_NAME = new String[]{"Weather","SMS","Uri","RongCloud"};
	
	private static final String BEAN_SUFFIX = "Bean";
	
	private static final String BEAN_PREFIX = "com.kungfu.dental.bean.";
	
	private static final String CONFIG_SUFFIX = ".properties";
	
	private static final Map<String, Object> BEANMAP = new HashMap<String, Object>();;
	
	public static final String WEATHER = "Weather";
	
	public static final String SMS = "SMS";
	
	public static final String RONGCLOUD = "RongCloud";
	
	public static final String URI = "Uri";
	
	private LoadProperty2Bean(){
		init();
	}
	public void init(){
		for(int i = 0;i<BEAN_NAME.length;i++ ){
			try {
				Object value = setBeanProperty(BEAN_PREFIX+BEAN_NAME[i]+BEAN_SUFFIX,BEAN_NAME[i]+CONFIG_SUFFIX);
				BEANMAP.put(BEAN_NAME[i], value);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	private static class LoadProperty2BeanHodler {
		private static LoadProperty2Bean instance = new LoadProperty2Bean();
	}
	
	public static LoadProperty2Bean getInstance(){
		return LoadProperty2BeanHodler.instance;
	}
	
	public Object getBean(String beanName){
		
		return BEANMAP.get(beanName);
	}
	
	public static Object setBeanProperty(String beanName,String configName) throws ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException, NoSuchMethodException, SecurityException{

		Class clazz = ReflectUtil.load(beanName);
		List<Field> list = ReflectUtil.getAllFields(clazz);
		//Method med=clazz.getMethod("getInstance");
		Constructor con = clazz.getDeclaredConstructor();  
        con.setAccessible(true); 
		Object obj = con.newInstance();
		LoadProperty loadProperty = new LoadProperty();
		Properties properties = loadProperty.load(configName);
		Map<String, String> map = getPropertyInfo(properties);
		
		for(int i=0;i<list.size();i++){
			Field field = list.get(i);
			String key = field.getName();
			ReflectUtil.setField(obj, field, map.get(key));
		}
		return obj;
	}

	public static Map<String, String> getPropertyInfo(Properties properties) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration en = properties.propertyNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String Property = properties.getProperty(key);
			map.put(key, Property);
		}
		return map;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
		 UriBean bean = (UriBean) setBeanProperty(BEAN_PREFIX+"Uri"+BEAN_SUFFIX,"Uri"+CONFIG_SUFFIX);
		 WeatherBean bean1 = (WeatherBean) setBeanProperty(BEAN_PREFIX+"Weather"+BEAN_SUFFIX,"Weather"+CONFIG_SUFFIX);
		 RongCloudBean bean2 = (RongCloudBean) setBeanProperty(BEAN_PREFIX+"RongCloud"+BEAN_SUFFIX,"RongCloud"+CONFIG_SUFFIX);
		 System.out.println(bean);
		 System.out.println(bean1);
		 System.out.println(bean2);
	}
}

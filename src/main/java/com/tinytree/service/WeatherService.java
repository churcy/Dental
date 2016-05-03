package com.tinytree.service;

import java.io.IOException;

import org.json.JSONException;

import com.tinytree.entity.Weather;
/**
 * @Description:天气信息服务
 * @ClassName: WeatherService
 * @Author：zhengzhong
 * @Date 2016-1-6
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface WeatherService {
	
	/**
	 * 获取天气信息,返回天气信息实体
	 * @param cityName
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public Weather getWeaterInfo(String cityName) throws IOException, JSONException;
}

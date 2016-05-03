package com.tinytree.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.tinytree.bean.WeatherBean;
import com.tinytree.entity.Weather;
import com.kungfu.dental.service.WeatherService;
import com.kungfu.dental.util.HttpUtil;
import com.kungfu.dental.util.LoadProperty2Bean;
/**
 * @Description:天气服务实现
 * @ClassName: WeratherServiceImpl
 * @Author：zhengzhong
 * @Date 2016-1-6
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("weatherService")
public class WeratherServiceImpl implements WeatherService{
	
	private static final WeatherBean weatherBean = (WeatherBean) LoadProperty2Bean.getInstance().getBean(LoadProperty2Bean.WEATHER);
	
	//参数为 城市名称的 全拼拼音
	public Weather getWeaterInfo(String cityName) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("apikey", weatherBean.getApikey());
		String url = weatherBean.getUrl();
		String weatherInfo = HttpUtil.httpGet(url,map,"citypinyin="+cityName);
		JSONObject jsonObject  = JSONObject.fromObject(weatherInfo);
		Object object = jsonObject.get("retData");
		String errMsg = jsonObject.get("errMsg").toString();
		if(StringUtils.isNotBlank(errMsg)&&errMsg.equals("success")){
			Weather weather = (Weather) JSONObject.toBean((JSONObject) object,Weather.class);
			return weather;
		}else{
			return null;
		}
	}

}

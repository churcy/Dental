package com.tinytree.bean;

public class WeatherBean {
	private String apikey;//应用api标识
	private String url;//请求url
	public String getApikey() {
		return apikey;
	}
	public void setApikey(String apikey) {
		this.apikey = apikey;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	private WeatherBean(){
		
	}
	private static class WeatherBeanHodler{
		private static WeatherBean instance = new WeatherBean();
	}
	
	public static WeatherBean getInstance(){
		return WeatherBeanHodler.instance;
	}
	@Override
	public String toString() {
		return "WeatherBean [apikey=" + apikey + ", url=" + url + "]";
	}
}

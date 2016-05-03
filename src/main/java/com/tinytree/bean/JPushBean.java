package com.tinytree.bean;

public class JPushBean {
	private String masterSecret;//应用密匙
	private String appKey;//应用表示
	@Override
	public String toString() {
		return "JPushBean [masterSecret=" + masterSecret + ", appKey=" + appKey
				+ "]";
	}
	public String getMasterSecret() {
		return masterSecret;
	}
	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
}

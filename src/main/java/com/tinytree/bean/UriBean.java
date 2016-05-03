package com.tinytree.bean;

public class UriBean {
	private String absoluteUri;//绝对路径
	
	private String relativeUri;//相对路径

	public String getAbsoluteUri() {
		return absoluteUri;
	}

	public void setAbsoluteUri(String absoluteUri) {
		this.absoluteUri = absoluteUri;
	}

	public String getRelativeUri() {
		return relativeUri;
	}

	public void setRelativeUri(String relativeUri) {
		this.relativeUri = relativeUri;
	}

	@Override
	public String toString() {
		return "UriBean [absoluteUri=" + absoluteUri + ", relativeUri="
				+ relativeUri + "]";
	}
}

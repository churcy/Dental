package com.tinytree.entity;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;
/**
 * @Description:图片基本信息
 * @ClassName: Images
 * @Author：zhengzhong
 * @Date 2016-01-26
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
@Alias(value="images")
public class Images extends BaseEntity {

	private static final long serialVersionUID = -7570158107086569608L;
	
	private String uri;//图片地址

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "Images [uri=" + uri + "]";
	}
}

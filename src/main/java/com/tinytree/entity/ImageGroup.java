package com.tinytree.entity;

import javax.persistence.Entity;

import org.apache.ibatis.type.Alias;
/**
 * @Description:图片组
 * @ClassName: ImageGroup
 * @Author：zhengzhong
 * @Date 2016-01-26
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Entity
@Alias(value="image_group")
public class ImageGroup extends BaseEntity{

	private static final long serialVersionUID = 6714228799258715833L;
	
	private String imagesId;//图片id 和 table  images 关联

	public String getImagesId() {
		return imagesId;
	}

	public void setImagesId(String imagesId) {
		this.imagesId = imagesId;
	}

	@Override
	public String toString() {
		return "Images [imagesId=" + imagesId + "]";
	}
}

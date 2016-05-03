package com.tinytree.dao;

import java.util.List;

import com.tinytree.entity.ImageVersion;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Description:图片发布版本数据库操作接口
 * @ClassName: ImageVersionDao
 * @Author：zhengzhong
 * @Date 2016-1-11
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */

public interface ImageVersionDao extends BaseDao<ImageVersion>{
	
	/**
	 * 根据appName appType usedBy 获取图片列表
	 * @param appName
	 * @param appType
	 * @param usedBy
	 * @return 图片列表信息 
	 */
	@Select("SELECT im.uri FROM images im LEFT JOIN image_group img ON im.id= img.images_id LEFT JOIN image_version imv ON img.id= imv.id WHERE img.id = (SELECT id FROM image_version WHERE app_name=#{0} AND app_type=#{1} AND used_by= #{2} AND status = 1)")
	public List<String>  getImages(int appName,int appType,int usedBy);
	
	/**
	 * 根据appName appType usedBy 获取版本号
	 * @param appName
	 * @param appType
	 * @param usedBy
	 * @return 版本号
	 */
	@Select("SELECT version FROM image_version WHERE app_name=#{0} AND app_type=#{1} AND used_by= #{2} AND status = 1")
	public String getVersion(int appName,int appType,int usedBy);
}

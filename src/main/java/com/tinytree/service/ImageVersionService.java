package com.tinytree.service;

import java.util.List;

import com.tinytree.entity.ImageVersion;
/**
 * @Description:图片发布版本服务接口
 * @ClassName: ImageVersionService
 * @Author：zhengzhong
 * @Date 2016-1-11
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
public interface ImageVersionService  extends BaseService<ImageVersion, String>{
	
	/**根据参数获取特定条件版本所对应的图片信息
	 * @param appName
	 * @param appType
	 * @param usedBy
	 * @return 图片信息
	 */
	public List<String> getImages(int appName,int appType,int usedBy);
	
	/**根据参数获取特定条件版本的版本号
	 * @param appName
	 * @param appType
	 * @param usedBy
	 * @return 版本号
	 */
	public String getVersion(int appName,int appType,int usedBy);
}

package com.tinytree.service.impl;

import com.tinytree.dao.ImageVersionDao;
import com.tinytree.entity.ImageVersion;
import com.tinytree.service.ImageVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * @Description:图片发布版本服务实现接口
 * @ClassName: ImageVersionServiceImpl
 * @Author：zhengzhong
 * @Date 2016-1-11
 * (变更历史)
 * 如：eric	2015/01/08 修改了删除功
 */
@Service("imageVersionService")
public class ImageVersionServiceImpl extends BaseServiceImpl<ImageVersion, String> implements ImageVersionService {
	@Autowired
	private ImageVersionDao imageVersionDao;
	
	public void setImageVersionDao(ImageVersionDao imageVersionDao) {
		this.imageVersionDao = imageVersionDao;
	}
	@Resource
	public void setBaseDao(ImageVersionDao imageVersionDao) {
		super.setBaseDao(imageVersionDao);
	}
	@Override
	public List<String> getImages(int appName, int appType, int usedBy) {
		List<String> list = imageVersionDao.getImages(appName, appType, usedBy);
		return list;
	}
	@Override
	public String getVersion(int appName, int appType, int usedBy) {
		String version = imageVersionDao.getVersion(appName, appType, usedBy);
		return version;
	}

}

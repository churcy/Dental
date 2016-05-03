package com.tinytree.dao;

import java.util.List;
import java.util.Map;

import com.kungfu.dental.entity.MedicalNode;

/**
 * @Author zhengzhong
 * @Description 病历节点数据库操作
 * @ClassName MedicalNodeDao
 * @Date 2016-03-21
 * 
 */
public interface MedicalNodeDao extends BaseDao<MedicalNode>{
	
	/**
	 * 查询父节点
	 * @return
	 */
	public MedicalNode getParentNode(String nodeId);
	
	/**
	 * 查询子节点
	 * @return
	 */
	public List<Map<String,Object>> getChildNodes(String nodeId);
	
	/**
	 * 移动节点
	 */
	public void modifyNodeLevle(String nodeId);
	
	/**
	 * 删除节点
	 */
	public void deleteNode(String nodeId);
	
	/**
	 * 查询节点
	 */
	public void getNode(String nodeId);

	/**
	 * 查询根节点
	 */
	public List<Map<String,Object>> getRootNodes();
}

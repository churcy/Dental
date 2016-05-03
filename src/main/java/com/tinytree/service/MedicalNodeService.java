package com.tinytree.service;

import java.util.List;
import java.util.Map;

import com.tinytree.entity.MedicalNode;

/**
 * @Author zhengzhong
 * @Description 节点操作服务类
 * @ClassName  MedicalNodeService
 * @Date 2016-03-21
 *
 */
public interface MedicalNodeService extends BaseService<MedicalNode, String>{
	
	/**
	 * 创建节点
	 * @param medicalNode
	 * @return 0:默认 1:成功 2:数据库操作失败
	 */
	public int createNode(MedicalNode medicalNode);

	/**
	 * 删除节点
	 * @param medicalNode
	 * @return 0:默认 1:成功 2:数据库操作失败
	 */
	public int deleteNode(MedicalNode medicalNode);
	
	/**
	 * 修改节点
	 * @param nodeId
	 * @return 0:默认 1:成功 2:数据库操作失败
	 */
	public int modifyNode(String nodeId);

	/**
	 * 查询父节点
	 * @param nodeId
	 * @return 0:默认 1:成功 2:数据库操作失败
	 */
	public MedicalNode getParentNode(String nodeId);
	/**
	 * 查询子节点
	 * @param nodeId
	 * @return 0:默认 1:成功 2:数据库操作失败
	 */
	public List<Map<String, Object>> getChildNodes(String nodeId);

	/**
	 * 查询所有的根节点
	 * @return
	 */
	public List<Map<String, Object>> getRootNodes();

	/**
	 * 查询节点by Id
	 * @param parentNodeId
	 * @return
	 */
	public MedicalNode getById(String nodeId);

}

package com.tinytree.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinytree.dao.GroupMembersDao;
import com.tinytree.dao.MedicalNodeDao;
import com.tinytree.entity.MedicalNode;
import com.kungfu.dental.service.MedicalNodeService;
/**
 * @Author zhengzhong
 * @Description 节点操作服务实现类
 * @ClassName  MedicalNodeServiceImpl
 * @Date 2016-03-21
 *
 */
@Service
public class MedicalNodeServiceImpl extends BaseServiceImpl<MedicalNode, String> implements MedicalNodeService{
	@Autowired
	private MedicalNodeDao medicalNodeDao;
	@Autowired
	public void setBaseDao(MedicalNodeDao medicalNodeDao){
		super.setBaseDao(medicalNodeDao);
	}
	@Override
	public int createNode(MedicalNode medicalNode) {
		int flag;
		try{
			medicalNodeDao.insert(medicalNode);
			flag = 1;
		}catch(Exception e){
			e.printStackTrace();
			flag = 2;
		}
		return flag;
	}

	@Override
	public int deleteNode(MedicalNode medicalNode) {
		int flag;
		try{
			medicalNodeDao.deleteByEntity(medicalNode);
			flag = 1;
		}catch(Exception e){
			e.printStackTrace();
			flag = 2;
		}
		return flag;
	}

	@Override
	public int modifyNode(String nodeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MedicalNode getParentNode(String nodeId) {
		MedicalNode medicalNode = medicalNodeDao.getParentNode(nodeId);
		return medicalNode;
	}

	@Override
	public List<Map<String, Object>> getChildNodes(String nodeId) {
		List<Map<String, Object>> result = medicalNodeDao.getChildNodes(nodeId);
		return result;
	}

	@Override
	public List<Map<String, Object>> getRootNodes() {
		List<Map<String, Object>> result = medicalNodeDao.getRootNodes();
		return result;
	}

	@Override
	public MedicalNode getById(String nodeId) {
		MedicalNode medicalNode = medicalNodeDao.selectByPrimaryKey(nodeId);
		return medicalNode;
	}



}

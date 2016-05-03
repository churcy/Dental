package com.tinytree.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tinytree.dao.FriendManageDao;
import com.tinytree.entity.GroupInfo;
import com.tinytree.entity.GroupMembers;
import com.tinytree.entity.User;
import com.kungfu.dental.service.DoctorService;
import com.kungfu.dental.service.FriendManageService;
import com.kungfu.dental.service.GroupInfoService;
import com.kungfu.dental.service.GroupMembersService;
import com.kungfu.dental.service.UserService;
import com.kungfu.dental.util.DateUtils;
import com.kungfu.dental.util.GlobalUtil;
@Controller
public class FreindManageServiceImpl extends BaseServiceImpl<User, String> implements FriendManageService {
	
	@Autowired
	private FriendManageDao friendManagerDao;
	@Autowired
	private GroupMembersService groupMembersService;
	@Autowired
	private GroupInfoService groupInfoService;
	@Autowired
	private UserService userService; 
	@Autowired
	private DoctorService doctorService;
	@Override
	public List<Map<String, Object>> getFriendList(String userId,String systemType,String registerType) {
		Map<String, String> map = new HashMap<String,String>();
		map.put("userId", userId);
		map.put("systemType", systemType);
		map.put("registerType", registerType);
		List<Map<String, Object>> result  = friendManagerDao.getFriendList(map);
		return result;
	}
	@Override
	public int addGroup(String groupName, String userId) {
		GroupInfo groupInfo = new GroupInfo();
		groupInfo.setName(groupName);
		groupInfo.setUserId(userId);
		if(groupName.equals(GlobalUtil.DEFAULT_GROUP)){
			groupInfo.setType(1);
		}
		int flag;
		try{
			do{
				List<Map<String, Object>> list = groupInfoService.getByConditons(userId, null, groupName);
				int size = list.size();
				if(size != 0){
					flag = 0;
					break;
				}
				groupInfoService.save(groupInfo);
				flag = 1;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			flag = 2;
		}
		return flag;
	}
	@Override
	public int deleteGroup(String userId,String groupId) throws Exception {
		int flag;
		try{
			do{
				List<Map<String, Object>> list= groupInfoService.getByConditons(userId, groupId, null);
				int size = list.size();
				if(size !=1){
					flag = 0;
					break;
				}
				int groupType = (int) list.get(0).get("groupType");
				if(groupType == 1){
					flag = 3;
					break;
				}
				//
				List<Map<String, Object>> defaultGroup= groupInfoService.getByConditons(userId, null, GlobalUtil.DEFAULT_GROUP);
				List<Map<String, Object>> registerGroupMember = groupMembersService.getByConditons(groupId, null, null, 1);
				List<Map<String, Object>> unRegisterGroupMember = groupMembersService.getByConditons(groupId, null, null, 0);
				int registerGroupMemberSize = registerGroupMember.size();
				int unRegisterGroupMemberSize = unRegisterGroupMember.size(); 
				int defaultGroupSize = defaultGroup.size(); 
				String defaultGroupId = null;
				if(defaultGroupSize!=0&&defaultGroupSize==1){
					defaultGroupId = (String) defaultGroup.get(0).get("groupId");
				}
				if(registerGroupMemberSize!=0){
					for(Map<String, Object> map :registerGroupMember){
						String friendUserId = (String) map.get("userId");
						this.moveFriend(userId,defaultGroupId,friendUserId);
					}
				}
				if(unRegisterGroupMemberSize!=0){
					for(Map<String, Object> map :unRegisterGroupMember){
						String friendUserId = (String) map.get("userId");
						this.moveFriend(userId,defaultGroupId,friendUserId);
					}
				}
				groupInfoService.delete(groupId);
				flag = 1;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			flag = 2;
		}
		return flag;
	}
	@Override
	public int modifyGroup(String groupName,String newName,String userId) throws Exception {
		int flag;
		try{
			do{
				List<GroupInfo> list= groupInfoService.getGroupByConditions(userId, null, groupName);
				if(list == null){
					flag = 0;
					break;
				}
				int size = list.size();
				if(size !=1){
					flag = 0;
					break;
				}
				GroupInfo groupInfo = list.get(0);
				groupInfo.setName(newName);
				groupInfoService.update(groupInfo);
				flag = 1;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			flag = 2;
		}
		return flag;
	}
	@Override
	public int addFriend(String groupId, String userId) {
		int flag;
		try{
			do{
				List<Map<String, Object>> list = groupMembersService.getByConditons(groupId, userId, null,1);
				int size = list.size();
				if(size != 0){
					flag = 0;
					break;
				}
				List<Map<String, Object>> list2 = groupMembersService.getByConditons(groupId, userId, null,0);
				int size2 = list2.size();
				if(size2 != 0){
					flag = 3;
					break;
				}
				GroupMembers groupMembers = new GroupMembers();
				List<Map<String, Object>> listDoctor = doctorService.getByUserId(userId);
				int doctorSize = listDoctor.size();
				if(doctorSize==1){
					groupMembers.setType(1);
				}else{
					groupMembers.setType(0);
				}
				groupMembers.setGroupId(groupId);
				groupMembers.setUserId(userId);
				groupMembers.setStatus(0);
				Map<String, Object> user = userService.getByUserId(userId);
				String realName = (String) user.get("realName");
				String accountId = (String) user.get("accountId");
				groupMembers.setUsername(realName);
				if(!StringUtils.isBlank(accountId)){
					groupMembers.setRegisterType(1);
				} 
				groupMembersService.save(groupMembers);
				flag = 1;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			flag = 2;
		}
		return flag;
	}
	@Override
	public int deleteFriend(String groupId, String userId,String groupMebId) {
		int flag;
		try{
			do{
				List<Map<String,Object>> list = groupMembersService.getByConditons(groupId,userId,groupMebId,3);
				int size = list.size();
				if(size!=1){
					flag = 0;
					break;
				}
				groupMebId = (String) list.get(0).get("groupMebId");
				groupMembersService.delete(groupMebId);
				flag = 1;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			flag = 2;
		}
		return flag;
	}
	@Override
	public int checkGroup(String userId, String groupName, String groupId) {
		int flag;
		try{
			do{
				List<Map<String,Object>> list = groupInfoService.getByConditons(userId, groupId, groupName);
				int size = list.size();
				if(size == 0){
					flag = 0;
					break;
				}
				flag = 1;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			flag = 2;
		}
		return flag;
	}
	@Override
	public int checkFriend(String userId, String friendUserId) {
		int flag;
		try{
			do{
				List<Map<String,Object>> list = groupMembersService.getFriend(userId, friendUserId);
				int size = list.size();
				if(size == 0){
					flag = 0;
					break;
				}
				flag = 1;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			flag = 2;
		}
		return flag;
	}
	@Override
	public List<Map<String, Object>> getGroupList(String userId) {
		List<Map<String, Object>> result = groupInfoService.getByConditons(userId, null, null);
		return result;
	}
	@Override
	public int moveFriend(String fromUserId,String groupId,String toUserId) {
		int flag;
		try{
			do{
				List<Map<String, Object>> result = groupMembersService.getFriend(fromUserId, toUserId);
				//List<Map<String, Object>> result = groupMembersService.getByConditons(null, toUserId, null,3);
				if(result==null){
					flag = 0;
					break;
				}
				Map<String, Object> map = result.get(0);
				String groupMebId = (String) map.get("groupMemberId");
				/*GroupMembers groupMembers = new GroupMembers();
				groupMembers.setGroupId(groupId);
				groupMembers.setUserId(toUserId);
				groupMembers.setUserId(toUserId);
				groupMembers.setUsername((String) map.get("userName"));*/
				GroupMembers groupMembers = groupMembersService.get(groupMebId);
				groupMembers.setGroupId(groupId);
				groupMembersService.update(groupMembers);
				flag = 1;
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
			flag = 2;
		}
		return flag;
	}
	@Override
	public Map<String, Object> getFriend(String userId,
			String friendUserId) {
		Map<String, Object> friendInfo = new HashMap<String, Object>();
		try{
			do{
				List<Map<String, Object>> result = groupMembersService.getFriend(userId, friendUserId);
				int size = result.size();
				if(size == 0){
					break;
				}
				friendInfo = result.get(0);
			}while(false);
		}catch(Exception e){
			e.printStackTrace();
		}
		return friendInfo;
	}
	
}

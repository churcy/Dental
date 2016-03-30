package com.tinytree.dental.entity;

/**
 * Created by 重 on 2016/3/29.
 */
public class GroupMember {
    private String id;
    private String groupId;//分组成员所属分组id
    private String groupMemberUserId;//分组成员id
    private String groupMemberUsername;//分组成员名称
    private String groupMemberStatus;//成员状态 0:未通过验证 1:通过验证

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupMemberUserId() {
        return groupMemberUserId;
    }

    public void setGroupMemberUserId(String groupMemberUserId) {
        this.groupMemberUserId = groupMemberUserId;
    }

    public String getGroupMemberUsername() {
        return groupMemberUsername;
    }

    public void setGroupMemberUsername(String groupMemberUsername) {
        this.groupMemberUsername = groupMemberUsername;
    }

    public String getGroupMemberStatus() {
        return groupMemberStatus;
    }

    public void setGroupMemberStatus(String groupMemberStatus) {
        this.groupMemberStatus = groupMemberStatus;
    }

    @Override
    public String toString() {
        return "GroupMember{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupMemberUserId='" + groupMemberUserId + '\'' +
                ", groupMemberUsername='" + groupMemberUsername + '\'' +
                ", groupMemberStatus='" + groupMemberStatus + '\'' +
                '}';
    }
}

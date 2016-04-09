package com.tinytree.dental.entity;

/**
 * Created by 重 on 2016/3/29.
 */
public class Group {
    private String id;//id
    private String groupName;//分组名称
    private String userId;//分组所属人id
    private int groupType;//分组类型 0:默认分组 1:一般分组

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", groupName='" + groupName + '\'' +
                ", userId='" + userId + '\'' +
                ", groupType='" + groupType + '\'' +
                '}';
    }
}

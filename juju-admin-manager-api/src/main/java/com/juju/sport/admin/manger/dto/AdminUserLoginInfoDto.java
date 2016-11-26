package com.juju.sport.admin.manger.dto;

import com.juju.sport.common.redis.RedisKeyPrefix;

import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ToString
@RedisKeyPrefix(prefixValue="admin:AdminUserLoginInfoDto:accountCode")
public class AdminUserLoginInfoDto implements Serializable{
	
	private static final long serialVersionUID = 4895587386966710764L;

	private boolean success;

	private String realName;
	
	private String groupName;

	private String accountCode;

	private String passwd;
	
	private RoleDto role;

	private List<AdminMenuGroupDto> groups = new ArrayList<AdminMenuGroupDto>();

    public boolean isSuccess() {
        return this.success;
    }

    public String getRealName() {
        return this.realName;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public String getAccountCode() {
        return this.accountCode;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public List<AdminMenuGroupDto> getGroups() {
        return this.groups;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setGroups(List<AdminMenuGroupDto> groups) {
        this.groups = groups;
    }

	public RoleDto getRole() {
		return role;
	}

	public void setRole(RoleDto role) {
		this.role = role;
	}
}

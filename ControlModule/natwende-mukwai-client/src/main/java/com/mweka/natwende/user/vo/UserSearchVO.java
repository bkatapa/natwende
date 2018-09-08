/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.user.vo;

import com.mweka.natwende.types.RoleType;
import com.mweka.natwende.types.Status;
import com.mweka.natwende.util.BaseSearchVO;
import java.util.List;

public class UserSearchVO extends BaseSearchVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7175063554664212350L;
	private String firstname, lastname, email, username;
	private String status = Status.ACTIVE.name();
	private Long parkingSiteId;
	private Long tenantId;
	private List<RoleType> roles;

	public UserSearchVO() {

	}

	public boolean hasFilters() {
		boolean result = false;
		if (firstname != null && !firstname.isEmpty()) {
			result = true;
		}
		if (lastname != null && !lastname.isEmpty()) {
			result = true;
		}
		if (email != null && !email.isEmpty()) {
			result = true;
		}
		if (username != null && !username.isEmpty()) {
			result = true;
		}
		if (status != null && !status.isEmpty()) {
			result = true;
		}
		if (roles != null && !roles.isEmpty()) {
			result = true;
		}
		if (parkingSiteId != null && parkingSiteId > 0) {
			result = true;
		}
		if (tenantId != null && tenantId > 0) {
			result = true;
		}
		return result;
	}

	public void clearSearch() {
		this.firstname = null;
		this.lastname = null;
		this.email = null;
		this.username = null;
		this.parkingSiteId = null;
		this.tenantId = null;
		this.status = Status.ACTIVE.name();
		this.roles = null;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<RoleType> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleType> roles) {
		this.roles = roles;
	}

	public Long getParkingSiteId() {
		return parkingSiteId;
	}

	public void setParkingSiteId(Long parkingSiteId) {
		this.parkingSiteId = parkingSiteId;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public void setRolesForUser(UserVO userVO) {

	}

}

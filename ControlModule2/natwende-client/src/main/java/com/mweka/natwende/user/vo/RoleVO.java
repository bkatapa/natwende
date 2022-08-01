/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.user.vo;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.types.RoleType;

public class RoleVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private RoleType roleType;

    public RoleVO() {

    }

    public RoleVO(RoleType roleType) {
        this.roleType = roleType;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    @Override
    public String toString() {
    	if (roleType == null) {
    		return "";
    	}
    	return roleType.getDescription();
    }
}

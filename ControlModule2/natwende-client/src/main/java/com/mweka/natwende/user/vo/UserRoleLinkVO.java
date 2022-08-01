/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.user.vo;

import com.mweka.natwende.base.vo.BaseVO;

public class UserRoleLinkVO extends BaseVO {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserVO user;
    private RoleVO role;
    
    public UserRoleLinkVO() {}
    
    public UserRoleLinkVO(UserVO userVO, RoleVO roleVO) {
    	this.user = userVO;
    	this.role = roleVO;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public RoleVO getRole() {
        return role;
    }

    public void setRole(RoleVO role) {
        this.role = role;
    }

}

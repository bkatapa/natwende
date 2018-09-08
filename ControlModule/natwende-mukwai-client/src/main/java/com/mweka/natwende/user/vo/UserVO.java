package com.mweka.natwende.user.vo;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.operator.vo.OperatorVO;

public class UserVO extends BaseVO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1, max = 255, message = "Username field is empty!")
    private String username;

    @NotNull
    @Size(min = 1, max = 255, message = "Email field is empty!")
    private String email;
    
    @Size(max = 100)
    private String contactNumber;

	@Size(max = 50)
    private String passwd;
	
	@Size(max = 50)
    private String confirmPasswd;

    @NotNull
    @Size(min = 1, max = 100, message = "First name field is empty!")
    private String firstname;

    @NotNull
    @Size(min = 1, max = 100, message = "Last name field is empty!")
    private String lastname;

    private OperatorVO operator;

    private List<RoleVO> roleVOs;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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

    public List<RoleVO> getRoleVOs() {
        return roleVOs;
    }

    public void setRoleVOs(List<RoleVO> roleVOs) {
        this.roleVOs = roleVOs;
    }

    public String getFullname() {
        return firstname + " " + lastname;
    }	
    
    public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public OperatorVO getOperator() {
		return operator;
	}

	public void setOperator(OperatorVO operator) {
		this.operator = operator;
	}

	public String getConfirmPasswd() {
		return confirmPasswd;
	}

	public void setConfirmPasswd(String confirmPasswd) {
		this.confirmPasswd = confirmPasswd;
	}

}

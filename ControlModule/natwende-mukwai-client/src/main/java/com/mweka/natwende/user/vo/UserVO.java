package com.mweka.natwende.user.vo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.location.vo.EmbeddedAddressVO;
import com.mweka.natwende.operator.vo.OperatorVO;
import com.mweka.natwende.types.RoleType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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
    private String nrc;

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
    
    private Date dob;

    private OperatorVO operator;
    
    private EmbeddedAddressVO address;

    @XmlTransient
    private List<RoleVO> roleVOs;
    
    @XmlTransient
    private List<RoleType> permissions;
    
    @XmlTransient
    @com.fasterxml.jackson.annotation.JsonIgnore
    private byte[] profilePic;
    
    public UserVO() {    	
    }
    
    public UserVO(String firstName, String lastName, String email) {
    	this.firstname = firstName;
    	this.lastname = lastName;
    	this.email = email;
    }
    
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

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}	

	public EmbeddedAddressVO getAddress() {
		if (address == null) {
			address = new EmbeddedAddressVO();
		}
		return address;
	}

	public void setAddress(EmbeddedAddressVO address) {
		this.address = address;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	//@JsonProperty(access = Access.WRITE_ONLY)
	public byte[] getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}

	public String getName() {
		return firstname + " " + lastname;
	}
	
	public InputStream getProfilePicAsStream() {
		return new ByteArrayInputStream(profilePic);
	}

	public List<RoleType> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<RoleType> permissions) {
		this.permissions = permissions;
	}
	
}

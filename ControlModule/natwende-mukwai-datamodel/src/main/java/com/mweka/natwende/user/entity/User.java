package com.mweka.natwende.user.entity;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.location.entity.EmbeddedAddress;
import com.mweka.natwende.operator.entity.Operator;

@Entity
@Table(name = "Users",uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username"}),
    @UniqueConstraint(columnNames = {"email"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = User.NAMED_QUERY_FIND_ALL_USERNAMES, query = "SELECT u.username FROM User u"),
    @NamedQuery(name = User.NAMED_QUERY_FIND_ALL_EMAILS, query = "SELECT u.email FROM User u"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPasswd", query = "SELECT u FROM User u WHERE u.passwd = :passwd"),
    @NamedQuery(name = "User.findByFirstname", query = "SELECT u FROM User u WHERE u.firstname = :firstname"),
    @NamedQuery(name = "User.findByLastname", query = "SELECT u FROM User u WHERE u.lastname = :lastname"),
    @NamedQuery(name = "User.findByStatus", query = "SELECT u FROM User u WHERE u.status = :status"),
    @NamedQuery(name = "User.findByPermissions", query = "SELECT u FROM UserRoleLink link JOIN link.user u WHERE link.role.roleType IN (:roleTypes)"),
    @NamedQuery(name = "User.findByOperatorId", query = "SELECT u FROM User u WHERE u.operator.id = :operatorId"),
    @NamedQuery(name = User.NAMED_QUERY_FIND_BY_OPERATOR_NAME, query = "SELECT u FROM User u WHERE u.operator.name = :operatorName"),
    @NamedQuery(name = User.NAMED_QUERY_FIND_LIST_BY_OPERATOR_NAME, query = "SELECT uol.user FROM UserOperatorLink uol WHERE uol.operator.name = :operatorName"),
    @NamedQuery(name = User.NAMED_QUERY_FIND_BY_ROLETYPE_AND_OPERATOR_NAME, query = " SELECT url.user FROM UserRoleLink url WHERE url.role.roleType = :_roleType AND url.user.operator.name = :operatorName"),
    @NamedQuery(name = User.NAMED_QUERY_FIND_LIST_BY_ROLETYPE_AND_OPERATOR_NAME, query = "SELECT url.user FROM UserRoleLink url, UserOperatorLink uol WHERE url.user.id = uol.user.id AND url.role.roleType = :_roleType AND uol.operator.name = :operatorName"),
    @NamedQuery(name = User.NAMED_QUERY_FIND_BY_PAYMENT_REF, query = " SELECT r.customer FROM Reservation r WHERE r.payment.ref = :paymentRef "),
    @NamedQuery(name = User.NAMED_QUERY_FIND_BY_NRC, query = " SELECT u FROM User u WHERE u.nrc LIKE :nrc ")
})
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;
    
    // Named Query Constants
    public static final String NAMED_QUERY_FIND_NON_REGISTERED_USERS = "User.findNonRegisteredUsersBySupplierId";
    public static final String NAMED_QUERY_FIND_USERS_BY_PERMISSIONS = "User.findByPermissions";
    public static final String NAMED_QUERY_FIND_ALL_USERNAMES = "User.findAllUsernames";
    public static final String NAMED_QUERY_FIND_ALL_EMAILS = "User.findAllEmails";
    public static final String NAMED_QUERY_FIND_BY_OPERATOR_NAME = "User.findByOperatorName";
    public static final String NAMED_QUERY_FIND_LIST_BY_OPERATOR_NAME = "User.findListByOperatorName";
    public static final String NAMED_QUERY_FIND_BY_ROLETYPE_AND_OPERATOR_NAME = "User.findByRoleTypeAndOperatorName";
    public static final String NAMED_QUERY_FIND_LIST_BY_ROLETYPE_AND_OPERATOR_NAME = "User.findListByRoleTypeAndOperatorName";
    public static final String NAMED_QUERY_FIND_BY_PAYMENT_REF = "User.findByPaymentRef";
    public static final String NAMED_QUERY_FIND_BY_NRC = "User.findByNrc";
    
    // Query Parameters
    public static transient final String PARAM_USER_ID = "userId";
    public static transient final String PARAM_NRC = "nrc";

    @Size(max = 255)
    @Column(length = 255)
    private String username;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(length = 255)
    private String email;
    
    @Size(max = 255)
    @Column(length = 255)
    private String nrc;
    
    @Size(max = 45)
	@Column(length = 45)
	private String contactNumber;
    
	@Size(max = 50)
    @Column(length = 50)
    private String passwd;
	
    @Size(max = 100)
    @Column(length = 100)
    private String firstname;
    
    @Size(max = 100)
    @Column(length = 100)
    private String lastname;
    
    @JoinColumn(name = "operator_id", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Operator operator;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserRoleLink> userRoleLinkList;
    
    @Embedded
    private EmbeddedAddress address;
    
    @Lob
    @Basic(fetch = FetchType.LAZY, optional = true)
    private byte[] profilePic;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
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

    public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	@XmlTransient
    public List<UserRoleLink> getUserRoleLinkList() {
        return userRoleLinkList;
    }

    public void setUserRoleLinkList(List<UserRoleLink> userRoleLinkList) {
        this.userRoleLinkList = userRoleLinkList;
    }

    @Override
    public String toString() {
        return "com.adaptris.skidata.parking.entity.User[ id=" + id + " ]";
    }

    public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public EmbeddedAddress getAddress() {
		if (address == null) {
			address = new EmbeddedAddress();
		}
		return address;
	}

	public void setAddress(EmbeddedAddress address) {
		this.address = address;
	}

	public byte[] getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}	
	
}

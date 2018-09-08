package com.mweka.natwende.location.entity;

import com.mweka.natwende.base.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Address")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a"),
    @NamedQuery(name = "Address.findById", query = "SELECT a FROM Address a WHERE a.id = :id"),
    @NamedQuery(name = "Address.findByName", query = "SELECT a FROM Address a WHERE a.name = :name"),
    @NamedQuery(name = "Address.findByLine1", query = "SELECT a FROM Address a WHERE a.line1 = :line1"),
    @NamedQuery(name = "Address.findByCity", query = "SELECT a FROM Address a WHERE a.city = :city"),
    @NamedQuery(name = "Address.findByProvince", query = "SELECT a FROM Address a WHERE a.province = :province"),
    @NamedQuery(name = "Address.findByPostalCode", query = "SELECT a FROM Address a WHERE a.postalCode = :postalCode"),
    @NamedQuery(name = "Address.findByCountry", query = "SELECT a FROM Address a WHERE a.country = :country")
})
public class Address extends BaseEntity {
	
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Size(max = 45)
    @Column(length = 45)
    private String name;
	
    @Size(max = 255)
    @Column(length = 255)
    private String line1;
    
    @Size(max = 255)
    @Column(length = 255)
    private String street;
    
    @Size(max = 255)
    @Column(length = 255)
    private String surbub;
    
    @Size(max = 255)
    @Column(length = 255)
    private String city;
    
    @Size(max = 255)
    @Column(length = 255)
    private String province;
    
    @Size(max = 255)
    @Column(length = 255)
    private String postalCode;
    
    @Size(max = 255)
    @Column(length = 255)
    private String country;

    public Address() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSurbub() {
		return surbub;
	}

	public void setSurbub(String surbub) {
		this.surbub = surbub;
	}

	@Override
    public String toString() {
        return "com.mweka.natwende.entity.Address[ id=" + id + " ]";
    }

}

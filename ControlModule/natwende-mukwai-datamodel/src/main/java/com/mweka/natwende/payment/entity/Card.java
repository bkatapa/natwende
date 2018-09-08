package com.mweka.natwende.payment.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.user.entity.User;

@Entity
@Table(name = "card")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Card.findAll", query="SELECT c FROM Card c"),    
})
public class Card extends BaseEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5765233713857557116L;

	@NotNull
	private String cardNumberEncrypted;
	
	@NotNull
	private String expiryDate;
	
	@NotNull
	private String cvv2;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private User owner;
	
	@NotNull
	private String nameOnCard;
	
	public Card(String cardNumberEncrypted, String expiryDate, String cvv2, String nameOnCard) {
		super();
		this.cardNumberEncrypted = cardNumberEncrypted;
		this.expiryDate = expiryDate;
		this.cvv2 = cvv2;
		this.nameOnCard = nameOnCard;
	}	
	
	public Card(User owner) {
		super();
		this.owner = owner;
	}

	public Card(String cardNumberEncrypted, String expiryDate, String cvv2) {
		super();
		this.cardNumberEncrypted = cardNumberEncrypted;
		this.expiryDate = expiryDate;
		this.cvv2 = cvv2;
	}

	public Card() {
		super();
	}

	public String getCardNumberEncrypted() {
		return cardNumberEncrypted;
	}
	
	public void setCardNumberEncrypted(String cardNumberEncrypted) {
		this.cardNumberEncrypted = cardNumberEncrypted;
	}
	
	public String getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public String getCvv2() {
		return cvv2;
	}
	
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
}

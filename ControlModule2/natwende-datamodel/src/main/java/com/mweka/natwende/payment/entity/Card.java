package com.mweka.natwende.payment.entity;

import javax.persistence.Column;
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
@Table(name = "Cards")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Card.QUERY_FIND_ALL, query=" SELECT c FROM Card c "),
    @NamedQuery(name = Card.QUERY_FIND_BY_USER_ID, query=" SELECT c FROM Card c WHERE c.owner.id = :userId ")
})
public class Card extends BaseEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5765233713857557116L;
	
	// Named Queries
	public static transient final String QUERY_FIND_ALL = "Card.findAll";
	public static transient final String QUERY_FIND_BY_USER_ID = "Card.findByUserId";
	
	// Query Parameters
	public static transient final String PARAM_CARD_ID = "cardId";

	@NotNull
	@Column(name = "card_number_encrypted")
	private String cardNumberEncrypted;
	
	@NotNull
	@Column(name = "expiry_date")
	private String expiryDate;
	
	@NotNull
	@Column(name = "cvv_2")
	private String cvv2;
	
	@Column(name = "is_primary")
	private boolean primary;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private User owner;
	
	@NotNull
	@Column(name = "name_on_card")
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

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	
}

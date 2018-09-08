package com.mweka.natwende.payment.vo;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.user.vo.UserVO;

public class CardVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1731957579334602696L;
	
	private String cardNumberEncrypted;
	private String expiryDate;
	private String cvv2;
	private UserVO owner;
	private String nameOnCard;
	
	public CardVO(String cardNumberEncrypted, String expiryDate, String cvv2, String nameOnCard) {
		super();
		this.cardNumberEncrypted = cardNumberEncrypted;
		this.expiryDate = expiryDate;
		this.cvv2 = cvv2;
		this.nameOnCard = nameOnCard;
	}	
	
	public CardVO(UserVO owner) {
		super();
		this.owner = owner;
	}

	public CardVO(String cardNumberEncrypted, String expiryDate, String cvv2) {
		super();
		this.cardNumberEncrypted = cardNumberEncrypted;
		this.expiryDate = expiryDate;
		this.cvv2 = cvv2;
	}

	public CardVO() {
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
	
	public UserVO getOwner() {
		return owner;
	}
	
	public void setOwner(UserVO owner) {
		this.owner = owner;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

}

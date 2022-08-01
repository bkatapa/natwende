package com.mweka.natwende.payment.facade;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.payment.vo.CardVO;

@Stateless
@LocalBean
public class CardFacade extends AbstractFacade<CardVO> {

	public CardFacade() {
		super(CardVO.class);
	}
	
	public void updateCards(List<CardVO> cardList, boolean primaryFlag) {
		for (CardVO card : cardList) {
			card.setPrimary(primaryFlag);
			serviceLocator.getCardDataFacade().update(card);
		}
	}
}

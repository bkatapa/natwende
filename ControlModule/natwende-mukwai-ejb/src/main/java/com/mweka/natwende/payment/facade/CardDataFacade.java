package com.mweka.natwende.payment.facade;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.payment.entity.Card;
import com.mweka.natwende.payment.vo.CardVO;
import com.mweka.natwende.user.entity.User;
import com.mweka.natwende.user.vo.UserVO;

@Stateless
public class CardDataFacade extends AbstractDataFacade<CardVO, Card> {

    public CardDataFacade() {
        super(CardVO.class, Card.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected Card updateEntity(CardVO vo) {
        Card entity = vo.getId() > 0 ? findById(vo.getId()) : new Card();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(Card entity, CardVO vo) {
    	super.convertBaseEntityToVO(entity, vo);
        vo.setCardNumberEncrypted(entity.getCardNumberEncrypted());
        vo.setCvv2(entity.getCvv2());
        vo.setExpiryDate(entity.getExpiryDate());
        vo.setNameOnCard(entity.getNameOnCard());
        vo.setPrimary(entity.isPrimary());
        
        if (entity.getOwner() != null) {
        	vo.setOwner(serviceLocator.getUserDataFacade().getCachedVO(entity.getOwner()));
        }
    }

    @Override
    public Card convertVOToEntity(CardVO vo, Card entity) {
        convertBaseVOToEntity(vo, entity);
        entity.setCardNumberEncrypted(vo.getCardNumberEncrypted());
        entity.setCvv2(vo.getCvv2());
        entity.setExpiryDate(vo.getExpiryDate());
        entity.setNameOnCard(vo.getNameOnCard());
        entity.setPrimary(vo.isPrimary());
        
        if (vo.getOwner() != null) {
        	entity.setOwner(serviceLocator.getUserDataFacade().findById(vo.getOwner().getId()));
        }
        return entity;
    }

    @Override
    public CardVO update(CardVO vo) {
        Card entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public List<CardVO> getListByUser(UserVO user) {
    	List<Card> resultList = createNamedQuery(Card.QUERY_FIND_BY_USER_ID, getEntityClass())
    			.setParameter(User.PARAM_USER_ID, user.getId())
    			.getResultList();
    	return transformList(resultList);
    }
    
}

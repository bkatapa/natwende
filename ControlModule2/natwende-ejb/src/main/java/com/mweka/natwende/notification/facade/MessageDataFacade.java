package com.mweka.natwende.notification.facade;

import javax.ejb.Stateless;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.message.MessageVO;
import com.mweka.natwende.notification.entity.Message;

@Stateless
public class MessageDataFacade extends AbstractDataFacade<MessageVO, Message>  {
	
	public MessageDataFacade() {
		super(MessageVO.class, Message.class);
	}

	@Override
	protected void convertEntitytoVO(Message entity, MessageVO vo) {
		super.convertBaseEntityToVO(entity, vo);
		vo.setDetail(entity.getDetail());
		vo.setSummary(entity.getSummary());
		vo.setSeverity(entity.getSeverity());
	}

	@Override
	protected Message convertVOToEntity(MessageVO vo, Message entity) {
		super.convertBaseVOToEntity(vo, entity);
		entity.setDetail(vo.getDetail());
		entity.setSummary(vo.getSummary());
		entity.setSeverity(vo.getSeverity());		
		return entity;
	}

	@Override
	protected Message updateEntity(MessageVO vo) throws EntityNotFoundException {		
		Message entity = vo.getId() > 0 ? findById(vo.getId()) : new Message();
		convertVOToEntity(vo, entity);
		update(entity);
		return entity;
	}	
}

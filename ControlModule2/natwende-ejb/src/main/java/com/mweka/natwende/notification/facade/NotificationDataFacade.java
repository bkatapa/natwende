package com.mweka.natwende.notification.facade;

import javax.ejb.Stateless;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.notification.entity.Notification;
import com.mweka.natwende.notification.vo.NotificationVO;

@Stateless
public class NotificationDataFacade extends AbstractDataFacade<NotificationVO, Notification>  {
	
	public NotificationDataFacade() {
		super(NotificationVO.class, Notification.class);
	}

	@Override
	protected void convertEntitytoVO(Notification entity, NotificationVO vo) {
		super.convertBaseEntityToVO(entity, vo);
		vo.setSubmitDate(entity.getSubmitDate());
		vo.setNotitifcationStatus(entity.getNotitifcationStatus());
		vo.setSubject(entity.getSubject());
		if (entity.getMessage() != null) {
			vo.setMessage(serviceLocator.getMessageDataFacade().getCachedVO(entity.getMessage()));
		}
	}

	@Override
	protected Notification convertVOToEntity(NotificationVO vo,	Notification entity) {
		super.convertBaseVOToEntity(vo, entity);
		entity.setSubmitDate(vo.getSubmitDate());
		entity.setNotitifcationStatus(vo.getNotitifcationStatus());
		entity.setSubject(vo.getSubject());		
		if (vo.getMessage() != null) {
			entity.setMessage(serviceLocator.getMessageDataFacade().findById(vo.getMessage().getId()));
		}
		return entity;
	}

	@Override
	protected Notification updateEntity(NotificationVO vo) throws EntityNotFoundException {		
		Notification entity = vo.getId() > 0 ? findById(vo.getId()) : new Notification();
		convertVOToEntity(vo, entity);
		update(entity);
		return entity;
	}	

}

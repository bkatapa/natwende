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
				
//		vo.setSenderVO(serviceLocator.getParkingSiteDataFacade().getCachedVO(entity.getSender()));
		vo.setSubmitDate(entity.getSubmitDate());
		vo.setNotitifcationStatus(entity.getNotitifcationStatus());
		vo.setSubject(entity.getSubject());		
		vo.setMessage(entity.getMessage());
	}

	@Override
	protected Notification convertVOToEntity(NotificationVO vo,	Notification entity) {
		convertBaseVOToEntity(vo, entity);
		
//		entity.setSender(serviceLocator.getParkingSiteDataFacade().findById(vo.getSenderVO().getId()));
		entity.setSubmitDate(vo.getSubmitDate());
		entity.setNotitifcationStatus(vo.getNotitifcationStatus());
		entity.setSubject(vo.getSubject());		
		entity.setMessage(vo.getMessage());		
		
		return entity;
	}

	@Override
	protected Notification updateEntity(NotificationVO vo) throws EntityNotFoundException {		
		Notification entity;
		if (vo.getId() > 0) {
			entity = findById(vo.getId());
		} else {
			entity = new Notification();
		}
		convertVOToEntity(vo, entity);
		update(entity);
		return entity;
	}	

}

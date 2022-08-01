package com.mweka.natwende.trip.action;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.trip.vo.ReservationVO;
import com.mweka.natwende.user.action.UserAction;

@Named("ReservationAction")
@SessionScoped
public class ReservationAction extends MessageHelper<ReservationVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 639186094057241940L;

	@Inject
	private UserAction userAction;
	
	@PostConstruct
	public void init() {
		selectedEntity = new ReservationVO();
	}
	
	@Override
	public List<ReservationVO> getEntityList() {
		loadEntityList();
		return entityList;
	}

	@Override
	public String createEntity() {
		init();
		return null;
	}

	@Override
	public String saveEntity() {
		selectedEntity.setCustomer(userAction.getSelectedEntity());
		selectedEntity = serviceLocator.getReservationDataFacade().update(selectedEntity);
		executeScript("PF('var_CardViewDlg').hide();");
		onMessage(SEVERITY_INFO, "Card saved successfully");
		return null;
	}

	@Override
	public String viewEntity() {
		updateComponent("@widgetVar(var_CardViewDlg)");
		executeScript("PF('var_CardViewDlg').show();");
		return null;
	}

	@Override
	public void deleteEntity() {
		try {
			serviceLocator.getReservationDataFacade().deleteById(selectedEntity.getId());
		} catch (EntityNotFoundException e) {
			log.debug(e);
			onMessage(SEVERITY_ERROR, e.getMessage());
		}		
	}
	
	private void loadEntityList() {
		entityList = serviceLocator.getReservationDataFacade().getListByCustomer(userAction.getLoggedInUser());
	}
}

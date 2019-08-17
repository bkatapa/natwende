package com.mweka.natwende.operator.action;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

//import org.primefaces.context.RequestContext;

import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.helper.VelocityGen;
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.types.Status;
import com.mweka.natwende.util.ServiceLocator;

@Named("BusAction")
@SessionScoped
public class BusAction extends MessageHelper<BusVO> {

private static final long serialVersionUID = 1L;
	
	//private BusSearchVO searchVO;
	private boolean editSeats;

	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	private OperatorAction operatorAction;
	
	@Inject
	private VelocityGen velocityGen;

	@PostConstruct
	public void init() {		
		selectedEntity = new BusVO();
		editSeats = false;
	}	

	public boolean isEditSeats() {
		return editSeats;
	}

	public void setEditSeats(boolean editSeats) {
		this.editSeats = editSeats;
	}

	@Override
	public List<BusVO> getEntityList() {		
		loadEntityList();
		return entityList;
	}

	@Override
	public String createEntity() {
		init();		
		return viewEntity();
	}
	
	@Override
	public String saveEntity() {
		try {
			if (selectedEntity.getOperator() == null) {
				selectedEntity.setOperator(operatorAction.getSelectedEntity());
			}
			serviceLocator.getBusFacade().saveBus(selectedEntity);
			onMessage(SEVERITY_INFO, "Bus [" + selectedEntity.getReg() + "] saved successfully.");
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
		return SUCCESS_PAGE;
	}
	
	@Override
	public String viewEntity() {
		return VIEW_PAGE;
	}
	
	@Override
	public void deleteEntity() {
		try {
			serviceLocator.getBusFacade().deleteBus(selectedEntity.getId());
			onMessage(SEVERITY_INFO, "Bus record deleted successfully");
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	public List<BusVO> getUnscheduledBuses() {
		return serviceLocator.getBusFacade().getUnScheduled(operatorAction.getSelectedEntity());
	}
	
	public String getBusTemplateScript() {
		return velocityGen.busTemplate(selectedEntity.getSeatsAsString(), null);
	}
	
	private void loadEntityList() {
		try {
			entityList = serviceLocator.getBusFacade().obtainBusListByOperatorAndStatus(operatorAction.getSelectedEntity().getId(), Status.ACTIVE);
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private static final String VIEW_PAGE = "/admin/operator/busView?faces-redirect=true&i=2";
	private static final String SUCCESS_PAGE = "/admin/operator/busSuccess?faces-redirect=true";
	//private static final String HIDE_NEW_BUS_PANEL = "PF('var_NewBus').hide()";

	
}

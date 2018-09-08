package com.mweka.natwende.operator.action;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.types.Status;
import com.mweka.natwende.util.ServiceLocator;

@Named("BusAction")
@SessionScoped
public class BusAction extends MessageHelper<BusVO> {

private static final long serialVersionUID = 1L;
	
	//private BusSearchVO searchVO;
	private boolean enableNewBusPanel;

	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	private OperatorAction operatorAction;

	@PostConstruct
	//@Override
	public void init() {		
		setSelectedEntity(new BusVO());
		enableNewBusPanel = false;
	}
	
	public boolean isEnableNewBusPanel() {
		return enableNewBusPanel;
	}
	
	public void setEnableNewBusPanel(boolean enableNewBusPanel) {
		this.enableNewBusPanel = enableNewBusPanel;
	}
	
	@Override
	public List<BusVO> getEntityList() {		
		loadEntityList();
		return entityList;
	}

	@Override
	public String createEntity() {
		init();
		enableNewBusPanel = true;
		return viewEntity();
	}
	
	@Override
	public String saveEntity() {
		try {
			getSelectedEntity().setOperator(operatorAction.getSelectedEntity());
			getSelectedEntity().setImgUrl(operatorAction.getSelectedEntity().getName().getUrl());
			serviceLocator.getBusFacade().saveBus(getSelectedEntity());			
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
		finally {
			enableNewBusPanel = false;
			RequestContext.getCurrentInstance().execute(HIDE_NEW_BUS_PANEL);
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
			serviceLocator.getBusFacade().deleteBus(getSelectedEntity().getId());
			onMessage(SEVERITY_INFO, "Record deleted successfully");
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private void loadEntityList() {
		try {
			entityList = serviceLocator.getBusFacade().obtainBusListByOperatorAndStatus(operatorAction.getSelectedEntity().getId(), Status.ACTIVE);
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private static final String VIEW_PAGE = "/admin/operator/busView?faces-redirect=true";
	private static final String SUCCESS_PAGE = "/admin/operator/busSuccess?faces-redirect=true";
	private static final String HIDE_NEW_BUS_PANEL = "PF('var_NewBus').hide()";

	
}

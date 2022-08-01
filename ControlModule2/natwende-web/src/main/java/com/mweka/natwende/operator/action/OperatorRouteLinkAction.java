package com.mweka.natwende.operator.action;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.operator.vo.OperatorRouteLinkVO;
import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.util.ServiceLocator;

import org.apache.commons.collections.CollectionUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

@Named("OperatorRouteLinkAction")
@SessionScoped
public class OperatorRouteLinkAction extends MessageHelper<OperatorRouteLinkVO> {

	private static final long serialVersionUID = 1L;	
	
	private int currentStep;
	//private OperatorRouteLinkSearchVO searchVO;
	private DualListModel<RouteVO> routePickList;

	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	private OperatorAction operatorAction;

	@PostConstruct
	//@Override
	public void init() {		
		setSelectedEntity(new OperatorRouteLinkVO());
		currentStep = 0;
		routePickList = new DualListModel<>();
	}
	
	public int getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public DualListModel<RouteVO> getRoutePickList() {
		return routePickList;
	}

	public void setRoutePickList(DualListModel<RouteVO> routePickList) {
		this.routePickList = routePickList;
	}

	@Override
	public List<OperatorRouteLinkVO> getEntityList() {		
		loadEntityList();
		return entityList;
	}

	@Override
	public String createEntity() {
		init();
		populateOperatorRouteLinkList();
		return viewEntity();
	}
	
	@Override
	public String saveEntity() {
		try {
			long operatorId = operatorAction.getSelectedEntity().getId();
			for (RouteVO route : routePickList.getTarget()) {
				long routeId = route.getId();
				OperatorRouteLinkVO result = serviceLocator.getOperatorRouteLinkFacade().fetchByOperatorIdAndRouteId(operatorId, routeId);
				if (result == null) { // link does not exist
					result = new OperatorRouteLinkVO(operatorAction.getSelectedEntity(), route);
					serviceLocator.getOperatorRouteLinkDataFacade().update(result);
				}
			}
			PrimeFaces.current().ajax().update(VAR_SUCCESS_DIALOG_BOX);
			PrimeFaces.current().executeScript(SHOW_SUCCESS_DIALOG_BOX);
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
			serviceLocator.getOperatorRouteLinkFacade().deleteOperatorRouteLink(getSelectedEntity().getId());
			onMessage(SEVERITY_INFO, "Record deleted successfully");
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	public void step(ActionEvent event) {
		UICommand comp = (UICommand) event.getComponent();
		final String text = ((String) comp.getValue()).toLowerCase();
		
		switch (text) {
		case "next" : setCurrentStep(currentStep + 1);
		break;
		case "prev" : setCurrentStep(currentStep - 1);
		break;
		case "finish" : onMessage(SEVERITY_INFO, "Steps finished!");
		break;
		default : onMessage(SEVERITY_ERROR, "Unsupported option: [" + text + "]");
		}
	}
	
	public String cancel() {
		init();
		return operatorAction.viewEntity();
	}
	
	public void onTransfer(TransferEvent event) {
		StringBuilder builder = new StringBuilder("*** Picklist targer: ");
		for (Object obj : event.getItems()) {
			builder.append(obj).append(System.lineSeparator());
			RouteVO route = (RouteVO) obj;
			if (route.getMirrorRoute() == null) {
				RouteVO result = serviceLocator.getRouteDataFacade().getByMirrorId(route.getId());
				if (result != null) {
					route.setMirrorRoute(result);
				}
			}
			if (event.isAdd()) {
				routePickList.getSource().remove(route.getMirrorRoute());
				routePickList.getTarget().add(route.getMirrorRoute());
			}
			if (event.isRemove()) {
				routePickList.getSource().add(route.getMirrorRoute());
				routePickList.getTarget().remove(route.getMirrorRoute());
			}
		}
		log.debug(builder);
	}
	
	public String closeSuccessDialogBox() {
		PrimeFaces.current().executeScript(HIDE_SUCCESS_DIALOG_BOX);
		return operatorAction.viewEntity();
	}
	
	public void populateTransitStationsForSelectedEntity() {
		if (selectedEntity.getId() > 0L && CollectionUtils.isEmpty(selectedEntity.getRoute().getRouteStops())) {
			try {
				selectedEntity.getRoute().setRouteStops(serviceLocator.getRouteStopLinkDataFacade().getAllByRoute(selectedEntity.getRoute()));
			}
			catch (Exception ex) {
				onMessage(SEVERITY_ERROR, ex.getMessage());
			}
		}
	}
	
	private void loadEntityList() {
		try {
			entityList = serviceLocator.getOperatorRouteLinkFacade().obtainListByOperatorId(operatorAction.getSelectedEntity().getId());
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private void populateOperatorRouteLinkList() {
		if (selectedEntity.getId() < 0L) {
			routePickList.setSource(serviceLocator.getRouteFacade().fetchRoutesNotYetLinkedToOperator(operatorAction.getSelectedEntity().getId()));
		}
		else {
			routePickList.setTarget(serviceLocator.getRouteFacade().fetchRoutesLinkedToOperator(operatorAction.getSelectedEntity().getId()));
		}
	}
	
	private static final String VIEW_PAGE = "/admin/operator/operatorRouteLinkView?faces-redirect=true";
	private static final String SUCCESS_PAGE = "/admin/operator/operatorRouteLinkSuccess?faces-redirect=true";
	private static transient final String WIDGET_NAME = "widgetName";
	private static transient final String SUCCESS_DIALOG_BOX = "var_OperatorRouteLinkSuccessDlg";
	private static transient final String VAR_SUCCESS_DIALOG_BOX = "@widgetVar(widgetName)".replace(WIDGET_NAME, SUCCESS_DIALOG_BOX);
	private static transient final String SHOW_SUCCESS_DIALOG_BOX = "PF('widgetName').show();".replace(WIDGET_NAME, SUCCESS_DIALOG_BOX);
	private static transient final String HIDE_SUCCESS_DIALOG_BOX = SHOW_SUCCESS_DIALOG_BOX.replace("show", "hide");
	
}

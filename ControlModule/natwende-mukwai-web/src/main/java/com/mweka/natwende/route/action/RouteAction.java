package com.mweka.natwende.route.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.route.vo.StopVO;
import com.mweka.natwende.types.Status;
import com.mweka.natwende.util.ServiceLocator;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

@Named("RouteAction")
@SessionScoped
public class RouteAction extends MessageHelper<RouteVO> {

	private static final long serialVersionUID = 1L;
	
	//private RouteSearchVO searchVO;
	private int currentStep;
	private String stepDirection;
	
	private DualListModel<StopVO> stationPickList;
	private List<StopVO> stationList;
	private List<StopVO> transitStationList;
	private StopVO selectedTransitStation;

	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	private StopAction stopAction;
	
	@Inject
	private StretchAction stretchAction;

	@PostConstruct
	//@Override
	public void init() {		
		setSelectedEntity(new RouteVO());
		currentStep = 0;
		stepDirection = "";
		stationPickList = new DualListModel<>();
		stationList = new ArrayList<>(stopAction.getEntityList());
		transitStationList = new ArrayList<>();
	}
	
	@Override
	public List<RouteVO> getEntityList() {
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
			getSelectedEntity().setName(getSelectedEntity().getStart().getTown().getDisplay() + " - " + getSelectedEntity().getStop().getTown().getDisplay());
			setSelectedEntity(serviceLocator.getRouteFacade().saveRouteAndStationLinks(getSelectedEntity(), stationPickList.getTarget()));
			stretchAction.saveEntityList();
			RequestContext.getCurrentInstance().update(SUCCESS_DIALOG_BOX);
			RequestContext.getCurrentInstance().execute(SHOW_SUCCESS_DIALOG);
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
			serviceLocator.getRouteFacade().deleteRoute(getSelectedEntity().getId());
			onMessage(SEVERITY_INFO, "Record deleted successfully");
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	public String listEntities() {
		return LIST_PAGE;
	}
	
	public List<StopVO> getStationList() {
		return stationList;
	}

	public void step(ActionEvent event) throws Exception {
		try {
			UICommand comp = (UICommand) event.getComponent();
			stepDirection = ((String) comp.getValue()).toLowerCase();
			
			switch (stepDirection) {
				case "next" : setCurrentStep(currentStep + 1);				
				break;
				case "prev" : setCurrentStep(currentStep - 1);			
				break;
				case "finish" : onMessage(SEVERITY_INFO, "Finished.");
				break;
				default : throw new UnsupportedOperationException("Unsupported option: [" + stepDirection + "]");
			}				
		}
		catch (Exception ex) {
			log.debug(ex);
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	public int getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public DualListModel<StopVO> getStationPickList() {
		loadPickList();
		return stationPickList;
	}

	public void setStationPickList(DualListModel<StopVO> stationPickList) {
		this.stationPickList = stationPickList;
	}
	
	public List<StopVO> getTransitStationList() {		
		onReorder();
		return transitStationList;
	}

	public void setTransitStationList(List<StopVO> transitStationList) {
		this.transitStationList = transitStationList;
	}

	public String onDialogClose() {
		RequestContext.getCurrentInstance().execute(HIDE_SUCCESS_DIALOG);
		return LIST_PAGE;
	}
	
	public void onSelect(SelectEvent event) {
		Object obj = event.getObject();
		if (obj instanceof StopVO) {
			selectedTransitStation = (StopVO) event.getObject();
			String logMsg = "Item selected: " + selectedTransitStation + ", item index: " + transitStationList.indexOf(selectedTransitStation);
			log.debug(logMsg);
		}
		else {
			log.error("event.getObject() : expected instance was StopVO. Received [" + obj.getClass().getName() + "]");
		}
	}
	
	public void onReorder() {
		String logMsg = "Item re-ordered: " + selectedTransitStation + ", item index: " + transitStationList.indexOf(selectedTransitStation);
		for (StopVO stop : transitStationList) {
			stop.setIndex(transitStationList.indexOf(stop));
		}
		log.debug(logMsg);
	}
	
	public void onTransfer(TransferEvent event) {
		/*
		for (Object item : event.getItems()) {			
			if (item instanceof StopVO) {
				StopVO station = (StopVO) item;
				if (!transitStationList.contains(station)) {
					transitStationList.add(station);
					station.setIndex(transitStationList.indexOf(station));
				}
			}
			else {
				log.error("event.getObject() : expected instance was StopVO. Received [" + item.getClass().getName() + "]");
			}
		} */
	}	

	private void loadEntityList() {
		entityList = serviceLocator.getRouteDataFacade().getAllByStatus(Status.ACTIVE);
	}
	
	private void loadPickList() {
		stationPickList.getSource().clear();		
		stationPickList.getSource().addAll(getStationList());
		stationPickList.getSource().removeAll(stationPickList.getTarget());
	}
	
	private static final String LIST_PAGE = "/admin/adminPanel?faces-redirect=true&amp;i=2";
	private static final String VIEW_PAGE = "/admin/route/routeView?faces-redirect=true";
	private static final String SUCCESS_PAGE = ""; //admin/route/routeSuccess?faces-redirect=true";
	private static transient final String SHOW_SUCCESS_DIALOG = "PF('var_RouteCreateSuccessDlg').show();";
	private static transient final String HIDE_SUCCESS_DIALOG = SHOW_SUCCESS_DIALOG.replace("show", "hide");
	private static transient final String SUCCESS_DIALOG_BOX = "@widgetVar(var_RouteCreateSuccessDlg)";

}

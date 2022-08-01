package com.mweka.natwende.trip.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

//import com.mweka.natwende.helper.ApplicationBean;
import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.operator.action.OperatorAction;
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.trip.vo.BusTripScheduleLinkVO;
import com.mweka.natwende.trip.vo.TripScheduleVO;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.types.DaysOfWeek;
import com.mweka.natwende.types.OperatorName;
import com.mweka.natwende.types.RoleType;

@Named("TripScheduleAction")
@SessionScoped
public class TripScheduleAction extends MessageHelper<TripScheduleVO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4647493226422924004L;
	
	private int currentStep;
	private List<UserVO> operatorDriverList, selectedDriverList, selectedDriverListMirror;
	private BusVO selectedBus, selectedBusMirror;
	private List<BusVO> selectedBusList, selectedBusListMirror, candidateBusList;
	private TripScheduleVO mirrorSchedule;
	private boolean showMirror;
	private Set<DaysOfWeek> daysOfWeek;
	private DaysOfWeek[] selectedDaysOfWeek;
	private UserVO selectedDriver, selectedDriverMirror;
	private String formattedFrequency;
	private String activeIndex;
	
	@Inject
	private OperatorAction operatorAction;
	
	//@Inject
	//private ApplicationBean appCache;

	@PostConstruct
	protected void init() {
		currentStep = 0;
		activeIndex = "0";
		showMirror = false;
		super.init(TripScheduleVO.class);
		selectedBus = new BusVO();
		selectedBusList = new ArrayList<>();
		selectedBusListMirror = new ArrayList<>();
		selectedDriver = new UserVO();
		selectedDriverList =  new ArrayList<>();
		selectedDriverListMirror =  new ArrayList<>();
		mirrorSchedule = new TripScheduleVO();
		daysOfWeek = EnumSet.copyOf(Arrays.asList(DaysOfWeek.values()));
	}

	public int getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public String getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(String activeIndex) {
		this.activeIndex = activeIndex;
	}

	public boolean isShowMirror() {
		return showMirror;
	}

	public void setShowMirror(boolean showMirror) {
		this.showMirror = showMirror;
	}
	
	public List<UserVO> getOperatorDriverList() {
		//loadOperatorDriverList();
		return operatorDriverList;
	}
	
	public BusVO getSelectedBus() {
		return showMirror ? selectedBusMirror : selectedBus;
	}
	
	public void setSelectedBus(BusVO bus) {
		if (showMirror) {
			this.selectedBusMirror = bus;
			return;
		}
		this.selectedBus = bus;
	}

	public List<BusVO> getSelectedBusList() {
		return showMirror ? selectedBusListMirror : selectedBusList;
	}

	public void setSelectedBusList(List<BusVO> selectedBusList) {
		if (showMirror) {
			this.selectedBusListMirror = selectedBusList;
			return;
		}
		this.selectedBusList = selectedBusList;
	}
	
	public UserVO getSelectedDriver() {
		return showMirror ? selectedDriverMirror : selectedDriver;
	}
	
	public void setSelectedDriver(UserVO selectedDriver) {
		if (showMirror) {
			this.selectedDriverMirror = selectedDriver;
			return;
		}
		this.selectedDriver = selectedDriver;
 	}
	
	public DaysOfWeek[] getSelectedDaysOfWeek() {
		return selectedDaysOfWeek;
	}
	
	public void setSelectedDaysOfWeek(DaysOfWeek[] selectedDaysOfWeek) {
		getTripSchedule().setFrequency(Arrays.asList(selectedDaysOfWeek));
		this.selectedDaysOfWeek = selectedDaysOfWeek;
	}
	
	public Set<DaysOfWeek> getDaysOfWeek() {
		return daysOfWeek;
	}
	
	public void setDaysOfWeek(Set<DaysOfWeek> daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}
	
	public List<UserVO> getSelectedDriverList() {
		return showMirror ? selectedDriverListMirror : selectedDriverList;
	}
	
	public void setSelectedDriverList(List<UserVO> selectedDriverList) {
		if (showMirror) {
			this.selectedDriverListMirror = selectedDriverList;
			return;
		}
		this.selectedDriverList = selectedDriverList;
	}
	
	public List<BusVO> getCandidateBusList() {
		return candidateBusList;
	}
	
	public void setCandidateBusList(List<BusVO> candidateBusList) {
		this.candidateBusList = candidateBusList;
	}
	
	public String getFormattedFrequency() {
		return formattedFrequency;
	}
	
	public void setFormattedFrequency(String formattedFrequency) {
		this.formattedFrequency = formattedFrequency;
	}

	@Override
	public List<TripScheduleVO> getEntityList() {
		populateEntityList();
		return entityList;
	}

	@Override
	public String createEntity() {
		init();
		loadCandidateBusList();
		loadOperatorDriverList();
		return viewEntity();
	}
	
	public String listEntities() {
		return TRIPSCHEDULE_LIST_PAGE;
	}
	
	public String updateEntity() {
		try {
			serviceLocator.getBusTripScheduleLinkFacade().updateBusTripScheduleLinkList(getTripSchedule().getBusTripScheduleLinkList());
			return TRIPSCHEDULE_LIST_PAGE;
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
		return null;
	}

	@Override
	public String saveEntity() {
		try {
			saveTripSchedule(selectedEntity);
			if (mirrorSchedule != null) {				
				saveTripSchedule(mirrorSchedule);
			}
			executeScript("PF('var_TripScheduleSuccessDlg').show();");
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
		return null;
	}
	
	private void saveTripSchedule(TripScheduleVO tripSchedule) throws Exception {
		tripSchedule.setOperator(operatorAction.getSelectedEntity());		
		TripScheduleVO savedTripSchedule = serviceLocator.getTripScheduleFacade().save(tripSchedule);
		savedTripSchedule.setBusTripScheduleLinkList(tripSchedule.getBusTripScheduleLinkList());
		serviceLocator.getBusTripScheduleLinkFacade().createLinks(savedTripSchedule);
	}

	@Override
	public String viewEntity() {
		return TRIPSCHEDULE_VIEW_PAGE;
	}

	@Override
	public void deleteEntity() {
		try {
			long linkId = getTripSchedule().getId();
			serviceLocator.getBusTripScheduleLinkDataFacade().deleteById(linkId);
			serviceLocator.getTripScheduleDataFacade().deleteById(getTripSchedule().getId());
			onMessage(SEVERITY_INFO, "Schedule deleted successfully");
		}
		catch (Exception ex) {
			log.debug(ex);
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
		
	}
	
	public void onRowSelect(SelectEvent<TripScheduleVO> event) {
		selectedEntity = event.getObject();
	}
	
	public void onRowUnSelect(SelectEvent<TripScheduleVO> event) {
		selectedEntity = null;
	}
	
	public String onBackBtn() {
		return listEntities();
	}
	
	public void step(ActionEvent event) {
		UICommand comp = (UICommand) event.getComponent();
		if (comp == null) {
			return;
		}
		if (comp.getValue() == null) {
			return;
		}
		if (!(comp.getValue() instanceof String)) {
			return;
		}
		String text = (String) comp.getValue();
		if (StringUtils.isBlank(text)) {
			return;
		}
		switch (text.toLowerCase()) {
		case "next" : setCurrentStep(currentStep + 1);
		break;
		case "prev" : setCurrentStep(currentStep - 1);
		break;
		case "finish" : onMessage(SEVERITY_INFO, "Finished");
		break;
		default : String msg = "Option not supported [" + text + "]";
		log.debug(msg);
		onMessage(SEVERITY_ERROR, msg);
		}
	}
	
	public void assignedDriversToBuses() {		
		//getTripSchedule().setBusTripScheduleLinkList(selectedBusTripScheduleLinkList);
	}
	
	public void createMirror() {
		// Check for mirror route
		if (selectedEntity.getRoute().getMirrorRoute() == null) {
			RouteVO mirrorRoute = serviceLocator.getRouteDataFacade().getByMirrorRoute(selectedEntity.getRoute());
			if (mirrorRoute == null) {
				onMessage(SEVERITY_INFO, "A mirror route for the shcedule you're creating does not exist.");
				return;
			}
			selectedEntity.getRoute().setMirrorRoute(mirrorRoute);
		}
		mirrorSchedule = new TripScheduleVO();
		mirrorSchedule.setBusTripScheduleLinkList(new ArrayList<BusTripScheduleLinkVO>());
		mirrorSchedule.setEndDate(selectedEntity.getEndDate());
		mirrorSchedule.setStartDate(selectedEntity.getStartDate());
		mirrorSchedule.setFrequency(selectedEntity.getFrequency());
		mirrorSchedule.setScheduledDepartureTime(selectedEntity.getScheduledDepartureTime());
		mirrorSchedule.setRoute(selectedEntity.getRoute().getMirrorRoute());
		selectedBusMirror = new BusVO();
		//selectedBusListMirror = new ArrayList<>();
		selectedDriverMirror = new UserVO();
		selectedDriverListMirror = new ArrayList<>();
	}
	
	public void cancelMirror() {
		mirrorSchedule = null;
		showMirror = false;
	}
	
	public void onSelectDriverStep() {
		/*
		TripScheduleVO tripSchedule = showMirror && selectedEntity.getId() == -1L ? mirrorSchedule : selectedEntity;
		selectedBusTripScheduleLinkList = tripSchedule.getBusTripScheduleLinkList();
		
		if (!selectedBusTripScheduleLinkList.isEmpty()) {
			for (BusTripScheduleLinkVO link : selectedBusTripScheduleLinkList) {
				if (link.getDriver() != null) {
					operatorDriverList.add(link.getDriver());
				}
			}
			selectedBusTripScheduleLinkList.clear();
			for (BusVO bus: selectedBusList) {
				selectedBusTripScheduleLinkList.add(new BusTripScheduleLinkVO(bus, tripSchedule));
			}
		}*/
	}
	
	public void onDriverRemove(BusTripScheduleLinkVO link) {
		operatorDriverList.add(link.getDriver());
		link.setDriver(null);
	}
	
	public void onDriverDrop(DragDropEvent<UserVO> event) {
		UserVO driver = event.getData();
		int busId = Integer.parseInt(event.getDropId().split("_")[1]);
		
		BusTripScheduleLinkVO result = null;
		
		for (BusTripScheduleLinkVO link : getTripSchedule().getBusTripScheduleLinkList()) {
			if (busId == link.getBus().getId()) {
				result = link;
				break;
			}
		}
		if (result != null) {
			if (result.getDriver() != null) {
				onDriverRemove(result);
			}
			result.setDriver(driver);
			operatorDriverList.remove(driver);
		}
	}
	
	public void onTabChange(TabChangeEvent event) {
		activeIndex = ((AccordionPanel) event.getSource()).getActiveIndex();
		if (event.getTab().getTitle().equalsIgnoreCase("Assigned Driver(s)")) {
			createBusScheduleLinksFromSelectedBusList();
		}
		if (event.getTab().getTitle().equalsIgnoreCase("Bus(es)")) {
			//selectedEntity.setBusTripScheduleLinkList(serviceLocator.getBusTripScheduleLinkDataFacade().getListByScheduleId(selectedEntity.getId()));
		}
		loadOperatorDriverList();
	}
	
	public void onBusSelect(SelectEvent<BusVO> event) {
		if (event.getObject() != null) {
			BusVO bus = event.getObject();
			setSelectedBus(bus);
		}
	}
	/*
	public void onEditDialogDone() {
		if (showMirror) {
			for (BusTripScheduleLinkVO link : mirrorSchedule.getBusTripScheduleLinkList()) {
				link.setBus(selectedBusMirror);
			}
		}
	}
	*/
	public void createBusScheduleLinksFromSelectedBusList() {
		getTripSchedule().getBusTripScheduleLinkList().clear();
		for (BusVO bus : getSelectedBusList()) {
			getTripSchedule().getBusTripScheduleLinkList().add(new BusTripScheduleLinkVO(bus, getTripSchedule()));
		}
	}
	
	public TripScheduleVO getTripSchedule() {
		return showMirror ? mirrorSchedule : selectedEntity;
	}
	
	public void setTripSchedule(TripScheduleVO tripSchedule) {
		if (showMirror) {
			mirrorSchedule = tripSchedule;
			return;
		}
		setSelectedEntity(tripSchedule);
	}
	
	public void loadCandidateBusList() {
		try {
			candidateBusList = new ArrayList<>(serviceLocator.getBusFacade().getByOperator(operatorAction.getSelectedEntity()));
		}
		catch(Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	} 
	
	public void loadOperatorDriverList() {
		OperatorName selectedOperatorName = operatorAction.getSelectedEntity().getName();
		operatorDriverList = serviceLocator.getUserFacade().getUserList(RoleType.BUS_DRIVER, selectedOperatorName);
	}
	
	public String closeSuccessDialogBox() {
		cancelMirror();
		executeScript("PF('var_TripScheduleSuccessDlg').hide();");
		return listEntities();
	}
	
	private void populateEntityList() {
		OperatorName selectedOperatorName = operatorAction.getSelectedEntity().getName();
		entityList = serviceLocator.getTripScheduleFacade().getActiveSchedules(selectedOperatorName);		
	}

}

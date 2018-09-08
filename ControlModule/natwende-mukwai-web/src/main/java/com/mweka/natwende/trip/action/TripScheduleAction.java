package com.mweka.natwende.trip.action;

import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.EnumSet;
import java.util.List;
//import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import com.mweka.natwende.helper.ApplicationBean;
import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.operator.action.OperatorAction;
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.trip.vo.BusTripScheduleLinkVO;
import com.mweka.natwende.trip.vo.TripScheduleVO;
import com.mweka.natwende.user.vo.UserVO;
//import com.mweka.natwende.types.DaysOfWeek;
import com.mweka.natwende.types.RoleType;
import com.mweka.natwende.types.Status;

@Named("TripScheduleAction")
@SessionScoped
public class TripScheduleAction extends MessageHelper<TripScheduleVO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4647493226422924004L;
	
	private int currentStep, activeIndex;
	private List<UserVO> operatorDriverList;
	private BusTripScheduleLinkVO selectedBusTripScheduleLink;
	private List<BusTripScheduleLinkVO> selectedBusTripScheduleLinkList, candidateBusTripScheduleLinkList;
	private TripScheduleVO mirrorSchedule;
	private boolean showMirror;
	//private Set<DaysOfWeek> daysOfWeek;
	
	@Inject
	private OperatorAction operatorAction;
	
	@Inject
	private ApplicationBean appCache;

	@PostConstruct
	protected void init() {
		currentStep = 0;
		activeIndex = 0;
		showMirror = false;
		super.init(TripScheduleVO.class);
		selectedBusTripScheduleLink = new BusTripScheduleLinkVO();
		selectedBusTripScheduleLinkList = new ArrayList<>();	
		candidateBusTripScheduleLinkList =  new ArrayList<>();
		mirrorSchedule = new TripScheduleVO();
		//daysOfWeek = EnumSet.copyOf(Arrays.asList(DaysOfWeek.values()));
		try {
			operatorDriverList = serviceLocator.getUserFacade().getUserList(RoleType.BUS_DRIVER, operatorAction.getSelectedEntity().getName());
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

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public boolean isShowMirror() {
		return showMirror;
	}

	public void setShowMirror(boolean showMirror) {
		this.showMirror = showMirror;
	}
	
	public List<UserVO> getOperatorDriverList() {
		return operatorDriverList;
	}
	
	public List<BusTripScheduleLinkVO> getSelectedBusTripScheduleLinkList() {
		return selectedBusTripScheduleLinkList;
	}

	public void setSelectedBusTripScheduleLinkList(List<BusTripScheduleLinkVO> selectedBusTripScheduleLinkList) {
		this.selectedBusTripScheduleLinkList = selectedBusTripScheduleLinkList;
	}

	public List<BusTripScheduleLinkVO> getCandidateBusTripScheduleLinkList() {
		return candidateBusTripScheduleLinkList;
	}

	public void setCandidateBusTripScheduleLinkList(List<BusTripScheduleLinkVO> candidateBusTripScheduleLinkList) {
		this.candidateBusTripScheduleLinkList = candidateBusTripScheduleLinkList;
	}

	public BusTripScheduleLinkVO getSelectedBusTripScheduleLink() {
		return selectedBusTripScheduleLink;
	}

	public void setSelectedBusTripScheduleLink(BusTripScheduleLinkVO selectedBusTripScheduleLink) {
		this.selectedBusTripScheduleLink = selectedBusTripScheduleLink;
	}

	@Override
	public List<TripScheduleVO> getEntityList() {
		populateEntityList();
		return entityList;
	}

	@Override
	public String createEntity() {
		init();
		try {
			List<BusVO> operatorBusList = serviceLocator.getBusFacade().obtainBusListByOperatorAndStatus(operatorAction.getSelectedEntity().getId(), Status.ACTIVE);
			for (BusVO bus : operatorBusList) {
				BusTripScheduleLinkVO result = serviceLocator.getBusTripScheduleLinkFacade().fetchByBusIdAndTripScheduleId(bus.getId(), selectedEntity.getId());
				candidateBusTripScheduleLinkList.add(result == null ? new BusTripScheduleLinkVO(Long.valueOf(candidateBusTripScheduleLinkList.size() + 1), bus, selectedEntity) : result);
				appCache.add("BTSL", candidateBusTripScheduleLinkList);
			}
			return viewEntity();
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
		return null;
	}
	
	public String listEntities() {
		return "";
	}

	@Override
	public String saveEntity() {
		try {
			saveTripSchedule(selectedEntity);
			if (mirrorSchedule != null) {
				saveTripSchedule(mirrorSchedule);
			}
			return listEntities();
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
	protected String viewEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void deleteEntity() {
		// TODO Auto-generated method stub
		
	}
	
	public void onRowSelect(SelectEvent event) {
		selectedEntity = (TripScheduleVO) event.getObject();
	}
	
	public void onRowUnSelect(SelectEvent event) {
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
	
	private void populateEntityList() {
		entityList = serviceLocator.getTripScheduleFacade().getActiveSchedules(operatorAction.getSelectedEntity().getName());
	}

}

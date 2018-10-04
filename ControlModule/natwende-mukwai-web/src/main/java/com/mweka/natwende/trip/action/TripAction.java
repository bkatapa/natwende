package com.mweka.natwende.trip.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.route.vo.RouteStretchLinkVO;
import com.mweka.natwende.route.vo.StopVO;
import com.mweka.natwende.route.vo.StretchVO;
//import com.mweka.natwende.trip.vo.TripScheduleVO;
//import com.mweka.natwende.operator.action.OperatorRouteLinkAction;
import com.mweka.natwende.trip.vo.TripVO;
import com.mweka.natwende.types.Town;

@Named("TripAction")
@SessionScoped
public class TripAction extends MessageHelper<TripVO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3475365497078490512L;
	
	private Date travelDate, returnDate;
	private boolean bookReturn;
	//private List<String> townListAsString;
	
	//@Inject
	//private OperatorRouteLinkAction operatorRouteLinkAction;
	
	@PostConstruct
	public void init() {
		super.init(TripVO.class);
		travelDate = new Date();
		returnDate = new Date();
		bookReturn = false;
		
		//for (Town t : Town.values()) {
			//townListAsString.add(t.getDisplay());
		//}
	}

	@Override
	protected List<TripVO> getEntityList() {
		populateEntityList();
		return entityList;
	}

	public Date getTravelDate() {
		return travelDate;
	}
	
	public String getTravelDateAsString() {
		return formatDate(travelDate, "dd/MM/yyyy");
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}
	
	public String getReturnDateAsString() {
		return formatDate(returnDate, "dd/MM/yyyy");
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public boolean isBookReturn() {
		return bookReturn;
	}

	public void setBookReturn(boolean bookReturn) {
		this.bookReturn = bookReturn;
	}

	@Override
	protected String createEntity() {
		init();
		return viewEntity();
	}

	@Override
	protected String saveEntity() {
		try {
			serviceLocator.getTripFacade().saveTrip(selectedEntity);
			return listEntities();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
		return null;
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
	
	public String listEntities() {
		return "";
	}
	
	public String search() {
		if (selectedEntity.getFrom() == null) {
			onMessage(SEVERITY_ERROR, "Please select departure town");
			return null;
		}
		
		if (selectedEntity.getTo() == null) {
			onMessage(SEVERITY_ERROR, "Please select destination town");
			return null;
		}
		
		log.debug("From: [" + selectedEntity.getFrom() + "]");
		log.debug("To: [" + selectedEntity.getTo() + "]");
		log.debug("Travel date: [" + getTravelDateAsString() + "]");
		
		// Search a route with entered stretch
		try {
			//List<TripVO> tripList = new ArrayList<>();
			
			StretchVO stretch = serviceLocator.getStretchFacade().getStretchByEndpoints(selectedEntity.getFrom(), selectedEntity.getTo());
			
			if (stretch == null) {
				onMessage(SEVERITY_INFO, "Sorry. No results were found for your trip search [" + selectedEntity.getFrom() + " - " + selectedEntity.getTo() + "]");
				return null;
			}
			
			List<RouteStretchLinkVO> rslList = serviceLocator.getRouteStretchLinkFacade().obtainByStretchId(stretch.getId());
			
			if (rslList.isEmpty()) {
				onMessage(SEVERITY_INFO, "Sorry. Currently we have no buses servicing this route stretch. [" + selectedEntity.getFrom() + " - " + selectedEntity.getTo() + "]");
			}
			
			//List<TripScheduleVO> scheduleList = new ArrayList<>();
			
			//for (RouteStretchLinkVO rsl : rslList) {
				// extract schedules, matching on routes
				//TripVO trip = new TripVO();
				
			//}
		}
		catch (Exception e) {
			onMessage(SEVERITY_ERROR, e.getMessage());
		}
		return performSearch();
	}
	
	public void toggleBookReturn() {
		
	}
	
	public List<Town> completeTown(String query) {
		List<Town> townList = new ArrayList<>();
		for (Town t : Town.values()) {
			if (StringUtils.isBlank(query)) {
				continue;
			}
			if (StringUtils.containsIgnoreCase(t.getDisplay(), query)) {
				townList.add(t);
			}
		}
		return townList;
	}
	
	public void onTownFromSelect(SelectEvent event) {
		if (event.getObject() != null && event.getObject() instanceof Town) {
			selectedEntity.setFrom((Town) event.getObject());
		}
	}
	
	public void onTownToSelect(SelectEvent event) {
		if (event.getObject() != null && event.getObject() instanceof Town) {
			selectedEntity.setTo((Town) event.getObject());
		}
	}
	
	private void populateEntityList() {
		try {
			entityList = serviceLocator.getTripFacade().obtainListByRouteName("");
		} catch (Exception e) {
			onMessage(SEVERITY_ERROR, e.getMessage());
			e.printStackTrace();
		}
	}
	
	private String formatDate(Date date, String fmt) {
		return date == null ? StringUtils.EMPTY : new SimpleDateFormat(fmt).format(date);
	}
	
	private String performSearch() {
		if (selectedEntity.getFrom() == null || selectedEntity.getTo() == null) {
			// raise error
		}
		
		try {
			List<StopVO> departureStationList = serviceLocator.getStopFacade().obtainStopByTown(selectedEntity.getFrom());
			List<StopVO> destinationStationList = serviceLocator.getStopFacade().obtainStopByTown(selectedEntity.getTo());
			List<TripVO> resultList = new ArrayList<>();
			
			for (StopVO dep : departureStationList) {
				for (StopVO dest : destinationStationList) {
					resultList.addAll(serviceLocator.getTripFacade().searchActiveByStretchAndTravelDate(new StretchVO(dep, dest), travelDate));
				}
			}
			
			if (resultList.isEmpty()) {
				log.debug("No trips for this search. Inspecting tripSchedule ... if valid create trip and add to resultList");
				StretchVO stretch = serviceLocator.getStretchFacade().getStretchByEndpoints(selectedEntity.getFrom(), selectedEntity.getTo());
				
				if (stretch == null) {
					log.debug("Sorry, currently we don't have bus on this route. [" + stretch + "]");
					return null;
				}
				
				log.debug("Attempting to create some trips from valid schedules if any ...");
				resultList.addAll(serviceLocator.getTripFacade().createNewTripFromSchedule(stretch, travelDate));
			}
			
			entityList.clear();
			entityList.addAll(resultList);
			
			return listEntities();
		}
		catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		
		return null;
	}
}

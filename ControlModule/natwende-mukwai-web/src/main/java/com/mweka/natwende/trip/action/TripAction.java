package com.mweka.natwende.trip.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.helper.VelocityGen;
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.route.vo.RouteStopLinkVO;
import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.route.vo.StopVO;
import com.mweka.natwende.route.vo.StretchVO;
import com.mweka.natwende.trip.search.vo.TripSearchResultVO;
//import com.mweka.natwende.route.vo.StretchVO;
import com.mweka.natwende.trip.search.vo.TripSearchVO;
import com.mweka.natwende.trip.vo.TripVO;
import com.mweka.natwende.types.Town;
import com.mweka.natwende.util.DateUtil;

@Named("TripAction")
@SessionScoped
public class TripAction extends MessageHelper<TripVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3475365497078490512L;
	
	private boolean bookReturn;
	private TripSearchVO searchVO;
	private TripSearchResultVO selectedSearchResult;
	private List<TripSearchResultVO> searchResultList;
	
	@Inject
	private VelocityGen velocityGen;
	
	@PostConstruct
	public void init() {
		super.init(TripVO.class);				
		bookReturn = false;
		searchResultList = new ArrayList<>();
	}

	@Override
	public List<TripVO> getEntityList() {
		//populateEntityList();
		if (entityList == null) {
			entityList = new ArrayList<>();
		}
		return entityList;
	}
	
	public List<TripSearchResultVO> getSearchResults() {
		return searchResultList;
	}
	
	public String getReturnDateAsString() {
		return formatDate(searchVO.getReturnDate(), "dd/MM/yyyy");
	}

	public boolean isBookReturn() {
		return bookReturn;
	}

	public void setBookReturn(boolean bookReturn) {
		this.bookReturn = bookReturn;
	}

	public TripSearchVO getSearchVO() {
		if (searchVO == null) {
			searchVO = new TripSearchVO();
		}
		return searchVO;
	}

	public void setSearchVO(TripSearchVO searchVO) {
		this.searchVO = searchVO;
	}

	public TripSearchResultVO getSelectedSearchResult() {
		if (selectedSearchResult.getRouteStopLinks() == null || selectedSearchResult.getRouteStopLinks().isEmpty()) {
			serviceLocator.getRouteStopLinkDataFacade().getAllByRoute(searchVO.getTrip().getTripSchedule().getRoute());
		}
		return selectedSearchResult;
	}

	public void setSelectedSearchResult(TripSearchResultVO selectedSearchResult) {
		this.selectedSearchResult = selectedSearchResult;
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
	public String viewEntity() {		
		return "";
	}

	@Override
	public void deleteEntity() {
		try {
			serviceLocator.getTripDataFacade().deleteById(selectedEntity.getId());
		} catch (EntityNotFoundException e) {
			onMessage(SEVERITY_ERROR, e.getMessage());
		}	
	}
	
	public String listEntities() {
		return "";
	}
	
	public String search() {
		try {
			entityList = serviceLocator.getTripFacade().scan(searchVO);
		}
		catch (Exception e) {
			onMessage(SEVERITY_ERROR, e.getMessage());			
		}
		return listEntities();
	}
	
	public String scanForTrips() {
		try {
			StretchVO stretch = serviceLocator.getStretchFacade().getStretchByEndpoints(searchVO.getFromTown(), searchVO.getToTown());
			if (stretch != null) {
				searchVO.setStretch(stretch);
				List<RouteVO> routeList = serviceLocator.getRouteDataFacade().getByStretch(stretch);
				entityList.clear();
				for (RouteVO route : routeList) {
					entityList.addAll(serviceLocator.getTripDataFacade().getByRouteAndDepartureDate(route, searchVO.getTravelDate()));
				}
				if (entityList.isEmpty()) {
					// Attempt to create new trips from schedule
					log.debug("No trips for this search. Inspecting tripSchedule ... if valid create trip and add to routeList");				
					for (RouteVO route : routeList) {
						entityList.addAll(serviceLocator.getTripFacade().createTripFromRoute(route, searchVO.getTravelDate()));
					}				
					if (entityList.isEmpty()) {
						throw new Exception("Sorry, currently we dont have a bus on this route [" + searchVO.getStretch() + "]");
					}
				}
				if (!entityList.isEmpty()) {
					prepareSearchResults();
				}
			}
			else {
				throw new Exception("Sorry, currently we dont have a bus from [" 
						+ searchVO.getFromTown().getDisplay() 
						+ "] to [" 
						+ searchVO.getToTown().getDisplay() 
						+ "]");
			}
		}
		catch (Exception ex) {
			log.debug(ex);
			ex.printStackTrace();
			onMessage(SEVERITY_ERROR, ex.getMessage());
			return null;
		}
		return listEntities();
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
	/*
	private void populateEntityList() {
		try {
			entityList = serviceLocator.getTripDataFacade().getActiveTrips();
			for (TripVO trip : entityList) {
				StretchVO stretch = serviceLocator.getStretchFacade().getStretchByEndpoints(trip.getFrom(), trip.getTo());
				if (stretch != null) {
					String amtStr = new DecimalFormat("K#,###.00").format(stretch.getFareAmount());
					trip.setPriceStr(amtStr);
				}
			}
		}
		catch (Exception e) {
			onMessage(SEVERITY_ERROR, e.getMessage());			
		}
	}
	*/
	private String formatDate(Date date, String fmt) {
		return date == null ? StringUtils.EMPTY : new SimpleDateFormat(fmt).format(date);
	}	
	
	public String getBusTemplateScript() {
		BusVO selectedBus = serviceLocator.getBusDataFacade().getByReg(selectedSearchResult.getTrip().getBusReg());
		return velocityGen.busTemplate(selectedBus.getSeatsAsString(), searchVO.getStretch().getFareAmount());
	}
	
	public String getEndpointsAndAnyIntermediateNodesAsJson() {
		try {
			return new ObjectMapper().writeValueAsString(resolveEndpointsAndAnyIntermediateNodes());
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}
		return null;
	}
	
	private void prepareSearchResults() {
		searchResultList = new ArrayList<>();
		TripSearchResultVO result;
		for (TripVO trip : entityList) {
			result = new TripSearchResultVO();
			result.setRouteStopLinks(serviceLocator.getRouteStopLinkDataFacade().getAllByRoute(trip.getTripSchedule().getRoute()));
			result.setStretch(searchVO.getStretch());
			result.setTrip(trip);
			result.setFromTown(searchVO.getStretch().getFrom().getTown());
			result.setToTown(searchVO.getStretch().getTo().getTown());
			result.setId(trip.getId());
			setJourneyTimes(result);
			searchResultList.add(result);
		}
	}
	
	private void setJourneyTimes(TripSearchResultVO searchResult) {
		TripVO trip = searchResult.getTrip();
		if (searchResult.getRouteStopLinks() == null || searchResult.getRouteStopLinks().isEmpty()) { // No transit stations
			searchResult.setEstimatedJourneyStartDate(trip.getScheduledDepartureDate());
		}
		else { // Check if origin town is transit
			boolean isOriginTransit = false;
			StopVO origin = null;
			for (RouteStopLinkVO rsl : searchResult.getRouteStopLinks()) {
				if (searchResult.getStretch().getFrom().equals(rsl.getStop())) {
					origin = rsl.getStop();
					isOriginTransit = true;
				}
			}
			if (isOriginTransit) {
				try {
					Date travelDate = trip.getScheduledDepartureDate();
					StopVO loadingStation = trip.getTripSchedule().getRoute().getStart();
					StretchVO stretchToPickupPoint = serviceLocator.getStretchFacade().getStretchByEndpoints(loadingStation, origin);
					searchResult.setEstimatedJourneyStartDate(DateUtil.addTimeToDate(stretchToPickupPoint.getEstimatedTravelTime(), travelDate));
				}
				catch (Exception ex) {
					onMessage(SEVERITY_ERROR, ex.getMessage());
				}
			}
			else { // Origin is not a transit town
				searchResult.setEstimatedJourneyStartDate(trip.getScheduledDepartureDate());
			}
			searchResult.setEstimatedJouneyEndDate(DateUtil.addTimeToDate(searchVO.getStretch().getEstimatedTravelTime(), searchResult.getEstimatedJourneyStartDate()));
		}
	}
	
	private List<String> resolveEndpointsAndAnyIntermediateNodes() {
		Long routeId = selectedSearchResult.getTrip().getTripSchedule().getRoute().getId();
		RouteStopLinkVO startLink = serviceLocator.getRouteStopLinkDataFacade().getByRouteIdAndTown(routeId, selectedSearchResult.getFromTown());
		RouteStopLinkVO stopLink = serviceLocator.getRouteStopLinkDataFacade().getByRouteIdAndTown(routeId, selectedSearchResult.getToTown());
		List<String> townList = new ArrayList<>();	
		
		if (startLink == null) {
			townList.add(selectedSearchResult.getFromTown().getDisplay());
			startLink = selectedSearchResult.getRouteStopLinks().get(0);
		}
		else {
			townList.add(startLink.getStop().getTown().getDisplay());
		}
		if (stopLink == null) {
			for (int index = 0; index < selectedSearchResult.getRouteStopLinks().size(); index++) {
				if (selectedSearchResult.getRouteStopLinks().get(index).getStationIndex() > startLink.getStationIndex()) {
					townList.add(selectedSearchResult.getRouteStopLinks().get(index).getStop().getTown().getDisplay());
				}
			}
			stopLink = selectedSearchResult.getRouteStopLinks().get(selectedSearchResult.getRouteStopLinks().size() - 1);
			townList.add(selectedSearchResult.getToTown().getDisplay());
			return townList;
		}
		
		List<RouteStopLinkVO> routeStopLinks = serviceLocator.getRouteStopLinkDataFacade().getByRouteIdAndStationIndexRange(routeId, startLink.getStationIndex(), stopLink.getStationIndex());

		for (int index = 0; index < routeStopLinks.size(); index++) {				
			townList.add(routeStopLinks.get(index).getStop().getTown().getDisplay());				
		}
		return townList;
	}	
	
}

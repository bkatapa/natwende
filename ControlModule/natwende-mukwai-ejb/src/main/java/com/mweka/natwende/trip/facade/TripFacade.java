package com.mweka.natwende.trip.facade;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.joda.time.DateTime;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.route.vo.RouteStopLinkVO;
import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.route.vo.StopVO;
import com.mweka.natwende.route.vo.StretchVO;
import com.mweka.natwende.trip.search.vo.TripSearchResultVO;
import com.mweka.natwende.trip.search.vo.TripSearchVO;
import com.mweka.natwende.trip.vo.ElasticTripEvent;
import com.mweka.natwende.trip.vo.ElasticTripVO;
import com.mweka.natwende.trip.vo.TripScheduleVO;
import com.mweka.natwende.trip.vo.TripVO;
import com.mweka.natwende.types.DaysOfWeek;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.DateUtil;

@Stateless
@LocalBean
public class TripFacade extends AbstractFacade<TripVO> {
	
	@Inject
	private Event<ElasticTripEvent> elasticTripEvent;

	public TripFacade() {
		super(TripVO.class);
	}

	public void deleteTrip(Long tripId) throws Exception {
		try {
			serviceLocator.getTripDataFacade().deleteById(tripId);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public List<TripVO> obtainListByRouteName(String routeName) throws Exception {
		try {
			return serviceLocator.getTripDataFacade().getListByRouteName(routeName);
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
	}
	
	public TripVO saveTrip(TripVO trip) throws Exception {
		try {
			return serviceLocator.getTripDataFacade().update(trip);
		}
		catch (Exception e) {
			log.debug(e);
			throw new EJBException(e);
		}
	}
	
	public List<TripVO> searchActiveByStretchAndTravelDate(StretchVO stretch, Date travelDate) {
		return serviceLocator.getTripDataFacade().searchActiveByStretchAndTravelDate(stretch, travelDate);
	}
	
	public List<TripVO> createNewTripFromSchedule(StretchVO stretch, Date travelDate) throws Exception {
		// Check if trip exists
		List<TripVO> tripList = serviceLocator.getTripDataFacade().searchActiveByStretchAndTravelDate(stretch, travelDate);
		
		if (!tripList.isEmpty()) {
			return tripList;
		}
		
		// Create trip if tripSchedule matches
		List<TripScheduleVO> scheduleList = serviceLocator.getTripScheduleDataFacade().getListByRouteStretchAndTravelDate(stretch, travelDate);
		
		if (!scheduleList.isEmpty()) {
			log.debug("Found some valid trip schedules, attempting to create some trips ...");
			String dayOfWeek = new SimpleDateFormat("EEEE").format(travelDate).toUpperCase();
			
			try {
				for (TripScheduleVO schedule : scheduleList) {
					if (schedule.getFrequency().contains(DaysOfWeek.valueOf(dayOfWeek))) {
						tripList.add(serviceLocator.getTripFacade().createTripFromSchedule(schedule, travelDate));
						log.debug("Successfully added trip!");
					}
				}
				
				if (tripList.isEmpty()) {
					throw new Exception("Sorry, we don't have buses travelling to this destination on " + DaysOfWeek.valueOf(dayOfWeek).getDisplay() + "s");
				}
			}
			catch (Exception ex) {
				log.debug(ex.getMessage(), ex);
				throw new EJBException(ex);
			}
		}
		else {
			throw new Exception("Oops! No valid trip schedules were found for " + stretch);
		}
		
		return tripList;
	}
	
	public TripVO createTripFromSchedule(TripScheduleVO schedule, Date travelDate) throws Exception {
		TripVO trip = new TripVO();
		
		if (schedule.getBusTripScheduleLinkList().isEmpty()) {
			schedule.getBusTripScheduleLinkList().addAll(serviceLocator.getBusTripScheduleLinkDataFacade().getListByScheduleId(schedule.getId()));
		}
		
		if (schedule.getBusTripScheduleLinkList().isEmpty()) {
			throw new Exception("No bus and/or driver has been assigned to this trip schedule. [" + schedule + "]");
		}
		
		BusVO bus = schedule.getBusTripScheduleLinkList().get(0).getBus();
		UserVO driver = schedule.getBusTripScheduleLinkList().get(0).getDriver();
		
		trip.setBusReg(bus.getReg());
		trip.setTotalNumOfSeats(bus.getCapacity());
		trip.setDriverName(driver.getName() == null ? "Josiah" : driver.getName());
		trip.setFrom(schedule.getRoute().getStart().getTown());
		trip.setTo(schedule.getRoute().getStop().getTown());
		trip.setTripSchedule(schedule);
		trip.setOperatorName(schedule.getOperator().getName());
		trip.setBusReg(schedule.getBusListAsString());
		trip.setRouteName(schedule.getRoute().getName());
		trip.setScheduledDepartureDate(DateUtil.addTimeToDate(schedule.getScheduledDepartureTime(), travelDate));
		trip.setAvailableNumOfSeats(bus.getCapacity());
		
		try {
			StretchVO stretch = serviceLocator.getStretchFacade().getStretchByEndpoints(trip.getFrom(), trip.getTo());
			
			if (stretch == null) {
				throw new Exception("No valid stretch was found for the trip being created. [" + trip + "]");
			}
			
			trip.setTravelDurationExpected(stretch.getEstimatedTravelTimeAsString());
			
			Calendar c = Calendar.getInstance();
			c.setTime(stretch.getEstimatedTravelTime());
			
			int durHrs = c.get(Calendar.HOUR_OF_DAY);
			int durMin = c.get(Calendar.MINUTE);
			
			c.setTime(schedule.getScheduledDepartureTime());
			
			int startHrs = c.get(Calendar.HOUR_OF_DAY);
			int startMin = c.get(Calendar.MINUTE);
			
			c.setTime(travelDate);
			c.set(Calendar.HOUR_OF_DAY, startHrs);
			c.set(Calendar.MINUTE, startMin);
			
			//trip.setScheduledDepartureDate(DateUtil.mergeDateAndTime(travelDate, schedule.getScheduledDepartureTime()));
			
			DateTime dateTime = new DateTime(trip.getScheduledDepartureDate());
			dateTime = dateTime.plusHours(durHrs).plusMinutes(durMin);
			trip.setScheduledArrivalDate(dateTime.toDate());
			
			trip = serviceLocator.getTripDataFacade().update(trip);
			ElasticTripVO elasticData = TripVO.convertToElasticData(trip, new ElasticTripVO());
			elasticTripEvent.fire(new ElasticTripEvent(elasticData));
			log.debug("Trip created from schedule successfully.");
			return trip;
		}
		catch (Exception ex) {
			log.debug(ex.getMessage(), ex);
			throw new EJBException(ex);
		}		
	}
	
	public TripVO findByBusAndDepartureDateTime(String busReg, Date departureDate) {
		return serviceLocator.getTripDataFacade().getByBusAndDepartureDateTime(busReg, departureDate);
	}
	
	public List<TripVO> scan(TripSearchVO searchVO) throws Exception {
		if (!searchVO.hasFilters()) {
			throw new Exception("No search parameters were set.");
		}
		if (searchVO.getFromTown() == null) {
			throw new Exception("Please specify town travelling from.");
		}
		if (searchVO.getToTown() == null) {
			throw new Exception("Please specify destination town.");
		}
		StretchVO stretch = serviceLocator.getStretchFacade().getStretchByEndpoints(searchVO.getFromTown(), searchVO.getToTown());
		if (stretch == null) {
			throw new Exception("Sorry, currently we dont have a bus on this stretch. [" + stretch + "]");
		}
		searchVO.setStretch(stretch);
		List<RouteVO> routeList = serviceLocator.getRouteDataFacade().getByStretch(stretch);
		List<TripVO> tripList = Collections.emptyList();
		
		for (RouteVO route : routeList) {
			tripList.addAll(serviceLocator.getTripDataFacade().getByRouteAndDepartureDate(route, searchVO.getTravelDate()));
		}
		if (tripList.isEmpty()) {
			// Attempt to create new trips from schedules
			log.debug("No trips for this search were found. Inspecting TripSchedule ... if valid create trip and add to resultList.");
			for (RouteVO route : routeList) {
				tripList.addAll(serviceLocator.getTripFacade().createTripFromRoute(route, searchVO.getTravelDate()));
			}
			if (tripList.isEmpty()) {
				final String msg = "Sorry, currently we don't have a bus on this route [" + stretch + "]";
				log.debug("Sorry, currently we don't have a bus on this route [" + msg + "]");
				throw new Exception(msg);
			}			
		}
		return tripList;
	}
	
	public List<TripVO> createTripFromRoute(RouteVO route, Date travelDate) throws Exception {
		StretchVO stretch = serviceLocator.getStretchFacade().getStretchByEndpoints(route.getStart(), route.getStop());
		List<TripVO> tripList = serviceLocator.getTripFacade().createNewTripFromSchedule(stretch, travelDate);
		return tripList;
	}
	
	public List<TripSearchResultVO> scanFetchTrips(TripSearchVO searchVO, List<TripVO> entityList, List<TripSearchResultVO> searchResultList) throws Exception {
		//StretchSearchVO StretchSearchVO = new StretchSearchVO(); 
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
				try {
					for (RouteVO route : routeList) {
						entityList.addAll(createTripFromRoute(route, searchVO.getTravelDate()));
					}
				}
				catch (Exception ex) {
					log.debug(ex);
					throw ex;
				}
				if (entityList.isEmpty()) {
					throw new Exception("Sorry, currently we don't have a bus on this route. [" + stretch + "]");
				}
			}
			if (!entityList.isEmpty()) {
				prepareSearchResults(stretch, entityList, searchResultList);
			}
		}
		else {
			throw new Exception("Sorry, currently we don't have a bus from "
					+ searchVO.getFromTown().getDisplay()
					+ " to "
					+ searchVO.getToTown().getDisplay());
		}
		return searchResultList;
	}
	
	private void prepareSearchResults(StretchVO stretch, List<TripVO> entityList, List<TripSearchResultVO> searchResultList) throws Exception {
		TripSearchResultVO result;
		for (TripVO trip : entityList) {
			result = new TripSearchResultVO();
			result.setRouteStopLinks(serviceLocator.getRouteStopLinkDataFacade().getAllByRoute(trip.getTripSchedule().getRoute()));
			result.setStretch(stretch);
			result.setTrip(trip);			
			result.setFromTown(stretch.getFrom().getTown());
			result.setToTown(stretch.getTo().getTown());
			result.setOperatorName(trip.getTripSchedule().getOperator().getName());
			setJourneyTimes(stretch, result);
			searchResultList.add(result);
			trip.setOccupiedSeats(new java.util.HashSet<String>(serviceLocator.getBookingDataFacade().getBookedSeatCoordinates(trip)));
		}
	}
	
	private void setJourneyTimes(StretchVO stretch, TripSearchResultVO searchResult) throws Exception {
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
					log.debug(ex);
					throw ex;
				}
			}
			else { // Origin is not a transit town
				searchResult.setEstimatedJourneyStartDate(trip.getScheduledDepartureDate());
			}
			searchResult.setEstimatedJouneyEndDate(DateUtil.addTimeToDate(stretch.getEstimatedTravelTime(), searchResult.getEstimatedJourneyStartDate()));
		}
	}
}

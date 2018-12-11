package com.mweka.natwende.trip.facade;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.joda.time.DateTime;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.route.vo.StretchVO;
import com.mweka.natwende.trip.vo.TripScheduleVO;
import com.mweka.natwende.trip.vo.TripVO;
import com.mweka.natwende.types.DaysOfWeek;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.DateUtil;

@Stateless
@LocalBean
public class TripFacade extends AbstractFacade<TripVO> {

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
		trip.setDriverName(driver.toString());
		trip.setFrom(schedule.getRoute().getStart().getTown());
		trip.setTo(schedule.getRoute().getStop().getTown());
		trip.setTripSchedule(schedule);
		
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
			
			trip.setScheduledDepartureDate(DateUtil.mergeDateAndTime(travelDate, schedule.getScheduledDepartureTime()));
			
			DateTime dateTime = new DateTime(trip.getScheduledDepartureDate());
			dateTime = dateTime.plusHours(durHrs).plusMinutes(durMin);
			trip.setScheduledArrivalDate(dateTime.toDate());
			
			return serviceLocator.getTripDataFacade().update(trip);
		}
		catch (Exception ex) {
			log.debug(ex.getMessage(), ex);
			throw new EJBException(ex);
		}		
	}
	
	public TripVO findByBusAndDepartureDateTime(String busReg, Date departureDate) {
		return serviceLocator.getTripDataFacade().getByBusAndDepartureDateTime(busReg, departureDate);
	}
}

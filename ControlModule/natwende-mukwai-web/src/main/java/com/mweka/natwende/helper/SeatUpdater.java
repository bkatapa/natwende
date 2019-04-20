package com.mweka.natwende.helper;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.operator.vo.SeatVO;
import com.mweka.natwende.trip.vo.TripVO;
import com.mweka.natwende.util.ServiceLocator;

@ServerEndpoint("/seatUpdater")
public class SeatUpdater {

	private static final String CLASS_NAME = SeatUpdater.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	private static final Set<Session> ALL_SESSIONS = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
	private static final Map<String, String> SESSION_TRIP_MAPPING = new ConcurrentHashMap<>();
	
	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	private ApplicationBean appCache;
	
	@OnOpen
	public void open(Session session) {
		final String methodName = "onOpen";
		ALL_SESSIONS.add(session);
		SESSION_TRIP_MAPPING.put(session.getId(), StringUtils.EMPTY);
		LOGGER.logp(Level.CONFIG, CLASS_NAME, methodName, "Opening session_id [{0}]", session.getId());
	}
	
	@OnMessage
	public void message(String msg, Session session) {
		final String methodName = "message";
		LOGGER.logp(Level.CONFIG, CLASS_NAME, methodName, "Session_id [{0}]. Received message [{1}] ", new String[]{session.getId(), msg});
		
		if (StringUtils.isNotBlank(msg) && session.isOpen()) {
			
			if (StringUtils.startsWithIgnoreCase(msg, "trip_id")) {
				SESSION_TRIP_MAPPING.put(session.getId(), msg);
				@SuppressWarnings("unchecked")
				Map<Long, Set<String>> reservedSeatsMap = (Map<Long, Set<String>>) appCache.get("Trip_Id");
				
				if (reservedSeatsMap != null) {
					String tripId = msg.split(":")[1];
					Set<String> reservedSeats = reservedSeatsMap.get(Long.parseLong(tripId));
					
					if (reservedSeats != null) {
						try {
							SeatUpdateVO update = new SeatUpdateVO(reservedSeats, "reserve");
							String jsonMsg = new ObjectMapper().writeValueAsString(update);
							sendMessageToSession(jsonMsg, session);
						}
						catch (JsonProcessingException jex) {
							LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Session_id [{0}]. Exception was thrown", jex);
						}
					}
				}
			}
			else if (StringUtils.startsWithIgnoreCase(msg, "selected_seat")) {
				updateSeatAcrossSessions(msg, session, "reserve");
			}
			else if (StringUtils.startsWithIgnoreCase(msg, "unselected_seat")) {
				updateSeatAcrossSessions(msg, session, "vacate");
			}
		}
	}
	
	@OnClose
	public void close(Session session) {
		final String methodName = "close";
		ALL_SESSIONS.remove(session);
		SESSION_TRIP_MAPPING.remove(session.getId());
		LOGGER.logp(Level.CONFIG, CLASS_NAME, methodName, "Closing session_id [{0}]", session.getId());
	}
	
	@OnError
	public void error(Throwable t) {
		final String methodName = "error";
		LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Session_id [{0}]. Exception was thrown", t);
	}
	
	public void sendMessageToAllSessions(String msg) {
		final String methodName = "sendMessageToAllSessions";
		try {
			for (Session session : ALL_SESSIONS) {
				if (session.isOpen()) {
					session.getBasicRemote().sendText(msg);					
				}
			}
		}
		catch (IOException ex) {
			LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Session_id [{0}]. Exception was thrown", ex);
		}
	}
	
	public void sendMessageToSession(String msg, Session session) {
		final String methodName = "sendMessageToSession";
		try {
			session.getBasicRemote().sendText(msg);
		}
		catch (IOException ex) {
			LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Session_id [{0}]. Exception was thrown", ex);
		}
	}
	
	public static Map<String, String> getSessionTripMapping() {
		return SESSION_TRIP_MAPPING;
	}
	
	private void updateSeatAcrossSessions(String msg, Session session, String action) {
		final String methodName = "updateSeatAcrossSessions";
		
		String publisherTripId = SESSION_TRIP_MAPPING.get(session.getId());
		long tripId = Long.parseLong(publisherTripId.split(":")[1]);
		TripVO trip = serviceLocator.getTripDataFacade().getById(tripId);
		BusVO bus = serviceLocator.getBusDataFacade().getByReg(trip.getBusReg());
		String seatNo = msg.split(":")[1];
		SeatVO seat = serviceLocator.getSeatDataFacade().getBySeatNoAndBusId(seatNo, bus.getId());
		
		for (Session s : ALL_SESSIONS) {
			if (!s.isOpen()) {
				continue;
			}
			String subscriberTripId = SESSION_TRIP_MAPPING.get(s.getId());
			if (!s.equals(session) && publisherTripId.equals(subscriberTripId)) {
				try {
					SeatUpdateVO update = new SeatUpdateVO(Collections.singleton(seat.getCoordinates()), action);
					switch (action) {
					case "reserve" : update.setMessage(new FacesMessage(StringEscapeUtils.escapeHtml(seat.getSeatNo()), StringEscapeUtils.escapeHtml("Seat no." + seat.getSeatNo() + " was reserved.")));
					updateGlobalCache(trip, seat, action);
					break;
					case "vacate" : update.setMessage(new FacesMessage(StringEscapeUtils.escapeHtml(seat.getSeatNo()), StringEscapeUtils.escapeHtml("Seat no." + seat.getSeatNo() + " was vacated.")));
					updateGlobalCache(trip, seat, action);
					break;
					default : LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Case item not yet implemented [{0}].", action);
					}
					String jsonMsg = new ObjectMapper().writeValueAsString(update);
					sendMessageToSession(jsonMsg, s);
				}
				catch (JsonProcessingException jex) {
					LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Exception occurred: ", jex);
				}
			}
		}
	}
	
	private void updateGlobalCache(TripVO trip, SeatVO seat, String action) {
		final String methodName = "updateGlobalCache";
		
		@SuppressWarnings("unchecked")
		Map<Long, Set<String>> reservedSeatsMap = (Map<Long, Set<String>>) appCache.get("Trip_Id");
		if (reservedSeatsMap == null) {
			reservedSeatsMap = new ConcurrentHashMap<>();
			appCache.add("Trip_Id", reservedSeatsMap);			
		}
		Set<String> reservedSeats = reservedSeatsMap.get(trip.getId());
		if (reservedSeats == null) {
			reservedSeats = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
			reservedSeatsMap.put(trip.getId(), reservedSeats);
		}
		if ("reserve".equals(action)) {
			reservedSeats.add(seat.getCoordinates());
		}
		if ("vacate".equals(action)) {
			reservedSeats.remove(seat.getCoordinates());
		}
		LOGGER.logp(Level.CONFIG, CLASS_NAME, methodName, "Reserved seats: {0}", reservedSeats);
	}
}

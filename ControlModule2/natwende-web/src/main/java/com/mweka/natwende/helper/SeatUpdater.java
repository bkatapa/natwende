package com.mweka.natwende.helper;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Singleton;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mweka.natwende.message.ReservationMessage;
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.operator.vo.SeatVO;
import com.mweka.natwende.resource.elasticdb.ElasticDB;
import com.mweka.natwende.trip.vo.ElasticTripVO;
import com.mweka.natwende.trip.vo.ReservationVO;
import com.mweka.natwende.util.ServiceLocator;
import com.rits.cloning.Cloner;

@Singleton
@ServerEndpoint("/seatUpdater")
public class SeatUpdater {

	private static final String CLASS_NAME = SeatUpdater.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	private static final Set<Session> ALL_SESSIONS = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
	private static final Map<String, ReservationMessage> SESSION_TRIP_MAPPING = new ConcurrentHashMap<>();
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	private ElasticDB elasticDB;
	
	@OnOpen
	public void open(Session session) {
		final String methodName = "onOpen";
		ALL_SESSIONS.add(session);
		//SESSION_TRIP_MAPPING.put(session.getId(), null);
		LOGGER.logp(Level.CONFIG, CLASS_NAME, methodName, "Opening session_id [{0}]", session.getId());
	}
	
	@OnMessage
	public void message(String msg, Session session) {
		final String methodName = "message";
		LOGGER.logp(Level.CONFIG, CLASS_NAME, methodName, "Session_id [{0}]. Received message [{1}] ", new String[]{session.getId(), msg});
		
		if (StringUtils.isNotBlank(msg) && session.isOpen()) {
			try {
				ReservationMessage reservationMsg = OBJECT_MAPPER.readValue(msg, ReservationMessage.class);
				
				if (StringUtils.isNotBlank(reservationMsg.getTripSerialNo())) {
				
					reservationMsg.setUserId(session.getUserPrincipal().getName());
					SESSION_TRIP_MAPPING.put(session.getId(), reservationMsg);					
					ElasticTripVO elasticTrip = elasticDB.getEntity(reservationMsg.getTripSerialNo());
					
					if (elasticTrip != null) {
						Set<String> userBookings = elasticTrip.getUserBookings().get(session.getUserPrincipal().getName());
						if (userBookings != null && !userBookings.isEmpty()) {
							Set<String> selectableCoordinates = ReservationVO.getReservedCoordinates(userBookings);
							SeatUpdateVO update = new SeatUpdateVO(selectableCoordinates, "select", ReservationVO.getEndpoints(userBookings));
							elasticTrip.getOccupiedSeats().removeAll(selectableCoordinates);						
							String jsonMsg = OBJECT_MAPPER.writeValueAsString(update);
							LOGGER.logp(Level.CONFIG, CLASS_NAME, methodName, "Session_id [" + session + "]. Json for select update: ", jsonMsg);
							session.getAsyncRemote().sendText(jsonMsg);
							
							populateUpdateObj(update, selectableCoordinates, userBookings, elasticTrip);
									//getNonSelectableCoordinatesForClient(update.getEndpoints(), selectableCoordinates, userBookings, elasticTrip);
							//if (!reservedSeats.isEmpty()) {
							//	update = new SeatUpdateVO(reservedSeats, "reserve", update.getEndpoints());
							//	update.setAvailSeats(elasticTrip.getTotalNumOfSeats() - reservedSeats.size());
							update.setAction("reserve");
								session.getAsyncRemote().sendText(OBJECT_MAPPER.writeValueAsString(update));
							//}
							return;
						}						
						removeNullsAndEmptiesFromList(elasticTrip.getOccupiedSeats());
						if (!elasticTrip.getOccupiedSeats().isEmpty()) {
							String[] travellerEnpoints = ReservationVO.getEndpoints(userBookings);
							//Set<String> reservedSeats = getNonSelectableCoordinatesForClient(travellerEnpoints, Collections.singleton(""), Collections.singleton(""), elasticTrip);
									//new java.util.HashSet<>(elasticTrip.getOccupiedSeats());						
							SeatUpdateVO update = new SeatUpdateVO(Collections.singleton(""), "reserve", travellerEnpoints);
							populateUpdateObj(update, Collections.singleton(""), userBookings, elasticTrip);
							update.setAvailSeats(elasticTrip.getAvailNumOfSeats());
							String jsonMsg = OBJECT_MAPPER.writeValueAsString(update);
							LOGGER.logp(Level.CONFIG, CLASS_NAME, methodName, "Session_id [" + session + "]. Json for reserve update: ", jsonMsg);
							sendMessageToSession(jsonMsg, session);
						}
					}				
				}			
				else if (reservationMsg.isSelected() != null) {				
					updateSeatAcrossSessions(reservationMsg.getSeatNo(), session, reservationMsg.isSelected() ? "reserve" : "vacate");				
				}
			}					
			catch (Exception jex) {
				LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Session_id [" + session + "]. Exception was thrown", jex);
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
	public void error(Throwable t, Session session) {
		final String methodName = "error";
		// update elastic search
		LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Session_id [" + session + "]. Exception was thrown", t);
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
			// update elastic search
			LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Session_id [" + session + "]. Exception was thrown", ex);
		}
	}
	
	public static Map<String, ReservationMessage> getSessionTripMapping() {
		return SESSION_TRIP_MAPPING;
	}
	
	private void updateSeatAcrossSessions(String seatNo, Session session, String action) {
		final String methodName = "updateSeatAcrossSessions";
		try {
			ReservationMessage publisherReservationMsg = SESSION_TRIP_MAPPING.get(session.getId());						
			ElasticTripVO elasticTrip = elasticDB.getEntity(publisherReservationMsg.getTripSerialNo());				
			BusVO bus = serviceLocator.getBusDataFacade().getByReg(elasticTrip.getBusReg());
			
			publisherReservationMsg.setSeatNo(seatNo);
			SeatVO seat = serviceLocator.getSeatDataFacade().getBySeatNoAndBusId(seatNo, bus.getId());
			String townFrom = publisherReservationMsg.getFromTown(), townTo = publisherReservationMsg.getToTown();
			String bookingToken = StringUtils.join(new Object[]{seat.getSeatNo(), seat.getCoordinates(), townFrom, townTo}, "|");					 
			SeatUpdateVO update = new SeatUpdateVO(Collections.singleton(seat.getCoordinates()), action, new String[]{townFrom, townTo});
			
			switch (action) {
			case "reserve" :
				if (elasticTrip.getUserBookings().get(session.getUserPrincipal().getName()) == null) {
					elasticTrip.getUserBookings().put(session.getUserPrincipal().getName(), new java.util.HashSet<String>());
				}				
				elasticTrip.getUserBookings().get(session.getUserPrincipal().getName()).add(bookingToken);					
				elasticTrip.getOccupiedSeats().add(seat.getCoordinates());							
				update.setMessage(new FacesMessage(StringEscapeUtils.escapeHtml(seat.getSeatNo()), StringEscapeUtils.escapeXml("Seat no." + seat.getSeatNo() + " was reserved.")));
				break;
			case "vacate" :
				elasticTrip.getUserBookings().get(session.getUserPrincipal().getName()).remove(bookingToken);				
				elasticTrip.getOccupiedSeats().remove(seat.getCoordinates());
				update.setMessage(new FacesMessage(StringEscapeUtils.escapeHtml(seat.getSeatNo()), StringEscapeUtils.escapeXml("Seat no." + seat.getSeatNo() + " was vacated.")));
				break;
			default : 
				LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Case item not yet implemented [{0}].", action);
				return;
			}
			removeNullsAndEmptiesFromList(elasticTrip.getOccupiedSeats());
			elasticTrip.setBookedNumOfSeats(elasticTrip.getOccupiedSeats().size());
			elasticTrip.setAvailNumOfSeats(elasticTrip.getTotalNumOfSeats() - elasticTrip.getBookedNumOfSeats());					
			ElasticTripVO result = elasticDB.updateEntity(elasticTrip);
			LOGGER.logp(Level.INFO, CLASS_NAME, methodName, "A seat reservation was updated: [{0}, {1}].", new Object[] {action, result});
			update.setAvailSeats(elasticTrip.getAvailNumOfSeats());	
			
			for (Session s : ALL_SESSIONS) {
				populateUpdateObj(update, 
						ReservationVO.getReservedCoordinates(elasticTrip.getUserBookings().get(s.getUserPrincipal().getName())), 
						elasticTrip.getUserBookings().get(s.getUserPrincipal().getName()), elasticTrip);
				ReservationMessage subscriberReservationMsg = SESSION_TRIP_MAPPING.get(s.getId());
				if (!s.getId().equals(session.getId()) && subscriberReservationMsg.getTripSerialNo().equals(publisherReservationMsg.getTripSerialNo())) {
					//sessions.add(s);
					s.getAsyncRemote().sendText(OBJECT_MAPPER.writeValueAsString(update));
				}
				else {
					SeatUpdateVO newUpdate = new Cloner().deepClone(update);
					newUpdate.setMessage(null);
					newUpdate.setAction("none");
					s.getAsyncRemote().sendText(OBJECT_MAPPER.writeValueAsString(newUpdate));
				}
			}
		}
		catch (Exception jex) {
			LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Exception occurred: ", jex);
		}
	}
	
	private void removeNullsAndEmptiesFromList(java.util.List<String> list) {
		//list.removeIf(StringUtils::isEmpty);
		list.removeAll(Collections.singleton(null));
		list.removeAll(Collections.singleton(StringUtils.EMPTY));
	}
	/*
	private void sendMessageToSessions(String msg, List<Session> sessions) {
		final String methodName = "sendMessageToSessions";
		try {
			for (Session session : sessions) {
				if (session.isOpen()) {
					session.getBasicRemote().sendText(msg);					
				}
			}
		}
		catch (IOException ex) {
			LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Session_id [{0}]. Exception was thrown", ex);
		}
	} */
	
	public void populateUpdateObj(SeatUpdateVO update, Set<String> selectableCoordinates, Set<String> userBookingTokens, ElasticTripVO elasticTrip) {		
		String[] endpoints = update.getEndpoints();
		endpoints[0] = elasticTrip.marryStationToIndex(endpoints[0]);
		endpoints[1] = elasticTrip.marryStationToIndex(endpoints[1]);
		
		for (String userBookingToken : userBookingTokens) {
			String[] tokens = userBookingToken.split("\\|");
			tokens[2] = elasticTrip.marryStationToIndex(tokens[2]);
			tokens[3] = elasticTrip.marryStationToIndex(tokens[3]);
			userBookingToken = StringUtils.join(tokens, "|");
		}
		
		Set<String> allBookingTokens = new HashSet<>(elasticTrip.getAllReservations());
		for (String allBookingToken : allBookingTokens) {
			String[] tokens = allBookingToken.split("\\|");
			tokens[2] = elasticTrip.marryStationToIndex(tokens[2]);
			tokens[3] = elasticTrip.marryStationToIndex(tokens[3]);
			allBookingToken = StringUtils.join(tokens, "|");
		}
		
		update.setEndpoints(endpoints);
		update.setSelectableCoordinates(selectableCoordinates);
		update.setUserBookingTokens(userBookingTokens);
		update.setReservedCoordinates(new HashSet<>(elasticTrip.getOccupiedSeats()));
		update.setAllBookingTokens(allBookingTokens);
	}
	
	public static Set<String> getNonSelectableCoordinatesForClient(String[] endpoints, Set<String> selectableCoordinates, Set<String> userBookingTokens, ElasticTripVO elasticTrip) {		
		Set<String> allReservedCoordinatesCopy = new HashSet<>(elasticTrip.getOccupiedSeats());
		allReservedCoordinatesCopy.removeAll(selectableCoordinates);
		
		Set<String> allBookingTokensCopy = new HashSet<>(elasticTrip.getAllReservations());
		allBookingTokensCopy.removeAll(userBookingTokens);
		
		Map<Integer, String> indexedTownMap = new java.util.LinkedHashMap<>();
		for (String indexedTown : elasticTrip.getStationList()) {
			String[] tokens = indexedTown.split("_");			
			indexedTownMap.put(Integer.valueOf(tokens[0]), tokens[1]);
		}
		
		for (Map.Entry<Integer, String> entry : indexedTownMap.entrySet()) {
			if (entry.getValue().equals(endpoints[0])) {
				endpoints[0] = entry.getKey() + "," + entry.getValue();
			}
			if (entry.getValue().equals(endpoints[1])) {
				endpoints[1] = entry.getKey() + "," + entry.getValue();
			}
		}
		
		for (String token : allBookingTokensCopy) {
			
			String[] tokenEndpoints = token.split("\\|");
			
			for (Map.Entry<Integer, String> entry : indexedTownMap.entrySet()) {
				if (entry.getValue().equals(tokenEndpoints[2])) {
					tokenEndpoints[2] = entry.getKey() + "," + entry.getValue();
				}
				if (entry.getValue().equals(tokenEndpoints[3])) {
					tokenEndpoints[3] = entry.getKey() + "," + entry.getValue();
				}								
			}
			if (endpoints[0].charAt(0) >= tokenEndpoints[3].charAt(0) || endpoints[1].charAt(0) <= tokenEndpoints[2].charAt(0)) {
				allReservedCoordinatesCopy.remove(tokenEndpoints[1]);				
			}
		}
		
		return allReservedCoordinatesCopy;
	}
	
}
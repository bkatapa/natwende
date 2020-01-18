package com.mweka.natwende.helper;

import java.io.IOException;
import java.util.Collections;
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
import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.operator.vo.SeatVO;
import com.mweka.natwende.resource.elasticdb.ElasticDB;
import com.mweka.natwende.trip.vo.ElasticTripVO;
import com.mweka.natwende.util.ServiceLocator;

@Singleton
@ServerEndpoint("/seatUpdater")
public class SeatUpdater {

	private static final String CLASS_NAME = SeatUpdater.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	private static final Set<Session> ALL_SESSIONS = Collections.newSetFromMap(new ConcurrentHashMap<Session, Boolean>());
	private static final Map<String, String> SESSION_TRIP_MAPPING = new ConcurrentHashMap<>();
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	private ElasticDB elasticDB;
	
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
			
			if (StringUtils.startsWithIgnoreCase(msg, "trip_unique_id")) {
				try {
					SESSION_TRIP_MAPPING.put(session.getId(), msg);
					String tripUniqueId = msg.split(":")[1];
					ElasticTripVO elasticTrip = elasticDB.getEntity(tripUniqueId);
					
					if (elasticTrip != null) {
						//elasticTrip.getOccupiedSeats().removeIf(StringUtils::isEmpty);
						elasticTrip.getOccupiedSeats().removeAll(Collections.singleton(null));
						elasticTrip.getOccupiedSeats().removeAll(Collections.singleton(StringUtils.EMPTY));
						if (!elasticTrip.getOccupiedSeats().isEmpty()) {
							Set<String> reservedSeats = new java.util.HashSet<>(elasticTrip.getOccupiedSeats());
							SeatUpdateVO update = new SeatUpdateVO(reservedSeats, "reserve");
							String jsonMsg = OBJECT_MAPPER.writeValueAsString(update);
							sendMessageToSession(jsonMsg, session);
						}
					}
				}			
				catch (Exception jex) {
					LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Session_id [" + session + "]. Exception was thrown", jex);
				}
			}
			else if (StringUtils.startsWithIgnoreCase(msg, "selected_seat")) {				
				updateSeatAcrossSessions(msg, session, "reserve");				
			}
			else if (StringUtils.startsWithIgnoreCase(msg, "unselected_seat")) {
				updateSeatAcrossSessions(msg, session, "vacate");
				// update elastic search
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
	
	public static Map<String, String> getSessionTripMapping() {
		return SESSION_TRIP_MAPPING;
	}
	
	private void updateSeatAcrossSessions(String msg, Session session, String action) {
		final String methodName = "updateSeatAcrossSessions";
		try {
			String publisherTripUniqueId = SESSION_TRIP_MAPPING.get(session.getId());
			String tripUniqueId = publisherTripUniqueId.split(":")[1];			
			ElasticTripVO elasticTrip = elasticDB.getEntity(tripUniqueId);				
			BusVO bus = serviceLocator.getBusDataFacade().getByReg(elasticTrip.getBusReg());
			String seatNo = msg.split(":")[1];
			SeatVO seat = serviceLocator.getSeatDataFacade().getBySeatNoAndBusId(seatNo, bus.getId());
			
			for (Session s : ALL_SESSIONS) {
				if (!s.isOpen()) {
					continue;
				}
				String subscriberTripUniqueId = SESSION_TRIP_MAPPING.get(s.getId());
				if (!s.equals(session) && publisherTripUniqueId.equals(subscriberTripUniqueId)) {					
					SeatUpdateVO update = new SeatUpdateVO(Collections.singleton(seat.getCoordinates()), action);
					switch (action) {
					case "reserve" :
							elasticTrip.getTripSessions().put(session.getId(), seat);
							elasticTrip.getOccupiedSeats().add(seat.getCoordinates());							
							update.setMessage(new FacesMessage(StringEscapeUtils.escapeHtml(seat.getSeatNo()), StringEscapeUtils.escapeXml("Seat no." + seat.getSeatNo() + " was reserved.")));
						break;
						case "vacate" :
							elasticTrip.getTripSessions().remove(session.getId());
							elasticTrip.getOccupiedSeats().remove(seat.getCoordinates());
							update.setMessage(new FacesMessage(StringEscapeUtils.escapeHtml(seat.getSeatNo()), StringEscapeUtils.escapeXml("Seat no." + seat.getSeatNo() + " was vacated.")));
						break;
						default : 
							LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Case item not yet implemented [{0}].", action);
							return;
					}
					elasticTrip.setBookedNumOfSeats(elasticTrip.getOccupiedSeats().size());
					elasticTrip.setAvailNumOfSeats(elasticTrip.getTotalNumOfSeats() - elasticTrip.getBookedNumOfSeats());
					ElasticTripVO result = elasticDB.updateEntity(elasticTrip);
					LOGGER.logp(Level.INFO, CLASS_NAME, methodName, "A seat reservation was updated: [{0}, {1}].", new Object[] {action, result});
					String jsonMsg = OBJECT_MAPPER.writeValueAsString(update);
					sendMessageToSession(jsonMsg, s);
				}
			}
		}
		catch (Exception jex) {
			LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Exception occurred: ", jex);
		}
	}
	
}
package com.mweka.natwende.payment.action;

//import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mweka.natwende.cdi.LoggedInUser;
import com.mweka.natwende.message.MessageVOBuilder;
import com.mweka.natwende.notification.vo.NotificationVO;
import com.mweka.natwende.payment.vo.PaymentEvent;
import com.mweka.natwende.payment.vo.PaymentVO;
import com.mweka.natwende.types.MessageSeverity;
import com.mweka.natwende.types.NotificationType;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.ServiceLocator;

@ServerEndpoint("/paymentUpdater")
public class PaymentUpdater {

	private static final String CLASS_NAME = PaymentUpdater.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	private static final Map<Session, UserVO> SESSION_USER_MAPPING = new ConcurrentHashMap<>();
	
	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	@LoggedInUser
	private UserVO loggedInUser;
	
	public void alertUser(@Observes PaymentEvent event) {
		final String methodName = "alertUser";
		PaymentVO payment = event.getPayment();
		LOGGER.logp(Level.CONFIG, CLASS_NAME, methodName, "Payment event received: {0}", payment);
		
		try {
			NotificationVO notification = new NotificationVO(NotificationType.PAYMENT);
			final String summary = NotificationType.PAYMENT.getDisplay();
			switch (payment.getPaymentStatus()) {
				case AUTHORIZED : notification.setMessage(new MessageVOBuilder(MessageSeverity.INFO)
						.summary(summary)
						.detail("Payment with ref = [" + payment.getRef() + "] was authorized.")
						.build());
				break;
				case FAILED : notification.setMessage(new MessageVOBuilder(MessageSeverity.ERROR)
						.summary(summary)
						.detail("Payment with ref = [" + payment.getRef() + "] was declined.")
						.build());
				break;
				default : LOGGER.logp(Level.WARNING, CLASS_NAME, methodName, "Case option not yet implemented: [{0}]", payment.getPaymentStatus());
			}
			
			final String jsonMsg = new ObjectMapper().writeValueAsString(notification);
			UserVO customer = serviceLocator.getUserDataFacade().getByPaymentRef(payment.getRef());
			
			for (Map.Entry<Session, UserVO> entry : SESSION_USER_MAPPING.entrySet()) {
				final Session session = entry.getKey();
				final UserVO user = entry.getValue();
				
				if (session.isOpen()) {
					if (customer.equals(user)) {
						sendMessageToSession(jsonMsg, session);
					}
				}
			}
		}
		catch (JsonProcessingException jex) {
			LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, jex.getMessage(), jex);
		}
	}
	
	@OnOpen
	public void open(Session session) {
		SESSION_USER_MAPPING.put(session, loggedInUser);
	}
	
	@OnMessage
	private void message(String msg, Session session) {		
	}
	
	@OnClose
	public void close(Session session) {
		SESSION_USER_MAPPING.remove(session);
	}
	
	@OnError
	public void error(Throwable t) {
		final String methodName = "error";
		LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, t.getMessage(), t);
	}
	
	private void sendMessageToSession(String msg, Session session) {
		//final String sourceMethod = "sendMessageToSession";
		//try {
			session.getAsyncRemote().sendText(msg);
		//}
		//catch (IOException e) {
			//LOGGER.logp(Level.SEVERE, CLASS_NAME, sourceMethod, e.getMessage(), e);
		//}
	}
}

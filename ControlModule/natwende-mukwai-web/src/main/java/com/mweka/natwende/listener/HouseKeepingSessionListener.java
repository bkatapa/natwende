package com.mweka.natwende.listener;

import javax.inject.Inject;
//import javax.servlet.annotation.WebListener;
//import javax.ejb.EJB;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;

//import com.mweka.natwende.util.ServiceLocator;

//@WebListener
public class HouseKeepingSessionListener implements HttpSessionListener {
//	@EJB
//	private ServiceLocator serviceLocator;
	
	@Inject
	private Log log;
	
	public void sessionCreated(HttpSessionEvent event) {		
		log.debug("A new session with tracking id ["+ event.getSession().getId() +"] created");
	}
 
	public void sessionDestroyed(HttpSessionEvent event) {
		event.getSession().removeAttribute("FilterRan");
		event.getSession().removeAttribute("userPermissionSet");
		event.getSession().removeAttribute("browseHistory");
		log.debug("Session with tracking id ["+ event.getSession().getId() +"] destroyed");
	}
}
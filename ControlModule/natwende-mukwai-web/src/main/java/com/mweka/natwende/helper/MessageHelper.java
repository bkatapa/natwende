/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.util.ServiceLocator;

/**
 *
 * @author Bell
 */
public abstract class MessageHelper<T extends BaseVO> implements Serializable {    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8712975582038426687L;
	
	private Class<T> clazz;
	
	protected Log log = LogFactory.getLog(this.getClass());	
	protected T selectedEntity;
	
	private void init() {
		try {
			selectedEntity = clazz.newInstance();
			entityList = new ArrayList<>();
		}
		catch (Exception ex) {
			log.debug("Instantiation failed for class [" + clazz);
		}
	}
	
	protected void init(Class<T> clazz) {
		this.clazz = clazz;
		init();
	}
	
	protected List<T> entityList;
	
	@EJB
	protected ServiceLocator serviceLocator;	
	
	public void setSelectedEntity(T selectedEntity) {
		this.selectedEntity = selectedEntity;
	}
	
	protected abstract List<T> getEntityList();
	
	public T getSelectedEntity() {
		return selectedEntity;
	}

	protected void onMessage(String msgType, String msg) {
        FacesMessage.Severity severity = msgType.equals(SEVERITY_INFO) ? FacesMessage.SEVERITY_INFO
                : msgType.equals(SEVERITY_WARN) ? FacesMessage.SEVERITY_WARN
                : msgType.equals(SEVERITY_ERROR) ? FacesMessage.SEVERITY_ERROR
                : FacesMessage.SEVERITY_FATAL;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(severity, msg, severity == FacesMessage.SEVERITY_INFO ? "Success"
                        : severity == FacesMessage.SEVERITY_WARN ? "Warn"
                        : severity == FacesMessage.SEVERITY_ERROR ? "Error"
                        : "Fatal"));
    }	
    
	protected abstract String createEntity();	
	protected abstract String saveEntity();	
	protected abstract String viewEntity();	
	protected abstract void deleteEntity();	
	
	public static transient final String SEVERITY_INFO = "info";	
	public static transient final String SEVERITY_WARN = "warn";	
	public static transient final String SEVERITY_ERROR = "error";	
	public static transient final String SEVERITY_FATAL = "fatal";
	
	public static transient final String FACES_REDIRECT = "?faces-redirect=true";
	
	/** Constants for RouteAction */
	public static transient final String ROUTE_SUCCESS_PAGE = "/route/routeCreateSuccess" + FACES_REDIRECT;
	
	/** Constants for OperatorAction */
	public static transient final String OPERATOR_VIEW_PAGE = "/admin/operator/operatorView" + FACES_REDIRECT + "&i=2";
	
	/** Constants for TripScheduleAction */
	public static transient final String TRIPSCHEDULE_LIST_PAGE = OPERATOR_VIEW_PAGE;
	public static transient final String TRIPSCHEDULE_VIEW_PAGE = "/admin/trip/tripScheduleView?faces-redirect=true&i=2";
}

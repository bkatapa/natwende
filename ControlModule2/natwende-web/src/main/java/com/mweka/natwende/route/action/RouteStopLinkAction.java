package com.mweka.natwende.route.action;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.route.vo.RouteStopLinkVO;
import com.mweka.natwende.types.Status;
import com.mweka.natwende.util.ServiceLocator;

@Named("RouteStopLinkAction")
@SessionScoped
public class RouteStopLinkAction extends MessageHelper<RouteStopLinkVO> {

private static final long serialVersionUID = 1L;
	
	//private RouteStopLinkSearchVO searchVO;

	@EJB
	private ServiceLocator serviceLocator;	

	@PostConstruct
	//@Override
	public void init() {		
		setSelectedEntity(new RouteStopLinkVO());
	}
	
	@Override
	public List<RouteStopLinkVO> getEntityList() {
		loadEntityList();
		return entityList;
	}

	@Override
	public String createEntity() {
		init();
		return viewEntity();
	}
	
	@Override
	public String saveEntity() {
		return SUCCESS_PAGE;
	}
	
	@Override
	public String viewEntity() {
		return VIEW_PAGE;
	}
	
	@Override
	public void deleteEntity() {
		try {
			serviceLocator.getRouteStopLinkFacade().deleteRouteStopLink(getSelectedEntity().getId());
			onMessage(SEVERITY_INFO, "Record deleted successfully");
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private void loadEntityList() {
		try {
			entityList = serviceLocator.getRouteStopLinkFacade().obtainAllByStatus(Status.ACTIVE);
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private static final String VIEW_PAGE = "/admin/route/routeStopLinkView?faces-redirect=true";
	private static final String SUCCESS_PAGE = "/admin/route/routeStopLinkSuccess?faces-redirect=true";
	
}

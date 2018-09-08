package com.mweka.natwende.route.action;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.route.vo.FareRouteLinkVO;
import com.mweka.natwende.util.ServiceLocator;

@Named("FareRouteLinkAction")
@SessionScoped
public class FareRouteLinkAction extends MessageHelper<FareRouteLinkVO> {

private static final long serialVersionUID = 1L;
	
	//private FareRouteLinkSearchVO searchVO;

	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	private RouteAction routeAction;

	@PostConstruct
	//@Override
	public void init() {		
		setSelectedEntity(new FareRouteLinkVO());
	}
	
	@Override
	public List<FareRouteLinkVO> getEntityList() {
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
			serviceLocator.getFareRouteLinkFacade().deleteFareRouteLink(getSelectedEntity().getId());
			onMessage(SEVERITY_INFO, "Record deleted successfully");
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private void loadEntityList() {
		try {
			entityList = serviceLocator.getFareRouteLinkFacade().obtainListByRouteId(routeAction.getSelectedEntity().getId());
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private static final String VIEW_PAGE = "/admin/route/routeStopLinkView?faces-redirect=true";
	private static final String SUCCESS_PAGE = "/admin/route/routeStopLinkSuccess?faces-redirect=true";
}

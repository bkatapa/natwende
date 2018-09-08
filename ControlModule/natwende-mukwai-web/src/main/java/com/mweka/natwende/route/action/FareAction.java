package com.mweka.natwende.route.action;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.route.vo.FareVO;
import com.mweka.natwende.util.ServiceLocator;

@Named("FareAction")
@SessionScoped
public class FareAction extends MessageHelper<FareVO> {

	private static final long serialVersionUID = 1L;	
	
	//private FareSearchVO searchVO;

	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	private RouteAction routeAction;

	@PostConstruct
	public void init() {
		init(FareVO.class);
		//setSelectedEntity(new FareVO());
	}
	
	@Override
	public List<FareVO> getEntityList() {		
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
			serviceLocator.getFareFacade().deleteFare(getSelectedEntity().getId());
			onMessage(SEVERITY_INFO, "Record deleted successfully");
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private void loadEntityList() {
		try {
			entityList = serviceLocator.getFareFacade().obtainListByRouteId(routeAction.getSelectedEntity().getId());
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private static final String VIEW_PAGE = "/admin/route/stopView?faces-redirect=true";
	private static final String SUCCESS_PAGE = "/admin/route/stopSuccess?faces-redirect=true";

}

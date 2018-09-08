package com.mweka.natwende.operator.action;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.operator.vo.OperatorRouteLinkVO;
import com.mweka.natwende.util.ServiceLocator;

@Named("OperatorRouteLinkAction")
@SessionScoped
public class OperatorRouteLinkAction extends MessageHelper<OperatorRouteLinkVO> {

private static final long serialVersionUID = 1L;	
	
	//private OperatorRouteLinkSearchVO searchVO;

	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	private OperatorAction operatorAction;

	@PostConstruct
	//@Override
	public void init() {		
		setSelectedEntity(new OperatorRouteLinkVO());
	}
	
	@Override
	public List<OperatorRouteLinkVO> getEntityList() {		
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
			serviceLocator.getOperatorRouteLinkFacade().deleteOperatorRouteLink(getSelectedEntity().getId());
			onMessage(SEVERITY_INFO, "Record deleted successfully");
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private void loadEntityList() {
		try {
			entityList = serviceLocator.getOperatorRouteLinkFacade().obtainListByOperatorId(operatorAction.getSelectedEntity().getId());
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	private static final String VIEW_PAGE = "/admin/operator/operatorRouteLinkView?faces-redirect=true";
	private static final String SUCCESS_PAGE = "/admin/operator/operatorRouteLinkSuccess?faces-redirect=true";
	
}

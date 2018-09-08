package com.mweka.natwende.operator.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.operator.vo.OperatorVO;
import com.mweka.natwende.types.OperatorName;
import com.mweka.natwende.types.Status;
import com.mweka.natwende.util.ServiceLocator;

@Named("OperatorAction")
@SessionScoped
public class OperatorAction extends MessageHelper<OperatorVO> {

	private static final long serialVersionUID = 1L;	
	
	//private OperatorSearchVO searchVO;
	private List<OperatorVO> allAvailableOperators;

	@EJB
	private ServiceLocator serviceLocator;

	@PostConstruct
	//@Override
	public void init() {		
		setSelectedEntity(new OperatorVO(-1L));		
		allAvailableOperators = new ArrayList<>();
		
		for (OperatorName name : OperatorName.values()) {
			allAvailableOperators.add(new OperatorVO(name));
		}
	}
	
	@Override
	public List<OperatorVO> getEntityList() {		
		loadEntityList();
		return entityList;
	}

	@Override
	public String createEntity() {
		init();
		log.info("Opreator id = [" + getSelectedEntity().getId() + "]");
		return viewEntity();
	}
	
	@Override
	public String saveEntity() {
		try {
			setSelectedEntity(serviceLocator.getOperatorFacade().saveOperator(getSelectedEntity()));
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());			
		}
		return SUCCESS_PAGE;
	}
	
	@Override
	public String viewEntity() {
		return VIEW_PAGE;
	}
	
	@Override
	public void deleteEntity() {
		try {
			serviceLocator.getOperatorFacade().deleteOperator(getSelectedEntity().getId());
			onMessage(SEVERITY_INFO, "Record deleted successfully");
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	public boolean isNewOperator() {
		return getSelectedEntity().getId() < 0;
	}
	
	public List<OperatorVO> getAllAvailableOperators() {		
		return allAvailableOperators;
	}
	
	public List<OperatorName> getAllAvailableOperatorNames() {
		List<OperatorName> names = new ArrayList<>(Arrays.asList(OperatorName.values()));
		
		for (OperatorVO vo1 : entityList) {
			OperatorName result = null;
			for (OperatorName name : names) {				
				if (name == vo1.getName()) {
					result = name;
					continue;
				}				
			}
			if (result != null) {
				names.remove(result);
			}
		}
		return names;
	}
	
	private void loadEntityList() {
		entityList = serviceLocator.getOperatorDataFacade().getAllByStatus(Status.ACTIVE);
		
		for (OperatorVO vo1 : entityList) {
			OperatorVO result = null;
			for (OperatorVO vo2 : allAvailableOperators) {				
				if (vo2.getName() == vo1.getName()) {
					result = vo1;
					continue;
				}				
			}
			if (result != null) {
				allAvailableOperators.remove(result);
			}
		}
	}
	
	private static final String VIEW_PAGE = "/admin/operator/operatorView?faces-redirect=true";
	private static final String SUCCESS_PAGE = null; //"/admin/adminPanel?faces-redirect=true";
	
}

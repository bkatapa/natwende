package com.mweka.natwende.operator.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import com.mweka.natwende.action.AbstractActionBean;
import com.mweka.natwende.operator.vo.OperatorSettingsVO;
import com.mweka.natwende.types.ConfigAttribute;
import com.mweka.natwende.types.UserActionMode;
import com.mweka.natwende.util.ServiceLocator;

@Named("OperatorSettingsAction")
@SessionScoped
public class OperatorSettingsAction extends AbstractActionBean<OperatorSettingsVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private OperatorSettingsSearchVO searchVO = new OperatorSettingsSearchVO();
	private UserActionMode actionMode;
	private List<SelectItem> availableConfigAttributes = null;
	private List<ConfigAttribute> selectedConfigAttributes = new ArrayList<>();  
	private ConfigAttribute configAttributeToAdd;

	@EJB
	private ServiceLocator serviceLocator;	
	
	@PostConstruct
	public void initSettings() {
		availableConfigAttributes = new ArrayList<SelectItem>();
		for (ConfigAttribute attr : ConfigAttribute.values()) {
			availableConfigAttributes.add(new SelectItem(attr, attr.getDescription()));
		}
	}

	@Override
	public List<OperatorSettingsVO> getListFromFacade() {
		return getEntityList().getWrappedData();
	}

	@Override
	public OperatorSettingsVO facadeUpdate(OperatorSettingsVO dataItem) throws Exception {
		OperatorSettingsVO result = null; //serviceLocator.getOperatorSettingsDataFacade().update(dataItem);
		if (actionMode == UserActionMode.CREATE) {
			addFacesMessageInfo("Settings created successfully");			
		}
		else if (actionMode == UserActionMode.EDIT) {
			addFacesMessageInfo("Settings updated successfully");
		} 
		else {
			addFacesMessageInfo("Settings saved successfully");
		}
		return result;
	}

	@Override
	protected void facadeDelete(OperatorSettingsVO dataItem) {
		if (dataItem.getId() > -1L) {
			try {
				//serviceLocator.getOperatorSettingsDataFacade().deleteById(dataItem.getId());
			} catch (Exception e) {
				log.debug(e);
				addFacesMessageError("There was a problem deleting the settings: ", e.getMessage());
			}
		}
		else {
			addFacesMessageError("Error occurred.", "There settings you are requesting to delete do not exist on the database");
		}
	}

	@Override
	protected String getViewPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getListPage() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void refresh() {
		actionMode = null;
		//searchVO = new OperatorSettingsSearchVO();
	}

//	public OperatorSettingsSearchVO getSearchVO() {
//		return searchVO;
//	}

//	public void setSearchVO(OperatorSettingsSearchVO searchVO) {
//		this.searchVO = searchVO;
//	}
	
	public UserActionMode getActionMode() {
		return actionMode;
	}

	public void setActionMode(UserActionMode actionMode) {
		this.actionMode = actionMode;
	}
	
	public List<SelectItem> getAvailableConfigAttributes() {	        
		return availableConfigAttributes;  
	}  
	  
	public List<ConfigAttribute> getSelectedConfigAttributes() {  
		return selectedConfigAttributes;  
	}  
	  
	public void setConfigAttributeToAdd(ConfigAttribute configAttributeToAdd) {  
		this.configAttributeToAdd = configAttributeToAdd;  
	}  
	  
	public ConfigAttribute getConfigAttributeToAdd() {  
		return configAttributeToAdd;  
	}
	
	 public void addConfigAttribute(OperatorSettingsVO settings) {  
		 if (configAttributeToAdd != null) {  
			 //settings.getConfigAttributes().add(configAttributeToAdd);  
		 }  
		 configAttributeToAdd = null;  
	 } 
}

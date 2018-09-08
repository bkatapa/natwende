package com.mweka.natwende.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;

import com.mweka.natwende.cdi.LoggedInUser;
import com.mweka.natwende.location.vo.AddressVO;
import com.mweka.natwende.types.PagePath;
import com.mweka.natwende.types.RoleType;
import com.mweka.natwende.user.action.ThemeSessionAction;
import com.mweka.natwende.user.vo.RoleVO;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.ServiceLocator;
import com.mweka.natwende.util.SystemConfigurationBean;
import java.io.Serializable;


@Named("NatwendeUtils")
@RequestScoped
public class NatwendeUtils implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ServiceLocator serviceLocator;
	
	@EJB
	private SystemConfigurationBean systemConfigurationBean;
	
	@Inject
	private Log log; // = LogFactory.getLog(this.getClass());
	
	@Inject
	private ThemeSessionAction themeSessionAction;
	
	@Inject
	@LoggedInUser
	private UserVO userVO;
	
	private Set<String> userPermissionSet;	
	
	public Log getLog() {
		if (log == null) {
			return LogFactory.getLog(this.getClass());
		}
		return log;
	}

	public boolean validateAddress(AddressVO address) {
		boolean failed = false;
		if (StringUtils.isBlank(address.getName())) {
			failed = true;
			//logWarnMessage("Delivery or Postal Address: Street Name is not set");
		} else if (StringUtils.isBlank(address.getPostalCode())) {
			failed = true;
			//logWarnMessage("Delivery or Postal Address: Postal Code is not set");
		} else if (StringUtils.isBlank(address.getCity())) {
			failed = true;
			//logWarnMessage("Delivery or Postal Address: City is not set");
		} else if (StringUtils.isBlank(address.getProvince())) {
			failed = true;
			//logWarnMessage("Delivery or Postal Address: Province is not set");
		}else if (StringUtils.isBlank(address.getCountry())) {
			failed = true;
			//logWarnMessage("Delivery or Postal Address: Country is not set");
		}
		return failed;
	}
	
	public void logWarnMessage(String warnMsg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", warnMsg));
	}
	
	public void logErrorMessage(String errMsg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", errMsg));
	}
	
	public void logInfoMessage(String infoMsg, String...args) {
		String infoTitle = "Success"; // default
		String componentToUpdate = null;
		if (args != null && args.length > 0 && args[0] instanceof String) {
			if (args.length == 1) {
				infoTitle = args[0];
			}
			if (args.length == 2) {
				componentToUpdate = args[1];
			}
		}
		FacesContext.getCurrentInstance().addMessage(componentToUpdate, new FacesMessage(FacesMessage.SEVERITY_INFO, infoMsg, infoTitle));
	}
	
	public String getBaseUrl() {
		return serviceLocator.getSystemConfigurationBean().getBaseURL();
	}
	
	public void showWarnMessageDlg(String warnMsg) {
		if (StringUtils.isBlank(warnMsg)) {
			return;
		}
		RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", warnMsg));
	}
	
	public void showErrorMessageDlg(String errMsg) {
		if (StringUtils.isBlank(errMsg)) {
			return;
		}
		RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", errMsg));
	}
	
	public void showInfoMessageDlg(String infoMsg, String...args) {
		if (StringUtils.isBlank(infoMsg)) {
			return;
		}
		String infoTitle = "Success";
		if (args != null && args.length > 0 && args[0] instanceof String) {
			infoTitle = args[0];
		}
		RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, infoTitle, infoMsg));
	}
	
	public List<RoleType> obtainRoleEnumTypesFromRoleVOList(List<RoleVO> roleVOs) {
		List<RoleType> roleTypes = new ArrayList<>();
		for (RoleVO roleVO : roleVOs) {
			roleTypes.add(roleVO.getRoleType());
		}
		return roleTypes;
	}
	
	public List<RoleVO> convertRoleTypesToRoleVOs(List<RoleType> roleTypes) {
		List<RoleVO> roleVOs = new ArrayList<>();
		for (RoleType selectedRole : roleTypes) {
			roleVOs.add(new RoleVO(selectedRole));
		}
		return roleVOs;
	}
	
	public List<RoleVO> convertRoleTypesToRoleVOs() {		
		return convertRoleTypesToRoleVOs(convertUserPermissionSetToRoleTypeList());
	}
	
	public List<RoleType> convertUserPermissionSetToRoleTypeList(Set<String> permissionSet) {
		List<RoleType> roleTypeList = new ArrayList<>();
		for (String selectedPermission : permissionSet) {
			roleTypeList.add(RoleType.valueOf(selectedPermission));
		}
		return roleTypeList;
	}
	
	public List<RoleType> convertUserPermissionSetToRoleTypeList() {		
		return convertUserPermissionSetToRoleTypeList(getUserPermissionSet());
	}
	
	public String getBaseURL() {
		return systemConfigurationBean.getBaseURL();
	}

	@SuppressWarnings("unchecked")
	public Set<String> getUserPermissionSet() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		userPermissionSet = (Set<String>) session.getAttribute("userPermissionSet");
		return userPermissionSet;
	}
	
	@SuppressWarnings("unchecked")
	public void addPageToBrowseHistory(String page) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Stack<String> browseHistory = (Stack<String>) session.getAttribute("browseHistory");
		if (browseHistory != null) {
			browseHistory.push(page);
			session.setAttribute("browseHistory", browseHistory);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getPreviousPageFromBrowseHistory() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
		Stack<String> browseHistory = (Stack<String>) session.getAttribute("browseHistory");
		String previousPage = null;
		if (browseHistory != null && StringUtils.isNotBlank(browseHistory.peek())) {
			previousPage = browseHistory.pop();
			session.setAttribute("browseHistory", browseHistory);
		} 
		else {
			previousPage = PagePath.HOME.getPath();
		}
		return previousPage;
	}
	
	public List<RoleType> getAllDeclaredRoleTypes() {
		return new ArrayList<RoleType>(Arrays.asList(RoleType.values()));
	}	
	
	public String getOperatorNameForCurrentUser() {
		if (userVO.getOperator() == null) {
			return "Undefined";
		}
		return userVO.getOperator().getName().getDisplay();
	}

	public Map<String,String> getParkingSiteSettingsMap() {
		return themeSessionAction.getOperatorSettingsMap();
	}	
	
}

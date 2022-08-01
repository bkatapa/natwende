package com.mweka.natwende.user.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.exceptions.UserNotFoundException;
import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.location.vo.AddressVO;
import com.mweka.natwende.operator.action.OperatorAction;
import com.mweka.natwende.payment.vo.CardVO;
import com.mweka.natwende.types.RoleType;
import com.mweka.natwende.user.vo.RoleVO;
import com.mweka.natwende.user.vo.UserRoleLinkVO;
import com.mweka.natwende.user.vo.UserSearchVO;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.RoleTypeHelper;
import com.mweka.natwende.util.ServiceLocator;

@Named("UserAction")
@SessionScoped
public class UserAction extends MessageHelper<UserVO> {

	private static final long serialVersionUID = 1L;

	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	private FacesContext facesContext;
	
	@Inject
	private OperatorAction operatorAction;

	private UserSearchVO userSearchVO = new UserSearchVO();	
	private AddressVO selectedAddress;	
	private CardVO selectedCard;
	private RoleVO userRole;
	private Set<RoleType> roleSet;
	private List<RoleVO> roleList;
	private List<CardVO> cardList;
	private RoleTypeHelper selectedRoleTypeHelper;	
	private List<RoleType> selectedRoleTypes;
	private List<UserVO> driverList;
	private int currentStep, activeIndex;
	private UploadedFile file;
	
	@PostConstruct
	public void init() {
		setSelectedEntity(new UserVO());
		selectedAddress = new AddressVO();		
		selectedRoleTypes = new ArrayList<>();
		cardList = new ArrayList<>();
		driverList = Collections.emptyList();
		currentStep = activeIndex = 0;
		userRole = new RoleVO(RoleType.PASSENGER);
		roleSet = EnumSet.copyOf(Arrays.asList(RoleType.values()));
		roleList = serviceLocator.getRoleDataFacade().getAllAvailableRoles();
	}
	
	public int getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public RoleVO getUserRole() {
		return userRole;
	}

	public void setUserRole(RoleVO userRole) {
		this.userRole = userRole;
	}

	public Set<RoleType> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<RoleType> roleSet) {
		this.roleSet = roleSet;
	}

	public List<RoleVO> getRoleList() {
		return roleList;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	@Override
	public List<UserVO> getEntityList() {
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
		try {
			if (RoleType.BUS_DRIVER == userRole.getRoleType() && null == selectedEntity.getOperator()) {
				selectedEntity.setOperator(operatorAction.getSelectedEntity());
			}
			selectedEntity = serviceLocator.getUserFacade().updateUser(selectedEntity, userRole);
			return SUCCESS_PAGE;
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
		return null;
	}
	
	@Override
	public String viewEntity() {		
		return VIEW_PAGE;
	}

	@Override
	public void deleteEntity() {
		try {
			serviceLocator.getUserDataFacade().deleteById(selectedEntity.getId());
			onMessage(SEVERITY_INFO, "User deleted successfully");
		} catch (EntityNotFoundException ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	public void updateEntity() {
		try {
			saveEntity();
			executeScript("PF('var_UserEditDlg').hide();");
			updateComponent("@widgetVar(var_UserEditDlg)");
			onMessage(SEVERITY_INFO, "User [" + selectedEntity.getName() + "] saved successfully!");
		} catch (Exception e) {
			log.debug(e);
			onMessage(SEVERITY_ERROR, e.getMessage());
		}
	}

	public void activate() {
		try {
			serviceLocator.getUserDataFacade().markAsActive(getSelectedEntity());
			onMessage(SEVERITY_INFO, "User re-activated");
		} catch (EntityNotFoundException ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}	

	public void reset() {
		userSearchVO.clearSearch();
	}
	
	public void logout() {		
		try {
			facesContext.getExternalContext().invalidateSession();
			facesContext.getExternalContext().redirect("/natwende");
		} catch (IOException e) {
			log.debug(e);
			onMessage(SEVERITY_ERROR, e.getMessage());
		};
	}
	
	public UserVO getLoggedInUser() {
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		UserVO user = null;
		
		if (request.getUserPrincipal() != null) {
			if (session.getAttribute(LOGGED_IN_USER) != null) {
				return (UserVO) session.getAttribute(LOGGED_IN_USER);
			}
			try {
				user = serviceLocator.getUserDataFacade().getUserByUsername(request.getUserPrincipal().getName());
				session.setAttribute(LOGGED_IN_USER, user);
			} catch (UserNotFoundException e) {
				log.debug(e);
				onMessage(SEVERITY_ERROR, e.getMessage());
			}
		}
		return user;
	}

	public UserSearchVO getUserSearchVO() {
		return userSearchVO;
	}

	public void setUserSearchVO(UserSearchVO userSearchVO) {
		this.userSearchVO = userSearchVO;
	}	

	public void setRoleTypeHelper(UserVO userVO, List<RoleVO> roleVOs) {
		selectedRoleTypeHelper = new RoleTypeHelper(userVO, roleVOs);
	}

	public RoleTypeHelper getSelectedRoleTypeHelper() {
		return selectedRoleTypeHelper;
	}	

	public CardVO getSelectedCard() {
		return selectedCard;
	}

	public void setSelectedCard(CardVO selectedCard) {
		this.selectedCard = selectedCard;
	}

	public AddressVO getSelectedAddress() {
		return selectedAddress;
	}

	public void setSelectedAddress(AddressVO selectedAddress) {
		this.selectedAddress = selectedAddress;
	}	
	
	public String register() {
		try {
			saveEntity();
			onMessage(SEVERITY_INFO, selectedEntity.getName() + " created successfully!");
			return "/comp/user/regSuccess?faces-redirect=true";
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
		return null;
	}
	
	public RoleType[] getRoleTypes() {		
		return RoleType.values();
	}

	public List<RoleType> getSelectedRoleTypes() {
		return selectedRoleTypes;
	}

	public void setSelectedRoleTypes(List<RoleType> selectedRoleTypes) {
		this.selectedRoleTypes = selectedRoleTypes;
	}

	public List<CardVO> getCardList() {
		return cardList;
	}
	
	public List<UserVO> getDriverList() {
		return driverList;
	}

	public void setCardList(List<CardVO> cardList) {
		this.cardList = cardList;
	}
	
	public void loadDriverList() {
		driverList = serviceLocator.getUserFacade().getUserList(RoleType.BUS_DRIVER, operatorAction.getSelectedEntity().getName());
	}
	
	public void loadEntityList() {
		entityList = serviceLocator.getUserDataFacade().getAllUsersList();
	}
	
	public void step(ActionEvent event) {
		UICommand comp = (UICommand) event.getComponent();
		String text = (String) comp.getValue();
		
		switch (text.toLowerCase()) {
		case "next" : setCurrentStep(currentStep + 1);
		break;
		case "prev" : setCurrentStep(currentStep - 1);
		break;
		case "finish" : onMessage(SEVERITY_INFO, "Finished!");
		break;
		default : onMessage(SEVERITY_ERROR, "Unsupported option: [" + text + "]");
		}
	}
	
	public StreamedContent getPreviewImage() throws IOException {
		if (facesContext.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		}		
		String userId = facesContext.getExternalContext().getRequestParameterMap().get("userId");
		UserVO user;
		if (StringUtils.isNotBlank(userId)) {
			user = serviceLocator.getUserDataFacade().getById(Long.valueOf(userId));
			return getPreviewImage(user);
		}
		return getPreviewImage(selectedEntity);
		
	}
	
	public StreamedContent getPreviewImage(UserVO user) throws IOException {
		if (user == null || user.getProfilePic() == null) {
			return getDefaultPreviewImage();
		}
		return DefaultStreamedContent.builder().stream(() -> user.getProfilePicAsStream()).build();
	}
	
	public StreamedContent getDefaultPreviewImage() throws IOException {
		return DefaultStreamedContent.builder()
				.contentType("image/png")
				.stream(() -> facesContext.getExternalContext().getResourceAsStream("/resources/images/default-user.png"))
				.build();
	}
	
	public void onTabChange(TabChangeEvent event) {
		AccordionPanel accordion = (AccordionPanel) event.getComponent().getParent();
		activeIndex = Integer.parseInt(accordion.getActiveIndex());
	}
	
	public void handlePictureUpload(FileUploadEvent event) {
		file = event.getFile();
		upload();
	}
	
	private void upload() {
		if (file != null) {
			selectedEntity.setProfilePic(file.getContent());
			if (selectedEntity.getId() != -1L) {
				saveEntity();
			}
			onMessage(SEVERITY_INFO, file.getFileName() + " is uploaded.");
			executeScript("location.reload();");
		}
	}
	
	public void resetPassword() {
		onMessage(SEVERITY_INFO, "Password reset link sent to " + selectedEntity.getEmail());
	}
	
	public void reloadCurrentURI() {
		executeScript("location.reload();");
	}
	
	public void onSelectEntity() {
		List<UserRoleLinkVO> resultList= serviceLocator.getUserRoleLinkFacade().obtainUserRoleLinkListGivenUser(selectedEntity.getId());
		if (!resultList.isEmpty()) {
			userRole = resultList.get(0).getRole();
		}
	}
	
	private static final String VIEW_PAGE = "/admin/user/userView?faces-redirect=true&i=2";
	private static final String SUCCESS_PAGE = "/admin/user/userSuccess?faces-redirect=true";
	private static final String LOGGED_IN_USER = "loggedInUser";
	
}

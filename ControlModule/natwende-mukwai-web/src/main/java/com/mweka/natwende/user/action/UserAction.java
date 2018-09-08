package com.mweka.natwende.user.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.location.vo.AddressVO;
import com.mweka.natwende.payment.vo.CardVO;
import com.mweka.natwende.types.RoleType;
import com.mweka.natwende.user.vo.RoleVO;
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

	private UserSearchVO userSearchVO = new UserSearchVO();	
	
	private AddressVO selectedAddress;
	
	private CardVO selectedCard;
	
	private List<CardVO> cardList;	

	private RoleTypeHelper selectedRoleTypeHelper;
	
	private List<RoleType> selectedRoleTypes;
	
	@PostConstruct
	public void init() {
		setSelectedEntity(new UserVO());
		selectedAddress = new AddressVO();		
		selectedRoleTypes = new ArrayList<>();
		cardList = new ArrayList<>();
	}
	
	@Override
	public List<UserVO> getEntityList() {		
		return serviceLocator.getUserDataFacade().getAllUsersList();
	}
	
	@Override
	public String createEntity() {
		init();
		return viewEntity();
	}

	@Override
	public String saveEntity() {
		try {
			serviceLocator.getUserFacade().updateUser(getSelectedEntity(), null);
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
			serviceLocator.getUserDataFacade().markAsInactive(getSelectedEntity());
			onMessage(SEVERITY_INFO, "User de-activated");
		} catch (EntityNotFoundException ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
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
	
	public String logout() {
		facesContext.getExternalContext().invalidateSession();
		return "/";
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
			return saveEntity();			
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

	public void setCardList(List<CardVO> cardList) {
		this.cardList = cardList;
	}
	
	private static final String VIEW_PAGE = "/admin/user/userView?faces-redirect=true";
	private static final String SUCCESS_PAGE = "/admin/user/userSuccess?faces-redirect=true";
	
}

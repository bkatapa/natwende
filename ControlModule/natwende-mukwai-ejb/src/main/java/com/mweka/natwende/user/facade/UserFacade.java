package com.mweka.natwende.user.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.exceptions.UserNotFoundException;
import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.types.OperatorName;
import com.mweka.natwende.types.RoleType;
import com.mweka.natwende.types.Status;
import com.mweka.natwende.user.vo.RoleVO;
import com.mweka.natwende.user.vo.UserPasswordResetVO;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.ServiceLocator;
import com.rits.cloning.Cloner;

@Stateless
@LocalBean
public class UserFacade extends AbstractFacade<UserVO> {

	@EJB
	ServiceLocator serviceLocator;

	public UserFacade() {
		super(UserVO.class);
		this.log = LogFactory.getLog(this.getClass().getName());
	}

	public void resendWelcomeEmailsToNonRegisteredBuyersBySupplierId(long supplierId) throws UserNotFoundException {
		List<UserVO> nonRegisteredBuyerUsers = serviceLocator.getUserDataFacade().getNonRegisteredBuyers(supplierId);
		List<UserPasswordResetVO> passwordResetVOList = new ArrayList<>(nonRegisteredBuyerUsers.size());

		for (UserVO buyerUser : nonRegisteredBuyerUsers) {
			UserPasswordResetVO passwordResetVO = serviceLocator.getUserPasswordResetDataFacade()
					.generateUserPasswordResetVOFor7Days(buyerUser.getEmail());
			passwordResetVOList.add(passwordResetVO);
		}
		for (UserPasswordResetVO passwordResetVO : passwordResetVOList) {
			serviceLocator.getMailerFacade().resendUserWelcomeEmail(passwordResetVO);
		}
	}

	public UserVO updateUser(UserVO userVO, List<RoleType> permissions) throws Exception {
		try {
			// Why use a cloned copy? For some odd reason first call to persist
			// sometimes would drop optional (fields with required=false) form
			// values from the userVO.
			UserVO copyOfUserVO = new Cloner().deepClone(userVO);

			/**
			 * permissions.get(0) == null or != null check added because the
			 * permissions list sometimes was coming with null elements it
			 * appears whenever a list is instantiated with new ArrayList<>()
			 * the runtime initializes the list with 10 null elements, more like
			 * the new Object[] array would do. Most likely under the hoods the
			 * ArrayList implementation is actually instantiating and
			 * manipulating arrays of objects.
			 */
			if (copyOfUserVO.getId() != -1L && (CollectionUtils.isEmpty(permissions) || permissions.get(0) == null)) {
				copyOfUserVO
						.setRoleVOs(serviceLocator.getRoleFacade().getDatabaseRoleVOsByUserId(copyOfUserVO.getId()));
			} else {
				serviceLocator.getUserRoleLinkFacade().dropCurrentDatabaseLinksForThisUser(copyOfUserVO.getId());
			}

			userVO = serviceLocator.getUserDataFacade().update(copyOfUserVO);

			if (CollectionUtils.isNotEmpty(permissions) && permissions.get(0) != null) {
				// Insert new links
				List<RoleVO> databasePermissions = serviceLocator.getRoleFacade()
						.getDatabaseRoleVOsByRoleTypes(permissions);
				serviceLocator.getUserRoleLinkFacade().insertNewDatabaseLinksForThisUser(userVO, databasePermissions);
				userVO.setRoleVOs(databasePermissions);
			}
			return userVO;
		} catch (Exception e) {
			log.debug(e);
			throw e;
		}
	}

	public List<UserVO> getAllUsersWithSpecifiedPermissions(List<RoleType> permissions) {
		List<UserVO> userVOs = serviceLocator.getUserDataFacade().getUsersByPermissions(permissions);
		return userVOs;
	}

	public UserVO toggleUserStatus(UserVO userVO) throws Exception {
		if (userVO.getStatus() == Status.ACTIVE) {
			userVO.setStatus(Status.INACTIVE);
		} else if (userVO.getStatus() == Status.INACTIVE) {
			userVO.setStatus(Status.ACTIVE);
		}
		return serviceLocator.getUserDataFacade().update(userVO);
	}
	
	public List<UserVO> getUserList(RoleType roleType, OperatorName operatorName) {
		return serviceLocator.getUserDataFacade().getListByRoleTypeAndOperatorName(roleType, operatorName);
	}

}

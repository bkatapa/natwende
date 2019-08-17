package com.mweka.natwende.user.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.types.OperatorName;
import com.mweka.natwende.types.RoleType;
import com.mweka.natwende.types.Status;
import com.mweka.natwende.user.vo.RoleVO;
import com.mweka.natwende.user.vo.UserRoleLinkVO;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.ServiceLocator;

@Stateless
@LocalBean
public class UserFacade extends AbstractFacade<UserVO> {

	@EJB
	ServiceLocator serviceLocator;

	public UserFacade() {
		super(UserVO.class);
		this.log = LogFactory.getLog(this.getClass().getName());
	}

	public UserVO updateUser(UserVO userVO, List<RoleType> permissions) throws Exception {
		try {
			if (userVO.getId() != -1L && (CollectionUtils.isEmpty(permissions) || permissions.get(0) == null)) {
				userVO.setRoleVOs(serviceLocator.getRoleFacade().getDatabaseRoleVOsByUserId(userVO.getId()));
			} else {
				serviceLocator.getUserRoleLinkFacade().dropCurrentDatabaseLinksForThisUser(userVO.getId());
			}

			userVO = serviceLocator.getUserDataFacade().update(userVO);

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
	
	public UserVO updateUser(UserVO userVO, RoleVO role) throws Exception {
		try {
			userVO = serviceLocator.getUserDataFacade().update(userVO);			
			UserRoleLinkVO url = serviceLocator.getUserRoleLinkDataFacade().getByUserAndRole(userVO, role);
			serviceLocator.getUserRoleLinkDataFacade().update(url == null ? new UserRoleLinkVO(userVO, role) : url);
			return userVO;
		}
		catch (Exception ex) {
			log.debug(ex);
			throw new EJBException(ex);
		}
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
		return serviceLocator.getUserDataFacade().getByRoleTypeAndOperatorName(roleType, operatorName);
	}

}

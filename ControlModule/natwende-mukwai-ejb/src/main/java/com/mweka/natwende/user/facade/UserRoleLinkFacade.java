package com.mweka.natwende.user.facade;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.user.vo.RoleVO;
import com.mweka.natwende.user.vo.UserRoleLinkVO;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.ServiceLocator;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.LogFactory;

@Stateless
@LocalBean
public class UserRoleLinkFacade extends AbstractFacade<UserRoleLinkVO> {

    @EJB
    ServiceLocator serviceLocator;

    public UserRoleLinkFacade() {
        super(UserRoleLinkVO.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }
    
    public void dropCurrentDatabaseLinksForThisUser(long userId) {
    	List<UserRoleLinkVO> linkVOs = serviceLocator.getUserRoleLinkDataFacade().getUserRoleLinksByUserId(userId);
    	if (CollectionUtils.isNotEmpty(linkVOs)) {
    		for (UserRoleLinkVO linkVO : linkVOs) {
    			try {
					serviceLocator.getUserRoleLinkDataFacade().deleteById(linkVO.getId());
				} catch (EntityNotFoundException e) {
					log.debug(e);
					throw new EJBException(e);
				}
    		}
    	}
    }

    public List<RoleVO> insertNewDatabaseLinksForThisUser(UserVO userVO, List<RoleVO> newRoleVOs) throws Exception {    	
    	for (RoleVO newRoleVO : newRoleVOs) {
    		serviceLocator.getUserRoleLinkDataFacade().update(new UserRoleLinkVO(userVO, newRoleVO));    		
    	}    	
    	return newRoleVOs;
    }
    
    public List<UserRoleLinkVO> obtainUserRoleLinkListGivenUser(Long userId) {
    	try {
    		List<UserRoleLinkVO> result = serviceLocator.getUserRoleLinkDataFacade().getUserRoleLinksByUserId(userId);
    		return result;
    	}
    	catch(Exception e) {
    		throw new EJBException(e);
    	}
    }
}

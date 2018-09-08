package com.mweka.natwende.user.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.exceptions.RoleNotFoundException;
import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.types.RoleType;
import com.mweka.natwende.user.vo.RoleVO;
import com.mweka.natwende.user.vo.UserRoleLinkVO;
import com.mweka.natwende.util.ServiceLocator;

@Stateless
@LocalBean
public class RoleFacade extends AbstractFacade<RoleVO> {

    @EJB
    ServiceLocator serviceLocator;

    public RoleFacade() {
        super(RoleVO.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }
    
    public List<RoleVO> getDatabaseRoleVOsByRoleTypes(List<RoleType> roleTypes) {
    	List<RoleVO> roleVOs = new ArrayList<RoleVO>();
    	for (RoleType roleType : roleTypes) {
    		try {
				roleVOs.add(serviceLocator.getRoleDataFacade().getRolebyRoleType(roleType));
			} catch (RoleNotFoundException e) {
				log.debug(e);
				throw new EJBException(e);
			}
    	}
    	return roleVOs;
    }
    
    public List<RoleVO> getDatabaseRoleVOsByUserId(long userId) {
    	List<UserRoleLinkVO> linkVOs = serviceLocator.getUserRoleLinkDataFacade().getUserRoleLinksByUserId(userId);
    	List<RoleVO> roleVOs = new ArrayList<RoleVO>();
    	for (UserRoleLinkVO linkVO : linkVOs) {
    		roleVOs.add(linkVO.getRole());
    	}
    	return roleVOs;
    }

}

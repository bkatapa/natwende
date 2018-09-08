package com.mweka.natwende.user.facade;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.exceptions.UserNotFoundException;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.user.vo.UserRoleLinkVO;
import com.mweka.natwende.user.entity.Role;
import com.mweka.natwende.user.entity.User;
import com.mweka.natwende.user.entity.UserRoleLink;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import org.apache.commons.logging.LogFactory;

@Stateless
public class UserRoleLinkDataFacade extends AbstractDataFacade<UserRoleLinkVO, UserRoleLink> {

    public UserRoleLinkDataFacade() {
        super(UserRoleLinkVO.class, UserRoleLink.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    public UserRoleLink updateEntity(UserRoleLinkVO userRoleLinkVO) {
        UserRoleLink userRoleLink;
        if (userRoleLinkVO.getId() > 0) {
            userRoleLink = findById(userRoleLinkVO.getId());
        } else {
            userRoleLink = new UserRoleLink();
        }
        convertVOToEntity(userRoleLinkVO, userRoleLink);
        update(userRoleLink);
        return userRoleLink;
    }

    @Override
    public void convertEntitytoVO(UserRoleLink userRoleLink, UserRoleLinkVO userRoleLinkVO) {
        if (userRoleLink.getRole() != null) {
            userRoleLinkVO.setRole(serviceLocator.getRoleDataFacade().getCachedVO(userRoleLink.getRole()));
        }
        if (userRoleLink.getUser() != null) {
            userRoleLinkVO.setUser(serviceLocator.getUserDataFacade().getCachedVO(userRoleLink.getUser()));
        }
    }

    @Override
    public UserRoleLink convertVOToEntity(UserRoleLinkVO userRoleLinkVO, UserRoleLink userRoleLink) {
        convertBaseVOToEntity(userRoleLinkVO, userRoleLink);
        if (userRoleLinkVO.getRole() != null) {
            userRoleLink.setRole(serviceLocator.getRoleDataFacade().convertVOToEntity(userRoleLinkVO.getRole(), new Role()));
        }
        if (userRoleLinkVO.getUser() != null) {
            userRoleLink.setUser(serviceLocator.getUserDataFacade().convertVOToEntity(userRoleLinkVO.getUser(), new User()));
        }
        return userRoleLink;
    }

    @Override
    public UserRoleLinkVO update(UserRoleLinkVO userRoleLinkVO) throws EntityNotFoundException {
        UserRoleLink userRoleLink = updateEntity(userRoleLinkVO);
        return getCachedVO(userRoleLink);
    }    

    /**
     * Find User By Username
     *
     * @param username
     * @return
     * @throws UserNotFoundException
     *
     */
    private List<UserRoleLink> findUserRoleLinksByUserId(Long userId) {
        TypedQuery<UserRoleLink> userRoleLinksByUserIdTypedQuery = getEntityManager().createNamedQuery("UserRoleLink.findByUserId", getEntityClass()).setParameter("userId", userId);
        return userRoleLinksByUserIdTypedQuery.getResultList();
    }

    public List<UserRoleLinkVO> getUserRoleLinksByUserId(Long userId) {
        List<UserRoleLink> userRoleLinks = findUserRoleLinksByUserId(userId);
        return transformList(userRoleLinks);
    }    

}

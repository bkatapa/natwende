package com.mweka.natwende.user.facade;

import com.mweka.natwende.exceptions.RoleNotFoundException;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.types.RoleType;
import com.mweka.natwende.types.Status;
import com.mweka.natwende.user.vo.RoleVO;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.user.entity.Role;
import com.mweka.natwende.user.entity.User;

import java.util.List;

import javax.ejb.Stateless;
import org.apache.commons.logging.LogFactory;

@Stateless
public class RoleDataFacade extends AbstractDataFacade<RoleVO, Role> {

    public RoleDataFacade() {
        super(RoleVO.class, Role.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected Role updateEntity(RoleVO roleVO) {
        Role role = roleVO.getId() > 0 ? findById(roleVO.getId()) : new Role();
        convertVOToEntity(roleVO, role);
        update(role);
        return role;
    }

    @Override
    public void convertEntitytoVO(Role entity, RoleVO vo) {
    	super.convertBaseEntityToVO(entity, vo);
        vo.setRoleType(entity.getRoleType());
    }

    @Override
    public Role convertVOToEntity(RoleVO vo, Role entity) {
        super.convertBaseVOToEntity(vo, entity);
        entity.setRoleType(vo.getRoleType());
        return entity;
    }

    @Override
    public RoleVO update(RoleVO roleVO) {
        Role role = updateEntity(roleVO);
        return getCachedVO(role);
    }
    
    public List<RoleVO> getByUserIdAndStatus(long userId, Status status) {
    	List<Role> resultList = createNamedQuery(Role.QUERY_FIND_BY_USER_ID_AND_STATUS, getEntityClass())
    			.setParameter(Role.PARAM_STATUS, status)
    			.setParameter(Role.PARAM_USER_ID, userId)
    			.getResultList();
    	return transformList(resultList);
    }

    public RoleVO getRolebyRoleType(RoleType roleType) throws RoleNotFoundException {
    	List<Role> resultList = createNamedQuery(Role.QUERY_FIND_BY_ROLETYPE, getEntityClass())
        		.setParameter(Role.PARAM_ROLE_TYPE, roleType)
        		.getResultList();
        if (resultList.isEmpty()) {
            throw new RoleNotFoundException("Role not found for roleType : " + roleType.getCode());
        }       
        return getVOFromList(resultList);
    }
    
    public List<RoleVO> getAllAvailableRoles() {
    	return transformList(findAll());
    }
    
    public List<RoleType> getRoleTypesByUser(UserVO user) {
    	List<RoleType> resultList = getEntityManager().createQuery(" SELECT url.role.roleType FROM UserRoleLink url WHERE url.user.id = :userId ", RoleType.class)
    			.setParameter(User.PARAM_USER_ID, user.getId())
        		.getResultList();       
        return resultList;
    }

}

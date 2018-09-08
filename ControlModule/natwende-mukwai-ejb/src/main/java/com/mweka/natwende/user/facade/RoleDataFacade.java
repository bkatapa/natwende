package com.mweka.natwende.user.facade;

import com.mweka.natwende.exceptions.RoleNotFoundException;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.types.RoleType;
import com.mweka.natwende.types.Status;
import com.mweka.natwende.user.vo.RoleVO;
import com.mweka.natwende.user.entity.Role;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.LogFactory;

@Stateless
public class RoleDataFacade extends AbstractDataFacade<RoleVO, Role> {

    public RoleDataFacade() {
        super(RoleVO.class, Role.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected Role updateEntity(RoleVO roleVO) {
        Role role;
        if (roleVO.getId() > 0) {
            role = findById(roleVO.getId());
        } else {
            role = new Role();
        }
        convertVOToEntity(roleVO, role);
        update(role);
        return role;
    }

    @Override
    public void convertEntitytoVO(Role role, RoleVO roleVO) {
        roleVO.setRoleType(role.getRoleType());
    }

    @Override
    public Role convertVOToEntity(RoleVO roleVO, Role role) {
        convertBaseVOToEntity(roleVO, role);
        role.setRoleType(roleVO.getRoleType());
        return role;
    }

    @Override
    public RoleVO update(RoleVO roleVO) {
        Role role = updateEntity(roleVO);
        return getCachedVO(role);
    }

    public Role findRoleVObyRoleType(RoleType roleType) throws RoleNotFoundException {
        TypedQuery<Role> findRoleVObyRoleTypeTypedQuery = getEntityManager().createNamedQuery(Role.QUERY_FIND_BY_ROLETYPE, getEntityClass())
        		.setParameter(Role.PARAM_ROLE_TYPE, roleType);
        List<Role> roles = findRoleVObyRoleTypeTypedQuery.getResultList();
        if (roles.isEmpty()) {
            throw new RoleNotFoundException("Role not found for roleType : " + roleType.getCode());
        } else {
            return roles.get(0);
        }
    }
    
    public List<Role> findByUserIdAndStatus(long userId, Status status) {
    	TypedQuery<Role> query = getEntityManager().createNamedQuery(Role.QUERY_FIND_BY_USER_ID_AND_STATUS, getEntityClass());
    	query.setParameter(Role.PARAM_STATUS, status);
    	query.setParameter(Role.PARAM_USER_ID, userId);
    	return query.getResultList();
    }
    
    
    public List<RoleVO> getByUserIdAndStatus(long userId, Status status) {
    	return transformList(findByUserIdAndStatus(userId, status));
    }

    public RoleVO getRolebyId(int id, RoleFacade roleFacade) throws RoleNotFoundException {
        Role role = findById(id);
        return getCachedVO(role);
    }

    public RoleVO getRolebyRoleType(RoleType roleType) throws RoleNotFoundException {
        Role role = findRoleVObyRoleType(roleType);
        return getCachedVO(role);
    }
    
    public List<RoleVO> getAllAvailableRoles() {
    	TypedQuery<Role> query = getEntityManager().createNamedQuery(Role.QUERY_FIND_ALL, getEntityClass());
    	List<Role> results = query.getResultList();
    	return transformList(results);
    }

}

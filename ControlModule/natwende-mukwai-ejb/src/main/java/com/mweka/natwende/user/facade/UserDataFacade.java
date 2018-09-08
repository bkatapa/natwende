package com.mweka.natwende.user.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.exceptions.UserNotFoundException;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.operator.entity.Operator;
import com.mweka.natwende.types.OperatorName;
import com.mweka.natwende.types.RoleType;
import com.mweka.natwende.types.Status;
import com.mweka.natwende.user.entity.Role;
import com.mweka.natwende.user.entity.User;
import com.mweka.natwende.user.entity.User_;
import com.mweka.natwende.user.vo.UserVO;

@Stateless
public class UserDataFacade extends AbstractDataFacade<UserVO, User> {

    public UserDataFacade() {
        super(UserVO.class, User.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    public User updateEntity(UserVO vo) {
        User entity;
        if (vo.getId() > 0) {
            entity = findById(vo.getId());
        } else {
            entity = new User();
        }
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    public UserVO markAsInactive(UserVO vo) throws UserNotFoundException, EntityNotFoundException {
        vo.setStatus(Status.INACTIVE);
        return update(vo);
    }
    
    public void markAsInactive(Long userId) throws UserNotFoundException, EntityNotFoundException {
		User entity = findById(userId);
		entity.setStatus(Status.INACTIVE);
		update(entity);
    }
    
    public UserVO markAsActive(UserVO vo) throws UserNotFoundException, EntityNotFoundException {
        vo.setStatus(Status.ACTIVE);
        return update(vo);
    }
    
    public void markAsActive(Long userId) throws UserNotFoundException, EntityNotFoundException {
		User entity = findById(userId);
		entity.setStatus(Status.ACTIVE);
		update(entity);
    }

    @Override
    public void convertEntitytoVO(User entity, UserVO vo) {
        vo.setUsername(entity.getUsername());
        vo.setEmail(entity.getEmail());
        vo.setFirstname(entity.getFirstname());
        vo.setLastname(entity.getLastname());
        vo.setPasswd(entity.getPasswd());
        vo.setStatus(entity.getStatus());
        vo.setContactNumber(entity.getContactNumber());
        if (entity.getOperator() != null) {
            vo.setOperator(serviceLocator.getOperatorDataFacade().getCachedVO(entity.getOperator()));
        }
    }

    @Override
    public User convertVOToEntity(UserVO vo, User entity) {
        convertBaseVOToEntity(vo, entity);
        entity.setUsername(vo.getUsername());
        entity.setEmail(vo.getEmail());
        entity.setFirstname(vo.getFirstname());
        entity.setLastname(vo.getLastname());                
        entity.setPasswd(vo.getPasswd());
        entity.setStatus(vo.getStatus());
        entity.setContactNumber(vo.getContactNumber());
        if (vo.getOperator() != null) {
            entity.setOperator(serviceLocator.getOperatorDataFacade().findById(vo.getOperator().getId()));
        }
        return entity;
    }

    @Override
    public UserVO update(UserVO vo) throws EntityNotFoundException {
        User entity = updateEntity(vo);
        return getCachedVO(entity);
    }

    /**
     * Find User By Username
     *
     * @param username
     * @return
     * @throws UserNotFoundException
     *
     */
    public User findByUserName(String username) throws UserNotFoundException {
        log.debug("findByUserName ");
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> cq = getEntityManager().getCriteriaBuilder().createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root).where(cb.equal(root.get(User_.username), username));
        try {
            return createTypedQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            throw new UserNotFoundException(e.getMessage(), e);
        }
    }

    /**
     * Find User By Email
     *
     * @param email
     * @return
     * @throws UserNotFoundException
     */
    public User findUserByEmail(String email) throws UserNotFoundException {
        log.info("findByUserByEmail ");
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<User> cq = getEntityManager().getCriteriaBuilder().createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root).where(cb.equal(root.get(User_.email), email)).orderBy(cb.desc(root.get(User_.updateDate)));
	    
	    List<User> resultList = createTypedQuery(cq).getResultList();
	    if (resultList.isEmpty()){
		throw new UserNotFoundException("User not found with email address: "+email);
	    } else if (resultList.size() > 1){
		log.warn("More than 1 user found with email address: "+email);
	    }
	    return resultList.get(0);
        } catch (NoResultException nre) {
            throw new UserNotFoundException(nre);
        }
    }

    public List<User> findUsersByOperatorId(Long operatorId) {
        TypedQuery<User> query = getEntityManager().createNamedQuery("User.findByOperatorId", getEntityClass()).setParameter("operatorId", operatorId);
        return query.getResultList();
    }

    public UserVO getUserByPrincipalName(String principalName) throws UserNotFoundException {
        User user = findByUserName(principalName);
        return getCachedVO(user);
    }
    
    private List<User> findUsersByPermissions(List<RoleType> roleTypes) {
        TypedQuery<User> query = getEntityManager().createNamedQuery(User.NAMED_QUERY_FIND_USERS_BY_PERMISSIONS, getEntityClass())
        		.setParameter(User.NAMED_QUERY_PARAM_ROLE_TYPES, roleTypes);
        List<User> users = query.getResultList();
        return users;
    }
    
    public List<String> getAllUserNames() {
    	TypedQuery<String> query = getEntityManager().createNamedQuery(User.NAMED_QUERY_FIND_ALL_USERNAMES, String.class);        		
        List<String> usernames = query.getResultList();
        return usernames;
    }
    
    public List<String> getAllEmails() {
    	TypedQuery<String> query = getEntityManager().createNamedQuery(User.NAMED_QUERY_FIND_ALL_EMAILS, String.class);        		
        List<String> emails = query.getResultList();
        return emails;
    }

    public List<UserVO> getUsersByOperatorId(Long id) {
        List<User> userList = findUsersByOperatorId(id);
        return transformList(userList);
    }         

    public UserVO getUserById(int id) throws UserNotFoundException {
        User user = findById(id);
        return getCachedVO(user);
    }

    public UserVO getUserByEmail(String email) throws UserNotFoundException {
        User user = findUserByEmail(email);
        UserVO userVO = getCachedVO(user);
        return userVO;
    }

    public List<UserVO> getAllUsersList() {
        List<User> userList = findAll();
        return transformList(userList);
    }

    public UserVO getUserByUsername(String username) throws UserNotFoundException {
        if (username == null || username.equalsIgnoreCase("")) {
            throw new UserNotFoundException("Blank/Null Username");
        }
        User user = findByUserName(username);
        UserVO userVO = getCachedVO(user);
        return userVO;
    }

    public List<UserVO> getNonRegisteredBuyers(long supplierId) {        
    	TypedQuery<User> query = createNamedQuery(User.NAMED_QUERY_FIND_NON_REGISTERED_USERS, User.class);
    	query.setParameter(User.NAMED_QUERY_PARAM_SUPPLIER_ID, supplierId);	
    	return transformList(query.getResultList());
    }

    public List<UserVO> getUsersByPermissions(List<RoleType> roleTypes) {
    	return transformList(findUsersByPermissions(roleTypes));
    }
    
    public List<UserVO> getListByRoleTypeAndOperatorName(RoleType roleType, OperatorName operatorName) {
    	return transformList(findListByRoleTypeAndOperatorName(roleType, operatorName));
    }
    
    private List<User> findListByRoleTypeAndOperatorName(RoleType roleType, OperatorName operatorName) {
    	List<User> resultList = createNamedQuery(User.NAMED_QUERY_FIND_LIST_BY_ROLETYPE_AND_OPERATOR_NAME, getEntityClass())
    			.setParameter(Role.PARAM_ROLE_TYPE, roleType)
    			.setParameter(Operator.PARAM_OPERATOR_NAME, operatorName)
    			.getResultList();
    	return resultList;
    }
}

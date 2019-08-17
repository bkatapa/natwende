package com.mweka.natwende.user.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.exceptions.UserNotFoundException;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.interceptor.UserPermissionsInterceptor;
import com.mweka.natwende.operator.entity.Operator;
import com.mweka.natwende.payment.entity.Payment;
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
        User entity = vo.getId() > 0 ? findById(vo.getId()) : new User();
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
    	super.convertBaseEntityToVO(entity, vo);
        vo.setUsername(entity.getUsername());
        vo.setEmail(entity.getEmail());
        vo.setFirstname(entity.getFirstname());
        vo.setLastname(entity.getLastname());
        vo.setPasswd(entity.getPasswd());
        vo.setStatus(entity.getStatus());
        vo.setContactNumber(entity.getContactNumber());
        vo.setNrc(entity.getNrc());
        vo.setProfilePic(entity.getProfilePic());
        if (entity.getAddress() != null) {
        	vo.getAddress().setLine1(entity.getAddress().getLine1());
        	//vo.getAddress().setPremises(entity.getAddress().getPremises());
        	vo.getAddress().setStreet(entity.getAddress().getStreet());
        	vo.getAddress().setSurbab(entity.getAddress().getSurbab());
        	vo.getAddress().setTown(entity.getAddress().getTown());        	
        }
        if (entity.getOperator() != null) {
            vo.setOperator(serviceLocator.getOperatorDataFacade().getCachedVO(entity.getOperator()));
        }
    }

    @Override
    public User convertVOToEntity(UserVO vo, User entity) {
        super.convertBaseVOToEntity(vo, entity);
        entity.setUsername(vo.getUsername());
        entity.setEmail(vo.getEmail());
        entity.setFirstname(vo.getFirstname());
        entity.setLastname(vo.getLastname());                
        entity.setPasswd(vo.getPasswd());
        entity.setStatus(vo.getStatus());
        entity.setContactNumber(vo.getContactNumber());
        entity.setProfilePic(vo.getProfilePic());
        entity.setNrc(vo.getNrc());
        if (vo.getAddress() != null) {
        	entity.getAddress().setLine1(vo.getAddress().getLine1());
        	//entity.getAddress().setPremises(vo.getAddress().getPremises());
        	entity.getAddress().setStreet(vo.getAddress().getStreet());
        	entity.getAddress().setSurbab(vo.getAddress().getSurbab());
        	entity.getAddress().setTown(vo.getAddress().getTown());
        }
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
            if (resultList.isEmpty()) {
            	throw new UserNotFoundException("User not found with email address: " + email);
            } 
            if (resultList.size() > 1) {
            	log.warn("More than 1 user found with email address: " + email);
            }
            return resultList.get(0);
    	} 
        catch (NoResultException nre) {
            throw new UserNotFoundException(nre);
        }
    }

    public UserVO getUserByPrincipalName(String principalName) throws UserNotFoundException {
        User user = findByUserName(principalName);
        return getCachedVO(user);
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

    public List<UserVO> getByOperatorId(Long operatorId) {
    	List<User> resultList = createNamedQuery("User.findByOperatorId", getEntityClass())
    			.setParameter("operatorId", operatorId)
    			.getResultList();
        return transformList(resultList);
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
        if (StringUtils.isBlank(username)) {
            throw new UserNotFoundException("Blank/Null Username");
        }
        User user = findByUserName(username);
        UserVO userVO = getCachedVO(user);
        return userVO;
    }    
    
    @Interceptors(UserPermissionsInterceptor.class)
    public UserVO getByPaymentRef(String paymentRef) {
    	List<User> resultList = createNamedQuery(User.NAMED_QUERY_FIND_BY_PAYMENT_REF, getEntityClass())
    			.setParameter(Payment.PARAM_REF, paymentRef)
    			.getResultList();
    	return getVOFromList(resultList);
    }
    
    public List<UserVO> getByRoleTypeAndOperatorName(RoleType roleType, OperatorName operatorName) {
    	List<User> resultList = createNamedQuery(User.NAMED_QUERY_FIND_BY_ROLETYPE_AND_OPERATOR_NAME, getEntityClass())
    			.setParameter(Role.PARAM_ROLE_TYPE, roleType)
    			.setParameter(Operator.PARAM_OPERATOR_NAME, operatorName)
    			.getResultList();
    	return transformList(resultList);
    }
    
    public UserVO getByNrc(String nrc) {
    	List<User> resultList = createNamedQuery(User.NAMED_QUERY_FIND_BY_NRC,getEntityClass())        		
    			.setParameter(User.PARAM_NRC, nrc + "%")
    			.getResultList();
        return getVOFromList(resultList);
    }
}

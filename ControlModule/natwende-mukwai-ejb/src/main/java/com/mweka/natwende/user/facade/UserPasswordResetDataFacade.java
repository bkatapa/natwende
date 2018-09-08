package com.mweka.natwende.user.facade;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;

import com.mweka.natwende.exceptions.UserNotFoundException;
import com.mweka.natwende.exceptions.UserPasswordResetNotFoundException;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.user.vo.UserPasswordResetVO;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.ServiceLocator;
import com.mweka.natwende.util.StringEncryption;
import com.mweka.natwende.user.entity.UserPasswordReset;
import com.mweka.natwende.user.entity.UserPasswordReset_;
import java.util.UUID;

@Stateless
public class UserPasswordResetDataFacade extends AbstractDataFacade<UserPasswordResetVO, UserPasswordReset> {

    @EJB
    ServiceLocator serviceLocator;

    public UserPasswordResetDataFacade() {
        super(UserPasswordResetVO.class, UserPasswordReset.class);
    }

    private UserPasswordResetVO insertUserPasswordRest(UserPasswordResetVO userPasswordResetVO) throws UserNotFoundException {
        log.debug("insertUserPasswordRest()");
        UserPasswordReset userPasswordReset = updateEntity(userPasswordResetVO);
        return getCachedVO(userPasswordReset);
    }

    private UserPasswordResetVO generateUserPasswordResetEntry(String email, Date expiryDate) throws UserNotFoundException {
        log.debug("generateUserPasswordResetEntry()");
        UserVO userVO = serviceLocator.getUserDataFacade().getUserByEmail(email);
	UserPasswordResetVO passwordResetVO = new UserPasswordResetVO();
	passwordResetVO.setUniqueId(UUID.randomUUID().toString());
	passwordResetVO.setEmail(userVO.getEmail());
	passwordResetVO.setUserVO(userVO);
	passwordResetVO.setResetPin(StringEncryption.encryptString(email+passwordResetVO.getUniqueId()));
		
        if(expiryDate == null){
	    passwordResetVO.setExpiryDate(getNext24HourDate());
	}else{
	    passwordResetVO.setExpiryDate(expiryDate);
	}
        removeCurrentUserPasswordResetEntriesByEmail(email);
        removeAllExpiredUserPasswordResetEntries();
        return insertUserPasswordRest(passwordResetVO);
    }
    
    private UserPasswordResetVO generateUserPasswordResetEntryGivenUsernameAndExpiryDate(String username, Date expiryDate) throws UserNotFoundException {
        log.debug("generateUserPasswordResetEntryFromSuppliedUsernameAndExpiryDate()");
        UserVO userVO = serviceLocator.getUserDataFacade().getUserByUsername(username);
        UserPasswordResetVO passwordResetVO = new UserPasswordResetVO();
        passwordResetVO.setUniqueId(UUID.randomUUID().toString());
        passwordResetVO.setEmail(userVO.getEmail());
        passwordResetVO.setUserVO(userVO);
        passwordResetVO.setResetPin(StringEncryption.encryptString(username+passwordResetVO.getUniqueId()));
		
        if(expiryDate == null){
        	passwordResetVO.setExpiryDate(getNext24HourDate());
        }else{
        	passwordResetVO.setExpiryDate(expiryDate);
        }
        removeCurrentUserPasswordResetEntriesByUsername(username);        
        removeAllExpiredUserPasswordResetEntries();
        return insertUserPasswordRest(passwordResetVO);
    }
    
    public UserPasswordResetVO generateUserPasswordResetEntry(String email) throws UserNotFoundException {
        log.debug("generateUserPasswordResetEntry()");
        return generateUserPasswordResetEntry(email, null);
    }
    
    public UserPasswordResetVO generateUserPasswordResetVOFor7Days(String email) throws UserNotFoundException {
        log.debug("generateUserPasswordResetEntry()");
        return generateUserPasswordResetEntry(email, getNext7DaysDate());
    }
    
    public UserPasswordResetVO generateUserPasswordResetEntryForSuppliedUsername(String username) throws UserNotFoundException {
        log.debug("generateUserPasswordResetEntryForSuppliedUsername()");
        return generateUserPasswordResetEntryGivenUsernameAndExpiryDate(username, null);
    }
    
    public UserPasswordResetVO generateUserPasswordResetVOFor7DaysForSuppliedUsername(String username) throws UserNotFoundException {
        log.debug("generateUserPasswordResetEntryForSuppliedUsername7days()");
        return generateUserPasswordResetEntryGivenUsernameAndExpiryDate(username, getNext7DaysDate());
    }
    
    private Date getNext7DaysDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return calendar.getTime();
    }
    
    private Date getNext24HourDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        return calendar.getTime();
    }

    private void removeAllExpiredUserPasswordResetEntries() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UserPasswordReset> cq = getEntityManager().getCriteriaBuilder().createQuery(getEntityClass());
        Root<UserPasswordReset> root = cq.from(getEntityClass());
        Predicate whereClause = cb.lessThanOrEqualTo(root.get(UserPasswordReset_.expiryDate), new Date());
        cq.select(root).where(whereClause);
        List<UserPasswordReset> deleteList = getEntityManager().createQuery(cq).getResultList();
        if (!deleteList.isEmpty()) {
            for (UserPasswordReset userPasswordReset : deleteList) {
                delete(userPasswordReset);
            }
        }
    }

    public void removeCurrentUserPasswordResetEntriesByEmail(String email) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UserPasswordReset> cq = getEntityManager().getCriteriaBuilder().createQuery(getEntityClass());
        Root<UserPasswordReset> root = cq.from(getEntityClass());
        Predicate whereClause = cb.equal(root.get(UserPasswordReset_.email), email);
        cq.select(root).where(whereClause);
        List<UserPasswordReset> deleteList = getEntityManager().createQuery(cq).getResultList();
        if (!deleteList.isEmpty()) {
            for (UserPasswordReset userPasswordReset : deleteList) {
                delete(userPasswordReset);
            }
        }
    }
    
    public void removeCurrentUserPasswordResetEntriesByUsername(String username) {        
        List<UserPasswordReset> deleteList = getEntityManager().createNamedQuery(UserPasswordReset.FIND_BY_USERNAME, getEntityClass())
        		.setParameter(UserPasswordReset.PARAM_USERNAME, username)
        		.getResultList();
        if (CollectionUtils.isNotEmpty(deleteList)) {
            for (UserPasswordReset userPasswordReset : deleteList) {
                delete(userPasswordReset);
            }
        }
    }

    public UserPasswordResetVO getUserPasswordResetVOByResetPin(String resetPin) throws UserPasswordResetNotFoundException {
    	return getCachedVO(getUserPasswordResetByResetPin(resetPin));
    }
    
    public UserPasswordReset getUserPasswordResetByResetPin(String resetPin) throws UserPasswordResetNotFoundException {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UserPasswordReset> cq = getEntityManager().getCriteriaBuilder().createQuery(getEntityClass());
        Root<UserPasswordReset> root = cq.from(getEntityClass());
        Predicate whereClause = cb.equal(root.get(UserPasswordReset_.resetPin), resetPin);
        cq.select(root).where(whereClause);
        List<UserPasswordReset> resultList = getEntityManager().createQuery(cq).getResultList();
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            throw new UserPasswordResetNotFoundException("User Password Reset not found with uniqueId : " + resetPin);
        }
    }

    @Override
    protected void convertEntitytoVO(UserPasswordReset userPasswordReset, UserPasswordResetVO userPasswordResetVO) {        
        userPasswordResetVO.setEmail(userPasswordReset.getEmail());
        userPasswordResetVO.setExpiryDate(userPasswordReset.getExpiryDate());
        userPasswordResetVO.setUserVO(serviceLocator.getUserDataFacade().getCachedVO(userPasswordReset.getUser()));
	userPasswordResetVO.setResetPin(userPasswordReset.getResetPin());
    }

    @Override
    protected UserPasswordReset convertVOToEntity(UserPasswordResetVO userPasswordResetVO, UserPasswordReset userPasswordReset) {
        convertBaseVOToEntity(userPasswordResetVO, userPasswordReset);
        userPasswordReset.setEmail(userPasswordResetVO.getEmail());
        userPasswordReset.setExpiryDate(userPasswordResetVO.getExpiryDate());
        userPasswordReset.setUser(serviceLocator.getUserDataFacade().findById(userPasswordResetVO.getUserVO().getId()));
	userPasswordReset.setResetPin(userPasswordResetVO.getResetPin());
        return userPasswordReset;
    }

    @Override
    protected UserPasswordReset updateEntity(UserPasswordResetVO userPasswordResetVO) {
        UserPasswordReset userPasswordReset;
        if (userPasswordResetVO.getId() > 0) {
            userPasswordReset = findById(userPasswordResetVO.getId());
        } else {
            userPasswordReset = new UserPasswordReset();
        }
        convertVOToEntity(userPasswordResetVO, userPasswordReset);
        update(userPasswordReset);
        return userPasswordReset;
    }

    public UserPasswordResetVO getUserPasswordResetVOByEmail(String email) throws UserNotFoundException {
        return generateUserPasswordResetEntry(email);
    }
    
    public UserPasswordResetVO getUserPasswordResetVOByUsername(String username) throws UserNotFoundException {
        return generateUserPasswordResetEntryForSuppliedUsername(username);
    }
}

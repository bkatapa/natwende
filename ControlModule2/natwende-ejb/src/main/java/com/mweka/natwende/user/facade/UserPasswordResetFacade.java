package com.mweka.natwende.user.facade;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.user.vo.UserPasswordResetVO;
import com.mweka.natwende.util.ServiceLocator;

@Stateless
@LocalBean
public class UserPasswordResetFacade extends AbstractFacade<UserPasswordResetVO> {

    @EJB
    ServiceLocator serviceLocator;

    public UserPasswordResetFacade() {
        super(UserPasswordResetVO.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

//    public UserPasswordResetVO generateUserPasswordResetEntry(UserPasswordResetVO userPasswordResetVO) throws UserNotFoundException {
//        log.info("generateUserPasswordResetEntry()");
//        userPasswordResetVO = serviceLocator.getUserPasswordResetDataFacade().generateUserPasswordResetEntry(userPasswordResetVO);
//        return userPasswordResetVO;
//
//    }

//    public UserPasswordResetVO getUserPasswordResetByResetPin(String resetPin) throws UserPasswordResetNotFoundException {
//        log.debug("getUserPasswordResetByResetPin()");
//        return serviceLocator.getUserPasswordResetDataFacade().getUserPasswordResetVOByResetPin(resetPin);
//    }

}

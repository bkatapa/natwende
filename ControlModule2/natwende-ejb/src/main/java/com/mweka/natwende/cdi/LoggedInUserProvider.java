package com.mweka.natwende.cdi;

import com.mweka.natwende.exceptions.UserNotFoundException;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.ServiceLocator;
import java.security.Principal;
import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.apache.commons.logging.Log;
import org.infinispan.manager.CacheContainer;

public class LoggedInUserProvider {

    @EJB
    private ServiceLocator serviceLocator;

    @Inject
    private Principal principal;

    @Inject
    @VOCache
    transient CacheContainer cacheContainer;

    @Inject
    protected transient Log log;

    @Produces
    @LoggedInUser
    public UserVO getCurrentUser() {
        UserVO userVO;
        try {
            userVO = serviceLocator.getUserDataFacade().getUserByPrincipalName(principal.getName());
            return userVO;
        } catch (UserNotFoundException e) {
            return null;
        }

    }

    public ServiceLocator getServiceLocator() {
        return serviceLocator;
    }

    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

}

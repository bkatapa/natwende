package com.mweka.natwende.interceptor;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.commons.lang.StringUtils;

import com.mweka.natwende.types.RoleType;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.ServiceLocator;

public class UserPermissionsInterceptor {
	private static final String SOURCE_CLASS = UserPermissionsInterceptor.class.getName();
	private static final Logger LOGGER = Logger.getLogger(SOURCE_CLASS);
	
	@EJB
	private ServiceLocator serviceLocator;

	@AroundInvoke
	public Object interceptMethodCall(InvocationContext ctx) throws Exception {
		final String sourceMethod = "intercept";
		LOGGER.logp(Level.CONFIG, SOURCE_CLASS, sourceMethod, "Before EJB method invoke: {0}", ctx.getMethod());

		Object result = ctx.proceed();
		
		if (StringUtils.containsIgnoreCase(ctx.getMethod().getName(), "getByPaymentRef") && result instanceof UserVO) {
			UserVO user = (UserVO) result;
			List<RoleType> permissions = serviceLocator.getRoleDataFacade().getRoleTypesByUser(user);
			user.setPermissions(permissions);
		}

		LOGGER.logp(Level.CONFIG, SOURCE_CLASS, sourceMethod, "After EJB method invoke: {0}", ctx.getMethod());

		return result;
	}

}

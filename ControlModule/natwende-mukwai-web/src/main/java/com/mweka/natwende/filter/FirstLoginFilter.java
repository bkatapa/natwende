package com.mweka.natwende.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;

import com.mweka.natwende.helper.NatwendeUtils;
import com.mweka.natwende.types.PagePath;
import com.mweka.natwende.types.RoleType;
import com.mweka.natwende.user.vo.RoleVO;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.ServiceLocator;

//@WebFilter("*.xhtml")
public class FirstLoginFilter implements Filter {

	@Inject
	protected transient Log log;
	
	@Inject
	private NatwendeUtils utils;

	@EJB
	private ServiceLocator serviceLocator;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.trace("doFilter()");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession session = httpServletRequest.getSession();
		String ensureRanOnceFlag = (String) session.getAttribute("FilterRan");

		if (ensureRanOnceFlag == null && httpServletRequest.getUserPrincipal() != null) {
			try {
				log.trace("redirect()");
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/home.xhtml");
				session.setAttribute("FilterRan", "FilterRan");
				
				// Set permission set
				Set<String> convertedPermissions = new HashSet<String>();
				String principalName = httpServletRequest.getUserPrincipal().getName();
				UserVO userVO = serviceLocator.getUserDataFacade().getUserByPrincipalName(principalName);
				List<RoleVO> roleVOs = serviceLocator.getRoleFacade().getDatabaseRoleVOsByUserId(userVO.getId());
				List<RoleType> permissions = utils.obtainRoleEnumTypesFromRoleVOList(roleVOs);
				for (RoleType roleType : permissions) {
					convertedPermissions.add(roleType.name());
				}
				session.setAttribute("userPermissionSet", convertedPermissions);
				
				// Set browsing history stack
				Stack<String> browseHistory = new Stack<>();
				browseHistory.push(PagePath.HOME.getPath());
				session.setAttribute("browseHistory", browseHistory);
			} catch (Exception e) {
				log.debug(e);
			}
		} else {
			chain.doFilter(request, response);
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {

	}

}

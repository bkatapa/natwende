package com.mweka.natwende.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.user.vo.RoleVO;
import com.mweka.natwende.util.ServiceLocator;

@Named("RoleVOConverter")
@FacesConverter(forClass = RoleVO.class)
public class RoleVOConveter implements Converter {
	private Log log = LogFactory.getLog(this.getClass());

	@EJB
	public ServiceLocator serviceLocator;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (StringUtils.isBlank(value)) {
			return new RoleVO();
		}
		Long id = NumberUtils.toLong(value, -2L);
		try {
			return serviceLocator.getRoleDataFacade().getById(id);
		} catch (Exception ex) {
			log.debug(ex);
		}
		return new RoleVO();
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null || !(value instanceof RoleVO)) {
			return "";
		}
		if (value instanceof String && "ALL".equalsIgnoreCase((String) value)) {
			return "";
		}
		RoleVO vo = (RoleVO) value;
		return String.valueOf(vo.getId());
	}
}

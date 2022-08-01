package com.mweka.natwende.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.util.ServiceLocator;

@Named("RouteVOConverter")
@FacesConverter(forClass = RouteVO.class)
public class RouteVOConverter implements Converter {
	private Log log = LogFactory.getLog(this.getClass());

	@EJB
	public ServiceLocator serviceLocator;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (StringUtils.isBlank(value)) {
			return new RouteVO();
		}
		Long id = NumberUtils.toLong(value, -2L);
		try {
			return serviceLocator.getRouteDataFacade().getById(id);
		} catch (Exception ex) {
			log.debug(ex);
		}
		return new RouteVO();
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null || !(value instanceof RouteVO)) {
			return "";
		}
		if (value instanceof String && "ALL".equalsIgnoreCase((String) value)) {
			return "";
		}
		RouteVO vo = (RouteVO) value;
		return String.valueOf(vo.getId());
	}
}

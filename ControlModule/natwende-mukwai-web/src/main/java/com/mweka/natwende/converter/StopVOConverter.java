package com.mweka.natwende.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.route.vo.StopVO;
import com.mweka.natwende.util.ServiceLocator;

@Named("StopVOConverter")
@FacesConverter(forClass = StopVO.class)
public class StopVOConverter implements Converter {
	
	private Log log = LogFactory.getLog(this.getClass());

	 @EJB
	    public ServiceLocator serviceLocator;

	    @Override
	    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	    	if (value == null || "".equals(value.trim())) {
	    		return new StopVO();
	    	}
	    	Long id = NumberUtils.toLong(value, -2L);
	        if (id == null || id == -2L) {
	            return new StopVO();
	        }	        
	        try {
	        	return serviceLocator.getStopFacade().obtainStopById(id);
	        }
	        catch (Exception ex) {
	        	log.debug(ex);        	
	        }
	        return new StopVO();
	    }

	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object value) {
	        if (value == null || !(value instanceof StopVO)) {
	            return "";
	        }
	        if (value instanceof String && "ALL".equalsIgnoreCase((String) value)) {
	            return "";
	        }
	        StopVO vo = (StopVO) value;
	        return String.valueOf(vo.getId());
	    }
}

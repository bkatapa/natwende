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

import com.mweka.natwende.operator.vo.OperatorVO;
import com.mweka.natwende.types.OperatorName;
import com.mweka.natwende.util.ServiceLocator;

@Named("OperatorVOConverter")
@FacesConverter(forClass = OperatorVO.class)
public class OperatorVOConverter implements Converter {
	
	private Log log = LogFactory.getLog(this.getClass());

    @EJB
    public ServiceLocator serviceLocator;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	if (value == null || "".equals(value.trim())) {
    		return new OperatorVO();
    	}
    	Long id = NumberUtils.toLong(value, -2L);
        if (id == null || id == -2L) {
            return new OperatorVO();
        }
        if (id == -1L) { // Auto enroll operators if any
        	for (OperatorName name : OperatorName.values()) {
        		try {
					serviceLocator.getOperatorFacade().autoEnrollOperator(name);
				} catch (Exception e) {
					log.debug(e);
				}
        	}
        }
        try {
        	return serviceLocator.getOperatorFacade().obtainOperatorById(id);
        }
        catch (Exception ex) {
        	log.debug(ex);        	
        }
        return new OperatorVO();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || !(value instanceof OperatorVO)) {
            return "";
        }
        if (value instanceof String && "ALL".equalsIgnoreCase((String) value)) {
            return "";
        }
        OperatorVO operator = (OperatorVO) value;
        return String.valueOf(operator.getId());
    }
}

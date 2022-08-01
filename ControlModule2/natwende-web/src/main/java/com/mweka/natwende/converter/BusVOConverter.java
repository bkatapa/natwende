package com.mweka.natwende.converter;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.operator.vo.BusVO;
import com.mweka.natwende.util.ServiceLocator;

@Named("BusVOConverter")
@FacesConverter(forClass = BusVO.class)
public class BusVOConverter implements Converter {
	
	private Log log = LogFactory.getLog(this.getClass());

    @EJB
    public ServiceLocator serviceLocator;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	if (StringUtils.isEmpty(value)) {
    		return new BusVO();
    	}
    	Long id = NumberUtils.toLong(value, -2L);
        if (id == null || id == -2L) {
            return new BusVO();
        }        
        try {
        	return serviceLocator.getBusDataFacade().getById(id);
        }
        catch (Exception ex) {
        	log.debug(ex);        	
        }
        return new BusVO();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || !(value instanceof BusVO)) {
            return StringUtils.EMPTY;
        }
        if (value instanceof String && "ALL".equalsIgnoreCase((String) value)) {
            return StringUtils.EMPTY;
        }
        BusVO bus = (BusVO) value;
        return String.valueOf(bus.getId());
    }
}

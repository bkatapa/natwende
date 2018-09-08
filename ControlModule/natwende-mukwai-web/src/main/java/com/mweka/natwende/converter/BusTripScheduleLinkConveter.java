package com.mweka.natwende.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import com.mweka.natwende.helper.ApplicationBean;
import com.mweka.natwende.trip.vo.BusTripScheduleLinkVO;

@Named
@FacesConverter(forClass = BusTripScheduleLinkVO.class)
public class BusTripScheduleLinkConveter implements Converter {
	
	@Inject
	private ApplicationBean cache;

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || StringUtils.isBlank(value)) {
			return new BusTripScheduleLinkVO();
		}
		Object resultList = cache.get("BTSL");
		List<BusTripScheduleLinkVO> list = null;
		if (resultList instanceof List) {
			list = (List<BusTripScheduleLinkVO>) resultList;
			for (BusTripScheduleLinkVO result : list) {
				if (result.getId() == Integer.valueOf(value)) {
					list.add(result);
				}
			}
		}
		return list.isEmpty() ? new BusTripScheduleLinkVO() : list.get(0);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null || !(value instanceof BusTripScheduleLinkVO)) {
			return StringUtils.EMPTY;
		}
		BusTripScheduleLinkVO result = (BusTripScheduleLinkVO) value;
		return String.valueOf(result.getId());
	}

}

package com.mweka.natwende.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("CurrencyFormatter")
public class CurrencyFormatter implements Converter {	

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		throw new ConverterException("We dont do reverse conversions");
	}

	@Override
	 public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) return "";
		if (value instanceof BigDecimal) {
                    DecimalFormat df = new DecimalFormat("#,##0.00");
			return df.format(value);			
		}
		throw new ConverterException("We only know how to convert BigDecimal");
	}

}

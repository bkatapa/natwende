package com.mweka.natwende.helper;

import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class VelocityGen implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9219397492959661107L;

	public static void main(String[] args) {
		String seats = "'e__ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee','ee_ee'";
		String result = new VelocityGen().busTemplate(seats, BigDecimal.valueOf(150.00));
		System.out.println(result);
	}
	
	public String busTemplate(String seats, BigDecimal pricePerSeat) {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		velocityEngine.init();
		Template template = velocityEngine.getTemplate("templates/velocity/busTemplate.vm");
		
		if (pricePerSeat == null) {
			pricePerSeat = BigDecimal.ZERO;
		}
		
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("price", pricePerSeat);
		velocityContext.put("seats", seats);
		
		StringWriter writer = new StringWriter();
		template.merge(velocityContext, writer);
		
		String result = writer.toString();
		return result;		
	}
	
}

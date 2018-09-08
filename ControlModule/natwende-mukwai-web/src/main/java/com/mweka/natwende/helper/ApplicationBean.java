/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

@Named("ApplicationBean")
@ApplicationScoped
public class ApplicationBean {

	private Properties applicationProperties;
	private Map<String, Object> cacheMap;
	
	@PostConstruct
	private void init(){
		applicationProperties = loadManifestFile();
		cacheMap = new ConcurrentHashMap<>();
	}
	
	private Properties loadManifestFile() {
		ServletContext servletContext = (ServletContext) FacesContext
			.getCurrentInstance().getExternalContext().getContext();
		Properties prop = new Properties();

		try (InputStream resourceAsStream = servletContext.getResourceAsStream("/META-INF/MANIFEST.MF")) {
			if (resourceAsStream != null) {
				prop.load(resourceAsStream);
			}
		} catch (IOException ex) {
			Logger.getLogger(ApplicationBean.class.getName()).log(Level.SEVERE, null, ex);
		}

		return prop;
	}

	public Properties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(Properties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	
	public void add(String key, Object val) {
		cacheMap.put(key, val);
	}
	
	public Object get(String key) {
		return cacheMap.get(key);
	}
	
}

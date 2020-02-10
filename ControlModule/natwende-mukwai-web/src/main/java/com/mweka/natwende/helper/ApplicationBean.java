/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import com.mweka.natwende.resource.elasticdb.ElasticDB;
import com.mweka.natwende.route.vo.StopVO;
import com.mweka.natwende.trip.vo.ElasticTripVO;
import com.mweka.natwende.trip.vo.TripVO;
import com.mweka.natwende.util.MapObjectInstance;
import com.mweka.natwende.util.ServiceLocator;

@Named("ApplicationBean")
@ApplicationScoped
public class ApplicationBean {
	
	private static final Logger LOGGER = Logger.getLogger(ApplicationBean.class.getName()); 

	private Properties applicationProperties;
	private Map<String, Object> cacheMap;
	
	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	private ElasticDB elasticDB;
	
	@PostConstruct
	public void init(){
		applicationProperties = loadManifestFile();
		loadActiveTrips();
		cacheMap = new ConcurrentHashMap<>();
	}
	
	public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }
	
	public Properties loadManifestFile() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext == null) {
			return null;
		}
		ServletContext servletContext = (ServletContext) FacesContext
			.getCurrentInstance().getExternalContext().getContext();
		Properties prop = new Properties();

		try (InputStream resourceAsStream = servletContext.getResourceAsStream("/META-INF/MANIFEST.MF")) {
			if (resourceAsStream != null) {
				prop.load(resourceAsStream);
			}
		} catch (IOException ex) {
			LOGGER.log(Level.SEVERE, null, ex);
		}

		return prop;
	}
	
	public void loadActiveTrips() {
		try {
			List<TripVO> resultList = serviceLocator.getTripDataFacade().getActiveTrips();
			ElasticTripVO elasticData = new ElasticTripVO();
			Map<String, Object> data = null;			
			for (TripVO trip : resultList) {				
				elasticDB.deleteEntity(elasticDB.getEntity(trip.getUniqueId()));				
				elasticData = TripVO.convertToElasticData(trip, elasticData);
				List<StopVO> stationList = serviceLocator.getStopDataFacade().getByRoute(trip.getTripSchedule().getRoute());
				for (StopVO station : stationList) {
					elasticData.getStationList().add(station.getTown().getDisplay());
				}
				data = MapObjectInstance.parameters(elasticData);
				elasticDB.insertEntity(data);
			}
		}
		catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Exception", ex);
		}
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
	
	public Object purge(String key) {
		return cacheMap.remove(key);
	}
	
	public int capacity() {
		return cacheMap.size();
	}
	
	public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
		cacheMap.clear();
    }
}

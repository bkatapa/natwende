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
import com.mweka.natwende.route.vo.RouteStopLinkVO;
import com.mweka.natwende.route.vo.RouteVO;
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
		cacheMap = new ConcurrentHashMap<>();
		loadStationsAndOrderOfIndexesPerRoute();
		loadActiveTrips();		
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
			if (resultList.isEmpty()) {
				return; // Warn and return
			}
			
			@SuppressWarnings("unchecked")
			List<RouteVO> routeList = (List<RouteVO>) cacheMap.get("ROUTES_WITH_LINKED_STOPS_INCLUDING_FIRST_STOP_AND_FINAL_DESTINATION");
			
			for (TripVO trip : resultList) {				
				elasticDB.deleteEntity(elasticDB.getEntity(trip.getUniqueId()));				
				ElasticTripVO elasticData = TripVO.convertToElasticData(trip, new ElasticTripVO());
				//List<StopVO> stationList = serviceLocator.getStopDataFacade().getByRoute(trip.getTripSchedule().getRoute());
				
				RouteVO targetRoute = trip.getTripSchedule().getRoute();
				if (targetRoute.getRouteStops().isEmpty()) {
					for (RouteVO route : routeList) {
						if (route.equals(targetRoute)) {
							targetRoute.setRouteStops(route.getRouteStops());
							break;
						}
					}
				}
				if (targetRoute.getRouteStops().isEmpty()) {
					return; // Warn and return
				}
				
				for (int j = 0; j < targetRoute.getRouteStops().size(); j++) {					
					RouteStopLinkVO stationLink = targetRoute.getRouteStops().get(j);
					System.out.println(stationLink);
					elasticData.getStationList().add(stationLink.getStationIndex() + "_" + stationLink.getStop().getTown().getDisplay());
				}
				System.out.println("====================================================================");
				Map<String, Object> data = MapObjectInstance.parameters(elasticData);
				elasticDB.insertEntity(data);
			}
		}
		catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Exception", ex);
		}
	}
	
	public void loadStationsAndOrderOfIndexesPerRoute() {
		List<RouteVO> allRoutes = serviceLocator.getRouteDataFacade().getAll();		
		for (RouteVO route : allRoutes) {
			route.getRouteStops().add(new RouteStopLinkVO(-1, route, route.getStart()));
			route.getRouteStops().addAll(serviceLocator.getRouteStopLinkDataFacade().getAllByRoute(route));
			route.getRouteStops().add(new RouteStopLinkVO(route.getRouteStops().size() - 1, route, route.getStop()));			
		}
		cacheMap.put("ROUTES_WITH_LINKED_STOPS_INCLUDING_FIRST_STOP_AND_FINAL_DESTINATION", allRoutes);
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

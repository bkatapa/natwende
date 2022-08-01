package com.mweka.natwende.cache;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import com.mweka.natwende.util.ServiceLocator;

@Singleton
@LocalBean
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class UsernameEmailCache {
	@EJB
	private ServiceLocator serviceLocator;
	
	@PostConstruct
	public void init() {
		new ConcurrentHashMap<>();		
	}
	
	public void updateCacheMap() {
		
	}
}

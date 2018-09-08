package com.mweka.natwende.helper;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;

import com.mweka.natwende.util.ServiceLocator;

@Named
public class AutoCompleteHelper {

	@EJB
	private ServiceLocator serviceLocator;

	@Inject
	private transient Log log;

}

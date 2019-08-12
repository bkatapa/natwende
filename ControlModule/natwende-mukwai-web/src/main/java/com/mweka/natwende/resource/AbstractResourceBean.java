package com.mweka.natwende.resource;

import java.io.Serializable;

import javax.ejb.EJB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.util.ServiceLocator;

public abstract class AbstractResourceBean <T extends BaseVO> implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3045978056927443382L;
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	@EJB
	protected ServiceLocator serviceLocator;
	
	protected Log getLog() {
        return log;
    }	
}

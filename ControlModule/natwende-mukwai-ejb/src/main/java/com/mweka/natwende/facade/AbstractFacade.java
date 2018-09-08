package com.mweka.natwende.facade;

import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.util.ServiceLocator;

import javax.ejb.EJB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractFacade<V extends BaseVO> {

	protected Log log = LogFactory.getLog(this.getClass().getName());

    @SuppressWarnings({ "rawtypes", "unused" })
	private final Class voClass;

    @EJB
    protected ServiceLocator serviceLocator;

    @SuppressWarnings("rawtypes")
	public AbstractFacade(Class voClass) {
        this.voClass = voClass;
    }

}

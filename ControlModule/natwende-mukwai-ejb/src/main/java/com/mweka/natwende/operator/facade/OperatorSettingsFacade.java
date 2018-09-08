package com.mweka.natwende.operator.facade;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractFacade;
import com.mweka.natwende.operator.vo.OperatorSettingsVO;
import com.mweka.natwende.util.ServiceLocator;

@Stateless
@LocalBean
public class OperatorSettingsFacade extends AbstractFacade<OperatorSettingsVO> {

    @EJB
    private ServiceLocator serviceLocator;

    public OperatorSettingsFacade() {
    	super(OperatorSettingsVO.class);
    	this.log = LogFactory.getLog(this.getClass().getName());
    }

}

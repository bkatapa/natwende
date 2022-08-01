package com.mweka.natwende.operator.facade;

import javax.ejb.Stateless;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.operator.entity.OperatorSettings;
import com.mweka.natwende.operator.vo.OperatorSettingsVO;

@Stateless
public class OperatorSettingsDataFacade extends AbstractDataFacade<OperatorSettingsVO, OperatorSettings> {

    public OperatorSettingsDataFacade() {
        super(OperatorSettingsVO.class, OperatorSettings.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

	@Override
	protected void convertEntitytoVO(OperatorSettings entity, OperatorSettingsVO vo) {
		super.convertBaseEntityToVO(entity, vo);		
	}

	@Override
	protected OperatorSettings convertVOToEntity(OperatorSettingsVO vo, OperatorSettings entity) {
		super.convertBaseVOToEntity(vo, entity);
		return entity;
	}

	@Override
	public OperatorSettings updateEntity(OperatorSettingsVO vo) throws EntityNotFoundException {
		OperatorSettings entity;
        if (vo.getId() > 0) {
            entity = findById(vo.getId());
        } else {
            entity = new OperatorSettings();
        }
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
	}	
	
}

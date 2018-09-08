package com.mweka.natwende.trip.facade;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.operator.entity.Bus;
import com.mweka.natwende.trip.entity.BusTripScheduleLink;
import com.mweka.natwende.trip.entity.TripSchedule;
import com.mweka.natwende.trip.vo.BusTripScheduleLinkVO;

@Stateless
public class BusTripScheduleLinkDataFacade extends AbstractDataFacade<BusTripScheduleLinkVO, BusTripScheduleLink> {

    public BusTripScheduleLinkDataFacade() {
        super(BusTripScheduleLinkVO.class, BusTripScheduleLink.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

	@Override
	protected void convertEntitytoVO(BusTripScheduleLink entity, BusTripScheduleLinkVO vo) {
		super.convertBaseEntityToVO(entity, vo);
		if (entity.getBus() != null) {
			vo.setBus(serviceLocator.getBusDataFacade().getCachedVO(entity.getBus()));
		}
		if (entity.getDriver() != null) {
			vo.setDriver(serviceLocator.getUserDataFacade().getCachedVO(entity.getDriver()));
		}
		if (entity.getTripSchedule() != null) {
			vo.setTripSchedule(serviceLocator.getTripScheduleDataFacade().getCachedVO(entity.getTripSchedule()));
		}
		
	}

	@Override
	protected BusTripScheduleLink convertVOToEntity(BusTripScheduleLinkVO vo, BusTripScheduleLink entity) {
		super.convertBaseVOToEntity(vo, entity);
		return null;
	}

	@Override
	protected BusTripScheduleLink updateEntity(BusTripScheduleLinkVO vo) throws EntityNotFoundException {
		BusTripScheduleLink entity = vo.getId() > 0 ? findById(vo.getId()) : new BusTripScheduleLink();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
	}
	
	public BusTripScheduleLinkVO getByBusIdAndTripScheduleId(Long busId, Long tripScheduleId) {
		List<BusTripScheduleLink> resultList = createNamedQuery(BusTripScheduleLink.QUERY_FIND_BY_BUS_ID_AND_TRIPSCHEDULE_ID, getEntityClass())
				.setParameter(Bus.PARAM_BUS_ID, busId)
				.setParameter(TripSchedule.PARAM_TRIPSCHEDULE_ID, tripScheduleId)
				.getResultList();
		return getVOFromList(resultList);
	}
}

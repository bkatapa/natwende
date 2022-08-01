package com.mweka.natwende.trip.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.trip.entity.TempBooking;
import com.mweka.natwende.trip.entity.TempBooking_;
import com.mweka.natwende.trip.search.vo.TempBookingSearchVO;
import com.mweka.natwende.trip.vo.TempBookingVO;

@Stateless
public class TempBookingDataFacade extends AbstractDataFacade<TempBookingVO, TempBooking> {

    public TempBookingDataFacade() {
        super(TempBookingVO.class, TempBooking.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected TempBooking updateEntity(TempBookingVO vo) {
    	TempBooking entity = vo.getId() > 0 ? findById(vo.getId()) : new TempBooking();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(TempBooking entity, TempBookingVO vo) {
    	super.convertBaseEntityToVO(entity, vo);
        vo.setLastHeartBeat(entity.getLastHeartBeat());
        vo.setSeatNo(entity.getSeatNo());
        vo.setTripId(entity.getTripId());      
        vo.setWsSessionId(entity.getWsSessionId());
        vo.setSeatStatus(entity.getSeatStatus());
    }

    @Override
    public TempBooking convertVOToEntity(TempBookingVO vo, TempBooking entity) {
        convertBaseVOToEntity(vo, entity);
        entity.setLastHeartBeat(vo.getLastHeartBeat());
        entity.setSeatNo(vo.getSeatNo());
        entity.setTripId(vo.getTripId());
        entity.setWsSessionId(vo.getWsSessionId());
        entity.setSeatStatus(vo.getSeatStatus());
        return entity;
    }

    @Override
    public TempBookingVO update(TempBookingVO vo) {
    	TempBooking entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public TempBookingVO getByWsSession(String wsSessionId) {
    	TempBookingSearchVO searchVO = new TempBookingSearchVO();    	
    	List<TempBookingVO> resultList = findBySearchVO(searchVO);
    	return getVOFromVOList(resultList);
    }
    
    public List<TempBookingVO> findBySearchVO(TempBookingSearchVO searchVO) {		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<TempBooking> cq = cb.createQuery(getEntityClass());

		Root<TempBooking> root = cq.from(getEntityClass());
		List<Predicate> filters = new ArrayList<>();
		
		if (searchVO.hasFilters()) {			
			if (searchVO.getTripId() != null) {
				filters.add(cb.equal(root.get(TempBooking_.tripId), searchVO.getTripId()));
			}
			if (StringUtils.isNotBlank(searchVO.getWsSessionId())) {
				filters.add(cb.equal(root.get(TempBooking_.wsSessionId), searchVO.getWsSessionId()));
			}
			if (StringUtils.isNotBlank(searchVO.getSeatNo())) {
				filters.add(cb.equal(root.get(TempBooking_.seatNo), searchVO.getSeatNo()));
			}
			if (searchVO.getSeatStatus() != null) {
				filters.add(cb.equal(root.get(TempBooking_.seatStatus), searchVO.getSeatStatus()));
			}
			cq.select(root).where(filters.toArray(new Predicate[filters.size()])).orderBy(cb.asc(root.get(TempBooking_.tripId)));
		}
		else {
			cq.select(root).orderBy(cb.asc(root.get(TempBooking_.tripId)));
		}
		List<TempBooking> results = getEntityManager().createQuery(cq).getResultList();
		return transformList(results);
	}
}

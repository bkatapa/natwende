package com.mweka.natwende.operator.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.operator.entity.Bus;
import com.mweka.natwende.operator.entity.Seat;
import com.mweka.natwende.operator.entity.Seat_;
import com.mweka.natwende.operator.search.vo.SeatSearchVO;
import com.mweka.natwende.operator.vo.SeatVO;

@Stateless
public class SeatDataFacade extends AbstractDataFacade<SeatVO, Seat> {

    public SeatDataFacade() {
        super(SeatVO.class, Seat.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected Seat updateEntity(SeatVO vo) {
        Seat entity = vo.getId() > 0 ? findById(vo.getId()) : new Seat();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(Seat entity, SeatVO vo) {
    	convertBaseEntityToVO(entity, vo);
        
    	vo.setSeatNo(entity.getSeatNo());
    	vo.setCoordinates(entity.getCoordinates());
    	vo.setSeatClass(entity.getSeatClass());
    	
    	if (entity.getBus() != null) {
    		vo.setBus(serviceLocator.getBusDataFacade().getCachedVO(entity.getBus()));
    	}
    }

    @Override
    public Seat convertVOToEntity(SeatVO vo, Seat entity) {
        convertBaseVOToEntity(vo, entity);
        
        entity.setSeatNo(vo.getSeatNo());
    	entity.setCoordinates(vo.getCoordinates());
    	entity.setSeatClass(vo.getSeatClass());
    	
    	if (vo.getBus() != null) {
    		entity.setBus(serviceLocator.getBusDataFacade().findById(vo.getBus().getId()));
    	}
        return entity;
    }

    @Override
    public SeatVO update(SeatVO vo) {
        Seat entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public List<SeatVO> getAllByBusId(Long busId) {
		return transformList(findAllByBusId(busId));
	}
	
	private List<Seat> findAllByBusId(Long busId) {
		final TypedQuery<Seat> query = createNamedQuery(Seat.QUERY_FIND_ALL_BY_BUS_ID, getEntityClass())
				.setParameter(Bus.PARAM_BUS_ID, busId);
		return query.getResultList();
	}
	
	public SeatVO getBySeatNoCoordinatesAndBusId(String seatNo, String coordinates, Long busId) {
		Seat entity = findBySeatNoCoordinatesAndBusId(seatNo, coordinates, busId);
		return entity == null ? null : convertEntityToVO(entity);
	}
	
	private Seat findBySeatNoCoordinatesAndBusId(String seatNo, String coordinates, Long busId) {
		final TypedQuery<Seat> query = createNamedQuery(Seat.QUERY_FIND_BY_SEATNO_COORDINATES_AND_BUS_ID, getEntityClass())
				.setParameter(Seat.PARAM_SEAT_NO, seatNo)
				.setParameter(Seat.PARAM_CO_ORDINATES, coordinates)
				.setParameter(Bus.PARAM_BUS_ID, busId);
		List<Seat> resultList = query.getResultList();
		return resultList.isEmpty() ? null : resultList.get(0);
	}

	public List<Seat> findBySearchVO(SeatSearchVO searchVO) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Seat> cq = getEntityManager().getCriteriaBuilder().createQuery(getEntityClass());
		Root<Seat> root = cq.from(getEntityClass());

		List<Predicate> filters = new ArrayList<>();
		
		if (searchVO.hasFilters()) {
			if (searchVO.getSeatNo() != null && !searchVO.getSeatNo().trim().isEmpty()) {
			    filters.add(cb.like(root.get(Seat_.seatNo), "%" + searchVO.getSeatNo() + "%"));
			}
			if (searchVO.getCoordinates() != null && !searchVO.getCoordinates().trim().isEmpty()) {
			    filters.add(cb.like(root.get(Seat_.coordinates), "%" + searchVO.getCoordinates() + "%"));
			}
			if (searchVO.getSeatClass() != null) {
			    filters.add(cb.equal(root.get(Seat_.seatClass), searchVO.getSeatClass()));
			}
			if (searchVO.getBusId() != null) {
			    filters.add(cb.equal(root.get(Seat_.bus), searchVO.getBusId()));
			}	
			cq.select(root).where(filters.toArray(new Predicate[filters.size()])).orderBy(cb.asc(root.get(Seat_.seatNo)));			
	    }
		else {
			cq.select(root).orderBy(cb.asc(root.get(Seat_.seatNo))); // Select all seats.
		}
		return getEntityManager().createQuery(cq).getResultList();
	}
}

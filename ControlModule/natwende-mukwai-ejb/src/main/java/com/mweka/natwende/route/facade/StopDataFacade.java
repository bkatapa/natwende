package com.mweka.natwende.route.facade;

import java.util.List;

import javax.ejb.Stateless;
//import javax.persistence.TypedQuery;

import org.apache.commons.logging.LogFactory;

import com.mweka.natwende.facade.AbstractDataFacade;
import com.mweka.natwende.route.entity.Route;
import com.mweka.natwende.route.entity.Stop;
import com.mweka.natwende.route.vo.RouteVO;
import com.mweka.natwende.route.vo.StopVO;
import com.mweka.natwende.types.Province;
import com.mweka.natwende.types.Status;
import com.mweka.natwende.types.Town;

@Stateless
public class StopDataFacade extends AbstractDataFacade<StopVO, Stop> {

    public StopDataFacade() {
        super(StopVO.class, Stop.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected Stop updateEntity(StopVO vo) {
        Stop entity = vo.getId() > 0 ? findById(vo.getId()) : new Stop();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(Stop entity, StopVO vo) {
    	convertBaseEntityToVO(entity, vo);
    	vo.setName(entity.getName());
    	vo.setTown(entity.getTown());
    	vo.setProvince(entity.getProvince());        
    }

    @Override
    public Stop convertVOToEntity(StopVO vo, Stop entity) {
        convertBaseVOToEntity(vo, entity);
        entity.setName(vo.getName());
    	entity.setTown(vo.getTown());
    	entity.setProvince(vo.getProvince());
        return entity;
    }

    @Override
    public StopVO update(StopVO vo) {
        Stop entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    /*========================================================================================================*/
    
    public List<StopVO> getAllByStatus(Status status) {
		return transformList(findAllByStatus(status));
	}
	
	private List<Stop> findAllByStatus(Status status) {
		return createNamedQuery(Stop.QUERY_FIND_ALL_BY_STATUS, getEntityClass())
				.setParameter(Stop.PARAM_STATUS, status)
				.getResultList();
	}
	
	/*========================================================================================================*/
	
	public StopVO getByName(String stopName) {
		List<Stop> resultList = createNamedQuery(Stop.QUERY_FIND_STOP_BY_NAME, getEntityClass())
				.setParameter(Stop.PARAM_STOP_NAME, stopName)
				.getResultList();
		return resultList.isEmpty() ? null : convertEntityToVO(resultList.get(0));
	}
	
	/*========================================================================================================*/
	
	public StopVO getByNameTownAndProvince(String stopName, Town town, Province province) {
		List<Stop> resultList = createNamedQuery(Stop.QUERY_FIND_BY_NAME_TOWN_AND_PROVINCE, getEntityClass())
				.setParameter(Stop.PARAM_STOP_NAME, stopName)
				.setParameter(Stop.PARAM_TOWN, town)
				.setParameter(Stop.PARAM_PROVINE, province)
				.getResultList();
		return resultList.isEmpty() ? null : convertEntityToVO(resultList.get(0));
	}
	
	/*========================================================================================================*/
	
	public List<StopVO> getByTown(Town town) {
		List<Stop> resultList = createNamedQuery(Stop.QUERY_FIND_STOP_BY_TOWN, getEntityClass())
				.setParameter(Stop.PARAM_TOWN, town)
				.getResultList();
		return transformList(resultList);
	}
	
/*========================================================================================================*/
	
	public List<StopVO> getByRoute(RouteVO route) {
		List<Stop> resultList = createNamedQuery(Stop.QUERY_FIND_BY_ROUTE, getEntityClass())
				.setParameter(Route.PARAM_ROUTE_ID, route.getId())
				.getResultList();
		return transformList(resultList);
	}

}

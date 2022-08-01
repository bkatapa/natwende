package com.mweka.natwende.log.facade;

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
import com.mweka.natwende.log.entity.ActivityLog;
import com.mweka.natwende.log.entity.ActivityLog_;
import com.mweka.natwende.log.search.vo.ActivityLogSearchVO;
import com.mweka.natwende.log.vo.ActivityLogVO;
import com.mweka.natwende.types.Status;

@Stateless
public class ActivityLogDataFacade extends AbstractDataFacade<ActivityLogVO, ActivityLog> {

    public ActivityLogDataFacade() {
        super(ActivityLogVO.class, ActivityLog.class);
        this.log = LogFactory.getLog(this.getClass().getName());
    }

    @Override
    protected ActivityLog updateEntity(ActivityLogVO vo) {
    	ActivityLog entity = vo.getId() > 0 ? findById(vo.getId()) : new ActivityLog();
        convertVOToEntity(vo, entity);
        update(entity);
        return entity;
    }

    @Override
    public void convertEntitytoVO(ActivityLog entity, ActivityLogVO vo) {
    	super.convertBaseEntityToVO(entity, vo);        
        vo.setUsername(entity.getUsername());
        vo.setUserSessionId(entity.getUserSessionId());
        vo.setData(entity.getData());
        vo.setActivityType(entity.getActivityType());
    }

    @Override
    public ActivityLog convertVOToEntity(ActivityLogVO vo, ActivityLog entity) {
        convertBaseVOToEntity(vo, entity);        
        entity.setUsername(vo.getUsername());
        entity.setUserSessionId(vo.getUserSessionId());
        entity.setData(vo.getData());
        entity.setActivityType(vo.getActivityType());
        return entity;
    }

    @Override
    public ActivityLogVO update(ActivityLogVO vo) {
    	ActivityLog entity = updateEntity(vo);
        return getCachedVO(entity);
    }
    
    public List<ActivityLogVO> getListByStatus(Status status) {
    	List<ActivityLog> resultList = createNamedQuery(ActivityLog.QUERY_FIND_BY_STATUS, getEntityClass())
    			.setParameter(ActivityLog.PARAM_STATUS, status)
    			.getResultList();
    	return transformList(resultList);
    }
    
    public ActivityLogVO getByUserSession(String userSessionId) {
    	ActivityLogSearchVO searchVO = new ActivityLogSearchVO();
    	searchVO.setUserSessionId(userSessionId);
    	List<ActivityLogVO> resultList = findBySearchVO(searchVO);
    	return resultList.isEmpty() ? null : resultList.get(0);
    }
    
    public List<ActivityLogVO> findBySearchVO(ActivityLogSearchVO searchVO) {		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<ActivityLog> cq = cb.createQuery(getEntityClass());

		Root<ActivityLog> root = cq.from(getEntityClass());
		List<Predicate> filters = new ArrayList<>();
		
		if (searchVO.hasFilters()) {
			if (StringUtils.isNotBlank(searchVO.getUserSessionId())) {
				filters.add(cb.equal(root.get(ActivityLog_.userSessionId), searchVO.getUserSessionId()));
			}
			if (StringUtils.isNotBlank(searchVO.getUsername())) {
				filters.add(cb.equal(root.get(ActivityLog_.username), searchVO.getUsername()));
			}
			if (searchVO.getActivityType() != null) {
				filters.add(cb.equal(root.get(ActivityLog_.activityType), searchVO.getActivityType()));
			}
			cq.select(root).where(filters.toArray(new Predicate[filters.size()])).orderBy(cb.asc(root.get(ActivityLog_.username)));
		}
		else {
			cq.select(root).orderBy(cb.asc(root.get(ActivityLog_.username)));
		}
		List<ActivityLog> results = getEntityManager().createQuery(cq).getResultList();
		return transformList(results);
	}
}

package com.mweka.natwende.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.infinispan.manager.CacheContainer;

import com.mweka.natwende.base.BaseEntity;
import com.mweka.natwende.base.vo.BaseVO;
import com.mweka.natwende.cdi.VOCache;
import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.util.ServiceLocator;

public abstract class AbstractDataFacade<V extends BaseVO, E extends BaseEntity> {

    public static final String ORDER_BY_ASC = "asc";
    public static final String ORDER_BY_DESC = "desc";

    @PersistenceContext(unitName = "NatwendePU")
    private EntityManager em;

    @Inject
    @VOCache
    transient CacheContainer cacheContainer;

    @EJB
    protected ServiceLocator serviceLocator;

    protected Log log = LogFactory.getLog(this.getClass().getName());

    private ConcurrentMap<Object, V> cache;
    private final Class<V> voClass;
    private Class<E> entityClass;

    public AbstractDataFacade(Class<V> voClass, Class<E> entityClass) {
        this.entityClass = entityClass;
        this.voClass = voClass;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public Query createQuery(String hql) {
        log.trace("createQuery");
        Query q = getEntityManager().createQuery(hql);
        q.setHint("org.hibernate.cacheable", true);
        q.setMaxResults(1000);
        return q;
    }

    public TypedQuery<E> createNamedQuery(String hql, Class<E> c) {
        log.trace("createNamedQuery");
        TypedQuery<E> q = getEntityManager().createNamedQuery(hql, c);
        q.setHint("org.hibernate.cacheable", true);
        q.setMaxResults(1000);
        return q;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public TypedQuery createNamedQuerywithNoClass(String hql, Class c) {
        log.trace("createNamedQuerywithNoClass");
        TypedQuery q = getEntityManager().createNamedQuery(hql, c);
        q.setHint("org.hibernate.cacheable", true);
        q.setMaxResults(1000);
        return q;
    }

    public TypedQuery<E> createTypedQuery(CriteriaQuery<E> cq) {
        log.trace("createTypedQuery");
        TypedQuery<E> q = getEntityManager().createQuery(cq);
        q.setHint("org.hibernate.cacheable", true);
        q.setMaxResults(1000);
        return q;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public TypedQuery createTypedQuerywithNoClass(CriteriaQuery cq) {
        log.trace("createTypedQuerywithNoClass");
        TypedQuery q = getEntityManager().createQuery(cq);
        q.setHint("org.hibernate.cacheable", true);
        q.setMaxResults(1000);
        return q;
    }

    public Class<E> getEntityClass() {
        return entityClass;
    }

    @PostConstruct
    public void initCache() {
        cache = cacheContainer.getCache(voClass.getName());
    }

    protected void putInCache(V vo) {
        log.trace("CacheUpdate: " + vo.getClass().getSimpleName() + " id=" + vo.getId());
        cache.put(vo.getId(), vo);
    }

    protected void putInCache(Object id, V vo) {
        log.trace("CacheUpdate: " + vo.getClass().getSimpleName() + " id=" + id);
        cache.put(id, vo);
    }

    protected V getFromCache(Object id) {
        return (V) cache.get(id);
    }

    public V getCachedVO(E entity) {
        V vo = getFromCache(entity.getId());
        if (vo == null) {
            vo = convertEntityToVO(entity);
            putInCache(vo);
        } else {
            log.trace("CacheHit: " + vo.getClass().getSimpleName() + " id=" + vo.getId());
            convertBaseEntityToVO(entity, vo);
            convertEntitytoVO(entity, vo);
        }
        return vo;
    }

    public void update(E entity) {
        getEntityManager().persist(entity);
    }

    public void delete(E entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public E findById(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
    public V getById(Long id) {
		E entity = findById(id);
		return entity == null ? null : convertEntityToVO(entity);
	}

    public void deleteById(long id) throws EntityNotFoundException {
        E entity = findById(id);
        delete(entity);
    }

    protected abstract void convertEntitytoVO(E entity, V vo);

    protected abstract E convertVOToEntity(V vo, E entity);

    protected abstract E updateEntity(V vo) throws EntityNotFoundException;

    public V convertEntityToVO(E entity) throws RuntimeException {
        try {
            V vo = (V) voClass.newInstance();
            convertBaseEntityToVO(entity, vo);
            convertEntitytoVO(entity, vo);
            return vo;
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected V convertEntityToVOWithCacheUpdate(E entity) throws RuntimeException {
        V vo = convertEntityToVO(entity);
        putInCache(vo);
        return vo;
    }

    protected static void convertBaseEntityToVO(BaseEntity entity, BaseVO vo) {
        vo.setId(entity.getId());
        vo.setInsertDate(entity.getInsertDate());
        vo.setUpdateDate(entity.getUpdateDate());
        vo.setUniqueId(entity.getUniqueId());
        vo.setStatus(entity.getStatus());
        vo.setVersion(entity.getVersion());
    }

    protected static void convertBaseVOToEntity(BaseVO vo, BaseEntity entity) {
        if (vo.getId() > 0) {
            entity.setId(vo.getId());
        }
        entity.setInsertDate(vo.getInsertDate());
        entity.setUpdateDate(vo.getUpdateDate());
        entity.setUniqueId(vo.getUniqueId());
        entity.setStatus(vo.getStatus());
        entity.setVersion(vo.getVersion());
    }

    public V update(V vo) throws EntityNotFoundException {
        E entity = updateEntity(vo);
        vo = convertEntityToVOWithCacheUpdate(entity);
        return vo;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected CriteriaQuery<E> findAllQuery() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return cq;
    }

    public List<E> findAll() {
        CriteriaQuery<E> cq = findAllQuery();
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<E> findAll(Order... orders) {
        CriteriaQuery<E> cq = findAllQuery();
        cq.orderBy(orders);
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<E> findAll(HashMap<String, String> orderMap) {
        List<Order> orders = new ArrayList<>(orderMap.size());
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<E> cq = findAllQuery();
        Root<E> from = cq.from(entityClass);
        for (Map.Entry<String, String> entry : orderMap.entrySet()) {
            String columnName = entry.getKey();
            String direction = entry.getValue();
            switch (direction) {
                case ORDER_BY_DESC:
                    orders.add(cb.desc(from.get(columnName)));
                    break;
                case ORDER_BY_ASC:
                default:
                    orders.add(cb.asc(from.get(columnName)));
            }
        }

        cq.orderBy(orders);
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    /**
     * 
     * Transform an Entity list into a VO list using cached VOs
     * @param list
     * @return The List<V>
     */
    public List<V> transformList(List<E> list) {
    	List<V> result = new ArrayList<>(list.size());
        if (list != null) {
            for (E entity : list) {
                    result.add(getCachedVO(entity));
            }
        }
    	return result;
    }
    
    protected V getVOFromList(List<E> list) {
    	return list.isEmpty() ? null : getCachedVO(list.get(0));
    }
    
    protected V getVOFromVOList(List<V> list) {
    	return list.isEmpty() ? null : list.get(0);
    }
    
}

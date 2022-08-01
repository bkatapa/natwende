package com.mweka.natwende.cache;

import com.mweka.natwende.cdi.VOCache;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.infinispan.manager.CacheContainer;

@Singleton
@Startup
public class CacheProvider {

    protected Log log = LogFactory.getLog(this.getClass());

    @Resource(lookup = "java:jboss/infinispan/container/voCache")
    private org.infinispan.manager.CacheContainer voCacheManager;

    @PostConstruct
    public void contextInitialized() {

    }

    @PreDestroy
    public void contextDestroyed() {
        voCacheManager.stop();
    }

    @Produces
    @VOCache
    public CacheContainer produceCacheManager() {
        return voCacheManager;
    }

}

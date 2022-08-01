package com.mweka.natwende.cdi;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

  
public class LoggerProducer {  
   /** 
    * @param injectionPoint 
    * @return logger 
    */  
    @Produces
    //@AppLogger
    public Log produceLogger(InjectionPoint injectionPoint) {  
        return LogFactory.getLog(injectionPoint.getMember().getDeclaringClass().getName());  
    }  
} 
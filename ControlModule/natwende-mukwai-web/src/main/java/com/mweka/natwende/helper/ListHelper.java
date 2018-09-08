package com.mweka.natwende.helper;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.logging.Log;

import com.mweka.natwende.types.ImageStatus;
import com.mweka.natwende.types.NotificationStatus;
import com.mweka.natwende.types.Status;
import com.mweka.natwende.util.ServiceLocator;

@Named
public class ListHelper {

    private HashMap<String, Status> statusMap;
    private HashMap<String, Status> articleStatusMap;
    private HashMap<String, Status> userStatusMap;
    private Map<String, NotificationStatus> notificationStatusMap;
    private Map<String, ImageStatus> imageStatusMap;

    @EJB
    private ServiceLocator serviceLocator;

    @Inject
    private transient Log log;

    public HashMap<String, Status> getStatusMap() {
        statusMap = new HashMap<>();
        for (Status status : Status.values()) {
            statusMap.put(status.getDisplay(), status);
        }
        return statusMap;
    }

    public void setStatusMap(HashMap<String, Status> statusMap) {
        this.statusMap = statusMap;
    }

    public Map<String, NotificationStatus> getNotificationStatusMap() {
        notificationStatusMap = new LinkedHashMap<>();
        for (NotificationStatus status : NotificationStatus.values()) {
            notificationStatusMap.put(status.getDisplay(), status);
        }
        return notificationStatusMap;
    }

    public Map<String, ImageStatus> getImageStatusMap() {
        imageStatusMap = new LinkedHashMap<>();
        for (ImageStatus status : ImageStatus.values()) {
            imageStatusMap.put(status.getDisplay(), status);
        }
        return imageStatusMap;
    }

    public HashMap<String, Status> getUserStatusMap() {
	userStatusMap = new HashMap<>();
        for (Status status : Status.values()) {
	    if(status.equals(Status.DELETED)){
		continue;
	    }
            userStatusMap.put(status.getDisplay(), status);
        }
	return userStatusMap;
    }
    
    public HashMap<String, Status> getArticleStatusMap() {
    	articleStatusMap = new HashMap<>();
    	for (Map.Entry<String, Status> entrySet : getStatusMap().entrySet()) {
    		if (EnumSet.of(Status.ACTIVE, Status.INACTIVE).contains(entrySet.getValue())) {
    			articleStatusMap.put(entrySet.getKey(), entrySet.getValue());
    		}
    	}
    	return articleStatusMap;
    }
    
    public HashMap<String, Status> getValidationProviderStatusMap() {    	
    	return getArticleStatusMap();
    }

    public void setUserStatusMap(HashMap<String, Status> userStatusMap) {
	this.userStatusMap = userStatusMap;
    }

}

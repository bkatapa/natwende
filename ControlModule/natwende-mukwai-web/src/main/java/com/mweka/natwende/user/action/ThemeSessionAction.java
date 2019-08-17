package com.mweka.natwende.user.action;

import java.io.Serializable;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@SessionScoped
public class ThemeSessionAction implements Serializable {

    private static final long serialVersionUID = 916055190609044881L;
    
    /**
     * Default constructor.
     */
    public ThemeSessionAction() {
    }
    
    public String getLogoFilename(){
	    Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap();
	    return "logo-"+(parameterMap.get("OPERATOR-NAME") == null? "natwende" : parameterMap.get("OPERATOR-NAME"))+".png";
    }
    
    public String getOperatorTheme(){
	    Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap();
	    switch (parameterMap.get("OPERATOR-NAME") == null? "natwende" : parameterMap.get("OPERATOR-NAME")){
		    case "euro":
			return "pepper-grinder";
		    default:
			return /*"paradise-blue";*//*"omega";*//*"home";*/"bootstrap";   
	    }
    }
    
    public String getOperatorName(){
	    Map<String, String> parameterMap = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap();
	    return (parameterMap.get("OPERATOR-NAME") == null? "natwende" : parameterMap.get("OPERATOR-NAME"));
    }
    
    public Map<String, String> getOperatorSettingsMap(){
	    return null;
    }
}

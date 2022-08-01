package com.mweka.natwende.menu.action;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import com.mweka.natwende.types.Town;

@Named("MenuAction")
@SessionScoped
public class MenuAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5245675908645653237L;
	
	private Integer selectedTabIndex = null;	

	public Integer getSelectedTabIndex() {
		return selectedTabIndex;
	}

	public void setSelectedTabIndex(Integer selectedTabIndex) {
		this.selectedTabIndex = selectedTabIndex;
	}
	
	public void onTabChange(TabChangeEvent event) {   
		TabView tabViewPanel = (TabView) event.getComponent();
        selectedTabIndex = tabViewPanel.getChildren().indexOf(event.getTab());
        LOGGER.log(Level.INFO, "Active index is: {0}", selectedTabIndex);
    }
	
	public String[] getSlideList() {
		return new String[] {"01.jpg", "02.jpg", "03.jpg", "04.png", "05.jpeg", "06.jpg", "07.jpg", "08.jpg", "09.jpg", "10.jpg", "11.jpg"};
	}
	
	public Town[] getTowns() {
		return Town.values();
	}
	
	public String homePage() {
		return "/home?faces-redirect=true";
	}

	private static transient final Logger LOGGER = Logger.getLogger(MenuAction.class.getName()); 
}

package com.mweka.natwende.menu.action;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

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

	private static transient final Logger LOGGER = Logger.getLogger(MenuAction.class.getName()); 
}

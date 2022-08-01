package com.mweka.natwende.admin.action;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import com.mweka.natwende.menu.action.MenuAction;
import com.mweka.natwende.trip.action.BookingAction;

@Named("AdminAction")
@SessionScoped
public class AdminAction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4739647375096177078L;
	
	private Integer selectedTabIndex = null;
	
	@Inject
	private BookingAction bookingAction;

	public Integer getSelectedTabIndex() {
		return selectedTabIndex;
	}

	public void setSelectedTabIndex(Integer selectedTabIndex) {
		this.selectedTabIndex = selectedTabIndex;
	}
	
	public void onTabChange(TabChangeEvent event) {		
		TabView tabViewPanel = (TabView) event.getComponent();
        selectedTabIndex = tabViewPanel.getChildren().indexOf(event.getTab());
        String tabTitle = event.getTab().getTitle();
        
        LOGGER.log(Level.CONFIG, "Active index is: {0} [{1}]", new Object[] {tabTitle, selectedTabIndex});
        
        switch (tabTitle) {
        	case "Bookings" : bookingAction.populateEntityList();
        	break;
        }
    }
	
	public void onTabClose(TabCloseEvent event) {
		LOGGER.log(Level.CONFIG, "Index {0} [{1}] was closed.", new Object[] {event.getTab().getTitle(), selectedTabIndex});
    }

	private static transient final Logger LOGGER = Logger.getLogger(MenuAction.class.getName());

}

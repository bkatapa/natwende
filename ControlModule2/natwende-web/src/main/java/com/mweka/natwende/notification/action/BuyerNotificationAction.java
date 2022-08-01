package com.mweka.natwende.notification.action;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mweka.natwende.action.AbstractActionBean;
import com.mweka.natwende.cdi.LoggedInUser;
import com.mweka.natwende.notification.vo.NotificationSearchVO;
import com.mweka.natwende.notification.vo.NotificationVO;
import com.mweka.natwende.types.NotificationStatus;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.ServiceLocator;

@Named("BuyerNotificationAction")
@SessionScoped
public class BuyerNotificationAction extends AbstractActionBean<NotificationVO> {	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ServiceLocator serviceLocator;
	
	@Inject
	@LoggedInUser
	private UserVO userVO;
	
	private NotificationSearchVO notificationSearchVO;
	
	@Override
	public void init() {		
		notificationSearchVO = new NotificationSearchVO();
		super.init();
	}

	@Override
	protected List<NotificationVO> getListFromFacade() {
		notificationSearchVO.setSenderCompanyId(null);
		//notificationSearchVO.setReceivingCompanyId(userVO.getParkingSiteVO().getId());
		notificationSearchVO.setNotificationStatus(NotificationStatus.SUBMITTED);		
		return null; //serviceLocator.getNotificationDataFacade().getBySearchVO(notificationSearchVO); 
	}

	@Override
	protected NotificationVO facadeUpdate(NotificationVO dataItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void facadeDelete(NotificationVO dataItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getViewPage() {		
		return null;
	}

	@Override
	protected String getListPage() {
		return "NotificationList";
	}
	
	public void search() {
	}
	
	public void reset() {
		notificationSearchVO = new NotificationSearchVO(); 
	}
	
	public NotificationSearchVO getNotificationSearchVO() {
		return notificationSearchVO;
	}

}

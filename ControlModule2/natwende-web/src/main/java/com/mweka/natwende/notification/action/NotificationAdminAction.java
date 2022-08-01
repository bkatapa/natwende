package com.mweka.natwende.notification.action;

import java.util.Date;
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

@Named("NotificationAdminAction")
@SessionScoped
public class NotificationAdminAction extends AbstractActionBean<NotificationVO> {

    private static final long serialVersionUID = 1L;

    @EJB
    private ServiceLocator serviceLocator;

    @Inject
    @LoggedInUser
    private UserVO userVO;

    private NotificationSearchVO notificationSearchVO = new NotificationSearchVO();

    public NotificationAdminAction() {
        super(NotificationVO.class);
    }

    @Override
    protected List<NotificationVO> getListFromFacade() {
        //notificationSearchVO.setSenderCompanyId(userVO.getParkingSiteVO().getId());
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
        return "NotificationAdminView";
    }

    @Override
    protected String getListPage() {
        return "NotificationAdminList";
    }

    public void search() {
    }

    public void reset() {
        notificationSearchVO = new NotificationSearchVO();
    }

    public NotificationSearchVO getNotificationSearchVO() {
        return notificationSearchVO;
    }

    public boolean isEditMode() {
        return getSelectedEntity() != null && (getSelectedEntity().getNotitifcationStatus() != NotificationStatus.SUBMITTED);
    }

    public String save(NotificationVO notificationVO) {
        notificationVO.setNotitifcationStatus(NotificationStatus.SAVED);
        //notificationVO.setSenderVO(userVO.getOperator());
        try {
            //notificationVO = serviceLocator.getNotificationDataFacade().update(notificationVO);
        } catch (Exception e) {
            addFacesMessageError("Could not save the alert", e.getMessage());
            return "";
        }
        return getListPage();
    }

    public String submit(NotificationVO notificationVO) {
        notificationVO.setNotitifcationStatus(NotificationStatus.SUBMITTED);
        notificationVO.setSubmitDate(new Date());
        //notificationVO.setSenderVO(userVO.getParkingSiteVO());
        try {
            //notificationVO = serviceLocator.getNotificationDataFacade().update(notificationVO);
        } catch (Exception e) {
            addFacesMessageError("Could not submit the alert", e.getMessage());
            return "";
        }
        return getListPage();
    }

    public String delete() {
        try {
            //serviceLocator.getNotificationDataFacade().deleteById(getSelectedEntity().getId());
        } catch (Exception e) {
            addFacesMessageError("Could not delete the alert", e.getMessage());
            return "";
        }
        return getListPage();
    }

}

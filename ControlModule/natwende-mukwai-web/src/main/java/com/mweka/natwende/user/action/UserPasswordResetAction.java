package com.mweka.natwende.user.action;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;

import com.mweka.natwende.action.AbstractActionBean;
import com.mweka.natwende.exceptions.UserNotFoundException;
import com.mweka.natwende.exceptions.UserPasswordResetNotFoundException;
import com.mweka.natwende.user.vo.UserPasswordResetVO;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.NullHelper;
import com.mweka.natwende.util.ServiceLocator;

import java.util.logging.Level;
import java.util.logging.Logger;

@Named("UserPasswordResetAction")
@SessionScoped
public class UserPasswordResetAction extends AbstractActionBean<UserPasswordResetVO> {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private ServiceLocator serviceLocator;
    
    @Inject
    private ThemeSessionAction themeSessionAction;

    //@NotNull(message = "Email Cannot be empty")
    @Size(min = 1, max = 50, message = "Email required and limited to 50 characters long")
    private String email;
    
    @NotNull(message = "Username Cannot be empty")
    @Size(min = 1, max = 50, message = "Username required and limited to 50 characters long")
    private String username;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 1, max = 50, message = "Password required and limited to 50 characters long")
    private String newPassword;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 1, max = 50, message = "Password required and limited to 50 characters long")
    private String confirmPassword;

    String resetPin;
    
    @Inject
    Log log;
    
    private Boolean userPasswordResetExpired;
    //private static final String PIN_RESET_COMPLETE = "COMPLETE";
    private String previousResetPin;

    @Override
    protected List<UserPasswordResetVO> getListFromFacade() {
        return null;
    }

    @Override
    protected UserPasswordResetVO facadeUpdate(UserPasswordResetVO dataItem) {
        return null;
    }

    @Override
    protected void facadeDelete(UserPasswordResetVO dataItem) {
    }

    /**
     * Sends Password Reset Notification
     *
     * @return
     */
    public String sendPasswordResetNotification() {
    	email = StringUtils.trimToEmpty(email);

        if (!email.isEmpty()) {
            try {
		UserPasswordResetVO userPasswordResetVO = serviceLocator.getUserPasswordResetDataFacade().getUserPasswordResetVOByEmail(email);
                serviceLocator.getMailerFacade().sendPasswordResetEmail(userPasswordResetVO,  themeSessionAction.getOperatorSettingsMap());                
            } catch (UserNotFoundException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
                return "";
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Failed to create a password reset: " + e.getMessage()));
                return "";
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Password Reset Message Sent"));
        }
        return "";
    }
    
    public String sendPasswordResetNotificationForSuppliedUsername() {
    	username = StringUtils.trimToEmpty(username);

        if (StringUtils.isNotBlank(username)) {
            try {
            	UserPasswordResetVO userPasswordResetVO = serviceLocator.getUserPasswordResetDataFacade().getUserPasswordResetVOByUsername(username);
                serviceLocator.getMailerFacade().sendPasswordResetEmail(userPasswordResetVO, themeSessionAction.getOperatorSettingsMap());                
            } catch (UserNotFoundException e) {
                addFacesMessageError("Password reset error", e.getMessage());
                return "";
            } catch (Exception e) {                
                addFacesMessageError("Password reset error", "Failed to create a password reset: " + e.getMessage());
                return "";
            }
            addFacesMessageInfo("Password Reset Message Sent");
        }
        return "";
    }
    
    public Boolean getUserPasswordResetExpired(){
	if(getResetPin() == null){
	    userPasswordResetExpired = true;
	}
	if(userPasswordResetExpired == null || !userPasswordResetExpired){
	    try {
		UserPasswordResetVO userPasswordResetVO = serviceLocator.getUserPasswordResetDataFacade().getUserPasswordResetVOByResetPin(getResetPin());
		if(userPasswordResetVO.getExpiryDate().getTime() < new Date().getTime()){
		    userPasswordResetExpired = true;
		} else{
		    userPasswordResetExpired = false;
		}
	    } catch (UserPasswordResetNotFoundException ex) {
		Logger.getLogger(UserPasswordResetAction.class.getName()).log(Level.SEVERE, "userPasswordReset record does not exist.", ex);
		userPasswordResetExpired = true;
	    }
	}
	
	return userPasswordResetExpired;
    }
    

    /**
     * Reset Password
     *
     * @return
     */
    public String passwordReset() {
    	
        try {
            UserPasswordResetVO userPasswordResetVO = serviceLocator.getUserPasswordResetDataFacade().getUserPasswordResetVOByResetPin(resetPin);
            
            if (userPasswordResetVO != null && userPasswordResetVO.getExpiryDate().after(new Date())) {
                UserVO userVO = userPasswordResetVO.getUserVO();
                
                userVO.setPasswd(newPassword);
                userVO = serviceLocator.getUserDataFacade().update(userVO);

                login(userVO);
		
		serviceLocator.getUserPasswordResetDataFacade().removeCurrentUserPasswordResetEntriesByEmail(userVO.getEmail());
                return "Home";

            } else {
            	addFacesMessageWarn("Password reset notification request has expired. Please request a new password.", "");
            }
        } catch (UserPasswordResetNotFoundException e) {
        	addFacesMessageError("Could not retrive the password reset request", e.getMessage());
        } catch (Exception e) {
            log.debug(e);
            addFacesMessageError("Failed to reset password", e.getMessage());            
        }
        return "";

    }

    private void login(UserVO userVO) throws ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();        
        servletRequest.login(userVO.getUsername(), userVO.getPasswd());
        servletRequest.getSession().setAttribute("FilterRan", "FilterRan");        
    }

    public void validateConfirmationPassword(FacesContext context, UIComponent toValidate, Object value) {
        String confirmingPassword = (String) value;

        if (NullHelper.getNonNullStringValue(newPassword).isEmpty()
                || !newPassword.equals(confirmingPassword)) {
            addFacesErrorMessageForComponent(context, toValidate, "Passwords do not match.");
            ((UIInput) toValidate).setValid(false);
            return;
        }

    }

    /**
     * Generate Email Message
     *
     * @param userPasswordResetVO
     * @param baseURL
     * @return
     */
//    private Mail getRegisteredUserMail(UserPasswordResetVO userPasswordResetVO, String baseURL) {
//
//        Mail mail = new Mail();
////        mail.setEmailAddress(userPasswordResetVO.getEmail());
////        String body = "Dear Buyer <br/> We have received a request to reset the password for the account registered to this email address."
////                + "<br><br> To complete the password reset, please click <a href=\"" + baseURL + "/public/user-password-reset-view.xhtml?guid=" + userPasswordResetVO.getGuid()
////                + "\">here</a> or copy and paste the following link into your favourite browser: <br>"
////                + baseURL + "/public/user-password-reset-view.xhtml?guid=" + userPasswordResetVO.getGuid()
////                + "<br><br> Regards, <br><br>" + " IT Team";
////        mail.setBody(body);
////        mail.setSubject("Password Reset");
//
//        return mail;
//    }
    public String getBaseUrl() {
        //return serviceLocator.getSystemConfigurationBean().getPpmBaseUrl();
        return "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getResetPin() {
	String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("resetPin");
	if(resetPin == null || (id != null && !id.equals(previousResetPin))){
	    resetPin = id;
	    previousResetPin = id;
	    userPasswordResetExpired = null;
	}
        return resetPin;
    }

    public void setResetPin(String resetPin) {
        this.resetPin = resetPin;
    }

    public String back() {
        return "login.xhtml?faces-redirect=true";
    }

    @Override
    protected String getViewPage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String getListPage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

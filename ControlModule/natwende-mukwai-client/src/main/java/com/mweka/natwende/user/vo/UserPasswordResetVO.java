package com.mweka.natwende.user.vo;

import com.mweka.natwende.base.vo.BaseVO;
import java.util.Date;
import javax.validation.constraints.NotNull;

public class UserPasswordResetVO extends BaseVO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    UserVO userVO;

    String email;
    
    String resetPin;

    @NotNull
    private Date expiryDate;

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResetPin() {
	return resetPin;
    }

    public void setResetPin(String resetPin) {
	this.resetPin = resetPin;
    }

}

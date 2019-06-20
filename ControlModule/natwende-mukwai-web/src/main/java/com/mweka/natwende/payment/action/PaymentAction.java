package com.mweka.natwende.payment.action;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.mweka.natwende.exceptions.EntityNotFoundException;
import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.payment.vo.PaymentVO;
import com.mweka.natwende.types.PaymentOption;

@Named("PaymentAction")
@SessionScoped
public class PaymentAction extends MessageHelper<PaymentVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7502395296200222501L;

	@PostConstruct
	public void init() {
		selectedEntity = new PaymentVO();
	}
	
	@Override
	public List<PaymentVO> getEntityList() {
		loadEntityList();
		return entityList;
	}

	@Override
	public String createEntity() {
		init();
		return null;
	}

	@Override
	public String saveEntity() {
		selectedEntity = serviceLocator.getPaymentDataFacade().update(selectedEntity);
		//RequestContext.getCurrentInstance().execute("PF('var_CardViewDlg').hide();");
		onMessage(SEVERITY_INFO, "Payment saved successfully");
		return null;
	}

	@Override
	public String viewEntity() {
		//RequestContext.getCurrentInstance().update("@widgetVar(var_CardViewDlg)");
		//RequestContext.getCurrentInstance().execute("PF('var_CardViewDlg').show();");
		return null;
	}

	@Override
	public void deleteEntity() {
		try {
			serviceLocator.getPaymentDataFacade().deleteById(selectedEntity.getId());
		} catch (EntityNotFoundException e) {
			log.debug(e);
			onMessage(SEVERITY_ERROR, e.getMessage());
		}		
	}
	
	public PaymentOption[] getPaymentOptions() {
		return PaymentOption.values();
	}
	
	private void loadEntityList() {
		entityList = serviceLocator.getPaymentDataFacade().getPaymentList();
	}
}

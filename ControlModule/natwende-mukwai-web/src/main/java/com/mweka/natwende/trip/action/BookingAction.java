package com.mweka.natwende.booking.action;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.trip.vo.BookingVO;
import com.mweka.natwende.types.PaymentStatus;

@Named("BookingAction1")
@SessionScoped
public class BookingAction extends MessageHelper<BookingVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5605106821593815447L;
	
	private int activeIndex;
	private boolean paymentTabEnabled;
	
	@PostConstruct
	public void init() {
		selectedEntity = new BookingVO();
		activeIndex = 0;
	}

	@Override
	protected List<BookingVO> getEntityList() {
		populateEntityList();
		return entityList;
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public boolean isPaymentTabEnabled() {
		return paymentTabEnabled;
	}

	public void setPaymentTabEnabled(boolean paymentTabEnabled) {
		this.paymentTabEnabled = paymentTabEnabled;
	}

	@Override
	protected String createEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String saveEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String viewEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void deleteEntity() {
		// TODO Auto-generated method stub
		
	}
	
	public void onTabChange(TabChangeEvent event) {
		AccordionPanel accordion = (AccordionPanel) event.getComponent();
		activeIndex = Integer.parseInt(accordion.getActiveIndex());
		switch (activeIndex) {
		case 0 : revealConfirmBookingDetailsTab();
		break;
		case 1 : revealPaymentTab();
		break;
		default : break;
		}
	}
	
	public void onTabClose(TabCloseEvent event) {		
	}
	
	public void revealConfirmBookingDetailsTab() {
		selectedEntity.setId(1L);
		//selectedEntity.setFromTown(Town);
		//selectedEntity.setToTown(Town);
		//selectedEntity.setOperator(Operator);
		activeIndex = 0;
	}
	
	public void revealPaymentTab() {
		selectedEntity.getPayment().setCardNumber("");
		selectedEntity.getPayment().setPaymentStatus(PaymentStatus.PENDING);
		selectedEntity.getPayment().setCustomerName("");
		//selectedEntity.getPayment().setNameOnCard("");
		//selectedEntity.getPayment().setBeneficiary("");
		paymentTabEnabled = true;
		activeIndex = 1;
	}
	
	public String authorizePayment() {
		// Send payment
		
		return "/booking/bookingSuccess";
	}

	private void populateEntityList() {		
	}
}

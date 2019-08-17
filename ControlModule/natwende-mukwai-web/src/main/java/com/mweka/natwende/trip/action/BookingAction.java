package com.mweka.natwende.trip.action;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import com.mweka.natwende.helper.MessageHelper;
import com.mweka.natwende.payment.vo.PaymentVO;
import com.mweka.natwende.trip.vo.BookingVO;
import com.mweka.natwende.trip.vo.ReservationVO;
import com.mweka.natwende.types.BookingStatus;
import com.mweka.natwende.types.PaymentOption;
import com.mweka.natwende.types.PaymentStatus;
import com.mweka.natwende.user.action.UserAction;
import com.mweka.natwende.user.vo.UserVO;

@Named("BookingAction")
@SessionScoped
public class BookingAction extends MessageHelper<BookingVO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5605106821593815447L;
	
	private int activeIndex, currentStep;
	private boolean paymentTabEnabled;
	
	@Inject
	private TripAction tripAction;
	
	@Inject
	private ReservationAction reservationAction;
	
	@Inject
	private UserAction userAction;
	
	@PostConstruct
	public void init() {
		selectedEntity = new BookingVO();
		activeIndex = currentStep = 0;
	}

	@Override
	public List<BookingVO> getEntityList() {
		return entityList;
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public int getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(int currentStep) {
		this.currentStep = currentStep;
	}

	public boolean isPaymentTabEnabled() {
		return paymentTabEnabled;
	}

	public void setPaymentTabEnabled(boolean paymentTabEnabled) {
		this.paymentTabEnabled = paymentTabEnabled;
	}

	@Override
	protected String createEntity() {
		serviceLocator.getBookingDataFacade().update(selectedEntity);
		return null;
	}

	@Override
	protected String saveEntity() {
		serviceLocator.getBookingDataFacade().update(selectedEntity);
		return listEntities();
	}
	
	public String listEntities() {
		return "";
	}

	@Override
	protected String viewEntity() {
		return null;
	}

	@Override
	protected void deleteEntity() {
		try {
			serviceLocator.getBookingDataFacade().deleteById(selectedEntity.getId());
		}
		catch (Exception ex) {
			onMessage(SEVERITY_ERROR, ex.getMessage());
		}
	}
	
	public void onTabChange(TabChangeEvent event) {
		TabView tabView = (TabView) event.getTab().getParent();
		activeIndex = tabView.getActiveIndex();
	}
	
	public void onTabClose(TabCloseEvent event) {		
	}
	
	public String generatePayment() {
		ReservationVO reservation = reservationAction.getSelectedEntity();
		PaymentVO payment = reservationAction.getSelectedEntity().getPayment();
		reservation.setCustomer(userAction.getLoggedInUser());
		populatePaymentFields(payment);
		payment.setAddress(userAction.getLoggedInUser().getAddress().toString());
		payment = serviceLocator.getPaymentDataFacade().update(payment);
		reservation.setPayment(payment);
		reservation = serviceLocator.getReservationDataFacade().update(reservation);
		
		for (BookingVO b : entityList) {
			b.setReservation(reservation);
			populateBookingFields(b);
			serviceLocator.getBookingDataFacade().update(b);
		}
			
		switch (payment.getPaymentOption()) {
			case BANK_APP : ;
			case MOBILE_MONEY : ;
			case ZOONA : // Save to database for third party to pickup. Show message of pending payment.
				reservation.setBookingStatus(BookingStatus.RESERVED);
			break;
			case CASH : ;
			case SWIPE_MACHINE : // Log ticket seller. Show success message.				
				payment.setPaymentStatus(PaymentStatus.AUTHORIZED);
				serviceLocator.getPaymentDataFacade().update(payment);
				reservation.setBookingStatus(BookingStatus.CONFIRMED);
				serviceLocator.getReservationDataFacade().update(reservation);
				return "/trip/bookingSuccess?faces-redirect=true";
			case VISA_CARD : ;// Connect to payment gateway. Show outcome of payment instruction.
			break;
			default : reservation.setBookingStatus(BookingStatus.UNKNOWN);
				serviceLocator.getReservationDataFacade().update(reservation);
				return "/trip/bookingUnknown?faces-redirect=true";
		}		
		return "/trip/bookingPending?faces-redirect=true";
	}
	
	private void populatePaymentFields(PaymentVO payment) {
		if (payment.getPaymentOption() == PaymentOption.VISA_CARD) {
			payment.setCardNumber("");
		}
		payment.setPaymentStatus(PaymentStatus.PENDING);
		UserVO customer = userAction.getLoggedInUser();
		payment.setCustomerName(customer.getName());
		payment.setPhoneNumber(customer.getContactNumber());
		payment.setBeneficiary(tripAction.getSelectedSearchResult().getTrip().getOperatorName().name());
		payment.setCustomerNrc(customer.getNrc());
		payment.setAmount(calculateTotalBookingCost());
		payment.setDescription(new StringBuilder(payment.getBeneficiary())
				.append(" ")
				.append(tripAction.getSelectedSearchResult().getFromTown().getDisplay())
				.append("-")
				.append(tripAction.getSelectedSearchResult().getToTown().getDisplay())
				.append(",")
				.append(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(tripAction.getSelectedSearchResult().getEstimatedJourneyStartDate()))
				.append(",")
				.append(payment.getCustomerName())
				.toString());
	}
	
	private void populateBookingFields(BookingVO booking) {
		booking.setTrip(tripAction.getSelectedEntity());
		booking.setFrom(tripAction.getSelectedSearchResult().getFromTown().getDisplay());
		booking.setTo(tripAction.getSelectedSearchResult().getToTown().getDisplay());
	}
	
	public void step(ActionEvent event) {
		UICommand comp = (UICommand) event.getComponent();
		String text = (String) comp.getValue();
		
		switch (text.toLowerCase()) {
		case "next" : setCurrentStep(currentStep + 1);
		break;
		case "prev" : setCurrentStep(currentStep - 1);
		break;
		case "finish" : onMessage(SEVERITY_INFO, "Finished!");
		break;
		default: onMessage(SEVERITY_ERROR, "Unsupported case item: [" + text + "]");
		}
	}
	
	public String authorizePayment() {
		// Send payment		
		return "/booking/bookingSuccess";
	}
	
	@SuppressWarnings("unused")
	private void printSessionMap() {
		for (Map.Entry<String, Object> entry : FacesContext.getCurrentInstance().getExternalContext().getSessionMap().entrySet()) {
			log.debug("Key = " + entry.getKey() + ", value = " + entry.getValue());
		}
	}

	public void populateReservationBookings() {
		entityList = serviceLocator.getBookingDataFacade().getByReservation(reservationAction.getSelectedEntity());
	}
	
	public void populateEntityList() {
		if (tripAction.getSelectedEntity() == null || tripAction.getSelectedEntity().getId() == -1L) {
			entityList = serviceLocator.getBookingDataFacade().getAll();
		}
		else {
			entityList = serviceLocator.getBookingDataFacade().getListByTripId(tripAction.getSelectedEntity().getId());
		}
	}
	
	private BigDecimal calculateTotalBookingCost() {
		BigDecimal sum = BigDecimal.ZERO;
		for (BookingVO b : entityList) {
			sum = sum.add(b.getFare());
		}
		return sum;
	}
}

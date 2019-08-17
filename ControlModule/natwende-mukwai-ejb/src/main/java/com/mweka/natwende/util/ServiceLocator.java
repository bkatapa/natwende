package com.mweka.natwende.util;

import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingException;

import com.mweka.natwende.cdi.StretchHelperInstance;
import com.mweka.natwende.dashboard.facade.BookingDashboardFacade;
import com.mweka.natwende.exceptions.ServiceLocatorException;
import com.mweka.natwende.location.facade.AddressDataFacade;
import com.mweka.natwende.mail.MailerFacade;
import com.mweka.natwende.media.facade.MediaDataFacade;
import com.mweka.natwende.media.facade.MediaFacade;
import com.mweka.natwende.notification.facade.MessageDataFacade;
import com.mweka.natwende.notification.facade.NotificationDataFacade;
import com.mweka.natwende.operator.facade.BusDataFacade;
import com.mweka.natwende.operator.facade.BusFacade;
import com.mweka.natwende.operator.facade.OperatorDataFacade;
import com.mweka.natwende.operator.facade.OperatorFacade;
import com.mweka.natwende.operator.facade.OperatorRouteLinkDataFacade;
import com.mweka.natwende.operator.facade.OperatorRouteLinkFacade;
import com.mweka.natwende.operator.facade.OperatorSettingsDataFacade;
import com.mweka.natwende.operator.facade.OperatorSettingsFacade;
import com.mweka.natwende.operator.facade.SeatDataFacade;
import com.mweka.natwende.operator.facade.SeatFacade;
import com.mweka.natwende.payment.facade.CardDataFacade;
import com.mweka.natwende.payment.facade.CardFacade;
import com.mweka.natwende.payment.facade.PaymentDataFacade;
import com.mweka.natwende.payment.facade.PaymentFacade;
import com.mweka.natwende.report.ReportFacade;
import com.mweka.natwende.route.facade.FareDataFacade;
import com.mweka.natwende.route.facade.FareFacade;
import com.mweka.natwende.route.facade.RouteDataFacade;
import com.mweka.natwende.route.facade.RouteFacade;
import com.mweka.natwende.route.facade.StretchDataFacade;
import com.mweka.natwende.route.facade.StretchFacade;
import com.mweka.natwende.route.facade.RouteStopLinkDataFacade;
import com.mweka.natwende.route.facade.RouteStopLinkFacade;
import com.mweka.natwende.route.facade.RouteStretchLinkDataFacade;
import com.mweka.natwende.route.facade.RouteStretchLinkFacade;
import com.mweka.natwende.route.facade.StopDataFacade;
import com.mweka.natwende.route.facade.StopFacade;
import com.mweka.natwende.trip.facade.BookingDataFacade;
import com.mweka.natwende.trip.facade.BookingFacade;
import com.mweka.natwende.trip.facade.BusTripScheduleLinkDataFacade;
import com.mweka.natwende.trip.facade.BusTripScheduleLinkFacade;
import com.mweka.natwende.trip.facade.ReservationDataFacade;
import com.mweka.natwende.trip.facade.TripDataFacade;
import com.mweka.natwende.trip.facade.TripFacade;
import com.mweka.natwende.trip.facade.TripScheduleDataFacade;
import com.mweka.natwende.trip.facade.TripScheduleFacade;
import com.mweka.natwende.user.facade.RoleDataFacade;
import com.mweka.natwende.user.facade.RoleFacade;
import com.mweka.natwende.user.facade.UserDataFacade;
import com.mweka.natwende.user.facade.UserFacade;
import com.mweka.natwende.user.facade.UserPasswordResetDataFacade;
import com.mweka.natwende.user.facade.UserPasswordResetFacade;
import com.mweka.natwende.user.facade.UserRoleLinkDataFacade;
import com.mweka.natwende.user.facade.UserRoleLinkFacade;

@Stateless
@LocalBean
public class ServiceLocator {

	final static String webManagerJndiUrl = "localhost:1099";

	private Context jndiContext;

	@EJB
	private UserFacade userFacade;

	@EJB
	private UserDataFacade userDataFacade;

	@EJB
	private MailerFacade mailerFacade;
	
	@EJB
	private OperatorFacade operatorFacade;

	@EJB
	private OperatorDataFacade operatorDataFacade;
	
	@EJB
	private OperatorRouteLinkFacade operatorRouteLinkFacade;

	@EJB
	private OperatorRouteLinkDataFacade operatorRouteLinkDataFacade;
	
	@EJB
	private BusFacade busFacade;

	@EJB
	private BusDataFacade busDataFacade;
	
	@EJB
	private SeatFacade seatFacade;

	@EJB
	private SeatDataFacade seatDataFacade;
	
	@EJB
	private RouteFacade routeFacade;

	@EJB
	private RouteDataFacade routeDataFacade;
	
	@EJB
	private StopFacade stopFacade;

	@EJB
	private StopDataFacade stopDataFacade;
	
	@EJB
	private FareFacade fareFacade;
	
	@EJB
	private FareDataFacade fareDataFacade;
	
	@EJB
	private RouteStopLinkFacade routeStopLinkFacade;

	@EJB
	private RouteStopLinkDataFacade routeStopLinkDataFacade;

	@EJB
	private MediaDataFacade mediaDataFacade;

	@EJB
	private MediaFacade mediaFacade;

	@EJB
	private RoleFacade roleFacade;

	@EJB
	private RoleDataFacade roleDataFacade;
	
	@EJB
	private ReportFacade reportFacade;

	@EJB
	private AddressDataFacade addressDataFacade;

	@EJB
	private UserRoleLinkFacade userRoleLinkFacade;

	@EJB
	private UserRoleLinkDataFacade userRoleLinkDataFacade;

	@EJB
	private UserPasswordResetDataFacade userPasswordResetDataFacade;

	@EJB
	private UserPasswordResetFacade userPasswordResetFacade;

	@EJB
	private SystemConfigurationBean systemConfigurationBean;

	@EJB
	private NotificationDataFacade notificationDataFacade;
	
	@EJB
	private BookingDashboardFacade bookingDashboardFacade;
	
	@EJB
	private OperatorSettingsFacade operatorSettingsFacade;
	
	@EJB
	private OperatorSettingsDataFacade operatorSettingsDataFacade;
	
	@EJB
	private BookingFacade bookingFacade;
	
	@EJB
	private BookingDataFacade bookingDataFacade;
	
	@EJB
	private TripFacade tripFacade;
	
	@EJB
	private TripDataFacade tripDataFacade;
	
	@EJB
	private PaymentFacade paymentFacade;
	
	@EJB
	private PaymentDataFacade paymentDataFacade;
	
	@EJB
	private StretchDataFacade stretchDataFacade;
	
	@EJB
	private StretchFacade stretchFacade;
	
	@EJB
	private RouteStretchLinkFacade routeStretchLinkFacade;
	
	@EJB
	private RouteStretchLinkDataFacade routeStretchLinkDataFacade;
	
	@EJB
	private TripScheduleFacade tripScheduleFacade;
	
	@EJB
	private TripScheduleDataFacade tripScheduleDataFacade;
	
	@EJB
	private BusTripScheduleLinkFacade busTripScheduleLinkFacade;
	
	@EJB
	private BusTripScheduleLinkDataFacade busTripScheduleLinkDataFacade;
	
	@EJB
	private CardFacade cardFacade;
	
	@EJB
	private CardDataFacade cardDataFacade;
	
	@EJB
	private ReservationDataFacade reservationDataFacade;
	
	@EJB
	private MessageDataFacade messageDataFacade;
	
	@Inject
	@StretchHelperInstance
	private StretchHelper stretchHelper;

	public Object getRemote(String jndiName) throws ServiceLocatorException {
		Object obj = null;
		try {
			if (jndiContext == null) {
				getInitialContext();
			}
			// Look up using the service name from
			// defined constant
			obj = jndiContext.lookup(jndiName);
		} catch (NamingException ex) {
			throw new ServiceLocatorException(ex);
		} catch (Exception ex) {
			throw new ServiceLocatorException(ex);
		}
		return obj;
	}

	private void getInitialContext() throws javax.naming.NamingException {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		p.put(Context.PROVIDER_URL, webManagerJndiUrl);
		jndiContext = new javax.naming.InitialContext(p);
	}

	public UserFacade getUserFacade() {
		return userFacade;
	}

	public UserDataFacade getUserDataFacade() {
		return userDataFacade;
	}

	public MailerFacade getMailerFacade() {
		return mailerFacade;
	}
	
	public OperatorFacade getOperatorFacade() {
		return operatorFacade;
	}

	public OperatorDataFacade getOperatorDataFacade() {
		return operatorDataFacade;
	}
	
	public OperatorRouteLinkFacade getOperatorRouteLinkFacade() {
		return operatorRouteLinkFacade;
	}

	public OperatorRouteLinkDataFacade getOperatorRouteLinkDataFacade() {
		return operatorRouteLinkDataFacade;
	}
	
	public BusFacade getBusFacade() {
		return busFacade;
	}
	
	public BusDataFacade getBusDataFacade() {
		return busDataFacade;
	}

	public MediaDataFacade getMediaDataFacade() {
		return mediaDataFacade;
	}

	public MediaFacade getMediaFacade() {
		return mediaFacade;
	}

	public RoleFacade getRoleFacade() {
		return roleFacade;
	}

	public RoleDataFacade getRoleDataFacade() {
		return roleDataFacade;
	}

	public UserRoleLinkFacade getUserRoleLinkFacade() {
		return userRoleLinkFacade;
	}

	public UserRoleLinkDataFacade getUserRoleLinkDataFacade() {
		return userRoleLinkDataFacade;
	}

	public AddressDataFacade getAddressDataFacade() {
		return addressDataFacade;
	}

	public NotificationDataFacade getNotificationDataFacade() {
		return notificationDataFacade;
	}

	public UserPasswordResetDataFacade getUserPasswordResetDataFacade() {
		return userPasswordResetDataFacade;
	}

	public UserPasswordResetFacade getUserPasswordResetFacade() {
		return userPasswordResetFacade;
	}

	public SystemConfigurationBean getSystemConfigurationBean() {
		return systemConfigurationBean;
	}

	public ReportFacade getReportFacade() {
		return reportFacade;
	}

	public void setReportFacade(ReportFacade reportFacade) {
		this.reportFacade = reportFacade;
	}

	public BookingDashboardFacade getBookingDashboardFacade() {
		return bookingDashboardFacade;
	}

	public OperatorSettingsFacade getOperatorSettingsFacade() {
		return operatorSettingsFacade;
	}

	public OperatorSettingsDataFacade getOperatorSettingsDataFacade() {
		return operatorSettingsDataFacade;
	}
	
	public SeatFacade getSeatFacade() {
		return seatFacade;
	}

	public SeatDataFacade getSeatDataFacade() {
		return seatDataFacade;
	}
	
	public RouteFacade getRouteFacade() {
		return routeFacade;
	}

	public RouteDataFacade getRouteDataFacade() {
		return routeDataFacade;
	}
	
	public StretchFacade getStretchFacade() {
		return stretchFacade;
	}

	public StretchDataFacade getStretchDataFacade() {
		return stretchDataFacade;
	}
	
	public StretchHelper getStretchHelper() {
		return stretchHelper;
	}
	
	public RouteStopLinkFacade getRouteStopLinkFacade() {
		return routeStopLinkFacade;
	}

	public RouteStopLinkDataFacade getRouteStopLinkDataFacade() {
		return routeStopLinkDataFacade;
	}
	
	public StopFacade getStopFacade() {
		return stopFacade;
	}

	public StopDataFacade getStopDataFacade() {
		return stopDataFacade;
	}
	
	public FareFacade getFareFacade() {
		return fareFacade;
	}
	
	public FareDataFacade getFareDataFacade() {
		return fareDataFacade;
	}
	
	public BookingFacade getBookingFacade() {
		return bookingFacade;
	}

	public BookingDataFacade getBookingDataFacade() {
		return bookingDataFacade;
	}
	
	public TripFacade getTripFacade() {
		return tripFacade;
	}

	public TripDataFacade getTripDataFacade() {
		return tripDataFacade;
	}
	
	public PaymentFacade getPaymentFacade() {
		return paymentFacade;
	}

	public PaymentDataFacade getPaymentDataFacade() {
		return paymentDataFacade;
	}
	
	public RouteStretchLinkFacade getRouteStretchLinkFacade() {
		return routeStretchLinkFacade;
	}
	
	public RouteStretchLinkDataFacade getRouteStretchLinkDataFacade() {
		return routeStretchLinkDataFacade;
	}

	public TripScheduleFacade getTripScheduleFacade() {
		return tripScheduleFacade;
	}

	public TripScheduleDataFacade getTripScheduleDataFacade() {
		return tripScheduleDataFacade;
	}

	public BusTripScheduleLinkFacade getBusTripScheduleLinkFacade() {
		return busTripScheduleLinkFacade;
	}

	public BusTripScheduleLinkDataFacade getBusTripScheduleLinkDataFacade() {
		return busTripScheduleLinkDataFacade;
	}
	
	public CardFacade getCardFacade() {
		return cardFacade;
	}
	
	public CardDataFacade getCardDataFacade() {
		return cardDataFacade;
	}
	
	public ReservationDataFacade getReservationDataFacade() {
		return reservationDataFacade;
	}
	
	public MessageDataFacade getMessageDataFacade() {
		return messageDataFacade;
	}
}

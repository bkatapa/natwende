package com.mweka.natwende.trip.action;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mweka.natwende.action.AbstractActionBean;
import com.mweka.natwende.cdi.LoggedInUser;
import com.mweka.natwende.trip.vo.BookingVO;
import com.mweka.natwende.types.PagePath;
import com.mweka.natwende.user.vo.UserVO;
import com.mweka.natwende.util.ServiceLocator;

@Named("BookingAction")
@SessionScoped
public class BookingAction extends AbstractActionBean<BookingVO> {
    private static final long serialVersionUID = 1L;
    //private static final String SHOW_BULK_BOOKING_WARNING = "PF('var_BulkBookingWarnForm').show();";
    
    @EJB
    private ServiceLocator serviceLocator;
    
    @Inject
    @LoggedInUser
    private UserVO userVO;
    
    private BookingVO selectedBookingVO;    
    //private BookingSearchVO bookingSearchVO = new BookingSearchVO();
    private boolean bulk = true;
    
	@Override
	protected List<BookingVO> getListFromFacade() {
		List<BookingVO> resultList = new ArrayList<>();
		try{
			//bookingSearchVO.setStatus(Status.ACTIVE);
			resultList = null; //serviceLocator.getBookingFacade().fetchAllTenantBookings(bookingSearchVO);
		}catch(Exception e){
			log.debug(e,e);
			addFacesMessageError("Error", e.getMessage());
		}	
			
		return resultList;
	}

	@Override
	protected BookingVO facadeUpdate(BookingVO dataItem) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected void facadeDelete(BookingVO dataItem) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
    public String getViewPage() {
        return PagePath.BOOKING_MANAGEMENT_VIEW.getPath();
    }

    @Override
    public String getListPage() {
    	refresh();
        return PagePath.BOOKING_MANAGEMENT_LIST.getPath();
    }
    
    public String createBooking() {	
    	return PagePath.BOOKING_MANAGEMENT_EDIT.getPath();
    }
    
    public BookingVO getSelectedBookingVO() {
		return selectedBookingVO;
	}

	public void setSelectedBookingVO(BookingVO selectedBookingVO) {
		this.selectedBookingVO = selectedBookingVO;
	}

//	public BookingSearchVO getBookingSearchVO() {
//		return bookingSearchVO;
//	}
//
//	public void setBookingSearchVO(BookingSearchVO bookingSearchVO) {
//		this.bookingSearchVO = bookingSearchVO;
//	}

	@PostConstruct
	public void postConstruct() {
		if (userVO != null) {
//			bookingSearchVO = new BookingSearchVO();
//			bookingSearchVO.setTenantId(userVO.getTenantVO().getId());
		}
	}

	public void cancelBooking() {
		//bookingEditAction.setActionMode(UserActionMode.CANCEL);
	}
	
	public String editBooking(BookingVO selectedBookingVO) {
//		if (selectedBookingVO.getBulkBookingVO() != null && bulk) {
//			this.selectedBookingVO = selectedBookingVO;
//			RequestContext.getCurrentInstance().execute(SHOW_BULK_BOOKING_WARNING);
//			return null;
//		}
//		bookingEditAction.setActionMode(UserActionMode.EDIT);
//		bookingEditAction.setBookingVO(selectedBookingVO);
		bulk = true;
		return PagePath.BOOKING_MANAGEMENT_EDIT.getPath();
	}
	
	public void search() {		
		super.refresh();
	}
	
	public void reset() {
//		bookingSearchVO.clearSearch();
//		bookingSearchVO.setTenantId(userVO.getTenantVO().getId());
	}
	
	@Override
	public void refresh() {
		super.refresh();
		//bookingSearchVO.clearSearch();
	}

	public boolean isBulk() {
		return bulk;
	}
	
	public String switchToBulkEditingMode() {		
		return null; //bulkBookingAction.editBulkBooking(selectedBookingVO.getBulkBookingVO());		
	}
	
	public String ignoreBulkEditingMode() {		
		bulk = false;
		return editBooking(selectedBookingVO);
	}
}

package com.mweka.natwende.booking.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

import javax.faces.validator.LengthValidator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mweka.natwende.operator.vo.SeatVO;
import com.mweka.natwende.payment.vo.PaymentVO;
import com.mweka.natwende.trip.vo.BookingVO;
import com.mweka.natwende.types.SeatClass;
import com.mweka.natwende.user.vo.UserVO;

/**
 * Servlet implementation class BookingDataServlet
 */
@WebServlet("/BookingDataServlet")
public class BookingDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingDataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		StringBuffer buffer = new StringBuffer();
		String line;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
				buffer.append(line);
			}
			if (buffer.length() > 0 && request.getSession(false) != null) {
				BookingAction bookingAction = (BookingAction) request.getSession().getAttribute("BookingAction");
				if (bookingAction == null) {
					bookingAction = new BookingAction();
					bookingAction.init();
					request.getSession().setAttribute("BookingAction", bookingAction);
				}
				processBookingData(buffer, bookingAction);
			}
			else {
				response.sendError(HttpServletResponse.SC_EXPECTATION_FAILED);
			}
		}
		catch (IOException e) {
			throw e;
		}
	}
	
	private synchronized void processBookingData(final StringBuffer data, BookingAction bookingAction) {
		int totalIndex = data.indexOf("total");
		data.delete(--totalIndex, totalIndex + 12);
		int bookedSeatsIndex = data.indexOf("bookedSeats");
		String bookedSeatsLine = data.substring(bookedSeatsIndex) + 14, data.length() - 3);
		String[] bookingList = bookedSeatsLine.split(",");
		bookingAction.getEntityList().clear();
		
		if (bookingList.length == 1) {
			breakDownBookingData(bookedSeatsLine, bookingAction);
		}
		else {
			for (String bookingEntry : bookingList) {
				breakDownBookingData(bookingEntry, bookingAction);
			}
		}
		if (bookingAction.getEntityList().size() == 1) {
			bookingAction.setSelectedEntity(bookingAction.getEntityList().get(0));
		}
	}
	
	private void breakDownBookingData(String data, BookingAction bookingAction) {
		String[] comps = data.split(":");
		int idx = comps[0].indexOf("Seat");
		String seatNo = comps[0].substring(idx + 7);
		String seatClass = comps[0].substring(1, idx - 1);
		String seatFare = comps[1].replaceAll("K", "").replace("[cancel]\"", "").trim();
		SeatVO seat = new SeatVO(seatNo);
		
		if (seatClass.contains("Economy")) {
			seat.setSeatClass(SeatClass.ECONOMY);
		}
		
		UserVO user = new UserVO("Chanda", "Chikokoshi", "cc@mweka.com");
		BookingVO booking = new BookingVO(seat.getSeatNo(), user);
		booking.setPayment(new PaymentVO(new BigDecimal(seatFare)));
		bookingAction.getEntityList().add(booking);		
	}

}

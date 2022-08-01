package com.mweka.natwende.trip.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

import javax.inject.Inject;
//import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
import com.mweka.natwende.trip.vo.BookingVO;

/**
 * Servlet implementation class BookingDataServlet
 */
@WebServlet("/bookingData")
public class BookingDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private BookingAction bookingAction;
       
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
		/*
		if (request.getUserPrincipal() == null) {
			response.sendRedirect("/" + request.getServletContext() + "/login");
		}*/
		response.setContentType("application/json; charset=UTF-8");
		StringBuffer buffer = new StringBuffer();
		String line;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
				buffer.append(line);
			}
			if (buffer.length() > 0) {
				if (extractDataFromJson(buffer.toString())) {
					processBookingData(buffer);
				}
			}
			else {
				response.sendError(HttpServletResponse.SC_EXPECTATION_FAILED);
			}
		}
		catch (IOException e) {
			throw e;
		}
	}
	
	private synchronized void processBookingData(final StringBuffer data) {
		int totalIndex = data.indexOf("total");
		data.delete(--totalIndex, totalIndex + 12);
		int bookedSeatsIndex = data.indexOf("bookedSeats");
		String bookedSeatsLine = data.substring(bookedSeatsIndex + 14, data.length() - 3);
		String[] bookingList = bookedSeatsLine.split(",");
		bookingAction.getEntityList().clear();
		
		if (bookingList.length == 1) {
			breakDownBookingData(bookedSeatsLine);
		}
		else {
			for (String bookingEntry : bookingList) {
				breakDownBookingData(bookingEntry);
			}
		}
	}
	
	private void breakDownBookingData(String data) {
		String[] comps = data.split(":");
		int idx = comps[0].indexOf("Seat");
		String seatNo = comps[0].substring(idx + 7);
		String seatFare = comps[1].replaceAll("\\D+", StringUtils.EMPTY);		
		BookingVO booking = new BookingVO(seatNo);
		BigDecimal busFare = BigDecimal.valueOf(Double.parseDouble(seatFare));
		booking.setFare(busFare);
		bookingAction.getEntityList().add(booking);		
	}
	
	private boolean extractDataFromJson(String jsonLine) { // json: { "bookedSeats" : [""], "total" : "0" }
		//JsonElement jelement = new JsonParser().parse(jsonLine);
		//JsonObject jobject = jelement.getAsJsonObject();
		//JsonArray jarray = jobject.getAsJsonArray("bookedSeats");
		//return jarray.size() > 0 && !jarray.get(0).getAsString().isEmpty();
		return true;
	}

}

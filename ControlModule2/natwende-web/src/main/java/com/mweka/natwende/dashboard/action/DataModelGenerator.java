package com.mweka.natwende.dashboard.action;

import java.util.Map;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

public class DataModelGenerator {
    public ChartModel createPieChartModel(long allocatedBayCount, long bookedBayCount) {
        PieChartModel model = new PieChartModel();
        model.setLegendPosition("w");
        model.setShowDataLabels(true);
        model.set("Booked", bookedBayCount);
        model.set("Available", allocatedBayCount - bookedBayCount);       
        model.setTitle("Bays [Booked vs Available]");
        model.setSeriesColors("c4d5f2,deefbd,929397");
        return model;
    }
    
    public ChartModel createHourlyBookingModel(String tenantName, Map<Integer, Integer> bookingCountPerHourMap) {
    	CartesianChartModel combinedModel = new BarChartModel();
        LineChartSeries bookingCountLineSeries = new LineChartSeries();
        BarChartSeries bookingCountBarSeries = new BarChartSeries();
        
        bookingCountLineSeries.setLabel("Booking count");
        bookingCountBarSeries.setLabel("Booking count");
	int maxBookingsPerHour = 0;
        for (Map.Entry<Integer, Integer> entry : bookingCountPerHourMap.entrySet()) {        	
//        	bookingCountLineSeries.set(entry.getKey(), entry.getValue());
        	bookingCountBarSeries.set(entry.getKey(), entry.getValue());
		if(maxBookingsPerHour < entry.getValue()){
			maxBookingsPerHour = entry.getValue();
		}
        }
 

        combinedModel.addSeries(bookingCountBarSeries);        
//        combinedModel.addSeries(bookingCountLineSeries);

        combinedModel.setTitle("Hourly Bookings");
        combinedModel.setLegendPosition("ne");
        combinedModel.setMouseoverHighlight(false);
        combinedModel.setShowDatatip(false);
        combinedModel.setShowPointLabels(true);
        combinedModel.setSeriesColors("c4d5f2,000000,929397");
        
        Axis yAxis = combinedModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(maxBookingsPerHour+(maxBookingsPerHour * 0.10 < 5 ? 10 : maxBookingsPerHour * 0.10));

        return combinedModel;
    }
}

package com.mweka.natwende.helper;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.mweka.natwende.operator.vo.SeatVO;
import com.mweka.natwende.types.SeatClass;

@Named
@ApplicationScoped
public class SeatImporter implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public synchronized List<SeatVO> process(InputStream in) throws Exception {
        try {
            Map<String, List<SeatVO>> seatMap;
            try (Scanner reader = new Scanner(in)) {
                seatMap = new LinkedHashMap<>();
                int rowCounter = 0;
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();

                    if ("".equals(line.trim()) || line.contains("*")) {
                        LOGGER.log(Level.INFO, "Row [{0}] skipped", rowCounter + 1);
                        continue;
                    }

                    if (!line.startsWith("R")) {
                        throw new Exception("Incorrect row format, must start with R: " + line);
                    }

                    List<SeatVO> seatRow = new ArrayList<>();

                    String[] result = line.split("\\s+");
                    String rowNum = result[0];

                    String[] record = line.split("\\|");
                    int columnCounter = 0;
                    rowCounter++;

                    for (String entry : record) {
                        columnCounter++;
                        if (entry.matches("\\s+") || !entry.contains(",")) {
                            continue;
                        }

                        String[] seatDetails = entry.split(",");
                        String seatNo = seatDetails[0];
                        if (Character.toUpperCase(seatNo.charAt(0)) == 'R') {
                            seatNo = seatNo.split("\\s+")[1];
                        }
                        String seatCoordinates = rowCounter + "_" + columnCounter;
                        SeatClass seatClass;
                        
                        switch (Character.toUpperCase(seatDetails[1].charAt(0))) {
                            case 'E':
                                seatClass = SeatClass.ECONOMY;
                                break;
                            case 'S':
                                seatClass = SeatClass.STANDARD;
                                break;
                            case 'B':
                                seatClass = SeatClass.BUSINESS;
                                break;
                            default:
                                seatClass = SeatClass.STANDARD;
                        }

                        seatRow.add(new SeatVO(seatNo, seatCoordinates, seatClass));
                    }
                    seatMap.put(rowNum, seatRow);
                }
            }
            List<SeatVO> resultList = new ArrayList<>();
            if (!seatMap.isEmpty()) {
                for (Map.Entry<String, List<SeatVO>> entry : seatMap.entrySet()) {
                	LOGGER.log(Level.INFO, "=========== Row num: " + entry.getKey() + " ==============");
                    for (SeatVO seat : entry.getValue()) {
                    	LOGGER.log(Level.INFO, seat.toString() + " => translated successfully!");
                        resultList.add(seat);
                    }
                }
            }
            return resultList;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw ex;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(SeatImporter.class.getName());
}

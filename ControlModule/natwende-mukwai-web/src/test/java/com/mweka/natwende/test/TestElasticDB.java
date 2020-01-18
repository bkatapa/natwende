package com.mweka.natwende.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mweka.natwende.trip.vo.ElasticTripVO;

public class TestElasticDB {
	
	@Test
    public void run() {
		try {
			insertEntity(null);
			getEntity(null);
			updateEntity(null);
			deleteEntity(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ElasticTripVO getEntity(String uniqueId) throws Exception {
		LOGGER.config("fetching trip...");		
        Client client = ClientBuilder.newClient();
        Response response = client.target(BASE_URL) // select/index/{index}/type/{type}/id/{id}
                .path("select")
                .path("index")
                .path(ElasticTripVO.INDEX)
                .path("type")
                .path(ElasticTripVO.TYPE)
                .path("id")
                .path(id)
                .request(MediaType.APPLICATION_JSON)
                .get();
        
        if (response.getStatus() != 200) {
            LOGGER.config(response.toString());
            throw new Exception(response.getStatusInfo().toString());
        }
        try {
            String result = response.readEntity(String.class);
            System.out.println(result);
            //OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            ElasticTripVO entity = null; //OBJECT_MAPPER.readValue(result, ElasticTripVO.class);
            return entity;
        }
        catch (Exception ex) {
            LOGGER.config(ex.toString());
            throw ex;
        }
    }

	public ElasticTripVO insertEntity(ElasticTripVO vo) throws Exception {
		//LOGGER.config("Inserting a new trip...");
		data = populateTrip();
        id = data.get("uniqueId").toString();
		
        Client client = ClientBuilder.newClient();
        Response response = client.target(BASE_URL) // insert/index/{index}/type/{type}/id/{id}
                .path("insert")
                .path("index")
                .path(ElasticTripVO.INDEX)
                .path("type")
                .path(ElasticTripVO.TYPE)
                .path("id")
                .path(id)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(data, MediaType.APPLICATION_JSON));
        
        if (response.getStatus() != 200) {
            LOGGER.config(response.toString());
            throw new Exception(response.getStatusInfo().toString());
        }
        try {
            String result = response.readEntity(String.class);
            System.out.println(result);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            ElasticTripVO entity = null /*mapper.readValue(result, ElasticTripVO.class)*/;
            return entity;
        }
        catch (Exception ex) {
            LOGGER.config(ex.toString());
            throw ex;
        }
    }
	
	public ElasticTripVO updateEntity(ElasticTripVO vo) throws Exception {
		//LOGGER.config("Updating a new trip...");
		adjustTripData();
        Client client = ClientBuilder.newClient();
        Response response = client.target(BASE_URL) // update/index/{index}/type/{type}/id/{id}
                .path("update")
                .path("index")
                .path(ElasticTripVO.INDEX)
                .path("type")
                .path(ElasticTripVO.TYPE)
                .path("id")
                .path(id)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(data, MediaType.APPLICATION_JSON));
        
        if (response.getStatus() != 200) {
            LOGGER.config(response.toString());
            throw new Exception(response.getStatusInfo().toString());
        }
        try {
            String result = response.readEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            ElasticTripVO entity = mapper.readValue(result, ElasticTripVO.class);
            return entity;
        }
        catch (IOException ex) {
            LOGGER.config(ex.toString());
            throw ex;
        }
    }
	
	public String deleteEntity(ElasticTripVO vo) throws Exception {
        Client client = ClientBuilder.newClient();
        Response response = client.target(BASE_URL) // delete/index/{index}/type/{type}/id/{id}
                .path("delete")
                .path("index")
                .path(ElasticTripVO.INDEX)
                .path("type")
                .path(ElasticTripVO.TYPE)
                .path("id")
                .path(id)
                .request(MediaType.TEXT_PLAIN)
                .delete();
        String result = response.readEntity(String.class);
        if (response.getStatus() == 200) {
            response.close();
        }
        else {
            response.close();
            throw new Exception(response.getStatusInfo().toString() + " " + result);
        }
        return result;
    }
	
	private static Map<String, Object> populateTrip() {
        InputStream inputStream = TestElasticDB.class.getClassLoader()
                .getResourceAsStream("META-INF/trip_input.txt");
        Scanner scan = new Scanner(inputStream);
        Map<String, Object> data = new HashMap<>();
        while (scan.hasNextLine()) {
            String[] tokens = scan.nextLine().split("=");
            data.put(tokens[0].trim(), tokens[1].trim().replace(";", ""));
        }
        scan.close();
        return data;
    }
	
	private void adjustTripData() {
		LOGGER.config("Reducing available seats by 1`...");
        Integer bookedNumOfSeats = Integer.valueOf((String) data.get("booked_num_of_seats")) + 1;
        Integer availNumOfSeats = Integer.valueOf((String) data.get("avail_num_of_seats")) - 1;
        data.put("booked_num_of_seats", bookedNumOfSeats.toString());
        data.put("avail_num_of_seats", availNumOfSeats.toString());
	}
	
	private static String id;
	private static Map<String, Object> data;
	private static final String BASE_URL = "http://localhost:8082";
    private static final String CLASS_NAME = TestElasticDB.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
}

package com.mweka.natwende.resource.elasticdb;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mweka.natwende.trip.vo.ElasticTripEvent;
import com.mweka.natwende.trip.vo.ElasticTripVO;
import com.mweka.natwende.util.MapObjectInstance;

@Named
public class ElasticDB {
	
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
                .path(uniqueId)
                .request(MediaType.APPLICATION_JSON)
                .get();
        
        if (response.getStatus() != 200) {
            LOGGER.config(response.toString());
            throw new Exception(response.getStatusInfo().toString());
        }
        try {
            String result = response.readEntity(String.class);
            System.out.println(result);
            OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            ElasticTripVO entity = OBJECT_MAPPER.readValue(result, ElasticTripVO.class);
            return entity;
        }
        catch (Exception ex) {
            LOGGER.config(ex.toString());
            throw ex;
        }
    }

	public ElasticTripVO insertEntity(Map<String, Object> data) throws Exception {
		LOGGER.config("Inserting a new trip...");		
        final String id = data.get("uniqueId").toString();
		
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
            OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            ElasticTripVO entity = null; //OBJECT_MAPPER.readValue(result, ElasticTripVO.class);
            return entity;
        }
        catch (Exception ex) {
            LOGGER.config(ex.toString());
            throw ex;
        }
    }
	
	public ElasticTripVO updateEntity(ElasticTripVO vo) throws Exception {
		LOGGER.config("Updating a new trip...");		
        Client client = ClientBuilder.newClient();
        Response response = client.target(BASE_URL) // update/index/{index}/type/{type}/id/{id}
                .path("update")
                .path("index")
                .path(ElasticTripVO.INDEX)
                .path("type")
                .path(ElasticTripVO.TYPE)
                .path("id")
                .path(vo.getUniqueId())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(vo, MediaType.APPLICATION_JSON));
        
        if (response.getStatus() != 200) {
            LOGGER.config(response.toString());
            throw new Exception(response.getStatusInfo().toString());
        }
        try {
            String result = response.readEntity(String.class);  System.out.println(result);          
            OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            ElasticTripVO entity = null; //OBJECT_MAPPER.readValue(result, ElasticTripVO.class);
            return entity;
        }
        catch (Exception ex) {
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
                .path(vo.getUniqueId())
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
	
	public void updateDB(@Observes ElasticTripEvent event) {
		Map<String, Object> data = MapObjectInstance.parameters(event.getObject());
		try {
			insertEntity(data);
		}
		catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Exception", ex);
		}
	}
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final String BASE_URL = "http://localhost:8082";
    private static final String CLASS_NAME = ElasticDB.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
}
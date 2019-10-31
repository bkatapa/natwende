package com.mweka.natwende.cdi.elastic;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

/**
 *
 * @author bellk
 */
@Named
@Singleton
public class ESUtils {
    
	@Transactional
    public long insertData(Map<String, Object> data, String index, String type, String id) throws Exception {   
        IndexRequest indexRequest = new IndexRequest(index, type, id)
            .source(data);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest);
            return response.getVersion();
        } catch(ElasticsearchException e) {
            LOGGER.log(Level.CONFIG, e.getDetailedMessage());
            throw e;
        } catch (java.io.IOException ex){
            LOGGER.log(Level.CONFIG, ex.getLocalizedMessage());
            throw ex;
        }
    }
    
    public String getDataById(String id, String index, String type) throws Exception {        
        try {
            GetRequest getPersonRequest = new GetRequest(index, type, id);
            GetResponse getResponse = restHighLevelClient.get(getPersonRequest);
            return getResponse.getSourceAsString();
        } catch (java.io.IOException e){
            LOGGER.log(Level.CONFIG, e.getLocalizedMessage());
            throw e;
        }
    }
    
    @Transactional
    public long updateDataById(String id, String index, String type, String jsonData) throws Exception {
        UpdateRequest updateRequest = new UpdateRequest(index, type, id)
            .fetchSource(true);    // Fetch json object after its update
        try {
            updateRequest.doc(jsonData, XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);
            return updateResponse.getVersion();
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.CONFIG, e.getLocalizedMessage());
            throw e;
        } catch (java.io.IOException e){
            LOGGER.log(Level.CONFIG, e.getLocalizedMessage());
            throw e;
        }
    }
    
    @Transactional
    public long deleteDataById(String id, String index, String type) throws Exception {
        DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
            return deleteResponse.getVersion();
        } catch (java.io.IOException e){
            LOGGER.log(Level.CONFIG, e.getLocalizedMessage());
            throw e;
        }
    }
 
    @PostConstruct
    public void makeConnection() {        
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(
            new HttpHost(HOST, PORT_ONE, SCHEME),
            new HttpHost(HOST, PORT_TWO, SCHEME)));
        LOGGER.log(Level.CONFIG, "Connection to elasticsearch instance established.");
    }
    
    @PreDestroy
    public void closeConnection() throws IOException {
        restHighLevelClient.close();
        restHighLevelClient = null;
    }

    private static final Logger LOGGER = Logger.getLogger(ESUtils.class.getName());

    //The config parameters for the connection
    private static final String HOST = "localhost";
    private static final int PORT_ONE = 9200;
    private static final int PORT_TWO = 9201;
    private static final String SCHEME = "http";
    
    private static RestHighLevelClient restHighLevelClient;       
}
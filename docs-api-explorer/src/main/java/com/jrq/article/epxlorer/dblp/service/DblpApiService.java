package com.jrq.article.epxlorer.dblp.service;

import com.jrq.article.epxlorer.dblp.exception.DblpExplorerException;
import com.jrq.article.epxlorer.dblp.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
@Slf4j
public class DblpApiService {

    private static final String DBLP_URL = "https://dblp.org/search/publ/api";

    private ObjectMapper objectMapper;

    DblpApiService(){
        configureObjectMapper();
    }

    public Result callDblp(String query) {
        try {
            return callHttp(query);
        } catch (DblpExplorerException e) {
            log.error("Cannot parse query: {}", query);
            return null;
        }
    }

    private Result callHttp(String query) throws DblpExplorerException {
        RestTemplate restTemplate = new RestTemplate();
        String fullResourceUrl = DBLP_URL + "?q=" + query + " type:Journal_Articles:" + "&format=json&h=1000";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fullResourceUrl, String.class);

        return mapResponseToResult(response);
    }

    private Result mapResponseToResult(ResponseEntity<String> response) throws DblpExplorerException {
        Result result = null;
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode resultNode = root.path("result");
            JsonNode totalNode = resultNode.path("hits").path("@total");
            result = objectMapper.readValue(resultNode, Result.class);
            result.getHits().setTotal(totalNode.asText());
            //log.info("The query " + result.getQuery() + " has returned " + result.getHits().getTotal() + " results");
        } catch (IOException e) {
            log.error("Error parsing response from DBLP:{}", e.getMessage(), e);
            throw new DblpExplorerException("Error parsing response from DBLP: " + e.getMessage());
        }
        return result;
    }

    private void configureObjectMapper() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }
}

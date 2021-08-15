package com.jrq.article.epxlorer.iexplore.service;

import com.jrq.article.epxlorer.iexplore.exception.IEeeXploreExplorerException;
import com.jrq.article.epxlorer.iexplore.model.Result;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
@Slf4j
public class IEEEEXploreApiService {

    @Value("${api.key}")
    @Setter
    private String apiKey;

    @Value( "${api.endpoint}" )
    @Setter
    private String apiEndpoint;

    private ObjectMapper objectMapper;

    IEEEEXploreApiService(){
        configureObjectMapper();
    }

    public Result callIeeeXplore(String query) {
        try {
            return callHttp(query);
        } catch (IEeeXploreExplorerException e) {
            log.error("Cannot parse query: {}", query);
            return null;
        }
    }

    private Result callHttp(String query) throws IEeeXploreExplorerException {
        RestTemplate restTemplate = new RestTemplate();
        String fullResourceUrl = apiEndpoint + "?article&" + query + "&apiKey=" + apiKey + "&max_records=200";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fullResourceUrl, String.class);

        return mapResponseToResult(query, response);
    }

    private Result mapResponseToResult(String query, ResponseEntity<String> response) throws IEeeXploreExplorerException {
        Result result = null;
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            result = objectMapper.readValue(root, Result.class);

            // log.info("The query " + query + " has returned " + result.getTotalRecords() + " results");
        } catch (IOException e) {
            log.error("Error parsing response from IeeeXplore:{}", e.getMessage(), e);
            throw new IEeeXploreExplorerException("Error parsing response from IeeeXplore: " + e.getMessage());
        }
        return result;
    }

    private void configureObjectMapper() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

}

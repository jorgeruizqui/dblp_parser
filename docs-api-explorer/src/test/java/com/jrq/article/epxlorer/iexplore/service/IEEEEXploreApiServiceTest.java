package com.jrq.article.epxlorer.iexplore.service;

import com.jrq.article.epxlorer.iexplore.model.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class IEEEEXploreApiServiceTest {

    private static final String API_ENDPOINT = "http://ieeexploreapi.ieee.org/api/v1/search/articles";
    private static final String API_KEY = "2quujqxvyzwpzztgkud8w2x6";
    @InjectMocks
    IEEEEXploreApiService service;

    @Test
    public void serviceMakeHttpRequestToDblp() {
        service.setApiEndpoint(API_ENDPOINT);
        service.setApiKey(API_KEY);

        Result result = service.callIeeeXplore("query");

        assertThat(result).isNotNull();
    }

}
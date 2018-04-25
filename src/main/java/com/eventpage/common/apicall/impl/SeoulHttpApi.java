package com.eventpage.common.apicall.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.http.client.ClientHttpRequestFactory;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.eventpage.common.apicall.RestHttpApi;
import com.eventpage.common.util.CommUtil;
import com.eventpage.controller.EventTestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import static com.eventpage.common.Constants.*;
/**
 * @Date : 2018. 4. 5. 
 * @author Kim jongseong
 * @Descrption : restApi client 관련
 */
@Repository
public class SeoulHttpApi implements RestHttpApi {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SeoulHttpApi.class);
    
    @Value("${api.seoul.key}")
    private String key;

    @Value("${api.seoul.url}")
    private String url;
    
    @Value("${api.seoul.requestSize}")
    private String requestSize;
    
    @Override
    public JsonNode getEventResult() {
        return getResult("SearchConcertDetailService", 1);
    }    
    
    @Override
    public JsonNode getEventResult(int pageNo) {
        return getResult("SearchConcertDetailService", pageNo);
    }

    @Override
    public JsonNode getEventSubjcodeResult() {
        return getResult("SearchConcertSubjectCatalogService", 1);
    }

    /**
     * @Date : 2018. 4. 5. 
     * @author Kim jongseong
     * @Descrption : 서울시 open 정보(rest) URL Call as client
     * @Overrided
     */
    @Override
    public JsonNode getResult(String ServiceName, int PageNo) {

        JsonNode feedback = null;

        RestTemplate restTemplate = getRestTemplate();

        try {
            String startNo = String.valueOf((Integer.valueOf(requestSize)) * (PageNo-1) + 1);
            String endNo = String.valueOf(Integer.valueOf(requestSize) * PageNo);
            
            String fUrl = url + "{key}/json/" + ServiceName + "/{startNo}/{endNo}";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

            HttpEntity<JsonNode> response = restTemplate.exchange(fUrl, HttpMethod.GET, requestEntity, JsonNode.class,
                    key, startNo, endNo);

            feedback = response.getBody();

            LOGGER.debug(CommUtil.jsonObjToPrettyString(feedback));

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            LOGGER.debug("e.getResponseBodyAsString() : " + e.getResponseBodyAsString());
            LOGGER.debug("e.getStatusCode() : " + e.getStatusCode());
            LOGGER.debug("e.getStatusText() : " + e.getStatusText());
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return feedback;

    }

    private RestTemplate getRestTemplate() {
        return new RestTemplateBuilder().setConnectTimeout(20000).setReadTimeout(20000).build();
    }
}

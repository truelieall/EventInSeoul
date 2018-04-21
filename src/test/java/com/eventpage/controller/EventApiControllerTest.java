/**
 * @Date : 2018. 4. 10. 
 * @author Kim jongseong
 * @Descrption : 
 */
package com.eventpage.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventpage.common.util.CommUtil;
import com.eventpage.entity.Seoulevent;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * @Date : 2018. 4. 10. 
 * @author Kim jongseong
 * @Descrption : 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class EventApiControllerTest {
    @LocalServerPort
    private int port;
    
    @Test
    public void getEventsCount() throws Exception {
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:" + this.port +  "/EventInSeoul/api/event";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
                
        Object[] events = restTemplate.getForObject(url, Object[].class); 
                
        CommUtil.printJsonObj(events);
        
        assertTrue(events.length > 0);        
        
    }
    
}

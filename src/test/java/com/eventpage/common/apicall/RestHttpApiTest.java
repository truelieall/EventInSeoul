/**
 * @Date : 2018. 4. 16. 
 * @author no.1
 * @Descrption : 
 */
package com.eventpage.common.apicall;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;

import static com.eventpage.common.Constants.SeoulApiConst;
/**
 * @Date : 2018. 4. 16. 
 * @author no.1
 * @Descrption : 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class RestHttpApiTest {
    @Autowired
    private RestHttpApi restHttpApi;
    
    @Test
    public void getEventResult() {
        
        JsonNode resultJson = restHttpApi.getEventResult();
        JsonNode Service = resultJson.get("SearchConcertDetailService");
        JsonNode result = Service.get("RESULT");
        
        SeoulApiConst.ApiResultStatus resultStatus = SeoulApiConst.getStatus(result.get("CODE").asText("")) ;        
        assertTrue(resultStatus.isOK());
    }
    
    @Test
    public void getEventSubjcodeResult() {
                
        JsonNode resultJson = restHttpApi.getEventSubjcodeResult();
        JsonNode Service = resultJson.get("SearchConcertSubjectCatalogService");
        JsonNode result = Service.get("RESULT");
        
        SeoulApiConst.ApiResultStatus resultStatus = SeoulApiConst.getStatus(result.get("CODE").asText("")) ;        
        assertTrue(resultStatus.isOK());        
    }    
    
    //Error Case Test
    @Test
    public void getResult() {
                        
        JsonNode resultJson = restHttpApi.getResult("XXX", 1);
        JsonNode result = resultJson.get("RESULT");
        
        SeoulApiConst.ApiResultStatus resultStatus = SeoulApiConst.getStatus(result.get("CODE").asText("")) ;        
        assertTrue(!resultStatus.isOK());            
    }        

}

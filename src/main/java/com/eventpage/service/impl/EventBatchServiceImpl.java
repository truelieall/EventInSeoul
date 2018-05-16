package com.eventpage.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventpage.common.apicall.RestHttpApi;
import com.eventpage.common.util.CommUtil;
import com.eventpage.dao.CommCodeCacheDAO;
import com.eventpage.dao.CommCodeDAO;
import com.eventpage.dao.EventCacheDAO;
import com.eventpage.dao.EventDAO;
import com.eventpage.entity.Commcode;
import com.eventpage.entity.Seoulevent;
import com.eventpage.service.CommService;
import com.eventpage.service.EventBatchService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.eventpage.common.Constants;
import com.eventpage.common.Constants.SeoulApiConst;
import com.eventpage.common.Constants.SeoulApiConst.ApiResultStatus;
/**
 * @Date : 2018. 4. 5. 
 * @author Kim jongseong
 * @Descrption : SeoulEvent BATCH(Schedule) Service
 */
@Service
public class EventBatchServiceImpl implements EventBatchService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EventBatchServiceImpl.class);
    
    @Autowired
    private CommCodeDAO commCodeDAO;

    @Autowired
    private CommCodeCacheDAO commCodeCacheDAO;

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private EventCacheDAO eventCacheDAO;

    @Autowired
    private RestHttpApi restHttpApi;

    @Autowired
    private CommService commService;

    @Scheduled(cron = "0 0 6 * * *")
    @Override
    @Transactional
    public void batchSeoulEvent() {        
        
        StringBuffer resultMessage = new StringBuffer();
        try {

            ApiResultStatus resultEventStatus = apiCallAndAddEvents();
            resultMessage.append(makeResultMessage("apiCallAndAddEvents", resultEventStatus));
                        
            ApiResultStatus resultSubjStatus = apiCallAndAddSubjcode();
            resultMessage.append(makeResultMessage("apiCallAndAddEvents", resultSubjStatus));
            
            commService.addBatchJobLog("SeoulEvent", "batchSeoulEvent", Constants.BatchStatus.OK.getCode(), resultMessage.toString(), "SYSTEM");

        } catch (Exception e) {
            commService.addBatchJobLog("SeoulEvent", "batchSeoulEvent"
                                      , Constants.BatchStatus.FAIL.getCode()
                                      , Constants.BatchStatus.FAIL.getValue() + ":" + CommUtil.getStackTraceString(e)
                                      , "SYSTEM");
            throw new RuntimeException(e);
        }

    }

    /**
     * @Date : 2018. 4. 13. 
     * @author Kim jongseong
     * @Descrption : 행사정보 restApi call / Seoulevent table & cache 갱신
     * @Overrided
     */
    @Override
    public ApiResultStatus apiCallAndAddEvents() {
        
        LOGGER.debug("apiCallAndAddEvents - Start");
        
        String today = CommUtil.getToday();

        int deleteCount = eventDAO.deleteEvents(today);
        
        if(deleteCount > 0) {
            eventCacheDAO.clearCache();
            eventCacheDAO.setCacheByEvents(today);
        }
        
        boolean moreEvents = true; 
        int callPageNo = 1;
        ApiResultStatus resultStatus = ApiResultStatus.UNDF;
        
        while (moreEvents) {
            
            JsonNode resultJson = restCallAndAddEvents(today, callPageNo);            
            JsonNode Service = resultJson.get("SearchConcertDetailService");
            JsonNode result;
            
            if(Service == null) result = resultJson.get("RESULT");
            else result = Service.get("RESULT");
                         
            resultStatus = SeoulApiConst.getStatus(result.get("CODE").asText("")) ;
            
            if(resultStatus.isNotFound()) moreEvents = false;
            
            if(callPageNo != 1 && resultStatus.isNotFound()) {
                resultStatus = ApiResultStatus.OK;
            }
            
            if(!resultStatus.isOK()) return resultStatus;
            
            callPageNo++;
        }

        eventCacheDAO.clearCache();
        eventCacheDAO.setCacheByEvents(today);

        LOGGER.debug("apiCallAndAddEvents - End");
        return resultStatus;

    }

    /**
     * @Date : 2018. 4. 25. 
     * @author Kim jongseong
     * @Descrption : 
     * @param today
     */
    private JsonNode restCallAndAddEvents(String today, int callPageNo) {
        
        JsonNode resultJson = restHttpApi.getEventResult(callPageNo);
        JsonNode Service = resultJson.get("SearchConcertDetailService");
        JsonNode result;
        
        if(Service == null) result = resultJson.get("RESULT");
        else result = Service.get("RESULT");
        
        ApiResultStatus resultStatus = SeoulApiConst.getStatus(result.get("CODE").asText("")) ;        
        
        if(!resultStatus.isOK()) return resultJson;
        
        JsonNode conList = Service.get("row");
   
        ObjectMapper objectMapper = new ObjectMapper();
   
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
   
        // 새로운 행사이거나, 정보변경이 확인될 경우만 Add 한다.        
        for (JsonNode conJson : conList) {
   
            Seoulevent event = objectMapper.convertValue(conJson, Seoulevent.class);
            event.setStrtdate(event.getStrtdate().replace("-", ""));
            event.setEnd_date(event.getEnd_date().replace("-", ""));
   
            Seoulevent legacyEvent = eventCacheDAO.getEventByCode(event.getCultcode());
            
            if (legacyEvent != null) {
                
                if (event.equals(legacyEvent)) continue;
                
            }
   
            event.setCreatedate(today);
            event.setFrstRegisterId("SYSTEM");
            event.setLastUpdusrId("SYSTEM");
            eventDAO.addEvent(event);
   
        }
        
        return resultJson;
        
    }

    /**
     * @Date : 2018. 4. 13. 
     * @author Kim jongseong
     * @Descrption : 행사종류코드(subjcode) restApi call / Commcode table & cache 갱신
     * @Overrided
     */
    @Override
    public ApiResultStatus apiCallAndAddSubjcode() {
        
        LOGGER.debug("apiCallAndAddSubjcode - Start");
        
        commCodeDAO.deleteCodes("SeoulEvent", "subjcode");

        JsonNode resultJson = restHttpApi.getEventSubjcodeResult();

        JsonNode Service = resultJson.get("SearchConcertSubjectCatalogService");
        JsonNode result = Service.get("RESULT");
        
        ApiResultStatus resultStatus = SeoulApiConst.getStatus(result.get("CODE").asText("")) ;        
        
        if(!resultStatus.isOK()) return resultStatus;
        
        JsonNode conList = Service.get("row");
        long listCount = Service.get("list_total_count").longValue();
        LOGGER.debug(">> LIST TOTAL COUNT :" + listCount + " - " + conList.size());

        for (JsonNode conJson : conList) {
            try {
                Commcode commcode = new Commcode();

                commcode.setFrstRegisterId("SYSTEM");
                commcode.setLastUpdusrId("SYSTEM");

                commcode.setCode_category("SeoulEvent");
                commcode.setCode_id("subjcode");

                commcode.setCode(String.valueOf(conJson.get("CODE").asInt()));
                commcode.setCodename(conJson.get("CODENAME").asText());

                commCodeDAO.addCode(commcode);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        commCodeCacheDAO.clearCache();
        commCodeCacheDAO.setCacheByCodes();
        
        LOGGER.debug("apiCallAndAddSubjcode - end");
        
        return resultStatus;
        
    }    

    private String makeResultMessage(String job, ApiResultStatus resultEventStatus) {

        return "[" + job + "-" + resultEventStatus.getCode() + ":" + resultEventStatus.getValue() + "]";
        
    }
    
}

package com.eventpage.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventpage.common.apicall.impl.SeoulHttpApi;
import com.eventpage.entity.Commcode;
import com.eventpage.service.CommService;

/**
 * @Date : 2018. 4. 5. 
 * @author Kim jongseong
 * @Descrption : 공통관련 Controller (rest)
 */
@RestController
@RequestMapping("/api")
public class CommApiController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CommApiController.class);
    
    @Autowired
    private CommService commService;      

    @GetMapping("commcode")
    public ResponseEntity<List<Commcode>> getAllCommCodes() {

        LOGGER.debug("getAllCommCodes ");

        List<Commcode> list = commService.getAllCodes();

        return new ResponseEntity<List<Commcode>>(list, HttpStatus.OK);

    }

    @GetMapping("commcode/{codeCategory}/{codeId}")
    public ResponseEntity<List<Commcode>> getCommCodes(@PathVariable("codeCategory") String codeCategory,
            @PathVariable("codeId") String codeId) {

        LOGGER.debug("getCommCodes : " + codeCategory + "   " + codeId);

        List<Commcode> list = commService.getCodes(codeCategory, codeId);

        return new ResponseEntity<List<Commcode>>(list, HttpStatus.OK);

    }

    @GetMapping("commcode/{codeCategory}/{codeId}/{code}")
    public ResponseEntity<Commcode> getCommCode( @PathVariable("codeCategory") String codeCategory
                                               , @PathVariable("codeId") String codeId
                                               , @PathVariable("code") String code) {

        LOGGER.debug("getCommCodes : " + codeCategory + "   " + codeId + "   " + code);

        return new ResponseEntity<Commcode>(commService.getCode(codeCategory, codeId, code), HttpStatus.OK);

    }
    
    @GetMapping("batch/{batch_id}/lastdate-{batch_job_name}")
    public ResponseEntity<String> getLastBatchProcDate( @PathVariable("batch_id") String batch_id
                                                       ,@PathVariable("batch_job_name") String batch_job_name) {
        
        return new ResponseEntity<String>(commService.getLastBatchProcDate(batch_id, batch_job_name), HttpStatus.OK);
    }
    

}
package com.eventpage.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.eventpage.common.util.CommUtil;
import com.eventpage.dao.EventCacheDAO;
import com.eventpage.dao.impl.TestCacheDAOImpl;
import com.eventpage.entity.Seoulevent;
import com.eventpage.entity.SeouleventPK;
import com.eventpage.entity.Seouleventmylist;
import com.eventpage.entity.Seouleventuser;
import com.eventpage.service.CommService;
import com.eventpage.service.EventBatchService;
import com.eventpage.service.EventService;

import com.eventpage.service.MylistService;
import com.eventpage.service.UserService;

/**
 * @Date : 2018. 4. 5. 
 * @author Kim jongseong
 * @Descrption : SeoulEvent Controller (rest) 테스트용
 */
@RestController
@RequestMapping("/test/api")
public class EventTestController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EventTestController.class);
    
    @Autowired
    private EventService eventService;

    @Autowired
    private TestCacheDAOImpl testCacheDAO;

    @Autowired
    private EventCacheDAO EventCacheDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private EventBatchService eventBatchService;
    
    @Autowired
    private CommService commService;    

    @GetMapping("events/test")
    public ResponseEntity<Void> getTest() {

        eventBatchService.batchSeoulEvent();

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("events/test2")
    public ResponseEntity<Void> getTest2() {

        eventBatchService.batchSeoulEvent();

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("events/testcache/{id}")
    public ResponseEntity<Seoulevent> getCache(@PathVariable("id") Integer id) {
        LOGGER.debug(Calendar.getInstance().getTime().toString());
        SeouleventPK key = new SeouleventPK();
        key.setCreatedate("20180318");
        key.setCultcode(id);

        Seoulevent event = testCacheDAO.getEventById(key);
        LOGGER.debug(Calendar.getInstance().getTime().toString());
        return new ResponseEntity<Seoulevent>(event, HttpStatus.OK);
    }

    @GetMapping("events/testdelcache")
    public ResponseEntity<Void> getDelCache() {

        testCacheDAO.clearCache();

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("events/testcacheprint")
    public ResponseEntity<Void> getPrintCache() {

        EventCacheDAO.printAllCache();

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("user/test")
    public ResponseEntity<Void> getUserTest(@CookieValue(value = "seoulEventUserId", defaultValue = "") String userId) {

        LOGGER.debug("getUserTest : " + userId);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("mylist/getTest")
    public ResponseEntity<List<Seoulevent>> getMylist() {

        List<Seoulevent> list = eventService.getEventsByMylist("test", 1);

        return new ResponseEntity<List<Seoulevent>>(list, HttpStatus.OK);
    }

    @GetMapping("user/testMylist/{userId}")
    public ResponseEntity<Seouleventmylist> getUserMylistTest(@PathVariable("userId") String userId) {
        LOGGER.debug("getUserMylistTest : " + userId);
        Seouleventuser user = userService.getUser(userId);

        LOGGER.debug("getUser Ok : " + user.getUser_type());

        List<Seouleventmylist> list = user.getMylist();

        LOGGER.debug("Seouleventmylist ");
        for (Seouleventmylist m : list)
            CommUtil.printJsonObj(m);

        return new ResponseEntity<Seouleventmylist>(list.get(0), HttpStatus.OK);
    }
    
    @GetMapping("batch/{batch_id}/lastdate-{batch_job_name}")
    public ResponseEntity<String> getLastBatchProcDate( @PathVariable("batch_id") String batch_id
                                                       ,@PathVariable("batch_job_name") String batch_job_name) {
        LOGGER.info("DEBUG test");
        return new ResponseEntity<String>(commService.getLastBatchProcDate(batch_id, batch_job_name), HttpStatus.OK);
    }

}
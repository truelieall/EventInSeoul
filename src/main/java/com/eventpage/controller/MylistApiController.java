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

import com.eventpage.entity.Seoulevent;
import com.eventpage.entity.Seouleventmylist;
import com.eventpage.entity.Seouleventuser;
import com.eventpage.service.EventService;

import com.eventpage.service.MylistService;
import com.eventpage.service.UserService;

/**
 * @Date : 2018. 4. 5. 
 * @author Kim jongseong
 * @Descrption : Mylist Controller (rest) 
 */
@RestController
@RequestMapping("/api/mylist")
@Transactional
public class MylistApiController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MylistApiController.class);
    
    @Autowired
    private EventService eventService;

    @Autowired
    private MylistService mylistService;

    @GetMapping
    public ResponseEntity<List<Seoulevent>> getMylist(
            @CookieValue(value = "seoulEventUserId", defaultValue = "") String userId,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo) {

        LOGGER.debug("getMylist : " + userId + ":" + pageNo);

        List<Seoulevent> list = eventService.getEventsByMylist(userId, pageNo);

        return new ResponseEntity<List<Seoulevent>>(list, HttpStatus.OK);

    }    
    
    @PostMapping("/{method}")
    public ResponseEntity<Void> addMylist(@CookieValue(value = "seoulEventUserId", defaultValue = "") String userId,
            @PathVariable("method") String method, @RequestBody Seouleventmylist mylist) {

        LOGGER.debug("addMylist");

        mylistService.handleMylist(mylist, method);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/exists")
    public ResponseEntity<Seouleventmylist> getMylistByCultcode(HttpServletRequest request,
            @CookieValue(value = "seoulEventUserId", defaultValue = "") String userId,
            @RequestParam(value = "cultcode", defaultValue = "0") Integer cultcode) {
        
        LOGGER.debug("getMylistByCultcode : " + userId);
        
        Seouleventmylist mylist = mylistService.getMylist(userId, cultcode);
        
        return new ResponseEntity<Seouleventmylist>(mylist, HttpStatus.OK);
    }
    
    

}
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

import com.eventpage.dto.EventSearchParameter;
import com.eventpage.entity.Seoulevent;
import com.eventpage.entity.Seouleventmylist;
import com.eventpage.entity.Seouleventuser;
import com.eventpage.service.EventService;

import com.eventpage.service.MylistService;
import com.eventpage.service.UserService;

/**
 * @Date : 2018. 4. 5. 
 * @author Kim jongseong
 * @Descrption : Event Controller (rest) 
 */
@RestController
@RequestMapping("/api/event")
@Transactional
public class EventApiController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EventApiController.class);
    
    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Seoulevent>> getEventList(
            @RequestParam(value = "subjcode",  defaultValue = "ALL") String subjcode,
            @RequestParam(value = "searchText", defaultValue = "") String searchText,
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "freeOnly", defaultValue = "false") boolean freeOnly) {

        LOGGER.debug("getEventList : " + subjcode + ":" + searchText + ":" + ":" + pageNo + ":" + freeOnly);
        
        List<Seoulevent> list = eventService.getEventList(new EventSearchParameter(subjcode, searchText, pageNo, freeOnly));

        return new ResponseEntity<List<Seoulevent>>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seoulevent> getEventById(@PathVariable("id") Integer id) {
        LOGGER.debug("getEventById");
        Seoulevent event = eventService.getEventById(id);
        return new ResponseEntity<Seoulevent>(event, HttpStatus.OK);
    }    
    
    @PutMapping
    public ResponseEntity<Seoulevent> updateEvent(@RequestBody Seoulevent event) {
     // TODO Auto-generated method stub
        return new ResponseEntity<Seoulevent>(event, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") Integer id) {
        // TODO Auto-generated method stub
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
    
}
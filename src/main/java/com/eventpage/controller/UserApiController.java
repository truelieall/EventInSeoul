package com.eventpage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventpage.entity.Seouleventuser;
import com.eventpage.service.UserService;

/**
 * @Date : 2018. 4. 5. 
 * @author Kim jongseong
 * @Descrption : User Controller (rest) 
 */
@RestController
@RequestMapping("/api/user")
@Transactional
public class UserApiController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(UserApiController.class);
    
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody Seouleventuser user) {

        LOGGER.debug("createUser");

        return new ResponseEntity<String>(userService.addNewUser(user), HttpStatus.OK);
    }
    
}
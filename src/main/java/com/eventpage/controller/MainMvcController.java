package com.eventpage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Date : 2018. 4. 5. 
 * @author Kim jongseong
 * @Descrption : SeoulEvent Controller (MVC) 
 */
@Controller
public class MainMvcController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MainMvcController.class);
    
    @GetMapping("/")
    public String greeting() {
        LOGGER.debug("greeting");
        return "main";

    }

    @GetMapping("/event")
    public String searchEvents(@RequestParam(value = "subjcode", defaultValue = "ALL") String subjcode,
            @RequestParam(value = "searchText") String searchText, @RequestParam(value = "freeOnly") boolean freeOnly,
            Model model) {

        LOGGER.debug("View Select searchSubjAndText : " + subjcode + ":" + searchText + ":" + freeOnly);

        model.addAttribute("subjcode", subjcode);
        model.addAttribute("searchText", searchText);
        model.addAttribute("freeOnly", freeOnly);
        
        return "main";
    }

    @GetMapping("/event/{id}")
    public String eventDetail(@PathVariable("id") Integer cultcode, Model model) {
        LOGGER.debug("View Select eventDetail : " + cultcode);

        model.addAttribute("cultcode", cultcode);

        return "eventDetail";

    }

    @GetMapping("/mylist")
    public String mylist(@CookieValue(value = "seoulEventUserId", defaultValue = "") String userId, Model model) {
        LOGGER.debug("View mylist : " + userId);
        
        model.addAttribute("userId", userId);
        
        return "mylist";
    }

}
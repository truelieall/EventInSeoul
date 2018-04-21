package com.eventpage.dao;

import java.util.List;
import java.util.Map;

import com.eventpage.dto.EventSearchParameter;
import com.eventpage.entity.Seoulevent;;

public interface EventCacheDAO {    

    Map<Integer, Seoulevent> getAllEventsMap();

    Seoulevent getEventByCode(int cultcode);

    List<Seoulevent> getEventsByPageNo(int pageNo);   
    
    List<Seoulevent> getCacheEventsBySearchText(EventSearchParameter searchParam);
    
    void setCacheByEvents(String stDate);

    void clearCache();

    void printAllCache();
}

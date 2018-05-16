package com.eventpage.dao;

import java.util.List;

import com.eventpage.entity.Seoulevent;;

public interface EventDAO {
    
    List<Seoulevent> getAllEvents();

    Seoulevent getEventByCode(int cultcode);

    void addEvent(Seoulevent event);

    void updateEvent(Seoulevent event);

    void deleteEvent(String createdate, int cultCode);

    int deleteEvents(String createdate);
    
    List<Seoulevent> getAllCurrentEventsList(String stndDate);
    
    List<Seoulevent> getEventsBySearchText(String subjcode, String searchText, int pageNo, boolean freeOnly);

}
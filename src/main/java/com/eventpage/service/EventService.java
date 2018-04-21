package com.eventpage.service;

import java.util.List;

import com.eventpage.dto.EventSearchParameter;
import com.eventpage.entity.Seoulevent;

public interface EventService {
    List<Seoulevent> getAllEvents();

    Seoulevent getEventById(int cultCode);

    List<Seoulevent> getEventList(EventSearchParameter searchParam);

    boolean addEvent(Seoulevent event);

    void updateEvent(Seoulevent event);

    void deleteEvent(String createdate, int cultCode);

    List<Seoulevent> getEventsByMylist(String userId, int pageNo);
}

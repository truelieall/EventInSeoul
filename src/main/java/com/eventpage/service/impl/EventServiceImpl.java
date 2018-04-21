package com.eventpage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventpage.common.util.CommUtil;
import com.eventpage.dao.EventCacheDAO;
import com.eventpage.dao.EventDAO;
import com.eventpage.dto.EventSearchParameter;
import com.eventpage.entity.Seoulevent;
import com.eventpage.entity.Seouleventmylist;
import com.eventpage.service.EventService;
import com.eventpage.service.MylistService;

/**
 * @Date : 2018. 4. 5. 
 * @author Kim jongseong
 * @Descrption : SeoulEvent관련 Service (주로 Seoulevent entity 관련)
 */
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private EventCacheDAO eventCacheDAO;

    @Autowired
    private MylistService mylistService;

    @Override
    public Seoulevent getEventById(int cultCode) {
        Seoulevent obj = eventCacheDAO.getEventByCode(cultCode);
        ;
        return obj;
    }

    @Override
    public List<Seoulevent> getEventList(EventSearchParameter searchParam) {
                
        List<Seoulevent> eventList = null;
        
        if(searchParam.isNoConditionSearch()){            
            eventList = eventCacheDAO.getEventsByPageNo(searchParam.getPageNo());
        }else{        
            eventList = eventCacheDAO.getCacheEventsBySearchText(searchParam);
        }
        
        return eventList;
    }

    @Override
    public List<Seoulevent> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    @Override
    public synchronized boolean addEvent(Seoulevent event) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void updateEvent(Seoulevent event) {
        eventDAO.updateEvent(event);
    }

    @Override
    public void deleteEvent(String createdate, int cultcode) {
        eventDAO.deleteEvent(createdate, cultcode);
    }

    @Override
    public List<Seoulevent> getEventsByMylist(String userId, int pageNo) {

        if (CommUtil.isEmpty(userId)) {
            return null;
        }

        List<Seouleventmylist> mylists = mylistService.getMylists(userId, pageNo);
        List<Seoulevent> myEventlists = new ArrayList<Seoulevent>();

        for (Seouleventmylist mylist : mylists) {
            myEventlists.add(eventCacheDAO.getEventByCode(mylist.getCultcode()));
        }

        return myEventlists;
    }
}

/**
 * @Date : 2018. 4. 10. 
 * @author Kim jongseong
 * @Descrption : 
 */
package com.eventpage.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.eventpage.common.util.CommUtil;
import com.eventpage.dto.EventSearchParameter;
import com.eventpage.entity.Seoulevent;
/**
 * @Date : 2018. 4. 10. 
 * @author Kim jongseong
 * @Descrption : 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class EventCacheDAOTest {
    
    @Autowired
    private EventCacheDAO eventCacheDAO;
    
    @Autowired
    private EventDAO eventDAO;
    
    @Test
    public void getEventByCode(){        
        
        List<Seoulevent> allEventList = eventDAO.getAllCurrentEventsList(CommUtil.getToday());         
                
        Seoulevent event = eventCacheDAO.getEventByCode(allEventList.get(0).getCultcode());
        
        assertNotNull(event);
        assertTrue(event.getCultcode() == allEventList.get(0).getCultcode());         
    
    }    
        
    @Test
    public void getCacheEventsBySearchText(){        
        
        List<Seoulevent> allEventListCache = eventCacheDAO.getCacheEventsBySearchText( new EventSearchParameter("ALL", "", 1, false));        
        System.out.println("=getCacheEventsBySearchText=");
        CommUtil.printJsonObj(allEventListCache);
        
    }       
    
}

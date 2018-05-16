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
import com.eventpage.entity.Seoulevent;
/**
 * @Date : 2018. 4. 10. 
 * @author Kim jongseong
 * @Descrption : 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class EventDAOTest {
    
    @Autowired
    private EventCacheDAO eventCacheDAO;
    
    @Autowired
    private EventDAO eventDAO;
    
    @Test
    public void getAllEventsList(){        
        
        List<Seoulevent> allEventList = eventDAO.getAllCurrentEventsList(CommUtil.getToday());
        
        assertNotNull(allEventList);
        assertTrue(allEventList.size() > 0);             
    }    


    @Test
    public void getEventsBySearchText(){        
        List<Seoulevent> allEventList = eventDAO.getEventsBySearchText("ALL", "서초구", 1, false);         
        System.out.println("=getEventsBySearchText=");
        CommUtil.printJsonObj(allEventList);
    }    
    
}

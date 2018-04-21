/**
 * @Date : 2018. 4. 12. 
 * @author Kim jongseong
 * @Descrption : 
 */
package com.eventpage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.annotation.DirtiesContext;

import com.eventpage.common.apicall.RestHttpApiTest;
import com.eventpage.controller.EventApiControllerTest;
import com.eventpage.controller.SeoulEventControllerTest;
import com.eventpage.dao.EventCacheDAOSearchTest;
import com.eventpage.dao.EventCacheDAOTest;
import com.eventpage.dao.EventDAOTest;
import com.eventpage.service.impl.EventBatchServiceImplTest;

/**
 * @Date : 2018. 4. 12. 
 * @author Kim jongseong
 * @Descrption : 
 */
@RunWith(Suite.class)
@SuiteClasses({
       EventDAOTest.class    
    ,  RestHttpApiTest.class       
    ,  EventApiControllerTest.class
    ,  EventCacheDAOSearchTest.class
    ,  EventCacheDAOTest.class
    ,  EventDAOTest.class
    ,  EventBatchServiceImplTest.class
//    ,  SeoulEventControllerTest.class    
    })
public class SeoulEventAllTest {

    
}

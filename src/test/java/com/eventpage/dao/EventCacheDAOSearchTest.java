/**
 * @Date : 2018. 4. 10. 
 * @author Kim jongseong
 * @Descrption : 
 */
package com.eventpage.dao;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.eventpage.MainApplication;
import com.eventpage.dto.EventSearchParameter;
import com.eventpage.entity.Seoulevent;
/**
 * @Date : 2018. 4. 10. 
 * @author Kim jongseong
 * @Descrption : 
 */

@RunWith(Parameterized.class)
//@ContextConfiguration(classes=MainApplication.class)  // specify context config
@SpringBootTest  // specify context config
@DirtiesContext
public class EventCacheDAOSearchTest {
  
    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();       
    
    private String subjcode;
    private String searchText;
    private int pageNo;
    private boolean freeOnly;
    
    @Autowired
    private EventCacheDAO eventCacheDAO;
    
    @Autowired
    private EventDAO eventDAO;
    
    public EventCacheDAOSearchTest(String subjcode, String searchText, int pageNo, boolean freeOnly) {
        this.subjcode = subjcode;
        this.searchText = searchText;
        this.pageNo = pageNo;
        this.freeOnly = freeOnly;
     }
    
    
    @Parameterized.Parameters
    public static List parametersForSearch() {
       return Arrays.asList(new Object[][] {
           {"ALL","서초구", 1, false }
          ,{"ALL","서초구", 2, false }
          ,{"ALL","서초구", 3, false }
          ,{"ALL","서초구", 4, false }
          ,{"ALL","서초구", 5, false }

          ,{"ALL","서초구", 1, true  }
          ,{"ALL","서초구", 2, true  }
          ,{"ALL","서초구", 3, true  }
          ,{"ALL","서초구", 4, true  }
          ,{"ALL","서초구", 5, true  }

          ,{"ALL","연주회", 1, false  }
          ,{"ALL","연주회", 2, false  }
          ,{"ALL","연주회", 3, false  }
          ,{"ALL","연주회", 4, false  }
          ,{"ALL","연주회", 5, false  }
          
          ,{"ALL","바이올린", 1, false }
          ,{"ALL","바이올린", 2, false }
          ,{"ALL","바이올린", 3, false }
          ,{"ALL","바이올린", 4, false }
          ,{"ALL","바이올린", 5, false }          
          
          ,{"1","강남", 1, true  }
          ,{"1","강남", 2, true  }
          ,{"1","강남", 3, true  }
          ,{"1","강남", 4, true  }
          ,{"1","강남", 5, true  }
          
          ,{"18","", 1, true  }
          ,{"18","", 2, true  }
          ,{"18","", 3, true  }
          ,{"18","", 4, true  }
          ,{"18","", 5, true  }
          
          ,{"19","교육", 1, true  }
          ,{"19","교육", 2, true  }
          ,{"19","교육", 3, true  }
          ,{"19","교육", 4, true  }
          ,{"19","교육", 5, true  }            
       });
    }
    
    
    //SQL SearchText, EhCache Search간 결과비교
    @Test
    public void compareSearchTextMethod(){        
        EventSearchParameter searchParam = new EventSearchParameter(this.subjcode, this.searchText, this.pageNo, this.freeOnly);
        
        List<Seoulevent> allEventList = eventDAO.getEventsBySearchText(this.subjcode, this.searchText, this.pageNo, this.freeOnly);                 
        List<Seoulevent> allEventListCache = eventCacheDAO.getCacheEventsBySearchText(searchParam);

        System.out.println("subjcode   :" + subjcode  ); 
        System.out.println("searchText :" + searchText);
        System.out.println("pageNo     :" + pageNo    );
        System.out.println("freeOnly   :" + freeOnly  );        
        
        System.out.println("compareSearchTextMethod=" + allEventList.size() +" ::::: " +  allEventListCache.size());
        assertTrue(allEventList.size() == allEventListCache.size());   
    
    }
    
}

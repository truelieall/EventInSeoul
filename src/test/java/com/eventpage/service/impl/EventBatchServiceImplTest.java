/**
 * @Date : 2018. 4. 12. 
 * @author Kim jongseong
 * @Descrption : 
 */
package com.eventpage.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.eventpage.service.CommService;
import com.eventpage.service.EventBatchService;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

/**
 * @Date : 2018. 4. 12. 
 * @author Kim jongseong
 * @Descrption : 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class EventBatchServiceImplTest {
    
    @Autowired
    private EventBatchService eventBatchService;
    
    @MockBean  //BatchJob log 적재 회피를 위한 MockBean사용
    private CommService commService;
    
    @Test
    @Transactional
    @Rollback(true)
    public void batchSeoulEventTest() {
                        
        eventBatchService.batchSeoulEvent();
        
        //Batch 정상 종료 작업 로그가 호출됐었는지 확인한다.
        //참고 : verify - Matcher(eq, anyString())등 사용할 경우 전체 Arg가 Matcher로 구성되어야 한다.
        verify(commService).addBatchJobLog(eq("SeoulEvent"), eq("batchSeoulEvent"), eq("0000"), anyString() , eq("SYSTEM"));
        
    }

}

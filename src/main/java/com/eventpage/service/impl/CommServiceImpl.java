package com.eventpage.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.eventpage.dao.CommCodeCacheDAO;
import com.eventpage.dao.CommCodeDAO;
import com.eventpage.common.util.CommUtil;
import com.eventpage.controller.MainMvcController;
import com.eventpage.dao.BatchJobLogDAO;

import com.eventpage.entity.Batchjoblog;
import com.eventpage.entity.Commcode;
import com.eventpage.service.CommService;

/**
 * @Date : 2018. 4. 5. 
 * @author Kim jongseong
 * @Descrption : 공통 Service 
 */
@Service
public class CommServiceImpl implements CommService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommServiceImpl.class);
    @Autowired
    private CommCodeCacheDAO commCodeCacheDAO;

    @Autowired
    private CommCodeDAO commCodeDAO;

    @Autowired
    private BatchJobLogDAO batchJobLogDAO;

    @Override
    public List<Commcode> getAllCodes() {
        return commCodeDAO.getAllCodes();
    }

    @Override
    public Commcode getCode(String code_category, String code_id, String code) {
        return commCodeCacheDAO.getCommcode(code_category, code_id, code);
    }

    @Override
    public List<Commcode> getCodes(String code_category, String code_id) {
        return commCodeCacheDAO.getCommcodeList(code_category, code_id);

    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addBatchJobLog(String batch_id, String batch_job_name, String result_code, String result_desc,
            String regId) {
        
        try {
            
            Batchjoblog batchjoblog = new Batchjoblog();

            batchjoblog.setBatch_id(batch_id);
            batchjoblog.setBatch_date(CommUtil.getToday());
            batchjoblog.setBatch_job_name(batch_job_name);
            batchjoblog.setResult_code(result_code);
            batchjoblog.setFrstRegisterId(regId);
            batchjoblog.setLastUpdusrId(regId);
            
            if (!CommUtil.isEmpty(result_desc)) {
                byte[] bystStr = result_desc.getBytes();
                if (bystStr.length > 1000)
                    batchjoblog.setResult_desc(new String(bystStr, 0, 1000));
                else
                    batchjoblog.setResult_desc(result_desc);
            }

            batchJobLogDAO.addBatchJobLog(batchjoblog);
            
        } catch (Exception e) {
            LOGGER.debug("ERROR WHEN BATCHJOBLOG INS");
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }
    
    @Override
    public String getLastBatchProcDate(String batch_id, String batch_job_name) {
        
        return batchJobLogDAO.getLastBatchProcDate(batch_id, batch_job_name);
        
    } 
    
    @Override
    public boolean addCode(Commcode code) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void updateCode(Commcode code) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteCode(String code_category, String code_id, String code) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteCodes(String code_category, String code_id) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addBatchJobLog(Batchjoblog batchJobLog) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateBatchJobLog(Batchjoblog batchJobLog) {
        // TODO Auto-generated method stub

    }
    
   
}
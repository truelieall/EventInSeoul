package com.eventpage.service;

import java.util.List;

import com.eventpage.entity.Batchjoblog;
import com.eventpage.entity.Commcode;

public interface CommService {
    List<Commcode> getAllCodes();

    Commcode getCode(String code_category, String code_id, String code);

    List<Commcode> getCodes(String code_category, String code_id);

    boolean addCode(Commcode code);

    void updateCode(Commcode code);

    void deleteCode(String code_category, String code_id, String code);

    void deleteCodes(String code_category, String code_id);


    void addBatchJobLog(Batchjoblog batchJobLog);

    void addBatchJobLog(String batch_id, String batch_job_name, String result_code, String result_desc, String regId);

    void updateBatchJobLog(Batchjoblog batchJobLog);
    
    String getLastBatchProcDate(String batch_id, String batch_job_name);
    
    
}

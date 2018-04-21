package com.eventpage.dao;

import java.util.List;

import com.eventpage.entity.Batchjoblog;;

public interface BatchJobLogDAO {

    List<Batchjoblog> getAllLogs();

    Batchjoblog getBatchJobLog(int batch_job_seq);

    Batchjoblog getBatchJobLog(String batch_id, String batch_date, String batch_job_name);

    List<Batchjoblog> getBatchJobLogs(String batch_id, String batch_date);

    void addBatchJobLog(Batchjoblog batchJobLog);

    void updateBatchJobLog(Batchjoblog batchJobLog);

    void deleteBatchJobLog(Batchjoblog batchJobLog);

    void deleteLists();
    
    String getLastBatchProcDate(String batch_id, String batch_job_name);

}
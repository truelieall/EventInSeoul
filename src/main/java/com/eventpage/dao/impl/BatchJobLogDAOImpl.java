package com.eventpage.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.eventpage.common.Constants;
import com.eventpage.common.util.CommUtil;
import com.eventpage.dao.BatchJobLogDAO;
import com.eventpage.entity.Batchjoblog;
import com.eventpage.entity.Seoulevent;;

@Repository
public class BatchJobLogDAOImpl implements BatchJobLogDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Batchjoblog> getAllLogs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Batchjoblog getBatchJobLog(int batch_job_seq) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Batchjoblog getBatchJobLog(String batch_id, String batch_date, String batch_job_name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Batchjoblog> getBatchJobLogs(String batch_id, String batch_date) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addBatchJobLog(Batchjoblog batchJobLog) {
        // TODO Auto-generated method stub
        entityManager.persist(batchJobLog);
    }

    @Override
    public void updateBatchJobLog(Batchjoblog batchJobLog) {

        entityManager.flush();

    }

    @Override
    public void deleteBatchJobLog(Batchjoblog batchJobLog) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteLists() {
        // TODO Auto-generated method stub

    }
    
    @Override
    @Cacheable(cacheNames = "Batchdate")    
    public String getLastBatchProcDate(String batch_id, String batch_job_name) {
        
        StringBuffer hql = new StringBuffer();
        hql.append("select max(batch_date) as batch_date from Batchjoblog as b ");
        hql.append(" where b.batch_id = :batch_id                              ");
        hql.append("   and b.batch_job_name = :batch_job_name                  ");
        hql.append("   and b.result_code    = :batchStatusOK                   ");
        
        return  (String) entityManager.createQuery(hql.toString())
                        .setParameter("batch_id", batch_id)
                        .setParameter("batch_job_name", batch_job_name)
                        .setParameter("batchStatusOK" , Constants.BatchStatus.OK.getCode())
                        .getSingleResult();
    }    
}

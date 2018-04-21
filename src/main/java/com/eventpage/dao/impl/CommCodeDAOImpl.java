package com.eventpage.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eventpage.dao.CommCodeDAO;
import com.eventpage.entity.Commcode;
import com.eventpage.entity.Seoulevent;

@Repository
public class CommCodeDAOImpl implements CommCodeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Commcode> getAllCodes() {
        Query query = entityManager.createNamedQuery("Commcode.findAll", Commcode.class);
        return query.getResultList();
    }

    @Override
    public void addCode(Commcode commcode) {
        entityManager.persist(commcode);
    }

    @Override
    public void updateCode(Commcode commcode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteCodes(String code_category) {

    }

    @Override
    public void deleteCodes(String code_category, String code_id) {
        // TODO Auto-generated method stub
        String hql = "delete FROM Commcode as code WHERE code.code_category = ? and code.code_id = ? ";

        entityManager.createQuery(hql).setParameter(1, code_category).setParameter(2, code_id).executeUpdate();
    }

    @Override
    public void deleteCode(Commcode commcode) {
        // TODO Auto-generated method stub

    }

}

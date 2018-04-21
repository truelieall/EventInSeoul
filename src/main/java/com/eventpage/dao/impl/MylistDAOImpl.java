package com.eventpage.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eventpage.dao.MylistDAO;
import com.eventpage.entity.Commcode;
import com.eventpage.entity.Seoulevent;
import com.eventpage.entity.Seouleventmylist;
import com.eventpage.entity.Seouleventuser;

@Repository
public class MylistDAOImpl implements MylistDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Seouleventmylist> getAllList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Seouleventmylist getMyList(int list_id) {
        return entityManager.find(Seouleventmylist.class, list_id);
    }

    @Override
    public Seouleventmylist getMyList(String user_id, int cultcode) {
        // TODO Auto-generated method stub
        String hql = "FROM Seouleventmylist as mylist where mylist.user_id = ? and mylist.cultcode = ?";

        Seouleventmylist result;
        try {
            result = (Seouleventmylist) entityManager.createQuery(hql).setParameter(1, user_id)
                    .setParameter(2, cultcode).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        return result;
    }

    @Override
    public List<Seouleventmylist> getMyLists(String user_id, int pageNo) {
        
        String hql = "FROM Seouleventmylist as mylist where mylist.user_id = ? order by mylist.list_no";

        Query query = (Query) entityManager.createQuery(hql.toString()).setParameter(1, user_id);

        query.setFirstResult((pageNo - 1) * 20);
        query.setMaxResults(20);

        return (List<Seouleventmylist>) query.getResultList();
    }

    @Override
    public void addMylist(Seouleventmylist myList) {
        entityManager.persist(myList);
    }

    @Override
    public void updateMylist(Seouleventmylist myList) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteMylist(Seouleventmylist myList) {
        entityManager.remove(myList);
    }

    @Override
    public void deleteLists() {
        // TODO Auto-generated method stub

    }

}

package com.eventpage.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eventpage.dao.UserDAO;
import com.eventpage.entity.Commcode;
import com.eventpage.entity.Seoulevent;
import com.eventpage.entity.Seouleventmylist;
import com.eventpage.entity.Seouleventuser;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Seouleventuser> getAllUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Seouleventuser getUser(String user_id) {

        return entityManager.find(Seouleventuser.class, user_id);
    }

    @Override
    public void addUser(Seouleventuser user) {
        entityManager.persist(user);

    }

    @Override
    public void updateUser(Seouleventuser user) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteUser(Seouleventuser user) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteUsers() {
        // TODO Auto-generated method stub

    }

}

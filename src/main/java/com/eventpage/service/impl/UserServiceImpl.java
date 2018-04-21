package com.eventpage.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventpage.dao.UserDAO;
import com.eventpage.entity.Seouleventuser;
import com.eventpage.service.UserService;

/**
 * @Date : 2018. 4. 5. 
 * @author Kim jongseong
 * @Descrption : User 관련 Service
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public List<Seouleventuser> getAllUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Seouleventuser getUser(String user_id) {

        return userDAO.getUser(user_id);
    }

    @Override
    @Transactional
    public String addNewUser(Seouleventuser user) {
        if ("tempUser".equals(user.getUser_type())) {
            user.setUser_id(createTempUserId());
        }

        user.setFrstRegisterId(user.getUser_id());
        user.setLastUpdusrId(user.getUser_id());

        addUser(user);

        return user.getUser_id();

    }

    @Override
    public boolean addUser(Seouleventuser user) {
        userDAO.addUser(user);
        return true;
    }

    @Override
    public void updateUser(Seouleventuser user) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteUser(Seouleventuser user) {
        // TODO Auto-generated method stub

    }

    private String createTempUserId() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return "tmpusr_" + String.valueOf(timestamp.getTime());
    }
}

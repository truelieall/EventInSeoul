package com.eventpage.dao;

import java.util.List;

import com.eventpage.entity.Seouleventuser;

public interface UserDAO {
    
    List<Seouleventuser> getAllUsers();

    Seouleventuser getUser(String user_id);

    void addUser(Seouleventuser user);

    void updateUser(Seouleventuser user);

    void deleteUser(Seouleventuser user);

    void deleteUsers();

}
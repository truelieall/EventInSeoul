package com.eventpage.service;

import java.util.List;

import com.eventpage.entity.Seouleventuser;

public interface UserService {
    List<Seouleventuser> getAllUsers();

    Seouleventuser getUser(String user_id);

    String addNewUser(Seouleventuser user);

    boolean addUser(Seouleventuser user);

    void updateUser(Seouleventuser user);

    void deleteUser(Seouleventuser user);
}
package com.eventpage.dao;

import java.util.List;

import com.eventpage.entity.Seouleventmylist;

public interface MylistDAO {
    
    List<Seouleventmylist> getAllList();

    Seouleventmylist getMyList(int list_id);

    Seouleventmylist getMyList(String user_id, int cultcode);

    List<Seouleventmylist> getMyLists(String user_id, int pageNo);

    void addMylist(Seouleventmylist myList);

    void updateMylist(Seouleventmylist myList);

    void deleteMylist(Seouleventmylist myList);

    void deleteLists();

}
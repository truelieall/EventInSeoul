package com.eventpage.service;

import java.util.List;

import com.eventpage.entity.Seouleventmylist;

public interface MylistService {
    List<Seouleventmylist> getAllMylists();

    Seouleventmylist getMylist(int list_id);

    Seouleventmylist getMylist(String user_id, int cultCode);

    List<Seouleventmylist> getMylists(String user_id, int pageNo);

    void handleMylist(Seouleventmylist myList, String method);

    void addMylist(Seouleventmylist myList);

    void updateMylist(Seouleventmylist myList);

    void deleteMylist(Seouleventmylist myList);
}
package com.eventpage.dao;

import java.util.List;

import com.eventpage.entity.Commcode;
import com.eventpage.entity.Seoulevent;;

public interface CommCodeDAO {
    
    List<Commcode> getAllCodes();

    void addCode(Commcode commcode);

    void updateCode(Commcode commcode);

    void deleteCodes(String code_category);

    void deleteCodes(String code_category, String code_id);

    void deleteCode(Commcode commcode);

}
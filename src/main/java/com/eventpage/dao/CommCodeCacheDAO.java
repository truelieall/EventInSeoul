package com.eventpage.dao;

import java.util.List;
import java.util.Map;

import com.eventpage.entity.Commcode;;

public interface CommCodeCacheDAO {
    
    Commcode getCommcode(String code_category, String code_id, String code);

    List<Commcode> getCommcodeList(String code_category, String code_id);

    void setCacheByCodes();

    void clearCache();
}

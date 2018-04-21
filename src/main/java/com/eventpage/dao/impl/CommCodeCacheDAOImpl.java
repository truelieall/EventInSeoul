package com.eventpage.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eventpage.common.util.CommUtil;
import com.eventpage.dao.CommCodeCacheDAO;
import com.eventpage.entity.Commcode;
import com.eventpage.entity.Seoulevent;

@Repository
public class CommCodeCacheDAOImpl implements CommCodeCacheDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CommCodeDAOImpl commCodeDAO;

    @Autowired
    private EhCacheCacheManager ehCacheManager;

    @PostConstruct
    public void initCommcodeCacheDAO() {
        setCacheByCodes();
    }

    @Override
    @Cacheable(cacheNames = "Commcode")
    public List<Commcode> getCommcodeList(String code_category, String code_id) {
        return null;
    }

    @Override
    @Cacheable(cacheNames = "Commcode")
    public Commcode getCommcode(String code_category, String code_id, String code) {
        return null;
    }

    @Override
    @CacheEvict(cacheNames = "Commcode", allEntries = true)
    public void clearCache() {
    }

    @Override
    public void setCacheByCodes() {
        List<Commcode> allCodes = commCodeDAO.getAllCodes();

        Cache ehCacheAllCode = ehCacheManager.getCache("Commcode");

        Map<SimpleKey, List<Commcode>> targetCodeGroup = new HashMap<SimpleKey, List<Commcode>>();

        for (Commcode code : allCodes) {
            SimpleKey codeKey = new SimpleKey(code.getCode_category(), code.getCode_id(), code.getCode());
            ehCacheAllCode.put(codeKey, code);

            SimpleKey codeGroupKey = new SimpleKey(code.getCode_category(), code.getCode_id());

            if (targetCodeGroup.get(codeGroupKey) != null)
                targetCodeGroup.get(codeGroupKey).add(code);
            else {
                ArrayList<Commcode> list = new ArrayList<Commcode>();
                list.add(code);
                targetCodeGroup.put(codeGroupKey, list);
            }
        }

        Iterator<SimpleKey> keys = targetCodeGroup.keySet().iterator();
        while (keys.hasNext()) {
            SimpleKey key = keys.next();
            ehCacheAllCode.put(key, targetCodeGroup.get(key));
        }

    }

}

package com.eventpage.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.eventpage.entity.Seoulevent;
import com.eventpage.entity.SeouleventPK;

@Repository
@CacheConfig(cacheNames = "SeoulEvent")
public class TestCacheDAOImpl {
    @PersistenceContext
    private EntityManager entityManager;

    @CacheEvict(allEntries = true)
    public void clearCache() {
    }

    @Cacheable
    public Seoulevent getEventById(SeouleventPK id) {
        simulateSlowService();
        return entityManager.find(Seoulevent.class, id);
    }

    // Don't do this at home
    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}

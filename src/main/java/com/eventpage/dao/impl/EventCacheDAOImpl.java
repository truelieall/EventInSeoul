package com.eventpage.dao.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Repository;

import com.eventpage.common.util.CommUtil;
import com.eventpage.controller.MainMvcController;
import com.eventpage.dao.EventCacheDAO;
import com.eventpage.dao.EventDAO;
import com.eventpage.dto.EventSearchParameter;
import com.eventpage.entity.Seoulevent;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.management.CacheStatistics;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Direction;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import net.sf.ehcache.search.expression.Criteria;
import net.sf.ehcache.search.expression.Or;

@Repository
public class EventCacheDAOImpl implements EventCacheDAO {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EventCacheDAOImpl.class);
    
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EhCacheCacheManager ehCacheManager;

    @Autowired
    private EventDAO eventDAO;    
    
    @PostConstruct
    public void initEventCacheDAO() {
        setCacheByEvents(CommUtil.getToday());
    }

    @Override
    public Map<Integer, Seoulevent> getAllEventsMap() {

        Map<Integer, Seoulevent> resultMap = new HashMap<Integer, Seoulevent>();

        List<Seoulevent> list = eventDAO.getAllCurrentEventsList();

        for (Seoulevent event : list) {
            resultMap.put(event.getCultcode(), event);
        }

        return resultMap;
    }

    @Override
    public void setCacheByEvents(String stDate) {
        List<Seoulevent> events = eventDAO.getAllCurrentEventsList();
        Cache ehCacheAll = ehCacheManager.getCache("SeoulEventAll");
        Cache ehCacheAllPage = ehCacheManager.getCache("SeoulEventAllPage");

        int maxPageMem = 20;

        int countAll = 0;
        int countForPage = 0;

        int allEventSize = events.size();

        List<Seoulevent> eventAllPage = new ArrayList<Seoulevent>();

        for (Seoulevent event : events) {
            countAll++;
            ehCacheAll.put(event.getCultcode(), event);

            if (event.getEnd_date().compareTo(stDate) < 0) continue;

            countForPage++;
            eventAllPage.add(event);

            
            if (countForPage % maxPageMem == 0) {
                
                int key = (int) (countForPage / maxPageMem);
                ehCacheAllPage.put(key, new ArrayList<Seoulevent>(eventAllPage));
                eventAllPage.clear();
                
            } else if ((countForPage % maxPageMem != 0) && (countAll == allEventSize)) {
                
                int key = (int) (countForPage / maxPageMem) + 1;
                ehCacheAllPage.put(key, new ArrayList<Seoulevent>(eventAllPage));
                eventAllPage.clear();
                
            }
            
        }

    }

    @CacheEvict(cacheNames = { "SeoulEventAll", "SeoulEventAllPage"}, allEntries = true)
    public void clearCache() {
    }

    @Override
    @Cacheable(cacheNames = "SeoulEventAll", key = "#cultcode")
    public Seoulevent getEventByCode(int cultcode) {
        simulateSlowService(cultcode);
        return null;
    }

    @Cacheable(cacheNames = "SeoulEventAllPage", key = "#pageNo")
    public List<Seoulevent> getEventsByPageNo(int pageNo) {
        return null;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    @Cacheable(cacheNames = "SeoulEventSearchText")    
    public List<Seoulevent> getCacheEventsBySearchText(EventSearchParameter searchParam) {
        
        Ehcache cache  = ehCacheManager.getCacheManager().getEhcache("SeoulEventAll");

        Attribute<String> cultcodeAt= cache.getSearchAttribute("cultcode");
        Attribute<String> codenameAt= cache.getSearchAttribute("codename");
        Attribute<String> titleAt   = cache.getSearchAttribute("title");
        Attribute<String> programAt = cache.getSearchAttribute("program");
        Attribute<String> playerAt  = cache.getSearchAttribute("player");
        Attribute<String> contentsAt= cache.getSearchAttribute("contents");
        Attribute<String> gcodeAt   = cache.getSearchAttribute("gcode");  
        Attribute<String> endDateAt = cache.getSearchAttribute("end_date");
        Attribute<Integer> subjcodeAt= cache.getSearchAttribute("subjcode");
        Attribute<String> is_freeAt = cache.getSearchAttribute("is_free");
        
        Criteria searchCriteria = new Or(
                                        codenameAt.ilike  ("*" +  searchParam.getSearchText() + "*")
                                      , titleAt.ilike     ("*" +  searchParam.getSearchText() + "*"))
                                    .or(programAt.ilike   ("*" +  searchParam.getSearchText() + "*"))
                                    .or(playerAt.ilike    ("*" +  searchParam.getSearchText() + "*"))
                                    .or(contentsAt.ilike  ("*" +  searchParam.getSearchText() + "*"))
                                    .or(gcodeAt.ilike     ("*" +  searchParam.getSearchText() + "*"))
                                    .and(endDateAt.ge(CommUtil.getToday()));                
        
        if (!searchParam.getSubjcode().equals("ALL")){
            searchCriteria = searchCriteria.and(subjcodeAt.eq(Integer.valueOf(searchParam.getSubjcode())));
        }        
        
        if (searchParam.isFreeOnly()){
            searchCriteria = searchCriteria.and(is_freeAt.eq("1"));
        }

        
        net.sf.ehcache.search.Query query = cache.createQuery()
                .addCriteria(searchCriteria)
                .addOrderBy(cultcodeAt, Direction.DESCENDING)
                .includeValues()
                .end();
        
        List<Seoulevent> eventsForReturn = new ArrayList<Seoulevent>();
        List<Result> resultList = query.execute().all();
        
        int numForPaging = 0;
        int maxNumForPaging = 20;
        int startNum = (searchParam.getPageNo()-1) * maxNumForPaging + 1;
        int endNum   = searchParam.getPageNo() * maxNumForPaging + 1;        
        
        for(Result ehResult : resultList){
            numForPaging++;
            
            if(numForPaging <  startNum) continue;
            if(numForPaging >= endNum) break;
            
            eventsForReturn.add((Seoulevent) ehResult.getValue());
        }
        
        return eventsForReturn;
    }    

    // Don't do this at home
    private void simulateSlowService(int cultcode) {
        LOGGER.debug("This is non-cache : " + cultcode);
        /*
         * try { long time = 3000L; Thread.sleep(time); } catch
         * (InterruptedException e) { throw new IllegalStateException(e); }
         */
    }

    public void printAllCache() {
        
        String[] cacheNames = ehCacheManager.getCacheManager().getCacheNames();

        for (String cacheName : cacheNames) {
            List strArr = ehCacheManager.getCacheManager().getCache(cacheName).getKeys();
            for (Object string : strArr) {
                LOGGER.debug(cacheName + "-Member : " + string.toString());
            }
        }
    }

}
package com.eventpage.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eventpage.common.util.CommUtil;
import com.eventpage.dao.EventDAO;
import com.eventpage.entity.Commcode;
import com.eventpage.entity.Seoulevent;

@Repository
public class EventDAOImpl implements EventDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Seoulevent getEventById(int cultCode) {
        return entityManager.find(Seoulevent.class, cultCode);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Seoulevent> getAllEvents() {
        String hql = "FROM Seoulevent as evnt ORDER BY evnt.id.cultcode";
        return (List<Seoulevent>) entityManager.createQuery(hql).getResultList();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Seoulevent> getAllCurrentEventsList() {

        StringBuffer sqlB = new StringBuffer();

        sqlB.append("SELECT  a.*                               ");
        sqlB.append("  FROM  seoulevent a                      ");
        sqlB.append("inner join                                ");
        sqlB.append("   (                                      ");
        sqlB.append("       SELECT                             ");
        sqlB.append("           cultcode                       ");
        sqlB.append("         , MAX(s.createdate) AS createdate");
        sqlB.append("       FROM   seoulevent s                ");
        sqlB.append("       GROUP BY s.cultcode                ");
        sqlB.append("   ) b                                    ");
        sqlB.append("    on a.createdate = b.createdate        ");
        sqlB.append("   and a.cultcode = b.cultcode            ");
        sqlB.append("order by a.cultcode desc                  ");

        Query nSql = entityManager.createNativeQuery(sqlB.toString(), Seoulevent.class);

        return (List<Seoulevent>) nSql.getResultList();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Seoulevent> getEventsBySearchText(String subjcode, String searchText, int pageNo, boolean freeOnly) {
        StringBuffer sql = new StringBuffer();
        
        sql.append("SELECT  a.*                               ");
        sql.append("  FROM  seoulevent a                      ");
        sql.append("inner join                                ");
        sql.append("   (                                      ");
        sql.append("       SELECT                             ");
        sql.append("           cultcode                       ");
        sql.append("         , MAX(s.createdate) AS createdate");
        sql.append("       FROM   seoulevent s                ");
        sql.append("       GROUP BY s.cultcode                ");
        sql.append("   ) b                                    ");
        sql.append("    on a.createdate = b.createdate        ");
        sql.append("   and a.cultcode = b.cultcode            ");                
        sql.append("where(upper(a.codename) like :searchText  ");
        sql.append("   or upper(a.title)    like :searchText  ");
        sql.append("   or upper(a.program)  like :searchText  ");
        sql.append("   or upper(a.player)   like :searchText  ");
        sql.append("   or upper(a.contents) like :searchText  ");
        sql.append("   or upper(a.gcode)    like :searchText) ");
        sql.append("  AND a.end_date >= :toDay                ");
        
        if (!subjcode.equals("ALL")){
            sql.append("  AND a.subjcode =" + subjcode);
        }
        if (freeOnly){
            sql.append("  AND a.is_free = 1 ");
        }
        sql.append("  order by a.cultcode desc");

        Query query = (Query) entityManager.createNativeQuery(sql.toString(), Seoulevent.class)
                .setParameter("searchText", "%" + searchText.toUpperCase() + "%").setParameter("toDay", CommUtil.getToday());

        query.setFirstResult((pageNo - 1) * 20);
        query.setMaxResults(20);

        return (List<Seoulevent>) query.getResultList();
    }    

    @Override
    public void addEvent(Seoulevent event) {
        entityManager.persist(event);
    }

    @Override
    public void updateEvent(Seoulevent event) {
        entityManager.flush();
    }

    @Override
    public void deleteEvent(String createdate, int cultCode) {
        entityManager.remove(getEventById(cultCode));
    }

    @Override
    public void deleteEvents(String createdate) {

        String hql = "delete FROM Seoulevent as evnt WHERE evnt.createdate = ?";

        entityManager.createQuery(hql).setParameter(1, createdate).executeUpdate();

    }

}

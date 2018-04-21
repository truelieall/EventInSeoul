package com.eventpage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventpage.common.util.CommUtil;
import com.eventpage.dao.MylistDAO;
import com.eventpage.entity.Seouleventmylist;
import com.eventpage.service.MylistService;

/**
 * @Date : 2018. 4. 5. 
 * @author Kim jongseong
 * @Descrption : Mylist 관련 Service 
 */
@Service
public class MylistServiceImpl implements MylistService {
    @Autowired
    private MylistDAO mylistDao;

    @Override
    public List<Seouleventmylist> getAllMylists() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Seouleventmylist getMylist(int list_id) {

        return mylistDao.getMyList(list_id);
    }

    @Override
    public Seouleventmylist getMylist(String user_id, int cultCode) {

        return CommUtil.isEmpty(user_id) ? null : mylistDao.getMyList(user_id, cultCode);
    }

    @Override
    public List<Seouleventmylist> getMylists(String user_id, int pageNo) {
        // TODO Auto-generated method stub
        return mylistDao.getMyLists(user_id, pageNo);
    }

    @Override
    @Transactional
    public void handleMylist(Seouleventmylist mylist, String method) {

        if (CommUtil.isEmpty(mylist.getUser_id())) {
            throw new RuntimeException("userId is null");
        }

        if ("Y".equals(method)){         
            addMylist(mylist);
        }
        else if ("N".equals(method)){
            deleteMylist(getMylist(mylist.getList_no()));
        }

    }

    @Override
    public void addMylist(Seouleventmylist mylist) {

        mylist.setFrstRegisterId(mylist.getUser_id());
        mylist.setLastUpdusrId(mylist.getUser_id());

        mylistDao.addMylist(mylist);
    }

    @Override
    public void updateMylist(Seouleventmylist myList) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteMylist(Seouleventmylist myList) {
        mylistDao.deleteMylist(myList);
    }

}

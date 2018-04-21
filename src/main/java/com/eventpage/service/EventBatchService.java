package com.eventpage.service;

import java.util.List;

import static com.eventpage.common.Constants.SeoulApiConst;
import com.eventpage.entity.Seoulevent;

public interface EventBatchService {

    void batchSeoulEvent();

    SeoulApiConst.ApiResultStatus apiCallAndAddEvents();

    SeoulApiConst.ApiResultStatus apiCallAndAddSubjcode();

}

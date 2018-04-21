/**
 * @Date : 2018. 4. 12. 
 * @author Kim jongseong
 * @Descrption : 
 */
package com.eventpage.dto;

import com.eventpage.common.util.CommUtil;

import lombok.Getter;
import lombok.Setter;
/**
 * @Date : 2018. 4. 12. 
 * @author Kim jongseong
 * @Descrption : 
 */
@Getter @Setter
public class EventSearchParameter {
    
    private String subjcode;
    private String searchText;
    private int pageNo;
    private boolean freeOnly;
    
    public EventSearchParameter(){
        this.subjcode = "ALL";
        this.searchText = "";
        this.pageNo = 1;
        this.freeOnly = false;
    }
    
    public EventSearchParameter(String subjcode, String searchText, int pageNo, boolean freeOnly){
        this.subjcode = subjcode;
        this.searchText = searchText;
        this.pageNo = pageNo;
        this.freeOnly = freeOnly;
    }
   
    
    /**
     * @Date : 2018. 4. 13. 
     * @author Kim jongseong
     * @Descrption : 기본검색조건 여부를 판단 (특별한 조건이 없는 경우)
     * @return
     */
    public boolean isNoConditionSearch(){
        
        if("ALL".equals(this.subjcode) 
        && CommUtil.isEmpty(this.searchText)
        && this.freeOnly == false){ 
            return true;
        }
        
        return false;
    }
    
}
package com.eventpage.common;
/**
 * @Date : 2018. 4. 12. 
 * @author Kim jongseong
 * @Descrption : 상수모음
 */
import lombok.Getter;


public class Constants {
  
	/**
	 * Batch 처리관련
	 */	
    @Getter
    public enum BatchStatus {
        OK("0000","정상처리"),
        FAIL("9001","오류");
        
        private String code;
        private String value;
        
        BatchStatus(String code, String value){
            this.code = code;
            this.value = value;
        }
    }
    
    
    public static class SeoulApiConst {
        
        @Getter
        public enum ApiResultStatus{ 
            OK("INFO-000","정상 처리되었습니다"),
            AUTH("INFO-100","인증키가 유효하지 않습니다. 인증키가 없는 경우, 열린 데이터 광장 홈페이지에서 인증키를 신청하십시오."),
            NONE("INFO-200","해당하는 데이터가 없습니다."),
            ER_300("ERROR-300","필수 값이 누락되어 있습니다. 요청인자를 참고 하십시오."),
            ER_301("ERROR-301","해당하는 서비스를 찾을 수 없습니다. 요청인자 중 SERVICE를 확인하십시오."),
            ER_310("ERROR-310","필수 값이 누락되어 있습니다. 요청인자를 참고 하십시오."),
            ER_331("ERROR-331","요청시작위치 값을 확인하십시오.요청인자 중 START_INDEX를 확인하십시오."),
            ER_332("ERROR-332","요청종료위치 값을 확인하십시오.요청인자 중 END_INDEX를 확인하십시오."),
            ER_333("ERROR-333","요청위치 값의 타입이 유효하지 않습니다.요청위치 값은 정수를 입력하세요."),
            ER_334("ERROR-334","요청종료위치 보다 요청시작위치가 더 큽니다.요청시작조회건수는 정수를 입력하세요."),
            ER_335("ERROR-335","샘플데이터(샘플키:sample) 는 한번에 최대 5건을 넘을 수 없습니다.요청시작위치와 요청종료위치 값은 1 ~ 5 사이만 가능합니다."),
            ER_336("ERROR-336","데이터요청은 한번에 최대 1000건을 넘을 수 없습니다.요청종료위치에서 요청시작위치를 뺀 값이 1000을 넘지 않도록 수정하세요."),
            ER_500("ERROR-500","서버 오류입니다.지속적으로 발생시 열린 데이터 광장으로 문의(Q&A) 바랍니다."),
            ER_600("ERROR-600","데이터베이스 연결 오류입니다.지속적으로 발생시 열린 데이터 광장으로 문의(Q&A) 바랍니다."),
            ER_601("ERROR-601","SQL 문장 오류 입니다.지속적으로 발생시 열린 데이터 광장으로 문의(Q&A) 바랍니다."),
            UNDF("UNDF-999","정의되지 않은 ERROR");
            private String code;
            private String value;
            
            ApiResultStatus(String code, String value){
                this.code = code;
                this.value = value;
            }
            
            public boolean isOK() {            
                return ApiResultStatus.OK == this;
            }            
        }
        
        public static ApiResultStatus getStatus(String inCode) {
            
            ApiResultStatus returnStatus = null;
            
            for(ApiResultStatus status : ApiResultStatus.values()) {
                if(status.getCode().equals(inCode)) {
                    returnStatus = status;
                    break;
                }
            };
            
            return returnStatus == null ? ApiResultStatus.UNDF : returnStatus;
        }
          

    }
    

    
}

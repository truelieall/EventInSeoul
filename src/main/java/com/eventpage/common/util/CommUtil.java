package com.eventpage.common.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class CommUtil {
    // --------------------------------------------------------------------------
    // # Log 설정
    // --------------------------------------------------------------------------
    private static final Logger LOGGER = LoggerFactory.getLogger(CommUtil.class);

    /**
     * String에서 주어진 길이 이상은 잘라낸다. 한글이 중간에 잘린경우 해당 한글도 잘라낸다.
     * 
     * @param src
     * @param size
     * @return
     */
    public static String trimHan(String src, int size) {
        if (src == null || size <= 0)
            return "";

        byte[] srcBytes = src.getBytes();
        int len = srcBytes.length;

        if (size >= len)
            return src;

        byte[] dstBytes = new byte[size];

        int hanCnt = 0;
        for (int i = 0; i < size; i++) {
            dstBytes[i] = srcBytes[i];
            if ((dstBytes[i] & 0xFF) > 0x7F)
                hanCnt++;
        }

        if (((hanCnt % 2) != 0) && ((dstBytes[size - 1] & 0xFF) > 0x7F))
            size--;

        return new String(dstBytes, 0, size);
    }

    /**
     * html Tag 제거.
     * 
     * @param src
     * @return
     */
    public static String removeTag(Object obj) {
        if (obj == null) {
            return "";
        }
        String str = String.valueOf(obj);
        return str.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "").replaceAll("&nbsp;", "")
                .replaceAll("\\r", "").replaceAll("\\n", "");
    }

    /**
     * 16진수값으로된 String을 int형으로 출력합니다.
     * 
     * @param str
     *            String Hex 문자열.
     * @return 10진수 형식으로 포맷팅된 int.
     */
    public static int stringHexToInt(String str) {
        byte[] b2 = new byte[str.length()];

        int temp = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i, i + 1).equals("A") || str.substring(i, i + 1).equals("a"))
                b2[i] = (byte) 0x0A;
            else if (str.substring(i, i + 1).equals("B") || str.substring(i, i + 1).equals("b"))
                b2[i] = (byte) 0x0B;
            else if (str.substring(i, i + 1).equals("C") || str.substring(i, i + 1).equals("c"))
                b2[i] = (byte) 0x0C;
            else if (str.substring(i, i + 1).equals("D") || str.substring(i, i + 1).equals("d"))
                b2[i] = (byte) 0x0D;
            else if (str.substring(i, i + 1).equals("E") || str.substring(i, i + 1).equals("e"))
                b2[i] = (byte) 0x0E;
            else if (str.substring(i, i + 1).equals("F") || str.substring(i, i + 1).equals("f"))
                b2[i] = (byte) 0x0F;
            else
                b2[i] = (byte) (Integer.parseInt(str.substring(i, i + 1)) & 0x0F);

            temp = temp + (b2[i] << 4 * (str.length() - (i + 1)));
        }
        return temp;
    }

    public static String stringToBinary(String str) {
        String binaryString = "";
        String temp = "";
        int off = 0;
        for (int i = 0; i < str.length(); i++) {
            int value = CommUtil.stringHexToInt(str.substring(off, off + 1));
            binaryString = Integer.toBinaryString(value);
            while (binaryString.length() % 4 != 0) {
                binaryString = "0" + binaryString;
            }

            temp = temp + binaryString;
            off++;
        }
        return temp;
    }

    /**
     * 
     * @param value
     * @return
     */
    public static int parseInt(String value) {
        if (value == null || value.length() == 0)
            return 0;
        String v = "";
        int radix = 10; /* default 10진수 */
        if (value.startsWith("0x") || value.startsWith("0X")) { /* 16진수 */
            radix = 16;
            v = value.substring(2);
        } else {
            radix = 10;
            v = value;
        }
        return Integer.parseInt(v, radix);
    }

    /**
     * 
     * @param value
     * @return
     */
    public static String valueOf(int value) {
        if (value == 0)
            return "0";
        return String.valueOf(value);
    }

    /**
     * 빈문자 체크
     * 
     * @param val
     * @return
     */
    public static boolean isEmpty(String val) {
        if (val == null || val.trim().length() == 0)
            return true;
        else
            return false;
    }

    public static String nvl(String src) {
        if (src == null || "".equals(src) || src.length() <= 0)
            return "";
        return src;
    }

    public static String nvl(String src, String rtn) {
        if (isEmpty(src) || "null".equals(src))
            return rtn;
        return src;
    }

    public static int nvlInt(String src, int rtn) {
        if (isEmpty(src) || "null".equals(src)) {
            return rtn;
        } else {

            try {
                return Integer.parseInt(src);
            } catch (Exception e) {
                LOGGER.error("nvlInt.e : " + e);
                return 0;
            }
        }
    }

    public static long nvlLong(String src, long rtn) {
        if (isEmpty(src) || "null".equals(src)) {
            return rtn;
        } else {

            try {
                return Long.parseLong(src);
            } catch (Exception e) {
                LOGGER.error("nvlLong.e : " + e);
                return 0;
            }
        }
    }

    public static boolean nvlBoolean(String src, boolean rtn) {
        if (isEmpty(src) || "null".equals(src)) {
            return rtn;
        }

        boolean bRet = false;

        if ("N".equals(src)) {
            bRet = false;
        } else if ("0".equals(src)) {
            bRet = false;
        } else if ("Y".equals(src)) {
            bRet = true;
        } else if ("1".equals(src)) {
            bRet = true;
        } else {
            try {
                bRet = Boolean.parseBoolean(src);
            } catch (Exception e) {
                LOGGER.error("nvlBoolean.e : " + e);
            }
        }

        return bRet;
    }

    /*
     * 주민 등록 번호 체크
     */
    public static boolean chkJuminNum(String num) {
        if (num == null) {
            return false;
        }
        try {
            int a = Integer.parseInt(num.substring(0, 6));

            if (num.trim().length() == 7) { // 뒷번호
                if (num.charAt(0) != '1' && num.charAt(0) != '2') {
                    return false;
                }
            } else if (num.trim().length() == 13) { // 전체
                int b = Integer.parseInt(num.substring(6, 13));

                if (num.charAt(6) != '1' && num.charAt(6) != '2') {
                    return false;
                }

                int yy = Integer.parseInt(num.substring(0, 2));
                int mm = Integer.parseInt(num.substring(2, 4));
                if (mm < 1 || mm > 12) {
                    return false;
                }

                int dd = Integer.parseInt(num.substring(4, 6));
                int[] mmDD = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
                if (dd < 1 || dd > mmDD[mm - 1]) {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            LOGGER.error("ERROR==>" + e.toString());
            return false;
        }
        return true;
    }

    public static String getJuminNumMasking(String num) {
        if (num == null || num.length() < 6) {
            return num;
        } else if (num.length() == 6) {
            return "******";
        } else if (num.length() == 7) {
            return "*******";
        }

        return (num.substring(0, 6) + "*******");
    }

    /**
     * Base64 Char 중 Web에서 특수문자로 인식하는 부분을 변경한다.
     * 
     * @param input
     * @return
     */
    public static String encodeWebBase64(String in) {
        if (in == null)
            return in;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);
            if (c == '+')
                c = '-';
            else if (c == '=')
                c = '_';
            else if (c == '/')
                c = '*';
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Base64 Decoder
     * 
     * @param in
     * @return
     */
    public static String decodeWebBase64(String in) {
        if (in == null)
            return in;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);
            if (c == '-')
                c = '+';
            else if (c == '_')
                c = '=';
            else if (c == '*')
                c = '/';
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * LPAD
     * 
     * @param str
     *            입력 및 반환
     * @param addStr
     *            add할 문자
     * @param len
     *            전체 길이
     * @return
     */
    public static String getLpad(String str, String addStr, int len) {

        if (addStr == null || addStr.length() == 0)
            return str;

        if (str == null)
            str = "";

        if (len < str.length())
            return str;

        int tmpLen = len - str.length();

        for (int i = 0; i < tmpLen; i++)
            str = addStr + str;

        return str;
    }

    /**
     * 랜덤번호생성
     * 
     * @param len
     * @return
     */
    public static String generateKey(int len) {

        Random r = new Random();
        r.setSeed(new Date().getTime());
        StringBuffer key = new StringBuffer();

        while (key.length() < len) {
            int iKey = Math.abs(r.nextInt() % 74) + 48;
            if (iKey >= 48 && iKey <= 57) {
                key.append((char) iKey);
            }
        }
        return key.toString();
    }

    public static boolean isNull(String argStr) {
        if (argStr == null) {
            return true;
        } else if ("".equals(argStr)) {
            return true;
        } else if ("null".equals(argStr)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getString(Object argObj) {
        if (argObj != null) {
            return "" + argObj;
        } else {
            return "";
        }
    }

    public static String getString(String argStr) {
        if (argStr != null) {
            return "" + argStr;
        } else {
            return "";
        }
    }

    public static String getString(int argInt) {
        return "" + argInt;
    }

    public static int getInt(String argStr) {
        try {
            return Integer.parseInt(argStr);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getInt(Object argObj) {
        try {
            return Integer.parseInt("" + argObj);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double getDouble(Object argObj) {
        try {
            return Double.parseDouble("" + argObj);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String pad(String str, String padStr, int size, int mode) {
        String paddedString = "";
        if (str == null) {
            return "";
        }

        int strLen = str.length();

        if ((size < 1) || (strLen >= size)) {
            return str;
        }

        for (int i = 0; i < (size - strLen); i++) {
            paddedString += padStr;
        }
        if (mode == -1) {
            paddedString += str; // left padding
        } else {
            paddedString = str + paddedString; // right padding
        }

        return paddedString;
    }

    public static String getOnlyValue(String strArg) {
        String result = "";

        if (strArg != null) {
            char[] arrChar = strArg.toCharArray();

            boolean boolStart = false;
            for (int i = 0; i < arrChar.length; i++) {

                if (!"0".equals("" + arrChar[i]) && !" ".equals("" + arrChar[i])) {
                    boolStart = true;
                }

                if (boolStart) {
                    result += arrChar[i];
                }
            }
        }

        return result;
    }

    /**
     * 카멜 형식으로 변환
     * 
     * @param underScore
     * @return
     */
    public static String convert2CamelCase(String underScore) {

        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        int len = underScore.length();

        for (int i = 0; i < len; i++) {
            char currentChar = underScore.charAt(i);
            if (currentChar == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(currentChar));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(currentChar));
                }
            }
        }
        return result.toString();
    }

    /**
     * 문자열 인자들을 합쳐서 하나의 문자열로 반환
     * 
     * @param delimeter
     *            구분자
     * @param args
     *            합칠 문자열들
     * @author 임종현
     * @return
     */
    public static String join(String delimeter, String... args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if (i != args.length - 1) {
                sb.append(delimeter);
            }
        }

        return sb.toString();
    }

    public static Double parseDouble(BigDecimal decimal) {
        if (decimal != null) {
            return decimal.doubleValue();
        } else {
            return new Double(0);
        }

    }

    public static boolean isNull(Object argObj) {
        String argStr = "" + argObj;

        if (argStr == null) {
            return true;
        } else if ("".equals(argStr)) {
            return true;
        } else if ("null".equals(argStr)) {
            return true;
        } else {
            return false;
        }
    }

    public static String lPadType(String str, int size, String strType) {
        String rtnStr = "";

        if ("N".equals(strType)) {
            rtnStr = lPad(str, size, "0");
        } else {
            rtnStr = lPad(str, size, " ");
        }

        return rtnStr;
    }

    public static String lPadC(String str, int size) {
        return lPad(str, size, " ");
    }

    public static String lPadN(String str, int size) {
        return lPad(str, size, "0");
    }

    public static String lPad(String str, int size, String padStr) {
        return pad(str, padStr, size, -1);
    }

    public static String rPad(String str, int size, String padStr) {
        return pad(str, padStr, size, 1);
    }


    /**
     * 전달받은 문자열이 빈 값(null, 혹은 빈 문자열) 일 경우 false 아닐 경우, true 를 return 한다.
     *
     * @param pStrSrc
     *            {String} 원본 문자열
     * @return {boolean} 빈 값(null or 빈 문자열)=false / !빈 값(null and 빈 문자열)=true
     */
    public static boolean isNotEmpty(String pStrSrc) {
        return !(pStrSrc == null || "".equals(pStrSrc));
    }

    
    public static String getStackTraceString(Exception e) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pinrtStream = new PrintStream(out);
        e.printStackTrace(pinrtStream);
        return out.toString();
    }


    /**
     * Json Object -> String (pretty)
     * 
     * @param inObj
     * @return
     */
    public static String jsonObjToPrettyString(Object inObj) {
        String outString = "";

        ObjectWriter objWrt = new ObjectMapper().writerWithDefaultPrettyPrinter();
        try {
            outString = objWrt.writeValueAsString((inObj));
        } catch (Exception e) {
            LOGGER.error("trans error occur");
        }

        return outString;
    }

    /**
     * print Json Object (pretty)
     */
    public static void printJsonObj(Object inObj) {
        LOGGER.info(jsonObjToPrettyString(inObj));
    }

    /**
     * return Today (19000101 [yyyyMMdd])
     */    
    public static String getToday() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    
    /**
     * printCookies (temp) 
     */    
    private static void printCookies(HttpServletRequest request){
        
        Cookie[] test = request.getCookies();
        for (Cookie c : test) {
            LOGGER.debug(c.getPath());
            LOGGER.debug(c.getName());
            LOGGER.debug(c.getDomain());
            LOGGER.debug(c.getValue());
        }       
    }
        
}

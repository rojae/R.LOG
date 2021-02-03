package kr.or.rlog.utils;

import java.util.Random;

public class RandomUtils {

    public static String getAlpha1(){
        Random rnd = new Random();
        return  String.valueOf((char) ((int) (rnd.nextInt(26)) + 97));
    }

    public static String getAlpha2(){
        Random rnd = new Random();
        return String.valueOf((char) ((int) (rnd.nextInt(26)) + 65));
    }

    /*
     * @param len : 생성할 랜덤 문자열의 길이
     */
    public static String getAlpha(int len) {
        if(len > 0) {
            char[] tmp = new char[len];
            for(int i=0; i<tmp.length; i++) {
                int div = (int) Math.floor( Math.random() * 2 );
                if(div == 0) { // 0이면 숫자로
                    tmp[i] = (char) (Math.random() * 10 + '0') ;
                 }else {         //1이면 알파벳
                    tmp[i] = (char) (Math.random() * 26 + 'A') ;
                }
            }
            return new String(tmp);
        }
        return "ERROR : Size is required.";
    }

    /*
     * @param len : 생성할 난수의 길이
     * @param dupCd : 중복 허용 여부 (1: 중복허용, 2:중복제거)
     */
    public static String getNumber(int len, int dupCd ) {
        Random rand = new Random();
        String numStr = "";
        for(int i=0;i<len;i++) {
            String ran = Integer.toString(rand.nextInt(10));
            if(dupCd==1) {
                numStr += ran;
            }else if(dupCd==2) {
                if(!numStr.contains(ran)) {
                    numStr += ran;
                }else {
                    i-=1;
                }
            }
        }
        return numStr;
    }

}

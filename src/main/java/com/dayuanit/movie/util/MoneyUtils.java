package com.dayuanit.movie.util;

import java.math.BigDecimal;

public class MoneyUtils {

    public static String plusMoney(String num1,String num2){
        BigDecimal bigDecimal1 = new BigDecimal(num1);
        BigDecimal bigDecimal2 = new BigDecimal(num2);
        return bigDecimal1.add(bigDecimal2).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String reduceMoney(String num1,String num2){
        BigDecimal bigDecimal1 = new BigDecimal(num1);
        BigDecimal bigDecimal2 = new BigDecimal(num2);
        return bigDecimal1.subtract(bigDecimal2).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
    }

    public static String mul(String num1,String num2){
        BigDecimal bigDecimal1 = new BigDecimal(num1);
        BigDecimal bigDecimal2 = new BigDecimal(num2);
        return bigDecimal1.multiply(bigDecimal2).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
    }
}

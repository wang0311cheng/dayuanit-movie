package com.dayuanit.movie.enums;

public enum  OrderStatusEnums {

    待付款(0,"待付款"),
    付款成功(1,"付款成功"),
    取消(2,"取消");

    private int k;
    private String v;

    private OrderStatusEnums(int k,String v){
        this.k = k;
        this.v = v;
    }

    public int getK() {
        return k;
    }

    public String getV() {
        return v;
    }
}

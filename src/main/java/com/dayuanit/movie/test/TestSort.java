package com.dayuanit.movie.test;

import java.util.ArrayList;
import java.util.Arrays;

public class TestSort {

    public static final int[] num = {2, 9, 5, 7, 3, 6};

    public static void main(String[] args) {
//        Integer a = new Integer(3);
//        Integer b =3;
//        Integer d =3;
//        int c = 3;
//        System.out.println(a==b);
//        System.out.println(a==c);
//        System.out.println(c==b);
//        System.out.println(d==b);
//        int[] a = {1,2,3,4,5};
//        System.out.println(a.length);
//        ArrayList<Object> objects = new ArrayList<>();
//        objects.add(1);
//        objects.add(2);
//        objects.add(3);
//        System.out.println(objects.size());
        int a = 5;
        int b = 4;
        int c = a;
        a = b;
        System.out.println(a);

//        String a = "我";
//        String b = "你";
//        System.out.println(a.compareTo(b));

     //        int a =1;
//        System.out.println(a++);
//        System.out.println(a++);
//        a++;
//        System.out.println(a++);
//        System.out.println(b);
//        System.out.println(++a);
//
//        maopao();
//        xuanze();
//        charu();
    }

    // 冒泡排序
    public static void maopao(){
        for (int i =0; i<num.length-1;i++){
            for (int j =0;j<num.length-1-i;j++){
                if (num[j]>num[j+1]){
                    int temp = num[j];
                    num[j] = num[j+1];
                    num[j+1] = temp;
                }
            }
        }

        System.out.println(Arrays.toString(num));
    }

    // 选择排序
    public static void xuanze(){
        for (int i = 0; i<num.length-1; i++){
            // 定义一个默认的最小值
            int min = i;
            // 拿当前后面的值跟他比
            for (int j = i+1; j<num.length; j++){
                // 判断 当前的min的值还是不是最小的，不是就重新赋值
                if (num[min] > num[j]){
//                    不是就重新赋值
                    min = j;
                }
            }
            // 判断如果min与i不相等，则说明最小值已经变掉
            if (min != i){
                int temp = num[min];
                num[min] = num[i];
                num[i] = temp;
            }
        }

        System.out.println(Arrays.toString(num));
    }

    // 插入排序
    public static void charu(){

        for (int i = 0; i<num.length-1; i++){
            for (int j = i+1; j>0 && num[j] < num[j-1];j--){
                int temp = num[j];
                num[j] = num[j-1];
                num[j-1] = temp;
            }
        }

        System.out.println(Arrays.toString(num));
    }

}

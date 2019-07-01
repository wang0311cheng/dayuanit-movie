package com.dayuanit.movie.test;

import java.util.Arrays;

public class Dog implements Comparable<Dog> {

    private int price;
    private int weight;

    public Dog(int price){
        this.price = price;
    }


    @Override
    public String toString() {
        return this.price + "";
    }

    @Override
    public int compareTo(Dog dog) {
        // 哪个对象调用这个方法，这个this就是指哪个对象
        return this.price - dog.price;
    }


    public static void main(String[] args) {
        Dog dog1 = new Dog(200);
        Dog dog2 = new Dog(800);
        Dog dog3 = new Dog(500);
//        System.out.println(dog1.compareTo(dog2));

        Dog[] dogs = {dog1,dog2,dog3};
//        Arrays.sort(dogs);
        Arrays.sort(dogs,new DogComparator());
        System.out.println(Arrays.toString(dogs));
    }

    public int getPrice() {
        return price;
    }

    public int getWeight() {
        return weight;
    }
}

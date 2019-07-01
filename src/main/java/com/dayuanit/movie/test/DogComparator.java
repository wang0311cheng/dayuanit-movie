package com.dayuanit.movie.test;

import java.util.Comparator;

public class DogComparator implements Comparator<Dog> {

    @Override
    public int compare(Dog dog1, Dog dog2) {
        return dog1.getPrice()-dog2.getPrice();
    }
}
